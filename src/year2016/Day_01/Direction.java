package year2016.Day_01;

import java.util.List;

import static year2016.Day_01.DirectionName.*;

public class Direction {
    Factors factors;

    public Direction() {
        factors = new Factors(0,1, NORTH);
    }

    public void turnLeft() {
        switch(factors.name()) {
            case NORTH -> factors = new Factors(-1, 0, WEST);
            case WEST -> factors = new Factors(0, -1, SOUTH);
            case SOUTH -> factors = new Factors(1, 0, EAST);
            case EAST -> factors = new Factors(0, 1, NORTH);
        }
    }

    public void turnRight() {
        switch(factors.name()) {
            case NORTH -> factors = new Factors(1, 0, EAST);
            case WEST -> factors = new Factors(0, 1, NORTH);
            case SOUTH -> factors = new Factors(-1, 0, WEST);
            case EAST -> factors = new Factors(0, -1, SOUTH);
        }
    }

    public Coordinate go(Coordinate currentPosition, int steps, List<Coordinate> nowVisited) {
        int newX = currentPosition.x();
        int newY = currentPosition.y();
        for (int i = 1; i <= steps; i++) {
            newX += factors.x();
            newY += factors.y();
            nowVisited.add(new Coordinate(newX, newY));
        }
        return new Coordinate(newX, newY);
    }
}
