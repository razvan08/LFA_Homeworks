import java.awt.GridLayout;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class App extends JPanel {

    protected Pattern pattern;
    protected Matcher matcher;
    protected JTextField patternTF, inputTF,matchTF;
    protected JCheckBox compiledOK;
    protected JRadioButton find, findAll;

    public static void main(String[] av) {
        JFrame frame = new JFrame("Regular Expressions");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        App app = new App();

        frame.setContentPane(app);

        frame.pack();

        frame.setLocation(200, 200);

        frame.setVisible(true);
    }

    public App() {
        super();

        JPanel top = new JPanel();

        top.add(new JLabel("Pattern:", JLabel.RIGHT));

        patternTF = new JTextField(20);

        patternTF.getDocument().addDocumentListener(new PatternListener());

        top.add(patternTF);

        top.add(new JLabel("Syntax OK?"));

        compiledOK = new JCheckBox();

        top.add(compiledOK);

        ChangeListener cl = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                MatchPattern();
            }
        };

        JPanel switchPane = new JPanel();
        ButtonGroup bg = new ButtonGroup();

        find = new JRadioButton("Find");
        find.addChangeListener(cl);
        bg.add(find);
        switchPane.add(find);

        findAll = new JRadioButton("Find All");

        findAll.addChangeListener(cl);
        bg.add(findAll);
        switchPane.add(findAll);

        JPanel strPane = new JPanel();

        strPane.add(new JLabel("String:", JLabel.RIGHT));
        inputTF = new JTextField(20);

        inputTF.getDocument().addDocumentListener(new InputListener());
        strPane.add(inputTF);
        strPane.add(new JLabel("Matches:"));

        matchTF = new JTextField(3);

        strPane.add(matchTF);
        setLayout(new GridLayout(0, 1, 5, 5));
        add(top);
        add(strPane);
        add(switchPane);
    }

    protected void setMatch(boolean isMatch) {
        if (isMatch == true) {
            matchTF.setText("Yes");

        } else {
            matchTF.setText("No");
        }
    }

    protected void setMatch(int match) {
        matchTF.setText(Integer.toString(match));
    }

    protected void CompileApp() {
        pattern = null;
        try {
            pattern = Pattern.compile(inputTF.getText());

            matcher = pattern.matcher("");

            compiledOK.setSelected(true);

        } catch (PatternSyntaxException ex) {
            compiledOK.setSelected(false);
        }
    }

    protected boolean MatchPattern() {
        if (pattern == null) {
            return false;
        }
        matcher.reset(inputTF.getText());

        if (find.isSelected() && matcher.find()) {
            setMatch(true);

            return true;
        }

        if (findAll.isSelected()) {
            int i = 0;
            while (matcher.find()) {
                ++i;
            }
            if (i > 0) {
                setMatch(i);

                return true;
            }

        }
        setMatch(false);

        return false;
    }

    class PatternListener implements DocumentListener {
        @Override
        public void changedUpdate(DocumentEvent ev) {
            CompileApp();
        }

        @Override
        public void insertUpdate(DocumentEvent ev) {
            CompileApp();
        }

        @Override
        public void removeUpdate(DocumentEvent ev) {
            CompileApp();
        }
    }

    class InputListener implements DocumentListener {
        @Override
        public void changedUpdate(DocumentEvent ev) {
            MatchPattern();
        }

        @Override
        public void insertUpdate(DocumentEvent ev) {
            MatchPattern();
        }

        @Override
        public void removeUpdate(DocumentEvent ev) {
            MatchPattern();
        }
    }
}