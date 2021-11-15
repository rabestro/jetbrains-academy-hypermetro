package metro.repository;

import metro.model.MetroLine;
import metro.model.MetroStation;
import metro.model.StationID;

import java.io.IOException;
import java.util.Optional;

public interface MetroRepository {
    void load(String fileName) throws IOException;

    Optional<MetroLine> getLine(String name);

    Optional<MetroStation> getStation(StationID stationId);

}
