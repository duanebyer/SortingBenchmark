package sort.benchmark;

public final class SortedArrayGenerator implements ArrayGenerator {
	
	public SortedArrayGenerator(int length) {
		this.length = length;
	}
	
	@Override
	public int[] generateArray() {
		int[] result = new int[length];
		for (int i = 0; i < length; ++i) {
			result[i] = i;
		}
		return result;
	}
	
	private final int length;
	
}
