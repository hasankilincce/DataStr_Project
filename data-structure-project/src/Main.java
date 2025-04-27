public class Main {
  public static void main(String[] args) {
    LinkedList<Integer> newList = new LinkedList<>();
    Stack<Integer> newStack = new Stack<>();
    Queue<Integer> newQueue = new Queue<>();

    newList.addFirst(0);
    newList.addLast(1);
    newList.addLast(2);
    newList.addLast(3);
    newList.addLast(4);
    newList.addLast(5);
    newList.insertAt(2,9); 
    newList.printElements();


    newStack.push(0);
    newStack.push(5);
    newStack.pop();
    newStack.push(3);
    newStack.push(4);

    newQueue.enqueue(0);
    newQueue.enqueue(1);
    newQueue.enqueue(2);
    newQueue.enqueue(3);
    newQueue.enqueue(4);
    newQueue.dequeue();
    newQueue.dequeue();
    newQueue.enqueue(5);
    newQueue.enqueue(6);



  }
}
