package util;

import java.util.ArrayList;
import java.util.Arrays;

import dndEntities.Alignment;
import dndEntities.Background;
import dndEntities.DndClass;
import dndEntities.Gender;
import dndEntities.Race;

public class RandUtils {
	
	/*Utility Method used to roll up character's stats.
	Is done randomly not using a point base system.
	Value returned is an int array with the values corresponding to
	each stat as follows: Str, Dex, Con, Int, Wis, Cha*/
	public static int [] rollCharacterStats() 
	{
		//Stats to be returned
		int statList [] = new int [6];
		
		for (int i = 0; i < 6; i++) 
		{
			//Temporary Array meant to hold the 4 dice rolls for each stat
			int [] tempStats = new int[4];
			for (int j = 0; j < 4; j++) 
			{
				tempStats[j] = (int)(Math.random() * 6) + 1;
			}
			//Sort the array so the lowest value is in index 0
			Arrays.sort(tempStats);
			//Add up the remaining values to get that specific score.
			statList[i] = tempStats[1] + tempStats[2] + tempStats[3];
		}
		return statList;
	}
	
	/*Utility Method used to calculate a Character's hit points
	given their class's die size, their character level and their conMod.
	This only works for single class characters.*/
	public static int rollHitPoints(int dieSize, int level, int conMod) 
	{
		//If conMod is negative, ignore it.
		if (conMod < 0)
			conMod = 0;
		
		//Take the max roll for level 1
		int hitPoint = dieSize + conMod;
		
		//For each level roll the character's hit points and add it to the total.
		for (int i = 0; i < level - 1; i++) 
		{
			hitPoint += (int)(Math.random() * dieSize) + 1 + conMod;
		}
		return hitPoint;
	}
	
	/*Utility Method used to pick random traits from a list/file.
	Depending on the file extension, this method will choose a random entry from a text file 
	or a Excel file. 
	Index number used for processing Excel files, does not matter for text files.*/
	public static String randomTrait(String fileName, int index)
	{
		if (fileName.endsWith(".txt")) 
		{
			String [] traitArray = IOUtils.getContentsFromtxt(fileName);
			
			//Return final result.
			int dieRoll = (int)(Math.random() * traitArray.length);
			return traitArray[dieRoll];
		}
		else if (fileName.endsWith(".xlsx")) 
		{
			String attributes [] = IOUtils.getCol(index, fileName);
			int numChoices = attributes.length - 1;
			
			int dieRoll = (int)(Math.random() * numChoices) + 1;
			return attributes[dieRoll];
		}
		
		return null;
		
	}
	
	/*Utility Method used to generate one of the nine alignments in DND 5e.*/
	public static Alignment randomAlignment() 
	{
		//Get all the values from the alignment enum as well as the total size 
		Alignment [] alignVals = Alignment.values();
		
		return alignVals[(int)(Math.random() * alignVals.length)];
	}
	
	/* Utility Method used to return a random age given the max age of a race.*/
	public static int randomAge(int maxAge) 
	{
		return (int)(Math.random() * maxAge) + 1;
	}
	
	
	/* Utility method used to return a random gender for a character.
	As of now only male and female are available.*/
	public static Gender randomGender() 
	{
		
		//Get all the values from the gender enum as well as the total size 
		Gender [] genderVals = Gender.values();
		
		return genderVals[(int)(Math.random() * genderVals.length)];
		
	}
	
	/*Utility Method used to generate a random name given the character's race and gender.
	For now only Male and Female are available options for first names. */
	public static String randomName(Race race, Gender gender)
	{
		String filePath = IOUtils.getAttributeFolder(race.getName(), Race.raceFileName);
		
		//First generate the first name depending on the gender
		String name = null;
		
		if (filePath != null) {
			filePath += "/Names.xlsx";
			switch (gender) 
			{
				case MALE:
					name = randomTrait(filePath, 0);
					break;
				case FEMALE:
					name = randomTrait(filePath, 1);
					break;
			}
			//Add the last name to the value and then return it.
			name += " " + randomTrait(filePath, 2);
		}
		
		return name;
	}
	
	public static Background randomBackground(Alignment alignment) 
	{
		
			String backgroundList [] = IOUtils.getRow(0, Background.backAttFileName);
			Background background = new Background(alignment, (int) (Math.random() * backgroundList.length));
			
			randomIdeal(background);
			background.setBond(randomTrait(Background.backBondFileName, background.getBackgroundIndex()));
			background.setFlaw(randomTrait(Background.backFlawFileName, background.getBackgroundIndex()));
			background.setPersonalityTrait(randomTrait(Background.backPersonFileName, background.getBackgroundIndex()));
			
			String oldToolProf [] = background.getToolProficiences();
			String newToolProf;
			
			for (int i = 0; i < oldToolProf.length; i++) 
			{
				if (oldToolProf[i].startsWith("*")) 
				{
					newToolProf = randomToolProf(oldToolProf[i].substring(1));
					String [] equip = background.getEquipment();
					for (int j = 0; j < equip.length; j++) 
					{
						if (equip[j].equalsIgnoreCase(oldToolProf[i])) 
						{
							equip[j] = newToolProf;
							background.setEquipment(equip);
							break;
						}
					}
					oldToolProf[i] = newToolProf;
				}
			}
			
			background.setToolProficiences(oldToolProf);
			
			return background;
	}
	
	private static void randomIdeal(Background background) 
	{
		int index = background.getBackgroundIndex();
		String ideals [] = IOUtils.getCol(index, Background.backIdealFileName);
		int numChoices = ideals.length - 1;
		
		boolean continueLoop = true;
		while (continueLoop) 
		{
			int idealIndex = (int)(Math.random() * numChoices) + 1;
			if (background.setIdeal(idealIndex)) {
				continueLoop = false;
			}
		}
	}
	
	public static Race randomRace() 
	{
		String raceContents [] = IOUtils.getContentsFromtxt(Race.raceFileName);
		String raceList [] = new String[raceContents.length];
		int counter = 0;
		for (String raceName: raceContents) 
		{
			raceList[counter++] = raceName.split("=")[0];
		}
		return new Race(raceList[(int)(Math.random() * raceList.length)]);
	}
	
	public static DndClass randomDndClass(Race race, Background background, int level) {
		
		String classList [] = IOUtils.getRow(0, DndClass.classStandFile);
		String className = classList[(int) (Math.random() * classList.length)];
		DndClass dndclass = new DndClass(race, background, className, level);
		
		String [] choiceFeatures = dndclass.getChoicesList();
		
		String [] skillBonusRules = choiceFeatures[1].split("=");
		String [] listOfSkills = skillBonusRules[1].split("/");
		dndclass.setSkillBonus(randomList(listOfSkills, Integer.parseInt(skillBonusRules[0])));
		
		dndclass.setWeapons(randomEquip(choiceFeatures[2].split("=")));
		dndclass.setArmors(randomEquip(choiceFeatures[3].split("=")));
		dndclass.setOtherItems(randomEquip(choiceFeatures[4].split("=")));
		
		String [] archetypeChoices = choiceFeatures[5].split("=");
		if (!(archetypeChoices[0].equalsIgnoreCase("None"))) {
			String [] archetypes = IOUtils.getRow(0, archetypeChoices[1]);
			dndclass.setPathTitle(archetypeChoices[0] + " " + 
				archetypes[(int)(Math.random() * archetypes.length - 1) + 1]);
		}
		
		return dndclass;
		
	}
	
	public static String [] randomList(String [] list, int numOfItems) 
	{
		String [] finalList = new String[numOfItems];
		int index = 0;
		
		do
		{
			String item = list[(int) (Math.random() * list.length)];
			boolean notInList = true;
			for (int i = 0; i < numOfItems; i++) 
			{
				if (finalList[i] != null && finalList[i].equalsIgnoreCase(item)) 
				{
					notInList = false;
					break;
				}
			}
			
			if (notInList)  {
				finalList[index++] = item;
			}
			
		} while (index < numOfItems);
		
		return finalList;
	}
	
	//Flags for method:
	// * - means check to see if character is proficient with item
	// ! - means and or multiple items in String
	// @ - means a certain number of the same item (ex: 2@Dagger is 2 daggers)
	// ? - means string is a category of item and needs to be randomly selected.
	private static String [] randomEquip(String [] list) 
	{
		ArrayList<String> finalList = new ArrayList<>();
		
		for (String items: list) 
		{
			boolean invalidChoice = true;
			do 
			{
				String itemList [] = items.split("/");
				String selectedItem = itemList[(int)(Math.random() * itemList.length)];
			
				if ((!(selectedItem.startsWith("*")) && (!finalList.contains(selectedItem)))) 
				{
					if (selectedItem.contains("!"))
					{
						itemList = selectedItem.split("!");
						for (String newItems: itemList) 
						{
							finalList.add(newItems);
						}
					}
					else 
					{
						finalList.add(selectedItem);
					}
					invalidChoice = false;
				}
			} while (invalidChoice);
			
		}
		
		ArrayList<String> removeItems = new ArrayList<>();
		ArrayList<String> addItems = new ArrayList<>();
		
		for (String item: finalList) 
		{
			if (item.contains("@")) 
			{
				String [] multiItems = item.split("@");
				removeItems.add(item);
				for (int i = 0; i < Integer.parseInt(multiItems[0]); i++) 
				{
					addItems.add(multiItems[1]);
				}
			}
		}
		
		for (String item: removeItems) 
		{
			finalList.remove(item);
		}
		for (String item: addItems) 
		{
			finalList.add(item);
		}
		
		return finalList.toArray(new String[0]);
	}
	
	private static String randomToolProf (String toolType) 
	{
		int toolIndex = IOUtils.getIndex(toolType, IOUtils.toolTypeFile);
		String [] tools = IOUtils.getCol(toolIndex, IOUtils.toolTypeFile);
		return tools[(int)(Math.random() * tools.length - 1) + 1];
	}
	
}
