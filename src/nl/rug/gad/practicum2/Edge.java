package nl.rug.gad.practicum2;

public class Edge {

	public final int capacity;
	public int flow;
	public Vertex start;
	public Vertex end;
	
	//To be used when on the search for an augmenting path
	//Not to be used when creating a graph
	public enum Direction {FORWARD, BACKWARD, NONE};
	public Direction direction = Direction.NONE;
	
	public Edge(int capacity) {
		this.capacity = capacity;
	}
}
