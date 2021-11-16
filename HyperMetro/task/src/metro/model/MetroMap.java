package metro.model;

import lombok.Value;

import java.util.Map;

@Value
public class MetroMap {
    Map<String, MetroLine> lines;
}