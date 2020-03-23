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
  public BinomialHeap(){}

  public BinomialNode getHead() {
    return head;
  }

  public void init(BinomialNode[] nodeList){
    if (nodeList == null) {
      return;
    }
    for (int i = 0; i < nodeList.length; i++) {
      insert(nodeList[i]);
    }

  }
  private void insert(BinomialNode node){

  }
  private void merge(BinomialHeap heap){
    BinomialNode p1 = head;
    BinomialNode p2 = heap.getHead();
    while (p1 != null && p2 != null){

    }

  }
  @Override
  public BinomialNode peekMinNode() {
    BinomialNode p = head;
    BinomialNode min = head;
    while (p != null)
    {
      if (min.getValue() > p.getDegree()){
        min = p;
      }
      p = p.getSubling();
    }
    return min;
  }

  @Override
  public BinomialNode popMinNode() {
    delete(minNode);
    return minNode;
  }
  private void delete(BinomialNode node){

  }

  @Override
  public void reduceKey(Node node, int to) {
    BinomialNode key = (BinomialNode)node;


  }
}
