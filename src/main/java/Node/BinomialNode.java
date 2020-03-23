package Node;

/**
 * @author ：komikun
 * @date ：Created in 2020-03-23 16:31
 * @description：
 * @modified By：
 * @version:
 */
public class BinomialNode implements Node{
  private int value;
  private int degree;
  BinomialNode child;
  BinomialNode parent;
  BinomialNode subling;

  public BinomialNode(int value) {
    this.value = value;
    this.degree = 0;
    this.child = null;
    this.parent = null;
    this.subling = null;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getDegree() {
    return degree;
  }

  public void setDegree(int degree) {
    this.degree = degree;
  }

  public BinomialNode getChild() {
    return child;
  }

  public void setChild(BinomialNode child) {
    this.child = child;
  }

  public BinomialNode getParent() {
    return parent;
  }

  public void setParent(BinomialNode parent) {
    this.parent = parent;
  }

  public BinomialNode getSubling() {
    return subling;
  }

  public void setSubling(BinomialNode subling) {
    this.subling = subling;
  }
}
