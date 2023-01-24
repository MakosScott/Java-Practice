/**
 * This class enables the calculation and sorting of information gain values
 * 
 * @author Mehrdad Sabetzadeh, University of Ottawa
 * @author Guy-Vincent Jourdan, University of Ottawa
 *
 */
public class InformationGainCalculator {

	/**
	 * @param dataset is the dataset whose attributes we want to analyze and sort
	 *                according to information gain
	 * @return an array of GainInfoItem instances sorted in descending order of gain
	 *         value
	 */
	public static GainInfoItem[] calculateAndSortInformationGains(VirtualDataSet dataset) {
		// WRITE YOUR CODE HERE!
		//First get the entropy of the dataset
		DataSet[] fullDS = new DataSet[1];
		fullDS[0] = dataset;
		double fullSetEntropy = EntropyEvaluator.evaluate(fullDS);

		//Second, get entropies after splits
		GainInfoItem[] items = new GainInfoItem[dataset.numAttributes-1];
		String splitAt = "";

		for (int i=0; i < dataset.numAttributes-1; i ++) {

			double avgEntropy = 10000.00;
			splitAt = "";

			if (dataset.getAttribute(i).getType() == AttributeType.NOMINAL) {
				avgEntropy = EntropyEvaluator.evaluate(dataset.partitionByNominallAttribute(i));
			} else {
				String[] temp = dataset.getAttribute(i).getValues();
				for (int j=0; j < temp.length; j++) {
					double tempEntropy = EntropyEvaluator.evaluate(dataset.partitionByNumericAttribute(i, j));

					if (tempEntropy < avgEntropy) {
						avgEntropy = tempEntropy;
						splitAt = dataset.getValueAt(j, i);
					}
				}				
			}

			items[i] = new GainInfoItem(dataset.getAttribute(i).getName(), dataset.getAttribute(i).getType(), fullSetEntropy - avgEntropy, splitAt);
		}
				
		GainInfoItem.reverseSort(items);
		return items;
	}

	public static void main(String[] args) throws Exception {

		StudentInfo.display();

		if (args == null || args.length == 0) {
			System.out.println("Expected a file name as argument!");
			System.out.println("Usage: java InformationGainCalculator <file name>");
			return;
		}

		String strFilename = args[0];

		ActualDataSet actual = new ActualDataSet(new CSVReader(strFilename));

		// System.out.println(actual);

		VirtualDataSet virtual = actual.toVirtual();

		// System.out.println(virtual);

		GainInfoItem[] items = calculateAndSortInformationGains(virtual);

		// Print out the output
		System.out.println(
				" *** items represent (attribute name, information gain) in descending order of gain value ***");
		System.out.println();

		for (int i = 0; i < items.length; i++) {
			System.out.println(items[i]);
		}
	}
}
