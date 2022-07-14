package dndEntities;

import util.IOUtils;

public class Background {
	
	private Alignment alignment;
	private int backgroundIndex;
	private int [] money = {0, 0, 0, 0, 0};
	private String name, ideal, personalityTrait, bond, flaw;
	private String [] skillProficiences, toolProficiences, languages, equipment, idealList, features;
	public static final String backBondFileName = "backgrounds/Bonds.xlsx";
	public static final String backFlawFileName = "backgrounds/Flaws.xlsx";
	public static final String backIdealFileName = "backgrounds/Ideals.xlsx";
	public static final String backAttFileName = "backgrounds/Attributes.xlsx";
	public static final String backPersonFileName = "backgrounds/PersonalityTraits.xlsx";
	
	public Background (Alignment alignment, String name) 
	{
		this.alignment = alignment;
		this.name = name;
		this.backgroundIndex = IOUtils.getIndex(name, backAttFileName);
		this.idealList = this.backgroundIndex != -1 ?
				IOUtils.getCol(backgroundIndex, backIdealFileName) : null;
		initalizeBackgroundAtts();
	}
	
	public Background (Alignment alignment, int backgroundIndex) 
	{
		this.alignment = alignment;
		this.backgroundIndex = backgroundIndex;
		this.idealList = this.backgroundIndex != -1 ?
				IOUtils.getCol(backgroundIndex, backIdealFileName) : null;
		initalizeBackgroundAtts();
	}

	/*Getters for all traits of the background*/
	public String getIdeal() { return ideal; }
	public String getPersonalityTrait() { return personalityTrait; }
	public String getBond() { return bond; }
	public String getFlaw() { return flaw; }
	public Alignment getAlignment() { return alignment; }
	public String getName() { return name; }
	public String[] getSkillProficiences() { return skillProficiences; }
	public String[] getToolProficiences() { return toolProficiences; }
	public String[] getLanguages() { return languages; }
	public String[] getEquipment() { return equipment; }
	public int getBackgroundIndex() { return backgroundIndex; }
	public int[] getMoney() { return money; }
	public String[] getFeatures() { return features; }

	/*Simple Setters for all traits of the background*/
	public void setName(String name) {this.name = name;}
	public void setIdeal(String ideal) { this.ideal = ideal; }
	public void setPersonalityTrait(String personalityTrait) { this.personalityTrait = personalityTrait; }
	public void setBond(String bond) { this.bond = bond; }
	public void setFlaw(String flaw) { this.flaw = flaw; }
	public void setToolProficiences(String[] toolProficiences) { this.toolProficiences = toolProficiences; }
	public void setEquipment(String[] equipment) { this.equipment = equipment; }
	public void setLanguages(String[] languages) { this.languages = languages; }
	
	public boolean setIdeal(int idealNum) 
	{
		String ideal [] = null;
		
			if (this.idealList != null) 
			{
				ideal = idealList[idealNum].split("=");
				if (ideal[0].equalsIgnoreCase("Any") || alignment.getGEAxis().equalsIgnoreCase(ideal[0])
						|| alignment.getLCAxis().equalsIgnoreCase(ideal[0])) 
				{
					this.ideal = ideal[1];
					return true;
				}
			}
		
		return false;
	}
	
	private void initalizeBackgroundAtts() 
	{
		if (backgroundIndex != -1) {
			
			String atts [] = IOUtils.getCol(backgroundIndex, backAttFileName);
			
			if (name == null) {
				this.name = atts[0];
			}
			
			this.skillProficiences = atts[1].split("=");
			this.toolProficiences = atts[2].split("=");
			this.languages = atts[3].split("=");
			this.equipment = atts[4].split("=");
			initializeMoney(atts[5].split("="));
			this.features = atts[6].split("=");
		}
	}
	
	@Override
	public String toString() 
	{
		String background = "";
		background += "Background: " + name + "\n";
		background += "Personality Trait: " + personalityTrait + "\n";
		background += "Flaw: " + flaw + "\n";
		background += "Ideals: " + ideal + "\n";
		background += "Bonds: " + bond;
		
		return background;
	}
	
	private void initializeMoney(String [] money) 
	{
		int index = 0;
		switch (money[1].charAt(0)) 
		{
			case 'C':
				break;
			case 'S':
				index++; break;
			case 'E':
				index += 2; break;
			case 'G':
				index += 3; break;
			case 'P':
				index += 4; break;
		}
		this.money[index] =Integer.parseInt(money[0]);
	}
}
