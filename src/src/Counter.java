package src;

import java.util.ArrayList;

public class Counter {
	
	private ArrayList<String> filePathNameList;
	private int totalLines;
	private int totalLetters; 

	public Counter(ArrayList<String> filePathNameList) {
		 this.filePathNameList=filePathNameList; 
		 count();
	}
	
	public int getTotalLines()
	{
		return totalLines;
	}
	
	public int getTotalWords()
	{
		return totalLetters/Main.MEAN_WORDS_LENGTH;
	}
	
	public int getTotalLetters()
	{
		return totalLetters;
	}
	
	private void count()
	{
		for (String fp : filePathNameList)
		{
			ArrayList<String> lines = Utils.getText(fp);
			totalLines += lines.size();
			
			for (String line : lines)
			{
				totalLetters += line.length();
			}
		}
	}

}
