import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {
    private List<List<Integer>> data;

    private static final int WATER = 0;
    private static final int GROUND = 0;
    private static final int LIGHTHOUSE = 0;
    private static final int POINT_A = 0;
    private static final int POINT_B = 0;

    public Map() {
        File mapFile = new File("map.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(mapFile);
        } catch (FileNotFoundException e) {
            System.out.println("Can't reach the file");
            System.exit(0);
        }

        data = new ArrayList<>();

        int i = 0;
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            data.add(new ArrayList<>());
            for (int k = 0; k < s.length(); i++) {
                char mapObject = s.charAt(k);
                int dataObject = 0;
                switch (mapObject) {
                    case ' ':
                        dataObject = WATER;
                        break;
                    case '*':
                        dataObject = GROUND;
                }
            }
        }
    }
}
