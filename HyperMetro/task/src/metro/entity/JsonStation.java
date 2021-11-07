package metro.entity;

import java.util.List;

public class JsonStation {
    private String name;
    private List<LineStation> transfer;

    public JsonStation() {
    }

    public JsonStation(final String name) {
        this.name = name;
        transfer = List.of();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<LineStation> getTransfer() {
        return transfer;
    }

    public void setTransfer(final List<LineStation> transfer) {
        this.transfer = transfer;
    }
}
