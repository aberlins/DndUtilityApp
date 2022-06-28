package dndEntities;

import java.util.ArrayList;

import util.IOUtils;

public class DndClass {
	
	private String className, castingAbility;
	private int hitDie, proBonus;
	private String [] armorPro, weaponPro, toolPro, savingThrows, skillBonus, 
		armors, weapons, otherItems, spells, features, pathTitles, choicesList;
	public static final String classFileName = "classes/classPathway.txt";
	public static final String classStandFile = "classes/StandardFeatures.xlsx";
	public static final String classChoiceFile = "classes/ChoiceFeatures.xlsx";
	
	public DndClass(Race race, Background background, String className, int level) 
	{
		this.className = className;
		int classIndex = initilizeStandardFeatures(level);
		this.choicesList = IOUtils.getCol(classIndex, classChoiceFile);
	}
	
	public String getClassName() { return className; }
	public int getProBonus() { return proBonus; }
	public String[] getSkillBonus() { return skillBonus; }
	public String[] getChoicesList() { return choicesList; }
	public String[] getWeapons() { return weapons; }
	public String[] getArmors() { return armors; }
	public String[] getOtherItems() { return otherItems; }

	public void setWeapons (String [] weapons) { this.weapons = weapons; }
	public void setArmors(String[] armors) { this.armors = armors; }
	public void setOtherItems(String[] otherItems) { this.otherItems = otherItems; }

	public boolean setSkillBonus(String[] skillBonus) 
	{
		String skillBonusRules [] = choicesList[1].split("=");
		
		if (skillBonus.length > Integer.parseInt(skillBonusRules[0]))
			return false;
		
		String [] validSkillList = skillBonusRules[1].split("/");
		
		for (String skill: skillBonus) 
		{
			boolean invalidInput = true;
			for (String validSkill: validSkillList) 
			{
				if (skill.equalsIgnoreCase(validSkill)) 
				{
					invalidInput = false;
					break;
				}
			}
			
			if (invalidInput)
				return false;
		}
		
		this.skillBonus = skillBonus;
		return true;
	}


	private int initilizeStandardFeatures(int level) 
	{
		int classIndex = IOUtils.getIndex(className, classStandFile);
		String featuresList [] = IOUtils.getCol(classIndex, classStandFile);
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
		
		return classIndex;
		
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
