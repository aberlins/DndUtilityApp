package dndEntities;

import java.util.ArrayList;

import util.GenUtils;
import util.IOUtils;
import util.MathUtils;

public class DndClass {
	
	private String className, castingAbility, pathTitle, spellListFilePath;
	private int hitDie, proBonus, spellsKnown, level, initBonus;
	private String [] armorPro, weaponPro, toolPro, savingThrows, skillBonus, 
		armors, weapons, otherItems, features, choicesList, langauges;
	private ArrayList<String> [] spells;
	private int [] spellSlots, abilityScores;
	private char preparedOrKnownCaster;
	private Race race;
	private Background background;
	public static final String classFileName = "classes/classPathway.txt";
	public static final String classStandFile = "classes/StandardFeatures.xlsx";
	public static final String classChoiceFile = "classes/ChoiceFeatures.xlsx";
	
	public DndClass(Race race, Background background, String className, int level, int [] abilityScores) 
	{
		this.race = race;
		this.background = background;
		this.className = className;
		this.level = level;
		this.abilityScores = abilityScores;
		finalizeAbilityScores();
		initilizeStandardFeatures();
		this.choicesList = IOUtils.getCol(className, classChoiceFile, false);
	}
	
	public String getClassName() { return className; }
	public int getProBonus() { return proBonus; }
	public String[] getSkillBonus() { return skillBonus; }
	public String[] getChoicesList() { return choicesList; }
	public String[] getWeapons() { return weapons; }
	public String[] getArmorPro() { return armorPro; }
	public String[] getArmors() { return armors; }
	public String getPathTitle() { return pathTitle; }
	public String getSpellListFilePath() { return spellListFilePath; }
	public ArrayList<String>[] getSpells() { return spells; }
	public int[] getSpellSlots() { return spellSlots; }
	public String getCastingAbility() { return castingAbility; }
	public int getSpellsKnown() { return spellsKnown; }
	public int getHitDie() { return hitDie; }
	public Race getRace() { return race; }
	public Background getBackground() { return background; }
	public int getLevel() { return level; }
	public String[] getSavingThrows() { return savingThrows; }
	public int[] getAbilityScores() { return abilityScores; }
	public int getInitBonus() { return initBonus; }
	public String[] getLangauges() { return langauges; }
	
	public int getSpellAttackBonus() {
		if (castingAbility != null) {
			return MathUtils.getSpellAttackBonus(castingAbility, proBonus, abilityScores);
		}
		return 0;
	}
	public int getSpellSaveDC() {
		if (castingAbility != null) {
			return MathUtils.getSpellSaveDC(castingAbility, proBonus, abilityScores);
		}
		return 0;
	}
	public int getRacialSpellAttackBonus() {
		if (race.getCastingAbility() != null) {
			return MathUtils.getSpellAttackBonus(race.getCastingAbility(), proBonus, abilityScores);
		}
		return 0;
	}
	public int getRacialSpellSaveDC() {
		if (race.getCastingAbility() != null) {
			return MathUtils.getSpellSaveDC(race.getCastingAbility(), proBonus, abilityScores);
		}
		return 0;
	}
	
	public String [] getEquipment() 
	{
		return (String []) GenUtils.combineLists(background.getEquipment(), otherItems, false);
		
	}

	public int [] getAbilityModifers() {
		int [] abilityScoreMod = new int[6];
		
		for (int i = 0; i < abilityScoreMod.length; i++) 
		{
			abilityScoreMod[i] = MathUtils.getAbilityModifer(abilityScores[i]);
		}
		
		return abilityScoreMod;
	}
	public int [] getSkillScores() 
	{
		return MathUtils.getSkillScores(abilityScores, proBonus, skillBonus);
	}
	public int [] getSavingThrowScores() {
		return MathUtils.getSavingThrowScores(abilityScores, savingThrows, proBonus);
	}
	
	//Needs to be completed later
	public int getInitative() {
		return MathUtils.getInitativeScore(abilityScores[1], null);
	}
	//Needs to be completed later
	public int getPassiveWisdom() {
		return MathUtils.getPassiveWisdomScore(getSkillScores()[11], 0);
	}
	
	public int getArmorClass() 
	{
		return MathUtils.getArmorClass(abilityScores[1], armors);
	}
	
	public String [][] getWeaponStats() 
	{
		String weaponStats[][] = new String[weapons.length][3];
		int counter = 0;
		for (String weapon: weapons) 
		{
			String [] weaponInfo = IOUtils.getCol(weapon, IOUtils.weaponStatsFile, true);
			String [] damage = weaponInfo[1].split("=");
			
			weaponStats[counter][0] = weapon;
			weaponStats[counter][1] = Integer.valueOf(
					MathUtils.getAttackBonus(abilityScores[0], abilityScores[1], proBonus, weapon)).toString();
			weaponStats[counter][2] = damage[0] + "/" + damage[1];
			
			counter++;
		}
		
		return weaponStats;
	}
	
	public String getPathName() {
		if (pathTitle != null) 
		{
			int splitIndex = pathTitle.indexOf(":") + 2;
			return pathTitle.substring(splitIndex);
		}
		return null;
	}
	
	public String [] getAllProficiencies() {
		String [] proList = GenUtils.combineLists(armorPro, weaponPro, true);
		return GenUtils.combineLists(proList, toolPro, true);
	}

	public void setWeapons (String [] weapons) { this.weapons = weapons; }
	public void setArmors(String[] armors) { this.armors = armors; }
	public void setOtherItems(String[] otherItems) { this.otherItems = otherItems; }
	public void setPathTitle(String pathTitle) { this.pathTitle = pathTitle; }
	public void setSpells(ArrayList<String>[] spells) { this.spells = spells; }
	public void setAbilityScores(int[] abilityScores) { this.abilityScores = abilityScores; }
	
	public boolean isPreparedCaster() {
		return preparedOrKnownCaster == 'P';
	}

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
	
	public boolean setBonusSpells (String filePath) 
	{
		ArrayList<String []> spellInfo = new ArrayList<>();
		int counter = 0;
		
		boolean completeSpellList = true;
		
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
					if (!(spell.equals("-"))) 
					{
						spells[counter].add(spell);
						if (spell.startsWith("*"))
							completeSpellList = false;
					}
				}
				counter++;
			}
		}
		
		return completeSpellList;
		
	}

	public boolean setArchetypeTraits(String filePath, int archetypePos) 
	{
		String [] archetypeInfo = IOUtils.getCol(archetypePos, filePath);
		boolean completeSpellList = true;
		
		if (!(archetypeInfo[1].equalsIgnoreCase("None"))) 
			this.armorPro = updateList(archetypeInfo[1].split("="), this.armorPro);
		
		if (!(archetypeInfo[2].equalsIgnoreCase("None")))
			this.weaponPro = updateList(archetypeInfo[2].split("="), this.weaponPro);
		
		if (!(archetypeInfo[3].equalsIgnoreCase("None")))
			this.toolPro = updateList(archetypeInfo[3].split("="), this.toolPro);
		
		if (!(archetypeInfo[4].equalsIgnoreCase("None")))
			this.skillBonus = updateList(archetypeInfo[4].split("="), this.skillBonus);
		
		String [] newCastingList = archetypeInfo[6].split("=");
		if (newCastingList[0].equalsIgnoreCase("true")) 
		{
			initilizeSpellCastingTraits(newCastingList);
		}
		
		String [] bonusSpellList = archetypeInfo[5].split("=");
		if (bonusSpellList[0].equalsIgnoreCase("true")) 
		{
			completeSpellList = setBonusSpells(bonusSpellList[1]);
		}
		
		this.features = updateList(archetypeInfo[7].split("="), this.features);
		
		return completeSpellList;
		
	}
	
	private void initilizeSpellCastingTraits(String [] spellCastingTraitList) 
	{
		this.castingAbility = spellCastingTraitList[1];
		this.spellListFilePath = spellCastingTraitList[3];
		this.preparedOrKnownCaster = spellCastingTraitList[4].toUpperCase().charAt(0);
		setSpellsAndSlots(spellCastingTraitList[2]);
	}
	
	
	private void initilizeStandardFeatures() 
	{
		String featuresList [] = IOUtils.getCol(className, classStandFile, true);
		proBonus = getProBonus(featuresList[1]);
		hitDie = (int)Double.parseDouble(featuresList[2]);
		armorPro = finalProList(featuresList[3].split("="), race.getArmorPro(), null);
		weaponPro =  finalProList(featuresList[4].split("="), race.getWeaponPro(), null);
		toolPro =  finalProList(featuresList[5].split("="), race.getToolPro(), background.getToolProficiences());
		savingThrows = featuresList[6].split("=");
		
		String spellList [] = featuresList[7].split("=");
		if (spellList[0].equalsIgnoreCase("true")) {
			initilizeSpellCastingTraits(spellList);
		}
		
		features = setFeatures(featuresList[8]);
		
	}
	
	private int getProBonus(String line) 
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
	
	private String [] setFeatures(String line) 
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
	
	private void setSpellsAndSlots(String filepath) 
	{
		String [] spellInfo = IOUtils.getCol(level - 1, filepath);
		this.spellSlots = new int [spellInfo.length - 2];
		
		this.spellsKnown = this.preparedOrKnownCaster == 'K' ? (int)Double.parseDouble(spellInfo[1]) : -1;
		
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
	
	private void finalizeAbilityScores() 
	{
		int abilityScoreIncr [] = race.getAbilityScoreIncr();
		
		for (int i = 0; i < 6; i++) 
		{
			if (abilityScores[i] + abilityScoreIncr[i] < 20) {
				abilityScores[i] = abilityScores[i] + abilityScoreIncr[i];
			}
			else {
				abilityScores[i] = 20;
			}
		}
	}
	
	private String[] finalProList (String [] classPro, String [] racePro, String [] backPro) 
	{
		String [] list = classPro;
		if (racePro != null) {
			list = GenUtils.combineLists(racePro, list, true);
		}
		if (backPro != null) {
			list = GenUtils.combineLists(backPro, list, true);
		}
		return list;
	}
	
}
