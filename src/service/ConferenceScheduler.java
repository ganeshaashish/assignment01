package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import utils.CSVData;
import utils.Combinations;
import utils.PresenterInfo;
import utils.Slots;

public class ConferenceScheduler 
{
	public static void main(String[] args) 
	{
		int N = 8;
		System.out.print("Enter conference hours: ");
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		try 
		{
			N = Integer.parseInt(bf.readLine());
		} catch (NumberFormatException e) {
			N = 0;
			e.printStackTrace();
		} catch (IOException e) {
			N = 0;
			e.printStackTrace();
		}
		int[] slots = Slots.getSlots(N);
		for(int i: slots)
			System.out.println(i);
		File file = new File("E:/SampleCSV.csv");
		Map<String, PresenterInfo> pi_map = new CSVData().readCSV(file);
		List<String> result = ConferenceScheduler.maximize_presenter_scheduler(slots, N, pi_map);
		for(int i=0; i<result.size(); i++)
			System.out.println(result.get(i));
	}
	
	//*** Maximize the number of presenters *********************
	public static List<String> maximize_presenter_scheduler(int[] slots, int N, Map<String, PresenterInfo> pi_map)
	{
		List<String> resultMinimumCost = new ArrayList<String>();
		Comparator<PresenterInfo> byPresentationTime = new Comparator<PresenterInfo>() {
	        @Override
	        public int compare(PresenterInfo left, PresenterInfo right) {
	            return right.getPresntationTime() - left.getPresntationTime();
	        }
	    };
	    List<String> all_presenters = new ArrayList<String>();
		all_presenters.addAll(pi_map.keySet());
		List<List<String>> possCombi = Combinations.possibleCombinations(all_presenters);
		List<String> currentCombination = null;
		PresenterInfo pi = null;
		List<PresenterInfo> pList = null;
		int max_presenters = 0;
		List<List<PresenterInfo>> finalSelection = new ArrayList<List<PresenterInfo>>();
		outer: for(int i=0; i<possCombi.size(); i++)
		{
			currentCombination = possCombi.get(i);
			if(currentCombination.size()>=3)
			{
				int sum_of_hours = 0;
				boolean status = true;
				pList = new ArrayList<PresenterInfo>();
				
				for(int j=0; j<currentCombination.size(); j++)
				{
					String presenter = currentCombination.get(j);
					pi = pi_map.get(presenter);
					//System.out.println(pi.getPresntationTime()+"<="+slots[0]+" || "+slots[1]+" || "+slots[2]);
					if(pi.getPresntationTime()<=slots[0] || pi.getPresntationTime() <= slots[1] || pi.getPresntationTime() <= slots[2])
					{
						sum_of_hours += pi.getPresntationTime();
						if(sum_of_hours<=N)
						{
							pList.add(pi);
						}
					}
					else
					{
						status = false;
						continue outer;
					}
				}
				//System.out.println("status: "+status+" pList: "+pList);
				if(status){
					Collections.sort(pList, byPresentationTime);
					finalSelection.add(pList);
					if(max_presenters<pList.size())
						max_presenters = pList.size();
				}
			}
		}
		
		int min_cost = 2000;
		List<String> result = new ArrayList<String>();
		for(int i=0; i<finalSelection.size(); i++)
		{
			List<PresenterInfo> p = finalSelection.get(i);
			if(p.size() >= max_presenters)
			{
				int total_sum = 0;
				String presenters = "";
				for(int j=0; j<p.size(); j++)
				{
					total_sum += p.get(j).getCostOfPresentation();
					presenters += p.get(j).getPresenterName()+",";
				}
				presenters += "$"+total_sum;
				result.add(presenters);
				if(min_cost>total_sum)
					min_cost=total_sum;
			}
		}
		
		for(String res : result)
		{
			String[] r = res.split(",");
			int cost = Integer.parseInt(r[r.length-1].replace("$", "").trim());
			if(cost==min_cost)
			{
				if(!resultMinimumCost.contains(res))
					resultMinimumCost.add(res);
			}
		}
		
		List<List<String>> finalizedMinCostPresenters = new ArrayList<List<String>>();
		for(int i=0; i<resultMinimumCost.size(); i++)
		{
			String s = resultMinimumCost.get(i);
			String[] ss = s.split(",");
			List<String> temp = new ArrayList<String>();
			for(int j=0; j<ss.length-1; j++)
			{
				temp.add(ss[j]);
			}
			finalizedMinCostPresenters.add(temp);
		}
		
		if(resultMinimumCost.size()==0)
			resultMinimumCost.add("Not enough presenters.");
		
		return resultMinimumCost;
	}
}
