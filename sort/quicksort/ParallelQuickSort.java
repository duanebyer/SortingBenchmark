package sort.quicksort;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import sort.Sorter;
import sort.insertionsort.InsertionSort;

public final class ParallelQuickSort implements Sorter {
	
	private ParallelQuickSort() {
		
	}
	
	public final static ParallelQuickSort Instance = new ParallelQuickSort();

	@Override
	public void sort(int[] array) {
		this.sort(array, 0, array.length);
	}

	@Override
	public void sort(int[] array, int start, int end) {
		new ForkJoinPool().invoke(
				new RecursiveQuickSort(array, start, end));
	}
	
	private static final class RecursiveQuickSort extends RecursiveAction {

		public RecursiveQuickSort(int[] array, int start, int end) {
			this.array = array;
			this.start = start;
			this.end = end;
		}
		
		@Override
		protected void compute() {
			if (end - start <= 20) {
				InsertionSort.Instance.sort(array, start, end);
				return;
			}
			
			int pivotIndex = RecursiveQuickSort.random.nextInt(end - start) + start;
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
	        
	        RecursiveAction.invokeAll(
	        		new RecursiveQuickSort(array, start, divide - 1),
	        		new RecursiveQuickSort(array, divide, end));
		}
		
		private final int[] array;
		private final int start;
		private final int end;
		
		private static final Random random = new Random();
		
		private static final long serialVersionUID = 8434835413209639696L;
		
	}
	
}
