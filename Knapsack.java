/* 
  Author: Michelle sackrejci
  Problem: knapsack, where index of used items should be returned aswell as length
  Time complexity: Find optimal subset takes O(n*c)
*/

package Lab1;

import java.util.ArrayList;
import java.util.Scanner;

public class Knapsack {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    while (sc.hasNextLong()) {
      int c = sc.nextInt(), n = sc.nextInt();

      Item[] valueWeights;
      valueWeights = new Item[n];

      for (int i = 0; i < n; i++) {
        int value = sc.nextInt();
        int weight = sc.nextInt();

        valueWeights[i] = new Item(value, weight);
      }

      ArrayList<Integer> res = knapsack(c, valueWeights);
      System.out.println(res.size());
      String indexes = "";
      for (int i = 0; i < res.size(); i++) {
        indexes += res.get(i) + " ";
      }
      System.out.println(indexes);
    }
    sc.close();
  }

  static ArrayList<Integer> knapsack(int capacity, Item[] items) {
    int n = items.length;
    int sack[][] = new int[n + 1][capacity + 1];

    for (int row = 0; row <= n; row++) {
      for (int c = 0; c <= capacity; c++) {
        if (row == 0 || c == 0)
          sack[row][c] = 0;
        else if (items[row - 1].weight <= c)
          sack[row][c] = Math.max(items[row - 1].value +
              sack[row - 1][c - items[row - 1].weight], sack[row - 1][c]);
        else
          sack[row][c] = sack[row - 1][c];
      }
    }

    return findIndexes(capacity, n, items, sack);
  }

  static ArrayList<Integer> findIndexes(int capacity, int n, Item[] items, int[][] sack ) {
    ArrayList<Integer> subset = new ArrayList<Integer>(n);

    int c = capacity;
    int res = sack[n][capacity];
    for (int row = n; row > 0 && res > 0; row--) {
      if (res == sack[row - 1][c])
        continue;
      else {
        subset.add(row - 1);
        res = res - items[row - 1].value;
        c = c - items[row - 1].weight;
      }
    }

    return subset;
  }

  private static class Item {
    int value;
    int weight;

    Item(int value, int weight) {
      this.value = value;
      this.weight = weight;
    }
  }

}
