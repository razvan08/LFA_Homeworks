import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class App {
    private JFrame frame;
    private JTextField resultMessage;
    private JTextField patternText;
    private JTextField inputText;
    private JLabel patternLabel;
    private JLabel inputLabel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    App app = new App();
                    app.frame.setTitle("Regular Expressions");
                    app.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public App() {
        setupApplication();
    }
    private void setupApplication() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(0, 9, 14, 142));
        frame.setBounds(100, 100, 350, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        setupCheckButton();
        setupEnvironment();
    }
    public void setupEnvironment(){
        patternText = new JTextField();
        patternText.setBounds(100, 25, 155, 20);
        frame.getContentPane().add(patternText);
        patternText.setColumns(10);

        inputText = new JTextField();
        inputText.setBounds(100, 50, 155, 20);
        frame.getContentPane().add(inputText);
        inputText.setColumns(10);

        patternLabel = new JLabel("Pattern");
        patternLabel.setFont(new Font("Font", Font.PLAIN, 14));
        patternLabel.setBounds(50, 25, 60, 15);
        frame.getContentPane().add(patternLabel);

        inputLabel = new JLabel("Input");
        inputLabel.setFont(new Font("Font", Font.PLAIN, 14));
        inputLabel.setBounds(55, 50, 60, 15);
        frame.getContentPane().add(inputLabel);

        resultMessage = new JTextField();
        resultMessage.setBounds(100, 90, 155, 40);
        frame.getContentPane().add(resultMessage);
        resultMessage.setColumns(10);

        JLabel messageLabel = new JLabel("Message");
        messageLabel.setFont(new Font("Font",Font.PLAIN, 14));
        messageLabel.setBounds(40, 95, 60, 15);
        frame.getContentPane().add(messageLabel);
    }
    public void setupCheckButton(){
        JButton btn = new JButton("Check");
        btn.setForeground(new Color(255, 255, 255, 225));
        btn.setBackground(new Color(203, 9, 96, 225));
        btn.setFont(new Font("Font",  Font.PLAIN, 11));
        btn.setBounds(130, 150, 80, 20);
        frame.getContentPane().add(btn);

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==btn){
                    try {
                        RegularExpression expr = new RegularExpression();
                        String pattern = patternText.getText();
                        String line = inputText.getText();
                        resultMessage.setText(expr.isMatching(pattern, line));
                    }catch(Exception exception)
                    {
                        resultMessage.setText("Pattern error");
                    }
                }
            }

        });
    }
}

class RegularExpression {

    public String isMatching(String pattern, String line) {

        Pattern givenPattern = Pattern.compile(pattern);
        Matcher matcher = givenPattern.matcher(line);
        String isMAtch = "We have a match";
        boolean matchFound = false;

        while (matcher.find()) {
            matchFound = true;
        }

        if(matchFound == false){
            return ("No match");
        } else {
            return isMAtch;
        }
    }

}