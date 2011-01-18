package nl.rug.gad.practicum2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import nl.rug.gad.practicum2.Edge.EdgeStatus;
import nl.rug.gad.practicum2.Vertex.VertexStatus;

public class AugmentingPath {

	private static boolean stop = false;

	public static List<Edge> getAugmentedPathDFS(Graph g, Vertex s, Vertex t,
			List<Edge> path) {
		stop = false;
		List<Edge> unionEdge = s.getAllEdges();

		s.status = VertexStatus.EXPLORED;
		for (Edge e : unionEdge) {
			if (e.status == EdgeStatus.UNEXPLORED
					&& getResidualCapacity(s, e) > 0) {
				Vertex w = g.opposite(s, e);
				if (w.status == VertexStatus.UNEXPLORED && canContinue(w, e, t)) {
					e.status = EdgeStatus.DISCOVERY;
					path.add(e);
					if (e.start.equals(s)) {
						e.forward = true;
					} else {
						e.forward = false;
					}
					if (w.equals(t)) {
						stop = true;
						return path;
					}
					getAugmentedPathDFS(g, w, t, path);
				} else {
					e.status = EdgeStatus.BACK;
					path.remove(e);
				}
			}
			if (stop) {
				return path;
			}
		}
		return path;
	}

	public static List<Edge> getAugmentedPathBFS(Graph g, Vertex s, Vertex t) {
		List<Edge> path = new LinkedList<Edge>();
		Queue<Vertex> vertexQueue = new LinkedList<Vertex>();
		
		//Map <x,y>. Vertex y discovered via x
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
					Vertex next = e.end;
					if (next.status == VertexStatus.UNEXPLORED) {
						vertexQueue.add(next);
						discoveryMap.put(next, e);
						next.status = VertexStatus.EXPLORED;
						e.status = EdgeStatus.DISCOVERY;

						if (next == t) {
							//sink found. Trace back.
							boolean traceDone = false;
							Edge currentEdge;
							Vertex previousVertex = next;
							while(!traceDone) {
								currentEdge = discoveryMap.get(previousVertex);
								path.add(currentEdge);
								
								previousVertex = currentEdge.start;
								
								if(previousVertex == s)
									traceDone = true;
							}
							
							return path;
						}
					} else {
						e.status = EdgeStatus.BACK;
						e.forward = false;
					}
				}
			}

			unionEdge = null;
		}

		return path;
	}

	private static boolean canContinue(Vertex v, Edge source, Vertex destination) {
		for (Edge e : v.getAllEdges()) {
			if (v.equals(destination)) {
				return true;
			}
			if (getResidualCapacity(v, e) > 0 && !e.equals(source)) {
				return true;
			}
		}
		return false;
	}

	private static int getResidualCapacity(Vertex v, Edge e) {
		if (e.start.equals(v)) {
			return e.capacity - e.flow;
		} else {
			return e.flow;
		}
	}
}
