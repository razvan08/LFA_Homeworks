import java.awt.*;
import java.io.Serializable;


public class Node {
	protected int x;
	protected int y;
	protected int radius;
	protected Color color;
	private String text;
	private String text1;

	public Node(int x, int y, Color color, int radius, String text,String text1) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
		setText(text);
		setText1(text1);
	}
	
    public void setText1(String text1){
		if(text1 == null) {
			this.text1 = "";
		}else {
			this.text1 = text1;
		}
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
		graphics.fillOval(x-radius, y-radius, 3*radius, 3*radius);
		graphics.setColor(Color.BLACK);
		graphics.drawOval(x-radius, y-radius, 3*radius, 3*radius);

		FontMetrics fontMetrics = graphics.getFontMetrics();
		int tempX = x - fontMetrics.stringWidth(text)/2;
		int tempY = y - fontMetrics.getHeight()/2 + fontMetrics.getAscent();
		graphics.drawString(text, tempX+6, tempY);
		graphics.drawString(text1,tempX+6,tempY+15);
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
