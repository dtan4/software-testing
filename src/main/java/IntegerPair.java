public class IntegerPair {
  private int a;
  private int b;

  public IntegerPair(int a, int b) {
    this.a = a;
    this.b = b;
  }

  public int gcd() {
    int _a = a;
    int _b = b;

    if ((_a <= 0) || (_b <= 0)) {
        return 0;
      }

    while (true) {
      if (_b == 0) {
        return _a;
      }

      int tmp = _b;
      _b = _a % _b;
      _a = tmp;
    }
  }
}
