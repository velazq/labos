package labos;

import java.util.Set;
import java.util.Map;


public class Lab {
    public int id; // 'public' para Mustache
    public Set<Integer> freeSeats, usedSeats;
    public Map<String, Map<String, String>> hoursInfo; //{"1": {"9:30": "LP1"}

    public Lab(int labId) {
        id = labId;
    }

    public void setFreeSeats(Set<Integer> seats) {
        freeSeats = seats;
    }

    public void setUsedSeats(Set<Integer> seats) {
        usedSeats = seats;
    }

    public Set<Integer> getFreeSeats() {
        return freeSeats;
    }

    public Set<Integer> getUsedSeats() {
        return usedSeats;
    }

    public int getLabCode() {
        if (id >= 1 && id <= 10) {
            return id + 206;
        } else if (id == 11) {
            return 229;
        }
        return 0; // FIXME
    }

    
    public void setHoursInfo(Map<String, Map<String, String>> hInfo) {
        hoursInfo = hInfo;
    }
    

    public Map<String, Map<String, String>> getHoursInfo() {
        return hoursInfo;
    }
    
}
