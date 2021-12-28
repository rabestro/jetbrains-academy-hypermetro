package metro.algorithm

import spock.lang.Specification
import spock.lang.Subject

class BreadthFirstSearchSpec extends Specification {

    @Subject
    def algorithm = new BreadthFirstSearch<String>()

    def 'should find the shortest path for a simple graph'() {
        given:
        def graph = new Graph([
                A: [B: 7, C: 2],
                B: [A: 3, C: 5],
                C: [A: 1, B: 3]
        ])

        when:
        def path = algorithm.findPath(graph, source, target)

        then:
        path == shortest

        where:
        source | target || shortest
        'A'    | 'A'    || ['A']
        'A'    | 'B'    || ['A', 'B']
        'B'    | 'C'    || ['B', 'C']
        'C'    | 'B'    || ['C', 'B']
    }
}
