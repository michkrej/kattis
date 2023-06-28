import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Dijkstra {
  static ArrayList<Edge>[] edges;
  static int d[];
  static double df[];
  static Edge p[];
  static int numNodes;
  static int s;

  Dijkstra(ArrayList<Edge>[] edges, int n, int s) {
    this.edges = edges;
    this.numNodes = n;
    this.s = s;
    this.d = new int[numNodes];
    this.df = new double[numNodes];
    this.p = new Edge[numNodes];
  }

  public Edge[] shortest_path1(ArrayList<Edge>[] edges, int numNodes, int s) {
    // sortig by smallest weight
    PriorityQueue<Integer> nodesToVisit = new PriorityQueue<>(Comparator.comparingInt(n -> d[n]));

    for (int i = 0; i < numNodes; i++) {
      d[i] = Integer.MAX_VALUE;
      p[i] = null;
    }

    d[s] = 0;
    nodesToVisit.add(s);

    while (!nodesToVisit.isEmpty()) {
      int current = nodesToVisit.poll();
      ArrayList<Edge> around = edges[current];

      for (Edge edge : around) {
        int dist = d[current] + edge.weight;

        if (dist < d[edge.dest]) {
          d[edge.dest] = dist;
          p[edge.dest] = edge;
          nodesToVisit.add(edge.dest);
        }
      }
    }
    return p;
  }

  public Edge[] shortest_path2(ArrayList<Edge>[] edges, int numNodes, int s) {
    // sortig by smallest weight
    PriorityQueue<Integer> nodesToVisit = new PriorityQueue<>(Comparator.comparingInt(n -> d[n]));

    for (int i = 0; i < numNodes; i++) {
      d[i] = Integer.MAX_VALUE;
      p[i] = null;
    }

    d[s] = 0;
    nodesToVisit.add(s);

    while (!nodesToVisit.isEmpty()) {
      int current = nodesToVisit.poll();
      ArrayList<Edge> around = edges[current];

      for (Edge edge : around) {
        // avoid adding infinite edges
        if ((edge.getNextTime(d[current], current, d) != Integer.MAX_VALUE)) {
          int dist = edge.getNextTime(d[current], current, d) + edge.d;

          if (dist < d[edge.dest]) {
            d[edge.dest] = dist;
            p[edge.dest] = edge;
            nodesToVisit.add(edge.dest);
          }
        }

      }
    }
    return p;
  }

  public double get_shorty(ArrayList<Edge>[] edges, int numNodes) {
    // sortig by smallest weight
    PriorityQueue<Integer> nodesToVisit = new PriorityQueue<>(Comparator.comparingDouble(n -> -df[n]));

    /*
     * for (int i = 0; i < numNodes; i++) {
     * df[i] = Integer.MAX_VALUE;
     * }
     */

    df[0] = 1.0000;
    nodesToVisit.add(0);

    while (!nodesToVisit.isEmpty()) {
      int current = nodesToVisit.poll();
      ArrayList<Edge> around = edges[current];

      if (current == numNodes - 1) {
        return df[numNodes - 1];
      }

      for (Edge edge : around) {
        double dist = df[current] * edge.factor;

        if (dist > df[edge.dest]) {
          nodesToVisit.remove(edge.dest); // removing redundant nodes i guess??
          df[edge.dest] = dist;
          nodesToVisit.add(edge.dest);
        }

      }
    }
    return df[0];
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

  public int getDistance(int n) {
    return d[n];
  }

  static class Edge {
    int src, dest, weight, t, P, d;
    double factor;

    Edge(int src, int dest, int weight) {
      this.src = src;
      this.dest = dest;
      this.weight = weight;
    }

    Edge(int src, int dest, double weight) {
      this.src = src;
      this.dest = dest;
      this.factor = weight;
    }

    Edge(int src, int dest, int t, int P, int d) {
      this.src = src;
      this.dest = dest;
      this.t = t;
      this.P = P;
      this.d = d;
    }

    public int getNextTime(int currentTime, int index, int dist[]) {
      if (currentTime <= t)
        return dist[index] + (t - currentTime);
      if (P != 0) {
        // very confusing time calculation
        return (int) Math.ceil((currentTime - t) / (double) P) * P + t;
      }
      return Integer.MAX_VALUE;
    }
  }
}
