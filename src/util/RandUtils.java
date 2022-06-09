package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import dndEntities.Alignment;
import dndEntities.Background;
import dndEntities.Race;

import java.util.ArrayList;

public class RandUtils {
	
	/*Utility Method used to roll up character's stats.
	Is done randomly not using a point base system.
	Value returned is an int array with the values corresponding to
	each stat as follows: Str, Dex, Con, Int, Wis, Cha*/
	public static int [] rollCharacterStats() 
	{
		//Stats to be returned
		int statList [] = new int [6];
		
		for (int i = 0; i < 6; i++) 
		{
			//Temporary Array meant to hold the 4 dice rolls for each stat
			int [] tempStats = new int[4];
			for (int j = 0; j < 4; j++) 
			{
				tempStats[j] = (int)(Math.random() * 6) + 1;
			}
			//Sort the array so the lowest value is in index 0
			Arrays.sort(tempStats);
			//Add up the remaining values to get that specific score.
			statList[i] = tempStats[1] + tempStats[2] + tempStats[3];
		}
		return statList;
	}
	
	/*Utility Method used to calculate a Character's hit points
	given their class's die size, their character level and their conMod.
	This only works for single class characters.*/
	public static int rollHitPoints(int dieSize, int level, int conMod) 
	{
		//If conMod is negative, ignore it.
		if (conMod < 0)
			conMod = 0;
		
		//Take the max roll for level 1
		int hitPoint = dieSize + conMod;
		
		//For each level roll the character's hit points and add it to the total.
		for (int i = 0; i < level - 1; i++) 
		{
			hitPoint += (int)(Math.random() * dieSize) + 1 + conMod;
		}
		return hitPoint;
	}
	
	/*Utility Method used to pick random traits from a list. For Example rolling a d100 to decide a random
	trait from a list, or picking a random name out of a list of 500.
	A filepath way to a text file with this list is given as input.
	Each option is separated by a new line. Returned is a random trait picked from said list.*/
	public static String randomTrait(String fileName) throws FileNotFoundException, IOException, Exception
	{
		//Scanner object is attempted to be made from the file path given.
		try (Scanner fileStream = new Scanner(new File(fileName)))
		{
			//Array meant to hold all values is allocated the proper memory.
			//Counter variable is used to keep track of index.
			long lineCount = GenUtils.getLineCount(fileName);
			String [] traitArray = new String[(int)lineCount];
			int counter = 0;
					
			//Fill Array
			while (fileStream.hasNextLine()) 
			{
				traitArray[counter++] = fileStream.nextLine();
			}
					
			//Return final result.
			int dieRoll = (int)(Math.random() * lineCount);
			return traitArray[dieRoll];
		}
	}
	
	/*Utility Method used to generate one of the nine alignments in DND 5e.*/
	public static Alignment randomAlignment() 
	{
		//Get all the values from the alignment enum as well as the total size 
		Alignment [] alignVals = Alignment.values();
		int size = alignVals.length;
		
		return alignVals[(int)(Math.random() * size)];
	}
	
	/* Utility Method used to return a random age given the max age of a race.*/
	public static int randomAge(int maxAge) 
	{
		return (int)(Math.random() * maxAge) + 1;
	}
	
	/* Utility method used to return a random gender for a character.
	As of now only male and female are available.*/
	public static String randomGender() 
	{
		int genNum = (int)(Math.random() * 2) + 1;
		
		switch(genNum) 
		{
			case 1:
				return "Male";
			case 2:
				return "Female";
			default:
				return null;
		}
	}
	
	/*Utility Method used to generate a random name given the character's race and gender.
	For now only Male and Female are available options for first names. */
	public static String randomName(Race race, String gender) throws FileNotFoundException, IOException, Exception
	{
		String raceFolder = GenUtils.getAttributeFolder(race.getName(), Race.raceFileName);
		
		//First generate the first name depending on the gender
		String name = null;
		switch (gender) 
		{
			case "Male":
				name = randomTrait(raceFolder + "/maleNames.txt");
				break;
			case "Female":
				name = randomTrait(raceFolder + "/femaleNames.txt");
				break;
		}
		
		//Add the last name to the value and then return it.
		name += " " + randomTrait(raceFolder + "/lastNames.txt");
		return name;
	}
	
	public static Background randomBackground(Alignment alignment) throws FileNotFoundException, IOException, Exception
	{
		try (Scanner fileStream = new Scanner(new File(Background.backgroundFileName)))
		{
			ArrayList<String> backgroundList = new ArrayList<>();
			while (fileStream.hasNextLine()) 
			{
				String [] line = fileStream.nextLine().split("=");
				backgroundList.add(line[0]);
			}
			
			Background background = new Background(alignment, 
					backgroundList.get((int)(Math.random() * backgroundList.size())));
			
			String backgroundFolder = GenUtils.getAttributeFolder(background.getName(), 
					Background.backgroundFileName);
			randomIdeal(background);
			background.setBond(randomTrait(backgroundFolder + "/bonds.txt"));
			background.setFlaw(randomTrait(backgroundFolder + "/flaws.txt"));
			background.setPersonalityTrait(randomTrait(backgroundFolder + "/personalityTraits.txt"));
			
			return background;
		}
	}
	
	private static void randomIdeal(Background background)  throws FileNotFoundException, IOException
	{
		
		long numOfLines = GenUtils.getLineCount(GenUtils.getAttributeFolder(background.getName(), 
				Background.backgroundFileName) + "/ideals.txt");
		
		if (numOfLines > 0) 
		{
			boolean continueLoop = true;
			while (continueLoop) 
			{
				long idealIndex = (long)(Math.random() * numOfLines) + 1;
				if (background.setIdeal(idealIndex)) {
					continueLoop = false;
				}
			}
		}	
	}
	
	public static Race randomRace() throws FileNotFoundException, IOException, Exception
	{
		try (Scanner fileStream = new Scanner(new File(Race.raceFileName)))
		{
			ArrayList<String> raceList = new ArrayList<>();
			while (fileStream.hasNextLine()) 
			{
				String [] line = fileStream.nextLine().split("=");
				raceList.add(line[0]);
			}
			return new Race(raceList.get((int)(Math.random() * raceList.size())));
		}
	}
	
}
