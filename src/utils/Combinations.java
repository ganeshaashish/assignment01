package utils;

import java.util.ArrayList;
import java.util.List;
 
public class Combinations 
{ 
	//*** Possible combination code *************************************
	static public List<List<String>> possibleCombinations(List<String> elements) 
	{
		List<List<String>> results = new ArrayList<List<String>>();
		results.add(new ArrayList<String>());
		for(String element : elements)
		{
			List<List<String>> tempResults = new ArrayList<List<String>>();
			for (List<String> prevResult : results) 
			{
				List<String> newResult = new ArrayList<String>(prevResult);
				newResult.add(element);
				tempResults.add(newResult);
			}
			results.addAll(tempResults);
		}
		return results;
	}
}
