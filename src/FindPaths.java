/* NWEN303 - Project 1
 * Name: Adam Bates
 * Student ID: 300223031
 * User: batesadam
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FindPaths {

	private String filename;
	private int startNodeID;
	public static ArrayList<Integer> goalNodeID;
	public static HashMap<Integer, Node> nodes;
	public static boolean type;
	public static boolean found;
	private static double time;
	public static boolean directed;
	
	public FindPaths(String filename, boolean type, boolean directed){
		this.filename = filename;
		this.type = type;
		this.found = false;
		this.directed = directed;
	}
	
	public void findPaths(){
		
		try {
			FileReader fileReader = new FileReader(filename);		// Create new FileReader object	
			fileReader.ReadGraph();									// Read in the graph
			
			/* Get graph information that has been read in from the file. */
			startNodeID = fileReader.getStartNode();
			goalNodeID = fileReader.getGoalNode();
			nodes = fileReader.getNodes();
			
			System.out.printf("Start Node = %d, Goal Node(s) = %s\nNode in graph - [children]\n", startNodeID, goalNodeID.toString());
			
			// Prints nodes that shows what links are connected to them 
			for(int id: nodes.keySet()){
				System.out.printf("%d - %s\n", id, nodes.get(id).getConnectedNodes().toString());
			}
			System.out.print("\n");
			if(!type){
				System.out.println("Paths:");
			}
		} catch (FileNotFoundException e) {
			System.out.println(filename + " was not found.");
			return;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		/* If the Start Node and the Goal Node are the same then exit */
		if(goalNodeID.contains(startNodeID)){
			System.out.println("Exiting, Start Node and Goal Node are the same");
			return;
		}
		
		// Record start time for single path (step 3)
		if(type) time = System.currentTimeMillis();	
	
		// Start new thread at start Node.
		Child child = new Child(startNodeID, null);
		Thread startNode = new Thread(child);
		startNode.run();
				
	}
	
	/* Synchronized method that will only let one thread execute this method at one time
	   So, first thread to call this method (for step 3) will cause all threads to quit when they find out that found is set to true
	   Any thread waiting on this method will get returned a false message which will cause them to terminate 
	*/ 
	public synchronized static boolean write(){
		if(!found){
			System.out.println("Time taken for search is: " + (System.currentTimeMillis() - time) + " ms."); // Print time taken.
			System.out.println("Path: ");
			found = true;
			return true;
		}
		else{
			return false;
		}
	}
	
	/* Getting user input, ie which graph to load, if the graph is directed, find all paths or one path */
	public static void main(String[] args) {
		
		Scanner inputScanner = new Scanner(System.in);
		String filename;
		String stringtype;
		
		System.out.println("Please enter a graph name from the \"Graphs\" folder to load.");
		System.out.print("-> ");

		filename = inputScanner.next();
		
		System.out.println("Is the graph directed(1) or not directed(2)?");
		System.out.print("-> ");

		String stringDirected = inputScanner.next();
		boolean directed;
		if(stringDirected.equalsIgnoreCase("1")){
			directed = true;
		}
		else if(stringDirected.equalsIgnoreCase("2")){
			directed = false;
		}
		else{
			System.out.println("ERROR"); return;
		}
		
		System.out.println("Enter 1 for all paths or 2 for one path");
		System.out.print("-> ");
		
		stringtype = inputScanner.next();
		
		boolean type;
		if(stringtype.equals("1"))
			type = false;
		else if(stringtype.equals("2"))
			type = true;		
		else{ System.out.println("ERROR"); return;}	
		inputScanner.close();
		FindPaths findPaths = new FindPaths("Graphs/" + filename, type, directed);
		findPaths.findPaths();
	}
}
