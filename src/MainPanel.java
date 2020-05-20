import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private static final int PX = 14;
    private int HEIGHT_PX_COUNT;
    private int WIDTH_PX_COUNT;

    public MainPanel(int screenWidth, int screenHeight) {
        WIDTH_PX_COUNT = screenWidth / PX;
        HEIGHT_PX_COUNT = screenHeight / PX;
    }

    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLUE);
        for (int i = 0; i < HEIGHT_PX_COUNT; i++) {
            for (int j = 0; j < WIDTH_PX_COUNT; j++) {
                g.setColor(Color.BLUE);
                g.fillRect(i * PX, j * PX, PX, PX);
                g.setColor(Color.BLACK);
                g.drawRect(i * PX, j * PX, PX, PX);
            }
        }
        g.fillRect(10 * PX, 10 * PX, PX, PX);
    }

}
