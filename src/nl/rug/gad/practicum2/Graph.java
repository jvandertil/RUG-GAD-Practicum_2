package nl.rug.gad.practicum2;

import java.util.List;

public class Graph {

	public List<Edge> edgeList;
	public List<Vertex> vertexList;
	
	public Vertex startPoint;
	public Vertex endPoint;
	
	//insert(Vertex)
	public void insert(Vertex v) {
		this.vertexList.add(v);
	}
	
	//insert(Edge e, Vertex start, Vertex eind)
	public void insert(Edge e, Vertex start, Vertex end) {
		edgeList.add(e);
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
	
	//Optioneel:
	//RemoveVertex
	//RemoveEdge
}
