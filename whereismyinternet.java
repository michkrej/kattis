import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class whereismyinternet {

  static boolean visitedNodes[];
  static LinkedList<Integer> network[];

  public static void main(String[] args) {
    Kattio io = new Kattio(System.in, System.out);
    int N = io.getInt();
    int M = io.getInt();

    network = new LinkedList[N + 1];
    visitedNodes = new boolean[N + 1];

    for (int i = 1; i < N + 1; i++) {
      network[i] = new LinkedList<Integer>();
    }

    for (int i = 0; i < M; i++) {
      int a = io.getInt();
      int b = io.getInt();

      network[a].add(b);
      network[b].add(a);
    }
    dfs(1);
    boolean used = false;
    for (int i = 1; i < N + 1; i++) {
      if (!visitedNodes[i]) {
        io.println(i);
        used = true;
      }
    }
    if (!used)
      io.println("Connected");

    io.flush();
    io.close();

  }

  static void dfs(int index) {
    visitedNodes[index] = true;
    Iterator<Integer> i = network[index].listIterator();
    while (i.hasNext()) {
      int n = i.next();
      if (!visitedNodes[n]) {
        dfs(n);
      }
    }
  }

}
