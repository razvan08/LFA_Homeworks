import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Graph implements Serializable{
	private String graphTitle;
	private ArrayList<Node> nodes;
	private ArrayList<Edge> edges;

	public Graph(String title) {
		setGraphTitle(title);
		setNodes(new ArrayList<Node>());
		setEdges(new ArrayList<Edge>());
		
	}

	public void setGraphTitle(String graphTitle) {
		if(graphTitle == null)
			graphTitle = "";
		else
			this.graphTitle = graphTitle;
	}
	
	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}
	
	public void draw(Graphics graphics) {
		for (Edge edge : getEdges()) {
			edge.draw(graphics);
		}
		
		for (Node node : getNodes()) {
			node.draw(graphics);
		}
	}
	
	public void addNode(Node node) {
		nodes.add(node);
	}
	
	public void addEdge(Edge edge) {
		for (Edge edg : edges) {
			if(edge.equals(edg))
				return;
		}
		edges.add(edge);
	}

	public Node findNodeUnderCursor(int currentX, int currentY) {
		for (Node node : nodes) {
			if(node.isUnderCursor(currentX, currentY)) {
				return node;
			}
		}
		return null;
	}
	
	public Edge findEdgeUnderCursor(int currentX, int currentY) {
		for (Edge edge : edges) {
			if(edge.isUnderCursor(currentX, currentY)) {
				return edge;
			}
		}
		return null;
	}

	public void removeNode(Node nodeUnderCursor) {
		removeAttachedEdges(nodeUnderCursor);
		nodes.remove(nodeUnderCursor);		
	}
	
	protected void removeAttachedEdges(Node nodeUnderCursor) {
		edges.removeIf(e -> {
			return e.getNodeA().equals(nodeUnderCursor) 
				|| e.getNodeB().equals(nodeUnderCursor);
		});
	}
	
	public void removeEdge(Edge edgeUnderCursor) {
		edges.remove(edgeUnderCursor);
	}
	
	public void moveGraph(int distanceX, int distanceY) {
		for (Node node : nodes) {
			node.move(distanceX, distanceY);
		}
	}
}
