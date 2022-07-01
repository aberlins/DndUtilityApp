package dndEntities;

public enum Gender {

	FEMALE("Female"),
	MALE("Male");
	
	private final String genderString;
	
	Gender (String genderString) 
	{
		this.genderString = genderString;
	}
	
	@Override
	public String toString() {
		return genderString;
	}
	
}
