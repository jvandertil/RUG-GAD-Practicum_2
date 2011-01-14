package nl.rug.gad.practicum2;

import java.util.LinkedList;

import nl.rug.gad.practicum2.Edge.EdgeStatus;
import nl.rug.gad.practicum2.Vertex.VertexStatus;

public class MaxFlowFordFulkerson {
	
	public enum Method {DFS, BFS}
	
	//MaxFlowFordFulkerson algorithm described on page 390
	public MaxFlowFordFulkerson(Graph g, Vertex s, Vertex t, Method m){
		for(Edge e : g.edgeList){
			e.flow = 0;
		}
		boolean stop = false;
		while(!stop){
			//Reset all directions in graph
			resetEdges(g);
			LinkedList<Edge> augmentedPath = getAugmentedPath(g, s, t, m);
			if(augmentedPath.size() > 0){
				int resCap = getResidualCapacity(augmentedPath);
				System.out.println(resCap);
				pushResCap(augmentedPath, resCap);
			} else {
				stop = true;
			}
		}
	}
	
	private void resetEdges(Graph g){
		for(Edge e : g.edgeList){
			e.status = EdgeStatus.UNEXPLORED;
		}
		for(Vertex v : g.vertexList){
			v.status = VertexStatus.UNEXPLORED;
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
			int resCap;
			if(e.forward){
				resCap = e.capacity - e.flow;
			} else {
				resCap = e.flow;
			}
			if(resCap < maxFlow){
				maxFlow = resCap;
			}
		}
		return maxFlow;
	}
	
	//Pushes the residual capacity along the augmenting path
	private void pushResCap(LinkedList<Edge> augmentedPath, int resCap){
		for(Edge e : augmentedPath){
			if(e.forward){
				e.flow = e.flow + resCap;
			} else {
				e.flow = e.flow - resCap;
			}
		}
	}
	
}
