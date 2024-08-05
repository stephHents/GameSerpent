package apple;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public class Pomme implements Serializable {
    private Point position;
    private int rows;
    private int cols;
    private Random random;

    public Pomme(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        random = new Random();
        generateNewPosition();
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
    // random placement de la grille pomme
    public void generateNewPosition() {
        int x = random.nextInt(cols);
        int y = random.nextInt(rows);
        position = new Point(x, y);
    }
}
