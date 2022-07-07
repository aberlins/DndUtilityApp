package dndEntities;
public class CharacterSheet {
	
	public static final String [] abilityNames = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
	public static final String [] skillNames = {"Acrobatics", "Animal Handling", "Arcana", "Athletics", "Deception", "History",
			"Insight", "Intimidation", "Investigation", "Medicine", "Nature", "Perception", "Performance", "Persuasion", 
			"Religion", "Sleight of Hand", "Stealth", "Survival"};
	private int [] abilityScores;
	private int hitPoints;
	private DndClass dndClass;
	
	public CharacterSheet (int [] abilityScores, DndClass dndClass) 
	{
		this.dndClass = dndClass;
		this.abilityScores = abilityScores;
		finalizeAbilityScores();
	}
	
	public void finalizeAbilityScores() 
	{
		int abilityScoreIncr [] = dndClass.getRace().getAbilityScoreIncr();
		for (int i = 0; i < 6; i++) 
		{
			abilityScores[i] = abilityScores[i] + abilityScoreIncr[i];
		}
	}

	public int[] getAbilityScores() { return abilityScores; }
	public int getHitPoints() { return hitPoints; }
	public DndClass getDndClass() { return dndClass; }

	public void setAbilityScores(int[] abilityScores) { this.abilityScores = abilityScores; }
	public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }
	
}
