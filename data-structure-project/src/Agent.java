public class Agent {

  public static int idCounter = 0; //In order to keep the id number for each agent
  private int id;

  private int currentX, currentY;
  public Stack<String> moveHistory = new Stack<>(); //Each entry as x, y coordinates
  private boolean hasReachedGoal = false;
  private int totalMoves = 0;
  private int backTracks = 0;
  private boolean hasPowerUp = false;

  // Constructor for Agent class
  public Agent(int startX, int startY) {
    this.id = idCounter++; // Increment the idCounter for the next agent
    this.currentX = startX;
    this.currentY = startY;
  }

  public void move(String direction) {
    // Check if the move is valid and update the current position accordingly
    switch (direction) {
      case "UP":
        currentY++;
        break;
      case "DOWN":
        currentY--;
        break;
      case "LEFT":
        currentX--;
        break;
      case "RIGHT":
        currentX++;
        break;
      default:
        System.out.println("Invalid move direction: " + direction);
        return; 
    }  
    recordMove(currentX, currentY);
  }

  public void backtrack(){
    if(!moveHistory.isEmpty()){
      String prevMove = moveHistory.peek();
      String[] coordinates = prevMove.split(","); // Split the coordinates into x and y
      currentX = Integer.parseInt(coordinates[0]);
      currentY = Integer.parseInt(coordinates[1]);
      moveHistory.pop(); // Remove the last move from history
      backTracks++; // Increment the backtrack count
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
