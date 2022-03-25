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

	private static final long serialVersionUID = 5673009196816218789L;

	private String graphTitle;
	private List<Node> nodes;
	private List<Edge> edges;

	
	public Graph(String title) {
		setGraphTitle(title);
		setNodes(new ArrayList<Node>());
		setEdges(new ArrayList<Edge>());
		
	}

	public String getGraphTitle() {
		return graphTitle;
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

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
	public void draw(Graphics g) {
		for (Edge edge : getEdges()) {
			edge.draw(g);
		}
		
		for (Node node : getNodes()) {
			node.draw(g);
		}
	}
	
	public void addNode(Node n) {
		nodes.add(n);
	}
	
	public void addEdge(Edge e) {
		for (Edge edge : edges) {
			if(e.equals(edge))
				return;
		}
		edges.add(e);
	}

	public Node findNodeUnderCursor(int mx, int my) {
		for (Node node : nodes) {
			if(node.isUnderCursor(mx, my)) {
				return node;
			}
		}
		return null;
	}
	
	public Edge findEdgeUnderCursor(int mx, int my) {
		for (Edge edge : edges) {
			if(edge.isUnderCursor(mx, my)) {
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
	
	public void moveGraph(int dx, int dy) {
		for (Node node : nodes) {
			node.move(dx, dy);
		}
	}

	@Override
	public String toString() {
		return graphTitle + "("+ nodes.size() + " nodes, " + edges.size() + " edges)";
	}
	
}
