package metro.service;

import metro.model.MetroLine;
import metro.model.MetroStation;

import java.util.Collection;
import java.util.Optional;

public interface MetroService {
    Optional<MetroLine> findMetroLine(String name);

    MetroLine getMetroLine(String name);

    Collection<MetroStation> getLineStations(String name);

    void addHead(String lineName, String stationName);

    void append(String lineName, String stationName);
}
