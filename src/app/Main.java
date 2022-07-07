package app;

import java.util.ArrayList;

import dndEntities.Alignment;
import dndEntities.Background;
import dndEntities.DndClass;
import dndEntities.Gender;
import dndEntities.Race;
import util.IOUtils;
import util.RandUtils;

public class Main {

	private static int stats [];
	private static final String [] abilityNames = {"Strength", "Dexterity", "Constitution", "Intelligence", "Wisdom", "Charisma"};
	private static String name, trait;
	private static Gender gender;
	private static Race race;
	private static Alignment align;
	private static Background background;
	private static int age, level, conMod, hitPoints;
	
	
	public static void main(String[] args) 
	{
		
		level = RandUtils.randomLevel();
		
		Thread race_Thread = new Thread(new raceThread());
		Thread background_Thread = new Thread(new backgroundThread());
		
		race_Thread.start();
		background_Thread.start();
		
		try {
			race_Thread.join();
			background_Thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DndClass dndclass = RandUtils.randomDndClass(race, background, level, stats);
		hitPoints = RandUtils.rollHitPoints(dndclass.getHitDie(), level, conMod);
		stats = RandUtils.ranomdAbilityScoreImprov(level, stats);
		
		System.out.println("Name is: " + name);
		System.out.println("Gender is: " + gender);
		System.out.println("Race is: " + race.getName());
		System.out.println("Age is: " + age);
		System.out.println("Level is: " + level);
		System.out.println("This new Character's base stat rolls are: ");
		for (int i = 0; i < 6; i++) 
		{
			System.out.println(abilityNames[i] + ": \t" + stats[i]);
		}
		System.out.println("Alignment is: " + align);
		System.out.println("Hit Points are: " + 
				hitPoints);
		System.out.println("Background is: " + background.getName());
		System.out.println("Personality Trait is: " + background.getPersonalityTrait());
		System.out.println("Flaw is: " + background.getFlaw());
		System.out.println("Ideals are: " + background.getIdeal());
		System.out.println("Bonds are: " + background.getBond());
		System.out.println("Random Trait is: " + trait);
		
		System.out.println("Class is: " + dndclass.getClassName());
		System.out.println("Archetype is: " + dndclass.getPathTitle());
		
		ArrayList<String> [] spellx = dndclass.getSpells();
		if (spellx != null) 
		{
			int counter = 0;
			for (ArrayList<String> spellLevel: spellx) 
			{
				System.out.print("Spell Level " + counter++ + ": ");
				for (String spell: spellLevel) 
				{
					System.out.print(spell + ", ");
				}
				System.out.println("");
			}
		}
	}
	
	private static class raceThread implements Runnable 
	{

		@Override
		public void run() 
		{
			stats = RandUtils.rollCharacterStats();
			gender = RandUtils.randomGender();
			
			race = RandUtils.randomRace(level);
			
			if (race != null)
			{
				
				name = RandUtils.randomName(race, gender);
				age =  RandUtils.randomAge(race.getMaxAge());
				
				int raceAbBon [] = race.getAbilityScoreIncr();
				for (int i = 0; i < 6; i++) 
				{
					stats[i] = stats[i] + raceAbBon[i];
				}
			}
			
			conMod = (stats[2] - 10)/2;
			
			String fileName = "races/aasimar/traits.txt";
			trait = RandUtils.randomTrait(fileName, -1);
		}
		
	}
	
	private static class backgroundThread implements Runnable 
	{

		@Override
		public void run() {
			align = RandUtils.randomAlignment();
			background = RandUtils.randomBackground(align);
			
		}
		
	}
	
}
