public class Agent {

  private static int idCounter = 0; //In order to keep the id number for each agent
  private int id;

  private int currentX, currentY;
  public Stack<String> moveHistory; //Each entry as x, y coordinates
  private boolean hasReachedGoal = false;
  private int totalMoves = 0;
  private int backTracks = 0;
  private boolean hasPowerUp = false;
  private char agentSymbol = 'A'; // Symbol representing the agent in the maze


  // Constructor for Agent class
  public Agent(int startX, int startY) {
    this.id = idCounter++; // Increment the idCounter for the next agent
    this.currentX = startX;
    this.currentY = startY;
    this.moveHistory = new Stack<>(); // Initialize the move history stack
  }

  public int getId() {
    return id;
  }

  public void setCurrentX(int x) {
    this.currentX = x;
  }

  public void setCurrentY(int y) {
    this.currentY = y;
  }

  public int getCurrentX() {
    return currentX;
  }

  public int getCurrentY() {
    return currentY;
  }

  public char getAgentSymbol() {
    return agentSymbol;
  }

  public void move(String direction) {
    int oldX = currentX;
    int oldY = currentY;



    switch (direction) {
      case "UP":
        currentY++;
        MazeManager.updateAgentLocation(this, oldX, oldY); // Update the maze with the new position
        break;
      case "DOWN":
        currentY--;
        MazeManager.updateAgentLocation(this, oldX, oldY); // Update the maze with the new position
        break;
      case "LEFT":
        currentX--;
        MazeManager.updateAgentLocation(this, oldX, oldY); // Update the maze with the new position
        break;
      case "RIGHT":
        currentX++;
        MazeManager.updateAgentLocation(this, oldX, oldY); // Update the maze with the new position
        break;
      default:
        System.out.println("Invalid move direction: " + direction);
        return; 
    }
    TurnManager.advanceTurn(); // Call advanceTurn() after each move  
    recordMove(currentX, currentY);
  }

  public void backtrack(){
    if(!moveHistory.isEmpty()){
      String prevMove = moveHistory.peek();
      String[] coordinates = prevMove.split(","); // Split the coordinates into x and y
      currentX = Integer.parseInt(coordinates[0]);
      currentY = Integer.parseInt(coordinates[1]);
      moveHistory.pop();
      TurnManager.advanceTurn(); // Call advanceTurn() after each move 
      backTracks++;
    } 
    else {
      System.out.println("No moves to backtrack to.");
    }
  }

  public void applyPowerUp() {
    hasPowerUp = true; 
  }

  private void recordMove(int x, int y){
    moveHistory.push(x + "," + y); 
    totalMoves++; 
  }

  public String getMoveHistoryAsString() {
    Stack<String> tempStack = new Stack<>();
    StringBuilder history = new StringBuilder();
    String move;

    while (!moveHistory.isEmpty()) {
      move = moveHistory.peek();
      tempStack.push(move); 
      moveHistory.pop(); 
    }

    while (!tempStack.isEmpty()) {
      move = tempStack.peek();
      history.append("Agent" + id + " " + move + "\n");
      moveHistory.push(move); 
      tempStack.pop(); 
    }

    return history.toString();
    
  }
}
