package metro.model;

import java.util.Set;

public class MetroStation {
    private final StationID stationID;
    private Set<StationID> transfer = Set.of();
    private Set<StationID> next = Set.of();
    private Set<StationID> prev = Set.of();
    private int time;

    public MetroStation(final StationID stationID) {
        this.stationID = stationID;
    }

    public StationID getStationID() {
        return stationID;
    }

    public Set<StationID> getTransfer() {
        return transfer;
    }

    public void setTransfer(final Set<StationID> transfer) {
        this.transfer = transfer;
    }

    public Set<StationID> getNext() {
        return next;
    }

    public void setNext(final Set<StationID> next) {
        this.next = next;
    }

    public Set<StationID> getPrev() {
        return prev;
    }

    public void setPrev(final Set<StationID> prev) {
        this.prev = prev;
    }

    public int getTime() {
        return time;
    }

    public void setTime(final int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MetroStation{" +
                "stationID=" + stationID +
                ", transfer=" + transfer.toString() +
                ", next=" + next.toString() +
                ", prev=" + prev.toString() +
                ", time=" + time +
                '}';
    }
}
