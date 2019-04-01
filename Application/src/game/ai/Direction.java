package game.ai;

public class Direction {

    int x;
    int y;

    public Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Direction getDirection(ReversiAI.Dir direction) {
        switch (direction) {
            case N:
                return new Direction(0,1);
            case NE:
                return new Direction(1,1);
            case E:
                return new Direction(1,0);
            case SE:
                return new Direction(1,-1);
            case S:
                return new Direction(0,-1);
            case SW:
                return new Direction(-1,-1);
            case W:
                return new Direction(-1,0);
            case NW:
                return new Direction(-1,1);
        }
        return null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
