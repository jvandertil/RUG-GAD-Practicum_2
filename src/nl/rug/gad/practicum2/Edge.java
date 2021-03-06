package nl.rug.gad.practicum2;

import java.awt.Color;

public class Edge {

	public final int capacity;
	public int flow;
	public Vertex start;
	public Vertex end;
	public Color color = Color.black;
	
	//To be used when on the search for an augmenting path
	//Not to be used when creating a graph
	public boolean forward = true;
	
	public enum EdgeStatus {UNEXPLORED, BACK, DISCOVERY};
	public EdgeStatus status = EdgeStatus.UNEXPLORED;
	
	public Edge(int capacity) {
		this.capacity = capacity;
	}
}
