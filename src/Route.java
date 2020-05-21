import java.util.ArrayList;
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

        // Test
        List<Coordinates> dots = makeLine(a, b);
        for (int i = 0; i < dots.size(); i++) {
            System.out.println(dots.get(i));
        }
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

        return null;
    }

    private List<Coordinates> makeLine(Coordinates a, Coordinates b) {
        boolean turned = false;
        if (a.x > b.x || (a.x == b.x && b.y < a.y)) {
            Coordinates c = new Coordinates(a.x, a.y);
            a = new Coordinates(b.x, b.y);
            b = new Coordinates(c.x, c.y);
            turned = true;
        }

        int deltaY = b.y - a.y;
        int deltaX = b.x - a.x;
        float k = (float) deltaY / deltaX;

        List<Coordinates> dots = new ArrayList<>();

        if (deltaX == 0) {
            dots = new ArrayList<>();
            int y = a.y;
            for (int i = 0; i <= b.y - a.y; i++) {
                dots.add(new Coordinates(a.x, y + i));
            }
            if (turned)
                dots = turnList(dots);
            return dots;
        }

        dots.add(a);
        int x = a.x;
        int y = a.y;
        while (x != b.x) {
            x++;
            y = Math.round(k * x) + a.y;
            dots.add(new Coordinates(x, y));
        }

        if (turned)
            dots = turnList(dots);

        return dots;
    }

    private List<Coordinates> turnList(List<Coordinates> list) {
        List<Coordinates> copy = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            copy.add(new Coordinates(list.get(i).x, list.get(i).y));
        }

        return copy;
    }
}
