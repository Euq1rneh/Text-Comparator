import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
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

public class TextComparatorGUI implements ActionListener {
	
	private static String version = "v1.0";	
	
	private JFrame frame;
	private JTextArea originalTextField;
	private JTextArea compareTextField;
	private JScrollPane originalTextScrollPane;
	private JScrollPane compareTextScrollPane;
	private JScrollPane resultTextScrollPane;
	
	private JButton confirmBtn;
	private JTextPane result;
	
	Dimension DimMax;
	
	public TextComparatorGUI() {
		setTextAreas();
		createWindow();
		setDim();
		
	}
	
	private void setDim() {
		DimMax = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setMaximumSize(DimMax);
		frame.getContentPane().setSize(DimMax);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	private void createWindow() {
		frame = new JFrame("Text Comparator "+ version);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// sets closing behaviour
		setLayout(frame.getContentPane());
		frame.setResizable(false);									// prevents window from changing dim
		
		frame.pack();
		frame.setVisible(true);
		//ImageIcon windowIcon = new ImageIcon();					// create window icon
		//this.setIconImage(windowIcon.getImage());					// set window icon

	}
	
	private void setLayout(Container pane) {	
		
		pane.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		
		// first Label
		JLabel label = new JLabel("Original Text");
		label.setFont(new Font("Arial", Font.BOLD, 24));
		constraints.gridx = 0;
		constraints.gridy = 0;
		
		constraints.weightx = 1;
		constraints.weighty = 1;
		
		
		constraints.insets = new Insets(-50,100,-250,0);
		pane.add(label, constraints);
		
		// first box
		constraints.gridx = 0;
		constraints.gridy = 1;
		
		constraints.weightx = 1;
		constraints.weighty = 1;
		
		constraints.ipadx = 400;      
		constraints.ipady = 293; 
		
		constraints.insets = new Insets(-50,100,0,0);
		pane.add(originalTextScrollPane, constraints);
		
		
		// Second Label
		JLabel label2 = new JLabel("Comparison Text");
		label2.setFont(new Font("Arial", Font.BOLD, 24));
		constraints.gridx = 2;
		constraints.gridy = 0;

		constraints.weightx = 1;
		constraints.weighty = 1;

		constraints.insets = new Insets(-50,0,-250,-300);
		pane.add(label2, constraints);
		
		// second box
		constraints.gridx = 2;
		constraints.gridy = 1;
		
		constraints.weightx = 1;
		constraints.weighty = 1;
		
		constraints.ipadx = 400;      
		constraints.ipady = 293;
		
		constraints.insets = new Insets(-50,0,0,100);
		pane.add(compareTextScrollPane, constraints);
		
		// button
		constraints.ipady = -1;       
		constraints.ipadx = 0; 
		
		constraints.weighty = 1.0;
		
		constraints.anchor = GridBagConstraints.CENTER;
		
		constraints.insets = new Insets(10,0,0,0);
		
		constraints.gridx = 1;       
		constraints.gridy = 1;       
		pane.add(confirmBtn, constraints);

		
		// result box
		constraints.ipady = 40;
		constraints.ipadx = 600;
		
		constraints.weightx = 0.0;
		constraints.gridwidth = 1;
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		
		pane.add(resultTextScrollPane, constraints);
		
		confirmBtn.addActionListener(this);	
	}
	
	//TODO change to a better layout
	private void setTextAreas() {
		
		confirmBtn = new JButton("Submit");
		result = new JTextPane();
		originalTextField = new JTextArea();
		compareTextField = new JTextArea();
		
		originalTextField.setSize(293, 293);
		compareTextField.setSize(293, 293);
		
		
		result.setEditable(false);
		
		originalTextScrollPane = new JScrollPane(originalTextField, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		compareTextScrollPane = new JScrollPane(compareTextField, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		resultTextScrollPane = new JScrollPane(result, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		TextComparator comparator = new TextComparator(originalTextField.getText(), compareTextField.getText());
		ArrayList<Integer> errorLog = comparator.checkText();
		result.setText(compareTextField.getText());
		
		// get to where the mistake is and highlight it
		//https://stackoverflow.com/questions/6530105/highlighting-text-in-java
		highlight(errorLog);
		compareTextField.setEditable(false);
		originalTextField.setEditable(false);
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
        			if(errorLog.get(i) > result.getText().length()) {
        				result.setText(result.getText()+ " ");
        			}
            		result.getHighlighter().addHighlight(errorLog.get(i), errorLog.get(i)+1, new DefaultHighlighter.DefaultHighlightPainter(new Color(255, 103, 92)));
        		}
        	}else {
        		result.getHighlighter().addHighlight(0, result.getText().length(), new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN));
        	}
        	

        } catch (BadLocationException e) {
        }
    }
}
