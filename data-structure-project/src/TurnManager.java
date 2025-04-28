public class TurnManager {
  private static Queue<Agent> agentQueue;
  private static int currentRound;
  private static int turnNumber;

  public TurnManager(Agent[] agents) {
    agentQueue = new Queue<>();
    for (Agent agent : agents) {
      agentQueue.enqueue(agent);
    }
    currentRound = 1;
    turnNumber = 0;
  }

  public int getCurrentRound() {
    return currentRound;
  }

  public static void advanceTurn() {
    if(!agentQueue.isEmpty()){
      Agent currentAgent = getCurrentAgent();
      agentQueue.dequeue();
      agentQueue.enqueue(currentAgent);
      turnNumber++; // Increment turn number for each agent's turn

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
    if(queueSize == turnNumber){
      turnNumber = 0; // Reset turn number for the next round
      return true;
    } 
    else {
      return false;
    }

  }

  public void logTurnSummary(Agent a){

  }
  
}
