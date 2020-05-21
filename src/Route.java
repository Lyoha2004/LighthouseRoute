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
        Coordinates mid = new Coordinates(m.x + (o.x - m.x) / 2, m.y + (o.y - m.y) / 2);
        Coordinates shift = new Coordinates(o.x, o.y);
        int x2 = (mid.x - o.x);
        int y2 = (mid.y - o.y);
        int A = -2 * x2;
        int B = -2 * y2;
        int C = x2^2 + y2^2;
    }
}
