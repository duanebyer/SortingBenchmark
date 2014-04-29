package sort.radixsort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import sort.Sorter;
import sort.insertionsort.InsertionSort;

public final class ParallelRadixSort implements Sorter {

	private ParallelRadixSort() {

	}

	public final static ParallelRadixSort Instance = new ParallelRadixSort();

	@Override
	public void sort(int[] array) {
		this.sort(array, 0, array.length);
	}

	@Override
	public void sort(int[] array, int start, int end) {
		new ForkJoinPool().invoke(new RecursiveRadixSort(array, start, end,
				Integer.SIZE - 1));
	}

	private static final class RecursiveRadixSort extends RecursiveAction {

		public RecursiveRadixSort(int[] array, int start, int end, int radix) {
			this.array = array;
			this.start = start;
			this.end = end;
			this.radix = radix;
		}

		@Override
		protected void compute() {
			if (end - start <= 25) {
				InsertionSort.Instance.sort(array, start, end);
				return;
			}
			int divide = start;
			for (int i = start; i < end; ++i) {
				if (!(getBit(array[i], radix) ^ (radix == Integer.SIZE - 1))) {
					int swapVal = array[divide];
					array[divide] = array[i];
					array[i] = swapVal;
					++divide;
				}
			}
			if (radix != 0 && start != end) {
				RecursiveAction.invokeAll(
						new RecursiveRadixSort(array, start, divide, radix - 1),
						new RecursiveRadixSort(array, divide, end, radix - 1));
			}
		}

		private boolean getBit(int num, int bit) {
			return ((num >> bit) & 1) != 0;
		}

		private final int[] array;
		private final int start;
		private final int end;
		private final int radix;

		private static final long serialVersionUID = -4867911572322861905L;

	}

}
