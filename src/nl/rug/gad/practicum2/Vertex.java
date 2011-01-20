package nl.rug.gad.practicum2;

import java.util.LinkedList;
import java.util.List;

public class Vertex implements Comparable<Vertex> {
	
	public int id;
	public List<Edge> incomingEdges;
	public List<Edge> outgoingEdges;
	
	//Only used for Dijkstra's algorithm
	public Integer maxFlow = 0;
	
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
	
	public int getResidualCapacity(Edge e){
		if (e.start.equals(this)) {
			return e.capacity - e.flow;
		} else {
			return e.flow;
		}
	}
	
	public List<Edge> getAllEdges(){
		List<Edge> unionEdge = new LinkedList<Edge>();
		unionEdge.addAll(this.incomingEdges);
		unionEdge.addAll(this.outgoingEdges);
		return unionEdge;
	}
	
	@Override
	public int compareTo(Vertex v) {
		return -this.maxFlow.compareTo(v.maxFlow);
	}
}
