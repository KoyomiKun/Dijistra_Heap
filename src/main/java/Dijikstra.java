import Heap.BinomialHeap;
import Heap.FibonacciHeap;
import Node.FibonacciNode;
import Node.BinomialNode;
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
 * @description Dijikstra算法处理最短路径问题，算法详细注释在用斐波那契堆实现的函数中
 */
public class Dijikstra {

  // 定义正无穷
  private static int INFINITY = Integer.MAX_VALUE;
  // 算法要处理的图
  private Graph graph;

  /**
   * @param fileName 文件名
   * @description 读取文件到graph中
   **/
  private void readFile(String fileName) {
    // 用gruva正常读取文件的流程，算法中不care
    Point[] adjacent = null;
    int nodeNum = 0;
    URL url = Resources.getResource(fileName);
    try {
      List<String> lines = Resources.readLines(url, Charsets.UTF_8);
      for (String line : lines) {
        String[] arr = line.split(" ");
        if (arr[0].equals("p")) {
          nodeNum = Integer.parseInt(arr[2]) + 1;
          break;
        }
      }
      adjacent = new Point[nodeNum];
      Arrays.setAll(adjacent, x -> new Point(new HashSet<>()));
      for (String line : lines) {
        String[] arr = line.split(" ");
        if (arr[0].equals("a")) {
          adjacent[Integer.parseInt(arr[1])].getAdjacent()
              .add(new Route(Integer.parseInt(arr[2]), Integer.parseInt(arr[3])));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    graph = new Graph(adjacent);
  }

  /**
   * @param from 出发点的编号
   * @return int[] 距离数组
   * @description 用遍历数组方式取最小值，用于和堆方法比较
   **/
  public int[] calMinDistanceByArray(int from) {
    // 对照组的算法和后面用堆的一致，不再赘述，后文有详细讲
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
    while (true) {
      int index = 0;
      for (int i = 1; i < distances.length; i++) {
        if (!marks[i] && distances[i] < distances[index]) {
          index = i;
        }
      }
      if (index == 0) {
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

  /**
   * @param from 开始点的编号
   * @return int[] 距离数组
   * @description 从斐波那契堆处理最小值
   **/
  private int[] calMinDistanceByFibonacciHeap(int from) {
    // distances数组记录from到各个点的距离最小值，即为"已阅列表"
    int[] distances = new int[graph.getPoints().length];
    // distanceNodes数组记录每个点在堆中对应的节点
    FibonacciNode[] distanceNodes = new FibonacciNode[graph.getPoints().length];
    // 初始所有距离都是正无穷
    Arrays.setAll(distanceNodes, i -> new FibonacciNode(INFINITY, i));
    Arrays.setAll(distances, i -> INFINITY);
    FibonacciHeap heap = new FibonacciHeap();
    // 初始化斐波那契堆
    heap.init(distanceNodes);
    // 将from节点的值减小到0
    heap.reduceKey(distanceNodes[from], 0);
    // 将from节点的距离设置为heap中的最小值，已阅
    distances[from] = heap.popMinNode().getvalue();
    // 将已阅的节点从节点数组中删除
    distanceNodes[from] = null;
    // 通过在堆中减小节点的键值，更新from相邻点的距离
    for (Route route : graph.getPoints()[from].getAdjacent()) {
      heap.reduceKey(distanceNodes[route.getTo()], route.getDistance());
    }
    while (true) {
      // 取出最小节点
      FibonacciNode minNode = heap.popMinNode();
      // 如果最小节点是正无穷，则堆中无与from联通的点，结束循环
      if (minNode.getvalue() == INFINITY) {
        break;
      }
      int index = minNode.getIndex();
      int minValue = minNode.getvalue();
      // 将最小节点设为已阅，并设置距离
      distanceNodes[index] = null;
      distances[index] = minValue;
      // 对每个与最小节点相邻的节点
      for (Route route : graph.getPoints()[index].getAdjacent()) {
        // 如果这个点尚未已阅
        if (distanceNodes[route.getTo()] != null) {
          // 且通过最小节点到这个点比其他路径到这个点更近
          if (minValue + route.getDistance() < distanceNodes[route.getTo()].getvalue()) {
            // 在堆中更新路径值
            heap.reduceKey(distanceNodes[route.getTo()], minValue + route.getDistance());
          }
        }
      }
    }
    return distances;

  }

  /**
   * @param from 开始点的编号
   * @return int[] 距离数组
   * @description 从二项式堆处理最小值
   **/
  private int[] calMinDistanceByBinomialHeap(int from) {
    int[] distances = new int[graph.getPoints().length];
    BinomialNode[] distanceNodes = new BinomialNode[graph.getPoints().length];
    Arrays.setAll(distanceNodes, i -> new BinomialNode(INFINITY, i));
    Arrays.setAll(distances, i -> INFINITY);
    BinomialHeap heap = new BinomialHeap();
    heap.init(distanceNodes);
    // 这里与斐波那契堆不同，因为减小节点是父与子值的交换，所以要传入节点列表，将节点列表中的也交换
    heap.reduceKey(distanceNodes[from], 0, distanceNodes);
    distances[from] = heap.popMinNode().getValue();
    distanceNodes[from] = null;
    for (Route route : graph.getPoints()[from].getAdjacent()) {
      heap.reduceKey(distanceNodes[route.getTo()], route.getDistance(), distanceNodes);
    }
    while (true) {
      BinomialNode minNode = heap.popMinNode();
      if (minNode.getValue() == INFINITY) {
        break;
      }
      int index = minNode.getIndex();
      int minValue = minNode.getValue();
      distanceNodes[index] = null;
      distances[index] = minValue;
      for (Route route : graph.getPoints()[index].getAdjacent()) {
        if (distanceNodes[route.getTo()] != null) {
          if (minValue + route.getDistance() < distanceNodes[route.getTo()].getValue()) {
            heap.reduceKey(distanceNodes[route.getTo()], minValue + route.getDistance(),
                distanceNodes);
          }
        }
      }
    }
    return distances;
  }

  public int[] calMinDistanceByHeap(int from, String type) {
    if (type.equals("F")) {
      return calMinDistanceByFibonacciHeap(from);
    } else if (type.equals("B")) {
      return calMinDistanceByBinomialHeap(from);
    }
    return null;
  }


  public Dijikstra(String fileName) {
    readFile(fileName);
  }

}

class Graph {

  // 图中包含的节点
  private Point[] points;

  public Graph(Point[] points) {
    this.points = points;
  }

  public Point[] getPoints() {
    return points;
  }
}

class Point {

  // 与该节点相邻的路径
  private Set<Route> adjacent;

  public Point(Set<Route> adjacent) {
    this.adjacent = adjacent;
  }

  public Set<Route> getAdjacent() {
    return adjacent;
  }
}

class Route {

  // 路径去向的节点编号
  private int to;
  // 路径长度
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

