import java.util.Random;

public class MazeManager {
  private MazeTile[][] grid;
  int width, height;
  int trapNum, powerUpNum;
  LinkedList<Agent> agents;
  CircularLinkedList<Integer> rotatingRows;

  public MazeManager(int width, int height, int trapNum, int powerUpNum, Agent[] a) {
    this.width = width;
    this.height = height;
    this.trapNum = trapNum;
    this.powerUpNum = powerUpNum;
    grid = new MazeTile[width][height];
    agents = new LinkedList<>();
    for (Agent agent : a) {
      agents.addLast(agent);
    }
    rotatingRows = new CircularLinkedList<>();
  }

  public void generateMaze() {
    Random rand = new Random();
    
    char type = 'E';
    int randomTrap;
    int randomPowerUp;
    int randomWall;
    int goalX = rand.nextInt(1, width-1); // Random goal position
    int goalY = rand.nextInt(1, height-1); // Random goal position
    
    for (int x = 0; x < width; x++) {
      for (int y = height-1; y >=0; y--) {

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

    
  }

  public void printMazeSnapshot(){
    System.out.println("Y axis");
    for (int y=height-1; y>=0; y--){
      for (int x=0; x<width; x++){
        System.out.printf("%-2s", grid[x][y].toString());
        if(x==width-1 && y==0){
          System.out.print("X axis");
        }
      }
      System.out.println();
    }
 
  }
}
