import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

public class LISTest {
    private static final int NROW = 2;
    private static final int NCOL = 3;
    private static final Rational zero = new Rational(0);
    private static final Rational minusOne = new Rational(-1);

    private Matrix createMatrix(int nRow, int nCol) {
        Rational[][] elem = new Rational[nRow][];

        for (int i = 0; i < nRow; i++) {
            elem[i] = new Rational[nCol];

            for (int j = 0; j < nCol; j++) {
                if (nRow < j) {
                    elem[i][j] = new Rational(i + 1, j + 1);
                } else {
                    elem[i][j] = (i == j) ? minusOne : zero;
                }
            }
        }

        return new Matrix(elem);
    }

    private Matrix createMatrix() {
        return createMatrix(NROW, NCOL);
    }


    // ********************************
    // LIS(a, b, c)
    // ********************************

    // when:   valid arguments
    // expect: create new LIS
    @Test
    public void testLIS_valid() {
        Matrix a = createMatrix(3, 3);
        Rational[] b = {
                new Rational(1),
                new Rational(2),
                new Rational(3)
        };
        int[] c = {0, 1, 2};

        LIS lis = new LIS(a, b, c);

        assertEquals(a, lis.a);
        assertArrayEquals(b, lis.b);
        assertArrayEquals(c, lis.c);
    }


    // when:   length of b is less than aRow
    // expect: fill b with 0
    @Test
    public void testLIS_smallB() {
        Matrix a = createMatrix(3, 3);
        Rational[] b = {
                new Rational(1),
        };
        int[] c = {0, 1, 2};

        LIS lis = new LIS(a, b, c);
        Rational[] expectB = {new Rational(1), zero, zero};

        assertArrayEquals(expectB, lis.b);
    }

    // when:   length of c is less than aRow
    // expect: fill c with 0
    @Test
    public void testLIS_smallC() {
        Matrix a = createMatrix(3, 3);
        Rational[] b = {
                new Rational(1),
                new Rational(2),
                new Rational(3)
        };
        int[] c = {0, 1};

        LIS lis = new LIS(a, b, c);
        int[] expectC = {0, 1, 0};

        assertArrayEquals(expectC, lis.c);
    }

    // when:   elements of c are invalid
    // expect: replace with 0
    @Test
    public void testLIS_invalidC() {
        Matrix a = createMatrix(3, 3);
        Rational[] b = {
                new Rational(1),
                new Rational(2),
                new Rational(3)
        };
        int[] c = {0, 1, 3};

        LIS lis = new LIS(a, b, c);
        int[] expectC = {0, 1, 0};

        assertArrayEquals(expectC, lis.c);
    }

    // ********************************
    // getX()
    // ********************************

    // when:
    // expect: return x
    @Test
    public void testGetX() {
        Matrix a = createMatrix(3, 3);
        Rational[] b = {
                new Rational(1),
                new Rational(2),
                new Rational(3)
        };
        int[] c = {0, 1, 2};

        LIS lis = new LIS(a, b, c);
        Rational[] x = {zero, zero, zero};
        lis.x = x;

        assertArrayEquals(x, lis.getX());
    }

    // ********************************
    // hasValidX()
    // ********************************

    // when:   x is valid
    // expect: true
    @Test
    public void testHasValidX_validX() {
        Matrix a = createMatrix(3, 3);
        Rational[] b = {
                minusOne,
                new Rational(-5),
                new Rational(2)
        };
        int[] c = {0, 1, 2};

        LIS lis = new LIS(a, b, c);
        Rational[] x = {new Rational(1), new Rational(2), new Rational(3)};
        lis.x = x;

        assertTrue(lis.hasValidX());
    }

    // when:   x is invalid
    // expect: false
    @Test
    public void testHasValidX_invalidX() {
        Matrix a = createMatrix(3, 3);
        Rational[] b = {
                new Rational(1),
                new Rational(2),
                new Rational(3)
        };
        int[] c = {0, 1, 2};

        LIS lis = new LIS(a, b, c);
        Rational[] x = {zero, zero, zero};
        lis.x = x;

        assertFalse(lis.hasValidX());
    }
}
