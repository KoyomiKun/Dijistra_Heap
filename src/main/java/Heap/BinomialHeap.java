package Heap;

import Node.BinomialNode;
import Node.Node;

/**
 * @author ：komikun
 * @date ：Created in 2020-03-23 16:24
 * @description：
 * @modified By：
 * @version:
 */
public class BinomialHeap implements Heap {

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

  public void init(BinomialNode[] nodeList) {
    if (nodeList == null) {
      return;
    }
    for (int i = 0; i < nodeList.length; i++) {
      insert(nodeList[i]);
    }

  }

  private void insert(BinomialNode node) {
    mergeWith(new BinomialHeap(node));
  }

  private void mergeHeap() {
    BinomialNode preP = null;
    BinomialNode p = head;
    BinomialNode nextP = head.getSubling();
    while (nextP != null) {
      if (   (p.getDegree() != nextP.getDegree())
          || ((nextP.getSubling() != null) && (nextP.getDegree() == nextP.getSubling().getDegree()))) {
        preP = p;
        p = nextP;
      } else if (p.getValue() <= nextP.getValue()) {
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

  private void mergeList(BinomialHeap heap) {
    BinomialNode p1 = head;
    BinomialNode p2 = heap.getHead();
    if (p1 == null){
      head = p2;
      return;
    }
    if (p2 == null){
      return;
    }
    BinomialNode newP = null;
    BinomialNode newHead = null;
    BinomialNode newNextP = null;
    while (p1 != null && p2 != null) {
      if (p1.getDegree() <= p2.getDegree()) {
        newP = p1;
        p1 = p1.getSubling();
      } else {
        newP = p2;
        p2 = p2.getSubling();
      }
      if (newNextP == null) {
        newNextP = newP;
        newHead = newP;
      } else {
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
  public void mergeWith(BinomialHeap h){
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

  private void deleteFromList(BinomialNode node) {
    BinomialNode p;
    if (head.equals(node)){
      head = node.getSubling();
      BinomialNode child = node.getChild();
      BinomialNode next;
      p = child;
      while (p!=null){
        next = p.getSubling();
        insert(p);
        p = next;
      }
      return;
    }
    p = head;
    while (p!=null){
      if (p.getSubling()!= null && p.getSubling().equals(node)){
        break;
      }
      p = p.getSubling();
    }
    if (p != null){
      p.setSubling(node.getSubling());
      node.setSubling(null);
    }else{
      return;
    }
    BinomialNode child = node.getChild();
    BinomialNode next = null;
    p = child;
    while (p!=null){
      next = p.getSubling();
      insert(p);
      p = next;
    }

  }

  @Override
  public void reduceKey(Node node, int to) {
    BinomialNode key = (BinomialNode) node;
    key.setValue(to);
    BinomialNode p = key;
    BinomialNode father = ((BinomialNode) node).getParent();
    while(father != null && p.getValue()<father.getValue()) {
      int tmp = father.getValue();
      father.setValue(p.getValue());
      p.setValue(tmp);
      p = father;
      father = p.getParent();
    }
  }
}
