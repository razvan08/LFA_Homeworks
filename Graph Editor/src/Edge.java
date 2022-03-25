import java.awt.*;
import java.io.Serializable;


public class Edge implements Serializable{

	private static final long serialVersionUID = -6972652167790425200L;

	protected Node nodeA;
	protected Node nodeB;
	protected String text;

	public Edge(Node a, Node b,String text) {
		nodeA = a;
		nodeB = b;
		this.text = text;
	}
	
	public Node getNodeA() {
		return nodeA;
	}

	public void setNodeA(Node nodeA) {
		this.nodeA = nodeA;
	}

	public Node getNodeB() {
		return nodeB;
	}

	public void setNodeB(Node nodeB) {
		this.nodeB = nodeB;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if(text == null){
			this.text = "";
		}else{
			this.text = text;
		}
	}

	public void draw(Graphics g) {
		int xa = nodeA.getX();
		int ya = nodeA.getY();
		int xb = nodeB.getX();
		int yb = nodeB.getY();
		
		g.setColor(Color.BLACK);
		g.drawLine(xa, ya, xb, yb);
		FontMetrics fm = g.getFontMetrics();
		int tx = ((xa+xb)/2) - fm.stringWidth(text)/2;
		int ty = ((ya+yb)/2+8) - fm.getHeight()/2 + fm.getAscent();
		g.drawString(text, tx, ty);
	}
	
	public boolean isUnderCursor(int mx, int my) {
		
		if( mx < Math.min(nodeA.getX(), nodeB.getX()) ||
			mx > Math.max(nodeA.getX(), nodeB.getX()) ||
			my < Math.min(nodeA.getY(), nodeB.getY()) ||
			my > Math.max(nodeA.getY(), nodeB.getY()) ) {
			return false;
		}
		
		
		int A = nodeB.getY() - nodeA.getY();
		int B = nodeB.getX() - nodeA.getX();
		
		double distance = Math.abs(A*mx - B*my + nodeB.getX()*nodeA.getY() - nodeB.getY()*nodeA.getX())/Math.sqrt(A*A+B*B);
		return distance <= 5;
	}
	
	public void move(int dx, int dy) {
		nodeA.move(dx, dy);
		nodeB.move(dx, dy);
	}
	
	@Override
	public String toString() {
		return "[" + "]: (" + nodeA.toString() + ") ===> (" + nodeB.toString() + ") ";
	}
}