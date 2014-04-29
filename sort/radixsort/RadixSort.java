package sort.radixsort;

import sort.Sorter;

public final class RadixSort implements Sorter {

	private RadixSort() {

	}

	public static final RadixSort Instance = new RadixSort();

	@Override
	public void sort(int[] array) {
		this.sort(array, 0, array.length);
	}

	@Override
	public void sort(int[] array, int start, int end) {
		this.sort(array, start, end, Integer.SIZE - 1);
	}

	private void sort(int[] array, int start, int end, int radix) {
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
			this.sort(array, start, divide, radix - 1);
			this.sort(array, divide, end, radix - 1);
		}
	}

	private boolean getBit(int num, int bit) {
		return ((num >> bit) & 1) != 0;
	}

}
