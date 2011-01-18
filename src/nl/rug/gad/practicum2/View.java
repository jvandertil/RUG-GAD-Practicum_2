package nl.rug.gad.practicum2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

public class View extends JFrame {

	private static final long serialVersionUID = -5640918753333009588L;
	private Graph graph;
	private HashMap<Vertex, Point> vertexPoints = new HashMap<Vertex, Point>();
	private HashMap<Integer, Integer> xymap = new HashMap<Integer, Integer>();
	private List<Vertex> drawn = new LinkedList<Vertex>();
	
	private int scale = 3;
	
	public View(Graph g){
		setSize(500, 300);
		setGraph(g);
		setVisible(true);
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
		drawVertex(v, p, g);
		vertexPoints.put(v, p);
		drawn.add(v);
		
		if(!xymap.containsKey(p.x)){
			xymap.put(p.x, p.y);
		}
		int y = xymap.get(p.x);
		
		for(Edge e : v.outgoingEdges){
			if(vertexPoints.containsKey(e.end)){
				drawEdge(e, p, vertexPoints.get(e.end), g);
			} else {
				Point p2 = new Point(p.x + 30, y);
				drawVertex(e.end, p2, g);
				vertexPoints.put(e.end, p2);
				drawEdge(e, p, p2, g);
				y = y + 20;
			}
		}
		for(Edge e : v.incomingEdges){
			if(vertexPoints.containsKey(e.start)){
				drawEdge(e, p, vertexPoints.get(e.start), g);
			} else {
				Point p2 = new Point(p.x + 30, y);
				drawVertex(e.start, p2, g);
				vertexPoints.put(e.start, p2);
				drawEdge(e, p, p2, g);
				y = y + 20;
			}
		}
		xymap.put(p.x, y);
		
		for(Edge e : v.outgoingEdges){
			if(!drawn.contains(e.end)){
				Point p2;
				if(vertexPoints.containsKey(e.end)){
					p2 = vertexPoints.get(e.end);
				} else {
					int nexty = xymap.get(p.x + 30) + 20;
					p2 = new Point(p.x + 30, nexty);
					xymap.put(p.x + 30, nexty);
				}
				draw(e.end, p2, g);
				return;
			}
		}
		for(Edge e : v.incomingEdges){
			if(!drawn.contains(e.start)){
				Point p2 = new Point(p.x + 30, p.y - 10);
				if(vertexPoints.containsKey(e.start)){
					p2 = vertexPoints.get(e.start);
				} else {
					int nexty = xymap.get(p.x + 30) + 20;
					p2 = new Point(p.x + 30, nexty);
					xymap.put(p.x + 30, nexty);
				}
				draw(e.start, p2, g);
				return;
			}
		}
	}
	
	private void drawVertex(Vertex v, Point p, Graphics2D g){
		g.drawOval(p.x * scale, p.y * scale, 3 * scale, 3 * scale);
		g.drawString(v.id + "", p.x * scale, p.y * scale);
	}
	
	private void drawEdge(Edge e, Point p1, Point p2, Graphics2D g){
		switch (e.status){
		case BACK:
			g.setColor(Color.RED);
			break;
		case UNEXPLORED:
			g.setColor(Color.BLACK);
			break;
		case DISCOVERY:
			g.setColor(Color.BLUE);
			break;
		default:
			g.setColor(Color.BLACK);
		}
		g.drawLine(p1.x * scale, p1.y * scale, p2.x * scale, p2.y * scale);
		
		g.drawString(e.flow + "/" + e.capacity, ((p1.x + p2.x) / 2) * scale, ((p1.y + p2.y) / 2) * scale);
		g.setColor(Color.black);
	}
	
	public void updateGraph(){
		this.repaint();
	}
}
