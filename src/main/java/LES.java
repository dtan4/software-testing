public class LES {
    protected Matrix a;
    protected Rational[] b;
    protected Rational[] x;
    private static final Rational ZERO = new Rational(0);
    private static final Rational MINUSONE = new Rational(-1);

    public LES(Matrix a, Rational[] b) {
        this.a = a;
        this.b = b;
    }

    public boolean hasValidX() {
        Rational[] substVector = a.substVector(x);

        for (int i = 0; i < a.nRow; i++) {
            if (!substVector[i].equals(b[i])) {
                return false;
            }
        }

        return true;
    }

    public int solve() {
        Rational[] bm = new Rational[b.length];

        for (int i = 0; i < b.length; i++) {
            bm[i] = b[i].multiply(MINUSONE);
        }

        Matrix d = a.concatVector(bm);
        HLES h = new HLES(d);
        h.solve();
        x = h.getX();

        return x[d.nCol - 1].equals(ZERO) ? 0 : 1;
    }
}
