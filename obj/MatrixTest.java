package obj;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixTest {

	@Test
	public void basicOpsTest() {
		/**
		 * TRANSPOSE
		 */
		double[][] basicArray = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
		Matrix m = new Matrix(basicArray);
		Matrix t = m.transpose();
		double[][] tArray = {{1, 4, 7, 10}, {2, 5, 8, 11}, {3, 6, 9, 12}};
		assertTrue(t.equals(new Matrix(tArray)));
		
		double[][] oneElArr = {{4}};
		Matrix oneEl = new Matrix(oneElArr);
		assertTrue(oneEl.transpose().equals(oneEl));
		
		/**
		 * MULTIPLICATION
		 */
		Matrix scaledThree = m.multiply(3);
		double[][] scaledThreeArr = {{3, 6, 9}, {12, 15, 18}, {21, 24, 27}, {30, 33, 36}};
		assertTrue(scaledThree.equals(new Matrix(scaledThreeArr)));
		
		double[][] oneCol = {{1}, {1}, {1}};
		Matrix prod = m.multiply(new Matrix(oneCol));
		double[][] oneColProd = {{6}, {15}, {24}, {33}};
		assertTrue(prod.equals(new Matrix(oneColProd)));
		
		/**
		 * TOSTRING
		 */
		assertEquals(m.toString(), "[[ 1.0, 2.0, 3.0 ], [ 4.0, 5.0, 6.0 ], [ 7.0, 8.0, 9.0 ], [ 10.0, 11.0, 12.0 ] ]");
	
		
		
	}
	
	@Test
	public void intermediateOpsTest() {
		
		double[][] basicArray = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
		Matrix m = new Matrix(basicArray);
		
		/**
		 * MINOR ARRAY (2, 2)
		 */
		double[][] minorArr = {{1, 3}, {7, 9}, {10, 12}};
		assertTrue(m.minor(2, 2).equals(new Matrix(minorArr)));
		
		/**
		 * DETERMINANT AND INVERSE
		 */
		double[][] basicSquare = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		Matrix basicSquareMatrix = new Matrix(basicSquare);
		assertTrue(basicSquareMatrix.det() == 0);
		assertFalse(basicSquareMatrix.invertible()); //since det = 0;
		
		double[][] invertibleArr = {{7, 2, 1}, {0, 3, -1}, {-3, 4, -2}};
		Matrix invertibleMatrix = new Matrix(invertibleArr);
		double[][] inverse = {{-2, 8 ,-5}, {3, -11, 7}, {9, -34, 21}};
		assertTrue(invertibleMatrix.inverse().equals(new Matrix(inverse)));
	
		/**
		 * ECHELON FORM
		 */
		double[][] echelonRes1 = {{1,2,0}, {0,1,1.25}, {0,0,1}};
		Matrix echelonMatrix1 = new Matrix(echelonRes1);
		
		double[][] testArr = {{1,2,0}, {0,4,5}, {3,2,6}};
		Matrix testMatrix = new Matrix(testArr);
		testMatrix.echelonForm();
		
		double[][] testArr2 = {{0,4,5}, {1,2,0}, {3,2,6}};
		Matrix testMatrix2 = new Matrix(testArr2);
		testMatrix2.echelonForm();
		
		assertTrue(testMatrix.equals(echelonMatrix1));
		assertTrue(testMatrix2.equals(echelonMatrix1));
		
		double[][] identityArr = {{1,0,0}, {0,1,0}, {0,0,1}};
		Matrix identityMatrix = new Matrix(identityArr);
		testMatrix.reducedEchelonForm();
		assertTrue(testMatrix.equals(identityMatrix));
		
		double[][] moreRows = {{4, 5}, {2, 1}, {0, 0}};
		Matrix moreRowsMatrix = new Matrix(moreRows);
		double[][] goodMoreRows = {{1,0}, {0,1}, {0,0}};
		Matrix goodMoreRowsMatrix = new Matrix(goodMoreRows);
		moreRowsMatrix.reducedEchelonForm();
		assertTrue(moreRowsMatrix.equals(goodMoreRowsMatrix));
		
		double[][] moreCols = {{2,4,8}, {1,2,9}};
		Matrix moreColsMatrix = new Matrix(moreCols);
		double[][] goodMoreCols = {{1,2,0}, {0,0,1}};
		Matrix goodMoreColsMatrix = new Matrix(goodMoreCols);
		moreColsMatrix.reducedEchelonForm();
		assertTrue(moreColsMatrix.equals(goodMoreColsMatrix));
	}

}
