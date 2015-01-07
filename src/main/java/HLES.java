public class HLES {
    protected Matrix d;
    protected Rational[] x;

    private final Rational zero = new Rational(0);
    private final Rational one = new Rational(1);

    public HLES(Matrix d) {
        this.d = d;
        this.x = new Rational[d.nCol];

        for (int i = 0; i < this.x.length; i++) {
            this.x[i] = zero;
        }
    }

    public Rational[] getX() {
        return this.x;
    }

    public void setX(int j, Rational r) {
        this.x[this.d.p[j]] = r;
    }

    public boolean hasValidX() {
        Rational[] x1 = new Rational[d.nCol];

        for (int i = 0; i < x1.length; i++) {
            x1[i] = x[d.p[i]];
        }

        Rational[] subst = d.substVector(x1);

        for (Rational r : subst) {
            if (!r.equals(zero)) {
                return false;
            }
        }

        return true;
    }

    protected Rational[] getZ() {
        Rational[] z = new Rational[d.nCol - d.nRow];

        for (int i = d.nRow; i < d.nCol; i++) {
            z[i - d.nRow] = x[d.p[i]];
        }

        return z;
    }

    protected void setY(Rational[] y) {
        if (y.length != d.nRow) {
            return;
        }

        for (int i = 0; i < d.nRow; i++) {
            x[d.p[i]] = y[i];
        }
    }

    public int pivot(int col1, int col2) {
        if (!d.isLeftIdentity()) {
            return -1;
        }

        if (!isInRange(0, d.nCol - 1, col1) || !isInRange(0, d.nCol - 1, col2)) {
            return -1;
        }

        if (d.elem[col1][col2].equals(zero)) {
            return -1;
        }

        d.exchangeCol(col1, col2);
        d.eliminate(col1, col2);
        Matrix a = d.rightLower(0, d.nRow);
        setY(a.substVector(getZ()));

        return 0;
    }

    private boolean isInRange(int min, int max, int n) {
        return (min <= n) && (n <= max);
    }
}
