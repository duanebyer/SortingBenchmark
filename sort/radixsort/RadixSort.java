package sort.radixsort;

import sort.Sorter;

public final class RadixSort implements Sorter {

	private RadixSort() {

	}
	
	// the singleton pattern is used by the Benchmark class
	public static final RadixSort Instance = new RadixSort();

	// this is the method used to sort an array
	@Override
	public void sort(int[] array) {
		this.sort(array, 0, array.length);
	}

	@Override
	public void sort(int[] array, int start, int end) {
		this.sort(array, start, end, Integer.SIZE - 1);
	}

	private void sort(
			int[] array,		// the array to be sorted
			int start, int end,	// the subset of the array that will be sorted,
						//   from start (inclusive) to end (exclusive)
			int radix) {		// the index of the bit that the array will be sorted by
		// the index of the divide between the left bin and the right bin
		int divide = start;
		// for every element of the subset of the array...
		for (int i = start; i < end; ++i) {
			// if the bit at the radix position is zero or the sign bit is one...
			// (note that ^ is the xor operator, which is true when only one of its
			// arguments is true)
			if (!(getBit(array[i], radix) ^ (radix == Integer.SIZE - 1))) {
				// put the value into the left bucket
				int swapVal = array[divide];
				array[divide] = array[i];
				array[i] = swapVal;
				// because the left bucket is now larger, move the
				// divide over one
				++divide;
			}
		}
		// if the radix is zero, or the array's length is zero...
		if (radix != 0 && start != end) {
			// recursively sort the two bins with a radix sort
			this.sort(array, start, divide, radix - 1);
			this.sort(array, divide, end, radix - 1);
		}
	}

	private boolean getBit(int num, int bit) {
		// use bit-shifting to get the nth bit
		return ((num >> bit) & 1) != 0;
	}

}
