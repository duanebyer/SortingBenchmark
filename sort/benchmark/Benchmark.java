package sort.benchmark;

import java.util.Arrays;

import sort.Sorter;

public final class Benchmark {
	
	public static void verboseTest(
			Sorter sorter,
			ArrayGenerator generator,
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
			}
			else {
				System.out.println("Sort failed at position " + (j - 1) + ".");
			}
			System.out.println();
		}
	}
	
	public static Result test(
			Sorter sorter,
			ArrayGenerator generator,
			int iterations,
			int warmupIterations) {
		
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
		
		return new Result((int) listLength, mean, deviation);
	}
	
	public static class Result {
		
		public Result(int listLength, double mean, double deviation) {
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
	
}
