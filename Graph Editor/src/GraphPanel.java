import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import javax.swing.filechooser.FileNameExtensionFilter;


public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener,  ComponentListener {

	
	private static final long serialVersionUID = 3544581658578869882L;
	
	private Grid grid;
	private boolean drawGrid;
	
	private Graph graph;
	
	
	private boolean mouseLeftButton = false;
	@SuppressWarnings("unused")
	private boolean mouseRightButton = false;	
	
	private int mouseX;
	private int mouseY;
	
	private Node nodeUnderCursor;
	private Edge edgeUnderCursor;
	
	private boolean chooseNodeB = false;
	private Node newEdgeNodeA;
	private Node newEdgeNodeB;

	public GraphPanel(Graph g) {
		if(g == null) {
			graph = new Graph("Graf");
		}else {
			setGraph(g);
		}
		
		grid = new Grid(getSize(), 50);
		drawGrid = false;
		
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		
		addComponentListener(this);
		
		setFocusable(true);
		requestFocus();
		
	}
	
	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		if(graph == null)
			this.graph = new Graph("Graph");
		else
			this.graph = graph;
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(grid != null && drawGrid) 
			grid.draw(g);

		if(graph != null)
			graph.draw(g);
	}

	public void createNewGraph() {
		setGraph(new Graph("Graph"));
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(mouseLeftButton) {
			moveGraphDrag(e.getX(), e.getY());
		}else {
			setMouseCursor(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		setMouseCursor(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			mouseLeftButton = true;
		}
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			mouseRightButton = true;
		}
		
		setMouseCursor(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			mouseLeftButton = false;
			finalizeAddEdge();
		}
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			mouseRightButton = false;
			chooseNodeB = false;
			if(nodeUnderCursor != null) {
				createNodePopupMenu(e, nodeUnderCursor);
			}else if(edgeUnderCursor != null){
				createEdgePopupMenu(e, edgeUnderCursor);
			}else {
				createPlainPopupMenu(e);
			}
		}
		setMouseCursor(e);
	}


	private void createPlainPopupMenu(MouseEvent e){
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem newNodeMenuItem = new JMenuItem("New node");
		popupMenu.add(newNodeMenuItem);
		newNodeMenuItem.addActionListener((action)->{
			createNewNode(e.getX(), e.getY());
		});
		popupMenu.show(e.getComponent(), e.getX(), e.getY());
		
	}
	
	private void createNodePopupMenu(MouseEvent e, Node n){
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem removeNodeMenuItem = new JMenuItem("Remove node");
		popupMenu.add(removeNodeMenuItem);
		removeNodeMenuItem.addActionListener((action)->{
			removeNode(n);
		});
		
		popupMenu.addSeparator();
		
		JMenuItem addEdgeMenuItem = new JMenuItem("Add edge");
		popupMenu.add(addEdgeMenuItem);
		addEdgeMenuItem.addActionListener((action)->{
			initializeAddEdge(n);
		});

		if(nodeUnderCursor instanceof Node) {
			popupMenu.addSeparator();

			JMenuItem changeTextMenuItem = new JMenuItem("Change node text");
			popupMenu.add(changeTextMenuItem);
			changeTextMenuItem.addActionListener((action)->{		
				changeNodeText(n);
			});
			
		}
		
		popupMenu.show(e.getComponent(), e.getX(), e.getY());
		
	}
	

	private void createEdgePopupMenu(MouseEvent event, Edge e) {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem removeEdgeMenuItem = new JMenuItem("Remove edge");
		popupMenu.add(removeEdgeMenuItem);
		removeEdgeMenuItem.addActionListener((action)->{
			removeEdge(e);
		});

		if(edgeUnderCursor instanceof Edge) {
			popupMenu.addSeparator();

			JMenuItem changeTextMenuItem = new JMenuItem("Change node text");
			popupMenu.add(changeTextMenuItem);
			changeTextMenuItem.addActionListener((action) -> {
				changeEdgeText(e);
			});
		}
		popupMenu.show(event.getComponent(), event.getX(), event.getY());
	}


	public void setMouseCursor(MouseEvent e) {
		if(e != null) {
			nodeUnderCursor = graph.findNodeUnderCursor(e.getX(), e.getY());
			if(nodeUnderCursor == null) {
				edgeUnderCursor = graph.findEdgeUnderCursor(e.getX(), e.getY());
			}
			mouseX = e.getX();
			mouseY = e.getY();
		}
		
		int mouseCursor;
		if (nodeUnderCursor != null) {
			mouseCursor = Cursor.HAND_CURSOR;
		}else if(edgeUnderCursor != null) {
			mouseCursor = Cursor.CROSSHAIR_CURSOR;
		}else if(chooseNodeB) {
			mouseCursor = Cursor.WAIT_CURSOR;
		} else if (mouseLeftButton) {
			mouseCursor = Cursor.MOVE_CURSOR;
		} else {
			mouseCursor = Cursor.DEFAULT_CURSOR;
		}
		setCursor(Cursor.getPredefinedCursor(mouseCursor));
		
	}
	
	private void moveGraphDrag(int mx, int my) {
		int dx = mx - mouseX;
		int dy = my - mouseY;
		
		if(nodeUnderCursor != null) {
			nodeUnderCursor.move(dx, dy);
		}else if(edgeUnderCursor != null){
			edgeUnderCursor.move(dx, dy);
		}else {
			graph.moveGraph(dx, dy);
		}
		
		mouseX = mx;
		mouseY = my;
		repaint();
	}
	

	
	private void createNewNode(int mx, int my) {
		try {

			String text = JOptionPane.showInputDialog(this, "Input text:", "New node", JOptionPane.QUESTION_MESSAGE);
			graph.addNode(new Node(mx, my, Color.GREEN,12, text));
			repaint();
		}catch(NullPointerException e) {
			JOptionPane.showMessageDialog(this, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	private void removeNode(Node n){
		graph.removeNode(n);
		repaint();
	}
	
	private void initializeAddEdge(Node n) {
		if(nodeUnderCursor != null) {
			newEdgeNodeA = n;
			chooseNodeB = true;
			setMouseCursor(null);
		}
	}
	
	private void finalizeAddEdge() {
		if(chooseNodeB) {
			if(nodeUnderCursor != null) {
				if(nodeUnderCursor.equals(newEdgeNodeA)) {
					JOptionPane.showMessageDialog(this, "Choose different node!", "Error!", JOptionPane.ERROR_MESSAGE);
				}else {
					try {
						newEdgeNodeB = nodeUnderCursor;
						    String text = JOptionPane.showInputDialog(this, "Input text:", "New edge", JOptionPane.QUESTION_MESSAGE);
							graph.addEdge(new Edge(newEdgeNodeA, newEdgeNodeB,text));
						repaint();
					}catch (NullPointerException e){
						JOptionPane.showMessageDialog(this, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
					}
					
				}
			}
			chooseNodeB = false;
		}
	}
	
	private void removeEdge(Edge e) {
		graph.removeEdge(e);
		repaint();
	}
	


	private void changeNodeText(Node n) {
		String text = JOptionPane.showInputDialog(this, "Input text:", "Edit node", JOptionPane.QUESTION_MESSAGE);
		try {
			((Node)n).setText(text);
			repaint();
		}catch(ClassCastException e) {
			JOptionPane.showMessageDialog(this, "This node cannot have text.", "Error!", JOptionPane.INFORMATION_MESSAGE);
		}catch (NullPointerException e) {
			JOptionPane.showMessageDialog(this, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void changeEdgeText(Edge e) {
		String text = JOptionPane.showInputDialog(this, "Input text:", "Edit edge", JOptionPane.QUESTION_MESSAGE);
		try {
			((Edge)e).setText(text);
			repaint();
		}catch(ClassCastException ex) {
			JOptionPane.showMessageDialog(this, "This node cannot have text.", "Error!", JOptionPane.INFORMATION_MESSAGE);
		}catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		Object eSource = e.getSource();
		if(eSource == this && drawGrid) {
			grid.scaleGrid(getSize());
			repaint();
		}
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
