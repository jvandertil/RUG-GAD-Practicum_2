package nl.rug.gad.practicum2;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import nl.rug.gad.practicum2.Edge.EdgeStatus;
import nl.rug.gad.practicum2.Vertex.VertexStatus;

public class AugmentingPath {

	private static boolean stop = false;
	
	public static List<Edge> getAugmentedPathDFS(Graph g, Vertex s, Vertex t, List<Edge> path){
		stop = false;
		List<Edge> unionEdge = s.getAllEdges();
		
		s.status = VertexStatus.EXPLORED;
		for(Edge e : unionEdge) {
			if(e.status == EdgeStatus.UNEXPLORED && getResidualCapacity(s, e) > 0){
				Vertex w = g.opposite(s, e);
				if(w.status == VertexStatus.UNEXPLORED && canContinue(w, e, t)){
					e.status = EdgeStatus.DISCOVERY;
					path.add(e);
					if(e.start.equals(s)){
						e.forward = true;
					} else {
						e.forward = false;
					}
					if(w.equals(t)){
						stop = true;
						return path;
					}
					getAugmentedPathDFS(g, w, t, path);
				} else {
					e.status = EdgeStatus.BACK;
					path.remove(e);
				}
			}
			if(stop){
				return path;
			}
		}
		return path;
	}
	
	private static boolean canContinue(Vertex v, Edge source, Vertex destination){
		for(Edge e : v.getAllEdges()){
			if(v.equals(destination)){
				return true;
			}
			if(getResidualCapacity(v, e) > 0 && !e.equals(source)){
				return true;
			}
		}
		return false;
	}
	
	private static int getResidualCapacity(Vertex v, Edge e){
		if(e.start.equals(v)){
			return e.capacity - e.flow;
		} else {
			return e.flow;
		}
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
