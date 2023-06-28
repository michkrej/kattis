public class evilstraw {
  static Kattio io = new Kattio(System.in, System.out);

  public static void main(String[] args) {

    int n = io.getInt();

    for (int i = 0; i < n; i++) {
      int res = solve(io.getWord().toCharArray());
      io.println(res == -1 ? "Impossible" : res);
    }
    io.flush();
  }

  static int solve(char[] str) {
    int totalSwaps = 0;
    int left = 0;
    int right = str.length - 1;

    while (left <= right) {

      if (str[left] == str[right]) {
        left++;
        right--;
        continue;
      }

      int swaps = findPal(str, left, right);

      if (swaps == 0) {
        return -1;
      }

      totalSwaps += swaps;
      left++;
      right--;
    }

    return totalSwaps;
  }

  static int findPal(char str[], int left, int right) {
    int swaps = 0;
    int nLeft = left + 1;
    int nRight = right - 1;

    while (nLeft <= right - 1) {
      // left match
      if (str[left] == str[nRight]) {
        while (str[left] != str[right]) {
          str = swap(str, nRight, nRight + 1);
          swaps++;
          nRight++;
        }

        return swaps;
      }

      // right match
      if (str[right] == str[nLeft]) {
        while (str[left] != str[right]) {
          swap(str, nLeft, nLeft - 1);
          swaps++;
          nLeft--;
        }

        return swaps;
      }

      nLeft++;
      nRight--;
    }

    return 0;
  }

  static char[] swap(char str[], int left, int right) {
    char temp = str[left];
    str[left] = str[right];
    str[right] = temp;
    return str;
  }
}
