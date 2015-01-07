import org.junit.Test;
import static org.junit.Assert.*;

public class HLESTest {
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

    // ********************************
    // getZ()
    // ********************************

    // when:
    // expect: calculated z

    @Test
    public void testGetZ() {
        Matrix matrix = createMatrix(3, 5);
        HLES hles = new HLES(matrix);

        Rational[] z = hles.getZ();
        Rational[] expectZ = {zero, zero};

        assertArrayEquals(expectZ, z);
    }

    // ********************************
    // setY()
    // ********************************

    // when:   length of y equals to d.nRow
    // expect: refresh x

    @Test
    public void testSetY_validY() {
        Matrix matrix = createMatrix(3, 3);
        HLES hles = new HLES(matrix);

        Rational[] y = {zero, new Rational(1), new Rational(2)};
        Rational[] expectX = {zero, new Rational(1), new Rational(2)};
        hles.setY(y);

        assertArrayEquals(expectX, hles.x);
    }

    // when:   length of y does not equal to d.nRow
    // expect: do not refresh x

    @Test
    public void testSetY_invalidX() {
        Matrix matrix = createMatrix(3, 3);
        HLES hles = new HLES(matrix);

        Rational[] y = {new Rational(1)};
        Rational[] expectX = hles.x;
        hles.setY(y);

        assertArrayEquals(expectX, hles.x);
    }

    // ********************************
    // pivot()
    // ********************************

    // when:   success
    // expect: 0

    @Test
    public void testPivot_success() {
        long[][][] dElem = {
                { { -1 }, { 0 }, { 0 }, { 1 }, { 1 } },
                { { 0 }, { -1 }, { 0 }, { 2 }, { -1 } },
                { { 0 }, { 0 }, { -1 }, { -1 }, { 2 } }
        };
        Matrix matrix = Matrix.arrayReader(dElem);

        HLES hles = new HLES(matrix);

        assertEquals(0, hles.pivot(0, 3));
        assertTrue(hles.d.isLeftIdentity());
        assertTrue(hles.hasValidX());
    }

    // when:   d is not left identity matrix
    // expect: -1

    @Test
    public void testPivot_notIdentityMatrix() {
        Matrix matrix = createMatrix(3, 5);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                matrix.elem[i][j] = new Rational(i + 1, j + 1);
            }
        }

        HLES hles = new HLES(matrix);

        assertEquals(-1, hles.pivot(0, 1));
    }

    // when:   col1 or col2 is not in range
    // expect: -1

    @Test
    public void testPivot_outOfRange() {
        Matrix matrix = createMatrix();
        HLES hles = new HLES(matrix);

        assertEquals(-1, hles.pivot(0, -1));
    }

    // when:   d.elem[col1][col2] is zero
    // expect: -1

    @Test
    public void testPivot_zeroElem() {
        Matrix matrix = createMatrix();
        HLES hles = new HLES(matrix);

        assertEquals(-1, hles.pivot(0, 1));
    }
}
