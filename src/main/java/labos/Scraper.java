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
    public static final int NUM_DAYS = 7;
    public static final int NUM_HOURS = 12;

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

            //Map<String, Map<Integer, String>> hoursInfo = getHoursInfo(lab.getLabCode());
            //lab.setHoursInfo(hoursInfo);

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

    public static Map<String, Map<Integer, String>> getHoursInfo(int labCode) { // {"lunes": {10: "LP1"}

        Document doc = null;
        String url = String.format(USAGE_URL, labCode, TERM);
        List<String> days = Arrays.asList("lunes", "martes", "miercoles", "jueves", "viernes");
        List<Integer> hours = Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

        try {
            doc = Jsoup.connect(url).timeout(10000).get();
        } catch (IOException e) {
            System.out.println(e.getMessage()); // FIXME
            return null; // FIXME
        }

        Hour[][] tbl = new Hour[NUM_DAYS][NUM_HOURS];
        for (int i = 0; i < days.size(); i++)
            for (int j = 0; j < hours.size(); j++)
                tbl[i][j] = new Hour();

        Elements elems = doc.select("#ctl00_ContentPlaceHolder1_TabContainer1_TabPanel_1_GridViewHorario1 tr");


        elems.remove(0); // Quito la fila de la cabecera
        int h = 0;
        for (Element tr : elems) {
            Elements tds = tr.select("td");
            tds.remove(0); // Quito el primero (columna de las horas)
            tds.remove(tds.size() - 1); // Quito el último (sábado)
            int d = 0;
            for (Element td : tds) {
                String txt = td.text().replace(String.valueOf((char) 160), "").trim(); // Para &nbsp
                //System.out.println(d + "," + (h+9) + " --> '" + txt + "'"); // FIXME
                while (tbl[d][h].isVisited()) {
                    d++;
                }
                if (td.attr("bgcolor").equals("White")) { // Hay clase
                    int rowspan = 1;
                    if (td.hasAttr("rowspan")) {
                        rowspan = Integer.parseInt(td.attr("rowspan"));
                    }
                    for (int k = 0; k < rowspan; k++) {
                        tbl[d][h + k].setSubjectName(txt);
                    }
                } else {
                    tbl[d][h].setEmpty();
                }
                d++;
            }
            h++;
        }

        Map<String, Map<Integer, String>> result = new HashMap<>();
        for (int i = 0; i < days.size(); i++) {
            Map<Integer, String> hrs = new HashMap<>();
            result.put(days.get(i), hrs);
            for (int j = 0; j < hours.size(); j++) {
                Integer key = hours.get(j);
                String value = tbl[i][j].getSubjectName();
                hrs.put(key, value);
            }
        }

        return result;
    }

    private static int getLabCode(int id) {
        if (id >= 1 && id <= 10) {
            return id + 206;
        } else if (id == 11) {
            return 229;
        }
        return 0; // FIXME
    }
}
