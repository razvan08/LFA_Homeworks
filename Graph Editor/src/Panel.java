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


public class Panel extends JPanel implements MouseListener, MouseMotionListener {

	private Grid grid;
	private boolean drawGrid;
	private Graph graph;
	private boolean mouseLeftButton = false;
	private int mouseX;
	private int mouseY;
	private Node nodeUnderCursor;
	private Edge edgeUnderCursor;
	private boolean chooseNodeB = false;
	private Node newEdgeNodeA;
	private Node newEdgeNodeB;

	public Panel(Graph graphic) {
		if(graphic == null) {
			graph = new Graph("Graph");
		}else {
			setGraph(graphic);
		}
		
		grid = new Grid(getSize(), 50);
		drawGrid = false;
		
		addMouseMotionListener(this);
		addMouseListener(this);
		setFocusable(true);
		requestFocus();
		
	}

	public void setGraph(Graph graph) {
		if(graph == null)
			this.graph = new Graph("Graph");
		else
			this.graph = graph;
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		if(grid != null && drawGrid) 
			grid.draw(graphics);

		if(graph != null)
			graph.draw(graphics);
	}

	public void createNewGraph() {
		setGraph(new Graph("Graph"));
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(mouseLeftButton) {
			moveGraph(e.getX(), e.getY());
		}else {
			setCursorEv(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		setCursorEv(e);
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

		setCursorEv(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			mouseLeftButton = false;
			finalizeEdge();
		}
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			chooseNodeB = false;
			if(nodeUnderCursor != null) {
				nodeMenu(e, nodeUnderCursor);
			}else if(edgeUnderCursor != null){
				edgeMenu(e, edgeUnderCursor);
			}else {
				plainMenu(e);
			}
		}
		setCursorEv(e);
	}


	private void plainMenu(MouseEvent e){
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem newNodeItem = new JMenuItem("New node");
		popupMenu.add(newNodeItem);
		newNodeItem.addActionListener((action)->{
			createNewNode(e.getX(), e.getY());
		});
		popupMenu.show(e.getComponent(), e.getX(), e.getY());
		
	}
	
	private void nodeMenu(MouseEvent e, Node node){
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem removeNodeItem = new JMenuItem("Remove node");
		popupMenu.add(removeNodeItem);
		removeNodeItem.addActionListener((action)->{
			removeNode(node);
		});
		
		popupMenu.addSeparator();
		
		JMenuItem addEdgeItem = new JMenuItem("Add edge");
		popupMenu.add(addEdgeItem);
		addEdgeItem.addActionListener((action)->{
			initializeEdge(node);
		});

		if(nodeUnderCursor instanceof Node) {
			popupMenu.addSeparator();

			JMenuItem changeNodeItem = new JMenuItem("Change node text");
			popupMenu.add(changeNodeItem);
			changeNodeItem.addActionListener((action)->{
				changeNodeText(node);
			});
			
		}
		
		popupMenu.show(e.getComponent(), e.getX(), e.getY());
		
	}
	

	private void edgeMenu(MouseEvent event, Edge edge) {
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem removeEdgeItem = new JMenuItem("Remove edge");
		popupMenu.add(removeEdgeItem);
		removeEdgeItem.addActionListener((action)->{
			removeEdge(edge);
		});

		if(edgeUnderCursor instanceof Edge) {
			popupMenu.addSeparator();

			JMenuItem changeEdgeItem = new JMenuItem("Change edge value");
			popupMenu.add(changeEdgeItem);
			changeEdgeItem.addActionListener((action) -> {
				changeEdgeText(edge);
			});
		}
		popupMenu.show(event.getComponent(), event.getX(), event.getY());
	}


	public void setCursorEv(MouseEvent e) {
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
	
	private void moveGraph(int currentX, int currentY) {
		int distanceX = currentX - mouseX;
		int distanceY = currentY - mouseY;
		
		if(nodeUnderCursor != null) {
			nodeUnderCursor.move(distanceX, distanceY);
		}else if(edgeUnderCursor != null){
			edgeUnderCursor.move(distanceX, distanceY);
		}else {
			graph.moveGraph(distanceX, distanceY);
		}
		
		mouseX = currentX;
		mouseY = currentY;
		repaint();
	}
	

	private void createNewNode(int currentX, int currentY) {
		try {

			String text = JOptionPane.showInputDialog(this, "Input text:", "New node", JOptionPane.QUESTION_MESSAGE);
			graph.addNode(new Node(currentX, currentY, Color.GREEN,12, text));
			repaint();
		}catch(NullPointerException e) {
			JOptionPane.showMessageDialog(this, "Operation canceled.", "Canceled", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	private void removeNode(Node node){
		graph.removeNode(node);
		repaint();
	}
	
	private void initializeEdge(Node node) {
		if(nodeUnderCursor != null) {
			newEdgeNodeA = node;
			chooseNodeB = true;
			setCursorEv(null);
		}
	}
	
	private void finalizeEdge() {
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

	private void changeNodeText(Node node) {
		String text = JOptionPane.showInputDialog(this, "Input text:", "Edit node", JOptionPane.QUESTION_MESSAGE);
		try {
			((Node)node).setText(text);
			repaint();
		}catch(ClassCastException e) {
			JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.INFORMATION_MESSAGE);
		}catch (NullPointerException e) {
			JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void changeEdgeText(Edge edge) {
		String text = JOptionPane.showInputDialog(this, "Input text:", "Edit edge", JOptionPane.QUESTION_MESSAGE);
		try {
			((Edge)edge).setText(text);
			repaint();
		}catch(ClassCastException ex) {
			JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.INFORMATION_MESSAGE);
		}catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(this, "Error", "Error", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
