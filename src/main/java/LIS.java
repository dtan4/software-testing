public class LIS {
    // S = (A, ~b, ~c, ~z)
    private Matrix a;     // A
    private Rational[] b; // ~b
    private int[] c;      // ~c
    private int aRow;
    private int aCol;

    // H = (D, ~x)
    private Matrix d;      // D
    private Rational[] x;  // ~x
    private Rational[] lb; // lower limit of R
    private Rational[] ub; // upeer limit of R
    private int bvIncDec;
    private int nbvIncdec;
    private boolean verbose;

    private final Rational zero = new Rational(0);
    private final Rational minusOne = new Rational(-1);

    private static final int EQUAL = 0;   // =
    private static final int GREATER = 1; // >=
    private static final int LESS = 2;    // <=

    public LIS(Matrix a, Rational[] b, int[] c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.aRow = a.getNRow();
        this.aCol = a.getNCol();
        this.x = new Rational[this.aCol];
    }

    public Rational[] getX() {
        return this.x;
    }

    public boolean hasValidSolution() {
        Rational[] r = a.substVector(x);

        for (int i = 0; i < aCol; i++) {
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
}
