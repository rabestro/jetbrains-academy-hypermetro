package metro.algorithm

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title("Graph")
@Narrative("A simple implementation of Graph")
class GraphSpec extends Specification {

    def "should return edges for a given node"() {
        given: 'a simple graph with three nodes'
        def graph = new Graph([
                A: [B: 7, C: 2],
                B: [A: 3, C: 5],
                C: [A: 1, B: 3]
        ])

        expect: 'The method returns expected edges for given node'
        graph.edges(node) == expected

        where:
        node | expected
        'A'  | [B: 7, C: 2]
        'B'  | [A: 3, C: 5]
        'C'  | [A: 1, B: 3]
    }
}
