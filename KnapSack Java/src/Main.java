import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		File fileInstance;
		KnapsackInstance knapsack;

		System.out.println("For schemeKnapsack write: 1 parameter filepath");
		System.out.println("Notes: parameter belong to ]0, 1[");
		System.out.println();
		System.out.println("For greedyKnapsack write: 2 filepath");

		int alg = in.nextInt();
		int result;
		if (alg == 1) {
			double parameter = in.nextDouble();
			fileInstance = new File(in.next());
			knapsack = readInstance(fileInstance);
			long startTime = System.currentTimeMillis();
			result = knapsack.schemeKnapsack(parameter);
			long endTime = System.currentTimeMillis();
			System.out.println("scheme approx. result is " + result);
			/* System.out.println(""); */
			System.out.println("Execution time " + (endTime - startTime));

		} else if (alg == 2) {
			fileInstance = new File(in.next());
			knapsack = readInstance(fileInstance);
			// long startTime = System.currentTimeMillis();
			// result = knapsack.greedyKnapsack();
			String resultS = knapsack.greedyKnapsack();
			// long endTime = System.currentTimeMillis();
			System.out.println("greedy approx. result is ");// + result);
			System.out.println(resultS);
			// System.out.println("Execution time " + (endTime - startTime));
		}
	}

	// this method reads from the file and created the knapsack instance
	private static KnapsackInstance readInstance(File fileInstance) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(fileInstance));

		// 1st line is the value of the capacity and the number of items
		String[] line = br.readLine().split(" ");
		int capacity = Integer.parseInt(line[0]);
		int nItems = Integer.parseInt(line[1]);

		Item[] items = new Item[nItems];

		// for each line there is a value and a weight
		for (int i = 0; i < nItems; i++) {
			line = br.readLine().split(" ");
			items[i] = new Item(Integer.parseInt(line[0]), Integer.parseInt(line[1]));
		}
		br.close();

		return new KnapsackInstance(items, capacity);
	}

}
