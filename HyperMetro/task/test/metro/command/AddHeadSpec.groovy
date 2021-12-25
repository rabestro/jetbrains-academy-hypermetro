package metro.command

import metro.service.MetroService
import spock.lang.Specification
import spock.lang.Subject

class AddHeadSpec extends Specification {
    def service = Mock MetroService

    @Subject
    def command = new AddHead(service)

    def "should execute add-head command"() {
        given:
        def params = ['Bakerloo', 'Waterloo']

        when:
        def result = command.apply(params)

        then:
        1 * service.addHead('Bakerloo', 'Waterloo')

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
