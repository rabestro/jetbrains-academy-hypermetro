package metro.repository

import metro.model.MetroLine
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Repository of Metro Map")
class MetroRepositoryImpSpec extends Specification {
    @Shared
    def lineOne = Mock MetroLine
    @Shared
    def lineTwo = Mock MetroLine

    @Subject
    def repository = new MetroRepositoryImpl()

    def "should find metro line by name"() {
        given: 'metro map with two lines'
        repository.metroMap = [one: (lineOne), two: (lineTwo)]

        when: 'we request the repository for existing line'
        def metroLine = repository.getLine(lineName)

        then: 'repository returns metro line'
        metroLine.isPresent()

        and: 'the metro line is expected'
        metroLine.get() === expected

        where:
        lineName | expected
        'one'    | lineOne
        'two'    | lineTwo
    }

}
