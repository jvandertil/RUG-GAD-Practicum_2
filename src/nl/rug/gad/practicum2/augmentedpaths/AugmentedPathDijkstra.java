package nl.rug.gad.practicum2.augmentedpaths;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import nl.rug.gad.practicum2.Edge;
import nl.rug.gad.practicum2.Graph;
import nl.rug.gad.practicum2.Vertex;

public class AugmentedPathDijkstra extends AugmentedPath {

	public List<Edge> getAugmentedPath(Graph g, Vertex s, Vertex t){
		HashMap<Vertex, Edge> parents = new HashMap<Vertex, Edge>();
		PriorityQueue<Vertex> Q = new PriorityQueue<Vertex>();
		for(Vertex v : g.vertexList){
			if(v.equals(s)){
				v.maxFlow = Integer.MAX_VALUE;
			} else {
				v.maxFlow = 0;
			}
			Q.add(v);
			parents.put(v, null);
		}
		while(!Q.isEmpty()){
			Vertex u = Q.remove();
			profiler.visitedVertex();
			if(u.equals(t)){
				return findAugmentedPath(parents, g, s, t);
			}
			for(Edge e : u.getAllEdges()){
				profiler.visitedEdge();
				Vertex z = g.opposite(u, e);
				int r = Math.min(u.getResidualCapacity(e), u.maxFlow);
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
		return Collections.emptyList();
	}
	
}
