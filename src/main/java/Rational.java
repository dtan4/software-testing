public class Rational {
  private long num; // numerator
  private long den; // denominator

  public Rational(long num, long den) {
    if (num == Long.MIN_VALUE) {
      this.num = -Long.MAX_VALUE;
    } else {
      this.num = num;
    }

    if (den == 0) {
      this.den = 1;
    } else if (den == Long.MIN_VALUE) {
      this.den = -Long.MAX_VALUE;
    } else {
      this.den = den;
    }

    normalize();
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

  @Override
  public Object clone() {
    return new Rational(this.num, this.den);
  }

  @Override
  public String toString() {
    if (this.den == 1) {
      return String.valueOf(this.num);
    } else {
      return String.valueOf(this.num) + "/" + String.valueOf(this.den);
    }
  }

  public Rational add(Rational r) {
    long newNum = this.num * r.getDen() + this.den * r.getNum();
    long newDen = this.den * r.getDen();

    return new Rational(newNum, newDen);
  }

  public Rational multiply(Rational r) {
    long newNum = this.num * r.getNum();
    long newDen = this.den * r.getDen();

    return new Rational(newNum, newDen);
  }

  public Rational inverse() {
    if (this.num == 0) {
      return null;
    }

    return new Rational(this.den, this.num);
  }

  private long compareWith(Rational r) {
    return this.num * r.getDen() - this.den * r.getNum();
  }

  public boolean greaterThan(Rational r) {
    return compareWith(r) > 0;
  }

  public boolean lessThan(Rational r) {
    return compareWith(r) < 0;
  }
}
