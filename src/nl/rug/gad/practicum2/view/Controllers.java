package nl.rug.gad.practicum2.view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nl.rug.gad.practicum2.MaxFlowFordFulkerson.Method;

public class Controllers extends JPanel {

	private static final long serialVersionUID = 2396396168572312934L;
	private JButton nextFlowButton = new JButton("Next flow");
	private JComboBox methodBox;
	private JButton resetButton = new JButton("Reset");
	private JLabel label = new JLabel("MaxFlow: 0");
	
	public Controllers(){
		add(nextFlowButton);
		methodBox = new JComboBox();
		methodBox.addItem(Method.DFS);
		methodBox.addItem(Method.BFS);
		methodBox.addItem(Method.DIJKSTRA);
		add(methodBox);
		add(resetButton);
		add(label);
	}
	
	public void addResetListener(ActionListener al){
		resetButton.addActionListener(al);
	}
	
	public void addNextFlowButtonListener(ActionListener al){
		nextFlowButton.addActionListener(al);
	}
	
	public Method getSelectedMethod(){
		return (Method) methodBox.getSelectedItem();
	}
	
	public void setLabelText(String text){
		label.setText(text);
	}
}
