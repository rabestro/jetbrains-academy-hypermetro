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
        GRAPH.each {
            def source = nodes[it.key]
            it.value.each { source.addEdge(nodes[it]) }
        }
    }

    @Unroll("from #sourceNode to #targetNode the path is #expected")
    def "should find a route"() {
        given:
        def algorithm = new BreadthFirstSearchAlgorithm()

        when:
        def actual = algorithm.findRoute(source, target)

        then:
        actual.id == expected

        where:
        sourceNode | targetNode || expected
        'A'        | 'B'        || ['A', 'B']
        'B'        | 'A'        || ['B', 'A']
        'A'        | 'C'        || ['A', 'B', 'D', 'C']
        'C'        | 'A'        || ['C', 'A']
        'E'        | 'B'        || ['E', 'F', 'D', 'C', 'A', 'B']

        and:
        source = nodes[sourceNode]
        target = nodes[targetNode]

    }

}
