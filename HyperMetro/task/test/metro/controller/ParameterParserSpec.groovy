package metro.controller

import spock.lang.Specification

class ParameterParserSpec extends Specification {

    def "should parse parameters"() {
        given:
        def parser = new ParameterParser()

        when:
        def result = parser.parse(line)

        then:
        result == expected

        where:
        line                                   | expected
        ''                                     | []
        'Waterloo'                             | ['Waterloo']
        '"Waterloo"'                           | ['Waterloo']
        '     "Waterloo"'                      | ['Waterloo']
        '     Waterloo'                        | ['Waterloo']
        '     Waterloo    '                    | ['Waterloo']
        'Bakerloo Waterloo'                    | ['Bakerloo', 'Waterloo']
        '"Bakerloo" "Waterloo"'                | ['Bakerloo', 'Waterloo']
        'Bakerloo "Waterloo"'                  | ['Bakerloo', 'Waterloo']
        '"Bakerloo" Waterloo'                  | ['Bakerloo', 'Waterloo']
        'Bakerloo     Waterloo'                | ['Bakerloo', 'Waterloo']
        '"Hammersmith & City"'                 | ['Hammersmith & City']
        '"Hammersmith & City" "Goldhawk Road"' | ['Hammersmith & City', 'Goldhawk Road']
        'Circle Temple Central Leyton'         | ['Circle', 'Temple', 'Central', 'Leyton']
        'Circle Temple Central "Gants Hill"'   | ['Circle', 'Temple', 'Central', 'Gants Hill']
    }
}
