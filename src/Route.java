import java.util.*;

public class Route {
    private Coordinates a;
    private Coordinates b;
    private List<Coordinates> lighthouses;
    private List<Coordinates> route;
    private HashSet<Coordinates> border;
    private Map map;
    private int r = 2;

    public List<Coordinates> getRoute() {
        return route;
    }

    /**
     * <p>Класс для вычисления кратчайшего пути от одной вершины до другой.</p>
     * <p>Для вычисления расстояния необходимо передать карту <code>Map</code>,
     * представленную в текстовом виде и включающую вершины начала пути,
     * конца пути и вершины, принадлежащие маякам</p>
     * <p>После нахождения кратайшего пути, в консоль выводся время, за которое отработал поиск</p>
     * @param fileName файл, содержащий карту в текстовом виде
     */
    public Route(String fileName) {
        map = new Map(fileName);
        a = map.getA();
        b = map.getB();
        lighthouses = map.getLighthouses();

        border = new HashSet<>();
        border.addAll(addScreenBorder());

        for (Coordinates lighthouse : lighthouses) {
            border.addAll(makeCircle(lighthouse));
        }

        long timeBeforeTest = System.currentTimeMillis();
        route = findRoute(a, b);
        long timeAfterTest = System.currentTimeMillis();
        System.out.printf("Time for algorithm: %.3f seconds\n", (double)(timeAfterTest - timeBeforeTest) / 1000);
    }


    private List<Coordinates> findRoute(Coordinates a, Coordinates b) {
        Queue<CoordinateWithCost> frontier = new PriorityQueue<>(costComparator);
        frontier.add(new CoordinateWithCost(0, a));
        HashMap<Coordinates, Coordinates> cameFrom = new HashMap<>();
        HashMap<Coordinates, Double> costSoFar = new HashMap<>();

        cameFrom.put(a, null);
        costSoFar.put(a, 0.0);

        // A star
        while (!frontier.isEmpty()) {
            Coordinates current = frontier.poll().coordinate;

//            if (current.equals(b)) {
//                break;
//            }

            for (Coordinates next : getNeighbors(current)) {
                double newCost = costSoFar.get(current) + moveCost(current, next);

                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    double priority = newCost + heuristic(next, b);
                    frontier.add(new CoordinateWithCost(priority, next));
                    cameFrom.put(next, current);
                }
            }
        }

        // Восстановление пути
        List<Coordinates> path = new ArrayList<>();
        Coordinates current = b;
        path.add(current);
        while (!current.equals(a)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        path = turnList(path);
        return path;
    }

    private Double getMinDouble(Double[] numbers) {
        Double minValue = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < minValue) {
                minValue = numbers[i];
            }
        }
        return minValue;
    }

    /**
     * Вычиляет стоимость перемещения из вершины <code>a</code> в врешину <code>b</code>.
     * Здесь применяется Эвклидово расстояние.
     * @param a вершина начала
     * @param b вершина конца
     * @return
     */
    private double moveCost(Coordinates a, Coordinates b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    class CoordinateWithCost {

        private double cost;
        private Coordinates coordinate;

        public CoordinateWithCost(double cost, Coordinates coordinate) {
            this.cost = cost;
            this.coordinate = coordinate;
        }

        public double getCost() {
            return cost;
        }

        public Coordinates getCoordinate() {
            return coordinate;
        }
    }

    /**
     * Компоратор, сравнивающий вершины по стоимости.
     */
    public static Comparator<CoordinateWithCost> costComparator = new Comparator<CoordinateWithCost>() {
        @Override
        public int compare(CoordinateWithCost o1, CoordinateWithCost o2) {
            return (int) (o1.getCost() - o2.getCost());
        }
    };

    /**
     * Находит все возможные соседние клетки
     * @param coord
     * @return
     */
    private List<Coordinates> getNeighbors(Coordinates coord) {
        Coordinates[] directions = {new Coordinates(1, 0), new Coordinates(0, 1),
                new Coordinates(-1, 0), new Coordinates(0, -1), new Coordinates(1, 1),
                new Coordinates(1, -1), new Coordinates(-1, -1), new Coordinates(-1, 1)};
        List<Coordinates> neighbors = new ArrayList<>();
        for (int i = 0; i < directions.length; i++) {
            Coordinates dot = new Coordinates(coord.x + directions[i].x, coord.y + directions[i].y);
            if (!border.contains(dot)) {
                neighbors.add(dot);
            }
        }
        return neighbors;
    }

    /**
     * Возвращает список вершин, входящих в границу карты
     * @return
     */
    private List<Coordinates> addScreenBorder() {
        List<Coordinates> border = new ArrayList<>();
        for (int i = -1; i < map.WIDTH + 1; i++) {
            border.add(new Coordinates(i, -1));
            border.add(new Coordinates(i, map.HEIGHT));
        }
        for (int i = -1; i < map.HEIGHT + 1; i++) {
            border.add(new Coordinates(-1, i));
            border.add(new Coordinates(map.WIDTH, i));
        }
        return border;
    }

    /**
     * <p>Эвристический метод. Заставляет границу поиска расширяться в сторону точки назначения.</p>
     * <p>Всё, что делает метод - это сообщает, насколько мы близки к цели. Это позволяет
     * приоритизировать направление распространения во врема проверки очередной точки, она
     * добавляется в кучу в зависимости не только от стоимости, но и от растояния до финиша</p>
     * <p>В данном случае мы используем Чебышего расстояние</p>
     *
     * @param a координата начала
     * @param b координата конца
     * @return расстояние от начала до конца
     */
    private double heuristic(Coordinates a, Coordinates b) {
        return Math.max(Math.abs(a.x - b.x), Math.abs(a.y - b.y));
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

    /**
     * <p>Создаёт линию, состоящую из множества вершин. При этом передаётся 2 вершины,
     * а возвращается список вершин начиная от первой и кончая последней.</p>
     * @param a
     * @param b
     * @return
     */
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

    /**
     * Возвращает список в перевёрнутном виде
     * @param list
     * @return
     */
    private List<Coordinates> turnList(List<Coordinates> list) {
        List<Coordinates> copy = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            copy.add(new Coordinates(list.get(i).x, list.get(i).y));
        }

        return copy;
    }

    /**
     * <p>Создаёт список вершин, входящих в окружность с переданным центром,
     * используя радиус, определённый глобально.</p>
     * <p>Для того, чтобы не было возможности пройти по диагонали
     * внутрь окружности и точно также выйти, создаётся вторая окружность
     * с радиусом на единицу меньше</p>
     * @param centre <code>Центр окружности</code>
     * @return список вершин
     */
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

            surface.add(dot);
        }

        for (int i = 1; i < 360; i += deltaAngle) {
            angle = i;
            x1 = (r-1) * Math.cos(angle * Math.PI / 180);
            y1 = (r-1) * Math.sin(angle * Math.PI / 180);

            Coordinates dot = new Coordinates((int) Math.round(centre.x + x1), (int) Math.round(centre.y + y1));

            surface.add(dot);
        }

        return surface;
    }
}
