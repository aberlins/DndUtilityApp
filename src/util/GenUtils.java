package util;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GenUtils {
	
	public static String [] combineLists (String [] listOne, String [] listTwo, boolean noDup) 
	{
		
		if (noDup) {
			HashSet<String> finalList = new HashSet<>();
			for (String item: listOne) 
			{
				if (!(item.equalsIgnoreCase("None")))
					finalList.add(item);
			}
			
			for (String item: listTwo) 
			{
				if (!(item.equalsIgnoreCase("None"))) 
					finalList.add(item);
			}
			return finalList.toArray(new String [0]);
		}
		else {
			ArrayList<String> finalList = new ArrayList<>();
			for (String item: listOne) 
			{
				if (!(item.equalsIgnoreCase("None")))
					finalList.add(item);
			}
			
			for (String item: listTwo) 
			{
				if (!(item.equalsIgnoreCase("None"))) 
					finalList.add(item);
			}
			return finalList.toArray(new String [0]);
		}
				
	}
	
	
	
	public static ArrayList<String> addToList(String[] list, ArrayList<String> totalList) 
	{
		if (list != null) {
			for (String item: list) {
				totalList.add(item);
			}
		}
		return totalList;
	}

}
