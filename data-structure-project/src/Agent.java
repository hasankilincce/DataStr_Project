public class Agent {

  private static int idCounter = 0; //In order to keep the id number for each agent
  private int id;

  private int currentX, currentY;
  public Stack<String> moveHistory; //Each entry as x, y coordinates
  private boolean hasReachedGoal = false;
  private int totalMoves;
  private int backTracks;
  private boolean hasPowerUp;
  private char agentSymbol = 'A'; // Symbol representing the agent in the maze


  // Constructor for Agent class
  public Agent(int startX, int startY) {
    this.id = idCounter++; // Increment the idCounter for the next agent
    this.currentX = startX;
    this.currentY = startY;
    this.hasReachedGoal = false;
    this.totalMoves = 0;
    this.backTracks = 0;
    this.hasPowerUp = false;
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

  public void setHasPowerUp(boolean hasPowerUp) {
    this.hasPowerUp = hasPowerUp;
  }

  public boolean getHasPowerUp() {
    return hasPowerUp;
  }

  public void move(String direction) {
    int oldX = currentX;
    int oldY = currentY;
    boolean isValidMove = true;


    switch (direction) {
      case "UP":
      if(!MazeManager.isValidMove(currentX, currentY, direction)){
        isValidMove = false;
        break;
      }
        currentY++;
        MazeManager.updateAgentLocation(this, oldX, oldY); // Update the maze with the new position
        break;
      case "DOWN":
      if(!MazeManager.isValidMove(currentX, currentY, direction)){
        isValidMove = false;
        break;
      }
        currentY--;
        MazeManager.updateAgentLocation(this, oldX, oldY); // Update the maze with the new position
        break;
      case "LEFT":
      if(!MazeManager.isValidMove(currentX, currentY, direction)){
        isValidMove = false;
        break;
      }
        currentX--;
        MazeManager.updateAgentLocation(this, oldX, oldY); // Update the maze with the new position
        break;
      case "RIGHT":
      if(!MazeManager.isValidMove(currentX, currentY, direction)){
        isValidMove = false;
        break;
      }
        currentX++;
        MazeManager.updateAgentLocation(this, oldX, oldY); // Update the maze with the new position
        break;
      default:
        System.out.println("Invalid move direction: " + direction);
        return; 
    }

    if(isValidMove){
      TurnManager.advanceTurn(); // Call advanceTurn() after each move  
      recordMove(currentX, currentY);
    }
    else{
      System.out.println("Invalid move. Agent " + id + " cannot move " + direction);
    }
    
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
