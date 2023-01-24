import java.util.ArrayList;
import java.util.List;


//##########################################################################################################################
//#                                      Brian Makos                                                                       #
//#                                       300194563                                                                        #
//#                                                                                                                        #
//#   Terh, F (March 28, 2019) How to solve the Knapsack Problem with dynamic programming [source code]                    #
//#   https://medium.com/@fabianterh/how-to-solve-the-knapsack-problem-with-dynamic-programming-eb88c706d3cf.              #                                                                             #
//##########################################################################################################################


public class KTable {
    
    private Item[] items;
    private int capacity;
    

    public KTable(Item[] items,int capacity){

        this.capacity = capacity;
        this.items = items;
    }



    public Knapsack optimizeKnapsack(){


        //#####################################
        //#            code used              #
        //#            starts here            #
        //#####################################
        int[][] matrix = new int[items.length + 1][capacity+1];

        //make line of zeros

                    // first line is initialized to 0
        for (int i = 0; i <= capacity; i++)
            matrix[0][i] = 0;

        // we iterate on items
        for (int i = 1; i <= items.length; i++) {
        // we iterate on each capacity
           
            for (int j = 0; j <= capacity; j++) {
                
                if (items[i - 1].getWeight() > j){
                    matrix[i][j] = matrix[i-1][j];
                }
                else{
                    // we maximize value at this rank in the matrix
                    matrix[i][j] = Math.max(matrix[i-1][j], matrix[i-1][j - items[i-1].getWeight()] + items[i-1].getValue());
                }
            }
        }
        
        int res = matrix[items.length][capacity];
        int w = capacity;
        List<Item> optimizedItems = new ArrayList<>();
        
        for (int i = items.length; i > 0  &&  res > 0; i--) {
            if (res != matrix[i-1][w]) {
                optimizedItems.add(items[i-1]);
                // we remove items value and weight
                res -= items[i-1].getValue();
                w -= items[i-1].getWeight();
                }
            }

        return new Knapsack(capacity, optimizedItems.toArray(new Item[optimizedItems.size()]));

        //#####################################
        //#            code used              #
        //#            stops here             #
        //#####################################

    }

}
