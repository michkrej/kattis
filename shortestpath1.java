import java.util.*;
/* 
  Author: Michelle Krejci
  Problem: Single source shortest path, non-negative weights - find the shortest 
           path from the startnode to the goalnode using dijkstra. 
  Time complexity: O((n+m)*log n)
  Usage instructions: Run the file and enter data in terminal
*/

public class shortestpath1 {
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
        int w = io.getInt();

        edges[u].add(new Dijkstra.Edge(u, v, w));
        // undirected graph, this seems to be directed?
        // edges[v].add(new Edge(v, u, w));
      }

      Dijkstra dji = new Dijkstra(edges, n, s);
      dji.shortest_path1(edges, n, s);

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
