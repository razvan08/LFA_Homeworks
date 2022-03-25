import java.awt.*;
import java.io.Serializable;


public class Node implements Serializable{

	private static final long serialVersionUID = -7357466511459361679L;

	protected int x;
	protected int y;
	protected int r;
	protected Color color;
	private String text;

	public Node(int x, int y, Color c, int r, String text) {
		this.x = x;
		this.y = y;
		this.r = r;
		color = c;
		setText(text);
	}
	

	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public void setText(String text){
		if(text == null) {
			this.text = "";
		}else {
			this.text = text;
		}

	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(x-r, y-r, r+r, r+r);
		g.setColor(Color.BLACK);
		g.drawOval(x-r, y-r, r+r, r+r);

		FontMetrics fm = g.getFontMetrics();
		int tx = x - fm.stringWidth(text)/2;
		int ty = y - fm.getHeight()/2 + fm.getAscent();
		g.drawString(text, tx, ty);
	}

	public boolean isUnderCursor(int mx, int my) {
		int a = x - mx;
		int b = y - my;
		
		return a*a + b*b <= r*r;
	}

	public void move(int dx, int dy) {
		x += dx;
		y += dy;
	}

	@Override
	public String toString() {
		String colorHex = "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
		return super.toString() + "{r: " + Integer.toString(r) + ", c: " + colorHex + ", t: " + text + "}";
	}
	
}
