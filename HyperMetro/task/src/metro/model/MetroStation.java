package metro.model;

import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode
public class MetroStation {
    private static final int CONSTANT_TIME = 1;

    private final StationId id;
    private final int time;
    private Set<StationId> transfer = Set.of();
    private Set<StationId> next = Set.of();
    private Set<StationId> prev = Set.of();

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

    public void setTransfer(Set<StationId> transfer) {
        this.transfer = transfer;
    }

    public Set<StationId> getNext() {
        return next;
    }

    public void setNext(Set<StationId> next) {
        this.next = next;
    }

    public Set<StationId> getPrev() {
        return prev;
    }

    public void setPrev(Set<StationId> prev) {
        this.prev = prev;
    }

    public StationId getId() {
        return id;
    }

    public int getTime() {
        return time;
    }
}
