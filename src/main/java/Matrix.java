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
      this.nRow = elem[0].length;
    } else {
      this.nRow = 0;
    }
  }

  public Rational[][] getElem() {
    return this.elem;
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
}
