package dndEntities;

import java.util.ArrayList;

import util.IOUtils;

public class DndClass {
	
	private String className, castingAbility, pathTitle, spellListFilePath;
	private int hitDie, proBonus, castingAbilityPos, spellsKnown;
	private String [] armorPro, weaponPro, toolPro, savingThrows, skillBonus, 
		armors, weapons, otherItems, features, choicesList;
	private ArrayList<String> [] spells;
	private int [] spellSlots;
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
	public String getPathTitle() { return pathTitle; }
	public String getSpellListFilePath() { return spellListFilePath; }
	public ArrayList<String>[] getSpells() { return spells; }
	public int[] getSpellSlots() { return spellSlots; }
	public String getCastingAbility() { return castingAbility; }
	public int getCastingAbilityPos() { return castingAbilityPos; }
	public int getSpellsKnown() { return spellsKnown; }

	public void setWeapons (String [] weapons) { this.weapons = weapons; }
	public void setArmors(String[] armors) { this.armors = armors; }
	public void setOtherItems(String[] otherItems) { this.otherItems = otherItems; }
	public void setPathTitle(String pathTitle) { this.pathTitle = pathTitle; }
	public void setSpells(ArrayList<String>[] spells) { this.spells = spells; }

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
	
	public void setBonusSpells (String filePath, int level) 
	{
		ArrayList<String []> spellInfo = new ArrayList<>();
		int counter = 0;
		
		do {
			String [] col = IOUtils.getCol(counter++, filePath);
			
			if (col.length > 0) 
				spellInfo.add(col);
			else 
				break;
			
		} while (true);
		
		for (String [] spellSection: spellInfo) 
		{
			if (level < (int)Double.parseDouble(spellSection[0]))
				break;
			
			counter = 0;
			for (int i = 1; i < spellSection.length; i++) 
			{
				String [] bonusSpells = spellSection[i].split("=");
				for (String spell: bonusSpells) 
				{
					spells[counter].add(spell);
				}
				counter++;
			}
		}
		
	}

	//Returns instructions for setting spells if the archetype gives spell casting
	public String [] setArchetypeTraits(int level, String filePath, int archetypePos) 
	{
		String [] results = null;
		String [] archetypeInfo = IOUtils.getCol(archetypePos, filePath);
		
		if (!(archetypeInfo[1].equalsIgnoreCase("None"))) 
			this.armorPro = updateList(archetypeInfo[1].split("="), this.armorPro);
		
		if (!(archetypeInfo[2].equalsIgnoreCase("None")))
			this.weaponPro = updateList(archetypeInfo[2].split("="), this.weaponPro);
		
		if (!(archetypeInfo[3].equalsIgnoreCase("None")))
			this.toolPro = updateList(archetypeInfo[3].split("="), this.toolPro);
		
		if (!(archetypeInfo[4].equalsIgnoreCase("None")))
			this.skillBonus = updateList(archetypeInfo[4].split("="), this.skillBonus);
		
		String [] bonusSpellList = archetypeInfo[5].split("=");
		if (bonusSpellList[0].equalsIgnoreCase("true")) 
		{
			setBonusSpells(bonusSpellList[1], level);
		}
		
		String [] newCastingList = archetypeInfo[6].split("=");
		if (newCastingList[0].equalsIgnoreCase("true")) 
		{
			results = newCastingList;
		}
		
		this.features = updateList(archetypeInfo[7].split("="), this.features);
		
		return results;
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
		if (spellList[0].equalsIgnoreCase("true")) {
			setCastingAbilityPos(spellList[1]);
			this.spellListFilePath = spellList[3];
			char preparedOrKnownCaster = spellList[4].toUpperCase().charAt(0);
			setSpellsAndSlots(spellList[2], level, preparedOrKnownCaster);
		}
		
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
	
	private void setCastingAbilityPos (String castingAbility) 
	{
		this.castingAbility = castingAbility;
		
		switch (this.castingAbility) 
		{
			case "Intelligence":
				castingAbilityPos = 3;
				break;
			case "Wisdom":
				castingAbilityPos = 4;
				break;
			case "Charisma":
				castingAbilityPos = 5;
				break;
			default:
				castingAbilityPos = -1;
		}
	}
	
	private void setSpellsAndSlots(String filepath, int level, char preparedOrKnownCaster) 
	{
		String [] spellInfo = IOUtils.getCol(level - 1, filepath);
		this.spellSlots = new int [spellInfo.length - 2];
		
		this.spellsKnown = preparedOrKnownCaster == 'K' ? (int)Double.parseDouble(spellInfo[1]) : -1;
		
		for (int i = 2; i < spellInfo.length; i++) 
		{
			this.spellSlots[i - 2] = (int)Double.parseDouble(spellInfo[i]);
		}
		
		this.spells = new ArrayList[this.spellSlots.length];
		
		for (int i = 0; i < spells.length; i++) 
		{
			spells[i] = new ArrayList<String>();
		}
	}
	
	private String [] updateList(String [] newList, String [] oldList) {
		
		String [] updatedList = new String [newList.length + oldList.length];
		
		int counter = 0;
		for (String armor: newList) 
		{
			updatedList[counter++] = armor;
		}
		
		for (String armor: oldList) 
		{
			updatedList[counter++] = armor;
		}
		
		return updatedList;
	}
}
