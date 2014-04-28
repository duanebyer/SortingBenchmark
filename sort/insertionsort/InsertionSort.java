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
		for (int i = start; i < end; ++i) {
			for (int j = i; j > start && array[j - 1] > array[j]; --j) {
				int swapVal = array[j];
				array[j] = array[j + 1];
				array[j + 1] = swapVal;
			}
		}
	}
	
}
