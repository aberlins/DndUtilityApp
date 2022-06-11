package dndEntities;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import util.IOUtils;

public class DndClass {
	
	private String className;
	private int hitDie, proBonus, currentLine = 1;
	private String [] armorPro, weaponPro, toolPro, savingThrows, skillBonus, 
		armors, weapons, otherItems, spells, features;
	public static final String classFileName = "classes/classPathway.txt";
	
	public DndClass(Race race, Background background, Alignment alignment, String className, int level) 
	{
		this.className = className;
		initilizeBasicFeatures(className, level);
	}
	
	private void initilizeBasicFeatures(String className, int level) 
	{
		String fileName = null;
		try {
			fileName = IOUtils.getAttributeFolder(className, classFileName);
			if (fileName != null) 
			{
				fileName += "/basicFeatures.txt";
				try (Scanner fileStream = new Scanner(new File(fileName))) 
				{
					proBonus = getProBonus(fileStream, level);
					hitDie = Integer.parseInt(fileStream.nextLine());
					armorPro = fileStream.nextLine().split("=");
					weaponPro = fileStream.nextLine().split("=");
					toolPro = fileStream.nextLine().split("=");
					savingThrows = fileStream.nextLine().split("=");
				}
			}
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("An error has occured locating the files: " + fileName + ", " + classFileName);
		}
		catch (IOException e) 
		{
			System.out.println("An error has occured when reading the files: " + fileName + ", " + classFileName);
		}
		catch (NumberFormatException e)
		{
			System.out.println("Improper formating in the " + fileName + " detected.");
		}
	}
	
	private int getProBonus(Scanner fileStream, int level) throws NumberFormatException
	{
		if (fileStream.hasNextLine()) 
		{
			String [] line = fileStream.nextLine().split("=");
			for (String levelPro: line) 
			{
				String [] proBonusArray = levelPro.split("/");
				if (Integer.toString(level).equals(proBonusArray[0]))
					return Integer.parseInt(proBonusArray[1]);
			}
		}
		return -1;
	}

}
