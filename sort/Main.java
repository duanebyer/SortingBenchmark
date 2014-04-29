package sort;

import sort.benchmark.Benchmark;
import sort.benchmark.RandomArrayGenerator;
import sort.insertionsort.InsertionSort;
import sort.quicksort.HybridQuickSort;
import sort.quicksort.ParallelQuickSort;
import sort.quicksort.QuickSort;
import sort.radixsort.HybridRadixSort;
import sort.radixsort.ParallelRadixSort;
import sort.radixsort.RadixSort;
import sort.standard.StandardSort;

public final class Main {
	
	public static void main(String[] args) {
		/*Benchmark.verboseTest(
				InsertionSort.Instance,
				new RandomArrayGenerator(10, 100),
				5);*/
		System.out.println(Benchmark.correctnessTest(
				ParallelRadixSort.Instance,
				new RandomArrayGenerator(1000),
				5));
		for (int i = 0; i <= 10; ++ i) {
			System.out.println(Benchmark.performanceTest(
					ParallelRadixSort.Instance,
					new RandomArrayGenerator(10_000_000 / 10 * i),
					5, 2));
		}
	}

}
