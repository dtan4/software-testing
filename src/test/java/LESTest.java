import org.junit.Test;
import static org.junit.Assert.*;

public class LESTest {
    // ********************************
    // solve()
    // ********************************

    // when:   matrix has solution
    // expect: return 1
    @Test
    public void testSolve_hasSolution() {
        long[][][] elem = {
                { { 0 }, { 3 }, { 3 }, { -2 } },
                { { 1 }, { 1 }, { 2 }, { 3 } },
                { { 1 }, { 2 }, { 3 }, { 2 } },
                { { 1 }, { 3 }, { 4 }, { 2 } }
        };
        Matrix a = Matrix.arrayReader(elem);
        Rational[] b = {
                new Rational(-4),
                new Rational(2),
                new Rational(1),
                new Rational(-1)
        };
        LES les = new LES(a, b);

        assertEquals(1, les.solve());
        assertTrue(les.hasValidX());
    }

    // when:   matrix has NO solution
    // expect: return 0
    @Test
    public void testSolve_hasNoSolution() {
        long[][][] elem = {
                { { 0 }, { 3 }, { 3 }, { -2 } },
                { { 1 }, { 1 }, { 2 }, { 3 } },
                { { 1 }, { 2 }, { 3 }, { 2 } },
                { { 1 }, { 3 }, { 4 }, { 2 } }
        };
        Matrix a = Matrix.arrayReader(elem);
        Rational[] b = {
                new Rational(-4),
                new Rational(2),
                new Rational(1),
                new Rational(1)
        };
        LES les = new LES(a, b);

        assertEquals(0, les.solve());
        assertFalse(les.hasValidX());
    }
}
