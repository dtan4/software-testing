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
    switch (array.length) {
    case 1:
      return new Rational(array[0]);
    case 2:
      return (array[1] == 0) ? null : new Rational(array[0], array[1]);
    default:
      return null;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Rational)) {
      return false;
    }

    if ((((Rational)o).getNum() == this.num) && (((Rational)o).getDen() == this.den)) {
      return true;
    } else {
      return false;
    }
  }
}