import java.util.ArrayList;
import java.util.Scanner;

/* 
  Author: Michelle Krejci
  Problem: Implement a function that given a string and a pattern 
           finds all matches of the pattern in the string.
  Time complexity: O(text + pattern)
  Usage instructions: Run the file and enter data in terminal
*/

public class stringmatching {

  public static void main(String[] args) {
    Kattio io = new Kattio(System.in, System.out);
    Scanner s = new Scanner(System.in);

    while (s.hasNext()) {
      char pattern[] = s.nextLine().toCharArray();
      char text[] = s.nextLine().toCharArray();

      ArrayList<Integer> res = findPattern(pattern, text);
      for (int elem : res) {
        io.print(elem + " ");
      }
      io.println();
      io.flush();
    }

    s.close();
  }

  static ArrayList<Integer> findPattern(char pattern[], char text[]) {
    int m = text.length;
    int n = pattern.length;
    int pi[] = createPrefix(pattern);
    ArrayList<Integer> res = new ArrayList<>();
    int j = 0;

    for (int i = 0; i < m; i++) {
      // int j = pi[i - 1];
      while (j > 0 && text[i] != pattern[j]) {
        j = pi[j - 1];
      }
      while (j == 0 && text[i] != pattern[j])
        j--;
      j++;

      if (j == n) {
        res.add(i - n + 1);
        j = pi[n - 1];
      }
    }

    return res;
  }

  static int[] createPrefix(char pattern[]) {
    int n = pattern.length;
    int pi[] = new int[n];

    for (int i = 1; i < n; i++) {
      int j = pi[i - 1];
      while (j > 0 && pattern[i] != pattern[j])
        j = pi[j - 1];
      if (pattern[i] == pattern[j])
        j++;
      pi[i] = j;
    }
    return pi;
  }

}
