package labos;

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

import java.nio.file.Files;
import java.nio.file.Paths;


public class DAO {

    public static final String SEATS_URL = "https://web.fdi.ucm.es/labs/estado_lab.asp";

    private String timetableJSON;
    private String softwareJSON;

    public DAO() {
        // TODO: Cargamos el JSON de los horarios (y del software)
        // timetableJSON = "{\"lab1\":{\"1\":{\"9:00\":\"EDA\",\"9:30\":\"EDA\",\"10:00\":\"\"},\"2\":{\"9:00\":\"AW\",\"9:30\":\"AW\",\"10:00\":\"AW\"},\"3\":{\"9:00\":\"\",\"9:30\":\"\",\"10:00\":\"X\"}}}";
        try {
            timetableJSON = new String(Files.readAllBytes(Paths.get("Horarios.json")));
            softwareJSON = new String(Files.readAllBytes(Paths.get("Programas.json")));
        } catch (IOException e) {
            
        }
    }

    public String getTimetableJSON() {
        return timetableJSON;
    }
    
    public String getSoftwareJSON(){
    	return softwareJSON;
    }
    
    public Map<Integer, Lab> getLabsInfo() {
        Map<Integer, Lab> info = new HashMap<>();
        Document doc = null;

        try {
            doc = Jsoup.connect(SEATS_URL).get();
        } catch (IOException e) {
            return info;
        }

        Elements free = doc.select(".tabla font[color='#006F00']");
        Elements used = doc.select(".tabla font[color='#FF0000']");

        for (int i = 0; i < free.size(); i++) {
            Lab lab = new Lab(i + 1);

            Element e1 = free.get(i);
            Element e2 = used.get(i);

            lab.setFreeSeats(parseSeatsList(e1.text()));
            lab.setUsedSeats(parseSeatsList(e2.text()));

            info.put(i + 1, lab);
        }

       return info;
    }

    private Set<Integer> parseSeatsList(String str) {
        return Arrays.asList(str.split(" "))
                     .stream()
                     .map(s -> s.replaceAll("\\D+", ""))
                     .filter(s -> !s.equals(""))
                     .map(Integer::parseInt)
                     .collect(Collectors.toCollection(TreeSet::new));
    }

}