import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainPanel extends JPanel implements ActionListener {
    private static final int PX = 50;
    private final int HEIGHT_PX_COUNT;
    private final int WIDTH_PX_COUNT;
    private final int Y0_PX_NUMBER;
    private final int X0_PX_NUMBER;
    private List<List<Integer>> data;

    private Image waterImage;
    private Image groundImage;
    private Image lighthouseImage;
    private Image aImage;
    private Image bImage;
    private Image shipImage;

    private Ship ship;
    private Map map;
    private List<Coordinates> route;

    public MainPanel(int screenWidth, int screenHeight) {
        Timer timer = new Timer(400, this);
        WIDTH_PX_COUNT = screenWidth / PX;
        HEIGHT_PX_COUNT = screenHeight / PX;
        try {
            waterImage = ImageIO.read(new File("./res/water.gif"));
            groundImage = ImageIO.read(new File("./res/grass.gif"));
            lighthouseImage = ImageIO.read(new File("./res/lighthouse.gif"));
            aImage = ImageIO.read(new File("./res/a.gif"));
            bImage = ImageIO.read(new File("./res/b.gif"));
            shipImage = ImageIO.read(new File("./res/advanced_ship.png"));
        } catch (IOException e) {
            System.out.println("Image error");
        }

        map = new Map();
        route = new Route().getRoute();

        data = map.getData();
        Y0_PX_NUMBER = (HEIGHT_PX_COUNT - data.size()) / 2;
        X0_PX_NUMBER = (WIDTH_PX_COUNT - data.get(0).size()) / 2;


        ship = new Ship(map.getA().x, map.getA().y);
        timer.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
        g.drawImage(shipImage, X0_PX_NUMBER * PX + ship.getX() * PX, Y0_PX_NUMBER * PX + ship.getY() * PX, PX, PX, this);

    }

    public void moveShip() {
        for (int i = 0; i < 5; i++) {
            ship.moveRight();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int direction = 1;
    private int i = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        ship.setX(route.get(i).x);
        ship.setY(route.get(i).y);
        repaint();
        i += direction;
        if (i == route.size() - 1 || i == 0)
            direction *= -1;
    }
}
