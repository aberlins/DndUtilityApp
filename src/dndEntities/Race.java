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
		String filePath = IOUtils.getAttributeFolder(raceName, raceFileName);
		if (filePath != null) {
			filePath += "/Race.xlsx";
			int raceIndex = IOUtils.getIndex(raceName, filePath);
			String raceAttributes [] = IOUtils.getCol(raceIndex, filePath);
			
			name = raceAttributes[0];
			
			String [] line = raceAttributes[1].split("=");
			abilityScoreIncr = new int [6];
			int counter = 0;
			for (String score: line) 
				abilityScoreIncr[counter++] = Integer.parseInt(score);
			
			line = raceAttributes[2].split("=");
			int numSpells = line.length / 3;
			racialSpellsN = new String[numSpells];
			numSpells = 0;
			for (int i = 0; i < line.length - 1; i = i + 3) 
				racialSpellsN[numSpells++] = line[i];
			
			line = raceAttributes[3].split("=");
			abilities = new String[line.length];
			System.arraycopy(line, 0, abilities, 0, abilities.length);
			
			maxAge = (int)Double.parseDouble(raceAttributes[4]); 
			size = raceAttributes[5];
			speed = (int)Double.parseDouble(raceAttributes[6]);
			
			line = raceAttributes[7].split("=");
			raceLanguages = new String [line.length];
			System.arraycopy(line, 0, raceLanguages, 0, raceLanguages.length);
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

}
