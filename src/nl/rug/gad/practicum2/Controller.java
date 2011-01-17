package nl.rug.gad.practicum2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import nl.rug.gad.practicum2.MaxFlowFordFulkerson.Method;

public class Controller {

	private View view;
	private Graph graph;
	private MaxFlowFordFulkerson mfff;
	
	public Controller(){
		graph = GraphBuilder.buildGraph(new File("input1.txt"));
		mfff = new MaxFlowFordFulkerson(graph, graph.startPoint, graph.endPoint, Method.DFS);
		mfff.setGraphListener(graphListener());
		view = new View(graph);
		view.addMouseListener(mouseListener());
		//mfff.start();
	}
	
	private MouseListener mouseListener(){
		return new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				mfff.nextFlow();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		};
	}
	
	private ActionListener graphListener(){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.updateGraph();
			}
		};
	}
	
	public static void main(String args[]){
		new Controller();
	}
}
