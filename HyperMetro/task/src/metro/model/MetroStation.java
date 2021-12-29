package metro.model;

import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
public class MetroStation {
    private static final int CONSTANT_TIME = 1;

    private final StationId id;
    private final int time;
    private final Set<StationId> next = new HashSet<>();
    private final Set<StationId> prev = new HashSet<>();
    private final Set<StationId> transfer = new HashSet<>();

    public MetroStation(final StationId sid) {
        id = sid;
        time = CONSTANT_TIME;
    }

    public MetroStation(final StationId sid, final int time) {
        this.id = sid;
        this.time = time;
    }

    public Set<StationId> getTransfer() {
        return transfer;
    }

    public void setTransfer(final Set<StationId> transfer) {
        this.transfer.clear();
        this.transfer.addAll(transfer);
    }

    public Set<StationId> getNext() {
        return next;
    }

    public void setNext(final Set<StationId> next) {
        this.next.clear();
        this.next.addAll(next);
    }

    public Set<StationId> getPrev() {
        return prev;
    }

    public void addPrev(final StationId station) {
        this.prev.add(station);
    }

    public void setPrev(final Set<StationId> prev) {
        this.prev.clear();
        this.prev.addAll(prev);
    }

    public StationId getId() {
        return id;
    }

    public int getTime() {
        return time;
    }
}
