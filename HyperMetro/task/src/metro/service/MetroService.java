package metro.service;

import metro.algorithm.Graph;
import metro.algorithm.Node;
import metro.model.MetroLine;
import metro.model.MetroStation;
import metro.model.StationId;

import java.util.Deque;
import java.util.List;

public interface MetroService {
    /**
     * Gets MetroLine by name
     *
     * @param name is the name of Metro Line
     * @return object representing the Metro Line
     */
    MetroLine getMetroLine(String name);

    /**
     * Gets Metro Station by Station ID
     *
     * @param stationId is metro station id
     * @return object representing Metro Station
     */
    MetroStation getMetroStation(StationId stationId);

    /**
     * Adds a new station at the beginning of metro line.
     *
     * @param lineName is the name of Metro Line
     * @param stationName is the name of Metro Station
     */
    void addHead(String lineName, String stationName);

    /**
     * Appends a new station at the end of the line.
     *
     * @param lineName is the name of Metro Line
     * @param stationName is the name of Metro Station
     */
    void append(String lineName, String stationName);

    /**
     * Connects the source metro station to the target station
     *
     * @param source metro station id
     * @param target metro station id
     */
    void connect(StationId source, StationId target);

    /**
     * Removes the metro station from metro map
     *
     * @param target metro station id
     */
    void remove(StationId target);

    /**
     * Finds the shortest (the smallest number of stations) way from one station to another.
     * Breadth-First search algorithm is used.
     *
     * @param source metro station id
     * @param target metro station id
     * @return the route from the source station to the target station.
     */
    List<StationId> shortestRoute(StationId source, StationId target);

    /**
     * Creates an abstract Graph representing the metro schema
     *
     * @param transferTime time to transfer between metro lines
     * @return the graph with nodes as metro station
     */
    Graph<StationId> getMetroGraph(int transferTime);

    /**
     * Finds the fastest way by using Dijkstra's algorithm.
     * The travel time between station is taken into account.
     *
     * Transactions between lines is not considered as moving around the nodes of the graph.
     *
     * @param source metro station id
     * @param target metro station id
     * @return the route from the source station to the target station.
     */
    Deque<Node<StationId>> route(StationId source, StationId target);

    /**
     * Finds the fastest way by using Dijkstra's algorithm.
     * The travel time between station is taken into account.
     *
     * Transferring from one line to another takes 5 minutes.
     *
     * @param source metro station id
     * @param target metro station id
     * @return the route from the source station to the target station.
     */
    Deque<Node<StationId>> fastestRoute(StationId source, StationId target);
}
