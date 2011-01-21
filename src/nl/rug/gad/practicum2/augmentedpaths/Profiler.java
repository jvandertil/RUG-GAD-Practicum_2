package nl.rug.gad.practicum2.augmentedpaths;

import nl.rug.gad.practicum2.Edge;
import nl.rug.gad.practicum2.Graph;

public class Profiler {

	private int maxFlow;
	private boolean maxFlowFound;
	private int visitedVertexes;
	private int visitedEdges;
	private long totalTime;
	private int iterations;
	
	private long currentTime;
	
	public void reset(){
		maxFlow = 0;
		maxFlowFound = false;
		visitedVertexes = 0;
		visitedEdges = 0;
		totalTime = 0;
		currentTime = 0;
		iterations = 0;
	}
	
	public void startStopwatch(){
		currentTime = System.nanoTime();
	}
	
	public void pauseStopwatch(){
		if(!maxFlowFound){
			totalTime += System.nanoTime() - currentTime;
		}
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
		if(!maxFlowFound){
			visitedVertexes++;
		}
	}
	
	public void visitedEdge(){
		if(!maxFlowFound){
			visitedEdges++;
		}
	}
	
	public void incIterations(){
		if(!maxFlowFound){
			iterations++;
		}
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
		
		message += " M: " + maxFlow;
		message += " V: " + visitedVertexes;
		message += " E: " + visitedEdges;
		message += " I: " + iterations;
		message += " T: " + totalTime + " ns";
				
		return message;
	}
}
