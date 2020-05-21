import java.util.List;

public class Route {
    private Coordinates a;
    private Coordinates b;
    private List<Coordinates> lighthouses;
    private List<Coordinates> route;
    private int r = 2;

    public Route() {
        Map map = new Map();
        a = map.getA();
        b = map.getB();
        lighthouses = map.getLighthouses();

        findRoute();
    }

    private void findRoute() {

    }

    private void makeCircle(Coordinates lighthouse) {

    }

    private List<Coordinates> findAllTangent(Coordinates m, Coordinates o) {
        Coordinates shift = new Coordinates(m.x + (o.x - m.x) / 2, m.y + (o.y - m.y) / 2);

    }
}
