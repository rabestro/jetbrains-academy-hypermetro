package metro.algorithm

import metro.model.StationId
import spock.lang.Specification

class NodeSpec extends Specification {

    def 'should accept different types for id'() {
        given:
        def node = new Node(id)

        when:
        def actualId = node.getId()

        then:
        actualId === id

        where:
        id << ['A' as char, 12, new StationId('Bakerloo', 'Waterloo')]
    }

    def 'should correctly create a node'() {
        given:
        def node = new Node('A')

        expect:
        node.edges == [:]
        node.previous == null
        node.distance == Integer.MAX_VALUE
    }

    def 'should correctly link next and previous nodes'() {
        given:
        def nodeA = new Node('A')
        def nodeB = new Node('B')

        expect:
        nodeA.previous == null
        nodeB.previous == null

        when:
        nodeA.setNextNode(nodeB)

        then:
        nodeA.previous == null
        nodeB.previous === nodeA
    }

    def 'should set and get edges'() {
        given:
        def nodeA = new Node('A')
        def nodeB = new Node('B')
        def nodeC = new Node('C')

        expect:
        nodeA.edges == [:]
        nodeB.edges == [:]
        nodeC.edges == [:]

        when:
        nodeA.addEdge(nodeB, 5)
        nodeA.addEdge(nodeC, 10)
        nodeB.addEdge(nodeA, 7)

        then:
        nodeA.edges == [(nodeB): 5, (nodeC): 10]
        nodeB.edges == [(nodeA): 7]
        nodeC.edges == [:]
    }

    def 'should set and get distance'() {
        given:
        def nodeA = new Node('A')
        def distance = 15

        when:
        nodeA.setDistance(distance)

        then:
        nodeA.getDistance() == distance
    }

}
