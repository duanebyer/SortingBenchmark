package sort.benchmark;

public final class FixedArrayGenerator implements ArrayGenerator {
	
	public FixedArrayGenerator(int... array) {
		this.array = array;
	}
	
	@Override
	public int[] generateArray() {
		int[] result = new int[array.length];
		for (int i = 0; i < result.length; ++i) {
			result[i] = array[i];
		}
		return result;
	}
	
	private final int[] array;
	
}
