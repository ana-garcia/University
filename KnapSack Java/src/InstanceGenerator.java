import java.util.Scanner;

public class InstanceGenerator {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.println("Give:");
		System.out.println("the number of elements(Size);");
		System.out.println("the possible range that values can take (MinValue to MaxValue);");
		System.out.println("the possible range that weights can take (MinWeights to MazWeights);");
		System.out.println("Format: Size MinValue MaxValue MinWeight MaxWeight ");

		int size = in.nextInt();
		int minValue = in.nextInt();
		int maxValue = in.nextInt();
		int minWeight = in.nextInt();
		int maxWeight = in.nextInt();

		instanceGenerator(size, minValue, maxValue, minWeight, maxWeight);
	}

	// generate a random instance
	private static void instanceGenerator(int size, int minValue, int maxValue, int minWeight, int maxWeight) {
		int sumWeight = 0;
		int[][] items = new int[size][2];
		for (int i = 0; i < size; i++) {
			items[i][0] = randomNumber(minValue, maxValue); // value
			items[i][1] = randomNumber(minWeight, maxWeight); // weight
			sumWeight += items[i][1];
		}

		int capacity = randomNumber(1, sumWeight);

		printInstance(capacity, items);

	}

	private static void printInstance(int capacity, int[][] items) {

		System.out.println(capacity + " " + items.length);

		for (int i = 0; i < items.length; i++) {
			System.out.println(items[i][0] + " " + items[i][1]);
		}

	}

	// generate a random int value between min and max
	private static int randomNumber(int min, int max) {
		return min + (int) (Math.random() * (max - min + 1));
	}
}
