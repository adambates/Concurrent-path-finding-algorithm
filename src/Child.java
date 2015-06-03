/* NWEN303 - Project 1
 * Name: Adam Bates
 * Student ID: 300223031
 * User: batesadam
 */

import java.util.ArrayList;

/* Child class represents Node when it is running */
public class Child implements Runnable {
	
	private int nodeID = 0;					// ID of the running Node
	private ArrayList<Integer> uptoHere;	// ArrayList containing the nodes this path has visited up to this Node.
	
	/* Child constructor that sets node id and passes in the already visited nodes */ 
	public Child(int nodeID, ArrayList<Integer> uptoHere){
		this.nodeID = nodeID;
		if(uptoHere != null)			
			this.uptoHere = uptoHere;
		else
			this.uptoHere = new ArrayList<Integer>();
	}
	
	
	@Override
	/* Runs when thread executes, creates a new child object to run in individual threads for each node that is connected to
	 * this node. If found we print the path and carry on or if we are finding a single path we shutdown all other threads 
	 * and print path.
	 */
	public void run() {

	// For testing performance.
	//	try {
	//		Thread.sleep(500);
	//	} catch (InterruptedException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
		
		/* Iterate over the nodes connected to this node */
		for(int nodeId: (FindPaths.nodes.get(nodeID)).getConnectedNodes()){
			/* If we are finding a single path and a path has already been found then terminate */
			if(FindPaths.type && FindPaths.found){
				return;
			}
			/* If the node hasn't been visited by this path */
			if(!(uptoHere.contains(nodeId))){
				if(FindPaths.goalNodeID.contains(nodeId)){
					if((FindPaths.type && FindPaths.write()) || !FindPaths.type){ // Print if we are finding all nodes
					for(int id: uptoHere){											// else only print if this is the first path for step 3
						System.out.printf("%d ->", id);
					}
					System.out.printf("%d -> %d\n", nodeID, nodeId);
					}
				}
				
				ArrayList<Integer> childList = new ArrayList<Integer>(uptoHere);	// New Array
				childList.add(nodeID);												// Add this node to list of visited and create 
																					// new thread and execute.
				Child child = new Child(nodeId, childList);
				Thread thread = new Thread(child);
				thread.run();
			
			}
			// else, don't visit a node that has already been visited, ie no cycles.
		}
	}
}