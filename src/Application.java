import javax.swing.*;
import java.awt.*;

public class Application {
    public static void main(String[] args) {

        JFrame frame = new MainFrame();
        frame.setTitle("Lighthouse Route");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);

    }
}

class MainFrame extends JFrame {
    JPanel panel;
    public MainFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height / 2;
        int screenWidth = screenSize.width / 2;
        panel = new MainPanel(screenWidth, screenHeight);
        add(panel);
        pack();

        setSize(screenWidth, screenHeight);
        setLocationByPlatform(true);
    }
}
