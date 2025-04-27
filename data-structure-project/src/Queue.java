public class Queue {
  private static class Node {
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

  public Queue() {
    head = null;
    size = 0;
  }

  public boolean isEmpty() {
    if (head == null) {
      return true;
    } else
      return false;
  }

  public void enqueue(int data){
    Node newNode = new Node(data);
    Node current = head;

    if(isEmpty()){
      newNode.next = head;
      head = newNode;
      size++;
      return;
    }

    //stops at last element
    while(current.next != null){
      current = current.next;
    }

    current.next=newNode;
    size++;
  }

  public void dequeue(){
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
