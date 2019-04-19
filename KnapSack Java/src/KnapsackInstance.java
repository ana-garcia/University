import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 
 * this class represent an instance of Knapsack all the approx. alg. are
 * implemente here
 *
 */

public class KnapsackInstance {

	public final Item[] items;
	public final int capacity;
	public final int nItems;

	KnapsackInstance(Item[] items, int capacity) {
		this.items = items;
		this.capacity = capacity;
		this.nItems = items.length;
	}

	public int schemeKnapsack(double parameter) {
		int maxValue = 0;
		// find the maxValue of all items
		for (Item item : items) {
			if (item.value > maxValue) {
				maxValue = item.value;
			}
		}
		int scale = (int) (parameter * maxValue / nItems);

		Item[] scaledItems = new Item[nItems];
		for (int i = 0; i < nItems; i++) {
			scaledItems[i] = new Item((items[i].value / scale), items[i].weight);
		}
		KnapsackInstance scaledKanpsack = new KnapsackInstance(scaledItems, capacity);
		return scaledKanpsack.exactKnapsack() * scale;

	}

	public int exactKnapsack() {
		int valuesSum = 0;
		// find the total sum of values for every item
		for (Item item : items) {
			valuesSum += item.value;
		}

		int[][] t = new int[nItems + 1][valuesSum + 1];

		for (int i = 0; i <= nItems; i++) {
			t[i][0] = 0;
		}

		for (int j = 1; j <= valuesSum; j++) {
			t[0][j] = valuesSum + 1;
		}

		for (int i = 1; i <= nItems; i++) {
			for (int j = 1; j <= items[i - 1].value; j++) {
				t[i][j] = t[i - 1][j];
			}
			for (int j = items[i - 1].value; j <= valuesSum; j++) {
				t[i][j] = Math.min(items[i - 1].weight + t[i - 1][j - items[i - 1].value], t[i - 1][j]);
			}
		}
		for (int j = valuesSum; j >= 1; j--)
			if (t[nItems][j] <= capacity)
				return j;
		return 0;
	}

	public String greedyKnapsack() {
		Item[] sortedItems = items.clone();

		Arrays.sort(sortedItems, new Comparator<Item>() {

			@Override
			public int compare(Item o1, Item o2) {
				double v1 = (double) o1.weight / o1.value;
				double v2 = (double) o2.weight / o2.value;
				return Double.compare(v1, v2);

			}

		});

		int totalValue = 0;
		int totalWeight = 0;

		ArrayList<Item> solutionItems = new ArrayList<Item>(); // vetor com os
																// items de
																// solução

		for (Item item : sortedItems) {
			if (totalWeight + item.weight > capacity) {
				String resultS = Solution(solutionItems);
				if (totalValue < item.value) {
					return "" + item.value + resultS;
				} else {
					return "" + totalValue + resultS;
				}
			} else {
				totalValue += item.value;
				totalWeight += item.weight;
				solutionItems.add(item);
			}
		}
		// this only happens when we can add all the items to the solution

		String resultS = "Value:" + totalValue + Solution(solutionItems);
		return resultS;

	}

	private String Solution(ArrayList<Item> solutionItems) {
		String result = "";
		for (Item item : solutionItems) {
			result += "\n" + item.value + " " + item.weight;
		}
		return result;
	}

}
