package obj;


import java.util.Arrays;

public class Matrix {
	
	private double[][] matrix;
	private int columns, rows;
	
	/**
	 * Constructs a matrix based on an input array
	 * @param array Array to copy
	 */
	public Matrix(double[][] array) {
		if (array == null) {
			System.err.println("ERR: Null array.");
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
			System.err.println("ERR: Null array.");
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
			System.err.println("ERR: Invalid dimensions.");
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
			System.err.println("ERR: Invalid dimensions.");
		}
		rows = m;
		columns = n;
		matrix = new double[rows][columns];
		for (int i=0; i<rows; i++) {
			Arrays.fill(matrix[i], val);
		}
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
			System.err.println("ERR: Cannot add these matrices.");
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
			System.err.println("ERR: Matrices cannot be multiplied.");
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
			System.err.println("ERR: Invalid position.");
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
			System.err.println("ERR: Non-square matrix.");
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
	 * Get determinant of matrix
	 * @return Double type determinant
	 */
	public double det() {
		if (rows != columns) {
			System.err.println("ERR: Non-square matrix.");
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
	public void scaleRow(int row, int c) {
		if (row <= 0 || row > rows) {
			System.err.println("ERR: Invalid row.");
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
	public void scaleColumn(int column, int c) {
		if (column <= 0 || column > columns) {
			System.err.println("ERR: Invalid column.");
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
		if (m <= 0 || n <= 0 || m > rows || n > columns) {
			System.err.println("ERR: Invalid location.");
			return;
		}
		matrix[m-1][n-1] = val;
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
	
	public static void main(String[] args) {
		double[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
		Matrix m = new Matrix(array);
		System.out.println("Base matrix:");
		m.print();
		System.out.println();
		
		System.out.println("Transpose:");
		Matrix t = m.transpose();
		t.print();
		System.out.println();
		
		Matrix mTimesThree = m.multiply(3);
		System.out.println("Scaled by factor of 3:");
		mTimesThree.print();
		System.out.println();
		
		double[][] oneColArray = {{1}, {1}, {1}};
		Matrix oneCol = new Matrix(oneColArray);
		Matrix p = m.multiply(oneCol);
		System.out.println("Multiplied with " + oneCol.toString());
		p.print();
		System.out.println();
		
		System.out.println("toString() Representation:");
		System.out.println(m.toString());
		System.out.println();
		
		Matrix subM = m.minor(2, 2);
		System.out.println("Minor(2, 2):");
		subM.print();
		System.out.println();
		
		double[][] squareArr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		Matrix squareMat = new Matrix(squareArr);
		System.out.println("Determinant of " + squareMat.toString());
		System.out.println(squareMat.det());
		System.out.println();
		
		double[][] squareArr2 = {{7, 2, 1}, {0, 3, -1}, {-3, 4, -2}};
		Matrix squareMat2 = new Matrix(squareArr2);
		Matrix inverse = squareMat2.inverse();
		System.out.println("Inverse of " + squareMat2.toString());
		inverse.print();
		System.out.println();
	}
}
