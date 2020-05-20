import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainPanel extends JPanel {
    private static final int PX = 30;
    private int HEIGHT_PX_COUNT;
    private int WIDTH_PX_COUNT;
    private int Y0_PX_NUMBER;
    private int X0_PX_NUMBER;
    private List<List<Integer>> data;

    private Image waterImage;
    private Image groundImage;
    private Image lighthouseImage;
    private Image aImage;
    private Image bImage;

    public MainPanel(int screenWidth, int screenHeight) {
        WIDTH_PX_COUNT = screenWidth / PX;
        HEIGHT_PX_COUNT = screenHeight / PX;
        try {
            waterImage = ImageIO.read(new File("./res/water.gif"));
            groundImage = ImageIO.read(new File("./res/grass.gif"));
            lighthouseImage = ImageIO.read(new File("./res/lighthouse.gif"));
            aImage = ImageIO.read(new File("./res/a.gif"));
            bImage = ImageIO.read(new File("./res/b.gif"));
        } catch (IOException e) {
            System.out.println("Image error");
        }

        data = new Map().getData();
        Y0_PX_NUMBER = (HEIGHT_PX_COUNT - data.size()) / 2;
        X0_PX_NUMBER = (WIDTH_PX_COUNT - data.get(0).size()) / 2;
    }

    protected void paintComponent(Graphics g) {
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                Image img = null;
                switch (data.get(i).get(j)) {
                    case Map.WATER:
                        img = waterImage;
                        break;
                    case Map.GROUND:
                        img = groundImage;
                        break;
                    case Map.LIGHTHOUSE:
                        img = lighthouseImage;
                        break;
                    case Map.POINT_A:
                        img = aImage;
                        break;
                    case Map.POINT_B:
                        img = bImage;
                        break;
                }
                g.drawImage(img, X0_PX_NUMBER * PX + j * PX, Y0_PX_NUMBER * PX + i * PX, PX, PX, this);
            }
        }
    }

}
