package src;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	 
	//Set files location
	public final static ArrayList<String> DIRECTORIES = 
			new ArrayList<>(Arrays.asList("C:\\BitBucket\\BabyApp v2\\app\\src\\main"));
	//Set which files to read
	public final static ArrayList<String> ACCEPTABLE_FILES = 
			new ArrayList<>(Arrays.asList(".java",".xml"));
	//Output folder name
	public final static String OUTPUTS_DIR = "WorkCounterOutput";
	
	// **** CONFIG ****
	public final static int MIN_ACCEPTABLE_LENGTH_FOR_VALID_LINE = 5;
	public final static int MEAN_WORDS_LENGTH = 5;

	public Main() {
		
		 Utils utils = new Utils(); 
		 ArrayList<String> filePathNameList = utils.getPathNamesList();
		 ArrayList<String> lastWeekFiles = utils.getPathNamesListLastWeek();
		 ArrayList<String> last24hFiles = utils.getPathNamesListLast24h();
		 
		 ArrayList<String> outputText = new ArrayList<String>();
		 outputText.add("Directories:");
		 for (String direc : DIRECTORIES)
		 {
			 outputText.add(direc);
		 }
		 outputText.add(" ");
		 
		//Last 24h
		 outputText.add("**** LAST 24H ****");
		 System.out.println("**** LAST 24H ****");
		 displayInfo(new Counter(last24hFiles),last24hFiles.size(),outputText); 
		 
		//Last week   
		 outputText.add("**** LAST WEEK ****");
		 System.out.println("**** LAST WEEK ****");
		 displayInfo(new Counter(lastWeekFiles),lastWeekFiles.size(),outputText);
		 
		 //Total  
		 outputText.add("**** TOTAL ****");
		 System.out.println("**** TOTAL ****");
		 displayInfo(new Counter(filePathNameList),filePathNameList.size(),outputText);
		 
		
		 
		
		 
		 Utils.createTextFile(outputText);
	}
	
	private void displayInfo(Counter counter,int totalFiles,ArrayList<String> outputText)
	{
		System.out.println("Total files: " + totalFiles);
		 System.out.println("Total lines: " + counter.getTotalLines());
		 System.out.println("Total words: " + counter.getTotalWords());
		 System.out.println("Total letters: " + counter.getTotalLetters());
		 System.out.println(" "); 
		 
		 outputText.add("Total files: " + totalFiles);
		 outputText.add("Total lines: " + counter.getTotalLines());
		 outputText.add("Total words: " + counter.getTotalWords());
		 outputText.add("Total letters: " + counter.getTotalLetters());
		 outputText.add(" ");
	}

	public static void main(String[] args) {
		new Main(); 
	}

}
