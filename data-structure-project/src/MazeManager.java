import java.util.Random;
import java.util.Stack;

/**
 * Labirent Yönetici Sınıfı
 * 
 * Bu sınıf, labirent oyununun temel mantığını ve labirent yapısını yönetir.
 * Labirentin oluşturulması, döndürülmesi ve ajanların hareketlerinin kontrolü bu sınıfta gerçekleşir.
 * 
 * Özellikler:
 * - Labirent oluşturma ve yönetme
 * - Satır döndürme mekanizması
 * - Ajan hareketlerini kontrol etme
 * - Güç artırıcı ve tuzak yönetimi
 */
public class MazeManager {

  private static MazeTile[][] grid;

  int width, height;
  int trapNum, powerUpNum;

  LinkedList<Agent> agents;

  CircularLinkedList<Integer> rotatingRows;
  CircularLinkedList<MazeTile> rowsTiles;

  Random rand = new Random();

  private boolean[][] visited;

  private int rotatingRow; // Dönen satırın indeksi

  private int rows;                         // Satır sayısı
  private int cols;                         // Sütun sayısı
  private int selectedRow;                  // Seçili satır
  private boolean isRotating;               // Döndürme durumu
  private int rotationDirection;            // Döndürme yönü
  private int rotationCount;                // Döndürme sayacı
  private static final int ROTATION_STEPS = 3; // Döndürme adım sayısı

  /**
   * MazeManager sınıfının yapıcı metodu.
   * @param width Satır sayısı
   * @param height Sütun sayısı
   * @param trapNum Tuzak sayısı
   * @param powerUpNum Güç artırıcı sayısı
   * @param a Ajan dizisi
   */
  public MazeManager(int width, int height, int trapNum, int powerUpNum, Agent[] a) {
    this.width = width;
    this.height = height;
    this.trapNum = trapNum;
    this.powerUpNum = powerUpNum;
    this.grid = new MazeTile[width][height];
    this.visited = new boolean[width][height];

    this.agents = new LinkedList<>();
    for (Agent agent : a) {
      agents.addLast(agent);
    }
    this.rotatingRows = new CircularLinkedList<>();
    this.rowsTiles = new CircularLinkedList<>();
    
    // Rastgele bir satır seç (1 ile height-2 arasında)
    this.rotatingRow = rand.nextInt(1, height-1);
    System.out.println("Selected rotating row: " + rotatingRow);
  }

  /**
   * Labirenti oluşturur ve başlangıç durumunu ayarlar.
   */
  public void generateMaze() {
    // Önce tüm labirenti duvarlarla doldur
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        grid[x][y] = new MazeTile(x, y, 'W');
        visited[x][y] = false;
      }
    }

    // Recursive Backtracking ile labirent oluştur
    Stack stack = new Stack();
    int startX = 1;
    int startY = 1;
    stack.push(new int[]{startX, startY});
    visited[startX][startY] = true;
    grid[startX][startY].setType('E');

    int[][] directions = {{0, 2}, {2, 0}, {0, -2}, {-2, 0}}; // Sağ, Aşağı, Sol, Yukarı

    while (!stack.isEmpty()) {
      int[] current = (int[]) stack.peek();
      int x = current[0];
      int y = current[1];

      // Rastgele sırayla yönleri karıştır
      shuffleArray(directions);

      boolean foundPath = false;
      for (int[] dir : directions) {
        int newX = x + dir[0];
        int newY = y + dir[1];

        if (isValidCell(newX, newY) && !visited[newX][newY]) {
          // Duvarı kaldır
          grid[x + dir[0]/2][y + dir[1]/2].setType('E');
          grid[newX][newY].setType('E');
          visited[newX][newY] = true;
          stack.push(new int[]{newX, newY});
          foundPath = true;
          break;
        }
      }

      if (!foundPath) {
        stack.pop();
      }
    }

    // Hedef noktasını yerleştir
    int goalX, goalY;
    do {
      goalX = rand.nextInt(1, width-1);
      goalY = rand.nextInt(1, height-1);
    } while (grid[goalX][goalY].getType() != 'E');
    grid[goalX][goalY].setType('G');

    // Tuzakları yerleştir
    placeSpecialTiles('T', trapNum);

    // Güç artırıcıları yerleştir
    placeSpecialTiles('P', powerUpNum);

    // Dönen koridorları belirle
    for (int y = 1; y < height-1; y += 2) {
      if (rand.nextInt(3) == 0) { // 1/3 olasılıkla koridor seç
        rotatingRows.addLast(y);
      }
    }

    // Ajanları güvenli başlangıç pozisyonlarına yerleştir
    placeAgents();
  }

  private void placeSpecialTiles(char type, int count) {
    int placed = 0;
    while (placed < count) {
      int x = rand.nextInt(1, width-1);
      int y = rand.nextInt(1, height-1);
      if (grid[x][y].getType() == 'E') {
        grid[x][y].setType(type);
        placed++;
      }
    }
  }

  private void placeAgents() {
    int size = agents.getSize();
    for (int i = 0; i < size; i++) {
      Agent a = agents.getByIndex(i);
      int agentX, agentY;
      do {
        agentX = rand.nextInt(1, width-1);
        agentY = rand.nextInt(1, height-1);
      } while (!isValidStartingPosition(agentX, agentY));
      
      a.setCurrentX(agentX);
      a.setCurrentY(agentY);
      grid[agentX][agentY].setHasAgent(true);
    }
  }

  private boolean isValidCell(int x, int y) {
    return x > 0 && x < width-1 && y > 0 && y < height-1;
  }

  private void shuffleArray(int[][] array) {
    for (int i = array.length - 1; i > 0; i--) {
      int j = rand.nextInt(i + 1);
      int[] temp = array[i];
      array[i] = array[j];
      array[j] = temp;
    }
  }

  private boolean isValidStartingPosition(int x, int y) {
    // Başlangıç pozisyonunun geçerli olup olmadığını kontrol et
    MazeTile tile = getTile(x, y);
    return tile != null && tile.isTraversable() && !tile.getHasAgent();
  }

  private void createRowCorridor(int rowId){
    for(int x=1; x<width-1; x++){
      MazeTile currentTile = getTile(x, rowId);
      rowsTiles.addLast(currentTile);  
      }
  }

  public void rotateCorridor(int rowId) {
    // Sadece seçili satır dönebilir
    if (rowId != rotatingRow) {
      return;
    }

    int size = agents.getSize();
    Agent a;
    int agentY, agentX;

    createRowCorridor(rowId);
    MazeTile lastTile = rowsTiles.removeLast();
    rowsTiles.addFirst(lastTile);

    for(int x=1; x<width-1; x++) {
      MazeTile currentTile = rowsTiles.removeFirst();
      grid[x][rowId] = currentTile;
    }

    // Rotate if any agent is in the corridor
    for(int i=0; i<size; i++) {
      a = agents.getByIndex(i);
      agentY = a.getCurrentY();
      agentX = a.getCurrentX();

      if(agentY == rowId && agentX < width-2) {
        a.setCurrentX(agentX+1);
      }
      else if(agentY == rowId && agentX == width-2) {
        a.setCurrentX(1);
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
    
  

  /**
   * Belirtilen konumdaki hücrenin tipini döndürür.
   * @param x X koordinatı
   * @param y Y koordinatı
   * @return Hücre tipi
   */
  public static MazeTile getTile(int x, int y) {
    return grid[x][y];
  }

  public static void updateAgentLocation(Agent a, int oldX, int oldY) {
    MazeTile oldTile = getTile(oldX, oldY);
    MazeTile newTile;

    int agentX = a.getCurrentX();
    int agentY = a.getCurrentY();

    newTile = getTile(agentX, agentY);

    oldTile.setHasAgent(false);
    newTile.setHasAgent(true);

    if (newTile.getType() == 'P' && !a.isPowerUpUsed()) {
      a.setHasPowerUp(true);
      newTile.setType('E');
      System.out.println("Agent " + a.getId() + " picked up a power-up!");
    }
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

  public int getHeight() {
    return height;
  }

  public boolean isGoalTile(int x, int y) {
    return getTile(x, y).getType() == 'G';
  }

  public boolean isTrapTile(int x, int y) {
    return getTile(x, y).getType() == 'T';
  }

  public boolean isPowerUpTile(int x, int y) {
    return getTile(x, y).getType() == 'P';
  }

  /**
   * Labirent ızgarasını döndürür.
   * @return Labirent ızgarası
   */
  public MazeTile[][] getGrid() {
    return grid;
  }

  /**
   * Seçili satır numarasını döndürür.
   * @return Seçili satır numarası
   */
  public int getRotatingRow() {
    return rotatingRow;
  }

  /**
   * Döndürme durumunu döndürür.
   * @return Döndürme durumu
   */
  public boolean isRotating() {
    return isRotating;
  }

  /**
   * Döndürme yönünü döndürür.
   * @return Döndürme yönü
   */
  public int getRotationDirection() {
    return rotationDirection;
  }

  /**
   * Döndürme sayacını döndürür.
   * @return Döndürme sayacı
   */
  public int getRotationCount() {
    return rotationCount;
  }

  /**
   * Belirtilen konumdan belirtilen yöne doğru hareketin geçerli olup olmadığını kontrol eder.
   * @param fromX Başlangıç X koordinatı
   * @param fromY Başlangıç Y koordinatı
   * @param direction Hareket yönü ("UP", "DOWN", "LEFT", "RIGHT")
   * @return Hareketin geçerliliği
   */
  public static boolean isValidMove(int fromX, int fromY, String direction) {
    int destinationX, destinationY;
    
    // Hedef koordinatları belirle
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
        return false; // Geçersiz yön
    }

    // Hedef konumun labirent sınırları içinde olup olmadığını kontrol et
    if (destinationX < 0 || destinationX >= grid.length || 
        destinationY < 0 || destinationY >= grid[0].length) {
      return false;
    }

    // Hedef konumdaki hücrenin geçilebilir olup olmadığını kontrol et
    MazeTile destinationTile = getTile(destinationX, destinationY);
    return destinationTile != null && destinationTile.isTraversable();
  }
}
