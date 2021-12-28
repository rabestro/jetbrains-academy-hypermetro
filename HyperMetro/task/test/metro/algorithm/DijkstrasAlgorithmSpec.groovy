package metro.algorithm

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Dijkstra's Algorithm")
class DijkstrasAlgorithmSpec extends Specification {
    @Subject
    def algorithm = new DijkstrasAlgorithm<String>()

    def 'should find a route for a simple graph'() {
        given: 'a simple graph with three nodes'
        def graph = new Graph([
                A: [B: 7, C: 2],
                B: [A: 3, C: 5],
                C: [A: 1, B: 3]
        ])

        when: "we use Dijkstra's algorithm to find the path"
        def path = algorithm.findPath(graph, source, target)

        then: 'we get the fastest path'
        path == fastest

        and: 'the distance calculated correctly'
        graph.getDistance(path) == time as double

        where: 'source and target nodes are defined'
        source | target || time | fastest
        'A'    | 'A'    || 0    | ['A']
        'B'    | 'B'    || 0    | ['B']
        'C'    | 'C'    || 0    | ['C']
        'A'    | 'C'    || 2    | ['A', 'C']
        'A'    | 'B'    || 5    | ['A', 'C', 'B']
    }
}
