public class Main {
  public static void main(String[] args) {
    Agent agent = new Agent(3, 4);
    Agent agent2 = new Agent(4, 2);
    Agent agent3 = new Agent(15, 2);

    TurnManager turnManager = new TurnManager(new Agent[]{agent, agent2, agent3}); // ids 0, 1, 2
    
    
    
      
    
    int currentRound = turnManager.getCurrentRound(); // Get the current round


    MazeManager mazeManager = new MazeManager(18, 6, 5, 5, new Agent[]{agent, agent2, agent3});
    
    mazeManager.generateMaze(); // Generate the maze

    mazeManager.printMazeSnapshot(); // Print the maze snapshot
    System.out.println();

    Agent currentAgent;
    currentAgent = TurnManager.getCurrentAgent(); // Get the first agent in the queue
    currentAgent.move("UP"); // Move the current agent up
    currentAgent = TurnManager.getCurrentAgent(); // Get the next agent in the queue
    currentAgent.move("DOWN"); // Move the current agent down
    currentAgent = TurnManager.getCurrentAgent(); // Get the next agent in the queue

    mazeManager.rotateCorridor(2); // Rotate the corridor at row 2
    mazeManager.printMazeSnapshot(); // Print the maze snapshot after rotation
    System.out.println(agent3.getCurrentX() + " " + agent3.getCurrentY()); // Print the agent's position after rotation
    System.out.println();
    mazeManager.rotateCorridor(2); // Rotate the corridor at row 2
    mazeManager.printMazeSnapshot(); // Print the maze snapshot after rotation
    System.out.println(agent3.getCurrentX() + " " + agent3.getCurrentY()); // Print the agent's position after rotation




  }
}
