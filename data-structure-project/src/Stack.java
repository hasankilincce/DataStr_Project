public class Stack {
  public static class Node {
    int data;
    Node next;

    //constructor for Node
    public Node(int data) {
      this.data = data;
      this.next = null;
    }
  }

  Node head;
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
  public void push(int data) {
    Node newNode = new Node(data);
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

  public void printElements(){
    Node current = head;
    for(int i=0; i<size; i++){
      System.out.print(current.data + " ");
      current = current.next;
    }
  }

    
  
}
