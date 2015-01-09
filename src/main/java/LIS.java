public class LIS {
    // S = (A, ~b, ~c, ~z)
    protected Matrix a;     // A
    protected Rational[] b; // ~b
    protected int[] c;      // ~c
    protected int aRow;
    protected int aCol;

    // H = (D, ~x)
    protected Matrix d;      // D
    protected HLES h;
    protected Rational[] x;  // ~x
    protected Rational[] lb; // lower limit of R, size is aRow + aCol
    protected Rational[] ub; // upeer limit of R, size is aRow + aCol

    protected int bvIncDec;    // increase: 1, decrease: -1
    protected int nbvIncDec;   // increase: 1, decrease: -1
    protected boolean verbose; // whether showing info

    private final Rational zero = new Rational(0);
    private final Rational minusOne = new Rational(-1);

    private static final int EQUAL = 0;   // =
    private static final int GREATER = 1; // >=
    private static final int LESS = 2;    // <=

    private static final int INCREASE = 1;
    private static final int DECREASE = -1;

    public static LIS arrayReader(long[][][] array) {
        int nRow, nCol;

        if (array.length == 0) {
            return null;
        }

        nRow = array.length;

        if (array[0].length <= 2) {
            return null;
        }

        nCol = array[0].length - 2;

        Rational[][] elem = new Rational[nRow][];

        for (int i = 0; i < nRow; i++) {
            elem[i] = new Rational[nCol];
        }

        Rational[] b = new Rational[nRow];
        int[] c = new int[nRow];

        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                elem[i][j] = Rational.arrayReader(array[i][j]);
                b[i] = Rational.arrayReader(array[i][nCol]);

                long tmpC = array[i][nCol + 1][0];

                if ((0 <= tmpC) && (tmpC <= 2)) {
                    c[i] = (int)tmpC;
                } else {
                    c[i] = 0;
                }
            }
        }

        Matrix a = new Matrix(elem);

        return new LIS(a, b, c);
    }

    public LIS(Matrix a, Rational[] b, int[] c) {
        this.a = a;
        this.aRow = a.nRow;
        this.aCol = a.nCol;
        this.x = new Rational[this.aCol];

        if (b.length < this.aRow) {
            this.b = new Rational[this.aRow];

            for (int i = 0; i < this.b.length; i++) {
                this.b[i] = (i < b.length) ? b[i] : zero;
            }
        } else {
            this.b = b;
        }

        if (c.length < this.aRow) {
            this.c = new int[this.aRow];

            for (int i = 0; i < this.c.length; i++) {
                this.c[i] = (i < c.length) ? c[i] : 0;
            }
        } else {
            this.c = c;
        }

        for (int i = 0; i < this.c.length; i++) {
            if (!(this.c[i] == EQUAL) && !(this.c[i] == GREATER) && !(this.c[i] == LESS)) {
                this.c[i] = 0;
            }
        }
    }

    public Rational[] getX() {
        return this.x;
    }

    public boolean hasValidX() {
        Rational[] r = a.substVector(x);
        for (int i = 0; i < b.length; i++) {
            switch (c[i])
            {
                case EQUAL:
                    if (!r[i].equals(b[i])) {
                        return false;
                    }
                    break;
                case GREATER:
                    if (r[i].lessThan(b[i])) {
                        return false;
                    }
                    break;
                case LESS:
                    if (r[i].greaterThan(b[i])) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }

    public void transform() {
        makeHLES();
        makeRestriction();
    }

    protected void makeHLES() {
        Rational[][] dElem = new Rational[aRow][aRow + aCol];

        for (int i = 0; i < aRow; i++) {
            for (int j = 0; j < aRow; j++) {
                dElem[i][j] = (i == j) ? minusOne : zero;
            }

            for (int j = aRow; j < aRow + aCol; j++) {
                dElem[i][j] = a.elem[i][j - aRow];
            }
        }

        this.d = new Matrix(dElem);
        int[] p = new int[aRow + aCol];

        for (int i = 0; i < p.length; i++) {
            p[i] = (i <= aCol) ? i + aRow - 1 : i - aCol - 1;
        }

        this.d.setP(p);
        this.h = new HLES(this.d);
        this.x = h.x;
    }

    protected void makeRestriction() {
        Rational negativeInfinity = new Rational(-Long.MAX_VALUE);
        Rational positiveInfinity = new Rational(Long.MAX_VALUE);

        this.lb = new Rational[aRow + aCol];
        this.ub = new Rational[aRow + aCol];

        for (int i = 0; i < aRow; i++) {
            int colNum = d.p[i];

            switch (c[i]) {
                case EQUAL:
                    this.lb[colNum] = b[i];
                    this.ub[colNum] = b[i];
                    break;
                case GREATER:
                    this.lb[colNum] = b[i];
                    this.ub[colNum] = positiveInfinity;
                    break;
                case LESS:
                    this.lb[colNum] = negativeInfinity;
                    this.ub[colNum] = b[i];
                    break;
            }
        }

        for (int i = aRow; i < aRow + aCol; i++) {
            this.lb[d.p[i]] = negativeInfinity;
            this.ub[d.p[i]] = positiveInfinity;
        }
    }

    protected int findBasicVar() {
        int org;

        for (int i = 0; i < aRow; i++) {
            org = d.p[i];

            if (x[org].lessThan(lb[org])) {
                bvIncDec = INCREASE;
                x[org] = lb[org];

                return i;
            }

            if (x[org].greaterThan(ub[org])) {
                bvIncDec = DECREASE;
                x[org] = ub[org];

                return i;
            }
        }

        return -1;
    }

    protected int findNonBasicVar(int bv) { // bv is equal to k
        int org;

        for (int i = aRow; i < aRow + aCol; i++) {
            org = d.p[i];

            if (bvIncDec == INCREASE) {
                if (d.elem[bv][i].greaterThan(zero) && x[org].lessThan(ub[org])) {
                    nbvIncDec = INCREASE;
                    return i;
                }

                if (d.elem[bv][i].lessThan(zero) && x[org].greaterThan(lb[org])) {
                    nbvIncDec = DECREASE;
                    return i;
                }
            }

            if (bvIncDec == DECREASE) {
                if (d.elem[bv][i].greaterThan(zero) && x[org].greaterThan(lb[org])) {
                    nbvIncDec = DECREASE;
                    return i;
                }

                if (d.elem[bv][i].lessThan(zero) && x[org].lessThan(ub[org])) {
                    nbvIncDec = INCREASE;
                    return i;
                }
            }
        }

        return -1;
    }

    public int solve() {
        int bv, nbv;

        while ((bv = findBasicVar()) != -1) {
            if ((nbv = findNonBasicVar(bv)) == -1) {
                return 0;
            }

            h.pivot(bv, nbv);
        }

        return 1;
    }
}
