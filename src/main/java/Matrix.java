public class Matrix {
  private Rational[][] elem;
  private int nRow;
  private int nCol;
  private final Rational minusOne = new Rational(-1);
  private final Rational zero = new Rational(0);

  public Matrix(Rational[][] elem) {
    this.elem = elem;
    this.nRow = elem.length;

    if (elem.length > 0) {
      this.nCol = elem[0].length;
    } else {
      this.nCol = 0;
    }
  }

  public Rational[][] getElem() {
    return this.elem;
  }

  public int getNRow() {
    return this.nRow;
  }

  public int getNCol() {
    return this.nCol;
  }

  static Matrix arrayReader(long[][][] array) {
    if ((array.length == 0) || (array[0].length == 0)) {
      return null;
    }

    int nRow, nCol;

    nRow = array.length;
    nCol = array[0].length;

    Rational[][] elem = new Rational[nRow][];

    for (int i = 0; i < nRow; i++) {
      elem[i] = new Rational[nCol];

      if (array[i].length <= array[0].length) {
        for (int j = 0; j < nCol; j++) {
          if (j < array[i].length) {
            elem[i][j] = Rational.arrayReader(array[i][j]);
          } else {
            elem[i][j] = new Rational(0);
          }
        }
      } else {
        for (int j = 0; j < nCol; j++) {
          elem[i][j] = Rational.arrayReader(array[i][j]);
        }
      }
    }

    return new Matrix(elem);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Matrix)) {
      return false;
    }

    Matrix m = (Matrix)o;

    if ((nRow != m.getNRow()) || (nCol != m.getNCol())) {
      return false;
    }

    Rational[][] givenElem = m.getElem();

    for (int i = 0; i < nRow; i++) {
      for (int j = 0; j < nCol; j++) {
        if (!elem[i][j].equals(givenElem[i][j])) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public Object clone() {
    Rational[][] elem = new Rational[nRow][];

    for (int i = 0; i < nRow; i++) {
      elem[i] = new Rational[nCol];

      for (int j = 0; j < nCol; j++) {
        elem[i][j] = (Rational)this.elem[i][j].clone();
      }
    }

    return new Matrix(elem);
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < nRow; i++) {
      for (int j = 0; j < nCol; j++) {
        sb.append(elem[i][j]);

        if (j < nRow - 1) {
          sb.append(", ");
        }
      }

      sb.append("\n");
    }

    return sb.toString();
  }

  public Rational[] substVector(Rational[] x) {
    Rational[] result = new Rational[nRow];

    for (int i = 0; i < nRow; i++) {
      Rational addResult = new Rational();

      for (int j = 0; j < nCol; j++) {
        if (j < x.length) {
          Rational multiplyResult = elem[i][j].multiply(x[j]);
          addResult = addResult.add(multiplyResult);
        }
      }

      result[i] = addResult;
    }

    return result;
  }

  private boolean isInRowRange(int row) {
    return (0 <= row) && (row < nRow);
  }

  private boolean isInColumnRange(int col) {
    return (0 <= col) && (col < nCol);
  }

  public Matrix rightLower(int row, int col) {
    if (!(isInRowRange(row) && isInColumnRange(col))) {
      return null;
    }

    int resultNRow = nRow - row + 1;
    int resultNCol = nCol - col + 1;
    Rational[][] rightLowerElem = new Rational[resultNRow][];

    for (int i = 0; i < resultNRow; i++) {
      rightLowerElem[i] = new Rational[resultNCol];

      for (int j = 0; j < resultNCol; j++) {
        rightLowerElem[i][j] = elem[row + i - 1][col + j - 1];
      }
    }

    return new Matrix(rightLowerElem);
  }

  public Matrix leftUpper(int row, int col) {
    if (!(isInRowRange(row) && isInColumnRange(col))) {
      return null;
    }

    Rational[][] leftUpperElem = new Rational[row][];

    for (int i = 0; i < row; i++) {
      leftUpperElem[i] = new Rational[col];

      for (int j = 0; j < col; j++) {
        leftUpperElem[i][j] = elem[i][j];
      }
    }

    return new Matrix(leftUpperElem);
   }

  public void replace(int row, int col, Matrix m) {
    if (!(isInRowRange(row) && isInColumnRange(col))) {
      return;
    }

    Rational[][] replaceElem = m.getElem();

    for (int i = 0; i < m.getNRow(); i++) {
      if (i >= nRow) {
        return;
      }

      for (int j = 0; j < m.getNCol(); j++) {
        if (j >= nCol) {
          break;
        }

        elem[row - 1 + i][col - 1 + j] = replaceElem[i][j];
      }
    }
  }

  public void multiplyRow(int row, Rational r) {
    if (!isInRowRange(row)) {
      return;
    }

    for (int i = 0; i < nCol; i++) {
      elem[row][i] = elem[row][i].multiply(r);
    }
  }

  public void addMultipliedRow(int row1, Rational r, int row2) {
    if (!(isInRowRange(row1) && isInRowRange(row2))) {
      return;
    }

    for (int i = 0; i < nCol; i++) {
      elem[row2][i] = elem[row2][i].add(elem[row1][i].multiply(r));
    }
  }

  public void exchangeRow(int row1, int row2) {
    if (!(isInRowRange(row1) && isInRowRange(row2))) {
      return;
    }

    Rational[] tmpRow = elem[row1];
    elem[row1] = elem[row2];
    elem[row2] = tmpRow;
  }

  public void exchangeCol(int col1, int col2) {
    if (!(isInColumnRange(col1) && isInColumnRange(col2))) {
      return;
    }

    for (int i = 0; i < nRow; i++) {
      Rational tmpRational = elem[i][col1];
      elem[i][col1] = elem[i][col2];
      elem[i][col2] = tmpRational;
    }
  }
}
