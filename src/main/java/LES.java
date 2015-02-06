public class LES {
    protected Matrix a;
    protected Rational[] b;
    protected Rational[] x;

    public LES(Matrix a, Rational[] b) {
        this.a = a;
        this.b = b;
    }

    public boolean hasValidX() {
        Rational[] substVector = a.substVector(x);

        for (int i = 0; i < substVector.length; i++) {
            if (i >= a.nCol) {
                return true;
            }

            if (!substVector[i].equals(b[i])) {
                return false;
            }
        }

        return true;
    }
}
