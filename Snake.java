package snake;

import apple.*;
import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;

public class Snake implements Serializable {
    private LinkedList<Point> body;
    private int direction; // 0 = UP, 1 = RIGHT, 2 = DOWN, 3 = LEFT
    private int rows;
    private int cols;
    private boolean crossedBorder;

    public Snake(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        body = new LinkedList<>();
        body.add(new Point(5, 5)); //placer le serpent au milieu de la fenetre
        direction = 1; // Par défaut, le serpent se déplace vers la droite
        crossedBorder = false;
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean hasCrossedBorder() {
        return crossedBorder;
    }

    public void move() {
        Point head = body.getFirst();
        Point newPoint = new Point(head);

        switch (direction) {
            case 0: newPoint.y--; break; // UP
            case 1: newPoint.x++; break; // RIGHT
            case 2: newPoint.y++; break; // DOWN
            case 3: newPoint.x--; break; // LEFT
        }

        crossedBorder = false;

        // verification si le serpent sort de la bordure et  le fait reapparaître de l'autre cote
        if (newPoint.x < 0) {
            newPoint.x = cols - 1;
            crossedBorder = true;
        } else if (newPoint.x >= cols) {
            newPoint.x = 0;
            crossedBorder = true;
        }
        if (newPoint.y < 0) {
            newPoint.y = rows - 1;
            crossedBorder = true;
        } else if (newPoint.y >= rows) {
            newPoint.y = 0;
            crossedBorder = true;
        }

        body.addFirst(newPoint);
        body.removeLast(); // fafana queue an'le serpent sinon lasa lava b izy isaky manao deplacemnt
    }
    //verifier si il y a collision entre tete serpent et pomme
    public boolean hasCollidedWithApple(Pomme apple) {
        return body.getFirst().equals(apple.getPosition());
    }

    public void grow() {
        body.addLast(new Point(body.getLast()));
    }
}
