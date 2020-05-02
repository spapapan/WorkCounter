package src;

import java.io.BufferedReader; 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader; 
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList; 

public class Utils {
	
	private ArrayList<String> filePathName;
	private ArrayList<String> filePathNameLast24h;
	private ArrayList<String> filePathNameLastWeek;
	private LocalDateTime currentTime;
	
	public Utils()
	{
		currentTime = LocalDateTime.now();
		filePathName = new ArrayList<>();
		filePathNameLast24h = new ArrayList<>();
		filePathNameLastWeek = new ArrayList<>();
		
		for (String dir : Main.DIRECTORIES)
		{
			getFileNames(dir);
		}  
	}
	
	public ArrayList<String> getPathNamesList()
	{
		return filePathName;
	}
	
	public ArrayList<String> getPathNamesListLast24h()
	{
		return filePathNameLast24h;
	}
	
	public ArrayList<String> getPathNamesListLastWeek()
	{
		return filePathNameLastWeek;
	}
	
	//Create and write to text file
	public static void createTextFile(ArrayList<String> textList)
	{ 
		File outputFolder = new File(Main.OUTPUTS_DIR);
		if (!outputFolder.exists())
			outputFolder.mkdir();
		
		DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MMMM yyyy"); 
		DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("EEEE"); 
		LocalDateTime dateTime = LocalDateTime.now();
		
		String dirName = dateTime.format(monthFormat);
		File dir = new File(Main.OUTPUTS_DIR+"/"+dirName);
		if (!dir.exists()) {
			dir.mkdir();
		}
		String fileName = dateTime.format(dayFormat);
		fileName = Main.OUTPUTS_DIR + "/" + dirName + "/" + fileName + ".txt";
		
		
		PrintWriter writer=null;
        try {  
            writer = new PrintWriter(fileName, "UTF-8");
            for (String textLine : textList)
            { 
                writer.println(textLine); 
            }  
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
          if ( writer != null ) {
        	  writer.close();
          }
        }
	}	
	
	public void getLastWeekFiles(String path)
	{ 
		Path file = Paths.get(path);
		BasicFileAttributes attr;
		try {
			attr = Files.readAttributes(file, BasicFileAttributes.class);
			FileTime lastModifiedTime = attr.lastModifiedTime(); 
			LocalDateTime fileTime = 
					LocalDateTime.ofInstant(lastModifiedTime.toInstant(), ZoneId.systemDefault());
			 
			if (fileTime.getYear() == currentTime.getYear())
			{
				if (ChronoUnit.DAYS.between(fileTime, currentTime)<7)
				{ 
					filePathNameLastWeek.add(path);
				}
			}
			
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
	
	public void getLast24hFiles(String path)
	{ 
		Path file = Paths.get(path);
		BasicFileAttributes attr;
		try {
			attr = Files.readAttributes(file, BasicFileAttributes.class);
			FileTime lastModifiedTime = attr.lastModifiedTime(); 
			LocalDateTime fileTime = 
					LocalDateTime.ofInstant(lastModifiedTime.toInstant(), ZoneId.systemDefault());
			  
			
			
			if (fileTime.getYear() == currentTime.getYear())
			{
				if (ChronoUnit.HOURS.between(fileTime, currentTime)<24)
				{
					filePathNameLast24h.add(path);
				}
			}
			 
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
 
	public void getFileNames(String path)
	{
		//System.out.println("Path: " + path);
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			String fileName = listOfFiles[i].getName();
		  if (listOfFiles[i].isFile()) {
		  
			  if (checkFileName(fileName)) {
				  getLast24hFiles(path+ "/" + fileName);
				  getLastWeekFiles(path+ "/" + fileName);
				  filePathName.add(path+ "/" + fileName);
			  }
		    
		  } else if (listOfFiles[i].isDirectory()) {
		   
		    getFileNames(path+ "/" + fileName);
		  }
		}
	}
	
	private boolean checkFileName(String fileName)
	{ 
		boolean valid=false;
		for (String name : Main.ACCEPTABLE_FILES)
		{ 
			if (fileName.contains(name))
			{
				valid=true;
				break;
			}
		}
		
		return valid;
	}
	 
	//Get text from file
	public static ArrayList<String> getText(String filePath)
	{
		File file = new File(filePath);
		ArrayList<String> list = new ArrayList<>(); 
		BufferedReader reader = null;

		try {
		    reader = new BufferedReader(new FileReader(file));
		    String text = null;

		    while ((text = reader.readLine()) != null) {  
		    	text = removeWhiteSpace(text);
		    	if (!text.equals("") && text.length() >= Main.MIN_ACCEPTABLE_LENGTH_FOR_VALID_LINE)
		    	{
		    		list.add(text);
		    	} 
		    }
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException e) {
		    }
		}
		
		return list;
	}	
	
	public static String removeWhiteSpace(String lineText)
	{
		String text = lineText.replace(" ", "");
		if (text.equals(""))
			return "";
		else
			return lineText;
	}

}
