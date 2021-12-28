package metro.algorithm

import spock.lang.Specification

class GraphSpec extends Specification {
    def "should return edges for a given node"() {
        given:
        def graph = new Graph([
                A: [B: 7, C: 2],
                B: [A: 3, C: 5],
                C: [A: 1, B: 3]
        ])

        expect:
        graph.edges(node) == edges

        where:
        node | edges
        'A'  | [B: 7, C: 2]
        'B'  | [A: 3, C: 5]
        'C'  | [A: 1, B: 3]
    }
}
