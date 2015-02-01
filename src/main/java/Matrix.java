public class Matrix {
    protected Rational[][] elem;
    protected int[] p;
    protected int nRow;
    protected int nCol;
    protected int rank;
    protected final Rational minusOne = new Rational(-1);
    protected final Rational zero = new Rational(0);

    public Matrix(Rational[][] elem) {
        this.elem = elem;
        this.nRow = elem.length;

        if (elem.length > 0) {
            this.nCol = elem[0].length;
        } else {
            this.nCol = 0;
        }

        this.p = new int[nCol];

        for (int i = 0; i < nCol; i++) {
            p[i] = i;
        }
    }

    public Rational[][] getElem() {
        return this.elem;
    }

    public int[] getP() {
        return this.p;
    }

    public void setP(int[] p) {
        this.p = p;
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

        Matrix m = (Matrix) o;

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
                elem[i][j] = (Rational) this.elem[i][j].clone();
            }
        }

        return new Matrix(elem);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < elem.length; i++) {
            for (int j = 0; j < elem[i].length; j++) {
                sb.append(elem[i][j]);

                if (j < elem[i].length - 1) {
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

        int resultNRow = nRow - row;
        int resultNCol = nCol - col;
        Rational[][] rightLowerElem = new Rational[resultNRow][];

        for (int i = 0; i < resultNRow; i++) {
            rightLowerElem[i] = new Rational[resultNCol];

            for (int j = 0; j < resultNCol; j++) {
                rightLowerElem[i][j] = elem[row + i][col + j];
            }
        }

        return new Matrix(rightLowerElem);
    }

    public Matrix leftUpper(int row, int col) {
        if (!(isInRowRange(row) && isInColumnRange(col))) {
            return null;
        }

        Rational[][] leftUpperElem = new Rational[row + 1][];

        for (int i = 0; i <= row; i++) {
            leftUpperElem[i] = new Rational[col + 1];

            for (int j = 0; j <= col; j++) {
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

                elem[row + i][col + j] = replaceElem[i][j];
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

        int tmpP = p[col1];
        p[col1] = p[col2];
        p[col2] = tmpP;
    }

    public void exchangeCol(int[] sigma) {
        assert isPermutation(sigma);

        Rational[][] elem = new Rational[nRow][];
        int[] p = new int[nCol];

        for (int i = 0; i < nRow; i++) {
            elem[i] = new Rational[nCol];

            for (int j = 0; j < nCol; j++) {
                elem[i][j] = this.elem[sigma[i]][j];
            }

            p[i] = this.p[sigma[i]];
        }

        this.elem = elem;
        this.p = p;
    }

    public void eliminate(int row, int col) {
        if (!(isInRowRange(row) && isInColumnRange(col))) {
            return;
        }

        Rational inversed = elem[row][col].inverse().multiply(minusOne);
        multiplyRow(row, inversed);

        for (int i = 0; i < nRow; i++) {
            if (i == row) {
                continue;
            }

            addMultipliedRow(row, elem[i][row], i);
        }
    }

    protected int nonZeroColumn(int row) {
        assert (isInRowRange(row));

        for (int colIndex = 0; colIndex < nCol; colIndex++) {
            if (!elem[row][colIndex].equals(zero)) {
                return colIndex;
            }
        }

        return nCol;
    }

    public boolean isEchelonForm() {
        int previousNonZeroColumn = -1;

        for (int i = 0; i < nRow; i++) {
            int currentNonZeroColumn = nonZeroColumn(i);

            if (previousNonZeroColumn == nCol) {
                if (currentNonZeroColumn != nCol) {
                    return false;
                }
            } else {
                if (currentNonZeroColumn <= previousNonZeroColumn) {
                    return false;
                }

                previousNonZeroColumn = currentNonZeroColumn;
            }
        }

        return true;
    }

    public void echelonForm() {
        if (elem[0][0].equals(zero)) {
            for (int i = 1; i < nRow; i++) {
                if (!elem[i][0].equals(zero)) {
                    exchangeRow(0, i);
                    echelonForm();

                    return;
                }
            }

            if (nCol == 1) {
                return;
            }

            Matrix rightLower = rightLower(0, 1);
            rightLower.echelonForm();
            replace(0, 1, rightLower);

        } else {
            eliminate(0, 0);

            if ((nRow == 1) || (nCol == 1)) {
                return;
            }

            Matrix rightLower = rightLower(1, 1);
            rightLower.echelonForm();
            replace(1, 1, rightLower);
        }
    }

    public boolean isLeftIdentity() {
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nRow; j++) {
                if (i == j) {
                    if (!this.elem[i][j].equals(minusOne)) {
                        return false;
                    }
                } else {
                    if (!this.elem[i][j].equals(zero)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public int pInverse(int var) {
        if ((var < 0) || (var >= nCol)) {
            return -1;
        }

        for (int i = 0; i < nCol; i++) {
            if (p[i] == var) {
                return i;
            }
        }

        return -1;
    }

    protected boolean isPermutation(int sigma[]) {
        if (sigma.length != nCol) {
            return false;
        }

        int[] check = new int[nCol];

        for (int i = 0; i < nCol; i++) {
            if ((sigma[i] < 0) || (nCol - 1 < sigma[i])) {
                return false;
            }

            if (check[sigma[i]] == 1) {
                return false;
            }

            check[sigma[i]] = 1;
        }

        return true;
    }
}