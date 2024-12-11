import java.awt.*;

public class Marker extends Rectangle {
    private final Color HIT_COLOUR = new Color(219, 23, 23, 180); 
    private final Color MISS_COLOUR = new Color(255, 255, 255);

    private final int PADDING = 3;
    private boolean showMarker;
    private Ship shipAtMarker;

    public Marker(int x, int y, int width, int height) {
        super(x, y, width, height);
        reset();
    }

    public void reset() {
        shipAtMarker = null;
        showMarker = false;
    }


    public void mark() {
        if(!showMarker && isShip()) {
            shipAtMarker.destroySection();
        }
        showMarker = true;
    }


    public boolean isMarked() {
        return showMarker;
    }

    public void setAsShip(Ship ship) {
        this.shipAtMarker = ship;
    }


    public boolean isShip() {
        return shipAtMarker != null;
    }

    public Ship getAssociatedShip() {
        return shipAtMarker;
    }

    public void paint(Graphics g) {
        if (!showMarker) return;
        g.setColor(isShip() ? HIT_COLOUR : MISS_COLOUR);
        int adjustedWidth = width - PADDING * 2;
        int adjustedHeight = height - PADDING * 2;
        int size = Math.min(adjustedWidth, adjustedHeight); 
 
        g.fillOval(position.x + PADDING + 1, position.y + PADDING + 1, size, size);
    }
}
