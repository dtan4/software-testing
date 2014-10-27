import spock.lang.Specification
import spock.lang.Unroll

class RationalSpec extends Specification {
    @Unroll
    def "When num is #num and den is #den, Rational(num, den) returns #result"() {
        setup:
        def rational = new Rational(num, den)

        expect:
        rational.getNum() == resultNum
        rational.getDen() == resultDen

        where:
        num | den || resultNum | resultDen
        2   | 3   || 2 | 3
        12  | 18  || 2 | 3
        12  | 12  || 1 | 1
        12  | 0   || 12 | 1
        -12 | 18  || -2 | 3
        12  | -18 || -2 | 3

    }
}