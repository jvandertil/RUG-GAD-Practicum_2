package nl.rug.gad.practicum2.augmentedpaths;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import nl.rug.gad.practicum2.Edge;
import nl.rug.gad.practicum2.Graph;
import nl.rug.gad.practicum2.Vertex;
import nl.rug.gad.practicum2.Edge.EdgeStatus;
import nl.rug.gad.practicum2.Vertex.VertexStatus;

public class AugmentedPathBFS extends AugmentedPath {

	public List<Edge> getPath(Graph g, Vertex s, Vertex t) {
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
			profiler.visitedVertex();
			unionEdge = w.getAllEdges();
			for (Edge e : unionEdge) {
				if (e.status == EdgeStatus.UNEXPLORED
						&& (w.getResidualCapacity(e) > 0)) {
					profiler.visitedEdge();
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
	
}
