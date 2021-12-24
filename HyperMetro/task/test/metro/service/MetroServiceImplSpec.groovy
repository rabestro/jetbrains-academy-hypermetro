package metro.service

import metro.model.MetroLine
import metro.repository.MetroRepository
import spock.lang.Specification
import spock.lang.Subject

class MetroServiceImplSpec extends Specification {
    def repository = Mock MetroRepository
    def lineMock = Mock MetroLine

    @Subject
    def service = new MetroServiceImpl(repository)

    def 'should return metro line'() {
        given:
        def lineName = 'First line'

        when:
        def metroLine = service.getMetroLine(lineName)

        then:
        1 * repository.getLine(lineName) >> Optional.of(lineMock)
        and:
        metroLine === lineMock
    }

}
