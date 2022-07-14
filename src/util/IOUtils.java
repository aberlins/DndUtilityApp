package util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook; 
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  



public class IOUtils {
	
	public static final String fileExtensionBuild = "C:/Users/Public/Documents/DndRandApp-Build-1-7-14-2022/lib/";
	public static final String toolTypeFile = "items/ToolTypes.xlsx";
	public static final String weaponTypeFile = "items/WeaponTypes.xlsx";
	public static final String weaponStatsFile = "items/WeaponStats.xlsx";
	public static final String armorTypeFile = "items/ArmorTypes.xlsx";
	public static final String armorStatsFile = "items/ArmorStats.xlsx";
	public static final String languageFile = "general/Languages.xlsx";
	public static final String [] pdfStringFieldNames = 
		{"ClassLevel", "Background", "CharacterName", "Race ", "Alignment",
			"ProfBonus", "AC", "Initiative", "Speed", "HPMax", "HPCurrent", "HD", "HDTotal", "Passive",
			"PersonalityTraits ", "Ideals", "Bonds", "Flaws",
			"ProficienciesLang", "Equipment",
			"STR", "DEX", "CON", "INT", "WIS", "CHA",
			"STRmod",  "DEXmod ", "CONmod", "INTmod",  "WISmod",  "CHamod",
			"ST Strength", "ST Dexterity", "ST Constitution", "ST Intelligence", "ST Wisdom", "ST Charisma",
			"Acrobatics", "Animal", "Arcana", "Athletics", "Deception ", "History ", "Insight", "Intimidation", 
			"Investigation ", "Medicine", "Nature", "Perception ", "Performance", "Persuasion", "Religion", 
			"SleightofHand", "Stealth ", "Survival",
			"Wpn Name", "Wpn1 AtkBonus", "Wpn1 Damage", "Wpn Name 2", "Wpn2 AtkBonus ", "Wpn2 Damage ",
			"Wpn Name 3", "Wpn3 AtkBonus  ", "Wpn3 Damage ",
			"CP", "SP", "EP", "GP", "PP", "Features and Traits", "AttacksSpellcasting",
			"Spellcasting Class 2", "SpellcastingAbility 2", "SpellSaveDC  2", "SpellAtkBonus 2"};
	public static final String [] pdfButtonFieldNames = 
		{"Check Box 11", "Check Box 18", "Check Box 19", "Check Box 20", "Check Box 21", "Check Box 22",
				"Check Box 23", "Check Box 24", "Check Box 25", "Check Box 26", "Check Box 27", "Check Box 28", 
				"Check Box 29", "Check Box 30", "Check Box 31","Check Box 32", "Check Box 33", "Check Box 34",
				"Check Box 35", "Check Box 36", "Check Box 37", "Check Box 38", "Check Box 39", "Check Box 40"};
	public static final String [][] pdfStringSpellFields = {
			//Level 0 Spell TextBoxes
			{"Spells 1014", "Spells 1016", "Spells 1017", "Spells 1018", "Spells 1019", "Spells 1020", "Spells 1021", "Spells 1022"},
			//Level 1 Spell TextBoxes
			{"Spells 1015", "Spells 1023", "Spells 1024", "Spells 1025", "Spells 1026", "Spells 1027", "Spells 1028", 
			"Spells 1029", "Spells 1030", "Spells 1031", "Spells 1032", "Spells 1033"},
			//Level 2 Spell TextBoxes
			{"Spells 1046", "Spells 1034", "Spells 1035", "Spells 1036", "Spells 1037", "Spells 1038", "Spells 1039",
			"Spells 1040", "Spells 1041", "Spells 1042", "Spells 1043", "Spells 1044", "Spells 1045"},
			//Level 3 Spell TextBoxes
			{"Spells 1048", "Spells 1047", "Spells 1049", "Spells 1050", "Spells 1051", "Spells 1052", "Spells 1053", "Spells 1054",
			"Spells 1055", "Spells 1056", "Spells 1057", "Spells 1058", "Spells 1059"},
			//Level 4 Spell TextBoxes
			{"Spells 1061", "Spells 1060", "Spells 1062", "Spells 1063", "Spells 1064", "Spells 1065", "Spells 1066", "Spells 1067",
			"Spells 1068", "Spells 1069", "Spells 1070", "Spells 1071", "Spells 1072"},
			//Level 5 Spell TextBoxes
			{"Spells 1074", "Spells 1073", "Spells 1075", "Spells 1076", "Spells 1077", "Spells 1078", "Spells 1079", "Spells 1080",
			"Spells 1081"},
			//Level 6 Spell TextBoxes
			{"Spells 1083", "Spells 1082", "Spells 1084", "Spells 1085", "Spells 1086", "Spells 1087", "Spells 1088", "Spells 1089", 
			"Spells 1090"},
			//Level 7 Spell TextBoxes
			{"Spells 1092", "Spells 1091", "Spells 1093", "Spells 1094", "Spells 1095", "Spells 1096", "Spells 1097", "Spells 1098",
			"Spells 1099"},
			//Level 8 Spell TextBoxes
			{"Spells 10101", "Spells 10100", "Spells 10102", "Spells 10103", "Spells 10104", "Spells 10105", "Spells 10106"},
			//Level 9 Spell TextBoxes
			{"Spells 10108", "Spells 10107", "Spells 10109", "Spells 101010", "Spells 101011", "Spells 101012", "Spells 101013"}
			};
	private static final String [] pdfStringSpellSlotFields = {"SlotsTotal 19", "SlotsTotal 20", "SlotsTotal 21",
			"SlotsTotal 22", "SlotsTotal 23", "SlotsTotal 24", "SlotsTotal 25", "SlotsTotal 26", "SlotsTotal 27"};
	private static final String [][] pdfButtonSpellFields = {
			//Level 1 Prepared Spell Buttons
			{"Check Box 251", "Check Box 309", "Check Box 3010", "Check Box 3011", "Check Box 3012",
			"Check Box 3013", "Check Box 3014", "Check Box 3015", "Check Box 3016", "Check Box 3017", 
			"Check Box 3018", "Check Box 3019"},
			//Level 2 Prepared Spell Buttons
			{"Check Box 313", "Check Box 310", "Check Box 3020", "Check Box 3021", "Check Box 3022", 
			"Check Box 3023", "Check Box 3024", "Check Box 3025", "Check Box 3026", "Check Box 3027", 
			"Check Box 3028", "Check Box 3029", "Check Box 3030"},
			//Level 3 Prepared Spell Buttons
			{"Check Box 315", "Check Box 314", "Check Box 3031", "Check Box 3032", "Check Box 3033", "Check Box 3034", "Check Box 3035",
			"Check Box 3036", "Check Box 3037", "Check Box 3038", "Check Box 3039", "Check Box 3040", "Check Box 3041"},
			//Level 4 Prepared Spell Buttons
			{"Check Box 317", "Check Box 316", "Check Box 3042", "Check Box 3043", "Check Box 3044", "Check Box 3045", "Check Box 3046", 
			"Check Box 3047", "Check Box 3048", "Check Box 3049", "Check Box 3050", "Check Box 3051", "Check Box 3052"},
			//Level 5 Prepared Spell Buttons
			{"Check Box 319", "Check Box 318", "Check Box 3053", "Check Box 3054", "Check Box 3055", "Check Box 3056", "Check Box 3057",
			"Check Box 3058", "Check Box 3059"},
			//Level 6 Prepared Spell Buttons
			{"Check Box 321", "Check Box 320", "Check Box 3060", "Check Box 3061", "Check Box 3062", "Check Box 3063", "Check Box 3064",
			"Check Box 3065", "Check Box 3066"},
			//Level 7 Prepared Spell Buttons
			{"Check Box 323", "Check Box 322", "Check Box 3067", "Check Box 3068", "Check Box 3069", "Check Box 3070", "Check Box 3071", 
			"Check Box 3072", "Check Box 3073"},
			//Level 8 Prepared Spell Buttons
			{"Check Box 325", "Check Box 324", "Check Box 3074", "Check Box 3075", "Check Box 3076", "Check Box 3077", "Check Box 3078"},
			//Level 9 Prepared Spell Buttons
			{"Check Box 327", "Check Box 326", "Check Box 3079", "Check Box 3080", "Check Box 3081", "Check Box 3082", "Check Box 3083"}
			};
	
	public static String getAttributeFolder(String attributeName, String fileAttributeList) 
	{
		//
		//fileAttributeList = fileExtensionBuild + fileAttributeList;
		
		try (Scanner fileStream = new Scanner(new File(fileAttributeList)))
		{
			while (fileStream.hasNextLine()) 
			{
				String [] line = fileStream.nextLine().split("=");
				if (attributeName.equals(line[0]))
					return line[1];
			}
		} catch (FileNotFoundException e) {
				System.out.println("File, " + fileAttributeList + ", not found.");
		} catch (IOException e) {
			System.out.println("Error reading the file: " + fileAttributeList);
		}
		
		return null;
	}
	
	public static long getLineCount(String fileName)
	{
		//
		//fileName = fileExtensionBuild + fileName;
		
		try (LineNumberReader lineReader = new LineNumberReader(new FileReader(fileName)))
		{
			while ((lineReader.readLine()) != null);
	        	return lineReader.getLineNumber();
		}
		catch (IOException e) {
			System.out.println("Error reading the file: " + fileName);
			return -1;
		}
	}
	
	public static String [] getContentsFromtxt(String fileName) 
	{
		String traitArray [] = null;
		//
		//fileName = fileExtensionBuild + fileName;
		
		try (Scanner fileStream = new Scanner(new File(fileName)))
		{
			//Array meant to hold all values is allocated the proper memory.
			//Counter variable is used to keep track of index.
			long lineCount = getLineCount(fileName);
			traitArray = new String[(int)lineCount];
			int counter = 0;
					
			//Fill Array
			while (fileStream.hasNextLine()) 
			{
				traitArray[counter++] = fileStream.nextLine();
			}
		
		} 
		catch (FileNotFoundException e) {
			System.out.println("File, " + fileName + ", not found.");
		}
		
		return traitArray;
	}
	
	public static String getStartingPoint (Scanner fileStream, String attName) 
	{
		if (fileStream.hasNextLine()) 
		{
			String [] line = fileStream.nextLine().split("=");
			for (String beginVar: line) 
			{
				String [] beginLoc = beginVar.split("/");
				if (attName.equals(beginLoc[0]))
					return beginLoc[1];
			}
		}
		return null;
	}
	
	public static String [] getRow(int rowIndex, String fileName) 
	{
		Sheet sheet = getSheet(fileName);
		
		if (sheet != null) {
			return getRowContents(sheet, rowIndex);
		}
		
		return null;
		
	}
		
	public static String [] getCol(int colIndex, String fileName) 
	{
		Sheet sheet = getSheet(fileName);
		
		if (sheet != null) {
			return getColContents(sheet, colIndex);
		}
		
		return null;
	}
	
	public static String[][] getRowCol (int endingIndex, String fileName) 
	{
		String rowCol [][] = new String [endingIndex][];
		int counter = 0;
		Sheet sheet = getSheet(fileName);
		
		if (sheet != null) {
			for (int i = 0; i < endingIndex; i++) 
			{
				rowCol[counter++] = getColContents(sheet, counter);
			}
		}
		
		return rowCol;
		
	}
	
	public static String [] getCol(String attributeName, String fileName, boolean isSorted) 
	{
		ArrayList<String> list = new ArrayList<>();
		
		Sheet sheet = getSheet(fileName);
		if (sheet != null) 
		{
			String [] rowContents = getRowContents(sheet, 0);
			int index = isSorted ? getIndexBinarySearch(attributeName, rowContents) :
					getIndex(attributeName, rowContents);
			return getColContents(sheet, index);
		}
		
		return list.toArray(new String[0]);
		
	}
	
	public static int getIndex(String attributeName, String filePath) {
		
		String attributeList [] = getRow(0, filePath);
		
		for (int i = 0; i < attributeList.length; i++) 
		{
			if (attributeList[i].equalsIgnoreCase(attributeName)) {
				return i;
			}
		}
		
		return -1;
	}
	
	public static int getIndexBinarySearch (String attributeName, String filePath) 
	{
		String attributeList [] = getRow(0, filePath);
		int low = 0, high = attributeList.length - 1;
		
		while (low <= high) 
		{
			int guess = low + ((high - low) / 2);
			String stringGuess = attributeList[guess];
			
			
			if (stringGuess.compareTo(attributeName) < 0) 
				low = guess + 1;
			else if (stringGuess.compareTo(attributeName) > 0)
				high = guess - 1;
			else
				return guess;
		}
		
		return -1;
		
	}
	
	private static int getIndex(String attributeName, String attributeList[]) 
	{
		for (int i = 0; i < attributeList.length; i++) 
		{
			if (attributeList[i].equalsIgnoreCase(attributeName)) {
				return i;
			}
		}
		
		return -1;
	}
	
	private static int getIndexBinarySearch(String attributeName, String attributeList[]) 
	{
		int low = 0, high = attributeList.length - 1;
		
		while (low <= high) 
		{
			int guess = low + ((high - low) / 2);
			String stringGuess = attributeList[guess];
			
			
			if (stringGuess.compareTo(attributeName) < 0) 
				low = guess + 1;
			else if (stringGuess.compareTo(attributeName) > 0)
				high = guess - 1;
			else
				return guess;
		}
		
		return -1;
	}
	
	public static boolean createCharacterSheet(String [] stringContents, Boolean [] buttonContents, 
			ArrayList<String> [] spells, int [] spellSlots, boolean isPreparedCaster, String fileName) 
	{
		//String pdfFilePath = fileExtensionBuild + "general/5E_CharacterSheet_Fillable.pdf";
		String pdfFilePath = "general/5E_CharacterSheet_Fillable.pdf";
		File pdfFile = new File(pdfFilePath);
		try (PDDocument doc = PDDocument.load(pdfFile))
		{
			PDDocumentCatalog docCatalog = doc.getDocumentCatalog();
			PDAcroForm acroForm = docCatalog.getAcroForm();
			
			for (int i = 0; i < pdfStringFieldNames.length; i++) 
			{
				PDField field = acroForm.getField(pdfStringFieldNames[i]);
				if(field != null)
				{
					field.setValue(stringContents[i]); 
				}
				else {
					return false;
				}
			}
			
			for (int i = 0; i < pdfButtonFieldNames.length; i++) {
				PDCheckBox btnField = (PDCheckBox) acroForm.getField(pdfButtonFieldNames[i]);
				if (btnField != null && buttonContents[i].booleanValue()) 
				{
					btnField.check();
				}
			}
			
			if (spells != null && spellSlots != null) 
			{
				int spellLevelLength = spells.length;
				for (int i = 0; i < pdfStringSpellFields.length; i++) {
					
					if (i >= spellLevelLength) 
						break;
					
					
					int listOfSpells = spells[i].size();
					for (int j = 0; j < pdfStringSpellFields[i].length; j++) 
					{
						if (j >= listOfSpells)
							break;
						
						PDField field = acroForm.getField(pdfStringSpellFields[i][j]);
						if(field != null)
						{
							field.setValue(spells[i].get(j));
							
							if (i != 0 && isPreparedCaster) 
							{
								PDCheckBox btnField = 
										(PDCheckBox) acroForm.getField(pdfButtonSpellFields[i -1][j]);
								if (btnField != null) 
									btnField.check();
							}
						}
						else {
							return false;
						}
					}
					
				}
				int slotLength = spellSlots.length;
				for (int i = 0; i < pdfStringSpellSlotFields.length; i++) 
				{
					if (i + 1 >= slotLength) 
						break;
					
					PDField field = acroForm.getField(pdfStringSpellSlotFields[i]);
					if(field != null)
					{
						field.setValue(Integer.valueOf(spellSlots[i + 1]).toString()); 
					}
					else {
						return false;
					}
				}
			}
			
			doc.save(fileName);
			return true;
			
		} catch (FileNotFoundException e) {
			System.out.println("File, " + fileName + ", not found.");
		} catch (IOException e) {
			System.out.println("Error reading the file: " + fileName);
		}
		
		return false;
	}
	
	private static Sheet getSheet(String fileName)
	{
		//
		//fileName = fileExtensionBuild + fileName;
		Workbook book = null;
		Sheet sheet = null;
		
		try (FileInputStream stream = new FileInputStream(fileName)) 
		{
			book = new XSSFWorkbook(stream);
		} catch (FileNotFoundException e) {
			System.out.println("File, " + fileName + ", not found.");
		} catch (IOException e) {
			System.out.println("Error reading the file: " + fileName);
		} finally {
			if (book == null) 
				return null;
		}
		
		sheet = (Sheet) book.getSheetAt(0);
		
		return sheet;
	}
	
	private static String [] getRowContents(Sheet sheet, int index) 
	{
		if (index != -1) {
			ArrayList<String> rowList = new ArrayList<>();
			Row row = sheet.getRow(index);
			Iterator<Cell> cellIterator = row.cellIterator();
	
			while (cellIterator.hasNext()) 
			{
				Cell entry = cellIterator.next();
				if (entry != null) {
					entry.setCellType(1);
					rowList.add(entry.getStringCellValue());
				}
			}
	
			return rowList.toArray(new String[0]);
		}
		else
			return null;
	}
	
	private static String [] getColContents(Sheet sheet, int index) 
	{
		if (index != -1) {
			ArrayList<String> colList = new ArrayList<>();
			Iterator<Row> rowIterator = sheet.iterator();
		
			while (rowIterator.hasNext()) 
			{
				Cell entry = rowIterator.next().getCell(index);
				if (entry != null) {
					entry.setCellType(1);
					colList.add(entry.getStringCellValue());
				}
			}
		
			return colList.toArray(new String[0]);
		}
		else
			return null;
	}
}
