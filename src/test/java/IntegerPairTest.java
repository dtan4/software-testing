import org.junit.Test;
import static org.junit.Assert.*;

public class IntegerPairTest {
  @Test
  public void testGcd_2_3() {
    IntegerPair pair = new IntegerPair(2, 3);
    assertEquals(pair.gcd(), 1);
  }

  @Test
  public void testGcd_12_18() {
    IntegerPair pair = new IntegerPair(12, 18);
    assertEquals(pair.gcd(), 6);
  }

  @Test
  public void testGcd_12_12() {
    IntegerPair pair = new IntegerPair(12, 12);
    assertEquals(pair.gcd(), 12);
  }

  @Test
  public void testGcd_12_0() {
    IntegerPair pair = new IntegerPair(12, 0);
    assertEquals(pair.gcd(), 0);
  }

  @Test
  public void testGcd_12_minus18() {
    IntegerPair pair = new IntegerPair(12, -18);
    assertEquals(pair.gcd(), -6);
  }
}
