package dndEntities;

public enum Alignment 
{
	LAWFUL_GOOD ("Lawful", "Good"),
	NEUTRAL_GOOD("Neutral", "Good"),
	CHAOTIC_GOOD("Chaotic", "Good"),
	LAWFUL_NEUTRAL("Lawful", "Neutral"),
	TRUE_NEUTRAL("Neutral", "Neutral"),
	CHAOTIC_NEUTRAL("Chaotic", "Neutral"),
	LAWFUL_EVIL ("Lawful", "Evil"),
	NEUTRAL_EVIL("Neutral", "Evil"),
	CHAOTIC_EVIL("Chaotic", "Evil");
	
	private final String LCAxis, GEAxis;
	
	Alignment (String LCAxis, String GEAxis) 
	{
		this.LCAxis = LCAxis;
		this.GEAxis = GEAxis;
	}

	public String getLCAxis() {
		return LCAxis;
	}

	public String getGEAxis() {
		return GEAxis;
	}
	
	@Override 
	public String toString() 
	{
		if (this.equals(TRUE_NEUTRAL)) 
			return "True Neutral";
		else
			return LCAxis + " " + GEAxis;
	}
}
