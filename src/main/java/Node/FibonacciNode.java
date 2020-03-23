package Node;


/**
 * @author ：komikun
 * @date ：Created in 2020-03-22 14:50
 * @description：
 * @modified By：
 * @version:
 */
public class FibonacciNode implements Node {

  private int value;            // 关键字(键值)
  private int degree;         // 度数
  private int index;
  private FibonacciNode left;       // 左兄弟
  private FibonacciNode right;      // 右兄弟
  private FibonacciNode child;      // 第一个孩子节点
  private FibonacciNode parent;     // 父节点
  private boolean marked;     // 是否被删除第一个孩子

  public FibonacciNode(int value,int index) {
    this.value = value;
    this.degree = 0;
    this.index = index;
    this.marked = false;
    this.left = this;
    this.right = this;
    this.parent = null;
    this.child = null;
  }

  public void addChild(FibonacciNode node){
    node.setParent(this);
    if (this.child == null){
      this.child = node;
    }else{
      node.left = this.child.getLeft();
      this.child.getLeft().setRight(node);
      this.child.setLeft(node);
      node.setRight(this.child);
      this.child = node;
    }
    this.degree++;
  }

  public int getIndex() {
    return index;
  }


  public int getvalue() {
    return value;
  }

  public void setvalue(int value) {
    this.value = value;
  }

  public int getDegree() {
    return degree;
  }

  public void setDegree(int degree) {
    this.degree = degree;
  }

  public FibonacciNode getLeft() {
    return left;
  }

  public void setLeft(FibonacciNode left) {
    this.left = left;
  }

  public FibonacciNode getRight() {
    return right;
  }

  public void setRight(FibonacciNode right) {
    this.right = right;
  }

  public FibonacciNode getChild() {
    return child;
  }

  public void setChild(FibonacciNode child) {
    this.child = child;
  }

  public FibonacciNode getParent() {
    return parent;
  }

  public void setParent(FibonacciNode parent) {
    this.parent = parent;
  }

  public boolean isMarked() {
    return marked;
  }

  public void setMarked(boolean marked) {
    this.marked = marked;
  }
}
