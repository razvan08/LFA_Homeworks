import java.awt.*;
import java.io.Serializable;


public class Node implements Serializable{
	protected int x;
	protected int y;
	protected int radius;
	protected Color color;
	private String text;

	public Node(int x, int y, Color color, int radius, String text) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
		setText(text);
	}
	

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setText(String text){
		if(text == null) {
			this.text = "";
		}else {
			this.text = text;
		}

	}

	public void draw(Graphics graphics) {
		graphics.setColor(color);
		graphics.fillOval(x-radius, y-radius, 2*radius, 2*radius);
		graphics.setColor(Color.BLACK);
		graphics.drawOval(x-radius, y-radius, 2*radius, 2*radius);

		FontMetrics fontMetrics = graphics.getFontMetrics();
		int tempX = x - fontMetrics.stringWidth(text)/2;
		int tempY = y - fontMetrics.getHeight()/2 + fontMetrics.getAscent();
		graphics.drawString(text, tempX, tempY);
	}

	public boolean isUnderCursor(int currentX, int currentY) {
		int auxPx = x - currentX;
		int auxPy = y - currentY;
		
		return auxPx*auxPx + auxPy*auxPy <= radius*radius;
	}

	public void move(int distanceX, int distanceY) {
		x += distanceX;
		y += distanceY;
	}
}
