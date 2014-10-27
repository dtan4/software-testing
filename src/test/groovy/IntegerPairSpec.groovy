import spock.lang.Specification
import spock.lang.Unroll

class IntegerPairSpec extends Specification {
    @Unroll
    def "When a is #a and b is #b, gcd() returns #result"() {
        setup:
        def integerPair = new IntegerPair(a, b)

        expect:
        integerPair.gcd() == result

        where:
        a  | b   || result
        2  | 3   || 1
        12 | 18  || 6
        12 | 12  || 12
        12 | 0   || 12
        0  | 0   || 0
        12 | -18 || 6
    }
}