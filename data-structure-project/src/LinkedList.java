public class LinkedList {

  public static class Node {
    int data;
    Node next;

    //constructor for Node
    public Node(int data) {
      this.data = data;
      this.next = null;
    }

  }

  //head of the linked list
  private Node head;

  //constructor for LinkedList
  public LinkedList() {
    head = null;
  }

  public boolean isEmpty(){
    if(head==null){
      return true;
    }
    else
      return false;
  }

  public void addFirst(int data){
    Node newNode = new Node(data);
    newNode.next = head;
    head = newNode;
  }

  public void addLast(int data){
    Node newNode = new Node(data);
    Node current = head;

    //stops at last element
    while(current.next != null){
      current = current.next;
    }

    current.next=newNode;
  }

  public void insertAt(int index, int data){
    Node newNode = new Node(data);
    Node current = head;

    for(int i=0; i<index-1; i++){
      current = current.next;
    }
    newNode.next = current.next;
    current.next = newNode;
  }

  public void removeFirst(){
    head = head.next;
  }

  public void removeLast(){
    Node current = head;
    while(current.next.next != null){
      current = current.next;
    }
    current.next = null;
  }

  public void removeAt(int index){
    Node current = head;
    for(int i=0; i<index-1; i++){
      current = current.next;
    }
    current.next = current.next.next;
  }

  public void printElements(){
    Node current = head;
    while(current.next != null){
      System.out.println(current.data);
      current = current.next;
    }
    System.out.println(current.data);
  }




}