package nl.rug.gad.practicum2;

import java.util.LinkedList;

import nl.rug.gad.practicum2.Edge.Direction;

public class AugmentingPath {
	
	/*
	 * g = graph
	 * s = source
	 * t = sink
	 */
	
	public static LinkedList<Edge> getAugmentedPathDFS(Graph g, Vertex s, Vertex t){
		LinkedList<Edge> augmentedPath = new LinkedList<Edge>();
		//Reset all directions in graph
		for(Edge e : g.edgeList){
			e.direction = Direction.NONE;
		}
		//TODO
		return augmentedPath;
	}
	
	public static LinkedList<Edge> getAugmentedPathBFS(Graph g, Vertex s, Vertex t){
		LinkedList<Edge> augmentedPath = new LinkedList<Edge>();
		//TODO
		return augmentedPath;
	}
}
