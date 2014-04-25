package sort.benchmark;

import java.util.Random;

public final class RandomArrayGenerator implements ArrayGenerator {
	
	public RandomArrayGenerator(int length) {
		this.length = length;
		this.range = -1;
		this.random = new Random();
	}
	
	public RandomArrayGenerator(int length, int range) {
		this.length = length;
		this.range = range;
		this.random = new Random();
	}
	
	public RandomArrayGenerator(int length, Random random) {
		this.length = length;
		this.range = -1;
		this.random = random;
	}
	
	public RandomArrayGenerator(int length, int range, Random random) {
		this.length = length;
		this.range = range;
		this.random = random;
	}
	
	@Override
	public int[] generateArray() {
		int[] result = new int[length];
		for (int i = 0; i < result.length; ++i) {
			if (range == -1) {
				result[i] = random.nextInt();
			}
			else {
				result[i] = random.nextInt(range);
			}
		}
		return result;
	}
	
	private final int length;
	private final int range;
	private final Random random;
	
}
