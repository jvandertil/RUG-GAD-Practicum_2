package nl.rug.gad.practicum2.augmentedpaths;

import java.util.HashMap;
import java.util.List;

import nl.rug.gad.practicum2.Edge;
import nl.rug.gad.practicum2.Graph;
import nl.rug.gad.practicum2.Vertex;
import nl.rug.gad.practicum2.Edge.EdgeStatus;
import nl.rug.gad.practicum2.Vertex.VertexStatus;

public class AugmentedPathDFS extends AugmentedPath {

	private boolean stop = false;
	
	public List<Edge> getAugmentedPath(Graph g, Vertex s, Vertex t){
		HashMap<Vertex, Edge> parents = getPathDFS(g, s, t, new HashMap<Vertex, Edge>());
		return findAugmentedPath(parents, g, s, t);
	}
	
	public HashMap<Vertex, Edge> getPathDFS(Graph g, Vertex s, Vertex t, HashMap<Vertex, Edge> parents) {
		s.status = VertexStatus.EXPLORED;
		profiler.visitedVertex();
		stop = false;
		for (Edge e : s.getAllEdges()) {
			if (e.status == EdgeStatus.UNEXPLORED
					&& s.getResidualCapacity(e) > 0) {
				profiler.visitedEdge();
				Vertex w = g.opposite(s, e);
				if (w.status == VertexStatus.UNEXPLORED) {
					e.status = EdgeStatus.DISCOVERY;
					//profiler.visitedVertex();
					parents.put(w, e);
					if (e.start.equals(s)) {
						e.forward = true;
					} else {
						e.forward = false;
					}
					if(s.equals(t)){
						stop = true;
						return parents;
					}
					getPathDFS(g, w, t, parents);
				} else {
					e.status = EdgeStatus.BACK;
				}
			}
			if(stop){
				return parents;
			}
		}
		return parents;
	}
}
