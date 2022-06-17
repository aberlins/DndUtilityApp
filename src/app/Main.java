package app;

import java.io.FileNotFoundException;
import java.io.IOException;

import dndEntities.Alignment;
import dndEntities.Background;
import dndEntities.Race;
import util.RandUtils;

public class Main {

	public static void main(String[] args) 
	{
		int test [] = RandUtils.rollCharacterStats();
		Race race = null;
		try {
			race = RandUtils.randomRace();
		} catch (FileNotFoundException e1) {
			System.out.println("Issue in finding the appropriate dnd race file.");
			System.exit(1);
		} catch (IOException e1) {
			System.out.println("Issue in reading the appropriate dnd race file.");
			System.exit(1);
		} catch (Exception e1) {
			System.out.println("An error has occured.");
			System.exit(1);
		}
		
		String name = null;
		String gender = RandUtils.randomGender();
		
		if (race != null)
		{
			try {
				name = RandUtils.randomName(race, gender);
			} catch (FileNotFoundException e) {
				System.out.println("Issue in finding the appropriate dnd race name file.");
				System.exit(1);
			} catch (IOException e) {
				System.out.println("Issue in reading the appropriate dnd race name file.");
				System.exit(1);
			}
			catch (Exception e) {
				System.out.println("An error has occured.");
				System.exit(1);
			}
			
			System.out.println("Name is: " + name);
			System.out.println("Gender is: " + gender);
			System.out.println("Race is: " + race.getName());
			System.out.println("Age is: " + RandUtils.randomAge(race.getMaxAge()));
			int raceAbBon [] = race.getAbilityScoreIncr();
			for (int i = 0; i < 6; i++) 
			{
				test[i] = test[i] + raceAbBon[i];
			}
		}
		
		int level = 2, dieSize = 6, conMod = (test[2] - 10)/2;
		String [] abilityNames = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
		Alignment align = RandUtils.randomAlignment();
		
		System.out.println("This new Character's base stat rolls are: ");
		
		for (int i = 0; i < 6; i++) 
		{
			System.out.println(abilityNames[i] + ": \t" + test[i]);
		}
		
		System.out.println("Level is: " + level);
		System.out.println("Alignment is: " + align);
		System.out.println("Hit Points are: " + 
				RandUtils.rollHitPoints(dieSize, level, conMod));
		
		String fileName = "races/aasimar/traits.txt";
		
		try {
			Background background = RandUtils.randomBackground(align);
			System.out.println("Background is: " + background.getName());
			System.out.println("Personality Trait is: " + background.getPersonalityTrait());
			System.out.println("Flaw is: " + background.getFlaw());
			System.out.println("Ideals are: " + background.getIdeal());
			System.out.println("Bonds are: " + background.getBond());
			System.out.println("Random Trait is: " + RandUtils.randomTrait(fileName, -1));
		}
		catch (Exception e) {
			System.out.println("A formatting issue has occurred with the file: " + fileName);
			e.printStackTrace();
		}

	}

}
