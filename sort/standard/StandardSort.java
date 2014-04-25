package sort.standard;

import java.util.Arrays;

import sort.Sorter;

public final class StandardSort implements Sorter {
	
	private StandardSort() {
		
	}
	
	public static final StandardSort Instance = new StandardSort();
	
	@Override
	public void sort(int[] array) {
		Arrays.sort(array);
	}

	@Override
	public void sort(int[] array, int start, int end) {
		Arrays.sort(array, start, end);
	}
	
}
