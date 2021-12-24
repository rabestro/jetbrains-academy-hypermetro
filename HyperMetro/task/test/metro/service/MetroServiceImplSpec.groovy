package metro.service

import metro.model.MetroLine
import metro.model.MetroStation
import metro.model.StationID
import metro.repository.MetroRepository
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Metro Service")
class MetroServiceImplSpec extends Specification {
    static final LINE_NAME = 'Bakerloo'
    static final STATION_NAME = 'Waterloo'
    static final SOURCE_LINE = 'Edgington'
    static final SOURCE_STATION = 'Kennington'
    static final TARGET_LINE = 'Central'
    static final TARGET_STATION = 'Hanger Lane'
    static final SOURCE = new StationID(SOURCE_LINE, SOURCE_STATION)
    static final TARGET = new StationID(TARGET_LINE, TARGET_STATION)

    def repository = Mock MetroRepository
    def mockLine = Mock MetroLine
    def mockStation = Mock MetroStation

    @Subject
    def service = new MetroServiceImpl(repository)

    def "should return metro line by it's name"() {
        when:
        def metroLine = service.getMetroLine(LINE_NAME)

        then:
        1 * repository.getLine(LINE_NAME) >> Optional.of(mockLine)

        and:
        metroLine === mockLine
    }

    def 'should add a new station at the beginning of metro line'() {
        when:
        service.addHead(LINE_NAME, STATION_NAME)

        then:
        1 * repository.getLine(LINE_NAME) >> Optional.of(mockLine)
        1 * mockLine.addHead(STATION_NAME)
    }

    def 'should append a new station at the end of the line'() {
        when:
        service.append(LINE_NAME, STATION_NAME)

        then:
        1 * repository.getLine(LINE_NAME) >> Optional.of(mockLine)
        1 * mockLine.append(STATION_NAME)
    }

    def 'should connect the source station to the target station'() {
        when:
        service.connect(SOURCE, TARGET)

        then:
        1 * repository.getStation(SOURCE) >> Optional.of(mockStation)
        1 * repository.getStation(TARGET) >> Optional.of(mockStation)
        2 * mockStation.setTransfer(_)
    }

    def 'should remove the metro station from the metro map'() {
        when:
        service.remove(SOURCE)

        then:
        1 * repository.getLine(SOURCE_LINE) >> Optional.of(mockLine)
        1 * repository.getStation(SOURCE) >> Optional.of(mockStation)
        1 * mockLine.remove(mockStation)
    }
}
