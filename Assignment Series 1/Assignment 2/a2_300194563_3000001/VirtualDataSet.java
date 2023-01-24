// You are allowed to use LinkedList or other Collection classes in A2 and A3
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.management.AttributeValueExp;

/**
 * This class is used for representing a virtual dataset, that is, a dataset
 * that is a view over an actual dataset. A virtual dataset has no data matrix
 * of its own.
 * 
 * @author Mehrdad Sabetzadeh, University of Ottawa
 * @author Guy-Vincent Jourdan, University of Ottawa
 *
 */
public class VirtualDataSet extends DataSet {

	/**
	 * reference to the source dataset (instance of ActualDataSet)
	 */
	private ActualDataSet source;

	/**
	 * array of integers mapping the rows of this virtual dataset to the rows of its
	 * source (actual) dataset
	 */
	private int[] map;

	
	/**
	 * Constructor for VirtualDataSet. There are two important considerations here:
	 * (1) Make sure that you keep COPIES of the "rows" and "attributes" passed as
	 * formal parameters. Do not, for example, say this.map = rows. Instead, create
	 * a copy of rows before assigning that copy to this.map. (2) Prune the value
	 * sets of the attributes. Since a virtual dataset is only a subset of an actual
	 * dataset, it is likely that some or all of its attributes may have smaller
	 * value sets.
	 * 
	 * @param source     is the source dataset (always an instance of ActualDataSet)
	 * @param rows       is the set of rows from the source dataset that belong to
	 *                   this virtual dataset
	 * @param attributes is the set of attributes belonging to this virtual dataset.
	 *                   IMPORTANT: you need to recalculate the unique value sets
	 *                   for these attributes according to the rows. Why? Because
	 *                   this virtual set is only a subset of the source dataset and
	 *                   its attributes potentially have fewer unique values.
	 */
	public VirtualDataSet(ActualDataSet source, int[] rows, Attribute[] attributes) {
		
		
		int [] copiedRows = new int[rows.length];		
		
		for (int i=0; i < rows.length; i++) {
			copiedRows[i] = rows[i];
		}
		this.source = source;
		this.map = copiedRows;
		
		super.numAttributes = attributes.length;
		super.numRows = rows.length; 

		Attribute [] updatedAttributes = new Attribute [attributes.length];
		super.attributes = attributes;		

		for (int h=0; h < attributes.length; h++) {
			Attribute tempAttr = attributes[h].clone();
			updatedAttributes[h] = tempAttr;
			String [] tempValues = getUniqueAttributeValues(h);
			updatedAttributes[h].replaceValues(tempValues);
			this.attributes[h] = updatedAttributes[h];
		}
		super.attributes = updatedAttributes;		
	}

	/**
	 * String representation of the virtual dataset.
	 */
	public String toString() {
		// WRITE YOUR CODE HERE!
		String data = "Virtual dataset with " + attributes.length + " attribute(s) and " + this.map.length + " row(s)\n";
		String source = " - Dataset is a view over " + this.source.getSourceId() + "\n";
		String indices = " - Row indices in this dataset (w.r.t. its source dataset): " + Util.intArrayToString(this.map) + "\n";

		String newString = "";
		newString += data + source + indices + super.toString();

		return newString;
	}

	/**
	 * Implementation of DataSet's getValueAt abstract method for virtual datasets.
	 * Hint: You need to call source.getValueAt(...). What you need to figure out is
	 * with what parameter values that method needs to be called.
	 */
	public String getValueAt(int row, int attributeIndex) {
		// WRITE YOUR CODE HERE!

		int tempRow = map[row]; 
		int tempIndex = attributes[attributeIndex].getAbsoluteIndex();
		return source.getValueAt(tempRow, tempIndex);
	}

	/**
	 * @return reference to source dataset
	 */
	public ActualDataSet getSourceDataSet() {
		// WRITE YOUR CODE HERE!
		return this.source; 
	}


	/**
	 * This method splits the virtual dataset over a nominal attribute. This process
	 * has been discussed and exemplified in detail in the assignment description.
	 * 
	 * @param attributeIndex is the index of the nominal attribute over which we
	 *                       want to split.
	 * @return a set (array) of partitions resulting from the split. The partitions
	 *         will no longer contain the attribute over which we performed the
	 *         split.
	 */
	public VirtualDataSet[] partitionByNominallAttribute(int attributeIndex) {
		// WRITE YOUR CODE HERE!

		// Prepare arrays for attribute names and row indexes
		int uniqueSplitValuesSize = this.attributes[attributeIndex].getValues().length;

		String [] tempAttrs = new String [uniqueSplitValuesSize];
		LinkedList<Integer> [] tempIndexes = new LinkedList [uniqueSplitValuesSize];

		for (int i=0; i < uniqueSplitValuesSize; i++) {
			tempIndexes[i] = new LinkedList();
		}


		for (int i=0; i < uniqueSplitValuesSize; i++) {
			tempAttrs[i] = this.attributes[attributeIndex].getValues()[i];

			for (int ii=0; ii < this.numRows; ii++) {

				if(this.getValueAt(this.map[ii], attributeIndex).equals(tempAttrs[i])) {
					 tempIndexes[i].add(Integer.valueOf(ii));
				}
			}
		}

		VirtualDataSet [] tempVDS = new VirtualDataSet[uniqueSplitValuesSize];

		for (int i=0; i < uniqueSplitValuesSize; i++) {

			int [] tempRows =  new int [tempIndexes[i].size()];

			for (int x=0; x < tempIndexes[i].size(); x++) {
				tempRows[x] = tempIndexes[i].get(x).intValue();
			}


			Attribute[] newAttrs = new Attribute[this.attributes.length-1];
			int count = 0;

			for (int x=0; x < this.attributes.length; x++ ) {
				if (x != attributeIndex) {
					Attribute tempAttr = this.attributes[x].clone();
					Set<String> tempValues = new HashSet<>();

					for (int y=0; y < tempRows.length; y++) {
						tempValues.add(this.getValueAt(tempRows[y], x));
					}

					tempAttr.replaceValues(tempValues.toArray(new String [tempValues.size()]));
					newAttrs[count++] = tempAttr;

				}
				
			}
			tempVDS[i] = new VirtualDataSet(this.source, tempRows, newAttrs);

		}
		
		return tempVDS;
	}

	/**
	 * This method splits the virtual dataset over a given numeric attribute at a
	 * specific value from the value set of that attribute. This process has been
	 * discussed and exemplified in detail in the assignment description.
	 * 
	 * @param attributeIndex is the index of the numeric attribute over which we
	 *                       want to split.
	 * @param valueIndex     is the index of the value (in the value set of the
	 *                       attribute of interest) to use for splitting
	 * @return a pair of partitions (VirtualDataSet array of length two) resulting
	 *         from the two-way split. Note that the partitions will retain the
	 *         attribute over which we perform the split. This is in contrast to
	 *         splitting over a nominal, where the split attribute disappears from
	 *         the partitions.
	 */
	public VirtualDataSet[] partitionByNumericAttribute(int attributeIndex, int valueIndex) {
		// WRITE YOUR CODE HERE!

		LinkedList<Integer> smallerEqualIndexes = new LinkedList<>();
		LinkedList<Integer> greaterIndexes = new LinkedList<>();

		LinkedList<Integer> [] tempIndexes = new LinkedList[2];
		tempIndexes[0] = smallerEqualIndexes;
		tempIndexes[1] = greaterIndexes;

		int splitValue = Integer.valueOf( this.getValueAt(valueIndex, attributeIndex) );

		for (int i=0; i < this.numRows; i++) {
			
			int value = Integer.valueOf( this.getValueAt(this.map[i], attributeIndex) );

			if(value > splitValue) {
				greaterIndexes.add(Integer.valueOf(i));
			} else {
				smallerEqualIndexes.add(Integer.valueOf(i));
			}
		}

		VirtualDataSet [] tempVDS = new VirtualDataSet[2];

		for (int i=0; i < 2; i++) {

			int [] tempRows =  new int [tempIndexes[i].size()];

			for (int x=0; x < tempIndexes[i].size(); x++) {
				tempRows[x] = tempIndexes[i].get(x).intValue();
			}


			Attribute[] newAttrs = new Attribute[this.attributes.length];

			for (int x=0; x < this.attributes.length; x++ ) {			
				Attribute tempAttr = this.attributes[x].clone();
				Set<String> tempValues = new HashSet<>();

				for (int y=0; y < tempRows.length; y++) {
					tempValues.add(this.getValueAt(tempRows[y], x));
				}

				tempAttr.replaceValues(tempValues.toArray(new String [tempValues.size()]));
				newAttrs[x] = tempAttr;				
			}

			tempVDS[i] = new VirtualDataSet(this.source, tempRows, newAttrs);
		}
	
	return tempVDS;
	}

	public static void main(String[] args) throws Exception {

		StudentInfo.display();

		System.out.println("============================================");
		System.out.println("THE WEATHER-NOMINAL DATASET:");
		System.out.println();

		ActualDataSet figure5Actual = new ActualDataSet(new CSVReader("weather-nominal.csv"));

		System.out.println(figure5Actual);

		VirtualDataSet figure5Virtual = figure5Actual.toVirtual();

		System.out.println("JAVA IMPLEMENTATION OF THE SPLIT IN FIGURE 5:");
		System.out.println();

		VirtualDataSet[] figure5Partitions = figure5Virtual
				.partitionByNominallAttribute(figure5Virtual.getAttributeIndex("outlook"));

		for (int i = 0; i < figure5Partitions.length; i++)
			System.out.println("Partition " + i + ": " + figure5Partitions[i]);

		System.out.println("============================================");
		System.out.println("THE WEATHER-NUMERIC DATASET:");
		System.out.println();

		ActualDataSet figure9Actual = new ActualDataSet(new CSVReader("weather-numeric.csv"));

		System.out.println(figure9Actual);

		VirtualDataSet figure9Virtual = figure9Actual.toVirtual();

		// Now let's figure out what is the index for humidity in figure9Virtual and
		// what is the index for "80" in the value set of humidity!

		int indexForHumidity = figure9Virtual.getAttributeIndex("humidity");

		Attribute humidity = figure9Virtual.getAttribute(indexForHumidity);

		String[] values = humidity.getValues();

		int indexFor80 = -1;

		for (int i = 0; i < values.length; i++) {
			if (values[i].equals("80")) {
				indexFor80 = i;
				break;
			}
		}

		if (indexFor80 == -1) {
			System.out.println("Houston, we have a problem!");
			return;
		}

		VirtualDataSet[] figure9Partitions = figure9Virtual.partitionByNumericAttribute(indexForHumidity, indexFor80);

		System.out.println("JAVA IMPLEMENTATION OF THE SPLIT IN FIGURE 9:");
		System.out.println();

		for (int i = 0; i < figure9Partitions.length; i++)
			System.out.println("Partition " + i + ": " + figure9Partitions[i]);

	}
}