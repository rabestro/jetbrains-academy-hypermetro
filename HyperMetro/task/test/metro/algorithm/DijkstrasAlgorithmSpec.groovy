package metro.algorithm

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class DijkstrasAlgorithmSpec extends Specification {

    static final SAMPLE_ONE = [A: [B: 7, C: 2], B: [A: 3, C: 5], C: [A: 1, B: 3]]

    static final SAMPLE_TWO = [
            A: [B: 5, H: 2],
            B: [A: 5, C: 7],
            C: [B: 7, D: 3, G: 4],
            D: [C: 20, E: 4],
            E: [F: 5],
            F: [G: 6],
            G: [C: 4],
            H: [G: 3]
    ]

    @Subject
    def algorithm = new DijkstrasAlgorithm()

    @Unroll("from #sid to #tid the time is #time and the path is #expected")
    def 'should find a route for sample one'() {
        given:
        def graph = createGraph SAMPLE_ONE

        and:
        def source = graph[sid]
        def target = graph[tid]

        when:
        def route = algorithm.findRoute(source, target)

        then:
        with(route) {
            it.id == expected
            it.last.distance == time
        }

        where:
        sid | tid || time | expected
        'A' | 'A' || 0    | ['A']
        'B' | 'B' || 0    | ['B']
        'A' | 'B' || 5    | ['A', 'C', 'B']
        'B' | 'A' || 3    | ['B', 'A']
        'B' | 'C' || 5    | ['B', 'C']
        'C' | 'A' || 1    | ['C', 'A']

    }

    @Unroll("from #sourceId to #targetId the time is #time and the path is #expected")
    def "should find a route"() {
        when:
        def result = algorithm.findRoute(source, target)
        def route = result.id

        then:
        result.last.distance == time

        and:
        route == expected

        where:
        sourceId | targetId || time | expected
        'A'      | 'A'      || 0    | ['A']
        'B'      | 'B'      || 0    | ['B']
        'A'      | 'B'      || 5    | ['A', 'B']
        'B'      | 'A'      || 5    | ['B', 'A']
        'A'      | 'C'      || 9    | ['A', 'H', 'G', 'C']
        'C'      | 'A'      || 12   | ['C', 'B', 'A']
        'A'      | 'G'      || 5    | ['A', 'H', 'G']
        'C'      | 'D'      || 3    | ['C', 'D']
        'D'      | 'C'      || 19   | ['D', 'E', 'F', 'G', 'C']
        'B'      | 'D'      || 10   | ['B', 'C', 'D']
        'D'      | 'B'      || 26   | ['D', 'E', 'F', 'G', 'C', 'B']
        'D'      | 'H'      || 33   | ['D', 'E', 'F', 'G', 'C', 'B', 'A', 'H']

        and:
        graph = createGraph SAMPLE_TWO

        and:
        source = graph[sourceId]
        target = graph[targetId]
    }

    private createGraph(Map<String, Map<String, Integer>> graphConfig) {
        def vertex = graphConfig.collectEntries { [it.key, new Node(it.key)] } as Map<String, Node>
        graphConfig.each {
            def node = vertex[it.key]
            it.value.each {
                node.addEdge(vertex[it.key] as Node, it.value)
            }
        }
        return vertex
    }

}
