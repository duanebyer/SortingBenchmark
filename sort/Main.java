package sort;

import sort.benchmark.Benchmark;
import sort.benchmark.RandomArrayGenerator;
import sort.radixsort.RadixSort;

public final class Main {
	
	public static void main(String[] args) {
		System.out.println(Benchmark.test(
				RadixSort.Instance,
				new RandomArrayGenerator(10000000),
				5, 2));
	}

}
