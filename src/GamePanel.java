import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {
    public enum GameState { PlacingShips, FiringShots, GameOver }

    private StatusPanel statusPanel;
    private SelectionGrid computer;
    private SelectionGrid player;
    private BattleshipAI aiController;
    private Ship placingShip;
    private Position tempPlacingPosition;
    private int placingShipIndex;
    private GameState gameState;

    private JLabel shipCountLabel; 
    private int shipsLeft = 5;     

    private JLabel opponentShipsLabel; 
    private int opponentShipsLeft = 5; 


    public static boolean debugModeActive;

    public GamePanel(int aiChoice) {
        setLayout(null); 
        setPreferredSize(new Dimension(600, 680)); 
        setBackground(new Color(0xc0c0c0));
    
        shipCountLabel = new JLabel("Your Ships Left: " + shipsLeft);
        shipCountLabel.setFont(new Font("Arial", Font.BOLD, 18));
        shipCountLabel.setForeground(new Color(0x004aad)); 
        shipCountLabel.setBounds(350, 20, 200, 30); 
        add(shipCountLabel);

    
        opponentShipsLabel = new JLabel("Opponent Ships Left: " + opponentShipsLeft);
        opponentShipsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        opponentShipsLabel.setForeground(new Color(0x800000)); 
        opponentShipsLabel.setBounds(350, 50, 200, 30); 
        add(opponentShipsLabel);

        computer = new SelectionGrid(25, 30, this);
        computer.setBackgroundColor(new Color(0x02233a)); 
        player = new SelectionGrid(25, computer.getHeight() + 50, this);
        player.setBackgroundColor(new Color(0x5ce1e6)); 

        addMouseListener(this);
        addMouseMotionListener(this);

        if (aiChoice == 0)
            aiController = new SimpleRandomAI(player);
        else
            aiController = new SmarterAI(player, aiChoice == 2, aiChoice == 2);
 
        statusPanel = new StatusPanel(new Position(320, computer.getHeight()), computer.getWidth(), 1);

        restart();
    }


    public void paint(Graphics g) {
        super.paint(g);
        computer.paint(g);
        player.paint(g);
    
        
        int computerX = computer.getPosition().x;
        int computerY = computer.getPosition().y;
        int playerX = player.getPosition().x;
        int playerY = player.getPosition().y;
    
        g.setColor(new Color(0x02233a));


        for (int i = 0; i < SelectionGrid.GRID_WIDTH; i++) {
            int x = computer.getPosition().x + (i * SelectionGrid.CELL_SIZE) + SelectionGrid.CELL_SIZE / 2;
            g.drawString(Integer.toString(i + 1), x, computer.getPosition().y - 5); // Draw above the grid
        }
    
        for (int i = 0; i < SelectionGrid.GRID_WIDTH; i++) {
            int x = player.getPosition().x + (i * SelectionGrid.CELL_SIZE) + SelectionGrid.CELL_SIZE / 2;
            g.drawString(Integer.toString(i + 1), x, player.getPosition().y - 5); // Draw above the grid
        }
        
        for (int i = 0; i < 10; i++) {
            int y = computerY + (i * SelectionGrid.CELL_SIZE) + SelectionGrid.CELL_SIZE / 2;
            g.drawString(Integer.toString(i + 1), computerX - 18, y);
        }
    
    
        for (int i = 0; i < 10; i++) {
            int y = playerY + (i * SelectionGrid.CELL_SIZE) + SelectionGrid.CELL_SIZE / 2;
            g.drawString(Integer.toString(i + 1), playerX - 18, y);
        }
    
        if (gameState == GameState.PlacingShips) {
            placingShip.paint(g);
        }
    
        statusPanel.paint(g);
    }
    

    private void updateOpponentShipsLeft() {
        opponentShipsLeft--;
        opponentShipsLabel.setText("Opponent Ships Left: " + opponentShipsLeft);

        if (opponentShipsLeft == 0) {
            JOptionPane.showMessageDialog(this, "You Win! All opponent ships destroyed!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateShipsLeft(int newCount) {
        shipsLeft = newCount;
        shipCountLabel.setText("Ships Left: " + shipsLeft);

        if (shipsLeft == 0) {
            JOptionPane.showMessageDialog(this, "Game Over! All your ships are destroyed!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void handleInput(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        } else if (keyCode == KeyEvent.VK_R) {
            restart();
        } else if (gameState == GameState.PlacingShips && keyCode == KeyEvent.VK_Z) {
            placingShip.toggleSideways();
            updateShipPlacement(tempPlacingPosition);
        } else if (keyCode == KeyEvent.VK_D) {
            debugModeActive = !debugModeActive;
        }
        repaint();
    }

    public void restart() {
        computer.reset();
        player.reset();

        player.setShowShips(true);
        aiController.reset();
        tempPlacingPosition = new Position(0, 0);
        placingShip = new Ship(new Position(0, 0),
                new Position(player.getPosition().x, player.getPosition().y),
                SelectionGrid.BOAT_SIZES[0], true);
        placingShipIndex = 0;
        updateShipPlacement(tempPlacingPosition);
        computer.populateShips();
        debugModeActive = false;
        statusPanel.reset();
        gameState = GameState.PlacingShips;

        shipsLeft = 5;
        opponentShipsLeft = 5;

        shipCountLabel.setText("Ships Left: " + shipsLeft);
        opponentShipsLabel.setText("Opponent Ships Left: " + opponentShipsLeft);
    }

    private void tryPlaceShip(Position mousePosition) {
        Position targetPosition = player.getPositionInGrid(mousePosition.x, mousePosition.y);
        updateShipPlacement(targetPosition);
        if (player.canPlaceShipAt(targetPosition.x, targetPosition.y,
                SelectionGrid.BOAT_SIZES[placingShipIndex], placingShip.isSideways())) {
            placeShip(targetPosition);
        }
    }

    private void placeShip(Position targetPosition) {
        placingShip.setShipPlacementColour(Ship.ShipPlacementColour.Placed);
        player.placeShip(placingShip, tempPlacingPosition.x, tempPlacingPosition.y);
        placingShipIndex++;
        if (placingShipIndex < SelectionGrid.BOAT_SIZES.length) {
            placingShip = new Ship(new Position(targetPosition.x, targetPosition.y),
                    new Position(player.getPosition().x + targetPosition.x * SelectionGrid.CELL_SIZE,
                            player.getPosition().y + targetPosition.y * SelectionGrid.CELL_SIZE),
                    SelectionGrid.BOAT_SIZES[placingShipIndex], true);
            updateShipPlacement(tempPlacingPosition);
        } else {
            gameState = GameState.FiringShots;
            statusPanel.setTopLine("Attack the Computer!");
            statusPanel.setBottomLine("Destroy all Ships to win!");
        }
    }

    private void tryFireAtComputer(Position mousePosition) {
        Position targetPosition = computer.getPositionInGrid(mousePosition.x, mousePosition.y);

        if (!computer.isPositionMarked(targetPosition)) {
            doPlayerTurn(targetPosition);

            if (!computer.areAllShipsDestroyed()) {
                doAITurn();
            }
        }
    }

    private void doPlayerTurn(Position targetPosition) {
        boolean hit = computer.markPosition(targetPosition);
        String hitMiss = hit ? "Hit" : "Missed";
        String destroyed = "";
        if (hit && computer.getMarkerAtPosition(targetPosition).getAssociatedShip().isDestroyed()) {
            destroyed = "(Destroyed)";
            updateOpponentShipsLeft();
        }
        statusPanel.setTopLine("Player " + hitMiss + " " + targetPosition + destroyed);
        if (computer.areAllShipsDestroyed()) {
            gameState = GameState.GameOver;
            statusPanel.showGameOver(true);
        }
    }

    private void doAITurn() {
        Position aiMove = aiController.selectMove();
        boolean hit = player.markPosition(aiMove);
        String hitMiss = hit ? "Hit" : "Missed";
        String destroyed = "";
        if (hit && player.getMarkerAtPosition(aiMove).getAssociatedShip().isDestroyed()) {
            destroyed = "(Destroyed)";
            updateShipsLeft(shipsLeft - 1);
        }
        statusPanel.setBottomLine("Computer " + hitMiss + " " + aiMove + destroyed);
        if (player.areAllShipsDestroyed()) {
            gameState = GameState.GameOver;
            statusPanel.showGameOver(false);
        }
    }

    public void shipDestroyed() {
    shipsLeft--;  
    shipCountLabel.setText("Ships Left: " + shipsLeft);  

    if (shipsLeft == 0) {
        JOptionPane.showMessageDialog(this, "Game Over! All your ships are destroyed!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        gameState = GameState.GameOver;  
    }
}


    private void tryMovePlacingShip(Position mousePosition) {
        if (player.isPositionInside(mousePosition)) {
            Position targetPos = player.getPositionInGrid(mousePosition.x, mousePosition.y);
            updateShipPlacement(targetPos);
        }
    }
    

    private void updateShipPlacement(Position targetPos) {
        if (placingShip.isSideways()) {
            targetPos.x = Math.min(targetPos.x, SelectionGrid.GRID_WIDTH - SelectionGrid.BOAT_SIZES[placingShipIndex]);
        } else {
            targetPos.y = Math.min(targetPos.y, SelectionGrid.GRID_HEIGHT - SelectionGrid.BOAT_SIZES[placingShipIndex]);
        }

        placingShip.setDrawPosition(new Position(targetPos),
                new Position(player.getPosition().x + targetPos.x * SelectionGrid.CELL_SIZE,
                        player.getPosition().y + targetPos.y * SelectionGrid.CELL_SIZE));

        tempPlacingPosition = targetPos;

        if (player.canPlaceShipAt(tempPlacingPosition.x, tempPlacingPosition.y,
                SelectionGrid.BOAT_SIZES[placingShipIndex], placingShip.isSideways())) {
            placingShip.setShipPlacementColour(Ship.ShipPlacementColour.Valid);
        } else {
            placingShip.setShipPlacementColour(Ship.ShipPlacementColour.Invalid);
        }
    }





    @Override
    public void mouseReleased(MouseEvent e) {
        Position mousePosition = new Position(e.getX(), e.getY());
        if (gameState == GameState.PlacingShips) {
            tryPlaceShip(mousePosition);
        } else if (gameState == GameState.FiringShots) {
            tryFireAtComputer(mousePosition);
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Position mousePosition = new Position(e.getX(), e.getY());
        if (gameState == GameState.PlacingShips) {
            tryMovePlacingShip(mousePosition);
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
