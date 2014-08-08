package utils;

public class Slots 
{
	public static int number_of_sessions = 3;
	public static int[] getSlots(int N)
	{
		int[] slots = new int[number_of_sessions];
		if(N>=3)
		{
			int slot_counter = 0;
			int adder = 1;
			for(int i=1; i<=N; i++)
			{
				slot_counter++;
				slots[slot_counter-1] = adder;
				//System.out.println("slot_counter: "+slot_counter+" value: "+slots[slot_counter-1]);
				if(slot_counter==number_of_sessions){
					slot_counter=0;
					adder++;
				}
			}
			//System.out.println("SLOTS: "+slots[0]+" "+slots[1]+" "+slots[2]);
		}
		else
		{
			slots = null;
			System.out.println("Conference must be atleast 3 hours.");
		}
		
		return slots;
	}
}
