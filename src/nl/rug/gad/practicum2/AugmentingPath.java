package nl.rug.gad.practicum2;

import java.util.LinkedList;

import nl.rug.gad.practicum2.Edge.EdgeStatus;
import nl.rug.gad.practicum2.Vertex.VertexStatus;

public class AugmentingPath {
	
	/*
	 * g = graph
	 * s = source
	 * t = sink
	 */
	
	public static LinkedList<Edge> getAugmentedPathDFS(Graph g, Vertex s, Vertex t){
		LinkedList<Edge> augmentedPath = new LinkedList<Edge>();
		s.status = VertexStatus.EXPLORED;
		
		for(Edge e : s.outgoingEdges){
			if(e.flow < e.capacity){
				Vertex w = g.opposite(s, e);
				if(w.status == VertexStatus.UNEXPLORED){
					e.status = EdgeStatus.DISCOVERY;
					e.forward = true;
					augmentedPath.add(e);				
					augmentedPath.addAll(getAugmentedPathDFS(g, w, t));
				} else {
					e.status = EdgeStatus.BACK;
					augmentedPath.remove(e);
				}
			}
		}
		for(Edge e : s.incomingEdges){
			if(e.flow > 0){
				Vertex w = g.opposite(s, e);
				if(w.status == VertexStatus.UNEXPLORED){
					e.status = EdgeStatus.DISCOVERY;
					e.forward = false;
					augmentedPath.add(e);				
					augmentedPath.addAll(getAugmentedPathDFS(g, w, t));
				} else {
					e.status = EdgeStatus.BACK;
					augmentedPath.remove(e);
				}
			}
		}
		return augmentedPath;
	}
	
	public static LinkedList<Edge> getAugmentedPathBFS(Graph g, Vertex s, Vertex t){
		LinkedList<Edge> augmentedPath = new LinkedList<Edge>();
		//TODO
		return augmentedPath;
	}
}
