package linkedList;

public class Link<T> {
  public T data;
  public Link<T> next;
  public LinkedList<T> followers = new LinkedList<>(); // list of words that follow
  
  public Link(T data) {
    this.data = data;
  }
  
  public void display() {
    System.out.print(data + " ");
  }
}
