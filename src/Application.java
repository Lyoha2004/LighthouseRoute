import javax.swing.*;

public class Application {
    private static final int DEFAULT_WIDTH = 202;
    private static final int DEFAULT_HEIGHT = 340;
    MainPanel mainPanel = new MainPanel();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Lighthouse route");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }


}
