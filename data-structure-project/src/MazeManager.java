import java.util.Random;

public class MazeManager {

  private static MazeTile[][] grid;

  int width, height;
  int trapNum, powerUpNum;

  LinkedList<Agent> agents;

  CircularLinkedList<Integer> rotatingRows;
  CircularLinkedList<MazeTile> rowsTiles;

  Random rand = new Random();

  public MazeManager(int width, int height, int trapNum, int powerUpNum, Agent[] a) {
    this.width = width;
    this.height = height;
    this.trapNum = trapNum;
    this.powerUpNum = powerUpNum;
    this.grid = new MazeTile[width][height];


    this.agents = new LinkedList<>();
    for (Agent agent : a) {
      agents.addLast(agent);
    }
    this.rotatingRows = new CircularLinkedList<>();
    this.rowsTiles = new CircularLinkedList<>();
  }

  public void generateMaze() {
    
    int size = agents.getSize();
    Agent a;
    int agentY, agentX;

    char type = 'E';
    int randomTrap;
    int randomPowerUp;
    int randomWall;
    int goalX = rand.nextInt(1, width-1); // Random goal position
    int goalY = rand.nextInt(1, height-1); // Random goal position
    
    for (int x = 0; x < width; x++) {
      for (int y = height-1; y >=0; y--) {

        createRandomRowID(y); // Randomize the row id for the corridor
      
        randomTrap = rand.nextInt(1, 21-trapNum);// Random number between 1 and 15-trapNum for frequency
        randomPowerUp = rand.nextInt(1, 31-powerUpNum);// Random number between 1 and 15-powerUpNum for frequency
        randomWall = rand.nextInt(1, 4);

        if(x==0 || x== width-1 || y==0 || y== height-1){
          type = 'W'; //Walls on the edges
        }
        else if(randomTrap==2){
          type = 'T'; //Trap tiles
        }
        else if(randomPowerUp==2){
          type = 'P'; //Powerup tiles 
        }
        else if(randomWall==2){
          type='W'; //Wall tiles
        }
        else{
          type = 'E'; //Empty tiles in the middle
        }

        if(x==goalX && y==goalY){
          type = 'G'; //Goal tile
        }
        grid[x][y] = new MazeTile(x, y, type);
      }
    }

    if(rotatingRows.isEmpty()){
      rotatingRows.addLast(height/2); //the row id is indicated by y
    }
    
    // Set the agents in the maze at starting positions
    for(int i=0; i<size; i++){
      a = agents.getByIndex(i);
      agentY = a.getCurrentY();
      agentX = a.getCurrentX();

      MazeTile startingTile = getTile(agentX, agentY);
      startingTile.setHasAgent(true); // Set the tile to have an agent
    }
    
  }

  private void createRowCorridor(int rowId){
    for(int x=1; x<width-1; x++){
      MazeTile currentTile = getTile(x, rowId);
      rowsTiles.addLast(currentTile);  
      }
  }

  public void rotateCorridor(int rowId){
    int size = agents.getSize();
    Agent a;
    int agentY, agentX;

    createRowCorridor(rowId); // Create a corridor in the row id
    MazeTile lastTile = rowsTiles.removeLast();
    rowsTiles.addFirst(lastTile); // Rotate the corridor by moving the last tile to the front

    for(int x=1; x<width-1; x++){
      MazeTile currentTile = rowsTiles.removeFirst(); // Remove the first tile from the corridor
      grid[x][rowId] = currentTile; // Update the maze grid with the rotated tile
    }

    // Rotate if any agent is in the corridor
    for(int i=0; i<size; i++){
      a = agents.getByIndex(i);
      agentY = a.getCurrentY();
      agentX = a.getCurrentX();

      if(agentY == rowId && agentX < width-2){
        a.setCurrentX(agentX+1);
      }
      else if(agentY == rowId && agentX == width-2){
        a.setCurrentX(1); // Move the agent to the first column of the corridor
      }
    }
  }

  private void createRandomRowID(int y){ 
    int randomizeRowId;

    randomizeRowId = rand.nextInt(1, height/2-1); // Randomize the row id for the corridor
      if(2==randomizeRowId){
        rotatingRows.addLast(y); //the row id is indicated by y
      }
  }
    
  

  public boolean isValidMove(int fromX, int fromY, String direction){
    int destinationX, destinationY;
    switch (direction) {
      case "UP":
        destinationX = fromX;
        destinationY = fromY + 1;
        break;
      case "DOWN":
        destinationX = fromX;
        destinationY = fromY - 1;
        break;
      case "LEFT":
        destinationX = fromX - 1;
        destinationY = fromY;
        break;
      case "RIGHT":
        destinationX = fromX + 1;
        destinationY = fromY;
        break;
      default:
        return false; // Invalid direction
    }

    MazeTile destinationTile = getTile(destinationX, destinationY);
    return destinationTile.isTraversable();

    
  }

  public static MazeTile getTile(int x, int y) {
    return grid[x][y];
  }

  public static void updateAgentLocation(Agent a, int oldX, int oldY){
    MazeTile oldTile = getTile(oldX, oldY);
    MazeTile newTile;

    int agentX = a.getCurrentX();
    int agentY = a.getCurrentY();

    newTile = getTile(agentX, agentY); // Get the new tile where the agent is moving

    oldTile.setHasAgent(false); // Remove the agent from the old tile
    newTile.setHasAgent(true); // Set the agent in the new tile

    
    
    
    
  }

  public void printMazeSnapshot(){
    MazeTile currentTile;
    
    System.out.println("Y axis");
    for (int y=height-1; y>=0; y--){
      for (int x=0; x<width; x++){
        currentTile = getTile(x, y);
        System.out.printf("%-2s", currentTile.toString());
        if(x==width-1 && y==0){
          System.out.print("X axis");
        }
      }
      System.out.println();
    }
 
  }
}
