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
	
	public static final String toolTypeFile = "items/ToolTypes.xlsx";
	public static final String weaponTypeFile = "items/WeaponTypes.xlsx";
	public static final String weaponStatsFile = "items/WeaponStats.xlsx";
	public static final String armorTypeFile = "items/ArmorTypes.xlsx";
	public static final String armorStatsFile = "items/ArmorStats.xlsx";
	public static final String languageFile = "general/Languages.xlsx";
	public static final String [] pdfStringFieldNames = 
		{"ClassLevel", "Background", "CharacterName", "Race ", "Alignment",
			"ProfBonus", "AC", "Initiative", "Speed", "HPMax", "HPCurrent",
			"PersonalityTraits ", "Ideals", "Bonds", "Flaws",
			"ProficienciesLang",
			"STR", "DEX", "CON", "INT", "WIS", "CHA",
			"STRmod",  "DEXmod ", "CONmod", "INTmod",  "WISmod",  "CHamod",
			"ST Strength", "ST Dexterity", "ST Constitution", "ST Intelligence", "ST Wisdom", "ST Charisma",
			"Acrobatics", "Animal", "Arcana", "Athletics", "Deception ", "History ", "Insight", "Intimidation", 
			"Investigation ", "Medicine", "Nature", "Perception ", "Performance", "Persuasion", "Religion", 
			"SleightofHand", "Stealth ", "Survival"};
	public static final String [] pdfButtonFieldNames = 
		{"Check Box 11", "Check Box 18", "Check Box 19", "Check Box 20", "Check Box 21", "Check Box 22",
				"Check Box 23", "Check Box 24", "Check Box 25", "Check Box 26", "Check Box 27", "Check Box 28", 
				"Check Box 29", "Check Box 30", "Check Box 31","Check Box 32", "Check Box 33", "Check Box 34",
				"Check Box 35", "Check Box 36", "Check Box 37", "Check Box 38", "Check Box 39", "Check Box 40"};
	
	public static String getAttributeFolder(String attributeName, String fileAttributeList) 
	{
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
	
	public static boolean createCharacterSheet(String [] stringContents, Boolean [] buttonContents, String fileName) 
	{
		File pdfFile = new File("general/5E_CharacterSheet_Fillable.pdf");
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
				PDCheckBox checkBox = (PDCheckBox) acroForm.getField(pdfButtonFieldNames[i]);
				if (checkBox != null && buttonContents[i].booleanValue()) 
				{
					checkBox.check();
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
	
	private static String [] getColContents(Sheet sheet, int index) 
	{
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
}
