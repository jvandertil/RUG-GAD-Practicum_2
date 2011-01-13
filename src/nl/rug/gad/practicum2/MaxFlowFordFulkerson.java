package nl.rug.gad.practicum2;

import java.util.LinkedList;

import nl.rug.gad.practicum2.Edge.Direction;

public class MaxFlowFordFulkerson {
	
	public enum Method {DFS, BFS}
	
	//MaxFlowFordFulkerson algorithm described on page 390
	public MaxFlowFordFulkerson(Graph g, Vertex s, Vertex t, Method m){
		for(Edge e : g.edgeList){
			e.flow = 0;
		}
		boolean stop = false;
		while(!stop){
			LinkedList<Edge> augmentedPath = getAugmentedPath(g, s, t, m);
			if(augmentedPath.size() > 0){
				int resCap = getResidualCapacity(augmentedPath);
				pushResCap(augmentedPath, resCap);
			} else {
				stop = true;
			}
		}
	}
	
	private LinkedList<Edge> getAugmentedPath(Graph g, Vertex s, Vertex t, Method m){
		LinkedList<Edge> augmentedPath = null;
		switch (m) {
		case DFS:
			augmentedPath = AugmentingPath.getAugmentedPathDFS(g, s, t);
			break;
		case BFS:
			augmentedPath = AugmentingPath.getAugmentedPathBFS(g, s, t);
			break;
		default:
			augmentedPath = new LinkedList<Edge>();
			break;
		}
		return augmentedPath;
	}
	
	//Computes the residual capacity of the augmenting path
	private int getResidualCapacity(LinkedList<Edge> augmentingPath){
		int maxFlow = Integer.MAX_VALUE;
		for(Edge e : augmentingPath){
			if(e.flow < maxFlow){
				maxFlow = e.flow;
			}
		}
		return maxFlow;
	}
	
	//Pushes the residual capacity along the augmenting path
	private void pushResCap(LinkedList<Edge> augmentedPath, int resCap){
		for(Edge e : augmentedPath){
			if(e.direction == Direction.FORWARD){
				e.flow = e.flow + resCap;
			} else if(e.direction == Direction.BACKWARD) {
				e.flow = e.flow - resCap;
			} else {
				System.out.println("Direction of the edge is NONE, that's not possible!");
			}
		}
	}
	
}
