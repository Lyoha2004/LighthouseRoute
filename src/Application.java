import javax.swing.*;
import java.awt.*;

public class Application {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new MainFrame();
            frame.setTitle("Lighthouse route");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }


}

class MainFrame extends JFrame {
    JPanel panel;
    public MainFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        panel = new MainPanel(screenWidth, screenHeight);
        add(panel);
        pack();

        setSize(screenWidth / 2, screenHeight / 2);
        setLocationByPlatform(true);
    }
}
