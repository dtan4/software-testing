import org.junit.Test;
import static org.junit.Assert.*;

public class IntegerPairTest {
  // ********************************
  // gcd()
  // ********************************

  // when:   互いに素な整数ペア
  // expect: 1
  @Test
  public void testGcd_2_3() {
    IntegerPair pair = new IntegerPair(2, 3);
    assertEquals(pair.gcd(), 1);
  }

  // when:   互いに素でない整数ペア
  // expect: 最大公約数
  @Test
  public void testGcd_12_18() {
    IntegerPair pair = new IntegerPair(12, 18);
    assertEquals(pair.gcd(), 6);
  }

  // when:   同じ整数のペア
  // expect: 与えられた整数
  @Test
  public void testGcd_12_12() {
    IntegerPair pair = new IntegerPair(12, 12);
    assertEquals(pair.gcd(), 12);
  }

  // when:   0を含む整数ペア
  // expect: 0
  @Test
  public void testGcd_12_0() {
    IntegerPair pair = new IntegerPair(12, 0);
    assertEquals(pair.gcd(), 0);
  }

  // when:   負数を含む整数ペア
  // expect: 最大公約数
  @Test
  public void testGcd_12_minus18() {
    IntegerPair pair = new IntegerPair(12, -18);
    assertEquals(pair.gcd(), -6);
  }
}
