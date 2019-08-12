import java.util.Iterator;
import java.util.Scanner;
/**
 * Part 2 of Assignment 3
 * a program that will find the shortest connection travel time between airports
 * 
 * @author Rayyan Albaz
 * 
 * Student ID# 214540017
 *
 */

public class shortestTravelTime {

    public static void main(String[] args) {
    	// Creates new Adjacency List Graph from Part 1
        AdjListGraph < String, String > myGraph = new AdjListGraph < String, String > (true);
        boolean loop = true; // Boolean for loop termination
        Scanner s = new Scanner(System.in); // Reads user inputs

        while (loop) {
            String line = s.nextLine();
            String[] ary = line.split(" "); // splits the string into array of strings 
            switch (ary[0]) {
                case "+": // case of adding 
                    if (ary.length == 5) { 
                        String origin = ary[1];
                        String dest = ary[2];
                        Vertex < String > originVertex = myGraph.getVertex(origin);
                        Vertex < String > destVertex = myGraph.getVertex(dest);
                        if (originVertex == null) {
                            originVertex = myGraph.insertVertex(origin);
                        }
                        if (destVertex == null) {
                            destVertex = myGraph.insertVertex(dest);
                        }
                        myGraph.insertEdge(originVertex, destVertex, ary[3] + " " + ary[4]);
                    } else {
                        System.out.println("Invalid Usage!");
                    }
                    break;
                case "-": // case of deleting 
                    if (ary.length == 2) {
                        String ver = ary[1];
                        Vertex < String > vertex = myGraph.getVertex(ver);
                        if (vertex != null) {
                            myGraph.removeVertex(vertex);
                        } else {
                            System.out.println("Airport Does Not Exist!");
                        }
                    } else if (ary.length == 5) {
                        Vertex < String > origin = myGraph.getVertex(ary[1]);
                        Vertex < String > dest = myGraph.getVertex(ary[2]);
                        if (origin != null && dest != null) {
                            Iterator < Edge < String >> iter = myGraph.outgoingEdges(origin).iterator();
                            while (iter.hasNext()) {
                                Edge < String > edge = (Edge < String > ) iter.next();
                                Vertex < String > [] endPoints = myGraph.endVertices(edge);
                                Vertex < String > checkDest = endPoints[1];
                                if (checkDest == dest) {
                                    myGraph.removeEdge(edge);
                                }
                            }

                        }
                    } else {
                        System.out.println("Invalid Usage!");
                    }
                    break;
                case "?": // case of listing 
                    if (ary.length == 1) {
                        if (myGraph.numEdges() == 0) {
                            System.out.println("No Connections To Display!");
                        }
                        Iterator < Edge < String >> iter = myGraph.edges().iterator();
                        while (iter.hasNext()) {
                            Edge < String > edge = (Edge < String > ) iter.next();
                            String duration = edge.getElement();
                            Vertex < String > [] endpoints = myGraph.endVertices(edge);
                            String origin = endpoints[0].getElement();
                            String dest = endpoints[1].getElement();
                            System.out.println(origin + " " + dest + " " + duration);
                        }
                    } else if (ary.length == 2) {
                        Vertex < String > origin = myGraph.getVertex(ary[1]);
                        if (origin != null) {
                            if (myGraph.outDegree(origin) == 0) {
                                System.out.println("No Outbound Flights From " + origin.getElement());
                            }
                            Iterator < Edge < String >> iter = myGraph.outgoingEdges(origin).iterator();
                            while (iter.hasNext()) {
                                Edge < String > edge = (Edge < String > ) iter.next();
                                String duration = edge.getElement();
                                String dest = myGraph.endVertices(edge)[1].getElement();
                                System.out.println(origin.getElement() + " " + dest + " " + duration);
                            }
                        } else {
                            System.out.println("Airport Does Not Exist!");
                        }
                    } else if (ary.length == 3) {
                    	System.out.println("Method not implemented yet");
                    }

                    break;
                case "QUIT":
                    loop = false;
                    s.close(); // close scanner
                default:
                    System.out.println("Invalid Usage");
                    break;
            }
        }
    }
}