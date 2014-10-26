import org.junit.Test;
import static org.junit.Assert.*;

public class RationalTest {
  @Test
  public void testNormalize_12_18() {
    Rational rational = new Rational(12, 18);
    rational.normalize();

    assertEquals(rational.getNum(), 2);
    assertEquals(rational.getDen(), 3);
  }

  @Test
  public void testNormalize_12_12() {
    Rational rational = new Rational(12, 12);
    rational.normalize();

    assertEquals(rational.getNum(), 1);
    assertEquals(rational.getDen(), 1);
  }

  @Test
  public void testNormalize_12_0() {
    Rational rational = new Rational(12, 0);
    rational.normalize();

    assertEquals(rational.getNum(), 12);
    assertEquals(rational.getDen(), 1);
  }

  // @Test
  // public void testNormalize_minus() {
  //   Rational rational = new Rational(12, -18);
  //   rational.normalize();

  //   assertEquals(rational.getNum(), 2);
  //   assertEquals(rational.getDen(), 3);
  // }
}
