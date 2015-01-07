import java.util.LongSummaryStatistics;

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

    private final Rational zero = new Rational(0);
    private final Rational minusOne = new Rational(-1);

    private static final int EQUAL = 0;   // =
    private static final int GREATER = 1; // >=
    private static final int LESS = 2;    // <=

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

        for (int i = 0; i < r.length; i++) {
            if (compare(r[i], b[i]) != c[i]) {
                return false;
            }
        }

        return true;
    }

    private int compare(Rational r1, Rational r2) {
        if (r1.greaterThan(r2)) {
            return GREATER;
        } else if (r1.lessThan(r2)) {
            return LESS;
        }

        return EQUAL;
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
            this.lb[d.p[i]] = (c[i] == GREATER) ? b[i] : negativeInfinity;
            this.ub[d.p[i]] = (c[i] == LESS) ? b[i] : positiveInfinity;
        }

        for (int i = aRow; i < aRow + aCol; i++) {
            this.lb[d.p[i]] = negativeInfinity;
            this.ub[d.p[i]] = positiveInfinity;
        }
    }
}
