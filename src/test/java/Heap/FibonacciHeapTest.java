package Heap;

import Node.FibonacciNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * @author ：komikun
 * @date ：Created in 2020-03-22 15:59
 * @description：
 * @modified By：
 * @version:
 */
public class FibonacciHeapTest {
  static final List<Integer> myData = new ArrayList<Integer>(Arrays.asList(9,4,5,3,8,1,6,2,10));
  static final FibonacciHeap heap = new FibonacciHeap();
  static final FibonacciNode[] nodes = new FibonacciNode[myData.size()];
  static {
    for (int i = 0; i < nodes.length; i++) {
      nodes[i] = new FibonacciNode(myData.get(i),i);
    }
    heap.init(nodes);
  }
  @Test
  public void test(){
//    System.out.println(heap);
    heap.popMinNode();
    heap.popMinNode();
    heap.reduceKey(nodes[4],1);
    heap.popMinNode();
    heap.popMinNode();
    heap.popMinNode();
    heap.popMinNode();
    heap.popMinNode();
    heap.popMinNode();
    heap.popMinNode();
    heap.popMinNode();

  }
}
