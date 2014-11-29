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


  // ********************************
  // equals()
  // ********************************

  // when:   same matrix is given
  // expect: true
  @Test
  public void testEquals_same() {
    long[][][] array1 = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    long[][][] array2 = array1.clone();
    Matrix matrix1 = Matrix.arrayReader(array1);
    Matrix matrix2 = Matrix.arrayReader(array2);

    assertEquals(matrix1, matrix2);
  }

  // when:   different matrix is given
  // expect: false
  @Test
  public void testEquals_different() {
    long[][][] array1 = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    long[][][] array2 = {{{1, 2}, {3, 4}}, {{5, 6}, {9, 10}}};
    Matrix matrix1 = Matrix.arrayReader(array1);
    Matrix matrix2 = Matrix.arrayReader(array2);

    assertNotEquals(matrix1, matrix2);
  }

  // when:   different size matrix is given
  // expect: false
  @Test
  public void testEquals_differentSize() {
    long[][][] array1 = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    long[][][] array2 = {{{1, 2}}, {{3, 4}, {5, 6}}};
    Matrix matrix1 = Matrix.arrayReader(array1);
    Matrix matrix2 = Matrix.arrayReader(array2);

    assertNotEquals(matrix1, matrix2);
  }

  // when:   not a Matrix instance is given
  // expect: false
  @Test
  public void testEquals_wrongClass() {
    long[][][] array1 = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    Matrix matrix1 = Matrix.arrayReader(array1);

    assertNotEquals(matrix1, "hoge");
  }

  // ********************************
  // clone()
  // ********************************

  // when:
  // expect: same matrix
  @Test
  public void testClone() {
    long[][][] array = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    Matrix matrix = Matrix.arrayReader(array);
    Matrix cloned = (Matrix)matrix.clone();

    assertEquals(cloned.getElem()[0][0], new Rational(1, 2));
  }

  // ********************************
  // toString()
  // ********************************

  // when:
  // expect: string of matrix
  @Test
  public void testToString() {
    long[][][] array = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    Matrix matrix = Matrix.arrayReader(array);

    assertEquals(matrix.toString(), "1/2, 3/4\n5/6, 7/8\n");
  }
}
