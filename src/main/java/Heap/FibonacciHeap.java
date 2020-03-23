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
public class FibonacciHeap implements Heap{

  private int nodeNum;         // 堆中节点的总数
  private int listNodeNum;
  private FibonacciNode minNode;        // 最小节点(某个最小堆的根节点)

  public FibonacciHeap() {
  }

  public void init(FibonacciNode[] nodeList) {
    if (nodeList == null) {
      return;
    }
    nodeNum = 0;
    listNodeNum = 0;
    for (int i = 0; i < nodeList.length; i++) {
      insert(nodeList[i]);
    }
  }
  /*
   * 将节点node插入到斐波那契堆中
   */
  private void insert(FibonacciNode node) {
    node.setParent(null);
    if (minNode == null) {
      minNode = node;
    } else {
      minNode.getRight().setLeft(node);
      node.setRight(minNode.getRight());
      minNode.setRight(node);
      node.setLeft(minNode);
      if (node.getvalue() < minNode.getvalue()) {
        minNode = node;
      }
    }
    nodeNum++;
    listNodeNum++;
  }


  /*
   * 删除结点node
   */
  private void removeNode(FibonacciNode node) {
    node.getLeft().setRight(node.getRight());
    node.getRight().setLeft(node.getLeft());
    node.setLeft(node);
    node.setRight(node);
  }
  private void removeNodeFromHeap(FibonacciNode heapNode){
    FibonacciNode left = heapNode.getLeft();
    FibonacciNode right = heapNode.getRight();
    removeNode(heapNode);
    heapNode.getParent().setDegree(heapNode.getParent().getDegree()-1);
    if (heapNode.getParent().getChild().equals(heapNode)){
      if (right != heapNode){
        heapNode.getParent().setChild(heapNode.getRight());
      }else{
        heapNode.getParent().setChild(null);
      }
    }
    nodeNum--;
  }
  private void removeNodeFromList(FibonacciNode listNode){
    if (listNode.getRight() == listNode){
      minNode = null;
    }
    removeNode(listNode);
    nodeNum--;
    listNodeNum--;
  }
  /*
   * 将node链接到root根结点
   */
  private FibonacciNode mergeNode(FibonacciNode n1,FibonacciNode n2) {
    if (n1.getvalue() > n2.getvalue()) {
      return mergeNode(n2,n1);
    }
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
    for (int i = 0; i < minNode.getDegree(); i++) {
      FibonacciNode next = p.getRight();
      insert(p);
      nodeNum--;
      p = next;
    }
    FibonacciNode next = minNode.getRight();
    removeNodeFromList(min);
    if(minNode != null){
      minNode = next;
      declineDgree();
    }

    return min;
  }
  private void declineDgree() {
    int currentNodeNum = nodeNum;
    FibonacciNode[] degrees = new FibonacciNode[nodeNum];
    Arrays.setAll(degrees,x->null);
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
        mergeNode(current,d);
        if (d.getvalue() < current.getvalue()){
          current = d;
        }
        degrees[degree] = null;
        degree++;
      }
      degrees[degree] = current;
    }
    // 将cons中的结点重新加到根表中
    for (FibonacciNode degree : degrees) {
      if (degree != null){
        insert(degree);
      }
    }
    nodeNum = currentNodeNum;

  }
  private FibonacciNode popMinTree(){
    FibonacciNode next = minNode.getRight();
    FibonacciNode current = minNode;
    removeNodeFromList(current);
    if (minNode!=null){
      minNode = next;
    }
    return current;
  }
  @Override
  public void reduceKey(Node node, int to) {
    FibonacciNode key = (FibonacciNode)node;
    key.setvalue(to);
    if (key.getParent() == null) {
      if (key.getvalue() < minNode.getvalue()) {
        minNode = key;
      }
      return;
    }
    if (key.getParent().getvalue() < to) {
      return;
    }
    while (key.getParent() != null) {
      FibonacciNode parent = key.getParent();
      removeNodeFromHeap(key);
      insert(key);
      if (parent.isMarked() == false) {
        parent.setMarked(true);
        break;
      } else {
        parent.setMarked(false);
      }
      key = parent;
    }

  }





}
