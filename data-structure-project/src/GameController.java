import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Labirent Oyunu Kontrol Sınıfı
 * 
 * Bu sınıf, labirent oyununun ana kontrol mantığını yönetir. Oyunun başlatılması,
 * ajanların hareketlerinin kontrolü, labirentin güncellenmesi ve oyun durumunun
 * takibi gibi temel işlevleri içerir.
 * 
 * Özellikler:
 * - Oyun başlatma ve başlangıç parametrelerini alma
 * - Ajanların sırayla hareket etmesini sağlama
 * - Labirentin belirli satırlarının dönmesini kontrol etme
 * - Oyun durumunu ve istatistiklerini takip etme
 * - Oyun olaylarını loglama
 */
public class GameController {
    private MazeManager mazeManager;      // Labirent yönetimi
    private TurnManager turnManager;      // Tur yönetimi
    private int maxTurns;                 // Maksimum tur sayısı
    private boolean gameOver;             // Oyun bitiş durumu
    private StringBuilder gameLog;        // Oyun logları
    private String logFileName;           // Log dosyası adı

    /**
     * GameController sınıfının yapıcı metodu.
     * Oyun başlangıç değerlerini ve log sistemini başlatır.
     */
    public GameController() {
        this.gameLog = new StringBuilder();
        this.logFileName = "game_simulation.log";
        this.gameOver = false;
    }

    /**
     * Oyunu başlatır ve gerekli başlangıç parametrelerini kullanıcıdan alır.
     * Parametreler:
     * - Labirent boyutları (genişlik ve yükseklik)
     * - Ajan sayısı
     * - Tuzak ve güç artırıcı sıklıkları
     * - Maksimum tur sayısı
     */
    public void initializeGame() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the Maze Simulation Game!");
        System.out.println("-----------------------------------");
        
        // Get maze dimensions
        System.out.print("Enter maze width (5-20): ");
        int width = getValidInput(scanner, 5, 20);
        System.out.print("Enter maze height (5-20): ");
        int height = getValidInput(scanner, 5, 20);
        
        // Get number of agents
        System.out.print("Enter number of agents (1-5): ");
        int numAgents = getValidInput(scanner, 1, 5);
        
        // Get trap and power-up frequencies
        System.out.print("Enter trap frequency (1-10): ");
        int trapFreq = getValidInput(scanner, 1, 10);
        System.out.print("Enter power-up frequency (1-10): ");
        int powerUpFreq = getValidInput(scanner, 1, 10);
        
        // Get maximum turns
        System.out.print("Enter maximum number of turns (50-500): ");
        this.maxTurns = getValidInput(scanner, 50, 500);
        
        // Create agents
        Agent[] agents = new Agent[numAgents];
        for (int i = 0; i < numAgents; i++) {
            agents[i] = new Agent(0, 0); // Initial position will be set by MazeManager
        }
        
        // Initialize managers
        this.mazeManager = new MazeManager(width, height, trapFreq, powerUpFreq, agents);
        this.turnManager = new TurnManager(agents);
        
        // Generate maze and place agents
        mazeManager.generateMaze();
        
        logGameEvent("Game initialized with " + numAgents + " agents in a " + width + "x" + height + " maze");
        logGameEvent("Trap frequency: " + trapFreq + ", Power-up frequency: " + powerUpFreq);
    }

    private int getValidInput(Scanner scanner, int min, int max) {
        int input;
        while (true) {
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    /**
     * Oyun simülasyonunu çalıştırır.
     * Her turda:
     * 1. Sıradaki ajanı alır
     * 2. Ajanın hareketini işler
     * 3. Başarılı hareketlerde labirenti günceller
     * 4. Oyun durumunu kontrol eder
     */
    public void runSimulation() {
        if (gameOver) {
            return;
        }

        // Her turda sadece bir ajanın hareketini işle
        Agent currentAgent = turnManager.getCurrentAgent();
        if (currentAgent == null) {
            gameOver = true;
            logGameEvent("No agents remaining in the queue.");
            return;
        }

        // Ajanın hareketini işle
        int oldX = currentAgent.getCurrentX();
        int oldY = currentAgent.getCurrentY();
        processAgentAction(currentAgent);
        
        // Sadece ajan başarılı bir hamle yaptıysa satırı döndür ve turu ilerlet
        if (currentAgent.getCurrentX() != oldX || currentAgent.getCurrentY() != oldY) {
            int rotatingRow = mazeManager.getRotatingRow();
            mazeManager.rotateCorridor(rotatingRow);
            logGameEvent("Rotating corridor at row " + rotatingRow);
            
            // Sırayı ilerlet
            turnManager.advanceTurn();
        }
        
        // Check if all agents have reached the goal
        if (turnManager.getRemainingAgents() == 0) {
            gameOver = true;
            logGameEvent("All agents have reached the goal!");
            printFinalStatistics();
            logGameSummaryToFile();
        }
        
        // Maksimum tur sayısına ulaşıldı mı kontrol et
        if (turnManager.getCurrentRound() >= maxTurns) {
            gameOver = true;
            logGameEvent("Maximum turns reached. Game over.");
            printFinalStatistics();
            logGameSummaryToFile();
        }
    }

    /**
     * Ajanın hareketini işler ve gerekli güncellemeleri yapar.
     * @param agent Hareket edecek ajan
     */
    private void processAgentAction(Agent agent) {
        // Get current position
        int currentX = agent.getCurrentX();
        int currentY = agent.getCurrentY();
        
        // Check tile effect before moving
        checkTileEffect(agent);
        
        // Determine next move (this would be replaced with actual agent AI/input)
        String direction = determineNextMove(agent);
        
        // Try to move the agent
        agent.move(direction);
        
        // Only update and print if the agent actually moved
        if (agent.getCurrentX() != currentX || agent.getCurrentY() != currentY) {
            // Update agent location in maze
            mazeManager.updateAgentLocation(agent, currentX, currentY);
            
            // Log the successful move
            logGameEvent("\nTurn " + turnManager.getCurrentRound() + " begins");
            logGameEvent("Agent " + agent.getId() + "'s turn");
            logGameEvent("Agent " + agent.getId() + " moved to position (" + 
                        agent.getCurrentX() + "," + agent.getCurrentY() + ")");
            
            // Print maze after successful move
            mazeManager.printMazeSnapshot();
            
            // Check if agent reached goal
            if (mazeManager.isGoalTile(agent.getCurrentX(), agent.getCurrentY())) {
                logGameEvent("Agent " + agent.getId() + " reached the goal!");
                turnManager.removeAgent(agent);
            }
        }
    }

    /**
     * Ajanın bir sonraki hamlesini belirler.
     * @param agent Hamle yapacak ajan
     * @return Hamle yönü ("UP", "DOWN", "LEFT", "RIGHT")
     */
    private String determineNextMove(Agent agent) {
        // This is a simple implementation - you might want to implement more sophisticated movement logic
        // For now, it just moves randomly
        String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
        return directions[(int) (Math.random() * 4)];
    }

    /**
     * Ajanın bulunduğu karenin etkisini kontrol eder.
     * Tuzak ve güç artırıcı etkilerini uygular.
     * @param agent Etkiyi alacak ajan
     */
    private void checkTileEffect(Agent agent) {
        int x = agent.getCurrentX();
        int y = agent.getCurrentY();
        
        if (mazeManager.isTrapTile(x, y)) {
            logGameEvent("Agent " + agent.getId() + " stepped on a trap!");
            // Force backtrack 2 steps
            for (int i = 0; i < 2; i++) {
                agent.backtrack();
            }
        } else if (mazeManager.isPowerUpTile(x, y)) {
            logGameEvent("Agent " + agent.getId() + " picked up a power-up!");
            agent.applyPowerUp();
        }
    }

    /**
     * Oyun sonu istatistiklerini yazdırır.
     * Her ajan için:
     * - Toplam hamle sayısı
     * - Geri adım sayısı
     * - Kullanılan güç artırıcı sayısı
     * - Hedefe ulaşma durumu
     */
    public void printFinalStatistics() {
        System.out.println("\n=== Final Statistics ===");
        System.out.println("Total turns played: " + turnManager.getCurrentRound());
        
        Agent[] allAgents = turnManager.getAllAgents();
        for (Agent agent : allAgents) {
            System.out.println("\nAgent " + agent.getId() + " Statistics:");
            System.out.println("Total moves: " + agent.getMoveCount());
            System.out.println("Backtracks: " + agent.getBacktrackCount());
            System.out.println("Power-ups used: " + agent.getPowerUpCount());
            System.out.println("Reached goal: " + (mazeManager.isGoalTile(agent.getCurrentX(), agent.getCurrentY()) ? "Yes" : "No"));
        }
    }

    /**
     * Oyun olaylarını loglar ve ekrana yazdırır.
     * @param event Loglanacak olay
     */
    private void logGameEvent(String event) {
        String logEntry = event + "\n";
        gameLog.append(logEntry);
        System.out.print(logEntry);
    }

    /**
     * Oyun loglarını dosyaya kaydeder.
     */
    public void logGameSummaryToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFileName))) {
            writer.write(gameLog.toString());
            System.out.println("\nGame log has been written to " + logFileName);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    /**
     * Labirent ızgarasını döndürür.
     * @return Labirent ızgarası
     */
    public MazeTile[][] getMazeGrid() {
        return mazeManager.getGrid();
    }

    /**
     * Mevcut tur sayısını döndürür.
     * @return Tur sayısı
     */
    public int getCurrentTurn() {
        return turnManager.getCurrentRound();
    }

    /**
     * Kalan ajan sayısını döndürür.
     * @return Kalan ajan sayısı
     */
    public int getRemainingAgents() {
        return turnManager.getRemainingAgents();
    }

    /**
     * Labirent yöneticisini döndürür.
     * @return MazeManager nesnesi
     */
    public MazeManager getMazeManager() {
        return mazeManager;
    }

    /**
     * Mevcut ajanı döndürür.
     * @return Sıradaki ajan
     */
    public Agent getCurrentAgent() {
        return turnManager.getCurrentAgent();
    }
} 