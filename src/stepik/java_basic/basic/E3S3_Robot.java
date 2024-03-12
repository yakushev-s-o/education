package stepik.java_basic.basic;

public class E3S3_Robot {
    public static void main(String[] args) {
        Robot robot = new Robot(0, 0, Direction.UP);
        moveRobot(robot, 3, 3);
    }

    public static void moveRobot(Robot robot, int toX, int toY) {
        while (robot.getX() != toX) {
            if (robot.getX() < toX) {
                while (!robot.getDirection().equals(Direction.RIGHT)) {
                    if (robot.getDirection().equals(Direction.DOWN)) {
                        robot.turnLeft();
                    } else {
                        robot.turnRight();
                    }
                }
            } else if (robot.getX() > toX) {
                while (!robot.getDirection().equals(Direction.LEFT)) {
                    if (robot.getDirection().equals(Direction.UP)) {
                        robot.turnLeft();
                    } else {
                        robot.turnRight();
                    }
                }
            }

            robot.stepForward();
        }

        while (robot.getY() != toY) {
            if (robot.getY() < toY) {
                while (!robot.getDirection().equals(Direction.UP)) {
                    if (robot.getDirection().equals(Direction.RIGHT)) {
                        robot.turnLeft();
                    } else {
                        robot.turnRight();
                    }
                }
            } else if (robot.getY() > toY) {
                while (!robot.getDirection().equals(Direction.DOWN)) {
                    if (robot.getDirection().equals(Direction.RIGHT)) {
                        robot.turnRight();
                    } else {
                        robot.turnLeft();
                    }
                }
            }

            robot.stepForward();
        }
    }
}

class Robot {
    private int x;
    private int y;
    private Direction direction;

    public Robot(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void turnLeft() {
        System.out.println("Поворот против часовой стрелки");
        if (direction == Direction.DOWN) {
            this.direction = Direction.RIGHT;
            return;
        }

        if (direction == Direction.UP) {
            this.direction = Direction.LEFT;
            return;
        }

        if (direction == Direction.LEFT) {
            this.direction = Direction.DOWN;
            return;
        }

        if (direction == Direction.RIGHT) {
            this.direction = Direction.UP;
        }
    }

    public void turnRight() {
        System.out.println("Поворот по часовой стрелке");
        if (this.direction == Direction.DOWN) {
            this.direction = Direction.LEFT;
            return;
        }

        if (this.direction == Direction.UP) {
            this.direction = Direction.RIGHT;
            return;
        }

        if (this.direction == Direction.LEFT) {
            this.direction = Direction.UP;
            return;
        }

        if (this.direction == Direction.RIGHT) {
            this.direction = Direction.DOWN;
        }
    }

    public void stepForward() {
        System.out.print("Движение -> ");
        if (direction == Direction.DOWN) {
            System.out.println("вниз");
            this.y--;
        }

        if (direction == Direction.UP) {
            System.out.println("вверх");
            this.y++;
        }

        if (direction == Direction.LEFT) {
            System.out.println("налево");
            this.x--;
        }

        if (direction == Direction.RIGHT) {
            System.out.println("направо");
            this.x++;
        }
    }
}

enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}



