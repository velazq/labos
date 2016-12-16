package labos;

import java.util.Set;

public class Lab {
    public int id; // 'public' para Mustache
    public Set<Integer> freeSeats, usedSeats;

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
}
