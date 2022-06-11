package dndEntities;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import util.IOUtils;

public class Background {
	
	private Alignment alignment;
	private String name, ideal, personalityTrait, bond, flaw;
	private String [] skillProficiences, toolProficiences, languages, equipment;
	public static final String backgroundFileName = "backgrounds/backGroundPathway.txt";
	public static final String backIdealFileName = "backgrounds/Ideals.xlsx";
	public static final String backAttFileName = "backgrounds/Attributes.xlsx";
	
	public Background (Alignment alignment, String name) 
	{
		this.alignment = alignment;
		this.name = name;
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

	/*Simple Setters for all traits of the background*/
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
	
	public boolean setIdealExcel(int idealNum) 
	{
		String idealList [] = null;
		String ideal [] = null;
		int backgroundIndex = getBackgroundIndex();
		
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
	
	public boolean setIdeal(long idealNum) 
	{
		String fileName = null;
		try {
			fileName = IOUtils.getAttributeFolder(name, backgroundFileName);
			if (fileName != null) 
			{
				fileName += "/ideals.txt";
				try (Scanner fileStream = new Scanner(new File(fileName))) 
				{
					int lineCounter = 1;
					String [] idealList = null;
					while (fileStream.hasNextLine()) 
					{
						if (lineCounter++ == idealNum) 
						{
							idealList = fileStream.nextLine().split("=");
							break;
						}
						else
							fileStream.nextLine();
					}
					
					if (idealList == null) {
						return false;
					}
					else if (idealList[0].equalsIgnoreCase("Any") || alignment.getGEAxis().equalsIgnoreCase(idealList[0])
							|| alignment.getLCAxis().equalsIgnoreCase(idealList[0])) 
					{
						this.ideal = idealList[1];
						return true;
					}
				}
			}
			else 
				return false;
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("An error has occured locating the files: " + fileName + ", " + backgroundFileName);
		}
		catch (IOException e) 
		{
			System.out.println("An error has occured when reading the files: " + fileName + ", " + backgroundFileName);
		}
		return false;
	}
	
	public boolean setPersonalityTrait(long persNum) 
	{
		String personalityTrait = setAttributeFromFile(persNum, "/personalityTraits.txt");
		if (personalityTrait != null) {
			this.personalityTrait = personalityTrait;
			return true;
		}
		return false;
	}
	
	public boolean setBond(long bondNum) 
	{
		String bond = setAttributeFromFile(bondNum, "/bonds.txt");
		if (bond != null) {
			this.bond = bond;
			return true;
		}
		return false;
	}
	
	public boolean setFlaw(long flawNum) 
	{
		String flaw = setAttributeFromFile(flawNum, "/flaws.txt");
		if (flaw != null) {
			this.flaw = flaw;
			return true;
		}
		return false;
	}
	
	private void initalizeBackgroundAtts() 
	{
		String fileName = null;
		try {
			fileName = IOUtils.getAttributeFolder(name, backgroundFileName);
			if (fileName != null) 
			{
				fileName += "/attributes.txt";
				try (Scanner fileStream = new Scanner(new File(fileName))) 
				{
					this.skillProficiences = fileStream.nextLine().split("=");
					this.toolProficiences = fileStream.nextLine().split("=");
					this.languages = fileStream.nextLine().split("=");
					this.equipment = fileStream.nextLine().split("=");
				}
			}
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("An error has occured locating the files: " + fileName + ", " + backgroundFileName);
		}
		catch (IOException e) 
		{
			System.out.println("An error has occured when reading the files: " + fileName + ", " + backgroundFileName);
		}
	}
	
	private String setAttributeFromFile(long index, String fileExtension) 
	{
		String fileName = null;
		try {
			fileName = IOUtils.getAttributeFolder(name, backgroundFileName);
			if (fileName != null) 
			{
				fileName += fileExtension;
				try (Scanner fileStream = new Scanner(new File(fileName))) 
				{
					int numOfOptions = Integer.parseInt(fileStream.nextLine());
					if (index > numOfOptions)
						return null;
					else 
					{
						int currentLine = 1;
						while (index != currentLine && fileStream.hasNextLine()) 
						{
							fileStream.nextLine(); currentLine++;
						}
						
						if (fileStream.hasNextLine())
							return fileStream.nextLine();
					}
				}
			}
		}
		catch (FileNotFoundException e) 
		{
			System.out.println("An error has occured locating the files: " + fileName + ", " + backgroundFileName);
		}
		catch (IOException e) 
		{
			System.out.println("An error has occured when reading the files: " + fileName + ", " + backgroundFileName);
		}
		catch (NumberFormatException e)
		{
			System.out.println("Improper formatting with the file: " + fileName);
		}
		return null;
	}
	
	public int getBackgroundIndex() 
	{
		String backgroundlist [] = IOUtils.getRow(0, backAttFileName);
		
		for (int i = 0; i < backgroundlist.length; i++) 
		{
			if (backgroundlist[i].equalsIgnoreCase(name)) {
				return i;
			}
		}
		
		return -1;
	}
}
