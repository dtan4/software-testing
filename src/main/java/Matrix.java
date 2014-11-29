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
}
