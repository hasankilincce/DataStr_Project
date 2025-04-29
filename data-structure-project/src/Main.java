public class Main {
  public static void main(String[] args) {
    Agent agent = new Agent(0, 0);
    Agent agent2 = new Agent(1, 1);
    Agent agent3 = new Agent(2, 2);

    TurnManager turnManager = new TurnManager(new Agent[]{agent, agent2, agent3}); // ids 0, 1, 2
    
    Agent currentAgent;
    currentAgent = TurnManager.getCurrentAgent(); // Get the first agent in the queue
    currentAgent.move("UP"); // Move the current agent up
    currentAgent = TurnManager.getCurrentAgent(); // Get the next agent in the queue
    currentAgent.move("DOWN"); // Move the current agent down
    currentAgent = TurnManager.getCurrentAgent(); // Get the next agent in the queue
    
      
    
    int currentRound = turnManager.getCurrentRound(); // Get the current round
    System.out.println("Current Round: " + currentRound); // Print the current round
    System.out.println("Current Agent: " + currentAgent.getId()); // Print the current agent's position

    MazeManager mazeManager = new MazeManager(6, 6, 5, 5, new Agent[]{agent, agent2, agent3});
    mazeManager.generateMaze(); // Generate the maze
    mazeManager.printMazeSnapshot(); // Print the maze snapshot



  }
}
