package metro.service;

import lombok.AllArgsConstructor;
import metro.model.MetroLine;
import metro.model.MetroMap;
import metro.model.MetroStation;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Collection<MetroStation> getLineStations(final String name) {
        return metroMap.getLine(name)
                .orElseThrow(() -> new NoSuchElementException(
                        "There is no metro line with a name '" + name + "'"))
                .getStations()
                .values();
    }
}
