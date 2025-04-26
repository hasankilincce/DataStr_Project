public class Main {
  public static void main(String[] args) {
    LinkedList newList = new LinkedList();
    LinkedList newList2 = new LinkedList();
    newList.addFirst(0);
    newList.addLast(1);
    newList.addLast(2);
    newList.addLast(3);
    newList.addLast(4);
    newList.addLast(5);
    newList.insertAt(2,9); 
    newList.printElements();

  }
}
