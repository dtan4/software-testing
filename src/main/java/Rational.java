public class Rational {
  private long num; // numerator
  private long den; // denominator

  public Rational(long num, long den) {
    this.num = num;

    if (den == 0) {
      this.den = 1;
    } else {
      this.den = den;
    }
  }

  public Rational(long num) {
    this.num = num;
    this.den = 1;
  }

  public Rational() {
    this.num = 0;
    this.den = 1;
  }

  long getNum() {
    return this.num;
  }

  long getDen() {
    return this.den;
  }

  protected void normalize() {
    liftSign();
    reduce();
  }

  protected void reduce() {
    IntegerPair intPair = new IntegerPair(this.num, this.den);
    long gcd = intPair.gcd();

    this.num /= gcd;
    this.den /= gcd;
  }

  protected void liftSign() {
    if (den < 0) {
      this.num *= -1;
      this.den *= -1;
    }
  }

  static Rational arrayReader(long[] array) {
    Rational rational = new Rational();

    return rational;
  }
}
