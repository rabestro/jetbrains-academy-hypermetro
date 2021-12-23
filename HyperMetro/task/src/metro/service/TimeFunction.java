package metro.service;

import metro.model.MetroStation;
import metro.model.StationID;

import java.util.function.BiFunction;

public interface TimeFunction extends BiFunction<MetroStation, StationID, Integer> {
}
