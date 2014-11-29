import org.junit.Test;
import static org.junit.Assert.*;

public class MatrixTest {
  // ********************************
  // arrayReader()
  // ********************************

  // when:   array.length == 0
  // expect: null
  @Test
  public void testArrayReader_empty() {
    long[][][] array = {};
    assertNull(Matrix.arrayReader(array));
  }

  // when:   array.length[0] == 0
  // expect: null
  @Test
  public void testArrayReader_empty_2() {
    long[][][] array = {{}};
    assertNull(Matrix.arrayReader(array));
  }

  // when:   non-empty array
  // expect: Matrix
  @Test
  public void testArrayReader() {
    long[][][] array = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    Matrix matrix = Matrix.arrayReader(array);

    assertTrue(matrix.getElem()[0][0].equals(new Rational(1, 2)));
  }

  // when:   non-empty array, over columns
  // expect: Matrix
  @Test
  public void testArrayReader_over_columns() {
    long[][][] array = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}, {9, 10}}};
    Matrix matrix = Matrix.arrayReader(array);

    assertEquals(2, matrix.getElem()[1].length);
  }

  // when:   non-empty array, few columns
  // expect: Matrix
  @Test
  public void testArrayReader_few_columns() {
    long[][][] array = {{{1, 2}, {3, 4}, {5, 6}}, {{7, 8}, {9, 10}}};
    Matrix matrix = Matrix.arrayReader(array);

    assertEquals(3, matrix.getElem()[1].length);
  }

  // when:   non-empty array, over rows
  // expect: Matrix
  @Test
  public void testArrayReader_over_rows() {
    long[][][] array = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}, {{9, 10}}};
    Matrix matrix = Matrix.arrayReader(array);

    assertEquals(3, matrix.getElem().length);
  }
}
