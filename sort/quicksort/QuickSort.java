package sort.quicksort;

import java.util.Random;

import sort.Sorter;

public final class QuickSort implements Sorter {
	
	private QuickSort() {
		
	}
	
	public static final QuickSort Instance = new QuickSort();
	
	private static final Random random = new Random();
	
	@Override
	public void sort(int[] array) {
		this.sort(array, 0, array.length);
	}

	@Override
	public void sort(int[] array, int start, int end) {
		if (end - start <= 1) {
			return;
		}
		
		int pivotIndex = QuickSort.random.nextInt(end - start) + start;
		int partition = array[pivotIndex];
		
		int swapVal = partition;
		array[pivotIndex] = array[start];
		array[start] = swapVal;
		
        int divide = start + 1;
        for (int i = start + 1; i < end; ++i) {
            if (array[i] < partition) {
                swapVal = array[divide];
                array[divide] = array[i];
                array[i] = swapVal;
                ++divide;
            }
        }
        
        swapVal = partition;
        array[start] = array[divide - 1];
        array[divide - 1] = swapVal;
        
        this.sort(array, start, divide - 1);
        this.sort(array, divide, end);
	}
	
}
