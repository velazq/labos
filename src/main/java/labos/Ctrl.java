package labos;

import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Ctrl {

    private DAO dao;

    public Ctrl() {
        dao = new DAO();
    }

    public String getLabsInfoJSON() {
        JSONObject obj = new JSONObject();
        Map<Integer, Lab> info = Scraper.getLabsInfo();
        for (Map.Entry<Integer, Lab> entry : Scraper.getLabsInfo().entrySet()) {
            String labName = "lab" + entry.getKey();
            Lab lab = entry.getValue();
            JSONObject labJSON = new JSONObject();
            obj.put(labName, labJSON);
            JSONArray freeList = new JSONArray();
            labJSON.put("free", freeList);
            for (Integer i : lab.getFreeSeats()) {
                freeList.add(i.toString());
            }
            JSONArray usedList = new JSONArray();
            labJSON.put("used", usedList);
            for (Integer i : lab.getUsedSeats()) {
                usedList.add(i.toString());
            }
        }
        return obj.toJSONString();
    }

    public String getTimetableJSON() {
        return dao.getTimetableJSON();
    }

}