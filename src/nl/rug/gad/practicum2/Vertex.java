package nl.rug.gad.practicum2;

import java.util.LinkedList;
import java.util.List;

public class Vertex {
	
	public int id;
	public List<Edge> incomingEdges;
	public List<Edge> outgoingEdges;
	
	//Only to be used when searching for augmented path
	public enum VertexStatus {UNEXPLORED, EXPLORED};
	public VertexStatus status = VertexStatus.UNEXPLORED;
	
	public Vertex(int id) {
		this.id = id;
		incomingEdges = new LinkedList<Edge>();
		outgoingEdges = new LinkedList<Edge>();
	}
	
	public void addIncoming(Edge e) {
		incomingEdges.add(e);
	}
	
	public void addOutgoing(Edge e) {
		outgoingEdges.add(e);
	}
}
