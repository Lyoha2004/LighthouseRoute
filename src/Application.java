import javax.swing.*;
import java.awt.*;

public class Application {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("java Syntax: <path>");
            System.exit(-1);
        }
        String fileName = args[0];
        JFrame frame = new MainFrame(fileName);
        frame.setTitle("Lighthouse Route");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}

class MainFrame extends JFrame {
    MainPanel panel;
    JPanel buttonPanel;
    JButton upSpeedButton;
    JButton downSpeedButton;
    public MainFrame(String filename) {
//        this.setUndecorated(true);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = (int)(screenSize.height * 0.8);
        int screenWidth = screenSize.width;
        panel = new MainPanel(screenWidth, screenHeight - 10, filename);
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
