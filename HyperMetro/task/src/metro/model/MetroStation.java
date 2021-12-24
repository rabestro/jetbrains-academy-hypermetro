package metro.model;

import java.util.Set;

public class MetroStation {
    private static final int CONSTANT_TIME = 1;

    private final StationID stationID;
    private final int time;
    private Set<StationID> transfer = Set.of();
    private Set<StationID> next = Set.of();
    private Set<StationID> prev = Set.of();

    public MetroStation(final StationID sid) {
        stationID = sid;
        time = CONSTANT_TIME;
    }

    public MetroStation(final StationID sid, final int time) {
        this.stationID = sid;
        this.time = time;
    }

    public Set<StationID> getTransfer() {
        return transfer;
    }

    public void setTransfer(Set<StationID> transfer) {
        this.transfer = transfer;
    }

    public Set<StationID> getNext() {
        return next;
    }

    public void setNext(Set<StationID> next) {
        this.next = next;
    }

    public Set<StationID> getPrev() {
        return prev;
    }

    public void setPrev(Set<StationID> prev) {
        this.prev = prev;
    }

    public StationID getStationID() {
        return stationID;
    }

    public int getTime() {
        return time;
    }
}
