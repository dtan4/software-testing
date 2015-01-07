import org.junit.Test;
import static org.junit.Assert.*;

public class HLESTest {
    private int NROW = 2;
    private int NCOL = 3;

    private Matrix createMatrix() {
        Rational[][] elem = new Rational[2][];

        for (int i = 0; i < 2; i++) {
            elem[i] = new Rational[3];

            for (int j = 0; j < 3; j++) {
                elem[i][j] = new Rational(i + 1, j + 1);
            }
        }

        return new Matrix(elem);
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
            expectX[i] = new Rational(0);
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
            expectX[i] = new Rational(0);
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
}
