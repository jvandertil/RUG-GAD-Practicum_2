package nl.rug.gad.practicum2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import nl.rug.gad.practicum2.view.View;


public class Controller {

	private View view;
	private MaxFlowFordFulkerson mfff;
	
	public Controller(){
		view = new View();
		view.addNextFlowListener(nextFlowListener());
		view.addFindMaxFlowListener(findMaxFlowListener());
		view.addResetListener(resetListener());
		view.addLoadListener(loadFileListener());
	}
	
	private void loadGraphFile(String fileName){
		File file = new File(fileName);
		if(!file.exists()){
			JOptionPane.showMessageDialog(new JFrame(),
				    "File not found!",
				    "Warning!",
				    JOptionPane.WARNING_MESSAGE);
			return;
		}
		Graph graph = GraphBuilder.buildGraph(file);
		mfff = new MaxFlowFordFulkerson(graph, graph.startPoint, graph.endPoint);
		mfff.setGraphListener(updateViewListener());
		mfff.completeReset();
		view.loadGraph(graph);
	}
	
	private ActionListener loadFileListener(){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadGraphFile(view.getFileName());
			}
		};
	}
	
	private ActionListener findMaxFlowListener(){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mfff == null){
					JOptionPane.showMessageDialog(new JFrame(),
						    "No graph loaded yet!",
						    "Warning!",
						    JOptionPane.WARNING_MESSAGE);
					return;
				}
				mfff.findMaxFlow(view.getSelectedMethod());
			}
		};
	}
	
	private ActionListener nextFlowListener(){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mfff == null){
					JOptionPane.showMessageDialog(new JFrame(),
						    "No graph loaded yet!",
						    "Warning!",
						    JOptionPane.WARNING_MESSAGE);
					return;
				}
				mfff.nextFlow(view.getSelectedMethod());
			}
		};
	}
	
	private ActionListener resetListener(){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mfff == null){
					JOptionPane.showMessageDialog(new JFrame(),
						    "No graph loaded yet!",
						    "Warning!",
						    JOptionPane.WARNING_MESSAGE);
					return;
				}
				mfff.completeReset();
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
