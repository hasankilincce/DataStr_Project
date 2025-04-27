public class CircularLinkedList {

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
  Node tail;
  int size=0;

  public CircularLinkedList() {
    head = null;
    tail = null;
    size = 0;
  }

  public boolean isEmpty() {
    if (head == null) {
      return true;
    } else
      return false;
  }

  public void addFirst(int data) {
    Node newNode = new Node(data);
    if (isEmpty()) {
        head = newNode;
        tail = newNode;
        newNode.next = head;
    } else {
        newNode.next = head;
        head = newNode;
        tail.next = head;
    }
    size++;
  }

  public void addLast(int data) {
    Node newNode = new Node(data);
    if (isEmpty()) {
        addFirst(data);
    } else {
        tail.next = newNode;
        tail = newNode;
        tail.next = head;
        size++;
    }
  }

  public void insertAt(int index, int data) {

    if (index == 0) {
        addFirst(data);
    } else if (index == size) {
        addLast(data);
    } else {
        Node newNode = new Node(data);
        Node current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }
  }

  public void removeFirst() {
    
    if (head == tail) {
      head = null;
      tail = null;
    } 
    else {
      head = head.next;
      tail.next = head;
    }
    size--;
  }

  public void removeLast() {

    if (head == tail) {
      removeFirst();
    } 
    else {
      Node current = head;
      while (current.next != tail) {
        current = current.next;
      }
      current.next = head;
      tail = current;
      size--;
    }

  }

  public void removeAt(int position) {
    
    if (position == 0) {
        removeFirst();
    } else if (position == size - 1) {
        removeLast();
    } else {
        Node current = head;
        for (int i = 0; i < position - 1; i++) {
            current = current.next;
        }
        current.next = current.next.next;
        size--;
    }
  }

  public void printElements() {
    Node current = head;
    for (int i = 0; i < size; i++) {
      System.out.print(current.data + " ");
      current = current.next;
    }
  }

}
