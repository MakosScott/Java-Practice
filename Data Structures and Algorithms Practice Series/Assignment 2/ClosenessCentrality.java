import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

public class ClosenessCentrality {

    public static final double Dicconnect_Distance = 1891;	// this means if you do not have any connection between two
                                                           // you can use total number of nodes.

    /**
     * we do not have any static or predefined parameters in Closeness Centrality formula but
     * you can add based on your code if you need it (like size, number of edges)
     */

    ClosenessCentrality(){
    }

    /**
     * Computes the ClosenessCentrality (CC) of each node in a graph.
     *
     * @param graph the Graph to compute CC for
     * @return returns a Map<Integer, Double> mapping each node to its ClosenessCentrality CC
     *
     */

    public Map<Integer, Double> computeClosenessCentrality(Graph graph){

        
        // replace this with your code

        List<Integer> nodes = new ArrayList<Integer>();
        Map<Integer, List<Integer>> edges = new HashMap<Integer, List<Integer>>();


        //getting nodes and edges
        nodes = graph.getGraphNodes();
        edges = graph.getGraphEdges();


        //Creating the adjacency matrix, I just want to have it under a different name

        Map<Integer, List<Integer>> adjacencyMatrix = new HashMap<Integer, List<Integer>>();
        

        adjacencyMatrix = edges;

        //array to store previous steps
        int previous[] = new int[nodes.size()];

        //Array to store the distances
        int distanceFromStartNode[] = new int[nodes.size()];


        ArrayList<ArrayList<Integer>> allDistanceFromStartNode = new ArrayList<ArrayList<Integer>>();
       
        //This methode does a breadth first search to find the shortest distance
        BFS(adjacencyMatrix, nodes, previous, distanceFromStartNode);

        //instantiate a where i get my distances
       


        //defining some variables
        ArrayList<ArrayList<Integer>> allDistanceList = allDistanceFromStartNode;

        Map<Integer, Double> finalHashMap = new HashMap<Integer, Double>();

        Map<Integer, Double> ccHashMap = new HashMap<Integer, Double>();

        //this methode does the math to find the cc values
        CC(allDistanceList, nodes);

        //final answer
        finalHashMap = ccHashMap;

        return finalHashMap;
    }


    //computes cc
    private static Map<Integer, Double> CC(ArrayList<ArrayList<Integer>> allDistanceFromStartNode,  List<Integer> nodes){

        ArrayList<ArrayList<Integer>> allDistanceList = allDistanceFromStartNode;



        Map<Integer, Double> ccHashMap = new HashMap<Integer, Double>();


        //fills the ccHashMap
        for (int i = 0; i < nodes.size() -1;  i++) {
            
            ccHashMap.put(i, (double) 0);

        }

        double temp = 0;

        temp =  allDistanceList.size() -1; 

        double temp2 = 0;


        //loops to compute cc
        for (int i = 0; i < allDistanceList.size(); i++) {


            for (int j = 0; j < allDistanceList.get(i).size()-1; j++) {
                
                

                temp2 = temp2 + allDistanceList.get(i).get(j);

            }

        //add cc values to hashmap
        ccHashMap.put(i, temp/temp2);

        }

        //return it
        return ccHashMap;


    }

    private static boolean BFS(Map<Integer, List<Integer>> adjacencyMatrix, List<Integer> nodes, int previous[], int distanceFromStartNode[]){
        
        //creates a queue with nodes to visit later
        LinkedList<Integer> toVisit = new LinkedList<Integer>();
        
        //list of booleans to inform us if a node has been visited
        boolean visitedNodes[] = new boolean[nodes.size()];
        

        //Matrix where we will store all the distances for each Node
        ArrayList<ArrayList<Integer>> allDistanceFromStartNode =new ArrayList<ArrayList<Integer>>();


        //set all the intial conditions
        for (int i = 0; i < nodes.size(); i++) {
            visitedNodes[i] = false;
            distanceFromStartNode[i] = (int) Dicconnect_Distance;
            previous[i] = -1;
            allDistanceFromStartNode.add(new ArrayList<>());
         
        }


        //For loops to loop through every starting node
        for (int i = 0; i < nodes.size()-1; i++) {

            visitedNodes[nodes.get(i)] = true;
            distanceFromStartNode[nodes.get(i)] = 0;
            toVisit.add(nodes.get(i));
            




            //for loop to loop through every end case
            for (int j = 0; j < nodes.size()-1; j++) {
                
                


                //while loop to assure we reach every case required
                while (!toVisit.isEmpty()) {
                    int nodeBeingVisited = toVisit.remove();


                    for (int x = 0; x < adjacencyMatrix.get(nodeBeingVisited).size(); x++) {


                        

                        //This checks to see if the node we are visiting has been visited before
                        if (visitedNodes[adjacencyMatrix.get(nodeBeingVisited).get(x)] == false) {

                            //if the node hasn't been visited yet, we set it now to visited
                            visitedNodes[adjacencyMatrix.get(nodeBeingVisited).get(x)] = true;

                            //we set the distance from the start node
                            distanceFromStartNode[adjacencyMatrix.get(nodeBeingVisited).get(x)] = distanceFromStartNode[nodeBeingVisited] + 1;

                            previous[adjacencyMatrix.get(nodeBeingVisited).get(x)] = nodeBeingVisited;
                            
                            toVisit.add(adjacencyMatrix.get(nodeBeingVisited).get(x));
                            
                        }
                            // stopping condition (when we find our desired node)
                            //We also add the distance to the matrix for later calculations
                            if (adjacencyMatrix.get(nodeBeingVisited).get(i) == nodes.get(j)){

                                allDistanceFromStartNode.get(x).add(distanceFromStartNode[distanceFromStartNode.length-1]);
                                break;
                                

                        }
                    }

                    //don't forget to add what to do if node is disconnected here

                }
            


            }
            
        }

        
            
        

        
        return false;
    }


    //if  a node can't be reached, check the paper on how to compute it
    //reverse path already computed, just grab it



    }



