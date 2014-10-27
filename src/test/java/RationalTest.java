import org.junit.Test;
import static org.junit.Assert.*;

public class RationalTest {
  @Test
  public void testRational_2_3() {
    Rational rational = new Rational(2, 3);
    assertEquals(rational.getNum(), 2);
    assertEquals(rational.getDen(), 3);
  }

  public void testRational_12_18() {
    Rational rational = new Rational(12, 18);

    assertEquals(rational.getNum(), 2);
    assertEquals(rational.getDen(), 3);
  }

  @Test
  public void testRational_12_12() {
    Rational rational = new Rational(12, 12);

    assertEquals(rational.getNum(), 1);
    assertEquals(rational.getDen(), 1);
  }

  @Test
  public void testRational_12_0() {
    Rational rational = new Rational(12, 0);

    assertEquals(rational.getNum(), 12);
    assertEquals(rational.getDen(), 1);
  }

  @Test
  public void testRational_12() {
    Rational rational = new Rational(12);

    assertEquals(rational.getNum(), 12);
    assertEquals(rational.getDen(), 1);
  }

  @Test
  public void testRational_no_args() {
    Rational rational = new Rational();

    assertEquals(rational.getNum(), 0);
    assertEquals(rational.getDen(), 1);
  }

  // @Test
  // public void testNormalize_minus() {
  //   Rational rational = new Rational(12, -18);
  //   rational.normalize();

  //   assertEquals(rational.getNum(), 2);
  //   assertEquals(rational.getDen(), 3);
  // }

  @Test
  public void testArrayReader_empty() {
    long[] array = {};
    Rational rational = Rational.arrayReader(array);
    assertNull(rational);
  }

  @Test
  public void testArrayReader_12() {
    long[] array = {12};
    Rational rational = Rational.arrayReader(array);
    assertEquals(rational.getNum(), 12);
    assertEquals(rational.getDen(), 1);
  }

  @Test
  public void testArrayReader_2_3(){
    long[] array = {12, 18};
    Rational rational = Rational.arrayReader(array);
    assertEquals(rational.getNum(), 2);
    assertEquals(rational.getDen(), 3);
  }

  @Test
  public void testArrayReader_12_18(){
    long[] array = {12, 18};
    Rational rational = Rational.arrayReader(array);
    assertEquals(rational.getNum(), 2);
    assertEquals(rational.getDen(), 3);
  }

  @Test
  public void testArrayReader_12_0(){
    long[] array = {12, 0};
    Rational rational = Rational.arrayReader(array);
    assertNull(rational);
  }

  @Test
  public void testArrayReader_12_18_2(){
    long[] array = {12, 18, 2};
    Rational rational = Rational.arrayReader(array);
    assertNull(rational);
  }

  @Test
  public void testEquals_same() {
    Rational rational1 = new Rational(2, 3);
    Rational rational2 = new Rational(2, 3);
    assertTrue(rational1.equals(rational2));
  }

  @Test
  public void testEquals_different() {
    Rational rational1 = new Rational(2, 3);
    Rational rational2 = new Rational(2, 5);
    assertFalse(rational1.equals(rational2));
  }

  @Test
  public void testEquals_wrongClass() {
    Rational rational1 = new Rational(2, 3);
    assertFalse(rational1.equals("hoge"));
  }

  @Test
  public void testToString_2_3() {
    Rational rational = new Rational(2, 3);
    assertEquals(rational.toString(), "2/3");
  }

  @Test
  public void testToString_12_18() {
    Rational rational = new Rational(12, 18);
    assertEquals(rational.toString(), "2/3");
  }

  @Test
  public void testToString_12_1() {
    Rational rational = new Rational(12, 1);
    assertEquals(rational.toString(), "12");
  }
}
