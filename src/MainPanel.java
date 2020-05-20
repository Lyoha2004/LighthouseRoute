import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainPanel extends JPanel {
    private static final int PX = 26;
    private int HEIGHT_PX_COUNT;
    private int WIDTH_PX_COUNT;
    private List<List<Integer>> data;

    public MainPanel(int screenWidth, int screenHeight) {
        WIDTH_PX_COUNT = screenWidth / PX;
        HEIGHT_PX_COUNT = screenHeight / PX;

        data = new Map().getData();
    }

    protected void paintComponent(Graphics g) {

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                switch (data.get(i).get(j)) {
                    case Map.WATER:
                        g.setColor(Color.BLUE);
                        break;
                    case Map.GROUND:
                        g.setColor(Color.ORANGE);
                        break;
                    case Map.LIGHTHOUSE:
                        g.setColor(Color.CYAN);
                        break;
                    case Map.POINT_A:
                        g.setColor(Color.GREEN);
                        break;
                    case Map.POINT_B:
                        g.setColor(Color.RED);
                        break;
                }
                g.fillRect(j * PX, i * PX, PX, PX);
                g.setColor(Color.BLACK);
                g.drawRect(j * PX, i * PX, PX, PX);
            }
        }

    }

}
