# matrix-computation-package
Linear algebra package made by Java for Java. It includes elementary operations such as addition, multiplication as well as more advanced operations, from determinants, inverses, to solving matrix equations. A full list of supported methods and use can be found below. 
Note: Row and column indexing starts at 1 for all public methods

To use the package, simply download and add to project as an external .jar file
[Download here] (https://www.dropbox.com/s/h78or0zgbck82yg/Matrix.jar?dl=0)

<hr>

### Documentation
#### Supported Methods
##### Constructors
######Matrix(double[][] array)
&nbsp;&nbsp;Build a Matrix object from a double[][] array
######Matrix(double[] array)
&nbsp;&nbsp;Build a Matrix object from a one-dimensional (one row) array
######Matrix(int m, int n)
&nbsp;&nbsp;Build a mxn empty Matrix
######Matrix(int m, int n, double val)
&nbsp;&nbsp;Build a mxn Matrix of value val
#### Boolean checks
######boolean isSquare()
&nbsp;&nbsp;Returns true if Matrix represents a square matrix
######boolean invertible()
&nbsp;&nbsp;Returns true if Matrix represents an invertible matrix
######boolean equals(Object obj)
&nbsp;&nbsp;Returns true if Matrix is equal to another Matrix object
##### Elementary Operations
######Matrix add(Matrix m)
&nbsp;&nbsp;Returns new Matrix that is result of adding self Matrix to specified Matrix m
######Matrix multiply(double c)
&nbsp;&nbsp;Returns new Matrix that is result of scaling self Matrix by a constant c
######double trace()
&nbsp;&nbsp;Returns the trace (sum of diagonal entries) of the Matrix
######void swapRows(int m1, int m2)
&nbsp;&nbsp;Swap two rows m1 and m2
######void scaleRow(int m, double c)
&nbsp;&nbsp;Scale row m by constant c
######void scaleColumn(int n, double c)
&nbsp;&nbsp;Scale column n by constant c
######void scaleAddRows(int m1, int m2, int c)
&nbsp;&nbsp;Scale row m1 by c then add to row m2
##### More Operations
######Matrix transpose()
&nbsp;&nbsp;Returns new Matrix that is result of transposing self Matrix
######Matrix multiply(Matrix m)
&nbsp;&nbsp;Returns new Matrix that is result of multiplying self Matrix with specified Matrix m
######double det()
&nbsp;&nbsp;Returns determinant of the Matrix
######Matrix minor(int m, int n)
&nbsp;&nbsp;Returns new Matrix that is the minor of self Matrix by removing mth row and nth column
######Matrix inverse()
&nbsp;&nbsp;Returns new Matrix that is the inverse of self Matrix
######void echelonForm()
&nbsp;&nbsp;Reduces the Matrix into echelon form (this method is destructive)
######void reducedEchelonForm()
&nbsp;&nbsp;Reduces the Matrix into row reduced echelon form (this method is destructive)
##### Miscellaneous
######Matrix random(int m, int n)
&nbsp;&nbsp;Returns an mxn matrix with random entries
######Matrix clone()
&nbsp;&nbsp;Clone the matrix into a new Matrix object
######double get(int m, int n)
&nbsp;&nbsp;Get an entry at row m, column n
######void setEntry(int m, int n, double c)
&nbsp;&nbsp;Set entry at (m, n) to be c
######int row()
&nbsp;&nbsp;Get number of rows
######int column()
&nbsp;&nbsp;Get number of columns
######int hashCode()
&nbsp;&nbsp;Returns hash code of Matrix object
######String toString()
&nbsp;&nbsp;Returns String representation of Matrix object
######void print()
&nbsp;&nbsp;Print Matrix to System.out



