package metro.service;

import lombok.AllArgsConstructor;
import metro.model.MetroLine;
import metro.model.MetroMap;
import metro.model.MetroStation;
import metro.model.StationID;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class MetroServiceImpl implements MetroService {
    private final MetroMap metroMap;

    @Override
    public Optional<MetroLine> findMetroLine(final String name) {
        return metroMap.getLine(name);
    }

    public MetroLine getMetroLine(final String name) {
        return metroMap.getLine(name).orElseThrow(() -> new NoSuchElementException(
                "There is no metro line with a name '" + name + "'"));
    }

    public MetroStation getMetroStation(final String line, final String name) {
        return getMetroLine(line).getStation(name)
                .orElseThrow(() -> new NoSuchElementException(
                        "There is no station '" + name + "' on the metro line '" + line + "'"));
    }

    public Collection<MetroStation> getLineStations(final String name) {
        return metroMap.getLine(name)
                .orElseThrow(() -> new NoSuchElementException(
                        "There is no metro line with a name '" + name + "'"))
                .getStations();
    }

    public void addHead(final String lineName, final String stationName) {
        getMetroLine(lineName).addHead(stationName);
    }

    public void append(final String lineName, final String stationName) {
        getMetroLine(lineName).append(stationName);
    }

    public void remove(final String lineName, final String stationName) {
        final var station = getMetroStation(lineName, stationName);
        getMetroLine(lineName).remove(station);
    }

    public void connect(final StationID s1, final StationID s2) {
        final var source = getMetroStation(s1.getLine(), s1.getName());
        final var target = getMetroStation(s2.getLine(), s2.getName());
        source.setTransfer(Set.of(s2));
        target.setTransfer(Set.of(s1));
    }
}
