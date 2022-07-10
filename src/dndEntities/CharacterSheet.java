package dndEntities;

import java.util.ArrayList;

import util.MathUtils;

public class CharacterSheet {
	
	public static final String [] abilityNames = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
	public static final String [] skillNames = {"Acrobatics", "Animal Handling", "Arcana", "Athletics", "Deception", "History",
			"Insight", "Intimidation", "Investigation", "Medicine", "Nature", "Perception", "Performance", "Persuasion", 
			"Religion", "Sleight of Hand", "Stealth", "Survival"};
	private int hitPoints, age;
	private Gender gender;
	private String name;
	private DndClass dndClass;
	
	public CharacterSheet (DndClass dndClass, Gender gender, String name, int age) 
	{
		this.dndClass = dndClass;
		this.gender = gender;
		this.age = age;
		this.name = name;
		
	}
	
	public int getHitPoints() { return hitPoints; }
	public DndClass getDndClass() { return dndClass; }
	public Gender getGender() { return gender; }
	public String getName() { return name; }
	public int getAge() { return age; }
	public Alignment getAlignment() { return dndClass.getBackground().getAlignment(); }
	public int[] getAbilityScores() { return dndClass.getAbilityScores(); }
	public int[] getAbilityModifers() { return dndClass.getAbilityModifers(); }
	public int [] getSavingThrowScores() { return dndClass.getSavingThrowScores(); }
	public int [] getSkillScores() { return dndClass.getSkillScores(); }
	public int getLevel() {return dndClass.getLevel(); }
	public int getInitative() { return dndClass.getInitative(); }
	public int getProBonus() {return dndClass.getProBonus(); }
	public int getSpeed() { return dndClass.getRace().getSpeed(); }
	public int getArmorClass() {return dndClass.getArmorClass();}
	public int getPassiveWisdom() { return dndClass.getPassiveWisdom(); }
	public String getCastingAbility() { return dndClass.getCastingAbility(); }
	public int getSpellAttackBonus() { return dndClass.getSpellAttackBonus(); }
	public int getSpellSaveDC() { return dndClass.getSpellSaveDC(); } 
	public String getRaceName() { return dndClass.getRace().getName(); }
	public String getRaceCastingAbility() { return dndClass.getRace().getCastingAbility(); }
	public int getRacialSpellAttackBonus() { return dndClass.getRacialSpellAttackBonus(); }
	public int getRacialSpellSaveDC() { return dndClass.getRacialSpellSaveDC(); }
	public String getClassName() { return dndClass.getClassName();}
	public String getPathTitle() { return dndClass.getPathTitle(); }
	public String [][] getWeaponStats() { return dndClass.getWeaponStats(); }
	public String [] getArmor() { return dndClass.getArmors(); }
	public ArrayList<String>[] getClassSpells() {return dndClass.getSpells();}
	public ArrayList<String>[] getRaceSpells() {return dndClass.getRace().getSpells();}
	public String [] getEquipment() { return dndClass.getEquipment(); }
	
	

	public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }

	@Override
	public String toString() 
	{
		String pathTitle = getPathTitle();
		ArrayList<String>[] classSpells = getClassSpells();
		ArrayList<String>[] raceSpells = getRaceSpells();
		String sheet = "";
		sheet += upperSheetString() + "\n";
		if (pathTitle != null) {
			sheet += getPathTitle() +"\n";
		}
		sheet += abilityString() + "\n";
		sheet += skillString() + "\n";
		sheet += dndClass.getBackground().toString() + "\n";
		sheet += "--------------------------------------------------------------------\n";
		sheet += "Weapons\n";
		sheet += weaponString() + "\n";
		sheet += "--------------------------------------------------------------------\n";
		sheet += "Inventory\n";
		sheet += inventoryString() + "\n";
		if (classSpells != null) {
			sheet += "--------------------------------------------------------------------\n";
			sheet += "Class Spells:\n";
			sheet += "Casting Ability:\tSpell Save DC:\t\tSpell Attack Bonus:\n";
			sheet += getCastingAbility() +"\t\t\t";
			if (getCastingAbility().length() < 8) {
				sheet += "\t";
			}
			sheet += getSpellSaveDC() + "\t\t\t";
			sheet += getSpellAttackBonus() + "\n";
			sheet += spellString(classSpells) + "\n";
			sheet += "--------------------------------------------------------------------\n";
		}
		if (raceSpells != null) {
			if (classSpells == null) {
				sheet += "--------------------------------------------------------------------\n";
			}
			sheet += "Race Spells:\n";
			sheet += "Casting Ability:\tSpell Save DC:\t\tSpell Attack Bonus:\n";
			sheet += getRaceCastingAbility() +"\t\t\t";
			if (getRaceCastingAbility().length() < 8) {
				sheet += "\t";
			}
			sheet += getRacialSpellSaveDC() + "\t\t\t";
			sheet += getRacialSpellAttackBonus() + "\n";
			sheet += spellString(raceSpells) + "\n";
		}
		
		sheet += "--------------------------------------------------------------------\n";
		
		return sheet;
	}
	
	private String upperSheetString() 
	{
		String upper = "";
		
		upper += "Name: " + name + "\n";
		upper += "Gender: " + gender + "\n";
		upper += "Age: " + age + "\n";
		upper += "Race: " + getRaceName() + "\n";
		upper += "Alignment: " + getAlignment() + "\n";
		upper += "Level: " + getLevel() + "\t";
		upper += "Proficiency Bonus: " + getProBonus() + "\t";
		upper += "Hit Points: " + hitPoints + "\n";
		upper += "Speed: " + getSpeed() + "\t";
		upper += "Initative: " + getInitative() + "\t";
		upper += "Armor Class: " + getArmorClass() + "\n";
		upper += "Class: " + getClassName();
		
		return upper;
	}
	
	private String abilityString() 
	{
		int [] abilityScores = getAbilityScores();
		int [] abilityModifers = getAbilityModifers();
		int [] savingThrowScores = getSavingThrowScores();
		String ability = "";
		ability += "------------------------------------------------------\n";
		ability += "Ability Scores\n";
		ability += "Ability: \t\tScore:\tModifer: Saving Throw:\n";
		for (int i = 0; i < abilityNames.length; i++) 
		{
			ability += abilityNames[i] + "  \t\t " + abilityScores[i] + "\t  " + abilityModifers[i] + 
					"\t       "
					+ savingThrowScores[i] + "\n";
		}
		ability += "------------------------------------------------------";
		
		return ability;
	}
	
	private String skillString() 
	{
		int [] skillScores = getSkillScores();
		String skill = "";
		skill += "Skills\n";
		skill += "Skill: \t\t\tScore:\n";
		for (int i = 0; i < skillNames.length; i++) 
		{
			if (skillNames[i].length() > 7) {
				skill += skillNames[i] + "\t\t  " + skillScores[i] + "\n";
			}
			else {
				skill += skillNames[i] + "\t\t\t  " + skillScores[i] + "\n";
			}
		}
		skill += "Passive Wisdom: " + getPassiveWisdom() + "\n";
		skill += "------------------------------------------------------";
		
		return skill;
	}
	
	private String spellString(ArrayList<String> [] spells) 
	{
		String spellString = "";
		int counter = 0;
		for (ArrayList<String> spellLevel: spells) 
		{
			spellString += "Spell Level " + counter++ + ": ";
			for (String spell: spellLevel) 
			{
				spellString += spell + ", ";
			}
			spellString = spellString.substring(0, spellString.length() - 2);
			spellString += "\n";
		}
		return spellString.substring(0, spellString.length() - 1);
	}
	
	private String weaponString() 
	{
		String weaponString = "";
		String [][] weaponStats = getWeaponStats();
		
		weaponString += "Name:\t\tAtk Bonus:\tDamage/Type:\n";
		
		for (String [] weapon : weaponStats) 
		{
			if (weapon[0].length() < 8) { 
				weaponString += weapon[0] + "\t\t    " + weapon[1] + "\t\t  " +  weapon[2] + "\n";
			}
			else {
				weaponString += weapon[0] + "\t    " + weapon[1] + "\t\t  " +  weapon[2] + "\n";
			}
		}
		
		return weaponString.substring(0, weaponString.length() - 1);
		
	}
	
	private String inventoryString() 
	{
		String inventoryString = "";
		String [] armors = getArmor();
		String [] equipment = getEquipment();
		
		inventoryString += "Armors:\n";
		
		for (String armor: armors) 
		{
			inventoryString += armor + ", ";
		}
		inventoryString = inventoryString.substring(0, inventoryString.length() - 2) + "\n";
		
		inventoryString += "Items:\n";
		
		int textwrap = 0;
		for (String item: equipment) 
		{
			if (textwrap > 2) {
				inventoryString += "\n";
				textwrap = 0;
			}
			
			inventoryString += item + ", ";
			textwrap++;
		}
		
		return inventoryString.substring(0, inventoryString.length() - 2);
	}
	
}
