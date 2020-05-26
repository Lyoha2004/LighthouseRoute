import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {
    private List<List<Integer>> data;
    private List<Coordinates> lighthouses;
    private Coordinates a;
    private Coordinates b;

    public final int WIDTH;
    public final int HEIGHT;

    static final int WATER = 0;
    static final int GROUND = 1;
    static final int LIGHTHOUSE = 2;
    static final int POINT_A = 3;
    static final int POINT_B = 4;

    public List<Coordinates> getLighthouses() {
        return lighthouses;
    }

    public Coordinates getA() {
        return a;
    }

    public Coordinates getB() {
        return b;
    }

    public Map() {
        File mapFile = new File("./maps/map4.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(mapFile);
        } catch (FileNotFoundException e) {
            System.out.println("Can't reach the file");
            System.exit(0);
        }

        data = new ArrayList<>();
        lighthouses = new ArrayList<>();

        int i = 0;
        int length = 0;
        int as = 0;
        int bs = 0;
        while (sc.hasNextLine()) {
            String s = sc.nextLine();
            try {
                if (i > 0 && s.length() != length) {
                    throw new IOException();
                }
            } catch (IOException e) {
                System.err.println("Wrong map! Field should be rectangular!");
                System.exit(0);
            }
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
                        lighthouses.add(new Coordinates(k, i));
                        dataObject = LIGHTHOUSE;
                        break;
                    case 'A':
                        a = new Coordinates(k, i);
                        as++;
                        dataObject = POINT_A;
                        break;
                    case 'B':
                        b = new Coordinates(k, i);
                        bs++;
                        dataObject = POINT_B;
                        break;
                }
                data.get(i).add(dataObject);
            }
            i++;
            length = s.length();
        }

        try {
            if (as != 1 || bs != 1) {
                throw new IOException();
            }
        } catch (IOException e) {
            System.err.println("Wrong map! There should be ane A and one B");
        }

        WIDTH = data.get(0).size();
        HEIGHT = data.size();
    }

    public List<List<Integer>> getData() {
        return data;
    }
}
