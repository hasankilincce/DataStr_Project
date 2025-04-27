public class Stack<T> {
  private static class Node<T> {
    T data;
    Node<T> next;

    //constructor for Node
    public Node(T data) {
      this.data = data;
      this.next = null;
    }
  }

  Node<T> head;
  int size = 0;

  public Stack() {
    head = null;
    size = 0;
  }

  public boolean isEmpty() {
    if (head == null) {
      return true;
    } else
      return false;
  }
  public void push(T data) {
    Node<T> newNode = new Node<>(data);
    newNode.next = head;
    head = newNode;
    size++;
  }

  public void pop() {
    if(isEmpty()) {
      System.out.println("Stack is empty, cannot pop element.");
      return;
    }
    head = head.next;
    size--;
  }

  public T peek() {
    if(isEmpty()) {
      System.out.println("Stack is empty, cannot peek element.");
      return null;
    }
    return head.data;
  }

  public void printElements(){
    Node<T> current = head;
    for(int i=0; i<size; i++){
      System.out.print(current.data + " ");
      current = current.next;
    }
  }

    
  
}
