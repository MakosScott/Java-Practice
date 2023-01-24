import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
import java.io.File;
import java.lang.reflect.Array;
import java.util.Scanner;

//##########################################################################################################################
//#                                      Brian Makos                                                                       #
//#                                       300194563                                                                        #
//#                                                                                                                        #
//#   Terh, F (March 28, 2019) How to solve the Knapsack Problem with dynamic programming [source code]                    #
//#   https://medium.com/@fabianterh/how-to-solve-the-knapsack-problem-with-dynamic-programming-eb88c706d3cf.              #                                                                             #
//##########################################################################################################################

public class KnapsackProblem {

    
    public static void main(String[] args) throws Exception {
        
        String filename = args[0];
        String flag = args[1];
      
        //Read the file
        Scanner scanner = new Scanner(new File(filename));


        //Read input variables and store them
        int numberOfItems = 0;
        int capacityOfKnap = 0;

        if (scanner.hasNext()) {
            String str = scanner.nextLine();
            numberOfItems = Integer.valueOf(str);
        }

        Item[] items = new Item[numberOfItems];

        for (int i = 0; i < numberOfItems; i++) {
            String str = scanner.nextLine();
            String[] arr = str.split(" ");
            items[i] = new Item(arr[0], Integer.valueOf(arr[1]), Integer.valueOf(arr[2]));
        }

        if (scanner.hasNext()) {
            String str = scanner.nextLine();
            capacityOfKnap = Integer.valueOf(str);
        }


        //Creating KTable To Calculate Optimized Knapsack

        KTable matrix = new KTable(items, capacityOfKnap);

        Knapsack knapsack = matrix.optimizeKnapsack(); 
        Item[] finalItems = knapsack.getItems();
        int finalValueTotal = knapsack.getValueTotal();

        //Getting all the requiered info for the .sol file

        String newFilename;

        //isolating the file name
        String[] splitFilename = filename.split("\\\\");
        newFilename = splitFilename[splitFilename.length-1];
        String[] splitNewFilename  = newFilename.split("\\.");
        String finalFilename = splitNewFilename[0];
        String finalItemsPrint = "";

        for (int i = finalItems.length -1 ; i >= 0; i--) {
            finalItemsPrint = finalItemsPrint + finalItems[i].getName() + " ";
        }


        //Creating the .sol file
        PrintWriter writer = new PrintWriter(finalFilename + ".sol");
        writer.println(finalValueTotal);
        writer.println(finalItemsPrint);
        writer.close();









        
    }

         
}



















