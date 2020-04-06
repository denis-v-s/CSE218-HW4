package linkedList;

public class ListIterator<T> {
  private Link<T> current;
  private Link<T> previous;
  private LinkedList<T> list;
  
  public ListIterator(LinkedList<T> list) {
    this.list = list;
    reset();
  }
  
  public void reset() {
    current = list.getFirst();
    previous = null;
  }
  
  public boolean atEnd() {
    return current.next == null;
  }
  
  public void nextLink() {
    previous = current;
    current = current.next;
  }
  
  public Link<T> getCurrent() {
    return current;
  }
  
  public Link<T> insertAfter(T data) {
    Link<T> newLink = new Link<T>(data);
    if (list.isEmpty()) {
      list.setFirst(newLink);
      current = newLink;
    }
    else {
      newLink.next = current.next;
      current.next = newLink;
      nextLink();
    }
    
    
    this.list.setSize(this.list.getSize() + 1);
    return newLink;
  }
  
  public void insertBefore(T data) {
    Link<T> newLink = new Link<T>(data);
    if (previous == null) { // if first item
      newLink.next = list.getFirst();
      list.setFirst(newLink);
      reset();
    }
    else {
      newLink.next = previous.next;
      previous.next = newLink;
      current = newLink;
    }
    this.list.setSize(this.list.getSize() + 1);
  }
  
  // go through the list and return the link associated with queried data
  // return null if not found;
  public Link<T> getLinkReference(T data) {
    Link<T> cur = list.getFirst();
    while (cur != null) {
      if (cur.data.equals(data)) {
        return cur;
      }
      cur = cur.next;
    }
    
    return null;
  }
  
  public boolean contains(T data) {
    Link<T> cur = list.getFirst();
    while (cur != null) {
      if (cur.data.equals(data)) {
        return true;
      }
      cur = cur.next;
    }
    return false;
  }
    
  public T deleteCurrent() {
    T value = current.data;
    if (previous == null) {
      list.setFirst(current);
      reset();
    }
    else {
      previous.next = current.next;
      if (atEnd()) {
        reset();
      }
      else {
        current = current.next;
      }
    }
    
    this.list.setSize(this.list.getSize() - 1);
    return value;
  }
  
  public T getRandomLinkValue(Link<T> node) {
    ListIterator<T> iter = node.followers.getIterator();
    iter.reset();
    
    int randomIndex = (int) (Math.random() * node.followers.getSize());
    for (int i = 0; i < randomIndex; i++) {
      iter.nextLink();
    }
    
    return iter.getCurrent().data;
  }
}
