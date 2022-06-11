package dndEntities;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import util.IOUtils;

import java.util.NoSuchElementException;

public class Race 
{
	private String name, size;
	private String [] raceLanguages, racialSpellsN, abilities;
	private int [] abilityScoreIncr;
	private int speed, maxAge, currentLine = 1;
	public static final String raceFileName = "races/racePathway.txt";
	
	public Race(String raceName) 
	{
		String fileName = null;
		try {
			fileName = IOUtils.getAttributeFolder(raceName, raceFileName);
			if (fileName != null) {
				fileName += "/race.txt";
				initializeRace(fileName, raceName);
			}
			else {
				System.out.println("Race is not on the list.");
			}
		} 
		catch (FileNotFoundException e) 
		{ 	
			System.out.println("An error has occured locating the files: " + fileName + ", " + raceFileName);
		} 
		catch (IOException e) 
		{
			System.out.println("An error has occured when reading the files: " + fileName + ", " + raceFileName);
		} 
		catch (NumberFormatException | NoSuchElementException e) 
		{
			System.out.println("Improper formating in the " + fileName + " detected.");
		}
		
	}
	
	/*Getters for all traits of the race*/
	public String getName() { return name; }
	public String getSize() { return size; }
	public String[] getRaceLanguages() { return raceLanguages; }
	public String[] getRacialSpellsN() { return racialSpellsN; }
	public String[] getAbilities() { return abilities; }
	public int[] getAbilityScoreIncr() { return abilityScoreIncr; }
	public int getSpeed() { return speed; }
	public int getMaxAge() { return maxAge; }

	private void initializeRace (String fileName, String raceName) throws FileNotFoundException, IOException, NumberFormatException
	{
		try (Scanner fileStream = new Scanner(new File(fileName)))
		{
			String startPointS = IOUtils.getStartingPoint(fileStream, raceName); currentLine++;
			
				int startPoint = Integer.parseInt(startPointS);
				
				if (startPoint > 2) 
				{
					setEndTraits(false, fileStream);
					setBeginningTrait(startPoint, fileStream);
				}
				else 
				{
					setBeginningTrait(startPoint, fileStream);
					setEndTraits(true, fileStream);
				}
		}
	}
	
	private void setEndTraits(boolean baseClass, Scanner fileStream) throws NumberFormatException, NoSuchElementException
	{
			if (baseClass == false) 
			{
				for (int i = 0; i < 4; i++) {
					fileStream.nextLine(); currentLine++;
				}
			}
		
			maxAge = Integer.parseInt(fileStream.nextLine()); currentLine++;
			size = fileStream.nextLine(); currentLine++;
			speed = Integer.parseInt(fileStream.nextLine()); currentLine++;
			
			String [] line = fileStream.nextLine().split("="); currentLine++;
			raceLanguages = new String [line.length];
			System.arraycopy(line, 0, raceLanguages, 0, raceLanguages.length);
			
	}
	
	private void setBeginningTrait(int startPoint, Scanner fileStream) throws NumberFormatException, NoSuchElementException
	{
		while (startPoint != currentLine && fileStream.hasNextLine()) {
			fileStream.nextLine(); currentLine++;
		}
			
			name = fileStream.nextLine();
			
			String [] line = fileStream.nextLine().split("=");
			abilityScoreIncr = new int [6];
			int counter = 0;
			for (String score: line) 
				abilityScoreIncr[counter++] = Integer.parseInt(score);
			
			//Need to add to this in the future
			line = fileStream.nextLine().split("=");
			int numSpells = line.length / 3;
			racialSpellsN = new String[numSpells];
			numSpells = 0;
			for (int i = 0; i < line.length - 1; i = i + 3) 
				racialSpellsN[numSpells++] = line[i];
			
			line = fileStream.nextLine().split("=");
			abilities = new String[line.length];
			System.arraycopy(line, 0, abilities, 0, abilities.length);
	}
}
