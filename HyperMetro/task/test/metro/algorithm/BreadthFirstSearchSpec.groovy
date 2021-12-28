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

    def 'should find the shortest path for a complex graph'() {
        given:
        def graph = new Graph([
                A: [B: 5, H: 2],
                B: [A: 5, C: 7],
                C: [B: 7, D: 3, G: 4],
                D: [C: 20, E: 4],
                E: [F: 5],
                F: [G: 6],
                G: [C: 4],
                H: [G: 3]
        ])

        when:
        def path = algorithm.findPath(graph, source, target)

        then:
        path == shortest

        where:
        source | target || shortest
        'A'    | 'A'    || ['A']
        'B'    | 'B'    || ['B']
        'A'    | 'B'    || ['A', 'B']
        'B'    | 'A'    || ['B', 'A']
        'A'    | 'C'    || ['A', 'B', 'C']
        'C'    | 'A'    || ['C', 'B', 'A']
        'A'    | 'G'    || ['A', 'H', 'G']
        'C'    | 'D'    || ['C', 'D']
        'D'    | 'C'    || ['D', 'C']
        'B'    | 'D'    || ['B', 'C', 'D']
        'D'    | 'B'    || ['D', 'C', 'B']
        'D'    | 'H'    || ['D', 'C', 'B', 'A', 'H']
    }

}
