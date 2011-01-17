package nl.rug.gad.practicum2;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import nl.rug.gad.practicum2.Edge.EdgeStatus;
import nl.rug.gad.practicum2.Vertex.VertexStatus;

public class MaxFlowFordFulkerson {
	
	public enum Method {DFS, BFS}
	
	private Graph g;
	private Vertex s, t;
	private Method m;
	
	private ActionListener listener;
	
	//MaxFlowFordFulkerson algorithm described on page 390
	public MaxFlowFordFulkerson(Graph g, Vertex s, Vertex t, Method m){
		this.g = g;
		this.s = s;
		this.t = t;
		this.m = m;
		for(Edge e : g.edgeList){
			e.flow = 0;
		}
	}
	
	public void nextFlow(){
		//Reset all directions in graph
		resetEdges(g);
		LinkedList<Edge> augmentedPath = getAugmentedPath(g, s, t, m);
		if(augmentedPath.size() > 0){
			int resCap = getResidualCapacity(augmentedPath);
			pushResCap(augmentedPath, resCap);
		}
		graphChanged(); //Update View
	}
	
	private void resetEdges(Graph g){
		for(Edge e : g.edgeList){
			e.status = EdgeStatus.UNEXPLORED;
			e.color = Color.black;
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
	
	private void graphChanged(){
		listener.actionPerformed(null);
	}
	
	public void setGraphListener(ActionListener listener){
		this.listener = listener;
	}
	
}
