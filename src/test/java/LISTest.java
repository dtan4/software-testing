import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

public class LISTest {
    private static final int NROW = 2;
    private static final int NCOL = 3;
    private static final Rational zero = new Rational(0);
    private static final Rational one = new Rational(1);
    private static final Rational minusOne = new Rational(-1);
    private static final Rational negativeInfinity = new Rational(-Long.MAX_VALUE);
    private static final Rational positiveInfinity = new Rational(Long.MAX_VALUE);

    private Matrix createMatrix(int nRow, int nCol) {
        Rational[][] elem = new Rational[nRow][];

        for (int i = 0; i < nRow; i++) {
            elem[i] = new Rational[nCol];

            for (int j = 0; j < nCol; j++) {
                if (nRow < j) {
                    elem[i][j] = new Rational(i + 1, j + 1);
                } else {
                    elem[i][j] = (i == j) ? minusOne : zero;
                }
            }
        }

        return new Matrix(elem);
    }

    private Matrix createMatrix() {
        return createMatrix(NROW, NCOL);
    }


    // ********************************
    // LIS(a, b, c)
    // ********************************

    // when:   valid arguments
    // expect: create new LIS
    @Test
    public void testLIS_valid() {
        Matrix a = createMatrix(3, 3);
        Rational[] b = {
                new Rational(1),
                new Rational(2),
                new Rational(3)
        };
        int[] c = {0, 1, 2};

        LIS lis = new LIS(a, b, c);

        assertEquals(a, lis.a);
        assertArrayEquals(b, lis.b);
        assertArrayEquals(c, lis.c);
    }


    // when:   length of b is less than aRow
    // expect: fill b with 0
    @Test
    public void testLIS_smallB() {
        Matrix a = createMatrix(3, 3);
        Rational[] b = {
                new Rational(1),
        };
        int[] c = {0, 1, 2};

        LIS lis = new LIS(a, b, c);
        Rational[] expectB = {new Rational(1), zero, zero};

        assertArrayEquals(expectB, lis.b);
    }

    // when:   length of c is less than aRow
    // expect: fill c with 0
    @Test
    public void testLIS_smallC() {
        Matrix a = createMatrix(3, 3);
        Rational[] b = {
                new Rational(1),
                new Rational(2),
                new Rational(3)
        };
        int[] c = {0, 1};

        LIS lis = new LIS(a, b, c);
        int[] expectC = {0, 1, 0};

        assertArrayEquals(expectC, lis.c);
    }

    // when:   elements of c are invalid
    // expect: replace with 0
    @Test
    public void testLIS_invalidC() {
        Matrix a = createMatrix(3, 3);
        Rational[] b = {
                new Rational(1),
                new Rational(2),
                new Rational(3)
        };
        int[] c = {0, 1, 3};

        LIS lis = new LIS(a, b, c);
        int[] expectC = {0, 1, 0};

        assertArrayEquals(expectC, lis.c);
    }

    // ********************************
    // getX()
    // ********************************

    // when:
    // expect: return x
    @Test
    public void testGetX() {
        Matrix a = createMatrix(3, 3);
        Rational[] b = {
                new Rational(1),
                new Rational(2),
                new Rational(3)
        };
        int[] c = {0, 1, 2};

        LIS lis = new LIS(a, b, c);
        Rational[] x = {zero, zero, zero};
        lis.x = x;

        assertArrayEquals(x, lis.getX());
    }

    // ********************************
    // hasValidX()
    // ********************************

    // when:   x is valid
    // expect: true
    @Test
    public void testHasValidX_validX() {
        Matrix a = createMatrix(3, 3);
        Rational[] b = {
                minusOne,
                new Rational(-5),
                new Rational(2)
        };
        int[] c = {0, 1, 2};

        LIS lis = new LIS(a, b, c);
        Rational[] x = {new Rational(1), new Rational(2), new Rational(3)};
        lis.x = x;

        assertTrue(lis.hasValidX());
    }

    // when:   x is invalid
    // expect: false
    @Test
    public void testHasValidX_invalidX() {
        Matrix a = createMatrix(3, 3);
        Rational[] b = {
                new Rational(1),
                new Rational(2),
                new Rational(3)
        };
        int[] c = {0, 1, 2};

        LIS lis = new LIS(a, b, c);
        Rational[] x = {zero, zero, zero};
        lis.x = x;

        assertFalse(lis.hasValidX());
    }

    // ********************************
    // transform()
    // ********************************

    // when:
    // expect: transform
    @Test
    public void testTransform() {
        long[][][] elem = {
                { { 1 }, { 1 } },
                { { 2 }, { -1 } },
                { { -1 }, { 2 } }
        };
        Matrix a = Matrix.arrayReader(elem);
        Rational[] b = {new Rational(2), zero, one};
        int[] c = {1, 1, 1};
        LIS lis = new LIS(a, b, c);
        lis.transform();

        assertEquals(lis.aRow, lis.d.nRow);
        assertEquals(lis.aRow + lis.aCol, lis.d.nCol);
        assertTrue(lis.d.isLeftIdentity());
        assertNotNull(lis.h);
        assertEquals(lis.aRow + lis.aCol, lis.x.length);

        for (Rational r : lis.x) {
            assertNotNull(r);
        }

        Rational[] expectLb =
                {negativeInfinity, negativeInfinity, new Rational(2), zero, one};
        Rational[] expectUb =
                {positiveInfinity, positiveInfinity, positiveInfinity, positiveInfinity, positiveInfinity};

        assertArrayEquals(expectLb, lis.lb);
        assertArrayEquals(expectUb, lis.ub);
    }


    // ********************************
    // makeHLES()
    // ********************************

    // when:
    // expect: create new HLES
    @Test
    public void testMakeHLES() {
        long[][][] elem = {
                { { 1 }, { 1 } },
                { { 2 }, { -1 } },
                { { -1 }, { 2 } }
        };
        Matrix a = Matrix.arrayReader(elem);
        Rational[] b = {new Rational(2), zero, one};
        int[] c = {1, 1, 1};
        LIS lis = new LIS(a, b, c);
        lis.makeHLES();

        assertEquals(lis.aRow, lis.d.nRow);
        assertEquals(lis.aRow + lis.aCol, lis.d.nCol);
        assertTrue(lis.d.isLeftIdentity());
        assertNotNull(lis.h);
        assertEquals(lis.aRow + lis.aCol, lis.x.length);

        for (Rational r : lis.x) {
            assertNotNull(r);
        }
    }

    // ********************************
    // makeRestriction()
    // ********************************

    // when:
    // expect: set lb and ub
    @Test
    public void testMakeRestriction() {
        long[][][] aElem = {
                { { 1 }, { 1 } },
                { { 2 }, { -1 } },
                { { -1 }, { 2 } }
        };
        Matrix a = Matrix.arrayReader(aElem);
        Rational[] b = {new Rational(2), zero, one};
        int[] c = {1, 1, 1};
        LIS lis = new LIS(a, b, c);
        long[][][] dElem = {
                { { -1 }, { 0 }, { 0 }, { 1 }, { 1 } },
                { { 0 }, { -1 }, { 0 }, { 2 }, { -1 } },
                { { 0 }, { 0 }, { -1 }, { -1 }, { 2 } }
        };
        lis.d = Matrix.arrayReader(dElem);
        int[] p = { 2, 3, 4, 0, 1 };
        lis.d.p = p;
        lis.makeRestriction();

        Rational[] expectLb =
                {negativeInfinity, negativeInfinity, new Rational(2), zero, one};
        Rational[] expectUb =
                {positiveInfinity, positiveInfinity, positiveInfinity, positiveInfinity, positiveInfinity};

        assertArrayEquals(expectLb, lis.lb);
        assertArrayEquals(expectUb, lis.ub);
    }

    // ********************************
    // findBasicVar()
    // ********************************

    // when:   all basic vars are valid
    // expect: -1
    @Test
    public void testFindBasicVar_valid() {
        long[][][] aElem = {
                { { 1 }, { 1 } },
                { { 2 }, { -1 } },
                { { -1 }, { 2 } }
        };
        Matrix a = Matrix.arrayReader(aElem);
        Rational[] b = {new Rational(2), zero, one};
        int[] c = {1, 1, 1};
        LIS lis = new LIS(a, b, c);
        long[][][] dElem = {
                { { -1 }, { 0 }, { 0 }, { 2, 3 }, { -1, 3 } },
                { { 0 }, { -1 }, { 0 }, { 1 }, { -1 } },
                { { 0 }, { 0 }, { -1 }, { 1, 3 }, { 1, 3 } }
        };
        lis.d = Matrix.arrayReader(dElem);
        int[] p = { 0, 3, 1, 2, 4 };
        lis.d.p = p;

        Rational[] x = {one, one, one, new Rational(2), one};
        Rational[] lb = {negativeInfinity, negativeInfinity, new Rational(2), zero, one};
        Rational[] ub = {positiveInfinity, positiveInfinity, positiveInfinity, positiveInfinity, positiveInfinity};
        lis.x = x;
        lis.lb = lb;
        lis.ub = ub;

        int result = lis.findBasicVar();

        assertEquals(-1, result);
    }

    // when:   basic var is less than lower limit
    // expect: column number of invalid var
    @Test
    public void testFindBasicVar_less() {
        long[][][] aElem = {
                { { 1 }, { 1 } },
                { { 2 }, { -1 } },
                { { -1 }, { 2 } }
        };
        Matrix a = Matrix.arrayReader(aElem);
        Rational[] b = {new Rational(2), zero, one};
        int[] c = {1, 1, 1};
        LIS lis = new LIS(a, b, c);
        long[][][] dElem = {
                { { -1 }, { 0 }, { 0 }, { 1 }, { 1 } },
                { { 0 }, { -1 }, { 0 }, { 2 }, { -1 } },
                { { 0 }, { 0 }, { -1 }, { -1 }, { 2 } }
        };
        lis.d = Matrix.arrayReader(dElem);
        int[] p = { 2, 3, 4, 0, 1 };
        lis.d.p = p;

        Rational[] x = {zero, zero, zero, zero, zero};
        Rational[] lb = {negativeInfinity, negativeInfinity, new Rational(2), zero, one};
        Rational[] ub = {positiveInfinity, positiveInfinity, positiveInfinity, positiveInfinity, positiveInfinity};
        lis.x = x;
        lis.lb = lb;
        lis.ub = ub;

        int result = lis.findBasicVar();

        assertEquals(0, result);
        assertEquals(1, lis.bvIncDec);
        assertEquals(new Rational(2), lis.x[2]);
    }

    // when:   basic var is more than upper limit
    // expect: column number of invalid var
    @Test
    public void testFindBasicVar_more() {
        long[][][] aElem = {
                { { 1 }, { 1 } },
                { { 2 }, { -1 } },
                { { -1 }, { 2 } }
        };
        Matrix a = Matrix.arrayReader(aElem);
        Rational[] b = {new Rational(2), zero, one};
        int[] c = {1, 1, 1};
        LIS lis = new LIS(a, b, c);
        long[][][] dElem = {
                { { -1 }, { 0 }, { 0 }, { 1 }, { 1 } },
                { { 0 }, { -1 }, { 0 }, { 2 }, { -1 } },
                { { 0 }, { 0 }, { -1 }, { -1 }, { 2 } }
        };
        lis.d = Matrix.arrayReader(dElem);
        int[] p = { 2, 3, 4, 0, 1 };
        lis.d.p = p;

        Rational[] x = {zero, zero, zero, zero, zero};
        Rational[] lb = {negativeInfinity, negativeInfinity, negativeInfinity, zero, one};
        Rational[] ub = {positiveInfinity, positiveInfinity, minusOne, positiveInfinity, positiveInfinity};
        lis.x = x;
        lis.lb = lb;
        lis.ub = ub;

        int result = lis.findBasicVar();

        assertEquals(0, result);
        assertEquals(-1, lis.bvIncDec);
        assertEquals(minusOne, lis.x[2]);
    }

    // ********************************
    // findNonBasicVar()
    // ********************************

    // when:   all non basic vars are valid
    // expect: -1
    @Test
    public void testFindNonBasicVar_valid() {
        long[][][] aElem = {
                { { 1 }, { 1 } },
                { { 2 }, { -1 } },
                { { -1 }, { 2 } }
        };
        Matrix a = Matrix.arrayReader(aElem);
        Rational[] b = {new Rational(2), zero, one};
        int[] c = {1, 1, 1};
        LIS lis = new LIS(a, b, c);
        long[][][] dElem = {
                { { -1 }, { 0 }, { 0 }, { 2, 3 }, { -1, 3 } },
                { { 0 }, { -1 }, { 0 }, { 1 }, { -1 } },
                { { 0 }, { 0 }, { -1 }, { 1, 3 }, { 1, 3 } }
        };
        lis.d = Matrix.arrayReader(dElem);
        int[] p = { 0, 3, 1, 2, 4 };
        lis.d.p = p;

        Rational[] x = {one, one, one, new Rational(2), one};
        Rational[] lb = {negativeInfinity, negativeInfinity, new Rational(2), zero, one};
        Rational[] ub = {positiveInfinity, positiveInfinity, positiveInfinity, positiveInfinity, positiveInfinity};
        lis.x = x;
        lis.lb = lb;
        lis.ub = ub;

        int result = lis.findNonBasicVar(2);

        assertEquals(-1, result);
    }

    // when:   bvIncDec is INCREASE
    // expect: column number of invalid var
    @Test
    public void testFindNonBasicVar_increase() {
        long[][][] aElem = {
                { { 1 }, { 1 } },
                { { 2 }, { -1 } },
                { { -1 }, { 2 } }
        };
        Matrix a = Matrix.arrayReader(aElem);
        Rational[] b = {new Rational(2), zero, one};
        int[] c = {1, 1, 1};
        LIS lis = new LIS(a, b, c);
        long[][][] dElem = {
                { { -1 }, { 0 }, { 0 }, { 1 }, { 1 } },
                { { 0 }, { -1 }, { 0 }, { 2 }, { -1 } },
                { { 0 }, { 0 }, { -1 }, { -1 }, { 2 } }
        };
        lis.d = Matrix.arrayReader(dElem);
        int[] p = { 2, 3, 4, 0, 1 };
        lis.d.p = p;

        Rational[] x = {zero, zero, zero, zero, zero};
        Rational[] lb = {negativeInfinity, negativeInfinity, new Rational(2), zero, one};
        Rational[] ub = {positiveInfinity, positiveInfinity, positiveInfinity, positiveInfinity, positiveInfinity};
        lis.x = x;
        lis.lb = lb;
        lis.ub = ub;
        lis.bvIncDec = 1;

        int result = lis.findNonBasicVar(0);

        assertEquals(3, result);
        assertEquals(1, lis.nbvIncDec);

        result = lis.findNonBasicVar(2);

        assertEquals(3, result);
        assertEquals(-1, lis.nbvIncDec);
    }

    // when:   bvIncDec is DECREASE
    // expect: column number of invalid var
    @Test
    public void testFindNonBasicVar_decrease() {
        long[][][] aElem = {
                { { 1 }, { 1 } },
                { { 2 }, { -1 } },
                { { -1 }, { 2 } }
        };
        Matrix a = Matrix.arrayReader(aElem);
        Rational[] b = {new Rational(2), zero, one};
        int[] c = {1, 1, 1};
        LIS lis = new LIS(a, b, c);
        long[][][] dElem = {
                { { -1 }, { 0 }, { 0 }, { 1 }, { 1 } },
                { { 0 }, { -1 }, { 0 }, { 2 }, { -1 } },
                { { 0 }, { 0 }, { -1 }, { -1 }, { 2 } }
        };
        lis.d = Matrix.arrayReader(dElem);
        int[] p = { 2, 3, 4, 0, 1 };
        lis.d.p = p;

        Rational[] x = {zero, zero, zero, zero, zero};
        Rational[] lb = {negativeInfinity, negativeInfinity, new Rational(2), zero, one};
        Rational[] ub = {positiveInfinity, positiveInfinity, positiveInfinity, positiveInfinity, positiveInfinity};
        lis.x = x;
        lis.lb = lb;
        lis.ub = ub;
        lis.bvIncDec = -1;

        int result = lis.findNonBasicVar(2);

        assertEquals(3, result);
        assertEquals(1, lis.nbvIncDec);

        result = lis.findNonBasicVar(0);

        assertEquals(3, result);
        assertEquals(-1, lis.nbvIncDec);
    }

    // ********************************
    // solve()
    // ********************************

    // when:   S has result
    // expect: 1
    @Test
    public void testSolve_haveResult() {
        long[][][] aElem = {
                { { 1 }, { 1 } },
                { { 2 }, { -1 } },
                { { -1 }, { 2 } }
        };
        Matrix a = Matrix.arrayReader(aElem);
        Rational[] b = {new Rational(2), zero, one};
        int[] c = {1, 1, 1};
        LIS lis = new LIS(a, b, c);
        lis.transform();

        int result = lis.solve();

        assertEquals(1, result);
        assertTrue(lis.hasValidX());
    }

    // when:   S does not have result
    // expect: 0
    @Test
    public void testSolve_notHaveResult() {
        long[][][] elem = {
                { { 1 }, { 1 }, { -1 } },
                { { 1 }, { -1 }, { 1 } },
                { { -1 }, { 1 }, { 1 } },
                { { 1 }, { 0 }, { 0 } }
        };
        Matrix a = Matrix.arrayReader(elem);
        Rational[] b = {one, one, one, zero};
        int[] c = {1, 1, 1, 2};
        LIS lis = new LIS(a, b, c);
        lis.transform();

        int result = lis.solve();

        assertEquals(0, result);
    }
}
