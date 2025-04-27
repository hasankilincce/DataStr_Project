public class Main {
  public static void main(String[] args) {
    LinkedList newList = new LinkedList();
    Stack newStack = new Stack();
    newList.addFirst(0);
    newList.addLast(1);
    newList.addLast(2);
    newList.addLast(3);
    newList.addLast(4);
    newList.addLast(5);
    newList.insertAt(2,9); 


    newStack.push(0);
    newStack.push(5);
    newStack.pop();
    newStack.push(3);
    newStack.push(4);
    newStack.printElements();

  }
}
