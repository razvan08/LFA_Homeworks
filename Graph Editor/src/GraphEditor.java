import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


public class GraphEditor extends JFrame implements ActionListener{

	private static final long serialVersionUID = 508317535368185508L;
	
	private static final String APP_TITLE = "Graph Editor";

	public static void main(String[] args) {
		new GraphEditor();

	}

	GraphPanel graphPanel = new GraphPanel(null);

	JMenuItem newGraphMenuItem = new JMenuItem("New graph", KeyEvent.VK_N);

	private GraphEditor() {
		super(APP_TITLE);
		setSize(800, 600);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(graphPanel);
		UIManager.put("OptionPane.messageFont", new Font("Monospaced", Font.BOLD, 12));

		addActionListeners();
		
		setVisible(true);
	}
	
	private void addActionListeners() {
		newGraphMenuItem.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object eSource = e.getSource();
		
		if(eSource == newGraphMenuItem) {
			graphPanel.createNewGraph();
		}
	}
	


}
