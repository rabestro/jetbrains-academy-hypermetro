package metro.controller

import metro.command.Command
import spock.lang.Specification
import spock.lang.Subject

class BrokerSpec extends Specification {
    def command = Mock Command
    def actions = [command: (command)]

    @Subject
    def broker = new Broker(actions)

    def 'should execute correct command'() {
        given:
        def request = 'command Bakerloo Waterloo'

        when:
        broker.apply(request)

        then:
        1 * command.apply(['Bakerloo', 'Waterloo'])
    }

    def 'should reject invalid command'() {
        given:
        def request = 'unknown Bakerloo Waterloo'

        when:
        def result = broker.apply(request)

        then:
        0 * command.apply(_)

        and:
        result =~ /(?i)invalid command/
    }

    def 'should print error messages'() {
        given:
        def request = 'command Bakerloo Waterloo'

        when:
        def result = broker.apply(request)

        then:
        1 * command.apply(_) >> { throw exception }

        and:
        result =~ message

        where:
        message                        | exception
        'Invalid number of parameters' | new IllegalArgumentException(message)
        'No such metro line'           | new NoSuchElementException(message)
        'No such metro station'        | new NoSuchElementException(message)
    }
}
