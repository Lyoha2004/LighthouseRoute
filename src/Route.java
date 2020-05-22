import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Route {
    private Coordinates a;
    private Coordinates b;
    private List<Coordinates> lighthouses;
    private List<Coordinates> route;
    private int r = 2;

    public List<Coordinates> getRoute() {
        return route;
    }

    public Route() {
        Map map = new Map();
        a = map.getA();
        b = map.getB();
        lighthouses = map.getLighthouses();

        findRoute();

        // Test

        Set<Coordinates> dots = new HashSet<>();

        for (Coordinates lighthouse : lighthouses) {
            dots.addAll(makeCircle(lighthouse));
        }

        for (Coordinates dot : dots) {
            System.out.println(dot);
        }

        route = new ArrayList<>(dots);
    }

    private void findRoute() {

    }

    private List<Coordinates> findAllTangent(Coordinates m, Coordinates o) {
        Coordinates mid = new Coordinates(m.x + (o.x - m.x) / 2, m.y + (o.y - m.y) / 2);
        Coordinates shift = new Coordinates(o.x, o.y);
        Coordinates midShift = new Coordinates(mid.x - shift.x, mid.y - shift.y);
        int x2 = (mid.x - o.x);
        int y2 = (mid.y - o.y);
        double A = -2 * x2;
        double B = -2 * y2;
        double r2 = ((double)((o.x - m.x) * (o.x - m.x) + (o.y - m.y) * (o.y - m.y))) / 4;
        double C = x2 * x2 + y2 * y2 + 4 - r2;
        //радиус = 2
        double x0 = -(A*C)/(A * A + B * B);//координаты x ближайшей к центру окружности точки прямой
        double y0 = -(B*C)/(A * A + B * B);//координаты y ближайшей к центру окружности точки прямой
        double d = Math.sqrt(4 - C * C / (A * A + B * B));//расстояние от (х0, у0) до точек пересечения
        double coef = (d /Math.sqrt(A * A + B * B));//коэффициент, на который нужно умножить вектор (-В, А) для того, чтобы его конец был в точке пересечения
        Coordinates point1 = new Coordinates(Math.round((float)(x0 + B*coef + shift.x)),Math.round((float)(y0 - A*coef + shift.y)));
        Coordinates point2 = new Coordinates(Math.round((float)(x0 - B*coef + shift.x)),Math.round((float)(y0 + A*coef + shift.y)));
        double xPoint2 = (x0 - B*coef + shift.x);
        double yPoint2 = (y0 + A*coef + shift.y);
        List<Coordinates> result = new ArrayList<>();
        result.add(point1);
        result.add(point2);
        return result;
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

        float k = (float) deltaY / deltaX;


        dots.add(a);

        int x = a.x;
        int y = a.y;
        while (x != b.x) {
            x++;
            y = Math.round(k * x) + a.y;
            int prev_y = dots.get(dots.size()-1).y;
            if (y == prev_y) {
                dots.add(new Coordinates(x, y));
            } else {
                if (prev_y < y) {
                    for (int i = prev_y + 1; i <= Math.min(y, b.y); i++) {
                        dots.add(new Coordinates(x, i));
                    }
                } else {
                    for (int i = prev_y - 1; i >= Math.max(y, b.y); i--) {
                        dots.add(new Coordinates(x, i));
                    }
                }
            }
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

    private List<Coordinates> makeCircle(Coordinates centre) {
        List<Coordinates> surface = new ArrayList<>();

        double angle = 0, x1 = 0, y1 = 0;
        int deltaAngle = 10;
        surface.add(new Coordinates(centre.x + r, centre.y));

        for (int i = 1; i < 360; i += deltaAngle) {
            angle = i;
            x1 = r * Math.cos(angle * Math.PI / 180);
            y1 = r * Math.sin(angle * Math.PI / 180);

            Coordinates dot = new Coordinates((int) Math.round(centre.x + x1), (int) Math.round(centre.y + y1));

            if (!dot.equals(surface.get(surface.size()-1)) && !dot.equals(surface.get(0)))
                surface.add(dot);
        }

        return surface;
    }
}
