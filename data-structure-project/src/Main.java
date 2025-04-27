public class Main {
  public static void main(String[] args) {
    Agent agent = new Agent(0, 0);
    agent.move("UP");
    agent.move("UP");
    agent.move("LEFT");
    agent.move("DOWN");
    agent.backtrack(); // Backtrack to the last position
    agent.move("RIGHT");

    Agent agent2 = new Agent(1, 1);
    agent2.move("UP");
    agent2.move("LEFT");
    agent2.move("DOWN");
    agent2.backtrack(); // Backtrack to the last position

    String str = agent.getMoveHistoryAsString();
    System.out.println(str); // Print the move history
    
    String str2 = agent2.getMoveHistoryAsString();
    System.out.println(str2); // Print the move history



  }
}
