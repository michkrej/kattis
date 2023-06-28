import java.util.*;
/* 
  Author: Michelle Krejci
  Problem: Implement Bellman-Fords algorithm for finding the shortest path 
           from a node to all other nodes in a graph where edge weights 
           may be negative. 
  Time complexity: O(n*m)
  Usage instructions: Run the file and enter data in terminal
*/

public class shortestpath3 {
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
      ArrayList<Edge> edges = new ArrayList<>();

      // Add connections between nodes
      for (int i = 0; i < m; i++) {
        int u = io.getInt();
        int v = io.getInt();
        int w = io.getInt();

        edges.add(new Edge(u, v, w));
      }

      BellmanFord bf = new BellmanFord(edges, n, s);
      bf.shortest_path(edges, n, s);

      // Run quieries
      for (int i = 0; i < q; i++) {
        int node = io.getInt();

        int dist = bf.getDistance(node);
        if (bf.isCycle(node)) {
          io.println("-Infinity");
        } else if (dist == Integer.MAX_VALUE) {
          io.println("Impossible");
        } else {
          io.println(dist);
        }
      }

    }

    io.flush();
  }

  static class BellmanFord {
    ArrayList<Edge> edges;
    int d[];
    Edge p[];
    int numNodes;
    int s;
    boolean cycle[];

    BellmanFord(ArrayList<Edge> edges, int n, int s) {
      this.edges = edges;
      this.numNodes = n;
      this.s = s;
      this.d = new int[numNodes];
      this.p = new Edge[numNodes];
      this.cycle = new boolean[numNodes];
    }

    void shortest_path(ArrayList<Edge> edges, int numNodes, int s) {
      for (int i = 0; i < numNodes; i++) {
        d[i] = Integer.MAX_VALUE;
        p[i] = null;
      }

      d[s] = 0;

      // Relax all edges
      for (int i = 1; i < numNodes; i++) {
        for (Edge edge : edges) {
          if (d[edge.src] == Integer.MAX_VALUE)
            continue;

          int dist = d[edge.src] + edge.weight;
          if (dist < d[edge.dest]) {
            d[edge.dest] = Math.max(Integer.MIN_VALUE, dist);
            p[edge.dest] = edge;
          }
        }
      }

      // Look for negative cycles
      for (int i = 1; i < numNodes; i++) {
        for (Edge edge : edges) {
          if (d[edge.src] == Integer.MAX_VALUE)
            continue;

          int dist = d[edge.src] + edge.weight;
          // if src node is part of a negative cycle the destination node is too
          if (cycle[edge.src]) {
            cycle[edge.dest] = true;
            d[edge.dest] = Integer.MIN_VALUE;
          } else if (dist < d[edge.dest]) {
            cycle[edge.dest] = true;
            d[edge.dest] = Integer.MIN_VALUE;
          }
        }
      }

    }

    public ArrayList<Integer> restore_path(int goal) {
      Edge current = p[goal];
      if (current == null) {
        return null;
      }

      ArrayList<Integer> path = new ArrayList<>();

      while (true) {
        path.add(0, current.dest);
        Edge parent = p[current.src];

        if (parent == null && current.src == s) {
          break;
        } else if (parent == null) {
          return null;
        }

        current = parent;
      }
      path.add(0, 0);
      return path;
    }

    int getDistance(int n) {
      return d[n];
    }

    boolean isCycle(int n) {
      return cycle[n];
    }

  }

  static class Edge {
    int src, dest, weight, t, P, d;
    double factor;

    Edge(int src, int dest, int weight) {
      this.src = src;
      this.dest = dest;
      this.weight = weight;
    }

  }
}
