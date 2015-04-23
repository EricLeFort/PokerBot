package tools;
/**
 * @author Eric
 * @version 1
 */

import java.util.ArrayList;
import java.util.Collections;

public class Sort{

	/**
	 * Takes an ArrayList of type int[] and sorts it from low to high based on the value at index 0 of each entry. 
	 * Removes any null entries.
	 * @param list
	 * @return sortedList
	 */
	public static ArrayList<int[]> sortIntArrayListByFirstValue(ArrayList<int[]> list){
		ArrayList<Integer> values = new ArrayList<Integer>(), unsortedValues = new ArrayList<Integer>();
		ArrayList<int[]> sortedList = new ArrayList<int[]>();
		int index;
		
		if(list != null){
			for(int i = 0; i < list.size(); i++){
				if(list.get(i) == null){
					list.remove(i);
					i--;
				}else{
					values.add(list.get(i)[0]);
				}
			}
			unsortedValues.addAll(values);
			Collections.sort(values);

			for(int i = 0; i < values.size(); i++){
				index = unsortedValues.indexOf(values.get(i).intValue());
				unsortedValues.remove(index);
				sortedList.add(list.remove(index));
			}
			return sortedList;
		}else{
			return null;
		}
	}//sortArrayListByFirst(ArrayList<int[]> list)
	
}//Sort
