package sort.benchmark;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import sort.Sorter;

public final class Benchmark {

	public static void verboseTest(Sorter sorter, ArrayGenerator generator,
			int iterations) {
		for (int i = 0; i < iterations; ++i) {
			System.out.println("Iteration " + i);
			System.out.println("----------------");
			int[] array = generator.generateArray();
			System.out.println("Before sorting:\n" + Arrays.toString(array));
			System.out.println("Sorting...");
			sorter.sort(array);
			System.out.println("After sorting:\n" + Arrays.toString(array));
			boolean success = true;
			int j = 1;
			while (j < array.length) {
				if (array[j] < array[j - 1]) {
					success = false;
					break;
				}
				++j;
			}
			if (success) {
				System.out.println("Sort succeeded.");
			} else {
				System.out.println("Sort failed at position " + (j - 1) + ".");
			}
			System.out.println();
		}
	}

	public static CorrectnessResult correctnessTest(Sorter sorter,
			ArrayGenerator generator, int iterations) {
		// edge cases are tested first

		Random random = new Random();

		// empty list
		int[] unsortedArray = new int[0];
		int[] sortedArray = new int[0];
		sorter.sort(sortedArray);

		// singleton list
		for (int i = 0; i < iterations; ++i) {
			int t = random.nextInt();
			unsortedArray = new int[] { t };
			sortedArray = new int[] { t };
			sorter.sort(sortedArray);
			if (sortedArray[0] != t) {
				new CorrectnessResult(unsortedArray, sortedArray, 0);
			}
		}

		// homogenous lists
		for (int i = 0; i < iterations; ++i) {
			unsortedArray = new int[random.nextInt(500) + 500];
			sortedArray = new int[unsortedArray.length];
			int t = random.nextInt();
			for (int j = 0; j < unsortedArray.length; ++j) {
				unsortedArray[j] = t;
				sortedArray[j] = t;
			}
			sorter.sort(sortedArray);
			for (int j = 0; j < sortedArray.length; ++j) {
				if (sortedArray[j] != t) {
					new CorrectnessResult(unsortedArray, sortedArray, j);
				}
			}
		}

		// sorted lists
		for (int i = 0; i < iterations; ++i) {
			unsortedArray = new int[random.nextInt(500) + 500];
			sortedArray = new int[unsortedArray.length];
			int t = random.nextInt(Integer.MAX_VALUE) - 1001;
			for (int j = 0; j < unsortedArray.length; ++j) {
				unsortedArray[j] = j + t;
				sortedArray[j] = j + t;
			}
			sorter.sort(sortedArray);
			for (int j = 0; j < sortedArray.length; ++j) {
				if (sortedArray[j] != j + t) {
					return new CorrectnessResult(unsortedArray, sortedArray, j);
				}
			}
		}

		// reverse-sorted lists
		for (int i = 0; i < iterations; ++i) {
			unsortedArray = new int[random.nextInt(500) + 500];
			sortedArray = new int[unsortedArray.length];
			int t = -random.nextInt(Integer.MAX_VALUE) + 1001;
			for (int j = 0; j < unsortedArray.length; ++j) {
				unsortedArray[j] = t - j;
				sortedArray[j] = t - j;
			}
			sorter.sort(sortedArray);
			for (int j = 0; j < sortedArray.length; ++j) {
				if (sortedArray[sortedArray.length - j - 1] != t - j) {
					return new CorrectnessResult(unsortedArray, sortedArray, j);
				}
			}
		}

		// random lists
		for (int i = 0; i < iterations; ++i) {
			unsortedArray = generator.generateArray();
			sortedArray = new int[unsortedArray.length];
			System.arraycopy(unsortedArray, 0, sortedArray, 0,
					unsortedArray.length);
			sorter.sort(sortedArray);
			int index = Benchmark.isSorted(unsortedArray, sortedArray);
			if (index != -1) {
				return new CorrectnessResult(unsortedArray, sortedArray, index);
			}
		}

		// random subsets of lists
		for (int i = 0; i < iterations; ++i) {
			unsortedArray = generator.generateArray();
			sortedArray = new int[unsortedArray.length];
			System.arraycopy(unsortedArray, 0, sortedArray, 0,
					unsortedArray.length);
			int start = random.nextInt(unsortedArray.length);
			int end = random.nextInt(unsortedArray.length - start) + start;
			sorter.sort(sortedArray, start, end);
			int index = Benchmark.isSorted(unsortedArray, sortedArray, start,
					end);
			if (index != -1) {
				return new CorrectnessResult(unsortedArray, sortedArray, index);
			}
		}

		return new CorrectnessResult(null, null, 0);
	}

	public static PerformanceResult performanceTest(Sorter sorter,
			ArrayGenerator generator, int iterations, int warmupIterations) {

		double[] times = new double[iterations];
		long listLength = 0L;
		for (int i = -warmupIterations; i < iterations; ++i) {
			int[] array = generator.generateArray();
			long startTime = System.nanoTime();
			sorter.sort(array);
			long endTime = System.nanoTime();
			if (i >= 0) {
				listLength += array.length;
				times[i] = (double) (endTime - startTime);
			}
		}

		listLength /= iterations;

		double mean = 0.0;
		for (int i = 0; i < times.length; ++i) {
			mean += times[i] / times.length;
		}

		double deviation = 0.0;
		for (int i = 0; i < times.length; ++i) {
			deviation += (mean - times[i]) / times.length * (mean - times[i]);
		}
		deviation = Math.sqrt(deviation);

		return new PerformanceResult((int) listLength, mean, deviation);
	}

	public static int isSorted(int[] unsorted, int[] sorted) {
		return Benchmark.isSorted(unsorted, sorted, 0, unsorted.length);
	}

	public static int isSorted(int[] unsorted, int[] sorted, int start, int end) {
		if (unsorted.length != sorted.length) {
			throw new IllegalArgumentException(
					"Arrays must have the same length.");
		}
		if (end < start) {
			throw new IllegalArgumentException(
					"End must come after start.");
		}
		if (start < 0) {
			throw new IllegalArgumentException(
					"Start must be non-negative.");
		}
		if (end > unsorted.length) {
			throw new IllegalArgumentException(
					"End must not be larger than the length of the array.");
		}
		HashSet<Integer> indices = new HashSet<>();
		for (int i = 0; i < unsorted.length; ++i) {
			if (i >= start && i < end) {
				boolean found = false;
				for (int j = start; j < end; ++j) {
					if (unsorted[j] == sorted[i] && !indices.contains(j)) {
						found = true;
						indices.add(j);
						break;
					}
				}
				if (!found) {
					return i;
				}
				if (i > start && sorted[i] < sorted[i - 1]) {
					return i;
				}
			} else {
				if (unsorted[i] != sorted[i]) {
					return i;
				}
			}
		}
		return -1;
	}

	public static class PerformanceResult {

		private PerformanceResult(int listLength, double mean, double deviation) {
			this.listLength = listLength;
			this.mean = mean;
			this.deviation = deviation;
		}

		@Override
		public String toString() {
			return listLength + "\t" + mean + "\t" + deviation;
		}

		public final int listLength;
		public final double mean;
		public final double deviation;

	}

	public static class CorrectnessResult {

		private CorrectnessResult(int[] unsortedArray, int[] sortedArray,
				int position) {
			this.unsortedArray = unsortedArray;
			this.sortedArray = sortedArray;
			this.position = position;
		}

		@Override
		public String toString() {
			if (!this.succeeded()) {
				return "          unsorted array: "
						+ Arrays.toString(this.unsortedArray)
						+ "\nincorrectly sorted array: "
						+ Arrays.toString(this.sortedArray)
						+ "\nerror at position " + position;
			}
			else {
				return "succeeded";
			}
		}

		public boolean succeeded() {
			return unsortedArray == null;
		}

		public final int[] unsortedArray;
		public final int[] sortedArray;
		public final int position;

	}

}
