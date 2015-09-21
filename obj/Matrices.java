package obj;

public class Matrices {
	
	/**
	 * Fill a matrix with a specified value
	 * @param m Matrix to be filled
	 * @param val Value to fill into matrix
	 */
	public static void fill(Matrix m, double val) {
		int rows = m.row();
		int columns = m.column();
		for (int i=0; i<rows; i++) {
			for (int j=0; j<columns; j++) {
				m.setEntry(i, j, val);
			}
		}
	}
	
	
	
}
