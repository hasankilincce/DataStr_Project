public class LinkedList<T> {

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
    int size = 0;

    public LinkedList() {
        head = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty(){
    if(head==null){
        return true;
    }
    else
      return false;
    }

    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        size++;
    }

    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        Node<T> current = head;

        if(isEmpty()) {
          addFirst(data);
          return;
        }

        //stops at last element
        while(current.next != null) {
            current= current.next;
        }

        current.next = newNode;
        size++;
    }

    public void insertAt(int index, T data) {
        Node<T> newNode = new Node<>(data);
        Node<T> current = head;

        for(int i = 0; i < index-1; i++) {
            current= current.next;
        }
        newNode.next= current.next;
        current.next= newNode;
        size++;
    }

    public T removeFirst() {
        T temp = head.data;  
        head = head.next;
        size--;
        return temp; 
    }

    public T removeLast() {
        Node<T> current = head;
        while(current.next.next != null) {
            current = current.next;
        }
        T temp = current.next.data; 
        current.next = null;
        size--;
        return temp;
    }

    public T removeAt(int index) {
        Node<T> current = head;
        for(int i = 0; i < index-1; i++) {
            current= current.next;
        }
        T temp = current.next.data;
        current.next= current.next.next;
        size--;
        return temp;
    }

    public T search(T data) {
        Node<T> current = head;
        for(int i = 0; i < size; i++) {
            if(current.data == data) {
                return current.data;
            }
            current = current.next;
        }
        return null; // not found
    }

    public void printElements() {
        Node<T> current = head;
        for(int i = 0; i < size; i++) {
            System.out.print(current.data + " ");
            current = current.next;
        }
    }
}