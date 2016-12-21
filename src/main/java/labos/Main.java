package labos;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
    	Scraper.iniciar();
    	staticFiles.location("/public");    	
        get("/lab/:labId", (request, result) -> showLabInfo(request.params(":labId")), new MustacheTemplateEngine());

    }

    private static ModelAndView showLabInfo(String labId) {
        
    	
    	Lab lab = getLab(labId);
        Map map = new HashMap();
        map.put("lab", lab);
        map.put("test", Scraper.getHoursInfo(lab).get("1").get("9:00")); // FIXME
        return new ModelAndView(map, "lab.mustache");
    }

    public static Lab getLab(String labId) {

        Integer id = 0;

        try {
            id = Integer.parseInt(labId);
        } catch (Exception e) {
            notFound("<html><body><h1>404: el labo '" + id + "' no existe</h1></body></html>");
        }

        Lab lab = Scraper.getLabsInfo().get(id);

        if (lab == null) {
            notFound("<html><body><h1>404: el labo '" + id + "' no existe</h1></body></html>");
        }

        return lab;
    }

}
