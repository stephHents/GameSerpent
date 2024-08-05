package panel;

import snake.*;
import apple.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.LinkedList;

public class GamePanel extends JPanel implements ActionListener {
    private final int DELAY = 150; // vitesse serpent
    private Timer timer;
    private Snake snake;
    private Pomme apple;
    private int score;

    public GamePanel() {
        setBackground(Color.GREEN);
        setFocusable(true);
        setPreferredSize(new Dimension(800, 600));
        addKeyListener(new TAdapter());

        snake = new Snake(20, 20); // Les dimensions de la grille serpent
        apple = new Pomme(20, 20); // Les dimensions de la grille pomme
        score = 0;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner la grille
        int width = getWidth();
        int height = getHeight();
        int rows = 20;
        int cols = 20;
        int cellWidth = width / cols;
        int cellHeight = height / rows;

        g.setColor(Color.BLACK);
        for (int i = 0; i <= rows; i++) {
            g.drawLine(0, i * cellHeight, width, i * cellHeight);
        }
        for (int i = 0; i <= cols; i++) {
            g.drawLine(i * cellWidth, 0, i * cellWidth, height);
        }

        // Dessiner le serpent
        for (Point point : snake.getBody()) {
            g.setColor(Color.BLUE);
            g.fillOval(point.x * cellWidth, point.y * cellHeight, cellWidth, cellHeight);
        }

        // Dessiner la pomme
        g.setColor(Color.RED);
        Point applePosition = apple.getPosition();
        g.fillOval(applePosition.x * cellWidth, applePosition.y * cellHeight, cellWidth, cellHeight);

        // Afficher le score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        snake.move();

        if (snake.hasCrossedBorder()) {
            score--;
        }

        if (snake.hasCollidedWithApple(apple)) {
            snake.grow();
            apple.generateNewPosition();
            score++;
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            switch (key) {
                case KeyEvent.VK_UP:
                    if (snake.getDirection() != 2) {
                        snake.setDirection(0);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (snake.getDirection() != 3) {
                        snake.setDirection(1);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (snake.getDirection() != 0) {
                        snake.setDirection(2);
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    if (snake.getDirection() != 1) {
                        snake.setDirection(3);
                    }
                    break;
            }
        }
    }

    public void saveGame() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("game.dat"))) {
            out.writeObject(snake);
            out.writeObject(apple);
            out.writeInt(score);
            JOptionPane.showMessageDialog(this, "Sauvegarde réussie!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            requestFocusInWindow(); // Assure que le panel a le focus après sauvegarde:
        }
    }

    public void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("game.dat"))) {
            snake = (Snake) in.readObject();
            apple = (Pomme) in.readObject();
            score = in.readInt();
            requestFocusInWindow(); // Assure que le panel a le focus après chargement
            timer.restart(); // Redémarrer le Timer:pour s'assurer que le jeu continue de fonctionner normalement.
            repaint();
            JOptionPane.showMessageDialog(this, "Chargement réussi!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement.", "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } 
    }
}
