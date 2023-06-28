import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* 
  Author: Michelle Krejci
  Problem: Implement an algorithm for finding a minimum spanning tree,
           given that one exists. 
  Time complexity: O(m*log n)
  Usage instructions: Run the file and enter data in terminal
*/

public class minspantree {

  private static int[] parents;
  private static int[] size;
  static Kattio io = new Kattio(System.in, System.out);

  public static void main(String[] args) {

    while (io.hasMoreTokens()) {
      int n = io.getInt();
      int m = io.getInt();

      if (n == 0 && m == 0) {
        break;
      }

      setup(n);
      ArrayList<Edge> edges = new ArrayList<>();

      for (int i = 0; i < m; i++) {
        int u = io.getInt();
        int v = io.getInt();
        int w = io.getInt();

        // Since the edges are undirected and we want x < y we calc the smallest and
        // biggest
        int min = Math.min(u, v);
        int max = Math.max(u, v);
        edges.add(new Edge(min, max, w));
      }

      ArrayList<Edge> res = solve(edges, n);

      if (res == null) {
        io.println("Impossible");
        continue;
      }

      int cost = 0;
      for (Edge e : res) {
        cost += e.weight;
      }
      io.println(cost);
      for (Edge e : res) {
        io.println(e.src + " " + e.dest);
      }

    }

    io.flush();
  }

  static ArrayList<Edge> solve(ArrayList<Edge> edges, int n) {
    edges.sort(Comparator.comparingInt(e -> e.weight));

    ArrayList<Edge> res = new ArrayList<>();
    for (Edge e : edges) {
      if (find_set(e.src) != find_set(e.dest)) {
        union(e.src, e.dest);
        res.add(e);
      }
    }

    if (res.size() != n - 1) {
      return null;
    }

    Collections.sort(res);
    return res;
  }

  static void setup(int n) {
    parents = new int[n];
    size = new int[n];
    for (int i = 0; i < n; i++) {
      parents[i] = i;
      size[i] = 1;
    }
  }

  static int find_set(int v) {
    if (v == parents[v]) {
      return v;
    }
    return parents[v] = find_set(parents[v]);
  }

  static void union(int a, int b) {
    a = find_set(a);
    b = find_set(b);

    int[] res = new int[2];
    res[0] = a;
    res[1] = b;
    if (a != b) {
      if (size[a] < size[b]) {
        res = swap(a, b);
      }
      parents[res[1]] = res[0];
      size[res[0]] += size[res[1]];
    }
  }

  static int[] swap(int a, int b) {
    int[] res = new int[2];
    int temp = a;
    a = b;
    b = temp;
    res[0] = a;
    res[1] = b;
    return res;
  }

  static class Edge implements Comparable<Edge> {
    int src, dest, weight;

    Edge(int src, int dest, int weight) {
      this.src = src;
      this.dest = dest;
      this.weight = weight;
    }

    public int compareTo(Edge otherEdge) {
      if (src == otherEdge.src)
        return dest - otherEdge.dest;
      return src - otherEdge.src;
    }
  }
}
