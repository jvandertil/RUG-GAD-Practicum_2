package nl.rug.gad.practicum2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import nl.rug.gad.practicum2.Edge.EdgeStatus;
import nl.rug.gad.practicum2.Vertex.VertexStatus;
import nl.rug.gad.practicum2.augmentedpaths.AugmentedPath;
import nl.rug.gad.practicum2.augmentedpaths.AugmentedPathBFS;
import nl.rug.gad.practicum2.augmentedpaths.AugmentedPathDFS;
import nl.rug.gad.practicum2.augmentedpaths.AugmentedPathDijkstra;
import nl.rug.gad.practicum2.augmentedpaths.AugmentedPath.Method;

public class MaxFlowFordFulkerson {
	
	private Graph g;
	private Vertex s, t;
	
	private ActionListener listener;
	
	//MaxFlowFordFulkerson algorithm described on page 390
	public MaxFlowFordFulkerson(Graph g, Vertex s, Vertex t){
		this.g = g;
		this.s = s;
		this.t = t;
		for(Edge e : g.edgeList){
			e.flow = 0;
		}
	}
	
	public void findMaxFlow(Method m){
		while(!AugmentedPath.getProfiler().getMaxFlowFound()){
			nextFlow(m);
		}
	}
	
	public void nextFlow(Method m){		
		//Reset all directions in graph
		resetGraph(true);
		List<Edge> augmentedPath = getAugmentedPath(m);
		if(augmentedPath.size() > 0){
			int resCap = getResidualCapacity(augmentedPath);
			pushResCap(augmentedPath, resCap);
		} else {
			AugmentedPath.getProfiler().setMaxFlowFound(true);
		}
		graphChanged(); //Update View
	}
	
	public void resetGraph(boolean statusOnly){
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
			AugmentedPath.getProfiler().reset();
			graphChanged(); //Update view
		}
	}
	
	private List<Edge> getAugmentedPath(Method m){
		AugmentedPath ap;
		switch (m) {
		case DFS:
			ap = new AugmentedPathDFS();
			break;
		case BFS:
			ap = new AugmentedPathBFS();
			break;
		case DIJKSTRA:
			ap = new AugmentedPathDijkstra();
			break;
		default:
			ap = AugmentedPath.NULL;
			break;
		}
		return ap.getPath(g, s, t);
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
			e.color = Color.blue;
			if(e.forward){
				e.flow = e.flow + resCap;
			} else {
				e.flow = e.flow - resCap;
			}
		}
	}
	
	private void graphChanged(){
		AugmentedPath.getProfiler().update(g);
		ActionEvent ae = new ActionEvent(g.startPoint, g.startPoint.id, AugmentedPath.getProfiler().getStatus());
		listener.actionPerformed(ae);
	}
	
	public void setGraphListener(ActionListener listener){
		this.listener = listener;
	}
	
}
