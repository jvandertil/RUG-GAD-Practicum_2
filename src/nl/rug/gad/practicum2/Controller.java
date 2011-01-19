package nl.rug.gad.practicum2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import nl.rug.gad.practicum2.view.View;


public class Controller {

	private View view;
	private MaxFlowFordFulkerson mfff;
	
	public Controller(){
		Graph graph = GraphBuilder.buildGraph(new File("input1.txt"));
		mfff = new MaxFlowFordFulkerson(graph, graph.startPoint, graph.endPoint);
		mfff.setGraphListener(updateViewListener());
		view = new View(graph);
		view.addNextFlowListener(nextFlowListener());
		view.addResetListener(resetListener());
		graph.printGraph();
	}
	
	private ActionListener nextFlowListener(){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mfff.nextFlow(view.getSelectedMethod());
			}
		};
	}
	
	private ActionListener resetListener(){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mfff.resetGraph(false);
			}
		};
	}
	
	private ActionListener updateViewListener(){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.updateGraph();
				view.setLabelText(e.getActionCommand());
			}
		};
	}
	
	public static void main(String args[]){
		new Controller();
	}
}
