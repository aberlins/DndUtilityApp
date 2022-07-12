package util;

import dndEntities.CharacterSheet;

public class MathUtils 
{
	public static int getAbilityModifer(int abilityScore) 
	{
		if (abilityScore > 9)
			return (abilityScore - 10)/2;
		else {
			double score = (abilityScore - 10);
			return (int) Math.floor(score/2);
		}
	}
	
	public static int [] getSavingThrowScores(int [] abilityScores, String [] savingThrows, int proBonus) 
	{
		int proBonuses [] = {0,0,0,0,0,0};
		int [] savingThrowScores = new int [6];
		
		for (int i = 0; i < savingThrowScores.length; i++) 
		{
			savingThrowScores[i] = getAbilityModifer(abilityScores[i]);
		}
		
		
		for (String ability: savingThrows) 
		{
			switch(ability) 
			{
				case "Strength":
					proBonuses[0] = proBonus;
					break;
				case "Dexterity":
					proBonuses[1] = proBonus;
					break;
				case "Constitution":
					proBonuses[2] = proBonus;
					break;
				case "Intelligence":
					proBonuses[3] = proBonus;
					break;
				case "Wisdom":
					proBonuses[4] = proBonus;
					break;
				case "Charisma":
					proBonuses[4] = proBonus;
					break;
			}
		}
		
		for (int i = 0; i < savingThrowScores.length; i++) 
		{
			savingThrowScores[i] += proBonuses[i];
		}
		
		return savingThrowScores;
	}
	
	public static int [] getSkillScores(int [] abilityScores, int proBonus, String [] skillBonus) 
	{
		String[] skillNames = CharacterSheet.skillNames;
		int[] skillScores = new int [skillNames.length];
		boolean [] addProBonus = new boolean [skillNames.length];
		
		for (int i = 0; i < addProBonus.length; i++) 
		{
			addProBonus[i] = false;
		}
		
		for (int i = 0; i < skillBonus.length; i++) 
		{
			int counter = 0;
			do {
				
				if(skillBonus[i].equalsIgnoreCase(skillNames[counter])) 
				{
					addProBonus[counter++] = true;
					break;
				}
				counter++;
				
			} while (true);
		}
		
		
		for (int i = 0; i < skillScores.length; i++) 
		{
			switch(skillNames[i]) 
			{
				case "Athletics":
					skillScores[i] = getSkillScore(abilityScores[0], proBonus, addProBonus[i]);
					break;
				case "Acrobatics": case "Sleight Of Hand": case "Stealth":
					skillScores[i] = getSkillScore(abilityScores[1], proBonus, addProBonus[i]);
					break;
				case "Arcana": case "History": case "Investigation": case "Nature": case "Religion":
					skillScores[i] = getSkillScore(abilityScores[3], proBonus, addProBonus[i]);
					break;
				case "Animal Handling": case "Insight": case "Medicine": case "Perception": case "Survival":
					skillScores[i] = getSkillScore(abilityScores[4], proBonus, addProBonus[i]);
					break;
				case "Deception": case "Intimidation": case "Performance": case "Persuasion":
					skillScores[i] = getSkillScore(abilityScores[5], proBonus, addProBonus[i]);
					break;
			}
		}
		
		return skillScores;
	}
	
	public static int getSkillScore (int abilityScore, int proBonus, boolean addProBonus) 
	{
		return addProBonus ? getAbilityModifer(abilityScore) + proBonus : getAbilityModifer(abilityScore);
	}
	
	public static int getInitativeScore (int dexerityScore, int [] bonuses) 
	{
		int init = getAbilityModifer(dexerityScore);
		
		if (bonuses != null) {
			for (int bonus: bonuses) 
			{
				init += bonus;
			}
		}
		
		return init;
	}
	
	public static int getPassiveWisdomScore (int perception, int bonus) {
		return perception + bonus + 10;
	}
	
	public static int getArmorClass (int dexerityScore, String [] armors) 
	{
		int armorClass =  0;
		int dexBonus = getAbilityModifer(dexerityScore);
		
		for (String armor: armors) 
		{
			String [] armorStats = IOUtils.getCol(armor, IOUtils.armorStatsFile, true);
			
			String [] addDexInfo = armorStats[2].split("=");
			
			if (addDexInfo[0].equalsIgnoreCase("true")) 
			{
				if (addDexInfo[1].equalsIgnoreCase("true")) 
				{
					if (Double.parseDouble(addDexInfo[2]) < armorClass) {
						armorClass += (int)Double.parseDouble(armorStats[1]) 
								+ (int) Double.parseDouble(addDexInfo[2]);
					}
					else {
						armorClass += (int)Double.parseDouble(armorStats[1]) + dexBonus;
					}
				}
				else 
				{
					armorClass += (int)Double.parseDouble(armorStats[1]) + dexBonus;
				}
			}
			else {
				armorClass += (int)Double.parseDouble(armorStats[1]);
			}
		}
		
		return armorClass;
	}
	
	public static int getAttackBonus(int strengthScore, int dexerityScore, int proBonus, String weapon) 
	{
		String [] weaponStats = IOUtils.getCol(weapon, IOUtils.weaponStatsFile, true);
		
		int abilityMod;
		
		if (weaponStats[2].split("=")[0].equalsIgnoreCase("true")) 
		{
			abilityMod =  getAbilityModifer(dexerityScore);
		}
		else if (weaponStats[3].split("=")[0].equalsIgnoreCase("true")) 
		{
			if (dexerityScore > strengthScore) {
				abilityMod =  getAbilityModifer(dexerityScore);
			}
			else {
				abilityMod =  getAbilityModifer(strengthScore);
			}
		}
		else {
			abilityMod =  getAbilityModifer(strengthScore);
		}
		
		return abilityMod + proBonus;
	}
	
	public static int getSpellAttackBonus(String castingAbility, int proBonus, int [] abilityScores) 
	{
		int castingAbilityPos = getCastingAbilityPos(castingAbility);
		
		return proBonus + getAbilityModifer(abilityScores[castingAbilityPos]);
	}
	
	public static int getSpellSaveDC(String castingAbility, int proBonus, int [] abilityScores) 
	{
		return getSpellAttackBonus(castingAbility, proBonus, abilityScores) + 8;
	}
	
	public static int getCastingAbilityPos(String castingAbility) 
	{
		switch(castingAbility) {
		case "Intelligence":
			return 3;
		case "Wisdom":
			return 4;
		case "Charisma":
			return 5;
		default:
			return -1;
		}
	}
	
}
