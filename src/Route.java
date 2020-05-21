import java.util.List;

public class Route {
    private Coordinates a;
    private Coordinates b;
    private List<Coordinates> lighthouses;
    private List<Coordinates> route;

    public Route() {
        Map map = new Map();
        a = map.getA();
        b = map.getB();
        lighthouses = map.getLighthouses();

        findRoute();
    }

    private void findRoute() {
        System.out.println(a);
        System.out.println(b);
        for (Coordinates i : lighthouses) {
            System.out.println(i);
        }
    }
}
