package app;

import java.util.ArrayList;

import dndEntities.Alignment;
import dndEntities.Background;
import dndEntities.CharacterSheet;
import dndEntities.DndClass;
import dndEntities.Race;
import util.IOUtils;
import util.MathUtils;
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
		
		ArrayList<String> data = new ArrayList<>();
		ArrayList<Boolean> butData = new ArrayList<>();
		data.add(character.getLevel() + " " + character.getClassName());
		data.add(character.getBackgroundName());
		data.add(character.getName());
		data.add(character.getRaceName());
		data.add(character.getAlignment().toString());
		data.add(Integer.valueOf(character.getProBonus()).toString());
		data.add(Integer.valueOf(character.getArmorClass()).toString());
		data.add(Integer.valueOf(character.getInitative()).toString());
		data.add(Integer.valueOf(character.getSpeed()).toString());
		data.add(Integer.valueOf(character.getHitPoints()).toString());
		data.add(Integer.valueOf(character.getHitPoints()).toString());
		data.add(character.getPersonalityTrait());
		data.add(character.getIdeal());
		data.add(character.getBond());
		data.add(character.getFlaw());
		data.add(character.proficienciesString() + "\n\n" + character.languagesString());
		
		int [] stats = character.getAbilityScores();
		
		for (int stat: stats) {
			data.add(Integer.valueOf(stat).toString());
		}
		
		stats = character.getAbilityModifers();
		for (int stat: stats) {
			data.add(Integer.valueOf(stat).toString());
		}
		
		stats = character.getSavingThrowScores();
		for (int stat: stats) {
			data.add(Integer.valueOf(stat).toString());
		}
		
		stats = character.getSkillScores();
		for (int stat: stats) {
			data.add(Integer.valueOf(stat).toString());
		}
		
		boolean [] button = character.getSavingProList();
		
		for (boolean val: button) {
			butData.add(Boolean.valueOf(val));
		}
		
		button = character.getSkillProList();
		for (boolean val: button) {
			butData.add(Boolean.valueOf(val));
		}
		
		Boolean [] buttonData = butData.toArray(new Boolean[0]);
		String [] charaData = data.toArray(new String [0]);
		String filePath = "C:\\Users\\Ash\\Desktop\\TestSheet.pdf";
		
		if (IOUtils.createCharacterSheet(charaData, buttonData, filePath)) 
		{
			System.out.println("Sheet created.");
		}
		
		System.out.println(character.proficienciesString());
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
