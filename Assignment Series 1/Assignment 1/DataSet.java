import java.io.File;
import java.util.Scanner;


/**
 * The class enables loading a dataset from a file (CSV format) and deriving
 * some important characteristics of the data
 * 
 * @author Mehrdad Sabetzadeh, University of Ottawa
 * @author Guy-Vincent Jourdan, University of Ottawa
 *
 */
public class DataSet {

	/**
	 * The delimiter that separates attribute names and attribute values
	 */
	private static final char DELIMITER = ',';

	/**
	 * Character allowing escape sequences containing the delimiter
	 */
	private static final char QUOTE_MARK = '\'';

	/**
	 * Instance variable for storing the number of attributes (columns)
	 */
	private int numColumns;

	/**
	 * Instance variable for storing the number of datapoints (data rows)
	 */
	private int numRows;

	/**
	 * Instance variable for storing attribute names
	 */
	private String[] attributeNames;

	/**
	 * Instance variable for storing datapoints
	 */
	private String[][] matrix;

	/**
	 * Constructs a dataset by loading a CSV file
	 * 
	 * @param strFilename is the name of the file
	 */
	
	
	 public DataSet(String strFilename) throws Exception {

		// WRITE YOUR CODE HERE!

		calculateDimensions(strFilename);
		instantiateFromFile(strFilename);

	}

	/**
	 * Returns the name of the attribute at a given column index
	 * 
	 * @param column is the column index
	 * @return attribute name at index (null if the index is out of range)
	 */
	public String getAttributeName(int column) {
		// WRITE YOUR CODE HERE!

		String nameHeader = "";


		if (column > numColumns){
			return null;
		}else {
			nameHeader = nameHeader + attributeNames[column];
		}

		return nameHeader;	
		// Note: Remember to handle out-of-range values!
		
		// Remove the following null return after you implement this method
		
	}

	/**
	 * Returns the value of a given column for a given row (datapoint)
	 * 
	 * @param row    is the row (datapoint) index
	 * @param column is the column index
	 * @return the value of the attribute at column for the datapoint at row (null
	 *         if either row or column are out of range)
	 */
	public String getAttributeValue(int row, int column) {
		// WRITE YOUR CODE HERE!
		// Note: Remember to handle out-of-range values!
		
		// Remove the following null return after you implement this method
		
		String nameValue = "";


		if (row > numRows){
			return null;
		}else if(column > numColumns) {
			return null;
		}else {
			nameValue = nameValue + matrix[row][column];
		}

		return nameValue;	
		

	}

	/**
	 * Returns the number of attributes
	 * 
	 * @return number of attributes
	 */
	public int getNumberOfAttributes() {
		return numColumns;
	}

	/**
	 * Returns the number of datapoints
	 * 
	 * @return number of datapoints
	 */
	public int getNumberOfDatapoints() {
		return numRows;
	}

	/**
	 * Returns a reference to an array containing the unique values that an
	 * attribute can assume in the dataset
	 * 
	 * @param attributeName is the name of the attribute whose unique values must be
	 *                      returned
	 * @return String[] reference to the unique values of the the attribute with the
	 *         given name
	 */
	public String[] getUniqueAttributeValues(String attributeName) {

		// WRITE YOUR CODE HERE!

		// 

		for (int q = 0 ; q < numColumns; q++){

			if (attributeName.equals(attributeNames[q])){

				
				return getUniqueAttributeValues(q);

			}
		
		}
		 
		
		// Hint: You can first implement getUniqueAttributeValues(int column), below, 
		// and then make use of that private method here!
		
	    // Remove the following null return after you implement this method and
	    // return an appropriate array reference instead
	    
		return null;	
	}

	/**
	 * Returns a reference to an array containing the unique values that the
	 * attribute at a certain column can assume in the dataset
	 * 
	 * @param column is the index (staring from zero) for the attribute whose unique
	 *               values must be returned
	 * @return String[] reference to the unique values of the attribute at the given
	 *         column
	 */
	private String[] getUniqueAttributeValues(int column) {
	
		// WRITE YOUR CODE HERE!

		String[] uniqueArr = new String[numRows];
		int temp = 0; 

		 // counter of the numbers of unique elements

		for (int e = 0; e < numRows; e++) { 
			boolean condition = false; 
			
			for (int i = 0; i < uniqueArr.length; i++) {
				if (matrix[e][column].equals(uniqueArr[i])) { 
					condition = true;
					
					break; 
				} 
			} if (!condition) { 
				
				uniqueArr[temp] = matrix[e][column];
				temp = temp + 1; 
			} 
		} 
		String[] resultArr = new String[temp];
		for (int j = 0; j < temp; j++) { 
			resultArr[j] = uniqueArr[j];
		}
		
		return resultArr; 

		
		//REMOVE DEUPLICATES FROM EACH FIRST ELEMENT [[Sunny, ..., ... ,... ,...][Sunny,..., ..., ..., ...][windy, ... ... ... ...]] removes all duplicate instances such as sunny
		//On

	    // Remove the following null return after you implement this method and
		// return an appropriate array reference instead
		
	}

	/**
	 * Returns in the form of an explanatory string some important characteristics
	 * of the dataset. These characteristics are: the number of attributes, the
	 * number of datapoints and the unique values that each attribute can assume
	 * 
	 * @return String containing the characteristics (metadata)
	 */
	public String metadataToString() {

		// Hint: You can combine multiple lines by appending
		// a (platform-dependent) separator to the end of each line.
		// To obtain the (platform-dependent) separator, you can use 
		// the following command.
		
		String sep = System.getProperty("line.separator");

		System.out.println("Number of attributes: " + numColumns);
        System.out.println("Number of datapoints: " + numRows);
        System.out.println();
        System.out.println("* * * Attribute value sets * * *");

        String empty = ""; 

			for (int i = 0; i < attributeNames.length; i++) { 
				String[] arrays = getUniqueAttributeValues(attributeNames[i]); 
				if (Util.isArrayNumeric(arrays)) { 
					empty = empty + (i + 1) + ") " + attributeNames[i] + "(numeric) " + Util.numericArrayToString(arrays) + sep; 
				} 

				else { 
					empty = empty + (i + 1) + ") "  + attributeNames[i] + "(nominal) " + Util.nominalArrayToString(arrays) + sep; 
				} 

			} 
			return empty; 
    	
		
		// Hint: You need to call getUniqueAttributeValues() for
		// each attribute (via either attribute name or attribute column) and
		// then concatenate the string representations of the arrays returned by
		// getUniqueAttributeValues(). To get the string representations for 
		// these arrays, you can use the methods provided in the Util class.
		// For nominal attributes use: Util.nominalArrayToString()
		// For numeric attributes use: Util.numericArrayToString()

		// Remove the following null return after you implement this method	
	}

	/**
	 * <b>main</b> of the application. The method first reads from the standard
	 * input the name of the CSV file to process. Next, it creates an instance of
	 * DataSet. Finally, it prints to the standard output the metadata of the
	 * instance of the DataSet just created.
	 * 
	 * @param args command lines parameters (not used in the body of the method)
	 */
	public static void main(String[] args) throws Exception {

        StudentInfo.display();

		System.out.print("Please enter the name of the CSV file to read: ");

		Scanner scanner = new Scanner(System.in);

		String strFilename = scanner.nextLine();

		DataSet dataset = new DataSet(strFilename);

		System.out.print(dataset.metadataToString());

	}

	/**
	 * This method should set the numColumns and numRows instance variables
	 * The method is incomplete; you need to complete it.
	 * @param strFilename is the name of the dataset file
	 */
	private void calculateDimensions(String strFilename) throws Exception {

		Scanner scanner = new Scanner(new File(strFilename));

		// YOU ARE ALLOWED TO DEFINE LOCAL VARIABLES
		int counterNum = 0;
		Boolean noHeader = true;

		while (scanner.hasNext()) {
			// Read one full line from file
			String str = scanner.nextLine();

			if(str.length() > 0){

				if (noHeader) {
					String[] top = str.split("" + DELIMITER); 

					int internalCommas = 0;
					for (int i=0; i < top.length; i++){

						top[i] = top[i].strip();

						if (top[i].startsWith("'") && !top[i].endsWith("'")) {
							internalCommas++;
						}
					}
					numColumns = top.length - internalCommas;
					noHeader = false;					
				} else {
					counterNum++;
				}
			}

		}

		numRows = counterNum;
		
		scanner.close();
	}

	/**
	 * This method should load the attribute names into the attributeNames
	 * instance variable and load the datapoints into the matrix instance variable.
	 * The method is incomplete; you need to complete it.
	 * @param strFilename is the name of the file to read
	 */
	private void instantiateFromFile(String strFilename) throws Exception {
		Scanner scanner = new Scanner(new File(strFilename));
		
		// YOU ARE ALLOWED TO DEFINE LOCAL VARIABL`ES
		attributeNames = null;
		matrix = new String[numRows][numColumns];
		int counter = 0;


		while (scanner.hasNext()) {
			String str = scanner.nextLine();
			String[] arr = str.split("" + DELIMITER); 

			if (str.length() > 0){
				//Deal with commas in the value
				if (numColumns < arr.length) {

					String[] tempArr = new String[numColumns];
					int internalCommas = 0;

					for (int i=0; i < arr.length; i++){

						if (arr[i].strip().startsWith("'") && !arr[i].endsWith("'")) {
							tempArr[i-internalCommas] = arr[i] + "," + arr[++i];
							internalCommas++;
						} else {
							tempArr[i-internalCommas] = arr[i];
						}
					}
				}
				//Check if you have attributes already
				if (attributeNames == null) {
					for (int u = 0; u < arr.length; u++){
						arr[u] = arr[u].strip();
					}
					attributeNames = arr;

				} else {
					
					if (arr.length < numColumns) {
						//We need to append when arr is smaller than number of columns
						String[] tmpArr = new String[numColumns];
						
						for (int a = 0; a < numColumns; a++){

							if (a < arr.length) {
								tmpArr[a] = arr[a];
							} else {								
								tmpArr[a] = "N/A";
							}
						}
						arr = tmpArr;
					} else {

						for (int u = 0; u < arr.length; u++){
							if (arr[u].equals("")) {
								//if there is a missing string, add new string
								arr[u] = "N/A";					
							}
						}
					}


					for (int g = 0; g < arr.length; g++) {
						arr[g] = arr[g].strip();
					}
				
					matrix[counter] = arr;
					counter++;
				}
			}
		}


		scanner.close();
	}
}