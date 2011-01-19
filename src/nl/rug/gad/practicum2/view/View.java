package nl.rug.gad.practicum2.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import nl.rug.gad.practicum2.Graph;
import nl.rug.gad.practicum2.MaxFlowFordFulkerson.Method;

public class View extends JFrame {

	private static final long serialVersionUID = -5640918753333009588L;
	
	private GraphView graphView;
	private Controllers controllers;
	
	public View(Graph g){
		super("Ford Fulkerson Algorithm!");
		setLayout(new GridLayout());
		
		graphView = new GraphView(g);
		controllers = new Controllers();
		
		JScrollPane scrollPane = new JScrollPane(graphView);
		Dimension d = new Dimension(400, 400);
		scrollPane.setPreferredSize(d);
		scrollPane.setMaximumSize(d);
		scrollPane.setMinimumSize(d);
		add(scrollPane);
		
		d.width = 100;
		controllers.setPreferredSize(d);
		controllers.setMaximumSize(d);
		controllers.setMinimumSize(d);
		add(controllers);
		
		setSize(700, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void setLabelText(String text){
		controllers.setLabelText(text);
	}
	
	public void addResetListener(ActionListener al){
		controllers.addResetListener(al);
	}
	
	public void addNextFlowListener(ActionListener al){
		controllers.addNextFlowButtonListener(al);
	}
	
	public Method getSelectedMethod(){
		return controllers.getSelectedMethod();
	}
	
	public void updateGraph(){
		graphView.updateGraph();
	}
}
