package metro.command

import metro.service.MetroService
import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Command: /append")
@Narrative("Append adds a new station at the end of the line")
@Issue("https://hyperskill.org/projects/120/stages/649/implement")
class AppendSpec extends Specification {
    def service = Mock MetroService

    @Subject
    def command = new Append(service)

    def "should execute append command"() {
        given:
        def params = ['Bakerloo', 'Waterloo']

        when:
        def result = command.apply(params)

        then:
        1 * service.append('Bakerloo', 'Waterloo')

        and:
        result.contains 'successfully'
    }

    def 'should check number of parameters'(){
        given:
        def params = ['Bakerloo']

        when:
        command.apply(params)

        then:
        thrown IllegalArgumentException
    }

}
