/**
 * This class enables calculating (weighted-average) entropy values for a set of
 * datasets
 * 
 * @author Mehrdad Sabetzadeh, University of Ottawa
 * @author Guy-Vincent Jourdan, University of Ottawa
 *
 */
public class EntropyEvaluator {

	/**
	 * A static method that calculates the weighted-average entropy of a given set
	 * (array) of datasets. The assignment description provides a detailed
	 * explanation of this calculation. In particular, note that all logarithms are
	 * to base 2. For your convenience, we provide a log2 method. You can use this
	 * method wherever you need to take logarithms in this assignment.
	 * 
	 * @param partitions is the array of datasets to compute the entropy of
	 * @return Shannon's logarithmic entropy (to base 2) for the partitions
	 */
	public static double evaluate(DataSet[] partitions) {
		// WRITE YOUR CODE HERE!
		double allRows = 0.0;
		double[] entropies = new double[partitions.length];

		for (int i=0; i < partitions.length; i++) {			
			double numRow = partitions[i].numRows;
			double yes = 0;
			allRows += numRow;

			for (int j=0; j < partitions[i].numRows; j ++) {

				String val = partitions[i].getValueAt(j, partitions[i].numAttributes-1);

				if (val.equals(partitions[i].getUniqueAttributeValues(partitions[i].numAttributes-1)[0])) {
					yes++;
				} 			
			}
			if (numRow == yes || yes == 0) {
				entropies[i] = 0;
			} else {
				entropies[i] = ((-1) * yes/ numRow) * EntropyEvaluator.log2(yes/numRow) - ((numRow-yes)/ numRow) * EntropyEvaluator.log2(((numRow-yes)/ numRow));
			}
		}

		double avgEntropy = 0;
		for (int i=0; i < entropies.length; i++) {
			avgEntropy += ((double)partitions[i].numRows)/allRows * entropies[i];
		}
		return avgEntropy;
	}

	/**
	 * Calculate base-2 logarithm for a given number
	 * 
	 * @param x is the number to take the logarithm of
	 * @return base-2 logarithm for x
	 */
	public static double log2(double x) {
		return (Math.log(x) / Math.log(2));
	}
}
