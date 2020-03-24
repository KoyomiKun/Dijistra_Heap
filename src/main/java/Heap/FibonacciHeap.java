package Heap;


import Node.FibonacciNode;
import Node.Node;
import java.util.Arrays;

/**
 * @author ：komikun
 * @date ：Created in 2020-03-22 14:48
 */
public class FibonacciHeap implements Heap {

  // heap中总节点数
  private int nodeNum;
  // 最小节点，即堆入口
  private FibonacciNode minNode;

  public FibonacciHeap() {
  }

  /**
   * @description 用节点列表初始化heap
   **/
  public void init(FibonacciNode[] nodeList) {
    if (nodeList == null) {
      return;
    }
    nodeNum = 0;
    // 遍历list，将每个element插入heap
    for (int i = 0; i < nodeList.length; i++) {
      insert(nodeList[i]);
    }
  }

  /**
   * @description 在堆中插入节点，并检查最小节点
   **/
  private void insert(FibonacciNode node) {
    if (minNode == null) {
      minNode = node;
    } else {
      // 在最小节点的右边插入
      insertToRight(node);
      // 检查是否要更新最小节点
      if (node.getvalue() < minNode.getvalue()) {
        minNode = node;
      }
    }
    nodeNum++;
  }

  /**
   * @description 在最小节点右边插入新节点
   **/
  private void insertToRight(FibonacciNode node) {
    minNode.getRight().setLeft(node);
    node.setRight(minNode.getRight());
    minNode.setRight(node);
    node.setLeft(minNode);
  }


  /**
   * @description 从根列表中删除节点
   **/
  private void removeFromList(FibonacciNode node) {
    node.getLeft().setRight(node.getRight());
    node.getRight().setLeft(node.getLeft());
  }

  /**
   * @param n1 较小节点
   * @param n2 较大节点
   * @description 将大节点放到小节点的孩子上
   **/
  private FibonacciNode mergeNode(FibonacciNode n1, FibonacciNode n2) {
    // 从根列表中删除节点n2
    removeFromList(n2);
    // n1添加孩子n2
    n1.addChild(n2);
    return n1;
  }

  /**
   * @return Node.FibonacciNode 最小节点
   * @description 得到最小节点
   **/
  @Override
  public FibonacciNode peekMinNode() {
    return minNode;
  }

  /**
   * @return Node.FibonacciNode 最小节点
   * @description 取出最小节点
   **/
  @Override
  public FibonacciNode popMinNode() {
    FibonacciNode min = minNode;
    // 空堆，直接返回
    if (minNode == null) {
      return null;
    }
    // 将最小节点的每个孩子都放到根节点上
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
    // 从根列表中删除最小节点
    FibonacciNode next = minNode.getRight();
    removeFromList(min);
    // 最小节点是最后一个节点，删除后为空堆
    if (min.getRight().equals(min)) {
      minNode = null;
    } else {
      // 否则将最小节点暂时设置为右边一个节点
      minNode = next;
      // 使得堆中没有相同degree的树
      declineDgree();
    }
    nodeNum--;
    return min;
  }

  /**
   * @description 合并相同度的根节点
   **/
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
    while (minNode != null) {
      // 取得最小树
      FibonacciNode current = popMinTree();
      int degree = current.getDegree();
      //如果degrees中存储了之前遇到的和current相同度数的树
      while (degrees[degree] != null) {
        FibonacciNode d = degrees[degree];
        // 大数在后，要被合并
        if (current.getvalue() > d.getvalue()) {
          FibonacciNode tmp = current;
          current = d;
          d = tmp;
        }
        // 合并这两个树
        mergeNode(current, d);
        degrees[degree] = null;
        // 再往后找是否有相同
        degree++;
      }
      degrees[degree] = current;
    }
    minNode = null;
    // 重新插入堆中
    for (FibonacciNode degree : degrees) {
      if (degree != null) {
        insert(degree);
      }
    }
    nodeNum = currentNodeNum;
  }

  /**
   * @description 取出最小节点所在树
   **/
  private FibonacciNode popMinTree() {
    FibonacciNode next = minNode.getRight();
    FibonacciNode current = minNode;
    if (current == next) {
      minNode = null;
    } else {
      removeFromList(current);
      minNode = next;
    }
    // 将树从根列表中删除
    current.setLeft(current);
    current.setRight(current);
    return current;
  }

  /**
   * @param node 父节点
   * @description 处理父亲的集联删除
   **/
  private void setForFather(FibonacciNode node) {
    FibonacciNode father = node.getParent();
    if (father != null) {
      // 父节点的父节点是否已经失去孩子
      if (!node.isMarked()) {
        node.setMarked(true);
      } else {
        // 已经失去，递归继续进行删除
        setForChild(node, father);
        setForFather(father);
      }
    }
  }

  /**
   * @param key 孩子节点
   * @param father 父亲节点
   * @description 处理孩子的集联删除
   **/
  private void setForChild(FibonacciNode key, FibonacciNode father) {
    // 从父亲的孩子列表中删除孩子
    removeFromList(key);
    // 父亲的度数-1
    father.setDegree(father.getDegree() - 1);
    // 如果孩子是父亲最后一个孩子，父亲孩子设为0
    if (key.equals(key.getRight())) {
      father.setChild(null);
    } else {
      // 否则，孩子设为孩子的右边
      father.setChild(key.getRight());
    }
    key.setParent(null);
    key.setLeft(key);
    key.setRight(key);
    key.setMarked(false);
    insertToRight(key);
  }
  /**
   * @description 将node的键值减小为to
   * @param node
   * @param to
   **/
  public void reduceKey(Node node, int to) {
    FibonacciNode key = (FibonacciNode) node;
    // 如果节点为空，直接返回
    if (key == null) {
      return;
    }
    key.setvalue(to);
    FibonacciNode father = key.getParent();
    // 分别处理孩子和父亲的集联删除
    if (father != null && key.getvalue() < father.getvalue()) {
      setForChild(key, father);
      setForFather(father);
    }
    // 更新最小节点，因为父亲节点如果不是根节点，要移动到根则必须是破坏最小堆
    // 而最小堆结构只有当父亲大于孩子才会被破坏，这里只要检查key是否小于最小节点即可
    if (minNode.getvalue() > to) {
      minNode = key;
    }

  }


}
