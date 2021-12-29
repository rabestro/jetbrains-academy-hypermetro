package metro.model

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Model of Metro Line")
class MetroLineSpec extends Specification {

    @Subject
    def metroLine = new MetroLine('Bakerloo')

    void setup() {
    }

    void cleanup() {
    }

    def "GetStation"() {
    }

    def "Remove"() {
    }

    def "AddHead"() {
    }

    def "Add"() {
    }

    def "Append"() {
    }

    def "should get the name of the metro line"() {
        given: 'the metro line created'
        def line = new MetroLine(name)

        expect: 'we get the correct metro line name'
        line.getLineName() == name

        where:
        name << ['Bakerloo', 'Circle', 'Central', 'Hammersmith & City']
    }

    def "GetStations"() {
    }
}
