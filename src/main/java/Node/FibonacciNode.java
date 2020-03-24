package Node;


/**
 * @author ：komikun
 * @date ：Created in 2020-03-22 14:50
 */
public class FibonacciNode implements Node {
  // 键值
  private int value;
  // 度数
  private int degree;
  // 节点编号
  private int index;
  // 左兄弟
  private FibonacciNode left;
  // 右兄弟
  private FibonacciNode right;
  // 孩子
  private FibonacciNode child;
  // 父亲
  private FibonacciNode parent;
  // 是否已经失去孩子
  private boolean marked;

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
  /**
   * @description 在本节点中添加孩子
   * @param key 要添加的孩子节点
   **/
  @Override
  public void addChild(Node key){

    FibonacciNode node = (FibonacciNode) key;
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
    node.setMarked(false);
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
