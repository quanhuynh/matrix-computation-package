


import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Matrix {

	private double[][] matrix;
	private int columns, rows;

	/**
	 * Constructs a matrix based on an input array
	 * @param array Array to copy
	 */
	public Matrix(double[][] array) {
		if (array == null) {
			System.err.println("ERR: Null array. Cannot construct.");
			return;
		}
		rows = array.length;
		columns = array[0].length;
		matrix = new double[rows][columns];
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				matrix[i][j] = array[i][j];
			}
		}
	}

	/**
	 * Creates a one dimensional matrix (first row) from a one dimensional array
	 * @param array Array to copy
	 */
	public Matrix(double[] array) {
		if (array == null) {
			System.err.println("ERR: Null array. Cannot construct.");
			return;
		}
		rows = 1;
		columns = array.length;
		matrix = new double[rows][columns];
		for (int j=0; j<columns; j++) {
			matrix[0][j] = array[j];
		}
	}

	/**
	 * Constructs an m x n matrix of zeroes
	 * @param m rows
	 * @param n columns
	 */
	public Matrix(int m, int n) {
		if (m < 0 || n < 0) {
			System.err.println("ERR: Invalid dimensions. Cannot construct.");
			return;
		}
		rows = m;
		columns = n;
		matrix = new double[rows][columns];
	}

	/**
	 * Constructs an m x n matrix of value val
	 * @param m Rows
	 * @param n Columns
	 * @param val Constant value
	 */
	public Matrix(int m, int n, double val) {
		if (m < 0 || n < 0) {
			System.err.println("ERR: Invalid dimensions. Cannot construct.");
		}
		rows = m;
		columns = n;
		matrix = new double[rows][columns];
		for (int i=0; i<rows; i++) {
			Arrays.fill(matrix[i], val);
		}
	}

	/**
	 * Returns a random matrix of m rows and n columns
	 * @param m Row
	 * @param n Column
	 * @return Matrix object with random entries
	 */
	public static Matrix random(int m, int n) {
		Matrix res = new Matrix(m, n);
		for (int i=0; i<res.rows; i++) {
			for (int j=0; j<res.columns; j++) {
				res.matrix[i][j] = Math.random()*50 + 1;
			}
		}
		return res;
	}

	/**
	 * Checks if matrix is a n x n matrix
	 * @return true if matrix is square
	 */
	public boolean isSquare() {
		return columns == rows;
	}

	/**
	 * Checks if matrix is invertible
	 * @return true if matrix is invertible
	 */
	public boolean invertible() {
		return rows == columns && det() != 0;
	}

	/**
	 * Checks equality between two matrices
	 * @return true if all entries are equal
	 */
	@Override
	public boolean equals(Object obj) {
		Matrix m = (Matrix) obj;
		if (rows != m.rows || columns != m.columns) {
			return false;
		}
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				if (matrix[i][j] != m.matrix[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Calculates the trace of the matrix
	 * Trace is the sum of the main diagonal
	 * @return trace of matrix
	 */
	public double trace() {
		if (!isSquare()) {
			System.err.println("ERR: Matrix is not a square");
			return Double.MIN_VALUE;
		}
		double trace = 0;
		for (int i=0; i<rows; i++) {
			trace += matrix[i][i];
		}
		return trace;
	}

	/**
	 * Transpose the matrix
	 * @return New Matrix object result
	 */
	public Matrix transpose() {
		Matrix res = new Matrix(columns, rows);
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				res.matrix[j][i] = matrix[i][j];
			}
		}
		return res;
	}

	/**
	 * Adds two matrices with the same dimensions
	 * @param m Matrix to add
	 * @return New Matrix object result
	 */
	public Matrix add(Matrix m) {
		if (columns != m.columns || rows != m.rows) {
			System.err.println("ERR: Cannot add matrices.");
			return null;
		}
		Matrix res = new Matrix(rows, columns);
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				res.matrix[i][j] = matrix[i][j] + m.matrix[i][j];
			}
		}
		return res;

	}

	/**
	 * Multiply two matrices
	 * @param m matrix to multiply with self
	 * @return New Matrix object result
	 */
	public Matrix multiply(Matrix m) {
		if (columns != m.rows) {
			System.err.println("ERR: Cannot multiply matrices.");
			return null;
		}
		Matrix res = new Matrix(rows, m.columns);
		for (int i=0; i<res.rows; i++) {
			for (int j=0; j<res.columns; j++) {
				res.matrix[i][j] = dotProduct(matrix[i], linearizeColumn(m, j));
			}
		}
		return res;
	}

	/**
	 * Multiply this matrix by a constant
	 * @param c Constant to multiply
	 * @return New Matrix object result
	 */
	public Matrix multiply(double c) {
		if (c == 0) {
			return new Matrix(rows, columns);
		}
		Matrix res = new Matrix(rows, columns);
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				res.matrix[i][j] = matrix[i][j]*c;
			}
		}
		return res;
	}

	/**
	 * Get a sub-matrix by removing a row and a column
	 * @param m Row to remove (starts at 1)
	 * @param n Column to remove (starts at 1)
	 * @return New Matrix object result
	 */
	public Matrix minor(int m, int n) {
		if (m <= 0 || n <= 0 || m > rows || n > columns) {
			System.err.println("ERR: Invalid position. Cannot get minor.");
			return null;
		}
		Matrix res = new Matrix(rows-1, columns-1);
		res.matrix = minorArray(matrix, m-1, n-1);
		return res;
	}

	/**
	 * Get the inverse of this matrix
	 * @return Inverse matrix A^-1
	 */
	public Matrix inverse() {
		if (rows != columns) {
			System.err.println("ERR: Non-square matrix. Cannot get inverse.");
			return null;
		}
		Matrix res = new Matrix(rows, columns);
		//Adjugate
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				res.matrix[i][j] = minor(i+1, j+1).det()*(Math.pow(-1, i+j));
			}
		}
		res = res.transpose();

		//Inverse = (1/det)(adj)
		res = res.multiply(1/det());

		return res;
	}

	/** Clone this matrix into a new object
	 * @return New Matrix object result
	 */
	public Matrix clone() {
		Matrix res = new Matrix(rows, columns);
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				res.matrix[i][j] = matrix[i][j];
			}
		}
		return res;
	}

	/**
	 * Return the echelon form of the Matrix; this method is destructive
	 */
	public void echelonForm() {
		int d; //diagonal boundary
		if (rows > columns) {
			d = columns;
		} else {
			d = rows;
		}
		for (int i=0; i<d; i++) {
			//find pivot row-column pair for submatrix(i, i) where i starts at 0
			RowColumnPair pivotRC = findPivotPos(i);

			if (pivotRC.row != i+1) {
				//if pivot row is not first row in submatrix, swap
				swapRows(pivotRC.row, i+1);
			}
			
			//Divide across row for leading entry to be one
			double pivotEntry = matrix[i][pivotRC.column-1];
			for (int x=i; x<columns; x++) {
				matrix[i][x] /= pivotEntry;
			}
			
			//Create zeroes underneath pivot row
			for (int y=i+2; y<=rows; y++) {
				double scaleFactor = matrix[y-1][pivotRC.column-1];
				if (scaleFactor == 0) {
					continue;
				}
				scaleAddRows(i+1, y, -scaleFactor);
			}
		}

	}
	
	/**
	 * Returns the reducedEchelonForm of this matrix; this method is destructive
	 */
	public void reducedEchelonForm() {
		echelonForm();
		Stack<RowColumnPair> pivots = new Stack<RowColumnPair>();
		int d;
		if (rows > columns) {
			d = columns;
		} else {
			d = rows;
		}
		for (int i=0; i<d; i++) {
			RowColumnPair rcPair = findPivotPos(i);
			pivots.push(rcPair);
		}
		//pop each pivot off the stack (last first)
			//for each, subtract
		try {
			RowColumnPair curPair = pivots.pop();
			while (true) {
				int pivotM = curPair.row;
				int pivotN = curPair.column;
				for (int i=pivotM-2; i>=0; i--) {
					double scaleFactor = matrix[i][pivotN-1];
					if (scaleFactor == 0) {
						continue;
					}
					scaleAddRows(pivotM, i+1, -scaleFactor);
				}
				curPair = pivots.pop();
			}
		} catch (EmptyStackException e) {
			
		}
	}
	
	/**
	 * Returns the number of pivot positions in this matrix
	 * @return number of pivot positions
	 */
	public int pivots() {
		int d;
		Set<RowColumnPair> pivots = new HashSet<RowColumnPair>();
		if (rows > columns) {
			d = columns;
		} else {
			d = rows;
		}
		
		for (int i=0; i<d; i++) {
			pivots.add(findPivotPos(i));
		}
		return pivots.size();
	}

	/**
	 * Find the row-column pair of the first pivot position of a submatrix(i, i)
	 * @param i Row and column of submatrix
	 * @return RowColumnPair object with row and column position of first pivot
	 */
	private RowColumnPair findPivotPos(int i) {
		for (int x=i; x<columns; x++) {
			for (int y=i; y<rows; y++) {
				if (matrix[y][x] != 0) {
					return new RowColumnPair(y+1, x+1);
				}
			}
		}
		System.err.println("ERR: Zero matrix.");
		return null;
	}

	/**
	 * Get determinant of matrix
	 * @return Double type determinant
	 */
	public double det() {
		if (rows != columns) {
			System.err.println("ERR: Non-square matrix. Cannot get determinant.");
			return Double.MIN_VALUE;
		}
		return arrayDet(matrix);
	}

	/**
	 * Helper function; returns determinant of array
	 * @param arr double[][] array
	 * @return Double type determinant
	 */
	private double arrayDet(double[][] arr) {
		if (arr.length == 2 && arr[0].length == 2) {
			return arr[0][0]*arr[1][1] - arr[1][0]*arr[0][1];
		} else {
			double det = 0;
			for (int j=0; j<arr[0].length; j++) {
				det += arr[0][j]*arrayDet(minorArray(arr, 0, j))*(Math.pow(-1, j));
			}
			return det;
		}
	}

	/**
	 * Turn a matrix column into a flat array
	 * @param m Matrix
	 * @param columnIndex Index of column
	 * @return double[] array representing row
	 */
	private double[] linearizeColumn(Matrix m, int columnIndex) {
		double[] linearized = new double[m.matrix.length];
		for (int i=0; i<linearized.length; i++) {
			linearized[i] = m.matrix[i][columnIndex];
		}
		return linearized;
	}

	/**
	 * Helper function for minoring a double[][] array
	 * @param arr double[][] array
	 * @param m Row to remove
	 * @param n Column to remove
	 * @return double[][] array
	 */
	private double[][] minorArray(double[][] arr, int m, int n) {
		int rows = arr.length;
		int columns = arr[0].length;
		double[][] resArray = new double[rows-1][columns-1];
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				if (i<m && j<n) {
					resArray[i][j] = arr[i][j];
				} else if (i<m && j>n) {
					resArray[i][j-1] = arr[i][j];
				} else if (i>m && j<n) {
					resArray[i-1][j] = arr[i][j];
				} else if (i>m && j>n) {
					resArray[i-1][j-1] = arr[i][j];
				} else {
					//i==m-1 and j==n-1 -> removed row/column
				}
			}
		}
		return resArray;
	}

	/**
	 * Get an entry
	 * @param m Row (starts at 1)
	 * @param n Column (starts at 1)
	 * @return Double type value
	 */
	public double get(int m, int n) {
		if (m < 0 || n < 0 || m > rows || n > columns) {
			System.err.println("ERR: Invalid dimensions. Cannot get entry.");
		}
		return matrix[m-1][n-1];
	}

	/**
	 * Sum the products of corresponding entries in two arrays
	 * @param a First array
	 * @param b Second array
	 * @return Sum of products of corresponding arrays (dot product)
	 */
	private int dotProduct(double[] a, double[] b) {
		int prod = 0;
		for (int i=0; i<a.length; i++) {
			prod += a[i]*b[i];
		}
		return prod;
	}

	/**
	 * Getter method for number of rows
	 * @return number of rows in matrix
	 */
	public int row() {
		return rows;
	}

	/**
	 * Getter method for number of columns
	 * @return number of columns in matrix
	 */
	public int column() {
		return columns;
	}

	/**
	 * Returns hash code for matrix
	 * @return int hash code
	 */
	@Override
	public int hashCode() {
		int hash = rows * columns;
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				hash += 13 * ((matrix[i][j] * rows) / columns);
			}
		}
		return hash;
	}

	/**
	 * String representation of the matrix
	 */
	@Override
	public String toString() {
		String rep = "[";
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				if (j==0 && columns == 1) {
					rep += "[ " + matrix[i][j] + " ]";
				} else if (j==0) {
					rep += "[ " + matrix[i][j] + ", ";
				} else if (j==columns-1 && i==rows-1) {
					rep += matrix[i][j] + " ] ";
				} else if (j==columns-1) {
					rep += matrix[i][j] + " ], ";
				} else {
					rep += matrix[i][j] + ", ";
				}
			}
		}
		rep += "]";
		return rep;
	}

	/**
	 * Scale a row in the matrix by c
	 * @param row Row to scale
	 * @param c Scaling factor
	 */
	public void scaleRow(int row, double c) {
		if (row <= 0 || row > rows) {
			System.err.println("ERR: Invalid row. Cannot scale.");
			return;
		}
		for (int j=0; j<columns; j++) {
			matrix[row-1][j] *= c;
		}
	}

	/**
	 * Scale a column in the matrix by c
	 * @param column Column to scale
	 * @param c Scaling factor
	 */
	public void scaleColumn(int column, double c) {
		if (column <= 0 || column > columns) {
			System.err.println("ERR: Invalid column. Cannot scale.");
			return;
		}
		for (int i=0; i<rows; i++) {
			matrix[i][column-1] *= c;
		}
	}

	/**
	 * Set an entry val at mth row, nth column
	 * @param m Index of row (starts at 1)
	 * @param n Index of column (starts at 1)
	 * @param val Value to set
	 */
	public void setEntry(int m, int n, double val) {
		if (m < 0 || n < 0 || m > rows || n > columns) {
			System.err.println("ERR: Invalid location.");
			return;
		}
		matrix[m-1][n-1] = val;
	}

	/**
	 * Swaps two rows of the matrix
	 * @param m1 First row
	 * @param m2 Second row
	 */
	public void swapRows(int m1, int m2) {
		if (m1 == m2) {
			return;
		}
		double[] t = matrix[m1-1];
		matrix[m1-1] = matrix[m2-1];
		matrix[m2-1] = t;
	}

	/**
	 * Scales row m1 by c and add to row m2
	 * @param m1 Row to scale and add
	 * @param m2 Resulting row
	 * @param c  Scaling constant
	 */
	public void scaleAddRows(int m1, int m2, double c) {
		if (m1 == m2) {
			System.err.println("ERR: Rows are the same. Cannot scale-add.");
			return;
		} else if (m1 <= 0 || m2 <= 0) {
			System.err.println("ERR: Invalid row dimension. Cannot scale-add.");
			return;
		}
		for (int j=0; j<columns; j++) {
			double adder = matrix[m1-1][j]*c;
			matrix[m2-1][j] += adder;
		}
	}

	/**
	 * Print matrix to System.out
	 */
	public void print() {
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				if (j==columns-1) {
					System.out.printf("%.2f\n", matrix[i][j]);
				} else {
					System.out.printf("%.2f  ", matrix[i][j]);
				}
			}
		}
	}

	private class RowColumnPair {
		private int row;
		private int column;
		public RowColumnPair(int m, int n) {
			row = m;
			column = n;
		}
	}

}
