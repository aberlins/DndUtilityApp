package util;

import dndEntities.CharacterSheet;

public class MathUtils 
{
	public static int getAbilityModifer(int abilityScore) 
	{
		return (abilityScore - 10)/2;
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
	
	public static int [] getTotalSkillScores(int [] abilityScores, int proBonus, String [] skillBonus) 
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
		return addProBonus ? abilityScore + proBonus : abilityScore;
	}
	

}
