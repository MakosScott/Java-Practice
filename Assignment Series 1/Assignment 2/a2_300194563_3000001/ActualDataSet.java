/**
 * This class is used for representing an actual dataset, that is, a dataset
 * that holds a data matrix
 * 
 * @author Mehrdad Sabetzadeh, University of Ottawafigure5Virtual
 * @author Guy-Vincent Jourdan, University of Ottawa
 *
 */
public class ActualDataSet extends DataSet {
	/**
	 * The data matrix
	 */
	private String[][] matrix;

	/**
	 * The source identifier for the data. When the data source is a file, sourceId
	 * will be the name and location of the source file
	 */
	private String dataSourceId;

	/**
	 * Constructor for ActualDataSet. In addition to initializing dataSourceId,
	 * numAttributes, numRows and matrix, the constructor needs to create an array of
	 * attributes (instance of the Attribute class) and initialize the "attributes"
	 * instance variable of DataSet.
	 * 
	 * 
	 * @param reader is the DataReader instance to read data from.
	 */
	public ActualDataSet(DataReader reader) {
		// WRITE YOUR CODE HERE!
		this.numRows = reader.getNumberOfDataRows(); 
		this.numAttributes = reader.getNumberOfColumns(); 
		this.dataSourceId = reader.getSourceId(); 
        this.matrix = reader.getData();  
		this.attributes = new Attribute[this.numAttributes];

		String[] name = reader.getAttributeNames(); 

		for (int h = 0; h < numAttributes; h++) { 

			String[] newArray = getUniqueAttributeValues(h);

			AttributeType type; 

			if(Util.isArrayNumeric(newArray)) { 
				type = AttributeType.NUMERIC; 
			} 

			else { 
				type = AttributeType.NOMINAL; 
			}
			
			Attribute dataReaderAttribute = new Attribute(name[h], h, type, newArray); 

			attributes[h] = dataReaderAttribute;
		}
	}

	/**
	 * Implementation of DataSet's abstract getValueAt method for an actual dataset
	 */
	public String getValueAt(int row, int attributeIndex) {
		// WRITE YOUR CODE HERE!

		if (row < 0 || row > (numRows -1)){
			return null;
		}	
		if (attributeIndex < 0 || attributeIndex > (numAttributes -1)){
			return null;
		}	
		
		// check for out of bounds
		
		//Remove the following line when this method has been implemented
		return matrix[row][attributeIndex];
	}

	/**
	 * @return the sourceId of the dataset.
	 */
	public String getSourceId() {
		return dataSourceId; 
	}

	/**
	 * Returns a virtual dataset over this (actual) dataset
	 * 
	 * @return a virtual dataset spanning the entire data in this (actual) dataset
	 */
	public VirtualDataSet toVirtual() {
	
		int [] rows = new int[this.numRows];

		for (int i=0; i < this.numRows; i++) {
			rows[i] = i;
		}

		return new VirtualDataSet( this, rows, this.attributes);

		// do this after you do 4.1-4.2
	}

	/**
	 * Override of toString() in DataSet
	 * 
	 * @return a string representation of this (actual) dataset.
	 */
	public String toString() {
		// WRITE YOUR CODE HERE!
		
		System.out.println("Actual dataset " + getSourceId() + " with " + getNumberOfAttributes() + " attribute(s) and " + getNumberOfDatapoints() + " row(s)"); 
		return super.toString(); 
	}
}