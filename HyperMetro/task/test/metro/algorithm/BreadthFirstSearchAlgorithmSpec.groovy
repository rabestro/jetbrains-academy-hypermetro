package metro.algorithm

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class BreadthFirstSearchAlgorithmSpec extends Specification {

    @Shared
    def nodes = createNodes 'A B C D E F'

    void setup() {
        [['A', 'B', 5],
         ['B', 'A', 9], ['B', 'D', 8],
         ['C', 'A', 7],
         ['D', 'C', 2], ['D', 'E', 3],
         ['E', 'F', 7],
         ['F', 'D', 1], ['F', 'E', 4]
        ].each {
            def source = nodes.get(it[0])
            def target = nodes.get(it[1])
            def distance = it[2] as Integer
            source.addEdge(target, distance)
        }
    }

    @Unroll("from #sourceNode to #targetNode the path is #expected")
    def "should find a route"() {
        given:
        def algorithm = new BreadthFirstSearchAlgorithm()

        when:
        def result = algorithm.findRoute(source, target)
        def actual = result.collect { it.id }

        then:
        actual == expected

        where:
        sourceNode | targetNode || expected
        'A'        | 'B'        || ['A', 'B']
        'A'        | 'C'        || ['A', 'B', 'D', 'C']
        'E'        | 'B'        || ['E', 'F', 'D', 'C', 'A', 'B']

        and:
        source = nodes[sourceNode]
        target = nodes[targetNode]

    }

    private static Map<String, Node> createNodes(String vertex) {
        vertex.split(/ /).collectEntries { [it, new Node(it)] }
    }

}
