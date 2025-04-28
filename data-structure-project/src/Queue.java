public class Queue<T> {
  private static class Node<T> {
      T data;
      Node<T> next;

      //constructor for Node
      public Node(T data) {
          this.data = data;
          this.next = null;
      }
  }

  private Node<T> head;
  private int size;


  public Queue() {
      head = null;
      size = 0;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    if (head == null) {
      return true;
    } 
    else
      return false;
  }

  public void enqueue(T data) {
    Node<T> newNode = new Node<>(data);
    Node<T> current = head;

    if (isEmpty()) {
      newNode.next = head;
      head = newNode;
      size++;
      return;
    }

    //stops at last element
    while (current.next != null) {
      current = current.next;
    }

    current.next = newNode;
    size++;
  }

  public void dequeue() {
    if (!isEmpty()) {
      head = head.next;
      size--;
    }
  }

  public T first() {
    if (!isEmpty()) {
      return head.data;
    } 
    else {
      System.out.println("Queue is empty, cannot peek element.");
      return null;
    }
  }

  public void printElements() {
    Node<T> current = head;
    for (int i = 0; i < size; i++) {
      System.out.print(current.data + " ");
      current = current.next;
    }
  }
}
