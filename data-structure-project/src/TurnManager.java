/**
 * Tur Yönetici Sınıfı
 * 
 * Bu sınıf, oyun içindeki tur sistemini yönetir.
 * Ajanların sırasını ve tur sayısını takip eder.
 * 
 * Özellikler:
 * - Tur sayısı takibi
 * - Ajan sırası yönetimi
 * - Dairesel kuyruk yapısı
 */
public class TurnManager {
  private CircularLinkedList<Agent> agentQueue;
  private int queueCounter;
  private int currentRound;
  private int turnCount;                    // Tur sayısı
  private static final int MAX_TURNS = 100; // Maksimum tur sayısı

  /**
   * TurnManager sınıfının yapıcı metodu.
   * @param agents Ajan dizisi
   */
  public TurnManager(Agent[] agents) {
    this.agentQueue = new CircularLinkedList<>();
    this.queueCounter = 0;
    this.currentRound = 0;
    
    // Ajanları kuyruğa ekle
    for (Agent agent : agents) {
      agentQueue.addLast(agent);
    }
  }

  /**
   * Tur sayısını döndürür.
   * @return Tur sayısı
   */
  public int getTurnCount() {
    return turnCount;
  }

  /**
   * Tur sayısını artırır.
   */
  public void advanceTurn() {
    if (!agentQueue.isEmpty()) {
      queueCounter++;
      if (queueCounter >= agentQueue.getSize()) {
        queueCounter = 0;
        currentRound++;
      }
    }
    turnCount++;
  }

  /**
   * Maksimum tur sayısına ulaşılıp ulaşılmadığını kontrol eder.
   * @return Maksimum tur kontrolü
   */
  public boolean isMaxTurnsReached() {
    return turnCount >= MAX_TURNS;
  }

  /**
   * Sıradaki ajanı döndürür.
   * @return Sıradaki ajan
   */
  public Agent getCurrentAgent() {
    if (agentQueue.isEmpty()) {
      return null;
    }
    return agentQueue.getByIndex(queueCounter);
  }

  /**
   * Ajan kuyruğunu ilerletir.
   */
  public void advanceQueue() {
    agentQueue.rotate();
  }

  /**
   * Belirtilen ajanı kuyruktan çıkarır.
   * @param agent Çıkarılacak ajan
   */
  public void removeAgent(Agent agent) {
    int index = -1;
    for (int i = 0; i < agentQueue.getSize(); i++) {
      if (agentQueue.getByIndex(i).getId() == agent.getId()) {
        index = i;
        break;
      }
    }
    if (index != -1) {
      agentQueue.removeByIndex(index);
      if (queueCounter >= agentQueue.getSize()) {
        queueCounter = 0;
      }
    }
  }

  public int getCurrentRound() {
    return currentRound;
  }

  /**
   * Kalan ajan sayısını döndürür.
   * @return Kalan ajan sayısı
   */
  public int getRemainingAgents() {
    return agentQueue.getSize();
  }

  public Agent[] getAllAgents() {
    Agent[] agents = new Agent[agentQueue.getSize()];
    for (int i = 0; i < agentQueue.getSize(); i++) {
      agents[i] = agentQueue.getByIndex(i);
    }
    return agents;
  }

  public void logTurnSummary(Agent a){

  }
}
