//##########################################################################################################################
//#                                      Brian Makos                                                                       #
//#                                       300194563                                                                        #
//#                                                                                                                        #
//#   Terh, F (March 28, 2019) How to solve the Knapsack Problem with dynamic programming [source code]                    #
//#   https://medium.com/@fabianterh/how-to-solve-the-knapsack-problem-with-dynamic-programming-eb88c706d3cf.              #                                                                             #
//##########################################################################################################################


public class Knapsack{
   
    //maximum capacity of the Knapsack
    private int capacity;

    //items after optimization
    private Item[] items;

  
    //The knapsack needs to take in the maximum capacity and the list of optimized items in our problem.
    public Knapsack(int c, Item[] i) {
      this.capacity = c;
      this.items = i;
  
    }

    public int getCapactity() {
      return capacity;
    }

    public Item[] getItems() {
      return items;
    }

    public int getValueTotal(){
      int valueTotal = 0;

      for (int i = 0; i < items.length; i++) {
        valueTotal = valueTotal + items[i].getValue();
      }
        
        
      return valueTotal;
    }

}
