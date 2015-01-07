import org.junit.Test;
import static org.junit.Assert.*;

public class HLESTest {
    private static final int NROW = 2;
    private static final int NCOL = 3;
    private static final Rational zero = new Rational(0);

    private Matrix createMatrix(int nRow, int nCol) {
        Rational[][] elem = new Rational[nRow][];

        for (int i = 0; i < nRow; i++) {
            elem[i] = new Rational[nCol];

            for (int j = 0; j < nCol; j++) {
                elem[i][j] = new Rational(i + 1, j + 1);
            }
        }

        return new Matrix(elem);
    }

    private Matrix createMatrix() {
        return createMatrix(NROW, NCOL);
    }

    // ********************************
    // HLES()
    // ********************************

    // when:   matrix
    // expect: HLES

    @Test
    public void testHLES() {
        Matrix matrix = createMatrix();
        HLES hles = new HLES(matrix);

        assertEquals(matrix, hles.d);
        assertEquals(NCOL, hles.x.length);

        Rational[] expectX = new Rational[NCOL];

        for (int i = 0; i < NCOL; i++) {
            expectX[i] = zero;
        }

        assertArrayEquals(expectX, hles.x);
    }

    // ********************************
    // getX()
    // ********************************

    // when:
    // expect: x

    @Test
    public void testGetX() {
        Matrix matrix = createMatrix();
        HLES hles = new HLES(matrix);

        Rational[] x = hles.getX();
        assertEquals(NCOL, x.length);

        Rational[] expectX = new Rational[NCOL];

        for (int i = 0; i < NCOL; i++) {
            expectX[i] = zero;
        }

        assertArrayEquals(expectX, x);
    }

    // ********************************
    // setX()
    // ********************************

    // when:
    // expect: update element

    @Test
    public void testSetX() {
        Matrix matrix = createMatrix();
        HLES hles = new HLES(matrix);

        int j = 1;
        Rational r = new Rational(2, 3);
        hles.setX(j, r);

        assertEquals(hles.x[hles.d.p[j]], r);
    }

    // ********************************
    // hasValidX()
    // ********************************

    // when:   matrix has valid x
    // expect: true

    @Test
    public void testHasValidX_valid() {
        Matrix matrix = createMatrix();
        HLES hles = new HLES(matrix);

        assertTrue(hles.hasValidX());
    }

    // when:   matrix has invalid x
    // expect: false

    @Test
    public void testHasValidX_invalid() {
        Matrix matrix = createMatrix();
        HLES hles = new HLES(matrix);
        hles.setX(1, new Rational(1));

        assertFalse(hles.hasValidX());
    }
}
