import java.util.ArrayList;
/**
 * This class implements a text comparator
 * @author Henrique Marques
 *
 */
public class TextComparator {
	
	private String originalText;
	private String comparisonText;
	private ArrayList<Integer> errorLog;
	
	/**
	 * Creates a new text comparator object
	 * @param originalText		the original text
	 * @param comparisonText	the text to compare to the original text
	 * @requires {@code (originalText != null && comparisonText != null) ||
	 * 					(originalText == null && comparisonText == null)}
	 */
	public TextComparator(String originalText, String comparisonText){
		errorLog = new ArrayList<>();
		if(originalText == null && comparisonText == null) {
			// do nothing
		}
		else if(originalText != null && comparisonText == null) {
			this.originalText = originalText;
		}
		else if(comparisonText != null && originalText == null) {
			this.comparisonText = comparisonText;
		}else {
			this.originalText = originalText;
			this.comparisonText = comparisonText;
		}

	}
	
	/**
	 * Checks if two texts are the same
	 * @return true if both text are the same, false otherwise. Also print a error log
	 * 
	 */
	public ArrayList<Integer> checkText() {
		if(originalText == null && comparisonText == null) {
			return errorLog;
		}else if(originalText != null && comparisonText == null) {
			return largerOriginalText();
		}else if(originalText == null && comparisonText != null) {
			return largerComparisonText();
		}else {
			if(originalText.length() > comparisonText.length()) {
				return largerOriginalText();
			}else if(comparisonText.length() > originalText.length()){
				return largerComparisonText();
			}
			
			return sameSizeTexts();
		}
	}
	
	private ArrayList<Integer> largerComparisonText(){   
		// adds no equal letters
		int errorOfset = 0;
		for(int i = 0; i < originalText.length(); i++) {
			// if two letters dont match on a certain index adds the index to a error log
			if(originalText.charAt(i-errorOfset) != comparisonText.charAt(i)) {
				errorLog.add(i);
				errorOfset++;
			}
		}
		// adds all the extra indexes
		for(int i = originalText.length(); i < comparisonText.length(); i++) {
			errorLog.add(i);
		}
		return errorLog;
	}
	
	private ArrayList<Integer> largerOriginalText(){ 
		int errorOfset = 0;
		// adds no equal letters
		for(int i = 0; i < comparisonText.length(); i++) {
			// if two letters dont match on a certain index adds the index to a error log
			if(originalText.charAt(i) != comparisonText.charAt(i-errorOfset)) {
				errorLog.add(i);
				errorOfset++;
			}
		}
//		// adds all the missing indexes
//		for(int i = comparisonText.length(); i < originalText.length(); i++) {
//			errorLog.add(i);
//		}
		return errorLog;
	}
	
	private ArrayList<Integer> sameSizeTexts(){
		for(int i = 0; i < originalText.length(); i++) {
			// if two letters dont match on a certain index adds the index to a error log
			if(originalText.charAt(i) != comparisonText.charAt(i)) {
				errorLog.add(i);
			}
		}
		return errorLog;
	}

}
