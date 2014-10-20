import org.junit.Test;
import static org.junit.Assert.*;

public class IntegerPairGroovyTest {
  @Test
  public void testGcd_12_18() {
    IntegerPair pair = new IntegerPair(12, 18);
    assert pair.gcd() == 6;
  }

  @Test
  public void testGcd_12_12() {
    IntegerPair pair = new IntegerPair(12, 12);
    assert pair.gcd() == 12;
  }

  @Test
  public void testGcd_12_0() {
    IntegerPair pair = new IntegerPair(12, 0);
    assert pair.gcd() == 0;
  }

  @Test
  public void testGcd_minus() {
    IntegerPair pair = new IntegerPair(12, -1);
    assert pair.gcd() == 0;
  }
}
