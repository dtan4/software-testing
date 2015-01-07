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
}
