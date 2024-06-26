import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Grid{
	private Dimension dimension;
	private int spacing;
	private Color color;
	private List<Rectangle2D.Double> rectangles;

	
	public Grid(Dimension dimension, int spacing) {
		color = new Color(0,0,0,0.1f);
		scaleGrid(dimension, spacing);
		
	}
	
	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		if(dimension == null)
			this.dimension = new Dimension(0, 0);
		else
			this.dimension = dimension;
	}

	public void setSpacing(int spacing) {
		if(spacing <= 0)
			this.spacing = 20;
		else
			this.spacing = spacing;
	}
	
	public void scaleGrid(Dimension dimension, int spacing) {
		setSpacing(spacing);
		scaleGrid(dimension);
	}

	public void scaleGrid(Dimension dimension) {
		setDimension(dimension);
		createGrid();
	}
	
	private void createGrid() {		
		rectangles = new ArrayList<Rectangle2D.Double>();
		for(int x = spacing; x <= dimension.getWidth(); x += 2*spacing) {
			rectangles.add(new Rectangle2D.Double(x, -1, spacing, (int) dimension.getHeight()));
		}
	
		for(int y = spacing; y < dimension.getHeight(); y += 2*spacing) {
			rectangles.add(new Rectangle2D.Double(0, y, (int)dimension.getWidth(), spacing));

		}

	}
	
	public void draw (Graphics graphics) {
		graphics.setColor(color);
		for(Rectangle2D.Double rectangle : rectangles) {
			graphics.drawRect((int)rectangle.getX(), (int)rectangle.getY(), (int)rectangle.getWidth(), (int)rectangle.getHeight());
		}
		
	}
	
}
