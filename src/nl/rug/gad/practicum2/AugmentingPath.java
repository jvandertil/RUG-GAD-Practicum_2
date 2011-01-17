package nl.rug.gad.practicum2;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import nl.rug.gad.practicum2.Edge.EdgeStatus;
import nl.rug.gad.practicum2.Vertex.VertexStatus;

public class AugmentingPath {

	/*
	 * g = graph s = source t = sink
	 */
	private static boolean stop = false;
	
	public static List<Edge> getAugmentedPathDFS(Graph g, Vertex s, Vertex t){
		stop = false;
		List<Edge> augmentedPath = new LinkedList<Edge>();
		s.status = VertexStatus.EXPLORED;

		for (Edge e : s.outgoingEdges) {
			if (e.flow < e.capacity) {
				Vertex w = g.opposite(s, e);
				if (w.status == VertexStatus.UNEXPLORED) {
					e.status = EdgeStatus.DISCOVERY;
					e.forward = true;
					augmentedPath.add(e);
					e.color = Color.blue;
					if(w.equals(t)){
						stop = true;
						return augmentedPath;
					}
					augmentedPath.addAll(getAugmentedPathDFS(g, w, t));
				} else {
					e.status = EdgeStatus.BACK;
					augmentedPath.remove(e);
					e.color = Color.black;
				}
			}
			if(stop){
				return augmentedPath;
			}
		}

		for (Edge e : s.incomingEdges) {
			if (e.flow > 0) { // TODO: Non zero flow toch? -Als ie 0 is, kan dr niks af
				Vertex w = g.opposite(s, e);
				if (w.status == VertexStatus.UNEXPLORED) {
					e.status = EdgeStatus.DISCOVERY;
					e.forward = false;
					augmentedPath.add(e);
					e.color = Color.blue;
					if(w.equals(t)){
						stop = true;
						return augmentedPath;
					}
					augmentedPath.addAll(getAugmentedPathDFS(g, w, t));
				} else {
					e.status = EdgeStatus.BACK;
					augmentedPath.remove(e);
					e.color = Color.black;
				}
			}
			if(stop){
				return augmentedPath;
			}
		}
		return new LinkedList<Edge>();
	}

	public static List<Edge> getAugmentedPathBFS(Graph g, Vertex s, Vertex t) {
		List<Edge> path = new LinkedList<Edge>();
		
		Queue<Vertex> vertexQueue = new LinkedList<Vertex>();

		s.status = VertexStatus.EXPLORED;
		vertexQueue.add(s);

		Vertex w;

		while (!vertexQueue.isEmpty()) {
			w = vertexQueue.poll();
			
			for (Edge e : w.outgoingEdges) {
				if (e.flow < e.capacity) {
					Vertex x = g.opposite(w, e);
					
					if (x.status == VertexStatus.UNEXPLORED) {
						vertexQueue.add(x);
						e.status = EdgeStatus.DISCOVERY;
						e.forward = true;
						path.add(e);
						e.color = Color.blue;
					} else {
						e.status = EdgeStatus.BACK;
						path.remove(e);
						e.color = Color.black;
					}
					
					if(x.equals(t)){
						return path;
					}
				}
			}
			
			for(Edge e : s.incomingEdges) {
				if(e.flow > 0) { //TODO:Verify.
					Vertex x = g.opposite(w, e);
					
					if(x.status == VertexStatus.UNEXPLORED) {
						e.status = EdgeStatus.DISCOVERY;
						e.forward = false;
						path.add(e);
						e.color = Color.blue;
						vertexQueue.add(x);
					} else {
						e.status = EdgeStatus.BACK;
						path.remove(e);
						e.color = Color.black;
					}
					
					if(x.equals(t)){
						return path;
					}
				}
			}
		}

		return path;
	}
}
