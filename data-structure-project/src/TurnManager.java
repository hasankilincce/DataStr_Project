public class TurnManager {
  private static Queue<Agent> agentQueue;
  private static int currentRound;
  private static int queueCounter;

  public TurnManager(Agent[] agents) {
    agentQueue = new Queue<>();
    for (Agent agent : agents) {
      agentQueue.enqueue(agent);
    }
    currentRound = 1;
    queueCounter = 0;
  }

  public int getCurrentRound() {
    return currentRound;
  }

  public static void advanceTurn() {
    if(!agentQueue.isEmpty()){
      Agent currentAgent = getCurrentAgent();
      agentQueue.dequeue();
      agentQueue.enqueue(currentAgent);
      queueCounter++; // Increment queueCounter for each agent's turn

      if(allAgentsFinished()){
        currentRound++; // Increment when Queue is returned to original state
      }
    } 
  }

  public static Agent getCurrentAgent() {
    return agentQueue.first();
  }

  private static boolean allAgentsFinished(){
    int queueSize = agentQueue.size();
    if(queueSize == queueCounter){
      queueCounter = 0; // Reset turn number for the next round
      return true;
    } 
    else {
      return false;
    }

  }

  public void logTurnSummary(Agent a){

  }
  
}
