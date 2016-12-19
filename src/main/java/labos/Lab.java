package labos;

import java.util.Set;
import java.util.Map;


public class Lab {
    public int id; // 'public' para Mustache
    public Set<Integer> freeSeats, usedSeats;
    public Map<String, Map<Integer, String>> hoursInfo;

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

    public void setHoursInfo(Map<String, Map<Integer, String>> hInfo) {
        hoursInfo = hInfo;
    }

    public Map<String, Map<Integer, String>> getHoursInfo() {
        return hoursInfo;
    }
}
