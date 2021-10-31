package metro.command

import spock.lang.Specification

class AppendTest extends Specification {
    def "Test"() {
        given:
        def command = new Append(null)

        expect:
        command.test(null)
    }
}
