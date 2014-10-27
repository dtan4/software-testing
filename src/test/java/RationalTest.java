import org.junit.Test;
import static org.junit.Assert.*;

public class RationalTest {
  // ********************************
  // Rational(num, den)
  // ********************************

  // when:   約分の生じない有理数
  // expect: 有理数
  @Test
  public void testRational_2_3() {
    Rational rational = new Rational(2, 3);

    assertEquals(rational.getNum(), 2);
    assertEquals(rational.getDen(), 3);
  }

  // when:   約分を生じる有理数
  // expect: 正規化された有理数
  public void testRational_12_18() {
    Rational rational = new Rational(12, 18);

    assertEquals(rational.getNum(), 2);
    assertEquals(rational.getDen(), 3);
  }

  // when:   1に正規化される有理数
  // expect: 1に正規化された有理数
  @Test
  public void testRational_12_12() {
    Rational rational = new Rational(12, 12);

    assertEquals(rational.getNum(), 1);
    assertEquals(rational.getDen(), 1);
  }

  // when:   分母に0が与えられた
  // expect: 1に正規化された有理数
  @Test
  public void testRational_12_0() {
    Rational rational = new Rational(12, 0);

    assertEquals(rational.getNum(), 12);
    assertEquals(rational.getDen(), 1);
  }

  // when:   分子に負数が与えられた
  // expect: 有理数
  @Test
  public void testNormalize_minus12_18() {
    Rational rational = new Rational(-12, 18);
    rational.normalize();

    assertEquals(rational.getNum(), -2);
    assertEquals(rational.getDen(), 3);
  }

  // when:   分母に負数が与えられた
  // expect: 分母が正数化された有理数
  @Test
  public void testNormalize_12_minus18() {
    Rational rational = new Rational(12, -18);
    rational.normalize();

    assertEquals(rational.getNum(), -2);
    assertEquals(rational.getDen(), 3);
  }

  // ********************************
  // Rational(num)
  // ********************************

  // when:   1つの数値のみ与えられた
  // expect: 分母1の有理数
  @Test
  public void testRational_12() {
    Rational rational = new Rational(12);

    assertEquals(rational.getNum(), 12);
    assertEquals(rational.getDen(), 1);
  }

  // ********************************
  // Rational()
  // ********************************

  // when:   無引数
  // expect: 分子・分母ともに1の有理数
  @Test
  public void testRational_no_args() {
    Rational rational = new Rational();

    assertEquals(rational.getNum(), 0);
    assertEquals(rational.getDen(), 1);
  }

  // ********************************
  // testArrayReader(array)
  // ********************************

  // when:   空配列を与える
  // expect: null
  @Test
  public void testArrayReader_empty() {
    long[] array = {};
    Rational rational = Rational.arrayReader(array);

    assertNull(rational);
  }

  // when:   1要素配列を与える
  // expect: 分母1の有理数
  @Test
  public void testArrayReader_12() {
    long[] array = {12};
    Rational rational = Rational.arrayReader(array);

    assertEquals(rational.getNum(), 12);
    assertEquals(rational.getDen(), 1);
  }

  // when:   約分を生じない2要素配列を与える
  // expect: 有理数
  @Test
  public void testArrayReader_2_3(){
    long[] array = {12, 18};
    Rational rational = Rational.arrayReader(array);

    assertEquals(rational.getNum(), 2);
    assertEquals(rational.getDen(), 3);
  }

  // when:   約分を生じる2要素配列を与える
  // expect: 正規化された有理数
  @Test
  public void testArrayReader_12_18(){
    long[] array = {12, 18};
    Rational rational = Rational.arrayReader(array);

    assertEquals(rational.getNum(), 2);
    assertEquals(rational.getDen(), 3);
  }

  // when:   分母が0となる2要素配列を与える
  // expect: null
  @Test
  public void testArrayReader_12_0(){
    long[] array = {12, 0};
    Rational rational = Rational.arrayReader(array);

    assertNull(rational);
  }

  // when:   3要素配列を与える
  // expect: null
  @Test
  public void testArrayReader_12_18_2(){
    long[] array = {12, 18, 2};
    Rational rational = Rational.arrayReader(array);

    assertNull(rational);
  }

  // ********************************
  // equals(o)
  // ********************************

  // when:   メソッド呼び出し元と等価な有理数を与える
  // expect: true
  @Test
  public void testEquals_same() {
    Rational rational1 = new Rational(2, 3);
    Rational rational2 = new Rational(2, 3);

    assertTrue(rational1.equals(rational2));
  }

  // when:   メソッド呼び出し元と等価でない有理数を与える
  // expect: false
  @Test
  public void testEquals_different() {
    Rational rational1 = new Rational(2, 3);
    Rational rational2 = new Rational(2, 5);

    assertFalse(rational1.equals(rational2));
  }

  // when:   Rational 以外のインスタンスを与える
  // expect: false
  @Test
  public void testEquals_wrongClass() {
    Rational rational1 = new Rational(2, 3);

    assertFalse(rational1.equals("hoge"));
  }

  // ********************************
  // toString()
  // ********************************

  // when:   約分を生じない有理数を与える
  // expect: 文字列として出力
  @Test
  public void testToString_2_3() {
    Rational rational = new Rational(2, 3);

    assertEquals(rational.toString(), "2/3");
  }

  // when:   約分を生じる有理数を与える
  // expect: 正規化した有理数を文字列として出力
  @Test
  public void testToString_12_18() {
    Rational rational = new Rational(12, 18);

    assertEquals(rational.toString(), "2/3");
  }

  // when:   分母が1の有理数を与える
  // expect: 整数として文字列を出力
  @Test
  public void testToString_12_1() {
    Rational rational = new Rational(12, 1);

    assertEquals(rational.toString(), "12");
  }

  // when:   負の有理数を与える
  // expect: 符号付き文字列を出力
  @Test
  public void testToString_minus2_3() {
    Rational rational = new Rational(-2, 3);

    assertEquals(rational.toString(), "-2/3");
  }

  // ********************************
  // add(r)
  // ********************************

  // when:   負の有理数を与える
  // expect: 符号付き文字列を出力
  @Test
  public void testAdd() {
    Rational rational1 = new Rational(2, 3);
    Rational rational2 = new Rational(3, 4);
    Rational added = rational1.add(rational2);

    assertEquals(added.getNum(), 17);
    assertEquals(added.getDen(), 12);
  }

  // ********************************
  // multiply(r)
  // ********************************

  // when:   負の有理数を与える
  // expect: 符号付き文字列を出力
  @Test
  public void testMultiply() {
    Rational rational1 = new Rational(2, 3);
    Rational rational2 = new Rational(3, 4);
    Rational multiplied = rational1.multiply(rational2);

    assertEquals(multiplied.getNum(), 1);
    assertEquals(multiplied.getDen(), 2);
  }

  // ********************************
  // inverse()
  // ********************************

  // when:   分子が0でない有理数
  // expect: 逆数
  @Test
  public void testInverse_2_3() {
    Rational rational = new Rational(2, 3);
    Rational inversed = rational.inverse();

    assertEquals(inversed.getNum(), 3);
    assertEquals(inversed.getDen(), 2);
  }

  // when:   分子が0である有理数
  // expect: null
  @Test
  public void testInverse_0_3() {
    Rational rational = new Rational(0, 3);
    Rational inversed = rational.inverse();

    assertNull(inversed);
  }

  // ********************************
  // greaterThan(r)
  // ********************************

  // when:   呼び出し元より大きい有理数を与える
  // expect: false
  @Test
  public void testGreaterThan_greater() {
    Rational rational1 = new Rational(2, 3);
    Rational rational2 = new Rational(4, 3);

    assertFalse(rational1.greaterThan(rational2));
  }

  // when:   呼び出し元と有理数を与える
  // expect: false
  @Test
  public void testGreaterThan_same() {
    Rational rational1 = new Rational(2, 3);
    Rational rational2 = new Rational(2, 3);

    assertFalse(rational1.greaterThan(rational2));
  }

  // when:   呼び出し元より小さいを与える
  // expect: true
  @Test
  public void testGreaterThan_less() {
    Rational rational1 = new Rational(2, 3);
    Rational rational2 = new Rational(1, 3);

    assertTrue(rational1.greaterThan(rational2));
  }

  // ********************************
  // lessThan(r)
  // ********************************

  // when:   呼び出し元より大きい有理数を与える
  // expect: true
  @Test
  public void testLessThan_greater() {
    Rational rational1 = new Rational(2, 3);
    Rational rational2 = new Rational(4, 3);

    assertTrue(rational1.lessThan(rational2));
  }

  // when:   呼び出し元と有理数を与える
  // expect: false
  @Test
  public void testLessThan_same() {
    Rational rational1 = new Rational(2, 3);
    Rational rational2 = new Rational(2, 3);

    assertFalse(rational1.lessThan(rational2));
  }

  // when:   呼び出し元より小さい有理数を与える
  // expect: false
  @Test
  public void testLessThan_less() {
    Rational rational1 = new Rational(2, 3);
    Rational rational2 = new Rational(1, 3);

    assertFalse(rational1.lessThan(rational2));
  }
}
