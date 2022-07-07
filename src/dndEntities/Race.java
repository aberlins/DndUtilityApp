package dndEntities;

import java.util.ArrayList;

import util.IOUtils;

public class Race 
{
	private String name, size, castingAbility, racialNameFilePath;
	private String [] languages, abilities;
	private ArrayList<String> [] spells;
	private int [] abilityScoreIncr;
	private int speed, maxAge;
	public static final String raceFileName = "races/Race.xlsx";
	
	public Race (int raceIndex, int level) 
	{
		String raceAttributes [] = IOUtils.getCol(raceIndex, raceFileName);
		initializeRaceAttributes(raceAttributes, level);
	}
	
	/*Getters for all traits of the race*/
	public String getName() { return name; }
	public String getSize() { return size; }
	public String[] getLanguages() { return languages; }
	public String[] getAbilities() { return abilities; }
	public int[] getAbilityScoreIncr() { return abilityScoreIncr; }
	public int getSpeed() { return speed; }
	public int getMaxAge() { return maxAge; }
	public ArrayList<String>[] getSpells() { return spells; }
	public String getRacialNameFilePath() { return racialNameFilePath; }
	public String getCastingAbility() { return castingAbility; }

	private void initializeRaceAttributes(String [] raceAttributes, int level) 
	{
		this.name = raceAttributes[0];
		
		String [] line = raceAttributes[1].split("=");
		this.abilityScoreIncr = new int [6];
		int counter = 0;
		for (String score: line) 
			abilityScoreIncr[counter++] = Integer.parseInt(score);
		
		setRacialSpells(raceAttributes[2].split("="), level);
		
		line = raceAttributes[3].split("=");
		this.abilities = new String[line.length];
		System.arraycopy(line, 0, abilities, 0, abilities.length);
		
		this.maxAge = (int)Double.parseDouble(raceAttributes[4]); 
		this.size = raceAttributes[5];
		this.speed = (int)Double.parseDouble(raceAttributes[6]);
		
		line = raceAttributes[7].split("=");
		this.languages = new String [line.length];
		System.arraycopy(line, 0, languages, 0, languages.length);
		
		this.racialNameFilePath = raceAttributes[8];
		
	}
	
	private void setRacialSpells(String[] spellList, int level) 
	{
		int counter = 0, maxLevelSpell = 0;
		ArrayList<String []> spellContent = new ArrayList<>();
		for (String spellEntry: spellList) 
		{	
			if (counter++ != 0) 
			{
				String [] spellLine = spellEntry.split("/");
				if (level >= Double.parseDouble(spellLine[0])) 
				{
					spellContent.add(spellLine);
					maxLevelSpell = Double.parseDouble(spellLine[2]) > maxLevelSpell ? 
							(int)Double.parseDouble(spellLine[2]) : maxLevelSpell;
				}
				else 
				{
					break;
				}
			}
			else 
			{
				this.castingAbility = spellEntry;
			}
		}
		
		
		
		spells = new ArrayList[maxLevelSpell + 1];
		
		for (int i = 0; i < spells.length; i++) 
		{
			boolean needExtraEntry = true;
			ArrayList<String> spellLevelList =  new ArrayList<>();
			for (String [] spellLine: spellContent) 
			{
				if (Double.parseDouble(spellLine[2]) == i) 
				{
					spellLevelList.add(spellLine[1]);
					needExtraEntry = false;
				}
			}
			
			if (needExtraEntry) 
			{
				spells[i] = new ArrayList<String>();
			}
			else 
			{
				spells[i] = spellLevelList;
			}
			
		}
		
	}

}
