package metro.service;

import metro.model.MetroLine;
import metro.model.MetroNode;
import metro.model.MetroStation;
import metro.model.StationID;

import java.util.Collection;
import java.util.List;

public interface MetroService {
    MetroLine getMetroLine(String name);

    MetroStation getMetroStation(StationID stationId);

    Collection<MetroStation> getLineStations(String name);

    void addHead(String lineName, String stationName);

    void append(String lineName, String stationName);

    void connect(StationID source, StationID target);

    void remove(StationID target);

    List<StationID> route(StationID source, StationID target);

    List<MetroNode> fastestRoute(StationID source, StationID target);
}
