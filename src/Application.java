import javax.swing.*;
import java.awt.*;

public class Application {
    public static void main(String[] args) {

        JFrame frame = new MainFrame();
        frame.setTitle("Lighthouse Route");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}

class MainFrame extends JFrame {
    MainPanel panel;
    JPanel buttonPanel;
    JButton upSpeedButton;
    JButton downSpeedButton;
    public MainFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = (int)(screenSize.height * 0.8);
        int screenWidth = (int)(screenSize.width * 0.8);
        panel = new MainPanel(screenWidth, screenHeight - 10);
        add(panel, BorderLayout.CENTER);
        buttonPanel = new JPanel();
        upSpeedButton = new JButton("X2");
        downSpeedButton = new JButton("/2");
        buttonPanel.add(downSpeedButton);
        buttonPanel.add(upSpeedButton);

        upSpeedButton.addActionListener(event -> {
            int delay = panel.getDelay() / 2;
            if (delay == 0)
                delay = 1;
            System.out.println("Delay: " + delay);
            panel.setDelay(delay);
            panel.timer.setDelay(delay);
        });

        downSpeedButton.addActionListener(event -> {
            int delay = panel.getDelay() * 2;
            System.out.println("Delay: " + delay);
            panel.setDelay(delay);
            panel.timer.setDelay(delay);
        });
        add(buttonPanel, BorderLayout.SOUTH);
        pack();

        setSize(screenWidth, screenHeight);
        setLocationByPlatform(true);
    }
}
