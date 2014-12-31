import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

public class LISTest {
    // ********************************
    // hasValidSolution()
    // ********************************

    // when:   correspond to c
    // expect: true
    @Ignore
    @Test
    public void testHasValidSolution_correspond() {
        long[][][] array = {
                {{1, 2}, {3, 4}},
                {{5, 6}, {7, 8}}
        };
        Matrix a = Matrix.arrayReader(array);
        Rational[] b = {
                new Rational(1),
                new Rational(2)
        };
        int[] c = {0, 1, 2};

        LIS lis = new LIS(a, b, c);
        assertTrue(lis.hasValidSolution());
    }

    // when:   not correspond to c
    // expect: false
    @Ignore
    @Test
    public void testHasValidSolution_notCorrespond() {

    }
}
