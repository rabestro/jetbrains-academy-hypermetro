package metro.service;

import metro.model.MetroStation;
import metro.model.StationId;

import java.util.function.BiFunction;

public interface TimeFunction extends BiFunction<MetroStation, StationId, Integer> {
}
