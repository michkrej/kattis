import java.util.*;
/* 
  Author: Michelle Krejci
  Problem: Implement Floyd-Warshalls algorithm for finding the shortest
           distance between all pairs of nodes in a graph with edge weights. 
  Time complexity: O(n^3)
  Usage instructions: Run the file and enter data in terminal
*/

public class allpairspath {
  public static void main(String[] args) {
    Kattio io = new Kattio(System.in, System.out);

    while (io.hasMoreTokens()) {
      int n = io.getInt();
      int m = io.getInt();
      int q = io.getInt();

      if (n == 0 && m == 0 && q == 0) {
        break;
      }

      // Using Adjecency List to keep track of neighbors
      ArrayList<Edge> edges = new ArrayList<>();

      // Add connections between nodes
      for (int i = 0; i < m; i++) {
        int u = io.getInt();
        int v = io.getInt();
        int w = io.getInt();

        edges.add(new Edge(u, v, w));
      }

      FloydWarshall fw = new FloydWarshall(edges, n);
      fw.shortest_path_all_pairs(edges, n);

      // Run quieries
      for (int i = 0; i < q; i++) {
        int x = io.getInt();
        int y = io.getInt();

        int dist = fw.getDistance(x, y);
        if (dist == Integer.MIN_VALUE) {
          io.println("-Infinity");
        } else if (dist == Integer.MAX_VALUE) {
          io.println("Impossible");
        } else {
          io.println(dist);
        }
      }

      io.println();

    }

    io.flush();
  }

  static class FloydWarshall {
    ArrayList<Edge> edges;
    int d[][];
    Edge p[][];
    int n;
    boolean cycle[];

    FloydWarshall(ArrayList<Edge> edges, int n) {
      this.edges = edges;
      this.n = n;
      this.d = new int[n][n];
      this.cycle = new boolean[n];
    }

    void shortest_path_all_pairs(ArrayList<Edge> edges, int n) {

      // Fill d with INF values
      for (int[] row : d) {
        Arrays.fill(row, Integer.MAX_VALUE);
      }

      // Set edge weights for the edges we have
      for (Edge edge : edges) {
        if (edge.src == edge.dest && edge.weight > 0)
          continue;
        // Need to smallest value to not override 0 in vertex
        d[edge.src][edge.dest] = Math.min(edge.weight, d[edge.src][edge.dest]);
      }

      // Set vertex to 0
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (i == j)
            d[i][j] = 0;
        }
      }

      // Calc distances
      for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
          for (int j = 0; j < n; j++) {
            if (d[i][k] != Integer.MAX_VALUE && d[k][j] != Integer.MAX_VALUE)
              d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
          }
        }
      }

      // Find negative cycles
      for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
          for (int j = 0; j < n; j++) {
            if (d[i][k] != Integer.MAX_VALUE && d[k][j] != Integer.MAX_VALUE) {
              // If path length is less than zero it denotes negative cycle
              if (d[k][k] < 0)
                d[i][j] = Integer.MIN_VALUE;
            }

          }
        }
      }
    }

    int getDistance(int x, int y) {
      return d[x][y];
    }

  }

  static class Edge {
    int src, dest, weight;

    Edge(int src, int dest, int weight) {
      this.src = src;
      this.dest = dest;
      this.weight = weight;
    }

  }
}