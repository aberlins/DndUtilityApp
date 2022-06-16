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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook; 
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  

public class IOUtils {
	
	public static String getAttributeFolder(String attributeName, String fileAttributeList) 
			throws FileNotFoundException, IOException 
	{
		try (Scanner fileStream = new Scanner(new File(fileAttributeList)))
		{
			while (fileStream.hasNextLine()) 
			{
				String [] line = fileStream.nextLine().split("=");
				if (attributeName.equals(line[0]))
					return line[1];
			}
		}
		return null;
	}
	
	public static long getLineCount(String fileName) throws IOException
	{
		try (LineNumberReader lineReader = new LineNumberReader(new FileReader(fileName)))
		{
			while ((lineReader.readLine()) != null);
	        	return lineReader.getLineNumber();
		}
		catch (Exception e) {
			return -1;
		}
	}
	
	public static int getEntryCount(String fileName) 
	{
		String entries;
		return -1;
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
		ArrayList<String> rowList = new ArrayList<>();
		Workbook book = null;
		
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
		
		Sheet sheet = (Sheet) book.getSheetAt(0);
		Row row = sheet.getRow(rowIndex);
		Iterator<Cell> cellIterator = row.cellIterator();
		
		while (cellIterator.hasNext()) 
		{
			Cell entry = cellIterator.next();
			if (entry != null)
				rowList.add(entry.getStringCellValue());
		}
		
		return rowList.toArray(new String[0]);

	}
	
	public static String [] getCol(int colIndex, String fileName) 
	{
		ArrayList<String> colList = new ArrayList<>();
		Workbook book = null;
		
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
		
		Sheet sheet = (Sheet) book.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		
		while (rowIterator.hasNext()) 
		{
			Cell entry = rowIterator.next().getCell(colIndex);
			if (entry != null)
				colList.add(entry.getStringCellValue());
		}
		
		return colList.toArray(new String[0]);
	}
	
}