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
	
	public GraphView(){
		
	}
	
	public void setGraph(Graph g){
		this.graph = g;
		updateGraph();
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
			Point p1 = new Point();
			Point p2 = new Point();
			if(vertexPoints.containsKey(opposite)){
				if(e.start.equals(v)){
					p1 = p;
					p2 = vertexPoints.get(opposite);
				} else {
					p1 = vertexPoints.get(opposite);
					p2 = p;
				}
			} else {
				p2 = new Point(p.x + 30, y);
				drawVertex(opposite, p2, g);
				vertexPoints.put(opposite, p2);
				if(e.start.equals(v)){
					p1 = p;
				} else {
					p1 = p2;
					p2 = p;
				}
				y = y + 20;
			}
			drawEdge(e, p1, p2, g);
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
		updateSize(p, g);
	}
	
	private void drawEdge(Edge e, Point p1, Point p2, Graphics2D g){
		p1 = new Point(p1.x * scale, p1.y * scale);
		p2 = new Point(p2.x * scale, p2.y * scale);
		
		g.setColor(e.color);
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
		
		double arrowLength = 4 * scale;
		double arrowAngle = 15;
		double angle;
		if(p1.y == p2.y){
			if(p1.x < p2.x){
				angle = 0;
			} else {
				angle = 180;
			}
		} else if(p1.x == p2.x){
			if(p1.y < p2.y){
				angle = 90;
			} else {
				angle = 270;
			}
		} else {
			double xDistance = Math.abs(p2.x-p1.x);
			double yDistance = Math.abs(p2.y-p1.y);
			double xyDistance = Math.sqrt(xDistance*xDistance + yDistance*yDistance);
			angle = Math.toDegrees(Math.acos(xDistance/xyDistance));
			if(p2.y < p1.y){
				angle = 360 - angle;
			}
			if(p2.x < p1.x){
				angle = 180 - angle;
			}
		}
		double y1 = Math.sin(Math.toRadians((angle + arrowAngle))) * arrowLength;
		double x1 = Math.cos(Math.toRadians((angle + arrowAngle))) * arrowLength;
		
		double y2 = Math.sin(Math.toRadians((-angle + arrowAngle))) * arrowLength;
		double x2 = Math.cos(Math.toRadians((-angle + arrowAngle))) * arrowLength;
		
		g.drawLine(p2.x, p2.y, (int)(p2.x - x1), (int)(p2.y - y1));
		g.drawLine(p2.x, p2.y, (int)(p2.x - x2), (int)(p2.y + y2));
		
		g.drawString(e.flow + "/" + e.capacity, (p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
		g.setColor(Color.black);
	}
	
	private void updateSize(Point p, Graphics g){
		Dimension d = new Dimension(getPreferredSize().width, getPreferredSize().height);
		if(p.x > d.width){
			d.width = p.x + 30;
		}
		if(p.y > d.height){
			d.height = p.y + 30;		
		}
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
		setSize(d);
	}
	
	public void updateGraph(){
		this.repaint();
	}
}
