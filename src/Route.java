import java.util.ArrayList;
import java.util.List;

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
        Coordinates lighthouse = lighthouses.get(3);
        System.out.println("Lighthouse: " + lighthouse);
        List<Coordinates> dots = makeLine(a, findAllTangent(a, lighthouse).get(0));
        for (Coordinates dot : dots) {
            System.out.println(dot);
        }
        route = dots;
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
        //радиус = 2
        int x0 = -(A*C)/(A^2 + B^2);//координаты x ближайшей к центру окружности точки прямой
        int y0 = -(B*C)/(A^2 + B^2);//координаты y ближайшей к центру окружности точки прямой
        int d =(int)Math.sqrt(4 - C^2/(A^2+B^2)^2);//расстояние от (х0, у0) до точек пересечения
        int coef = (int)(d/Math.sqrt(A^2+B^2));//коэффициент, на который нужно умножить вектор (-В, А) для того, чтобы его конец был в точке пересечения
        Coordinates point1 = new Coordinates(x0 + B*coef + o.x,x0 - A*coef + o.y);
        Coordinates point2 = new Coordinates(x0 - B*coef + o.x,x0 + A*coef + o.y);
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
        float k = (float) deltaY / deltaX;
        float kTurned = (float) deltaX / deltaY;

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
            int prev_y = dots.get(dots.size()-1).y;
            if (y == prev_y) {
                dots.add(new Coordinates(x, y));
            } else {
                for (int i = prev_y + 1; i <= y; i++) {
                    dots.add(new Coordinates(x, i));
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
}
