import java.awt.*;
import java.io.Serializable;


public class Edge{
	protected Node NodeA;
	protected Node NodeB;
	protected String text;

	public Edge(Node a, Node b,String text) {
		NodeA = a;
		NodeB = b;
		this.text = text;
	}
	
	public Node getNodeA() {
		return NodeA;
	}

	public Node getNodeB() {
		return NodeB;
	}


	public void setText(String text) {
		if(text == null){
			this.text = "";
		}else{
			this.text = text;
		}
	}

	public void draw(Graphics graphics) {
		int xa = NodeA.getX();
		int ya = NodeA.getY();
		int xb = NodeB.getX();
		int yb = NodeB.getY();
		
		graphics.setColor(Color.BLACK);
		graphics.drawLine(xa, ya, xb, yb);
		FontMetrics fontMetrics = graphics.getFontMetrics();
		int tx = ((xa+xb)/2) - fontMetrics.stringWidth(text)/2;
		int ty = ((ya+yb)/2+8) - fontMetrics.getHeight()/2 + fontMetrics.getAscent();
		graphics.drawString(text, tx, ty);
	}
	
	public boolean isUnderCursor(int currentX, int currentY) {
		
		if( currentX < Math.min(NodeA.getX(), NodeB.getX()) ||
			currentX > Math.max(NodeA.getX(), NodeB.getX()) ||
			currentY < Math.min(NodeA.getY(), NodeB.getY()) ||
			currentY > Math.max(NodeA.getY(), NodeB.getY()) ) {
			return false;
		}
		
		
		int A = NodeB.getY() - NodeA.getY();
		int B = NodeB.getX() - NodeA.getX();
		
		double distance = Math.abs(A*currentX - B*currentY + NodeB.getX()*NodeA.getY() - NodeB.getY()*NodeA.getX())/Math.sqrt(A*A+B*B);
		return distance <= 5;
	}
	
	public void move(int distanceX, int distanceY) {
		NodeA.move(distanceX, distanceY);
		NodeB.move(distanceX, distanceY);
	}
}