package nl.rug.gad.practicum2;

import java.util.LinkedList;
import java.util.List;

public class Graph {
	
	public List<Edge> edgeList;
	public List<Vertex> vertexList;
	
	public Vertex startPoint;
	public Vertex endPoint;
	
	public void setStartPoint(Vertex v) {
		edgeList = new LinkedList<Edge>();
		vertexList = new LinkedList<Vertex>();
		this.startPoint = v;
		insert(v);
	}
	
	public void setEndPoint(Vertex v) {
		this.endPoint = v;
		insert(v);
	}
	
	public Vertex opposite(Vertex v, Edge e){
		if(e.start.equals(v)){
			return e.end;
		}
		return e.start;
	}
	
	//insert(Vertex)
	public void insert(Vertex v) {
		this.vertexList.add(v);
	}
	
	//insert(Edge e, Vertex start, Vertex eind)
	public void insert(Edge e, Vertex start, Vertex end) {
		edgeList.add(e);
		e.start = start;
		e.end = end;
		start.addOutgoing(e);
		end.addIncoming(e);
	}
	
	//areAdjacent(Vertex a, Vertex b)
	public boolean areAdjacent(Vertex a, Vertex b) {
		for(Edge e : a.outgoingEdges) {
			if(e.end == b)
				return true;
		}
		
		return false;
	}
	
	/**
	 * Finds a Vertex with the specified id within the vertexList.
	 * 
	 * Access time O(n). Where n is the number of vertexes
	 * 
	 * @param id
	 * @return The vertex, or null.
	 */
	public Vertex findVertex(int id) {
		for(Vertex v : vertexList)
			if(v.id == id)
				return v;
		
		return null;
	}
	
	public void printGraph(){
		for(Vertex v : this.vertexList){
			System.out.println("Vertex " + v.id);
			for(Edge e : v.outgoingEdges){
				System.out.println("ForwardEdge to " + e.end.id + " Flow: " + e.flow + "/" + e.capacity);
			}
			for(Edge e : v.incomingEdges){
				System.out.println("BackwardEdge to " + e.start.id + " Flow: " + e.flow + "/" + e.capacity);
			}
		}
	}
	
	//Optioneel:
	//RemoveVertex
	//RemoveEdge
}
