package nl.rug.gad.practicum2;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
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
	
	public static List<Edge> getAugmentedPathDijkstra(Graph g, Vertex s, Vertex t){
		HashMap<Vertex, Edge> parents = new HashMap<Vertex, Edge>();
		PriorityQueue<Vertex> Q = new PriorityQueue<Vertex>();
		for(Vertex v : g.vertexList){
			if(v.equals(s)){
				v.maxFlow = Integer.MAX_VALUE;
			} else {
				v.maxFlow = 0;
			}
			if(!v.equals(t)){
				Q.add(v);
			}
			parents.put(v, null);
		}
		while(!Q.isEmpty()){
			Vertex u = Q.remove();
			for(Edge e : u.getAllEdges()){
				Vertex z = g.opposite(u, e);
				int r = Math.min(getResidualCapacity(u, e), u.maxFlow);
				if(r > z.maxFlow && !e.equals(parents.get(z))){
					z.maxFlow = r;
					parents.put(z, e);
					if(Q.contains(z)){
						Q.remove(z);
						Q.add(z);
					}
				}
			}
		}
		
		List<Edge> augmentedPath = new LinkedList<Edge>();
		Vertex iterator = t;
		Edge parent = parents.get(iterator);
		boolean sourceReached = false;
		while(parent != null){
			//System.out.println(iterator.id + " to " + g.opposite(iterator, parent).id);
			augmentedPath.add(parent);
			parent.status = EdgeStatus.DISCOVERY;
			iterator = g.opposite(iterator, parent);
			parent = parents.get(iterator);
			if(iterator.equals(s)){
				sourceReached = true;
			}
		}
		
		if(sourceReached){
			return augmentedPath;
		}
		return Collections.emptyList();
	}
}
