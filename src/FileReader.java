/* NWEN303 - Project 1
 * Name: Adam Bates
 * Student ID: 300223031
 * User: batesadam
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/* FileReader is a helper class than reads a graph from a file into memory */

public class FileReader {

	private int startNode;
	private ArrayList<Integer> goalNode = new ArrayList<Integer>();
	private String filename;
	private File reader = null;
	
	private HashMap<Integer, Node> nodes = new HashMap<Integer, Node>();
	
	public FileReader(String filename) throws FileNotFoundException{
		this.filename = filename;
		reader = new File(filename);
	}
	
	/* 	Reads in graph from a file in the correct format 
	*	
	* Where n = node id 
	* Edge Format = E n n'
	* Start Node = S n
	* Goal Node = G n  
	*/ 
	public void ReadGraph() throws IOException{
		
		Scanner scanner = new Scanner(reader); 

		int lineNumber = 1;
		boolean start = false;
		boolean goal = false;
		
		/* Scanning each line of the file */
		while(scanner.hasNextLine()){
			if(scanner.hasNext()){
				String type = scanner.next();	// Getting line type.
				
				/* If line is an Edge */
				if(type.equalsIgnoreCase("E")){
					int nodeId1 = 0;
					int nodeId2 = 0;
					
					if(scanner.hasNextInt()){
						nodeId1 = scanner.nextInt(); 
					}
					if(scanner.hasNextInt()){
						nodeId2 = scanner.nextInt(); 
					}
					
					
					/* If nodeId1 already exists in the HashMap of nodes,  add nodeId2 to the list of nodes
					 *  connected to nodeId1.
					 */
					if(nodes.containsKey(nodeId1)){
						nodes.get(nodeId1).addNode(nodeId2);
					}
					/* Doesn't already exist in the list of nodes so create a new HashMap key value pair
					 *  and create a new node to represent nodeId1 and add nodeId2 to the list of nodes connected to it.
					 */
					else{
						Node node = new Node();
						node.setNodeId(nodeId1);
						node.addNode(nodeId2);
						nodes.put(nodeId1, node);
					}
					if(!nodes.containsKey(nodeId2)){
						Node node = new Node();
						node.setNodeId(nodeId2);
						nodes.put(nodeId2, node);
					}
					
					// If the graph is not directed we need links going both ways between nodes.
					if(!FindPaths.directed){
						if(nodes.containsKey(nodeId2)){
							nodes.get(nodeId2).addNode(nodeId1);
						}
					}
					
				}
				/* If line is a Start Node set startNode */
				else if(type.equalsIgnoreCase("S")){
					if(start){
						scanner.close();
						throw new IOException("Can't have more than one start node");
					}
					if(scanner.hasNextInt())
						startNode = scanner.nextInt();
					else{
						scanner.close();
						throw new IOException("Error Scanning Start on " + lineNumber + ". Not enough values"); 
					}
					start = true;
				}
				/* If line is a Goal node set goalNode */
				else if(type.equalsIgnoreCase("G")){
					if((FindPaths.type) && goal){	// If we are finding multiple paths then we can find multiple goals.
						scanner.close();
						throw new IOException("Can't have more than one goal node");
					}
					if(scanner.hasNextInt())
						goalNode.add(scanner.nextInt());	
					else{
						scanner.close();
						throw new IOException("Error Scanning Goal on " + lineNumber + ". Not enough values"); 
					}
					goal = true;
				}
				lineNumber++;
			}
		}
		scanner.close();
	}
	
	/* Returns HashMap of Nodes */
	public HashMap<Integer, Node> getNodes(){
		return nodes;
	}
	
	/* Returns Start Node */
	public int getStartNode(){
		return startNode;
	}
	
	/* Returns Goal Node */
	public ArrayList<Integer> getGoalNode(){
		return goalNode;
	}
}
