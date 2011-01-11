package nl.rug.gad.practicum2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GraphBuilder {

	public static Graph buildGraph(File f) {
		BufferedReader reader = null;
		Graph g = new Graph();
		
		try {
			reader = new BufferedReader(new FileReader(f));
			
			String line = reader.readLine();
			
			String[] split = line.split(" ");
			int numVertexes = Integer.parseInt(split[0]);
			int numEdges = Integer.parseInt(split[1]);
			
			line = reader.readLine();
			split = line.split(" ");
			
			int start = Integer.parseInt(split[0]);
			int end = Integer.parseInt(split[1]);
			
			
			Vertex a = new Vertex(start);
			Vertex b = new Vertex(end);
			
			g.insert(a);
			g.insert(b);
			
			g.startPoint = a;
			g.endPoint = b;
			
			for(int m = 0; m < numEdges; ++m) {
				line = reader.readLine();
				
				if(line == null) {
					throw new IOException("Unexpected end of edge list.");
				}
				
				split = line.split(" ");
				
				if(split.length != 3) {
					throw new IOException("Malformed input: " + line);
				}
				
				int s = Integer.parseInt(split[0]); //Start vertex
				int e = Integer.parseInt(split[1]); //End vertex
				int c = Integer.parseInt(split[2]); //Capacity
				
				a = g.findVertex(s);
				b = g.findVertex(e);
				
				if(a == null) {
					a = new Vertex(s);
					g.insert(a);
				}
				
				if(b == null) {
					b = new Vertex(e);
					g.insert(b);
				}
				
				g.insert(new Edge(c), a, b);
			}
			
			if(g.vertexList.size() != numVertexes)
				throw new IOException(String.format("Not all vertexes are used? %d declared. %d added", numVertexes, g.vertexList.size()));
			
			
			return g;
		} catch(FileNotFoundException fnfe) {
			System.err.println("File not found: " + f);
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				reader.close();
			}catch(Exception e){}
		}
		
		return null;
	} 
}
