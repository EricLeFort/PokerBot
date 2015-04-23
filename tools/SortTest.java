package tools;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.ArrayList;

public class SortTest {

	@Test
	public void testSortArrayListByFirstValue(){
		ArrayList<int[]> expected1 = null;
		ArrayList<int[]> expected2 = new ArrayList<int[]>();
		expected2.add(new int[]{0,6,3,6});
		expected2.add(new int[]{2,6,3,6});
		expected2.add(new int[]{4,6,3,6});
		expected2.add(new int[]{5,6,3,6});
		ArrayList<int[]> expected3 = new ArrayList<int[]>();
		expected3.add(new int[]{0,2});
		expected3.add(new int[]{0,0,0,5});
		expected3.add(new int[]{1,2,3});
		
		ArrayList<int[]> input1 = null;
		ArrayList<int[]> input2 = new ArrayList<int[]>();
		input2.add(new int[]{2,6,3,6});
		input2.add(new int[]{5,6,3,6});
		input2.add(null);
		input2.add(new int[]{4,6,3,6});
		input2.add(new int[]{0,6,3,6});
		ArrayList<int[]> input3 = new ArrayList<int[]>();
		input3.add(null);
		input3.add(new int[]{1,2,3});
		input3.add(new int[]{0,2});
		input3.add(new int[]{0,0,0,5});
		input3.add(null);
		
		ArrayList<int[]> actual1 = Sort.sortIntArrayListByFirstValue(input1);
		ArrayList<int[]> actual2 = Sort.sortIntArrayListByFirstValue(input2);
		ArrayList<int[]> actual3 = Sort.sortIntArrayListByFirstValue(input3);
		
		assertEquals(expected1, actual1);
		for(int i = 0; i < 4; i++){
			assertArrayEquals(expected2.get(i), actual2.get(i));
		}
		for(int i = 0; i < 3; i++){
			assertArrayEquals(expected3.get(i), actual3.get(i));
		}
		
	}

}
