package nl.rug.gad.practicum2;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import nl.rug.gad.practicum2.Edge.EdgeStatus;
import nl.rug.gad.practicum2.Vertex.VertexStatus;

public class AugmentingPath {

	public static List<Edge> getAugmentedPathDFS(Graph g, Vertex s, Vertex t){
		HashMap<Vertex, Edge> parents = getPathDFS(g, s, t, new HashMap<Vertex, Edge>());
		return getAugmentedPath(parents, g, s, t);
	}
	
	public static HashMap<Vertex, Edge> getPathDFS(Graph g, Vertex s, Vertex t, HashMap<Vertex, Edge> parents) {
		s.status = VertexStatus.EXPLORED;
		for (Edge e : s.getAllEdges()) {
			if (e.status == EdgeStatus.UNEXPLORED
					&& getResidualCapacity(s, e) > 0) {
				Vertex w = g.opposite(s, e);
				if (canContinue(w, e, t)) {
					e.status = EdgeStatus.DISCOVERY;
					parents.put(w, e);
					if (e.start.equals(s)) {
						e.forward = true;
					} else {
						e.forward = false;
					}
					getPathDFS(g, w, t, parents);
				} else {
					e.status = EdgeStatus.BACK;
				}
			}
		}
		return parents;
	}
	
	private static boolean canContinue(Vertex v, Edge source, Vertex destination) {
		for (Edge e : v.getAllEdges()) {
			if (((getResidualCapacity(v, e) > 0 && !e.equals(source)) || 
						v.equals(destination)) && 
				v.status == VertexStatus.UNEXPLORED) {
				return true;
			}
		}
		return false;
	}

	public static List<Edge> getAugmentedPathBFS(Graph g, Vertex s, Vertex t) {
		List<Edge> path = new LinkedList<Edge>();
		Queue<Vertex> vertexQueue = new LinkedList<Vertex>();
		
		//Map <x,y>. Vertex y discovered by following x
		Map<Vertex, Edge> discoveryMap = new HashMap<Vertex, Edge>(); 

		s.status = VertexStatus.EXPLORED;
		vertexQueue.add(s);

		Vertex w;
		List<Edge> unionEdge;

		while (!vertexQueue.isEmpty()) {
			w = vertexQueue.poll();

			unionEdge = w.getAllEdges();

			for (Edge e : unionEdge) {
				if (e.status == EdgeStatus.UNEXPLORED
						&& (getResidualCapacity(w, e) > 0)) {
					Vertex next = g.opposite(w, e);
					if (next.status == VertexStatus.UNEXPLORED) {
						vertexQueue.add(next);
						discoveryMap.put(next, e);
						next.status = VertexStatus.EXPLORED;
						e.status = EdgeStatus.DISCOVERY;
						
						if (e.start.equals(w)) {
							e.forward = true;
						} else {
							e.forward = false;
						}
						
						if (next == t) {
							//sink found. Trace back.
							boolean traceDone = false;
							Edge currentEdge;
							Vertex previousVertex = next;
							while(!traceDone) {
								currentEdge = discoveryMap.get(previousVertex);
								path.add(currentEdge);
								
								previousVertex = g.opposite(previousVertex, currentEdge);
								
								if(previousVertex == s)
									traceDone = true;
							}
							
							return path;
						}
					} else {
						e.status = EdgeStatus.BACK;
					}
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
                                        if (e.start.equals(u)) {
						e.forward = true;
					} else {
						e.forward = false;
					}
					if(Q.remove(z)){
						Q.add(z);
					}
				}
			}
		}
		return getAugmentedPath(parents, g, s, t);
	}
	
	private static List<Edge> getAugmentedPath(HashMap<Vertex, Edge> parents, Graph g, Vertex s, Vertex t){
		List<Edge> augmentedPath = new LinkedList<Edge>();
		Vertex iterator = t;
		Edge parent = parents.get(iterator);
		boolean sourceReached = false;
		while(parent != null){
			augmentedPath.add(parent);
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

	private static int getResidualCapacity(Vertex v, Edge e) {
		if (e.start.equals(v)) {
			return e.capacity - e.flow;
		} else {
			return e.flow;
		}
	}
}
