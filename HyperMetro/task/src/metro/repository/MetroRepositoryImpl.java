package metro.repository;

import metro.model.MetroLine;
import metro.model.MetroMap;
import metro.model.Station;
import metro.model.StationId;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

public class MetroRepositoryImpl implements MetroRepository {

    private MetroMap metroMap;

    @Override
    public Optional<MetroLine> getLine(final String name) {
        return Optional.ofNullable(metroMap.lines().get(name));
    }

    @Override
    public Optional<Station> getStation(final StationId id) {
        return getLine(id.line()).flatMap(metroLine -> metroLine.getStation(id.name()));
    }

    @Override
    public Stream<StationId> stream() {
        return metroMap.lines().values().stream().flatMap(MetroLine::stream).map(Station::id);
    }

    @Override
    public void load(final String fileName) throws IOException {
        metroMap = new MapLoader().load(fileName);
    }

}
