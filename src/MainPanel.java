import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainPanel extends JPanel implements ActionListener {
    private int PX = 0;
    private int X0 = 50;
    private int Y0 = 50;
    private double screenFormat = 0;
    private List<List<Integer>> data;

    private Image waterImage;
    private List<Image> waterImages;
    private Image groundImage;
    private Image lighthouseImage;
    private Image aImage;
    private Image bImage;
    private Image shipImage;
    private Image shipRightImage;
    private Image shipLeftImage;
    private Image shipUpImage;
    private Image shipDownImage;
    private int delay = 25;
    public Timer timer;

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

    private Ship ship;
    private Map map;
    private List<Coordinates> route;

    public MainPanel(int screenWidth, int screenHeight) {
        screenFormat = (double)screenWidth / screenHeight;
        timer = new Timer(delay, this);
        waterImages = new ArrayList<>();
        try {
            waterImages.add(ImageIO.read(new File("./res/water_4.gif")));
            waterImages.add(ImageIO.read(new File("./res/water_3.gif")));
            waterImages.add(ImageIO.read(new File("./res/water_2.gif")));
            waterImages.add(ImageIO.read(new File("./res/water.gif")));
            groundImage = ImageIO.read(new File("./res/grass.gif"));
            lighthouseImage = ImageIO.read(new File("./res/lighthouse.gif"));
            aImage = ImageIO.read(new File("./res/a_transparent.gif"));
            bImage = ImageIO.read(new File("./res/b_transparent.gif"));
            shipRightImage = ImageIO.read(new File("./res/advanced_ship.gif"));
            shipLeftImage = ImageIO.read(new File("./res/advanced_ship_left.gif"));
            shipUpImage = ImageIO.read(new File("./res/advanced_ship_up.gif"));
            shipDownImage = ImageIO.read(new File("./res/advanced_ship_down.gif"));

            waterImage = waterImages.get(0);
            shipImage = shipRightImage;
        } catch (IOException e) {
            System.out.println("Image error");
        }

        map = new Map();
        Route routeAlgo = new Route();

        route = routeAlgo.getRoute();

        data = map.getData();

        if (map.WIDTH >= map.HEIGHT * screenFormat) {
            PX = (screenWidth - X0 * 2) / map.WIDTH;
            Y0 = (screenHeight - PX * map.HEIGHT) / 2;
        } else {
            PX = (screenHeight - Y0 * 2) / map.HEIGHT;
            X0 = (screenWidth - PX * map.WIDTH) / 2;
        }
        speed = (double)PX / 20;


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
                        g.drawImage(waterImage, X0 + j * PX, Y0 + i * PX, PX, PX, this);
                        img = aImage;
                        break;
                    case Map.POINT_B:
                        g.drawImage(waterImage, X0 + j * PX, Y0 + i * PX, PX, PX, this);
                        img = bImage;
                        break;
                }
                g.drawImage(img, X0 + j * PX, Y0 + i * PX, PX, PX, this);
            }
        }
        g.drawImage(shipImage, (int)(X0 + ship.getX() * PX), (int)(Y0 + ship.getY() * PX), PX, PX, this);

    }

    private int direction = 1;
    private int i = 0;
    private int j = 1;
    private int waterNum = 0;
    private double speed;
    private final int waterSpeed = 6;
    private int k = 0;

    @Override
    public void actionPerformed(ActionEvent e) {
        ship.setX(route.get(i).x + (speed * j * (getNextPoint().x - route.get(i).x)) / PX);
        ship.setY(route.get(i).y + (speed * j * (getNextPoint().y - route.get(i).y)) / PX);
        j++;
        k++;

        if (j == 20) {
            j = 0;
        }

        if (k == waterSpeed) {
            k = 0;
            waterNum++;
            if (waterNum == 4)
                waterNum = 0;
            waterImage = waterImages.get(waterNum);
        }

        repaint();
        if (j == 0) {
            i += direction;
            if (i == route.size() - 1 || i == 0)
                direction *= -1;

            if (getNextPoint().y - route.get(i).y < 0) {
                shipImage = shipUpImage;
            } else if (getNextPoint().y - route.get(i).y > 0) {
                shipImage = shipDownImage;
            } else if (getNextPoint().x - route.get(i).x < 0) {
                shipImage = shipLeftImage;
            } else {
                shipImage = shipRightImage;
            }
        }
    }

    public Coordinates getNextPoint() {
        return route.get(i + direction);
    }
}
