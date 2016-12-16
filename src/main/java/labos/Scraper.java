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
                     .collect(Collectors.toSet());
    }
}
