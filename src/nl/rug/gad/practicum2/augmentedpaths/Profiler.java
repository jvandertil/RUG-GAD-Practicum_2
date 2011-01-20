package nl.rug.gad.practicum2.augmentedpaths;

import nl.rug.gad.practicum2.Edge;
import nl.rug.gad.practicum2.Graph;

public class Profiler {

	private int maxFlow;
	private boolean maxFlowFound;
	private int visitedVertexes;
	private int visitedEdges;
	
	public void reset(){
		maxFlow = 0;
		maxFlowFound = false;
		visitedVertexes = 0;
		visitedEdges = 0;
	}
	
	public void update(Graph g){
		maxFlow = 0;
		for(Edge e : g.startPoint.outgoingEdges){
			maxFlow += e.flow;
		}
		for(Edge e : g.startPoint.incomingEdges){
			maxFlow += e.capacity - e.flow;
		}
	}
	
	public void visitedVertex(){
		visitedVertexes++;
	}
	
	public void visitedEdge(){
		visitedEdges++;
	}
	
	public void setMaxFlowFound(boolean value){
		maxFlowFound = value;
	}
	
	public boolean getMaxFlowFound(){
		return maxFlowFound;
	}
	
	public String getStatus(){
		String message = "";
		if(maxFlowFound){
			message += "Done!";
		}
		
		message += " MaxFlow: " + maxFlow;
		message += " Vertexes visited: " + visitedVertexes;
		message += " Edges visited: " + visitedEdges;
				
		return message;
	}
}
