import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

/**
 * Labirent Oyunu Grafiksel Arayüz Sınıfı
 * 
 * Bu sınıf, labirent oyununun grafiksel kullanıcı arayüzünü oluşturur ve yönetir.
 * Oyunun görsel temsilini, kontrol butonlarını ve bilgi panelini içerir.
 * 
 * Özellikler:
 * - Labirentin görsel temsili
 * - Oyun kontrol butonları (Başlat, Duraklat)
 * - Oyun bilgilerini gösteren panel
 * - Otomatik güncelleme ve animasyon
 */
public class GameGUI extends JFrame {
    private GameController gameController;    // Oyun kontrolcüsü
    private JPanel mazePanel;                 // Labirent paneli
    private JPanel controlPanel;              // Kontrol paneli
    private JPanel infoPanel;                 // Bilgi paneli
    private JButton startButton;              // Başlat butonu
    private JButton pauseButton;              // Duraklat butonu
    private Timer gameTimer;                  // Oyun zamanlayıcısı
    private boolean isPaused = false;         // Duraklatma durumu
    private static final int CELL_SIZE = 30;  // Hücre boyutu
    private static final int UPDATE_INTERVAL = 500;  // Güncelleme aralığı (500ms)
    private static final int SUCCESS_DELAY = 1000;   // Başarılı hamle bekleme süresi (1s)
    private static final int FAIL_DELAY = 200;       // Başarısız hamle bekleme süresi (200ms)

    /**
     * GameGUI sınıfının yapıcı metodu.
     * Arayüz bileşenlerini başlatır ve düzenler.
     */
    public GameGUI() {
        setTitle("Maze Simulation Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        initializeComponents();
        
        // Create game controller
        gameController = new GameController();
        gameController.initializeGame();

        // Setup layout
        setupLayout();
        
        // Setup timer
        setupTimer();

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Arayüz bileşenlerini başlatır.
     * Labirent paneli, kontrol butonları ve bilgi panelini oluşturur.
     */
    private void initializeComponents() {
        mazePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMaze(g);
            }
        };
        mazePanel.setPreferredSize(new Dimension(600, 600));
        mazePanel.setBackground(Color.WHITE);

        controlPanel = new JPanel();
        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        // Add action listeners
        startButton.addActionListener(e -> startGame());
        pauseButton.addActionListener(e -> pauseGame());
    }

    /**
     * Arayüz düzenini oluşturur.
     * Panelleri ve bileşenleri yerleştirir.
     */
    private void setupLayout() {
        // Add maze panel
        add(mazePanel, BorderLayout.CENTER);

        // Setup control panel
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        add(controlPanel, BorderLayout.SOUTH);

        // Setup info panel
        JLabel turnLabel = new JLabel("Turn: 0");
        JLabel agentsLabel = new JLabel("Remaining Agents: 0");
        infoPanel.add(turnLabel);
        infoPanel.add(agentsLabel);
        add(infoPanel, BorderLayout.EAST);
    }

    /**
     * Oyun zamanlayıcısını ayarlar.
     * Belirli aralıklarla oyunu günceller ve arayüzü yeniler.
     */
    private void setupTimer() {
        gameTimer = new Timer(UPDATE_INTERVAL, e -> {
            if (!isPaused) {
                // Ajanın önceki konumunu kaydet
                Agent currentAgent = gameController.getCurrentAgent();
                int oldX = currentAgent.getCurrentX();
                int oldY = currentAgent.getCurrentY();

                // Oyunu ilerlet
                gameController.runSimulation();
                updateInfoPanel();
                mazePanel.repaint();

                // Eğer ajan hareket ettiyse daha uzun bekle
                if (currentAgent.getCurrentX() != oldX || currentAgent.getCurrentY() != oldY) {
                    gameTimer.setDelay(SUCCESS_DELAY);
                } else {
                    gameTimer.setDelay(FAIL_DELAY);
                }
                
                if (gameController.getRemainingAgents() == 0) {
                    gameTimer.stop();
                    startButton.setEnabled(false);
                    pauseButton.setEnabled(false);
                }
            }
        });
    }

    /**
     * Labirenti çizer.
     * Her hücreyi ve ajanları görsel olarak temsil eder.
     * @param g Çizim için kullanılacak grafik nesnesi
     */
    private void drawMaze(Graphics g) {
        MazeTile[][] grid = gameController.getMazeGrid();
        if (grid == null) return;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int x = j * CELL_SIZE;
                int y = i * CELL_SIZE;
                
                // Draw cell background
                g.setColor(getCellColor(grid[i][j]));
                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                
                // Draw cell border
                g.setColor(Color.BLACK);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);

                // Draw agent if present
                if (grid[i][j] != null && grid[i][j].getHasAgent()) {
                    g.setColor(Color.BLUE);
                    g.fillOval(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                    g.setColor(Color.WHITE);
                    g.drawOval(x + 5, y + 5, CELL_SIZE - 10, CELL_SIZE - 10);
                }
            }
        }
    }

    /**
     * Hücre tipine göre renk belirler.
     * @param tile Hücre nesnesi
     * @return Hücre rengi
     */
    private Color getCellColor(MazeTile tile) {
        if (tile == null) return Color.WHITE;
        
        switch (tile.getType()) {
            case 'W': return Color.DARK_GRAY;  // Wall
            case 'T': return Color.RED;        // Trap
            case 'P': return Color.GREEN;      // Power-up
            case 'G': return Color.YELLOW;     // Goal
            default: return Color.WHITE;       // Empty
        }
    }

    /**
     * Bilgi panelini günceller.
     * Tur sayısı ve kalan ajan sayısını gösterir.
     */
    private void updateInfoPanel() {
        Component[] components = infoPanel.getComponents();
        ((JLabel)components[0]).setText("Turn: " + gameController.getCurrentTurn());
        ((JLabel)components[1]).setText("Remaining Agents: " + gameController.getRemainingAgents());
    }

    /**
     * Oyunu başlatır.
     * Zamanlayıcıyı başlatır ve butonları günceller.
     */
    private void startGame() {
        if (!gameTimer.isRunning()) {
            gameTimer.start();
        }
        isPaused = false;
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
    }

    /**
     * Oyunu duraklatır veya devam ettirir.
     * Duraklatma durumunu değiştirir ve buton metnini günceller.
     */
    private void pauseGame() {
        isPaused = !isPaused;
        pauseButton.setText(isPaused ? "Resume" : "Pause");
    }

    /**
     * Programın giriş noktası.
     * Grafiksel arayüzü başlatır.
     * @param args Komut satırı argümanları
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameGUI gui = new GameGUI();
            gui.setVisible(true);
        });
    }
} 