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
		List<Edge> unionEdge;

		while (!vertexQueue.isEmpty()) {
			w = vertexQueue.poll();
			
			unionEdge = new LinkedList<Edge>();
			
			unionEdge.addAll(w.incomingEdges);
			unionEdge.addAll(w.outgoingEdges);
			
			for(Edge e : unionEdge) {
				Vertex next = e.end;
				if(next.status == VertexStatus.UNEXPLORED && e.hasResidualCapacity()) {
					vertexQueue.add(next);
					next.status = VertexStatus.EXPLORED;
					path.add(e);
					
					if(next == t)
						return path;
				}
			}
			
			unionEdge = null;
		}
		
		return path;
	}
}
