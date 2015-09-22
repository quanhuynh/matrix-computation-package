# matrix-computation-package
Linear algebra package made by Java for Java. It includes elementary operations such as addition, multiplication as well as more advanced operations, from determinants, inverses, to solving matrix equations. A full list of supported methods and use can be found below. 

<hr>

### Documentation
#### Constructors
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
#### Elementary Operations
######Matrix add(Matrix m)
&nbsp;&nbsp;Returns new Matrix that is result of adding self Matrix to specified Matrix m
######Matrix multiply(double c)
&nbsp;&nbsp;Returns new Matrix that is result of scaling self Matrix by a constant c
######double trace()
&nbsp;&nbsp;Returns the trace (sum of diagonal entries) of the Matrix
#### More Operations
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
#### Miscellaneous
