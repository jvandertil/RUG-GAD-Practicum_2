package nl.rug.gad.practicum2.augmentedpaths;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import nl.rug.gad.practicum2.Edge;
import nl.rug.gad.practicum2.Graph;
import nl.rug.gad.practicum2.Vertex;

public abstract class AugmentedPath {

	public enum Method {DFS, BFS, DIJKSTRA}
	
	protected static Profiler profiler = new Profiler();
	
	public static final AugmentedPath NULL = new NullPath();
	
	public abstract List<Edge> getPath(Graph g, Vertex s, Vertex t);
	
	protected static List<Edge> findAugmentedPath(HashMap<Vertex, Edge> parents, Graph g, Vertex s, Vertex t){
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

	public static Profiler getProfiler() {
		return profiler;
	}

	private static final class NullPath extends AugmentedPath {
		@Override
		public List<Edge> getPath(Graph g, Vertex s, Vertex t) {
			return Collections.emptyList();
		}
	}
	
}
