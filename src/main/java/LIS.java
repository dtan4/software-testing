public class LIS {
    private Matrix a;
    private Rational[] b;
    private int[] c;
    private Matrix d;
    private int aRow;
    private int aCol;
    private Rational[] x;
    private Rational[] lb;
    private Rational[] ub;
    private int bvIncDec;
    private int nbvIncdec;
    private boolean verbose;
    private final Rational zero = new Rational(0);
    private final Rational minusOne = new Rational(-1);

    public LIS(Matrix a, Rational[] b, int[] c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Rational[] getX() {
        return this.x;
    }
}
