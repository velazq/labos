package labos;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.Arrays;

import java.util.stream.Collectors;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

    public static final String SEATS_URL = "https://web.fdi.ucm.es/labs/estado_lab.asp";
    public static final String USAGE_URL = "https://web.fdi.ucm.es/Docencia/Horarios.aspx?AulaLab_Cod=%s&fdicurso=%s";
    public static final String TERM = "2016-2017"; // TODO: extraer a config.

    public static Map<Integer, Lab> getLabsInfo() {
        Map<Integer, Lab> info = new HashMap<>();
        Document doc = null;

        try {
            doc = Jsoup.connect(SEATS_URL).get();
        } catch (IOException e) {
            return info; // FIXME
        }

        Elements free = doc.select(".tabla font[color='#006F00']");
        Elements used = doc.select(".tabla font[color='#FF0000']");

        for (int i = 0; i < free.size(); i++) {
            Element e1 = free.get(i);
            Element e2 = used.get(i);

            Lab lab = new Lab(i + 1);
            lab.setFreeSeats(parseSeatsList(e1.text()));
            lab.setUsedSeats(parseSeatsList(e2.text()));

            info.put(i + 1, lab);
        }

        return info;
    }

    private static Set<Integer> parseSeatsList(String str) {
        return Arrays.asList(str.split(" "))
                     .stream()
                     .map(s -> s.replaceAll("\\D+", ""))
                     .filter(s -> !s.equals(""))
                     .map(Integer::parseInt)
                     .collect(Collectors.toCollection(TreeSet::new));
    }

    public static Map<String, Map<String, String>> getHoursInfo(Lab lab) { // {"1": {"9:30": "LP1"}

        Document doc = null;
        String url = String.format(USAGE_URL, lab.getLabCode(), TERM);
        List<String> days = Arrays.asList("1", "2", "3", "4", "5"); // "1" => lunes
        List<String> hours = Arrays.asList(
             "9:00",  "9:30", "10:00", "10:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30", "14:00", "14:30",
            "15:00", "15:30", "16:00", "16:30", "17:00", "17:30",
            "18:00", "18:30", "19:00", "19:30", "20:00", "20:30");

        try {
            doc = Jsoup.connect(url).timeout(10000).get();
        } catch (IOException e) {
            return null; // FIXME
        }

        int numDaysTbl = days.size();
        int numHoursTbl = lab.hasHalfHours() ? hours.size() : hours.size() / 2;

        String[][] tbl = new String[numDaysTbl][numHoursTbl];

        Elements elems = doc.select("#ctl00_ContentPlaceHolder1_TabContainer1_TabPanel_1_GridViewHorario1 tr");

        elems.remove(0); // Quito la fila de la cabecera
        int h = 0;
        for (Element tr : elems) {
            Elements tds = tr.select("td");
            tds.remove(tds.size() - 1); // Quito el último (sábado)
            int d = 0;
            for (Element td : tds) {
                if ("#94AEC6".equals(td.attr("bgcolor"))) { // Hora o minutos
                    continue;
                }
                String txt = td.text().replace(String.valueOf((char)160), " ").trim(); // Para &nbsp
                while (tbl[d][h] != null) {
                    d++;
                }
                int rowspan = 1;
                if (td.hasAttr("rowspan")) {
                    rowspan = Integer.parseInt(td.attr("rowspan"));
                }
                for (int k = 0; k < rowspan; k++) {
                    tbl[d][h + k] = txt;
                }
            }
            h++;
        }

        Map<String, Map<String, String>> result = new HashMap<>();
        for (int i = 0; i < numDaysTbl; i++) {
            Map<String, String> hrs = new HashMap<>();
            result.put(days.get(i), hrs);
            for (int j = 0; j < numHoursTbl; j++) {
                if (lab.hasHalfHours()) {
                    hrs.put(hours.get(j), tbl[i][j]);
                } else {
                    hrs.put(hours.get(j * 2), tbl[i][j]);
                    hrs.put(hours.get(j * 2 + 1), tbl[i][j]);
                }
            }
        }

        return result;
    }

}
