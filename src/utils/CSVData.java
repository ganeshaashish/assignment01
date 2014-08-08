package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVData 
{	
	//*** Reading a CSV file and get the map of entries ****************
	public Map<String, PresenterInfo> readCSV(File file)
	{
		Map<String, PresenterInfo> csv_data = new HashMap<String, PresenterInfo>();
		System.out.println("--------------- CSV FILE TEST DATA --------------");
		System.out.println("PresenterName\tHours Of Presentation\tPresenter Cost");
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null)
			{
				String[] splits = line.split(",");
				PresenterInfo pi = new PresenterInfo();
				pi.setPresenterName(splits[0].trim());
				pi.setPresntationTime(Integer.parseInt(splits[1].trim()));
				pi.setCostOfPresentation(Integer.parseInt(splits[2].trim()));
				
				csv_data.put(splits[0].trim(), pi);
				
				System.out.print(pi.getPresenterName());
				System.out.print("\t\t"+pi.getPresntationTime());
				System.out.print("\t\t\t$"+pi.getCostOfPresentation());
				System.out.println();
			}
			System.out.println("-----------------------------");
			br.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return csv_data;
	}
	
}
