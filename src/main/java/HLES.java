public class HLES {
    protected Matrix d;
    protected Rational[] x;

    private final Rational zero = new Rational(0);
    private final Rational one = new Rational(1);

    public HLES(Matrix d) {
        this.d = d;
        this.x = new Rational[d.getNCol()];

        for (int i = 0; i < this.x.length; i++) {
            this.x[i] = zero;
        }
    }
}
