package sort.insertionsort;

import sort.Sorter;

public class InsertionSort implements Sorter {
	
	private InsertionSort() {
		
	}
	
	public final static InsertionSort Instance = new InsertionSort();
	
	@Override
	public void sort(int[] array) {
		this.sort(array, 0, array.length);
	}
	
	@Override
	public void sort(int[] array, int start, int end) {
		for (int i = start + 1; i < end - start; ++i) {
			int x = array[i];
			int j = i;
			while (j > 0 && array[j - 1] > x) {
				array[j] = array[j - 1];
				--j;
			}
			array[j] = x;
		}
	}
	
}
