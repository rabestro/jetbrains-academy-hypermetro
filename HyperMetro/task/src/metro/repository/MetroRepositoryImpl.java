package metro.repository;

import metro.model.MetroLine;
import metro.model.MetroMap;
import metro.model.MetroStation;
import metro.model.StationID;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

public class MetroRepositoryImpl implements MetroRepository {

    private MetroMap metroMap;

    @Override
    public Optional<MetroLine> getLine(final String name) {
        return Optional.ofNullable(metroMap.getLines().get(name));
    }

    @Override
    public Optional<MetroStation> getStation(final StationID stationId) {
        return getLine(stationId.getLine()).flatMap(metroLine -> metroLine.getStation(stationId.getName()));
    }

    @Override
    public Stream<StationID> stream() {
        return metroMap.getLines().values().stream().flatMap(MetroLine::stream).map(MetroStation::getStationID);
    }

    @Override
    public void load(final String fileName) throws IOException {
        metroMap = new MapLoader().load(fileName);
    }

}
