import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class Game implements KeyListener {
    public static void main(String[] args) {
        Game game = new Game();
    }

    private GamePanel gamePanel;

    public Game() {
        showWelcomeScreen();
    }
  
    private void showWelcomeScreen() {
        JFrame welcomeFrame = new JFrame("Welcome to Battleship");
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setResizable(false);
        welcomeFrame.setSize(400, 450); 

        UIManager.put("OptionPane.background", new Color(0xFFFFFF)); 
        UIManager.put("Panel.background", new Color(0xFFFFFF)); 

      

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    

        JLabel titleLabel = new JLabel("BATTLESHIP");
        titleLabel.setFont(new Font("IMPACT", Font.BOLD, 50)); 
        titleLabel.setForeground(new Color(0x004aad)); 
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomePanel.add(Box.createVerticalStrut(10)); 
        welcomePanel.add(titleLabel);
        welcomePanel.add(Box.createVerticalStrut(5));

        JLabel imageLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(Game.class.getResource("/resources/images/BattleShipLogo.png"));
            if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                System.out.println("ImageIcon successfully loaded.");
            } else {
                System.out.println("ImageIcon could not be loaded.");
            }
    
            Image resizedImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(resizedImage));
    
        } catch (Exception e) {
            e.printStackTrace();
            imageLabel.setText("Image not available");
            welcomePanel.add(imageLabel);
        }
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(imageLabel);

        JLabel nameLabel = new JLabel("Kayla's Version");
        nameLabel.setFont(new Font("Britannic",Font.PLAIN,20)); 
        nameLabel.setForeground(new Color(0x004aad)); 
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomePanel.add(Box.createVerticalStrut(5)); 
        welcomePanel.add(nameLabel);
        welcomePanel.add(Box.createVerticalStrut(10));

       

        
        JButton startButton = new JButton("Start");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        startButton.setFont(new Font("Arial", Font.BOLD, 24)); 
        startButton.setPreferredSize(new Dimension(200, 60));  
        startButton.setBackground(new Color(0x02233a));  
        startButton.setForeground(Color.WHITE);  
        startButton.setOpaque(true); 
        startButton.setBorderPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                welcomeFrame.dispose();
                showDifficultySelection();
            }

            
        });

        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        welcomePanel.add(startButton);
        welcomePanel.add(Box.createVerticalStrut(15));

        JButton rulesButton = new JButton("Rules");
        rulesButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        rulesButton.setFont(new Font("Arial", Font.BOLD, 22));  
        rulesButton.setPreferredSize(new Dimension(200, 30)); 
        rulesButton.setBackground(new Color(0x02233a)); 
        rulesButton.setForeground(Color.WHITE); 
        rulesButton.setOpaque(true); 
        rulesButton.setBorderPainted(false);
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.put("OptionPane.background", new Color(0x02233a)); 
                UIManager.put("Panel.background", new Color(0x02233a)); 
                UIManager.put("OptionPane.messageForeground", Color.WHITE); 

               
                JOptionPane.showMessageDialog(welcomeFrame, 
                    " Battleship Game Rules:" + "\n" + "\n" +
                    " 1. Players take turns to guess the locations of each other's ships." + 
                    "\n" + 
                    " 2. Ships can be placed horizontally or vertically on the grid." + 
                    "\n" + 
                    " 3. The game ends when all ships of one player are sunk." + 
                    "\n" + 
                    " 4. Use the mouse to interact with the grid." + 
                    "\n" + "\n" + 
                    "Click 'OK' to return to the welcome screen",
                    "Game Rules", JOptionPane.PLAIN_MESSAGE);
                
                UIManager.put("OptionPane.background", null);
                UIManager.put("Panel.background", null);
                UIManager.put("OptionPane.messageForeground", null);
            }
        });
        welcomePanel.add(rulesButton);

        welcomeFrame.getContentPane().add(welcomePanel);
        welcomeFrame.setVisible(true);
    }

    private void showDifficultySelection() {
        JFrame difficultyFrame = new JFrame("Choose Difficulty");
        difficultyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        difficultyFrame.setResizable(false);
        difficultyFrame.setSize(400, 400);
    
        JPanel mainPanel = new JPanel(new BorderLayout());
    
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new BoxLayout(difficultyPanel, BoxLayout.Y_AXIS));
        difficultyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
      
        JLabel titleLabel = new JLabel("CHOOSE DIFFICULTY");
        titleLabel.setFont(new Font("IMPACT", Font.BOLD, 30));
        titleLabel.setForeground(new Color(0x004aad));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        difficultyPanel.add(Box.createVerticalStrut(30));
        difficultyPanel.add(titleLabel);
        difficultyPanel.add(Box.createVerticalStrut(20));
    
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel messageLabel = new JLabel("<html>Easy will make moves entirely randomly,<br>Hard will focus on areas where it finds ships.</html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setForeground(new Color(0x02233a));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 

        messagePanel.add(messageLabel);

        difficultyPanel.add(messagePanel);
        difficultyPanel.add(Box.createVerticalStrut(20));

    
     
        JButton easyButton = new JButton("Easy");
        easyButton.setFont(new Font("Arial", Font.BOLD, 24));
        easyButton.setPreferredSize(new Dimension(200, 60));
        easyButton.setBackground(new Color(0x02233a));
        easyButton.setForeground(Color.WHITE);
        easyButton.setOpaque(true);
        easyButton.setBorderPainted(false);
        easyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        easyButton.addActionListener(e -> {
            difficultyFrame.dispose();
            startGame(0); 
        });
    
        difficultyPanel.add(easyButton);
        difficultyPanel.add(Box.createVerticalStrut(15));
    
      
        JButton hardButton = new JButton("Hard");
        hardButton.setFont(new Font("Arial", Font.BOLD, 24));
        hardButton.setPreferredSize(new Dimension(200, 60));
        hardButton.setBackground(new Color(0x02233a));
        hardButton.setForeground(Color.WHITE);
        hardButton.setOpaque(true);
        hardButton.setBorderPainted(false);
        hardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        hardButton.addActionListener(e -> {
            difficultyFrame.dispose();
            startGame(1);
        });
    
        difficultyPanel.add(hardButton);
    
        
        mainPanel.add(difficultyPanel, BorderLayout.CENTER);
    
       
        difficultyFrame.getContentPane().add(mainPanel);
        difficultyFrame.setVisible(true);
    }
    
    
    private void startGame(int difficultyChoice) {
        System.out.println("Selected Difficulty: " + (difficultyChoice == 0 ? "Easy" : "Hard"));
    
        JFrame frame = new JFrame("Battleship");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    
        gamePanel = new GamePanel(difficultyChoice);
        frame.getContentPane().add(gamePanel);
    
        frame.addKeyListener(this);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void handleHit() {
        gamePanel.shipDestroyed();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gamePanel != null) {
            gamePanel.handleInput(e.getKeyCode());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    
}
