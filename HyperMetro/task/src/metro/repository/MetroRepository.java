package metro.repository;

import metro.model.MetroLine;
import metro.model.MetroStation;
import metro.model.StationID;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

public interface MetroRepository {
    Optional<MetroLine> getLine(String name);

    Optional<MetroStation> getStation(StationID stationId);

    Stream<StationID> stream();

    void load(String fileName) throws IOException;

}
