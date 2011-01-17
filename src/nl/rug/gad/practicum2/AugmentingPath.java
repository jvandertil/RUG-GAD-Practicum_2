package nl.rug.gad.practicum2;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import nl.rug.gad.practicum2.Edge.EdgeStatus;
import nl.rug.gad.practicum2.Vertex.VertexStatus;

public class AugmentingPath {

	public static List<Edge> getAugmentedPathDFS(Graph g, Vertex s, Vertex t, List<Edge> path){
		if(s == t)
			return path;
		
		List<Edge> unionEdge = new LinkedList<Edge>();
		
		unionEdge.addAll(s.incomingEdges);
		unionEdge.addAll(s.outgoingEdges);

		for(Edge e : unionEdge) {
			if(e.hasResidualCapacity() && !path.contains(e)) {
				path.add(e);
				List<Edge> result = getAugmentedPathDFS(g, e.end, t, path);
				
				if(result.size() > 0)
					return result;
			}
		}
		
		return Collections.emptyList();
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
					} else {
						e.status = EdgeStatus.BACK;
						path.remove(e);
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
						vertexQueue.add(x);
					} else {
						e.status = EdgeStatus.BACK;
						path.remove(e);
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
