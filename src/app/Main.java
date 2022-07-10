package app;

import dndEntities.Alignment;
import dndEntities.Background;
import dndEntities.CharacterSheet;
import dndEntities.DndClass;
import dndEntities.Race;
import util.IOUtils;
import util.RandUtils;

public class Main {

	private static int stats [];
	private static String trait;
	private static Race race;
	private static Alignment align;
	private static Background background;
	private static int level;
	
	
	public static void main(String[] args) 
	{
		
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
		CharacterSheet character = RandUtils.randomCharacter(dndclass);
		
		System.out.println(character);
		
	}
	
	private static class raceThread implements Runnable 
	{

		@Override
		public void run() 
		{
			level = RandUtils.randomLevel();
			stats = RandUtils.rollCharacterStats();
			
			race = RandUtils.randomRace(level);
			
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
