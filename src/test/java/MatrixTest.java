import org.junit.Test;
import static org.junit.Assert.*;

public class MatrixTest {
  // ********************************
  // Matrix(elem)
  // ********************************

  // when:   elements
  // expect: matrix
  @Test
  public void testMatrix() {
    Rational[][] elem = new Rational[2][];

    for (int i = 0; i < 2; i++) {
      elem[i] = new Rational[2];

      for (int j = 0; j < 3; j++) {
        elem[i][j] = new Rational(i + 1, j + 1);
      }
    }

    Matrix matrix = new Matrix(elem);

    assertEquals(2, matrix.getNRow());
    assertEquals(3, matrix.getNCol());
  }

  // when:   no elements
  // expect: empty matrix
  @Test
  public void testMatrix_empty() {
    Rational[][] elem = {};
    Matrix matrix = new Matrix(elem);

    assertEquals(0, matrix.getNRow());
    assertEquals(0, matrix.getNCol());
  }

  // ********************************
  // arrayReader(array)
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
  // equals(o)
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

  // ********************************
  // substVector(x)
  // ********************************

  // when:   matrix and x have the same size
  // expect: vector
  @Test
  public void testSubstVector_sameSize() {
    long[][][] array = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    Matrix matrix = Matrix.arrayReader(array);
    Rational[] x = {new Rational(1), new Rational(1)};
    Rational[] result = matrix.substVector(x);

    assertEquals(new Rational(5, 4), result[0]);
    assertEquals(new Rational(41, 24), result[1]);
  }

  // when:   x have smaller size
  // expect: vector
  @Test
  public void testSubstVector_smallSize() {
    long[][][] array = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    Matrix matrix = Matrix.arrayReader(array);
    Rational[] x = {new Rational(1)};
    Rational[] result = matrix.substVector(x);

    assertEquals(new Rational(1, 2), result[0]);
    assertEquals(new Rational(5, 6), result[1]);
  }

  // when:   x have bigger size
  // expect: vector
  @Test
  public void testSubstVector_biggerSize() {
    long[][][] array = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    Matrix matrix = Matrix.arrayReader(array);
    Rational[] x = {new Rational(1), new Rational(1), new Rational(1)};
    Rational[] result = matrix.substVector(x);

    assertEquals(new Rational(5, 4), result[0]);
    assertEquals(new Rational(41, 24), result[1]);
  }

  // ********************************
  // rightLower(row, col)
  // ********************************

  // when:   row and col are in range
  // expect: matrix
  @Test
  public void testRightLower_inRange() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    Matrix rightLower = matrix.rightLower(1, 1);

    assertEquals(2, rightLower.getNRow());
    assertEquals(new Rational(7, 8), rightLower.getElem()[0][0]);
    assertEquals(new Rational(5, 7), rightLower.getElem()[1][0]);
  }

  // when:   row and col are out of range
  // expect: null
  @Test
  public void testRightLower_outRange() {
    long[][][] array = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    Matrix matrix = Matrix.arrayReader(array);
    Matrix rightLower = matrix.rightLower(1, 2);

    assertNull(rightLower);
  }

  // ********************************
  // leftUpper(row, col)
  // ********************************

  // when:   row and col are in range
  // expect: matrix
  @Test
  public void testLeftUpper_inRange() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    Matrix leftUpper = matrix.leftUpper(1, 1);

    assertEquals(2, leftUpper.getNRow());
    assertEquals(new Rational(1, 2), leftUpper.getElem()[0][0]);
    assertEquals(new Rational(7, 8), leftUpper.getElem()[1][1]);
  }

  // when:   row and col are out of range
  // expect: null
  @Test
  public void testLeftUpper_outRange() {
    long[][][] array = {{{1, 2}, {3, 4}}, {{5, 6}, {7, 8}}};
    Matrix matrix = Matrix.arrayReader(array);
    Matrix leftUpper = matrix.leftUpper(1, 2);

    assertNull(leftUpper);
  }

  // ********************************
  // replace(row, col, m)
  // ********************************

  // when:   row and col are in range
  // expect: replace matrix
  @Test
  public void testReplace_inRange() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    long[][][] replaceArray = {
            {{11, 12}, {13, 14}}
    };
    Matrix replaceMatrix = Matrix.arrayReader(replaceArray);
    matrix.replace(0, 0, replaceMatrix);

    assertEquals(new Rational(11, 12), matrix.getElem()[0][0]);
    assertEquals(new Rational(13, 14), matrix.getElem()[0][1]);
  }

  // when:   m is small
  // expect: replace matrix
  @Test
  public void testReplace_inRange_small() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    long[][][] replaceArray = {
            {{11, 12}}
    };
    Matrix replaceMatrix = Matrix.arrayReader(replaceArray);
    matrix.replace(0, 0, replaceMatrix);

    assertEquals(new Rational(11, 12), matrix.getElem()[0][0]);
    assertEquals(new Rational(3, 4), matrix.getElem()[0][1]);
  }

  // when:   m is big
  // expect: replace matrix
  @Test
  public void testReplace_inRange_big() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    long[][][] replaceArray = {
            {{11, 12}, {13, 14}, {15, 16}}
    };
    Matrix replaceMatrix = Matrix.arrayReader(replaceArray);
    matrix.replace(0, 0, replaceMatrix);

    assertEquals(new Rational(11, 12), matrix.getElem()[0][0]);
    assertEquals(new Rational(13, 14), matrix.getElem()[0][1]);
  }

  // when:   row and col are out of range
  // expect: do nothing
  @Test
  public void testReplace_outRange() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    long[][][] replaceArray = {
            {{11, 12}, {13, 14}}
    };
    Matrix replaceMatrix = Matrix.arrayReader(replaceArray);
    matrix.replace(0, 3, replaceMatrix);

    assertEquals(new Rational(1, 2), matrix.getElem()[0][0]);
    assertEquals(new Rational(3, 4), matrix.getElem()[0][1]);
  }

  // ********************************
  // multiplyRow(row, r)
  // ********************************

  // when:   row is in range
  // expect: multiply
  @Test
  public void testMultiplyRow_inRange() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    Rational r = new Rational(2);
    matrix.multiplyRow(1, r);

    assertEquals(new Rational(5, 3), matrix.getElem()[1][0]);
    assertEquals(new Rational(7, 4), matrix.getElem()[1][1]);
  }

  // when:   r is zero
  // expect: multiply with zero
  @Test
  public void testMultiplyRow_zero() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    Rational r = new Rational();
    matrix.multiplyRow(1, r);

    assertEquals(new Rational(), matrix.getElem()[1][0]);
    assertEquals(new Rational(), matrix.getElem()[1][1]);
  }

  // when:   row is out of range
  // expect: do nothing
  @Test
  public void testMultiplyRow_outRange() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    Rational r = new Rational();
    matrix.multiplyRow(3, r);

    assertEquals(new Rational(5, 6), matrix.getElem()[1][0]);
    assertEquals(new Rational(7, 8), matrix.getElem()[1][1]);
  }

  // ********************************
  // addMultipliedRow(row1, r, row2)
  // ********************************

  // when:   row is in range
  // expect: add multiplied row
  @Test
  public void testAddMultipliedRow_inRange() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    Rational r = new Rational(2);
    matrix.addMultipliedRow(1, r, 2);

    assertEquals(new Rational(6, 3), matrix.getElem()[2][0]);
    assertEquals(new Rational(69, 28), matrix.getElem()[2][1]);
  }

  // when:   r is zero
  // expect: add multiplied row (= do nothing)
  @Test
  public void testAddMultipliedRow_zero() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    Rational r = new Rational();
    matrix.addMultipliedRow(1, r, 2);

    assertEquals(new Rational(1, 3), matrix.getElem()[2][0]);
    assertEquals(new Rational(5, 7), matrix.getElem()[2][1]);
  }

  // when:   row is out of range
  // expect: do nothing
  @Test
  public void testAddMultipliedRow_outRange() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    Rational r = new Rational();
    matrix.multiplyRow(3, r);

    matrix.addMultipliedRow(3, r, 2);

    assertEquals(new Rational(1, 3), matrix.getElem()[2][0]);
    assertEquals(new Rational(5, 7), matrix.getElem()[2][1]);
  }

  // ********************************
  // exchangeRow(row1, row2)
  // ********************************

  // when:   row is in range
  // expect: exchange row
  @Test
  public void testExchangeRow_inRange() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    matrix.exchangeRow(0, 1);

    assertEquals(new Rational(5, 6), matrix.getElem()[0][0]);
    assertEquals(new Rational(1, 2), matrix.getElem()[1][0]);
  }

  // when:   row is out of range
  // expect: do nothing
  @Test
  public void testExchangeRow_outRange() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    matrix.exchangeRow(0, 3);

    assertEquals(new Rational(1, 2), matrix.getElem()[0][0]);
    assertEquals(new Rational(5, 6), matrix.getElem()[1][0]);
  }

  // ********************************
  // exchangeCol(col1, col2)
  // ********************************

  // when:   col is in range
  // expect: exchange col
  @Test
  public void testExchangeCol_inRange() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    matrix.exchangeCol(0, 1);

    assertEquals(new Rational(3, 4), matrix.getElem()[0][0]);
    assertEquals(new Rational(1, 2), matrix.getElem()[0][1]);
  }

  // when:   col is out of range
  // expect: do nothing
  @Test
  public void testExchangeCol_outRange() {
    long[][][] array = {
            {{1, 2}, {3, 4}},
            {{5, 6}, {7, 8}},
            {{1, 3}, {5, 7}}
    };
    Matrix matrix = Matrix.arrayReader(array);
    matrix.exchangeCol(0, 2);

    assertEquals(new Rational(1, 2), matrix.getElem()[0][0]);
    assertEquals(new Rational(3, 4), matrix.getElem()[0][1]);
  }

  // ********************************
  // eliminate(row, col)
  // ********************************

  // when:   row and col are in range
  // expect: eliminate
  @Test
  public void testElimintate_inRange() {
    long[][][] array = {
            {{1, 1}, {2, 1}, {3, 1}},
            {{4, 1}, {5, 1}, {6, 1}},
            {{7, 1}, {8, 1}, {9, 1}},
    };
    Matrix matrix = Matrix.arrayReader(array);
    matrix.eliminate(1, 1);

    assertEquals(new Rational(-1, 1), matrix.getElem()[1][1]);
    assertEquals(new Rational(0), matrix.getElem()[0][1]);
    assertEquals(new Rational(0), matrix.getElem()[2][1]);
  }

  // when:   row and col are out of range
  // expect: do nothing
  @Test
  public void testEliminate_outRange() {
    long[][][] array = {
            {{1, 1}, {2, 1}, {3, 1}},
            {{4, 1}, {5, 1}, {6, 1}},
            {{7, 1}, {8, 1}, {9, 1}},
    };
    Matrix matrix = Matrix.arrayReader(array);
    matrix.eliminate(1, 3);

    assertEquals(new Rational(5, 1), matrix.getElem()[1][1]);
  }

  // ********************************
  // nonZeroColumn(row)
  // ********************************

  // when:   non-zero column exists
  // expect: index of non-zero column
  @Test
  public void testNonZeroColumn_exist() {
    long[][][] array = {
            {{1, 1}, {2, 1}, {3, 1}},
            {{0, 1}, {0, 1}, {6, 1}},
            {{7, 1}, {8, 1}, {9, 1}},
    };
    Matrix matrix = Matrix.arrayReader(array);

    assertEquals(2, matrix.nonZeroColumn(1));
  }

  // when:   non-zero column does not exist
  // expect: number of columns
  @Test
  public void testNonZeroColumn_not_exist() {
    long[][][] array = {
            {{1, 1}, {2, 1}, {3, 1}},
            {{0, 1}, {0, 1}, {0, 1}},
            {{7, 1}, {8, 1}, {9, 1}},
    };
    Matrix matrix = Matrix.arrayReader(array);

    assertEquals(3, matrix.nonZeroColumn(1));
  }

  // when:   row is out of range
  // expect: raise AssertionError
  @Test(expected = AssertionError.class)
  public void testNonZeroColumn_outRange() {
    long[][][] array = {
            {{1, 1}, {2, 1}, {3, 1}},
            {{0, 1}, {0, 1}, {6, 1}},
            {{7, 1}, {8, 1}, {9, 1}},
    };
    Matrix matrix = Matrix.arrayReader(array);
    matrix.nonZeroColumn(3);
  }

  // ********************************
  // isEchelonForm()
  // ********************************

  // when:   matrix is echelon form
  // expect: true
  @Test
  public void testIsEchelonForm_true() {
    long[][][] array = {
            {{1, 1}, {2, 1}, {3, 1}},
            {{0, 1}, {5, 1}, {0, 1}},
            {{0, 1}, {0, 1}, {9, 1}},
    };
    Matrix matrix = Matrix.arrayReader(array);

    assertTrue(matrix.isEchelonForm());
  }

  // when:   matrix is not echelon form
  // expect: false
  @Test
  public void testIsEchelonForm_false() {
    long[][][] array = {
            {{1, 1}, {2, 1}, {3, 1}},
            {{0, 1}, {0, 1}, {0, 1}},
            {{7, 1}, {8, 1}, {9, 1}},
    };
    Matrix matrix = Matrix.arrayReader(array);

    assertFalse(matrix.isEchelonForm());
  }

  // when:   matrix is not echelon form
  // expect: false
  @Test
  public void testIsEchelonForm_false2() {
    long[][][] array = {
            {{1, 1}, {2, 1}, {3, 1}},
            {{0, 1}, {0, 1}, {1, 1}},
            {{7, 1}, {8, 1}, {9, 1}},
    };
    Matrix matrix = Matrix.arrayReader(array);

    assertFalse(matrix.isEchelonForm());
  }

  // ********************************
  // echelonForm()
  // ********************************

  // when:
  // expect: introduce echelon form
  @Test
  public void testEchelonForm() {
    long[][][] array = {
            {{1, 1}, {2, 1}, {3, 1}},
            {{4, 1}, {5, 1}, {6, 1}},
            {{7, 1}, {8, 1}, {9, 1}},
    };
    Matrix matrix = Matrix.arrayReader(array);
    matrix.echelonForm();

    // -1 -2 -3
    // 0 -1 -2
    // 0 0 0
    assertTrue(matrix.isEchelonForm());
    assertEquals(new Rational(-3), matrix.getElem()[0][2]);
    assertEquals(new Rational(-2), matrix.getElem()[1][2]);
  }

  // when:
  // expect: introduce echelon form
  @Test
  public void testEchelonForm_2() {
    long[][][] array = {
            {{0, 1}, {2, 1}, {3, 1}},
            {{0, 1}, {5, 1}, {6, 1}},
            {{0, 1}, {8, 1}, {9, 1}},
    };
    Matrix matrix = Matrix.arrayReader(array);
    matrix.echelonForm();

    // 0 -1 -3/2
    // 0 0 -1
    // 0 0 0
    assertTrue(matrix.isEchelonForm());
    assertEquals(new Rational(-3, 2), matrix.getElem()[0][2]);
  }

  // when:
  // expect: introduce echelon form
  @Test
  public void testEchelonForm_3() {
    long[][][] array = {
            {{0, 1}, {2, 1}, {3, 1}},
            {{1, 1}, {5, 1}, {6, 1}},
            {{0, 1}, {8, 1}, {9, 1}},
    };
    Matrix matrix = Matrix.arrayReader(array);
    matrix.echelonForm();

    // -1 -5 -6
    // 0 -1 -3/2
    // 0 0 -1
    assertTrue(matrix.isEchelonForm());
    assertEquals(new Rational(-6), matrix.getElem()[0][2]);
    assertEquals(new Rational(-3, 2), matrix.getElem()[1][2]);
  }
}
