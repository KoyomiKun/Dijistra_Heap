package Heap;

import Node.BinomialNode;
import Node.Node;

/**
 * @author ：komikun
 * @date ：Created in 2020-03-23 16:24
 */
public class BinomialHeap implements Heap {

  // 头节点，堆入口
  private BinomialNode head;

  public BinomialHeap() {
  }

  public BinomialHeap(BinomialNode head) {
    head.setSubling(null);
    head.setParent(null);
    this.head = head;
  }

  public BinomialNode getHead() {
    return head;
  }

  /**
   * @description 用节点列表初始化堆
   **/
  public void init(BinomialNode[] nodeList) {

    if (nodeList == null) {
      return;
    }
    for (int i = 0; i < nodeList.length; i++) {
      insert(nodeList[i]);
    }

  }

  /**
   * @description 在堆中插入新节点
   **/
  private void insert(BinomialNode node) {
    mergeWith(new BinomialHeap(node));
  }

  /**
   * @description 将堆中相同度数的合并
   **/
  private void mergeHeap() {
    // p和其左右节点
    BinomialNode preP = null;
    BinomialNode p = head;
    BinomialNode nextP = head.getSubling();
    while (nextP != null) {
      // 如果堆节点度不相同或者在下一个节点不为空且下一个节点度相同的情况下，保留p
      if ((p.getDegree() != nextP.getDegree()) || ((nextP.getSubling() != null) && (
          nextP.getDegree() == nextP.getSubling().getDegree()))) {
        preP = p;
        p = nextP;
      } else if (p.getValue() <= nextP.getValue()) {
        // 否则将值大的合并到小的上
        p.setSubling(nextP.getSubling());
        p.addChild(nextP);
      } else {
        if (preP == null) {
          head = nextP;
        } else {
          preP.setSubling(nextP);
        }
        nextP.addChild(p);
        p = nextP;
      }
      nextP = p.getSubling();
    }
  }

  /**
   * @description 将两个heap的根merge到一起
   **/
  private void mergeList(BinomialHeap heap) {
    BinomialNode p1 = head;
    BinomialNode p2 = heap.getHead();
    if (p1 == null) {
      head = p2;
      return;
    }
    if (p2 == null) {
      return;
    }
    BinomialNode newP = null;
    BinomialNode newHead = null;
    BinomialNode newNextP = null;
    // 直到其中有一个heap到最后
    while (p1 != null && p2 != null) {
      // 度数小的进入新堆
      if (p1.getDegree() <= p2.getDegree()) {
        newP = p1;
        p1 = p1.getSubling();
      } else {
        newP = p2;
        p2 = p2.getSubling();
      }
      // 初始化前一个节点和新堆入口
      if (newNextP == null) {
        newNextP = newP;
        newHead = newP;
      } else {
        // 已经初始化过，那就将前一个连到自己身上
        newNextP.setSubling(newP);
        newNextP = newP;
      }
      if (p1 != null) {
        newP.setSubling(p1);
      } else {
        newP.setSubling(p2);
      }
    }
    head = newHead;
  }

  public void mergeWith(BinomialHeap h) {
    mergeList(h);
    mergeHeap();
  }

  @Override
  public BinomialNode peekMinNode() {
    BinomialNode p = head;
    BinomialNode min = head;
    while (p != null) {
      if (min.getValue() > p.getValue()) {
        min = p;
      }
      p = p.getSubling();
    }
    return min;
  }

  @Override
  public BinomialNode popMinNode() {
    BinomialNode minNode = peekMinNode();
    deleteFromList(minNode);
    return minNode;
  }

  /**
   * @description 从根链表中删除node
   **/
  private void deleteFromList(BinomialNode node) {
    BinomialNode p;
    // 如果node就是head
    if (head.equals(node)) {
      head = node.getSubling();
      BinomialNode child = node.getChild();
      BinomialNode next;
      // 将node孩子加入list
      p = child;
      while (p != null) {
        next = p.getSubling();
        insert(p);
        p = next;
      }
      return;
    }
    // 否则，找到node的前一个节点
    p = head;
    while (p != null) {
      if (p.getSubling() != null && p.getSubling().equals(node)) {
        break;
      }
      p = p.getSubling();
    }
    // node存在，p必不为空
    if (p != null) {
      p.setSubling(node.getSubling());
      node.setSubling(null);
    } else {
      return;
    }
    // 将孩子加入list
    BinomialNode child = node.getChild();
    BinomialNode next = null;
    p = child;
    while (p != null) {
      next = p.getSubling();
      insert(p);
      p = next;
    }

  }
  /**
   * @description 减小node键值为to
   * @param node
   * @param to
   * @param distances dijkstra 的 节点列表
   **/
  public void reduceKey(Node node, int to, BinomialNode[] distances) {

    BinomialNode key = (BinomialNode) node;
    key.setValue(to);
    BinomialNode p = key;
    BinomialNode father = ((BinomialNode) node).getParent();
    // 从node往上递归
    while (father != null && p.getValue() < father.getValue()) {
      // 交换父子值和父子在distances中位置
      int tmp = father.getValue();
      int tmpIndex = father.getIndex();
      father.setValue(p.getValue());
      father.setIndex(p.getIndex());
      p.setValue(tmp);
      distances[p.getIndex()] = father;
      p.setIndex(tmpIndex);
      distances[tmpIndex] = p;
      p = father;
      father = p.getParent();
    }
  }
}
