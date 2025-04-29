public class CircularLinkedList<T> {

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
  private Node<T> tail;
  private int size = 0;

  public CircularLinkedList() {
      head = null;
      tail = null;
      size = 0;
  }

  public int getSize() {
    return size;
  }

  public boolean isEmpty() {
    if (head == null) {
      return true;
    }
    else
      return false;
  }

  public void addFirst(T data) {
    Node<T> newNode = new Node<>(data);
    if (isEmpty()) {
        head = newNode;
        tail = newNode;
        newNode.next = head;
    } 
    else {
        newNode.next = head;
        head = newNode;
        tail.next = head;
    }
    size++;
  }

  public void addLast(T data) {
    Node<T> newNode = new Node<>(data);
    if (isEmpty()) {
      addFirst(data);
    } 
    else {
      tail.next = newNode;
      tail = newNode;
      tail.next = head;
      size++;
    }
  }

  public void insertAt(int index, T data) {
      if (index == 0) {
          addFirst(data);
      } else if (index == size) {
          addLast(data);
      } else {
          Node<T> newNode = new Node<>(data);
          Node<T> current = head;

          for (int i = 0; i < index - 1; i++) {
            current = current.next;
          }
          newNode.next = current.next;
          current.next = newNode;
          size++;
      }
  }

  public T removeFirst() {
    T temp = head.data;
      if (head == tail) {

        head = null;
        tail = null;
      } 
      else 
      {
        head = head.next;
        tail.next = head;
      }
      
    size--;
    return temp;
  }

  public T removeLast() {
      T temp;
      if (head == tail) {
        temp = removeFirst();
        return temp;
      } 
      else {
        Node<T> current = head;
        while (current.next != tail) {
            current = current.next;
        }
        temp = tail.data;
        current.next = head;
        tail = current;
        size--;
        return temp;
      }
  }

  public T removeAt(int position) {
    T temp;
      if (position == 0) {
        temp = removeFirst();
        return temp;
      } 
      else if (position == size - 1) {
        temp = removeLast();
        return temp;
      } 
      else {
        Node<T> current = head;
        for (int i = 0; i < position - 1; i++) {
            current = current.next;
        }
        temp = current.next.data;
        current.next = current.next.next;
        size--;
        return temp;
      }
  }

  public T getByIndex(int index) { 
      Node<T> current = head;
      for (int i = 0; i < index; i++) {
        current = current.next;
      }
      return current.data;
  }

  public void printElements() {
    Node<T> current = head;
    for (int i = 0; i < size; i++) {
      System.out.print(current.data + " ");
      current = current.next;
    }
  }
}
