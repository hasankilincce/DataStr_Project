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
    if (!agentQueue.isEmpty()) {
      Agent currentAgent = getCurrentAgent();
      agentQueue.dequeue();
      agentQueue.enqueue(currentAgent);
      queueCounter++;

      if (allAgentsFinished()) {
        currentRound++;
        queueCounter = 0;
      }
    } else {
      currentRound++;
      queueCounter = 0;
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

  public int getRemainingAgents() {
    return agentQueue.size();
  }

  public void removeAgent(Agent agent) {
    Queue<Agent> tempQueue = new Queue<>();
    while (!agentQueue.isEmpty()) {
      Agent current = agentQueue.first();
      agentQueue.dequeue();
      if (current != agent) {
        tempQueue.enqueue(current);
      }
    }
    agentQueue = tempQueue;
  }

  public Agent[] getAllAgents() {
    Agent[] agents = new Agent[agentQueue.size()];
    Queue<Agent> tempQueue = new Queue<>();
    int index = 0;
    
    while (!agentQueue.isEmpty()) {
      Agent current = agentQueue.first();
      agentQueue.dequeue();
      agents[index++] = current;
      tempQueue.enqueue(current);
    }
    
    agentQueue = tempQueue;
    return agents;
  }
}
