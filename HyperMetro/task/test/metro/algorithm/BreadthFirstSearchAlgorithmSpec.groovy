package metro.algorithm

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class BreadthFirstSearchAlgorithmSpec extends Specification {

    static final GRAPH = [
            A: ['B'],
            B: ['A', 'D'],
            C: ['A'],
            D: ['C', 'E'],
            E: ['F'],
            F: ['D', 'E']]

    @Shared
    Map<String, Node> nodes = GRAPH.collectEntries { [it.key, new Node(it.key)] }

    void setup() {
        GRAPH.each { source, edges ->
            edges.each { nodes[source].addEdge(nodes[it]) }
        }
    }

    @Unroll("from #sourceId to #targetId the path is #expected")
    def "should find a route"() {
        given:
        def algorithm = new BreadthFirstSearchAlgorithm()

        when:
        def route = algorithm.findRoute(source, target)

        then:
        route.id == expected

        where:
        sourceId | targetId || expected
        'A'      | 'A'      || ['A']
        'B'      | 'B'      || ['B']
        'A'      | 'B'      || ['A', 'B']
        'B'      | 'A'      || ['B', 'A']
        'A'      | 'C'      || ['A', 'B', 'D', 'C']
        'C'      | 'A'      || ['C', 'A']
        'E'      | 'B'      || ['E', 'F', 'D', 'C', 'A', 'B']

        and:
        source = nodes[sourceId]
        target = nodes[targetId]
    }

}
