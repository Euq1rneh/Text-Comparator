import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;

public class TextComparatorGUI implements ActionListener {
	
	private static String version = "v1.0";	
	
	private JFrame frame;
	private JPanel panel;
	private JTextArea originalTextField;
	private JTextArea compareTextField;
	private JScrollPane originalTextScrollPane;
	private JScrollPane compareTextScrollPane;
	private JScrollPane resultTextScrollPane;
	
	private JButton confirmBtn;
	private JTextPane result;
	
	public TextComparatorGUI() {
		createWindow();
		setLayout();
		setTextAreas();
		
		frame.setVisible(true);
	}
	
	private void createWindow() {
		frame = new JFrame();
		frame.setTitle("Text Comparator "+ version);				// window title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// sets closing behaviour
		frame.setSize(1024, 712);	 							// sets window dim
		frame.setResizable(false);								// prevents window from changing dim
		
		//ImageIcon windowIcon = new ImageIcon();				// create window icon
		//this.setIconImage(windowIcon.getImage());				// set window icon
		frame.getContentPane().setBackground(Color.DARK_GRAY);	// set background color

	}
	
	private void setLayout() {
		
		
	}
	
	//TODO change to a better layout
	private void setTextAreas() {
		panel = new JPanel(new GridLayout(3,3, 0, 0));
		
		confirmBtn = new JButton("Submit");
		result = new JTextPane();
		originalTextField = new JTextArea();
		compareTextField = new JTextArea();
		
		result.setEditable(false);
		
		originalTextScrollPane = new JScrollPane(originalTextField, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		compareTextScrollPane = new JScrollPane(compareTextField, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		resultTextScrollPane = new JScrollPane(result, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		confirmBtn.addActionListener(this);
		
		panel.add(new JLabel("Original text"));
		panel.add(new JPanel());
		panel.add(new JLabel("Comparison text"));
		panel.add(originalTextScrollPane);
		panel.add(confirmBtn);
		panel.add(compareTextScrollPane);
		panel.add(new JPanel());
		panel.add(resultTextScrollPane);
		panel.add(new JPanel());
		
		frame.add(panel);		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		TextComparator comparator = new TextComparator(originalTextField.getText(), compareTextField.getText());
		ArrayList<Integer> errorLog = comparator.checkText();
		result.setText(compareTextField.getText());
		
		// get to where the mistake is and highlight it
		//https://stackoverflow.com/questions/6530105/highlighting-text-in-java
		highlight(errorLog);
	}
	
    /**
     * Higlights the diferences between the two texts
     * @param errorLog the error log containing the indexes of the diferences
     */
    public void highlight(ArrayList<Integer> errorLog) {
        try {
        	result.getHighlighter().removeAllHighlights();
        	if(errorLog.size() > 0) {
        		for(int i = 0; i < errorLog.size(); i++) {
            		result.getHighlighter().addHighlight(errorLog.get(i), errorLog.get(i)+1, new DefaultHighlighter.DefaultHighlightPainter(new Color(255, 103, 92)));
        		}
        	}else {
        		result.getHighlighter().addHighlight(0, result.getText().length(), new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN));
        	}
        	

        } catch (BadLocationException e) {
        }
    }
}
