package dndEntities;

import java.util.ArrayList;

import util.IOUtils;

public class DndClass {
	
	private String className, castingAbility;
	private int hitDie, proBonus, currentLine = 1;
	private String [] armorPro, weaponPro, toolPro, savingThrows, skillBonus, 
		armors, weapons, otherItems, spells, features;
	public static final String classFileName = "classes/classPathway.txt";
	public static final String classStandFile = "classes/StandardFeatures.xlsx";
	
	public DndClass(Race race, Background background, Alignment alignment, String className, int level) 
	{
		this.className = className;
		initilizeStandardFeatures(level);
	}
	
	private void initilizeStandardFeatures(int level) 
	{
		int raceIndex = IOUtils.getIndex(className, classStandFile);
		String featuresList [] = IOUtils.getCol(raceIndex, classStandFile);
		proBonus = getProBonus(featuresList[1], level);
		hitDie = (int)Double.parseDouble(featuresList[2]);
		armorPro = featuresList[3].split("=");
		weaponPro = featuresList[4].split("=");
		toolPro = featuresList[5].split("=");
		savingThrows = featuresList[6].split("=");
		
		String spellList [] = featuresList[7].split("=");
		if (spellList[0].equalsIgnoreCase("true"))
			castingAbility = spellList[1];
		
		features = setFeatures(featuresList[8], level);
		
	}
	
	private int getProBonus(String line, int level) 
	{
		String proList [] = line.split("=");
		for (String levelPro: proList) 
		{
			String [] proBonusArray = levelPro.split("/");
			if (Integer.toString(level).equals(proBonusArray[0]))
				return Integer.parseInt(proBonusArray[1]);
		}
		return -1;
	}
	
	private String [] setFeatures(String line, int level) 
	{
		ArrayList<String> tempFeatures = new ArrayList<>();
		String featList [] = line.split("=");
		for (String levelFeat: featList) 
		{
			String [] featLevelArray = levelFeat.split("/");
			if (level < Integer.parseInt(featLevelArray[0]))
				break;
			else
				tempFeatures.add(featLevelArray[1]);
		}
		
		return tempFeatures.toArray(new String [tempFeatures.size()]);
	}

}
