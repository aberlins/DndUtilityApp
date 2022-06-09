package util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class GenUtils {
	
	/*Given a string, the variable is checked to see if it can be converted to an integer. 
	A boolean value is returned representing if this operation can be done.*/
	public static boolean isAnInteger(String word)
	{
		boolean numericString = true;
		
		//Loop is used to check if each character is
		//a digit.
		for (int i = 0; i < word.length(); i++) 
		{
			if (Character.isDigit(word.charAt(i)) == false)
				numericString = false;
		}
		
		//If the string is null then false is returned.
		if (word.equals(""))
			numericString = false;
		
		return numericString;
		
	}
	
	public static String getAttributeFolder(String attributeName, String fileAttributeList) 
			throws FileNotFoundException, IOException 
	{
		try (Scanner fileStream = new Scanner(new File(fileAttributeList)))
		{
			while (fileStream.hasNextLine()) 
			{
				String [] line = fileStream.nextLine().split("=");
				if (attributeName.equals(line[0]))
					return line[1];
			}
		}
		return null;
	}
	
	public static long getLineCount(String fileName) throws IOException
	{
		try (LineNumberReader lineReader = new LineNumberReader(new FileReader(fileName)))
		{
			while ((lineReader.readLine()) != null);
	        	return lineReader.getLineNumber();
		}
		catch (Exception e) {
			return -1;
		}
	}
	
	public static String getStartingPoint (Scanner fileStream, String attName) 
	{
		if (fileStream.hasNextLine()) 
		{
			String [] line = fileStream.nextLine().split("=");
			for (String beginVar: line) 
			{
				String [] beginLoc = beginVar.split("/");
				if (attName.equals(beginLoc[0]))
					return beginLoc[1];
			}
		}
		return null;
	}
}
