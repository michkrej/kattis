import java.util.*;
/* 
  Author: Michelle Krejci
  Problem: Add support to you Dijkstra implementation to handle time table graphs,
           i.e., graphs where an edge may only be used during certain time intervals.  
  Time complexity: O(n^2 + m)
  Usage instructions: Run the file and enter data in terminal
*/

public class shortestpath2 {
  public static void main(String[] args) {
    Kattio io = new Kattio(System.in, System.out);

    while (io.hasMoreTokens()) {
      int n = io.getInt();
      int m = io.getInt();
      int q = io.getInt();
      int s = io.getInt();

      if (n == 0 && m == 0 && q == 0 && s == 0) {
        break;
      }

      // Using Adjecency List to keep track of neighbors
      ArrayList<Dijkstra.Edge>[] edges = new ArrayList[n];

      for (int i = 0; i < n; i++) {
        edges[i] = new ArrayList<>();
      }

      // Add connections between nodes
      for (int i = 0; i < m; i++) {
        int u = io.getInt();
        int v = io.getInt();
        int t = io.getInt();
        int P = io.getInt();
        int d = io.getInt();

        edges[u].add(new Dijkstra.Edge(u, v, t, P, d));
        // undirected graph, this seems to be directed?
        // edges[v].add(new Edge(v, u, w));
      }

      Dijkstra dji = new Dijkstra(edges, n, s);
      dji.shortest_path2(edges, n, s);

      // Run quieries
      for (int i = 0; i < q; i++) {
        int node = io.getInt();
        int dist = dji.getDistance(node);
        // ArrayList<Integer> path = dji.restore_path(node);
        // io.println("Path: " + path);
        if (dist == Integer.MAX_VALUE) {
          io.println("Impossible");
        } else {
          io.println(dist);
        }
      }

    }

    io.flush();
  }
}
