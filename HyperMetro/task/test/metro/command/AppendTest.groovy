package metro.command

import org.junit.Test
import spock.lang.Specification

class AppendTest extends Specification {
    @Test
    def "Test"() {
        given:
        def command = new Append(null)

        expect:
        command.test(null)
    }
}
