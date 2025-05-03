/**
 * Labirent Oyunu Ajan Sınıfı
 * 
 * Bu sınıf, labirent içinde hareket eden ajanları temsil eder.
 * Her ajanın kendi konumu, hareket geçmişi ve özel yetenekleri vardır.
 * 
 * Özellikler:
 * - Benzersiz kimlik numarası
 * - Konum takibi
 * - Hareket geçmişi
 * - Güç artırıcı kullanımı
 * - İstatistik takibi
 */
public class Agent {

  private static int idCounter = 0; //In order to keep the id number for each agent
  private int id;

  private int currentX, currentY;
  public Stack<String> moveHistory; //Each entry as x, y coordinates
  private boolean hasReachedGoal = false;
  private int totalMoves;
  private int backTracks;
  private boolean hasPowerUp;
  private boolean powerUpUsed; // Power-up'ın kullanılıp kullanılmadığını takip etmek için
  private char agentSymbol = 'A'; // Symbol representing the agent in the maze


  /**
   * Ajan sınıfının yapıcı metodu.
   * @param startX Başlangıç X koordinatı
   * @param startY Başlangıç Y koordinatı
   */
  // Constructor for Agent class
  public Agent(int startX, int startY) {
    this.id = idCounter++; // Increment the idCounter for the next agent
    this.currentX = startX;
    this.currentY = startY;
    this.hasReachedGoal = false;
    this.totalMoves = 0;
    this.backTracks = 0;
    this.hasPowerUp = false;
    this.powerUpUsed = false;
    this.moveHistory = new Stack<>(); // Initialize the move history stack
  }

  /**
   * Ajanın kimlik numarasını döndürür.
   * @return Ajan ID'si
   */
  public int getId() {
    return id;
  }

  /**
   * Ajanın X koordinatını ayarlar.
   * @param x Yeni X koordinatı
   */
  public void setCurrentX(int x) {
    this.currentX = x;
  }

  /**
   * Ajanın Y koordinatını ayarlar.
   * @param y Yeni Y koordinatı
   */
  public void setCurrentY(int y) {
    this.currentY = y;
  }

  /**
   * Ajanın X koordinatını döndürür.
   * @return Mevcut X koordinatı
   */
  public int getCurrentX() {
    return currentX;
  }

  /**
   * Ajanın Y koordinatını döndürür.
   * @return Mevcut Y koordinatı
   */
  public int getCurrentY() {
    return currentY;
  }

  /**
   * Ajanın sembolünü döndürür.
   * @return Ajan sembolü
   */
  public char getAgentSymbol() {
    return agentSymbol;
  }

  /**
   * Ajanın güç artırıcı durumunu ayarlar.
   * @param hasPowerUp Güç artırıcı durumu
   */
  public void setHasPowerUp(boolean hasPowerUp) {
    this.hasPowerUp = hasPowerUp;
  }

  /**
   * Ajanın güç artırıcı durumunu döndürür.
   * @return Güç artırıcı durumu
   */
  public boolean getHasPowerUp() {
    return hasPowerUp;
  }

  /**
   * Ajanı belirtilen yönde hareket ettirir.
   * @param direction Hareket yönü ("UP", "DOWN", "LEFT", "RIGHT")
   */
  public void move(String direction) {
    if (hasPowerUp && !powerUpUsed) {
      // Power-up kullanılıyor
      powerUpUsed = true;
      hasPowerUp = false;
      System.out.println("Agent " + id + " used power-up!");
    }

    int oldX = currentX;
    int oldY = currentY;
    boolean isValidMove = true;

    // Sadece bir birim hareket et
    switch (direction) {
      case "UP":
        if(!MazeManager.isValidMove(currentX, currentY, direction)){
          isValidMove = false;
          break;
        }
        currentY++;
        break;
      case "DOWN":
        if(!MazeManager.isValidMove(currentX, currentY, direction)){
          isValidMove = false;
          break;
        }
        currentY--;
        break;
      case "LEFT":
        if(!MazeManager.isValidMove(currentX, currentY, direction)){
          isValidMove = false;
          break;
        }
        currentX--;
        break;
      case "RIGHT":
        if(!MazeManager.isValidMove(currentX, currentY, direction)){
          isValidMove = false;
          break;
        }
        currentX++;
        break;
      default:
        System.out.println("Invalid move direction: " + direction);
        return; 
    }

    if(isValidMove){
      // Hareket geçerliyse konumu güncelle
      MazeManager.updateAgentLocation(this, oldX, oldY);
      recordMove(currentX, currentY);
    }
    else{
      System.out.println("Invalid move. Agent " + id + " cannot move " + direction);
    }
  }

  /**
   * Ajanı bir önceki konumuna geri döndürür.
   * Geri adım sayısını artırır.
   */
  public void backtrack() {
    if(!moveHistory.isEmpty()){
      String prevMove = moveHistory.peek();
      String[] coordinates = prevMove.split(",");
      currentX = Integer.parseInt(coordinates[0]);
      currentY = Integer.parseInt(coordinates[1]);
      moveHistory.pop();
      backTracks++;
    } 
    else {
      System.out.println("No moves to backtrack to.");
    }
  }

  /**
   * Ajanın güç artırıcı kullanmasını sağlar.
   * Güç artırıcıyı aktif eder.
   */
  public void applyPowerUp() {
    if (!powerUpUsed) {
      hasPowerUp = true;
      System.out.println("Agent " + id + " picked up a power-up!");
    }
  }

  /**
   * Ajanın hareketini kaydeder.
   * @param x X koordinatı
   * @param y Y koordinatı
   */
  private void recordMove(int x, int y){
    moveHistory.push(x + "," + y); 
    totalMoves++; 
  }

  /**
   * Ajanın hareket geçmişini string olarak döndürür.
   * @return Hareket geçmişi
   */
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

  /**
   * Ajanın toplam hamle sayısını döndürür.
   * @return Toplam hamle sayısı
   */
  public int getMoveCount() {
    return totalMoves;
  }

  /**
   * Ajanın geri adım sayısını döndürür.
   * @return Geri adım sayısı
   */
  public int getBacktrackCount() {
    return backTracks;
  }

  /**
   * Ajanın güç artırıcı sayısını döndürür.
   * @return Güç artırıcı sayısı
   */
  public int getPowerUpCount() {
    return hasPowerUp ? 1 : 0;
  }

  /**
   * Ajanın güç artırıcı kullanıp kullanmadığını döndürür.
   * @return Güç artırıcı kullanım durumu
   */
  public boolean isPowerUpUsed() {
    return powerUpUsed;
  }
}
