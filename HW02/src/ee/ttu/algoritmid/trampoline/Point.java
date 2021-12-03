package ee.ttu.algoritmid.trampoline;

public final class Point {

    public int x;
    public int y;

    public Point(int x, int y) { this.x = x; this.y = y; }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Point)) {
            return false;
        }
        Point point = (Point) other;
        return this.x == point.x && this.y == point.y;
    }

    @Override
    public int hashCode() {
        return this.x + 31 * this.y;
    }
}