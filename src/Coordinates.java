import java.util.Objects;

public class Coordinates {
    private static final int RIGHT = 0;
    private static final int RIGHT_DOWN = 1;
    private static final int DOWN = 2;
    private static final int LEFT_DOWN = 3;
    private static final int LEFT = 4;
    private static final int LEFT_UP = 5;
    private static final int UP = 6;
    private static final int RIGHT_UP = 7;
    public int x;
    public int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getDirection(Coordinates a) {
        if (a.x - this.x == 1 && a.y - this.y == 0) {
            return RIGHT;
        }
        if (a.x - this.x == 1 && a.y - this.y == 1) {
            return RIGHT_DOWN;
        }
        if (a.x - this.x == 0 && a.y - this.y == 1) {
            return DOWN;
        }
        if (a.x - this.x == -1 && a.y - this.y == 1) {
            return LEFT_DOWN;
        }
        if (a.x - this.x == -1 && a.y - this.y == 0) {
            return LEFT;
        }
        if (a.x - this.x == -1 && a.y - this.y == -1) {
            return LEFT_UP;
        }
        if (a.x - this.x == 0 && a.y - this.y == -1) {
            return UP;
        }
        if (a.x - this.x == 1 && a.y - this.y == -1) {
            return RIGHT_UP;
        }
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
