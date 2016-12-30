package labos;

import java.util.Map;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
    	Scraper.iniciar();

        String portStr = System.getenv("LABOS_PORT");
        int portNum = portStr != null ? Integer.parseInt(portStr) : 4567;
        port(portNum);
        ipAddress("localhost");

    	staticFiles.location("/public"); 
		
		enableCORS("*", "*", "*");
		   	
        get("/lab/:labId", (request, result) -> showLabInfo(request.params(":labId")), new MustacheTemplateEngine());

		Ctrl ctrl = new Ctrl();
		get("/api/availability", (req, res) -> ctrl.getLabsInfoJSON());
		get("/api/timetable", (req, res) -> ctrl.getTimetableJSON());
    }

	// Enables CORS on requests. This method is an initialization method and should be called once.
	private static void enableCORS(final String origin, final String methods, final String headers) {

		options("/*", (request, response) -> {

			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}

			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}

			return "OK";
		});

		before((request, response) -> {
			response.header("Access-Control-Allow-Origin", origin);
			response.header("Access-Control-Request-Method", methods);
			response.header("Access-Control-Allow-Headers", headers);
			// Note: this may or may not be necessary in your particular application
			// response.type("application/json");
		});
	}

	private static ModelAndView showLabInfo(String labId) {

		Lab lab = getLab(labId);
		Map map = new HashMap();
		map.put("lab", lab);
		String test = lab.getHoursInfo().get(CurrentDay()).get(CurrentDate());
		if (test == null)
			map.put("isTest", false);
		else {
			map.put("test", test);
			map.put("isTest", true);
		}
		map.put("hora", CurrentDate());
		map.put("dia", realDay(CurrentDay()));
		return new ModelAndView(map, "lab.mustache");
	}

	private static String realDay(String currentDay) {
		String day = null;
		switch (currentDay) {
		case "1":
			day = "Lunes";
			break;
		case "2":
			day = "Martes";
			break;
		case "3":
			day = "Miercoles";
			break;
		case "4":
			day = "Jueves";
			break;
		case "5":
			day = "Viernes";
			break;
		case "6":
			day = "S�bado";
			break;
		case "7":
			day = "Domingo";
			break;
		default:
			break;
		}
		return day;
	}

	private static String CurrentDate() {
		// Coge la hora actual
		Date date = new Date();
		DateFormat hourFormat = new SimpleDateFormat("HH:mm");
		return hourFormat.format(date);
	}

	private static String CurrentDay() {
		// Viene Domingo = 1 Lunes = 2... Y para nosotros es Lunes = 1 Martes =
		// 2...
		Date date = new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		if (cal.get(Calendar.DAY_OF_WEEK) == 1)
			return "7";
		else
			return String.valueOf(cal.get(Calendar.DAY_OF_WEEK) - 1);
	}

    public static Lab getLab(String labId) {

        Integer id = 0;

        try {
            id = Integer.parseInt(labId);
        } catch (Exception e) {
            notFound("<html><body><h1>404: el labo '" + id + "' no existe</h1></body></html>");
        }

       // Lab lab = Scraper.getLabsInfo().get(id);
        if(id <= 0 || id > Scraper.laboratorios.length){
        	 notFound("<html><body><h1>404: el labo '" + id + "' no existe</h1></body></html>");
        	 return null;
        }
//        if (lab == null) {
//            notFound("<html><body><h1>404: el labo '" + id + "' no existe</h1></body></html>");
//        }
        Scraper.getLabsInfo(Scraper.laboratorios[id - 1]);
        return Scraper.laboratorios[id - 1];
    }

}
