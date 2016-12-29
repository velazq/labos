package labos;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.stream.Collectors;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.util.Iterator;



public class Scraper {

    public static final String SEATS_URL = "https://web.fdi.ucm.es/labs/estado_lab.asp";
    public static final String USAGE_URL = "https://web.fdi.ucm.es/Docencia/Horarios.aspx?AulaLab_Cod=%s&fdicurso=%s";
    public static final String TERM = "2016-2017"; // TODO: extraer a config.
    
    public static Lab[] laboratorios = new Lab[11];//cada celda representa uno de los laboratorios y contiene su informacion
    
    
    
    /*
     * Inicializa el array de laboratorios
     */
    public static void iniciar(){
    	for(int i = 0;i < laboratorios.length;i ++){
    		laboratorios[i] = new Lab(i + 1);
    		Scraper.getLabsInfo(laboratorios[i]);
    		Scraper.getHoursInfo(laboratorios[i]);
    	}
    	
    	createJson();

    }
    
    
    public static void getLabsInfo(Lab lab) {
        //Map<Integer, Lab> info = new HashMap<>();
        Document doc = null;

        try {
            doc = Jsoup.connect(SEATS_URL).get();
        } catch (IOException e) {
            //return info; // FIXME
        }

        Elements free = doc.select(".tabla font[color='#006F00']");
        Elements used = doc.select(".tabla font[color='#FF0000']");
        //for (int i = 0; i < free.size(); i++) {
        Element e1 = free.get(lab.id - 1);
        Element e2 = used.get(lab.id - 1);

        // Lab lab = new Lab(i + 1);
        lab.setFreeSeats(parseSeatsList(e1.text()));
        lab.setUsedSeats(parseSeatsList(e2.text()));

            //info.put(i + 1, lab);
        //}

       //return info;
    }

    private static Set<Integer> parseSeatsList(String str) {
        return Arrays.asList(str.split(" "))
                     .stream()
                     .map(s -> s.replaceAll("\\D+", ""))
                     .filter(s -> !s.equals(""))
                     .map(Integer::parseInt)
                     .collect(Collectors.toCollection(TreeSet::new));
    }
    
    /*
     * Genera los datos en el archivo Horarios.json correspondientes a cada lab
     * 
     */
	private static void createJson(){
        	
    		
    		
    		JSONObject obj = new JSONObject();
    		for(int j = 0;j < laboratorios.length; j++){
    			JSONObject cadena = new JSONObject();
    			Map<String,Map<String,String>> result = laboratorios[j].getHoursInfo();
	            for(int i = 1; i <= result.size(); i ++){
	    	        JSONArray horarioDia = new JSONArray();
	    	        Map<String,String> mapa = result.get(Integer.toString(i));//Obtengo el mapa de hora/asignatura para cada dia
	    	        Collection<String> values = mapa.values();//obtengo las asignaturas
	    	        Set<String> keys = mapa.keySet();//obtengo las horas
	    	        Iterator<String> it1 = keys.iterator();
	    	        Iterator<String> it2 = values.iterator();
	    	        while(it1.hasNext()){
	    	            JSONObject objeto = new JSONObject();		
	    	            objeto.put("hora", it1.next());
	    	            objeto.put("asignatura", it2.next());
	    	            horarioDia.add(objeto);
	    	        }
	    
	    			cadena.put(i, horarioDia);
	  
	            }
            
	    		obj.put("Lab" + laboratorios[j].id , cadena);
			}
    		try {

    			FileWriter file = new FileWriter("Horarios.json");//pone al final si ya existe el fichero
    			file.write(obj.toJSONString());
    			file.flush();
    			file.close();

    		} catch (IOException e) {
    			//manejar error
    		}
    		
    		//jsonCreated[lab.id - 1] = true;
    		
    		
//    		JSONParser parser = new JSONParser();
//    		 
//            try {
//     
//                Object objeto = parser.parse(new FileReader(
//                        "C:\\Users\\usuario1\\workspaceNeon\\Labos\\Horarios.json"));
//     
//                JSONObject jsonObject = (JSONObject) objeto;
//                JSONObject lab1 = (JSONObject) jsonObject.get("Lab2");
//                JSONArray lunes = (JSONArray) lab1.get("1");  
//                Iterator<JSONObject> iterator = lunes.iterator();
//                while (iterator.hasNext()) {
//                	JSONObject eoo = iterator.next();
//                    System.out.println(eoo.get("asignatura"));
//                    System.out.println(eoo.get("hora"));
//                }
//     
//     
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        
    		
    		
    		
    	
    }
    
    private static Elements seleccionarPorFecha(Document doc){
        Calendar c = Calendar.getInstance();
        int mes = c.get(Calendar.MONTH);
        if((mes > 7) && (mes < 11))
        	return doc.select("#ctl00_ContentPlaceHolder1_TabContainer1_TabPanel_1_GridViewHorario1 tr");
        if(mes == 11 || mes < 2){
        	return doc.select("#ctl00_ContentPlaceHolder1_TabContainer1_body").size() == 0 ? 
        		   doc.select("#ctl00_ContentPlaceHolder1_TabContainer1_TabPanel_1_GridViewHorario1 tr") : 
        		   doc.select("#ctl00_ContentPlaceHolder1_TabContainer1_TabPanel_12_CwGridViewHorario_C1");
        }
        if(mes > 1 && mes < 7)
        	return doc.select("#ctl00_ContentPlaceHolder1_TabContainer1_TabPanel_12_CwGridViewHorario_C2");
        else
        	return null;
    }

    public static void getHoursInfo(Lab lab) {
        // {"1": {"9:30": "LP1"} => El lunes a las 9:30 hay LP1 en ese lab

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
            //return null; // FIXME
        }

        Elements elems = seleccionarPorFecha(doc); //tbody
        Elements elemsTr = elems.select("tr");
        elemsTr.remove(0); // Quito la fila de la cabecera

        boolean hasHalfHours = elemsTr.size() == hours.size();

        int numDaysTbl = days.size();
        int numHoursTbl = hasHalfHours ? hours.size() : hours.size() / 2;
        
        String[][] tbl = new String[numDaysTbl][numHoursTbl];

        int h = 0;
        for (Element tr : elemsTr) {
            Elements tds = tr.select("td");
            tds.remove(tds.size() - 1); // Quito el ultimo (sabado)
            int d = 0;
            for (Element td : tds) {
                if ("#94AEC6".equals(td.attr("bgcolor"))) { // Hora o minutos
                    continue;
                }
                String txt = td.text().replace(String.valueOf((char)160), " ").trim(); // Elimina &nbsp
                String txt1 = txt.substring(0, txt.length() / 2).trim();
                String txt2 = txt.substring(txt.length() / 2).trim();
                if (txt1.equals(txt2)) {
                    txt = txt1;
                }
                
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
                if (hasHalfHours) {
                    hrs.put(hours.get(j), tbl[i][j]);
                } else {
                    hrs.put(hours.get(j * 2), tbl[i][j]);
                    hrs.put(hours.get(j * 2 + 1), tbl[i][j]);
                }
            }
        }
        
        lab.setHoursInfo(result);
      
    }

}
