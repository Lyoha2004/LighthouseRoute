import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {
    private List<List<Integer>> data;

    static final int WATER = 0;
    static final int GROUND = 1;
    static final int LIGHTHOUSE = 2;
    static final int POINT_A = 3;
    static final int POINT_B = 4;

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
            for (int k = 0; k < s.length(); k++) {
                char mapObject = s.charAt(k);
                int dataObject = 0;
                switch (mapObject) {
                    case '~':
                        dataObject = WATER;
                        break;
                    case '*':
                        dataObject = GROUND;
                        break;
                    case 'L':
                        dataObject = LIGHTHOUSE;
                        break;
                    case 'A':
                        dataObject = POINT_A;
                        break;
                    case 'B':
                        dataObject = POINT_B;
                        break;
                }
                data.get(i).add(dataObject);
            }
            i++;
        }
    }

    public List<List<Integer>> getData() {
        return data;
    }
}
