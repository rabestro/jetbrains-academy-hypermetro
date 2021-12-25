package metro.command

import metro.model.StationID
import metro.service.MetroService
import spock.lang.Specification
import spock.lang.Subject

class ConnectSpec extends Specification {
    static final SOURCE_LINE = 'Edgington'
    static final SOURCE_STATION = 'Kennington'
    static final TARGET_LINE = 'Central'
    static final TARGET_STATION = 'Hanger Lane'
    static final SOURCE = new StationID(SOURCE_LINE, SOURCE_STATION)
    static final TARGET = new StationID(TARGET_LINE, TARGET_STATION)

    def service = Mock MetroService

    @Subject
    def command = new Connect(service)

    void setup() {
    }

    def "should execute connect command"() {
        given:
        def parameters = [SOURCE_LINE, SOURCE_STATION, TARGET_LINE, TARGET_STATION]

        when:
        def result = command.apply(parameters)

        then:
        1 * service.connect(SOURCE, TARGET)

        and:
        result.contains 'successfully'
    }

    def 'should throw an exception for incorrect number of parameters'() {
        when:
        command.apply(incorrectParameters)

        then:
        thrown IllegalArgumentException

        where:
        incorrectParameters << [[], ['Bakerloo'], ['A', 'B', 'C'], ['A', 'B', 'C', 'D', 'E']]
    }

}
