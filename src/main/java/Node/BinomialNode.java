package Node;

/**
 * @author ：komikun
 * @date ：Created in 2020-03-23 16:31
 */
public class BinomialNode implements Node{
  // 键值
  private int value;
  // 度数
  private int degree;
  // 节点编号
  private int index;
  // 孩子
  private BinomialNode child;
  // 父亲
  private BinomialNode parent;
  // 邻居
  private BinomialNode subling;
  @Override
  public void addChild(Node key){
    BinomialNode node = (BinomialNode) key;
    node.setParent(this);
    node.setSubling(this.child);
    child = node;
    degree++;
  }
  public BinomialNode(int value,int index) {
    this.value = value;
    this.index = index;
    this.degree = 0;
    this.child = null;
    this.parent = null;
    this.subling = null;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
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
