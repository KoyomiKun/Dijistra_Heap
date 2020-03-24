package Heap;


import Node.FibonacciNode;
import Node.Node;
import java.util.Arrays;

/**
 * @author ：komikun
 * @date ：Created in 2020-03-22 14:48
 * @description：
 * @modified By：
 * @version:
 */
public class FibonacciHeap implements Heap {

  private int nodeNum;
  private FibonacciNode minNode;        // 最小节点(某个最小堆的根节点)

  public FibonacciHeap() {
  }

  public void init(FibonacciNode[] nodeList) {
    if (nodeList == null) {
      return;
    }
    nodeNum = 0;
    for (int i = 0; i < nodeList.length; i++) {
      insert(nodeList[i]);
    }
  }

  /*
   * 将节点node插入到斐波那契堆中
   */
  private void insert(FibonacciNode node) {
    if (minNode == null) {
      minNode = node;
    } else {
      insertToRight(node);
      if (node.getvalue() < minNode.getvalue()) {
        minNode = node;
      }
    }
    nodeNum++;
  }

  private void insertToRight(FibonacciNode node) {
    minNode.getRight().setLeft(node);
    node.setRight(minNode.getRight());
    minNode.setRight(node);
    node.setLeft(minNode);
  }


  /*
   * 删除结点node
   */
  private void removeNode(FibonacciNode node) {
    reduceKey(node, minNode.getvalue() - 1);
    popMinNode();
  }

  private void removeFromList(FibonacciNode node) {
    node.getLeft().setRight(node.getRight());
    node.getRight().setLeft(node.getLeft());
  }

  /*
   * 将node链接到root根结点
   */
  private FibonacciNode mergeNode(FibonacciNode n1, FibonacciNode n2) {
    removeFromList(n2);
    n1.addChild(n2);
    return n1;
  }

  /*
   * 移除最小节点
   */
  @Override
  public FibonacciNode peekMinNode() {
    return minNode;
  }


  @Override
  public FibonacciNode popMinNode() {
    FibonacciNode min = minNode;
    if (minNode == null) {
      return null;
    }
    FibonacciNode p = minNode.getChild();
    while (p != null) {
      FibonacciNode next = p.getRight();
      removeFromList(p);
      if (p.getRight() == p) {
        minNode.setChild(null);
      } else {
        minNode.setChild(next);
      }
      insertToRight(p);
      p.setParent(null);
      p = minNode.getChild();
    }
    FibonacciNode next = minNode.getRight();
    removeFromList(min);
    if (min.getRight().equals(min)) {
      minNode = null;
    } else {
      minNode = next;
      declineDgree();
    }
    nodeNum--;
    return min;
  }

  private void declineDgree() {
    int currentNodeNum = nodeNum;
    FibonacciNode[] degrees = new FibonacciNode[nodeNum];
    Arrays.setAll(degrees, x -> null);
//    int i = listNodeNum;
//    FibonacciNode current = minNode;
//    do {
//      i--;
//      int degree = current.getDegree();
//      while (degrees[degree] != null) {
//        if (!current.equals(degrees[degree])){
//          current = mergeNode(current,degrees[degree]);
//        }
//        degrees[degree] = null;
//        degree++;
//      }
//      degrees[degree] = current;
//      if (current.getvalue() <= minNode.getvalue()) {
//        minNode = current;
//      }
//      current = current.getRight();
//    } while (i > 0);

    // 合并相同度的根节点，使每个度数的树唯一
    while (minNode != null) {
      FibonacciNode current = popMinTree();            // 取出堆中的最小树(最小节点所在的树)
      int degree = current.getDegree();                        // 获取最小树的度数
      // cons[d] != null，意味着有两棵树(x和y)的"度数"相同。
      while (degrees[degree] != null) {
        FibonacciNode d = degrees[degree];                // y是"与x的度数相同的树"
        if (current.getvalue() > d.getvalue()) {
          FibonacciNode tmp = current;
          current = d;
          d = tmp;
        }
        mergeNode(current, d);
        degrees[degree] = null;
        degree++;
      }
      degrees[degree] = current;
    }
    minNode = null;
    for (FibonacciNode degree : degrees) {
      if (degree != null) {
        insert(degree);
      }
    }
    nodeNum = currentNodeNum;

  }

  private FibonacciNode popMinTree() {
    FibonacciNode next = minNode.getRight();
    FibonacciNode current = minNode;
    if (current == next) {
      minNode = null;
    } else {
      removeFromList(current);
      minNode = next;
    }
    current.setLeft(current);
    current.setRight(current);
    return current;
  }

  private void setForFather(FibonacciNode node) {
    FibonacciNode father = node.getParent();
    if (father != null) {
      if (!node.isMarked()) {
        node.setMarked(true);
      } else {
        setForChild(node,father);
        setForFather(father);
      }
    }
  }

  private void setForChild(FibonacciNode key, FibonacciNode father) {
    removeFromList(key);
    father.setDegree(father.getDegree() - 1);
    if (key.equals(key.getRight())) {
      father.setChild(null);
    } else {
      father.setChild(key.getRight());
    }
    key.setParent(null);
    key.setLeft(key);
    key.setRight(key);
    key.setMarked(false);
    insertToRight(key);
  }

  public void reduceKey(Node node, int to) {
    FibonacciNode key = (FibonacciNode) node;
    if (minNode == null || key == null) {
      return;
    }
    key.setvalue(to);
    FibonacciNode father = key.getParent();
    if (father != null && key.getvalue() < father.getvalue()) {
      setForChild(key, father);

      setForFather(father);
    }
    if (minNode.getvalue() > to) {
      minNode = key;
    }

  }


}
