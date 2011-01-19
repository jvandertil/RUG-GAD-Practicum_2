package nl.rug.gad.practicum2.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import nl.rug.gad.practicum2.Edge;
import nl.rug.gad.practicum2.Graph;
import nl.rug.gad.practicum2.Vertex;

public class GraphView extends JPanel {
	
	private static final long serialVersionUID = -8380122751232428458L;
	private Graph graph;
	private HashMap<Vertex, Point> vertexPoints = new HashMap<Vertex, Point>();
	private HashMap<Integer, Integer> xymap = new HashMap<Integer, Integer>();
	private List<Vertex> drawn = new LinkedList<Vertex>();
	
	private int scale = 3;
	
	public GraphView(Graph g){
		setGraph(g);
	}
	
	public void setGraph(Graph g){
		this.graph = g;
	}
	
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		g2.clearRect(0, 0, this.getWidth(), this.getHeight());
		if(graph == null){
			return;
		}
		vertexPoints.clear();
		drawn.clear();
		xymap.clear();
		draw(graph.startPoint, new Point(10, 20), g2);
	}
	
	private void draw(Vertex v, Point p, Graphics2D g){
		if(!vertexPoints.containsKey(v)){
			vertexPoints.put(v, p);
		}
		p = vertexPoints.get(v);
		drawVertex(v, p, g);
		drawn.add(v);
		
		if(!xymap.containsKey(p.x)){
			xymap.put(p.x, p.y);
		}
		int y = xymap.get(p.x);
		
		for(Edge e : v.getAllEdges()){
			Vertex opposite = graph.opposite(v, e);
			if(vertexPoints.containsKey(opposite)){
				drawEdge(e, p, vertexPoints.get(opposite), g);
			} else {
				Point p2 = new Point(p.x + 30, y);
				drawVertex(opposite, p2, g);
				vertexPoints.put(opposite, p2);
				drawEdge(e, p, p2, g);
				y = y + 20;
			}
		}
		
		xymap.put(p.x, y);
		
		for(Edge e : v.getAllEdges()){
			Vertex opposite = graph.opposite(v, e);
			if(!drawn.contains(opposite)){
				Point p2 = vertexPoints.get(opposite);
				draw(opposite, p2, g);
			}
		}
	}
	
	private void drawVertex(Vertex v, Point p, Graphics2D g){
		if(v.equals(graph.startPoint)){
			g.setColor(Color.GREEN);
		} else if(v.equals(graph.endPoint)) {
			g.setColor(Color.red);
		}
		p = new Point(p.x * scale, p.y * scale);
		g.fillOval(p.x - (1 * scale), p.y - (1 * scale), 2 * scale, 2 * scale);
		g.setColor(Color.black);
		g.drawString(v.id + "", p.x + scale, p.y - scale);
		updateSize(p);
	}
	
	private void drawEdge(Edge e, Point p1, Point p2, Graphics2D g){
		g.setColor(e.color);
		g.drawLine(p1.x * scale, p1.y * scale, p2.x * scale, p2.y * scale);
		g.drawString(e.flow + "/" + e.capacity, ((p1.x + p2.x) / 2) * scale, ((p1.y + p2.y) / 2) * scale);
		g.setColor(Color.black);
	}
	
	private void updateSize(Point p){
		Dimension d = new Dimension(getWidth(), getHeight());
		if(p.x > d.width){
			d.width = p.x + 30;
		}
		if(p.y > d.height){
			d.height = p.y + 30;
		}
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
	}
	
	public void updateGraph(){
		this.repaint();
	}
}
