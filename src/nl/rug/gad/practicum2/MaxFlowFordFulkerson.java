package nl.rug.gad.practicum2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import nl.rug.gad.practicum2.Edge.EdgeStatus;
import nl.rug.gad.practicum2.Vertex.VertexStatus;

public class MaxFlowFordFulkerson {
	
	public enum Method {DFS, BFS, DIJKSTRA}
	
	private Graph g;
	private Vertex s, t;
	
	private ActionListener listener;
	private boolean maxFlowFound = false;
	
	//MaxFlowFordFulkerson algorithm described on page 390
	public MaxFlowFordFulkerson(Graph g, Vertex s, Vertex t){
		this.g = g;
		this.s = s;
		this.t = t;
		for(Edge e : g.edgeList){
			e.flow = 0;
		}
	}
	
	public void nextFlow(Method m){
		//Reset all directions in graph
		resetGraph(true);
		List<Edge> augmentedPath = getAugmentedPath(m);
		//printAugmentedPath(augmentedPath);
		if(augmentedPath.size() > 0){
			int resCap = getResidualCapacity(augmentedPath);
			pushResCap(augmentedPath, resCap);
		} else {
			maxFlowFound = true;
		}
		graphChanged(); //Update View
	}
	
	private void printAugmentedPath(List<Edge> path){
		System.out.println("Augmented Path");
		for(Edge e : path){
			System.out.println(e.start.id + " to " + e.end.id);
		}
	}
	
	public void resetGraph(boolean statusOnly){
		maxFlowFound = false;
		for(Edge e : g.edgeList){
			e.status = EdgeStatus.UNEXPLORED;
			e.color = Color.black;
			if(!statusOnly){
				e.flow = 0;
			}
		}
		for(Vertex v : g.vertexList){
			v.status = VertexStatus.UNEXPLORED;
			if(!statusOnly){
				v.maxFlow = 0;
			}
		}
		if(!statusOnly){
			graphChanged(); //Update view
		}
	}
	
	private List<Edge> getAugmentedPath(Method m){
		List<Edge> augmentedPath = new LinkedList<Edge>();
		switch (m) {
		case DFS:
			augmentedPath = AugmentingPath.getAugmentedPathDFS(g, s, t);
			break;
		case BFS:
			augmentedPath = AugmentingPath.getAugmentedPathBFS(g, s, t);
			break;
		case DIJKSTRA:
			augmentedPath = AugmentingPath.getAugmentedPathDijkstra(g, s, t);
			break;
		default:
			augmentedPath = new LinkedList<Edge>();
			break;
		}
		for(Edge e : augmentedPath){
			e.color = Color.blue;
		}
		return augmentedPath;
	}
	
	//Computes the residual capacity of the augmenting path
	private int getResidualCapacity(List<Edge> augmentingPath){
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
	private void pushResCap(List<Edge> augmentedPath, int resCap){
		for(Edge e : augmentedPath){
			if(e.forward){
				e.flow = e.flow + resCap;
			} else {
				e.flow = e.flow - resCap;
			}
		}
	}
	
	private void graphChanged(){
		int maxFlow = 0;
		for(Edge e : g.startPoint.outgoingEdges){
			maxFlow += e.flow;
		}
		for(Edge e : g.startPoint.incomingEdges){
			maxFlow += e.capacity - e.flow;
		}
		String message = "";
		if(maxFlowFound){
			message = "Done! ";
		}
		message += "MaxFlow: " + maxFlow;
		ActionEvent ae = new ActionEvent(g.startPoint, g.startPoint.id, message);
		listener.actionPerformed(ae);
	}
	
	public void setGraphListener(ActionListener listener){
		this.listener = listener;
	}
	
}
