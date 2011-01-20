package nl.rug.gad.practicum2.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import nl.rug.gad.practicum2.augmentedpaths.AugmentedPath.Method;

public class Controllers extends JPanel {

	private static final long serialVersionUID = 2396396168572312934L;
	private JButton nextFlowButton = new JButton("Next flow");
	private JButton findMaxFlowButton = new JButton("Max flow");
	private JComboBox methodBox;
	private JButton resetButton = new JButton("Reset");
	private JLabel label = new JLabel("MaxFlow: 0");
	private JButton loadButton = new JButton("Load");
	private JTextField inputFileField = new JTextField("input1.txt");
	
	private Box topBox = Box.createHorizontalBox();
	private Box middleBox = Box.createHorizontalBox();
	private Box bottomBox = Box.createHorizontalBox();
	
	public Controllers(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Dimension d = new Dimension(400, 27);
		//topBox.setPreferredSize(d);
		topBox.setMaximumSize(d);
		//topBox.setMinimumSize(d);
		middleBox.setMaximumSize(d);
		bottomBox.setMaximumSize(d);
		add(topBox);
		add(middleBox);
		add(bottomBox);
		
		topBox.add(nextFlowButton);
		methodBox = new JComboBox();
		methodBox.addItem(Method.DFS);
		methodBox.addItem(Method.BFS);
		methodBox.addItem(Method.DIJKSTRA);
		topBox.add(findMaxFlowButton);
		topBox.add(methodBox);
		topBox.add(resetButton);
		
		middleBox.add(loadButton);
		
		//inputFileField.setPreferredSize(d);
		//inputFileField.setMaximumSize(d);
		//inputFileField.setMinimumSize(d);
		middleBox.add(inputFileField);
		
		bottomBox.add(label);
		
		//add(Box.createVerticalGlue());

	}
	
	public void addFindMaxFlowListener(ActionListener al){
		findMaxFlowButton.addActionListener(al);
	}
	
	public void addResetListener(ActionListener al){
		resetButton.addActionListener(al);
	}
	
	public String getFileName(){
		return inputFileField.getText();
	}
	
	public void addLoadListener(ActionListener al){
		loadButton.addActionListener(al);
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
