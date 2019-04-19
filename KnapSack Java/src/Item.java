/**
 * 
 * item is a pair of (value, weight)
 *
 */
public class Item {

	public final int value, weight;

	Item(int value, int weight) {
		this.weight = weight;
		this.value = value;
	}

	@Override
	public String toString() {
		return "(" + value + ", " + weight + ")";
	}

}
