import Heap.FibonacciHeap;
import Node.FibonacciNode;
import Node.Node;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ：komikun
 * @date ：Created in 2020-03-23 09:33
 * @description：
 * @modified By：
 * @version:
 */
public class Dijikstra {

  private static int INFINITY = Integer.MAX_VALUE;

  private Graph graph;

  private void readFile(String fileName) {
    Point[] adjacent = null;
    int nodeNum = 0;
    URL url = Resources.getResource(fileName);
    try {
      List<String> lines = Resources.readLines(url, Charsets.UTF_8);
      for (String line : lines) {
        String[] arr = line.split(" ");
        if (arr[0].equals("p")) {
          nodeNum = Integer.parseInt(arr[2])+1;
          break;
        }
      }
      adjacent = new Point[nodeNum];
      Arrays.setAll(adjacent,x->new Point(new HashSet<>()));
      for (String line : lines) {
        String[] arr = line.split(" ");
        if (arr[0].equals("a")) {
          adjacent[Integer.parseInt(arr[1])].getAdjacent().add(new Route(Integer.parseInt(arr[2]),Integer.parseInt(arr[3])));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    graph = new Graph(adjacent);
  }

  public int[] calMinDistanceByArray(int from) {
    int[] distances = new int[graph.getPoints().length];
    Boolean[] marks = new Boolean[distances.length];
    Arrays.setAll(distances, x -> INFINITY);
    Arrays.setAll(marks, x -> false);
    distances[0] = INFINITY;
    distances[from] = 0;
    marks[from] = true;
    for (Route route : graph.getPoints()[from].getAdjacent()) {
      distances[route.getTo()] = route.getDistance();
    }
    while (true){
      int index = 0;
      for (int i = 1; i < distances.length; i++) {
        if (!marks[i] && distances[i] < distances[index]){
          index = i;
        }
      }
      if (index == 0){
        break;
      }
      int minValue = distances[index];
      marks[index] = true;
      for (Route route : graph.getPoints()[index].getAdjacent()) {
        if (!marks[route.getTo()]) {
          if (minValue + route.getDistance() < distances[route.getTo()]) {
            distances[route.getTo()] = minValue + route.getDistance();
          }
        }
      }
    }
    return distances;
  }
  public int[] calMinDistanceByHeap(int from) {
    // heap
    int[] distances = new int[graph.getPoints().length];
    FibonacciNode[] distanceNodes = new FibonacciNode[graph.getPoints().length];
    Arrays.setAll(distanceNodes, i -> new FibonacciNode(INFINITY,i));
    Arrays.setAll(distances, i -> INFINITY);
    FibonacciHeap heap = new FibonacciHeap();
    heap.init(distanceNodes);
    heap.reduceKey(distanceNodes[from],0);
    distances[from] = heap.popMinNode().getvalue();
    distanceNodes[from] = null;
    for (Route route : graph.getPoints()[from].getAdjacent()) {
      heap.reduceKey(distanceNodes[route.getTo()],route.getDistance());
    }
    while (true){
      FibonacciNode minNode = heap.popMinNode();
      if (minNode.getvalue() == INFINITY){
        break;
      }
      int index = minNode.getIndex();
      int minValue = minNode.getvalue();
      distanceNodes[index] = null;
      distances[index] = minValue;
      for (Route route : graph.getPoints()[index].getAdjacent()) {
        if (distanceNodes[route.getTo()] != null) {
          if (minValue + route.getDistance() < distanceNodes[route.getTo()].getvalue()) {
            heap.reduceKey(distanceNodes[route.getTo()],minValue + route.getDistance());
          }
        }
      }
    }
    return distances;
  }



  public Dijikstra(String fileName) {
    readFile(fileName);
  }

}

class Graph {
  private Point[] points;

  public Graph(Point[] points) {
    this.points = points;
  }

  public Point[] getPoints() {
    return points;
  }
}
class Point{
  private Set<Route> adjacent;

  public Point(Set<Route> adjacent) {
    this.adjacent = adjacent;
  }

  public Set<Route> getAdjacent() {
    return adjacent;
  }
}
class Route {

  private int to;
  private int distance;


  public Route(int to, int distance) {
    this.to = to;
    this.distance = distance;
  }


  public int getTo() {
    return to;
  }

  public int getDistance() {
    return distance;
  }
}

