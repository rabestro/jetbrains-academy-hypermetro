package metro.algorithm

import spock.lang.Specification
import spock.lang.Subject

class SearchAlgorithmSpec extends Specification {

    @Subject
    def algorithm = new SearchAlgorithm() {
        @Override
        Deque<Node> findRoute(Node source, Node target) {
            return null
        }
    }

    def "should build a correct path"() {
        given:
        def nodeA = new Node('A')
        def nodeB = new Node('B')
        def nodeC = new Node('C')

        and:
        nodeA.setNextNode(nodeB)
        nodeB.setNextNode(nodeC)

        when:
        def path = algorithm.buildPath(nodeC)

        then:
        path == [nodeA, nodeB, nodeC]
    }
}
