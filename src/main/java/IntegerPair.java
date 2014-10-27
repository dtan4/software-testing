public class IntegerPair {
  private long a;
  private long b;

  public IntegerPair(long a, long b) {
    this.a = a;
    this.b = b;
  }

  public long gcd() {
    long _a = a;
    long _b = b;

    if ((_a == 0) && (_b == 0)) {
      return 0;
    }

    while (true) {
      if (_b == 0) {
        return _a > 0 ? _a : -_a;
      }

      long tmp = _b;
      _b = _a % _b;
      _a = tmp;
    }
  }
}
