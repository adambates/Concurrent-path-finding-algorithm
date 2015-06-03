import java.util.ArrayList;

/* This class represents a node in a directed graph */
public class Node {
	
	// Store the nodeID in case we need it.
	private int nodeID;
	
	/* Every Node has a list of nodes connected to it */
	private ArrayList<Integer> nodes = new ArrayList<Integer>();

	/* Add Node to the list of connected nodes connected to this Node */
	public void addNode(int nodeId){
		nodes.add(nodeId);
	}
	
	/* Set Node ID of this object */
	public void setNodeId(int nodeId){
		nodeID = nodeId;
	}
	
	/* Get the nodeID of this object */
	public int getNodeID(){
		return nodeID;
	}
	
	/* Return the list of nodes connected to this Node */
	public ArrayList<Integer> getConnectedNodes(){
		return nodes;
	}
}