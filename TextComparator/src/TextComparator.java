import java.util.ArrayList;
/**
 * This class implements a text comparator
 * @author Henrique Marques
 *
 */
public class TextComparator {
	
	private String[] originalText;
	private String[] comparisonText;
	private ArrayList<Integer> errorLog;
	
	/**
	 * Creates a new text comparator object
	 * @param originalText		the original text
	 * @param comparisonText	the text to compare to the original text
	 * @requires {@code (originalText != null && comparisonText != null) ||
	 * 					(originalText == null && comparisonText == null)}
	 */
	public TextComparator(String originalText, String comparisonText){
		this.originalText = originalText.split("");
		this.comparisonText = comparisonText.split("");	
		errorLog = new ArrayList<>();
	}
	
	/**
	 * Checks if two texts are the same
	 * @return true if both text are the same, false otherwise. Also print a error log
	 * 
	 */
	public ArrayList<Integer> checkText() {
		if(originalText == null && comparisonText == null) {
			return errorLog;
		}
		
		if(originalText.length > comparisonText.length) {
			return largerOriginalText();
		}else if(comparisonText.length > originalText.length){
			return largerComparisonText();
		}
		
		return sameSizeTexts();
	}
	
	private ArrayList<Integer> largerComparisonText(){   
		// adds no equal letters
		for(int i = 0; i < originalText.length; i++) {
			// if two letters dont match on a certain index adds the index to a error log
			if(!originalText[i].equals(comparisonText[i])) {
				errorLog.add(i);
			}
		}
		// adds all the extra indexes
		for(int i = originalText.length; i < comparisonText.length; i++) {
			errorLog.add(i);
		}
		return errorLog;
	}
	
	private ArrayList<Integer> largerOriginalText(){   
		// adds no equal letters
		for(int i = 0; i < comparisonText.length; i++) {
			// if two letters dont match on a certain index adds the index to a error log
			if(!originalText[i].equals(comparisonText[i])) {
				errorLog.add(i);
			}
		}
		// adds all the missing indexes
		for(int i = comparisonText.length; i < originalText.length; i++) {
			errorLog.add(i);
		}
		return errorLog;
	}
	
	private ArrayList<Integer> sameSizeTexts(){
		//TODO ADD CASE FOR originalText.LENGH < COMPARISONTEXT.LENGTH || COMPARISONTEXT.LENGTH < originalText.LENGH  
				for(int i = 0; i < originalText.length; i++) {
					// if two letters dont match on a certain index adds the index to a error log
					if(!originalText[i].equals(comparisonText[i])) {
						
						errorLog.add(i);
					}
				}
				return errorLog;
	}

}
