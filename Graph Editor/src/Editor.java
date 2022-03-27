import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import javax.swing.JMenuItem;
import javax.swing.UIManager;


public class Editor extends JFrame implements ActionListener{

	public static void main(String[] args) {
		new Editor();
	}

	Panel graphPanel = new Panel(null);

	JMenuItem newGraphItem = new JMenuItem("New graph", KeyEvent.VK_N);

	private Editor() {
		super();
		setTitle("Graph Editor");
		setSize(800, 600);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(graphPanel);
		UIManager.put("Option", new Font("Message", Font.ITALIC, 12));

		addActionListeners();
		
		setVisible(true);
	}
	
	private void addActionListeners() {
		newGraphItem.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == newGraphItem) {
			graphPanel.createNewGraph();
		}
	}
	


}
