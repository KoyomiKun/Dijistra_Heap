package Heap;

import Node.Node;

/**
 * @author ：komikun
 * @date ：Created in 2020-03-22 14:50
 * @description：
 * @modified By：
 * @version:
 */
public interface Heap {
  public Node peekMinNode();
  public void reduceKey(Node key, int to);
  public Node popMinNode();
}
