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
        assertEquals(new Rational(0), hles.x[0]);
    }
}
