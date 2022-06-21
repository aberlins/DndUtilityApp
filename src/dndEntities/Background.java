package dndEntities;

import util.IOUtils;

public class Background {
	
	private Alignment alignment;
	private int backgroundIndex;
	private String name, ideal, personalityTrait, bond, flaw;
	private String [] skillProficiences, toolProficiences, languages, equipment;
	public static final String backgroundFileName = "backgrounds/backGroundPathway.txt";
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
		initalizeBackgroundAtts();
	}
	
	public Background (Alignment alignment, int backgroundIndex) 
	{
		this.alignment = alignment;
		this.backgroundIndex = backgroundIndex;
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

	/*Simple Setters for all traits of the background*/
	public void setName(String name) {this.name = name;}
	public void setIdeal(String ideal) { this.ideal = ideal; }
	public void setPersonalityTrait(String personalityTrait) { this.personalityTrait = personalityTrait; }
	public void setBond(String bond) { this.bond = bond; }
	public void setFlaw(String flaw) { this.flaw = flaw; }
	
	public void setLanguages(String [] languages) 
	{ 
		int index = 0;
		for (String lan: this.languages) 
		{
			if (lan.equals("Any") && languages.length > index) 
				lan = languages[index++];
		}
	}
	
	public boolean setIdeal(int idealNum) 
	{
		String idealList [] = null;
		String ideal [] = null;
		
		if(backgroundIndex != -1) 
		{
			idealList = IOUtils.getCol(backgroundIndex, backIdealFileName);
			
			if (idealList != null) 
			{
				ideal = idealList[idealNum].split("=");
				if (ideal[0].equalsIgnoreCase("Any") || alignment.getGEAxis().equalsIgnoreCase(ideal[0])
						|| alignment.getLCAxis().equalsIgnoreCase(ideal[0])) 
				{
					this.ideal = ideal[1];
					return true;
				}
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
		}
	}
}
