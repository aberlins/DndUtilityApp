import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook; 
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  

public class excelTest {

	public static void main(String[] args)   
	{  
		String [] row = getRow(0, "Ideals.xlsx");
		String [] entry = getCol(2, "Ideals.xlsx");
		for (String e: row) {
			System.out.println(e);
		}
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
			rowList.add(cellIterator.next().getStringCellValue());
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
			colList.add(rowIterator.next().getCell(colIndex).getStringCellValue());
		}
		
		return colList.toArray(new String[0]);
	}

	
	
}
