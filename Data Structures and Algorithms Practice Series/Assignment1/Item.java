//##########################################################################################################################
//#                                      Brian Makos                                                                       #
//#                                       300194563                                                                        #
//#                                                                                                                        #
//#   Terh, F (March 28, 2019) How to solve the Knapsack Problem with dynamic programming [source code]                    #
//#   https://medium.com/@fabianterh/how-to-solve-the-knapsack-problem-with-dynamic-programming-eb88c706d3cf.              #                                                                             #
//##########################################################################################################################


public class Item{  
    
    private String name;
    private int weight;
    private int value;
  
    public Item(String n, int v, int w) {
      this.weight = w;
      this.value = v;
      this.name = n;
  
    }

    public int getValue(){
      return value;

    }

    public String getName(){
      return name;

    }

    public int getWeight(){
      return weight;
    }

}


