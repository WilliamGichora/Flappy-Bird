import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Flappybird extends JPanel implements ActionListener, KeyListener {
    int boardwidth = 360;
    int boardheight = 640;

    Image bgImage;
    Image birdImage;
    Image topPipeImg;
    Image bottomPipeImg;

    int birdX = boardwidth / 8;
    int birdY = boardheight / 2;
    int birdwidth = 34;
    int birdheight = 24;

    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdwidth;
        int height = birdheight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    Bird bird;
    int velocityY = 0;
    int gravity = 1;

    Timer gameloop;

    int pipeX = boardwidth;
    int pipeY = 0;
    int pipewidth = 64;
    int pipeHeight = 512;
    Random rand = new Random();

    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipewidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;
        boolean isTopPipe;

        Pipe(Image img, Boolean isTopPipe) {
            this.img = img;
            this.isTopPipe = isTopPipe;
        }
    }

    int velocityX = -4;
    ArrayList<Pipe> pipes;
    Timer placePipesTimer;

    Flappybird() {
        setPreferredSize(new Dimension(boardwidth, boardheight));
        setFocusable(true);
        addKeyListener(this);

        bgImage = new ImageIcon("Images\\flappybirdbg.png").getImage();
        birdImage = new ImageIcon("Images\\flappybird.png").getImage();
        topPipeImg = new ImageIcon("Images\\toppipe.png").getImage();
        bottomPipeImg = new ImageIcon("Images\\bottompipe.png").getImage();

        bird = new Bird(birdImage);
        pipes = new ArrayList<>();

        placePipesTimer = new Timer(1500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }

        });

        gameloop = new Timer(1000 / 60, this);
        placePipesTimer.start();
        gameloop.start();

    }

    public void placePipes() {
        Pipe topPipe = new Pipe(topPipeImg, true);
        topPipe.height = rand.nextInt((300 - 250 + 1) + 250);
        pipes.add(topPipe);

        int openingSpace = boardheight / 5;

        Pipe bottomPipe = new Pipe(bottomPipeImg, false);
        bottomPipe.height = boardheight - topPipe.height - openingSpace;
        bottomPipe.y = boardheight - bottomPipe.height;
        pipes.add(bottomPipe);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, boardwidth, boardheight, null);
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
    }

    public void move() {
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;
        }
        loseLogic();
    }

    public void loseLogic() {
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);

            boolean birdCollidesHorizontally = bird.x + bird.width >= pipe.x+2 && bird.x <= pipe.x + pipe.width;

            if (pipe.isTopPipe) {
                if (birdCollidesHorizontally && bird.y <= (pipe.y + pipe.height)) {
                    gameOver();
                    return;
                }
            }
            else {
                if (birdCollidesHorizontally && bird.y + bird.height >= pipe.y+10) {
                    gameOver();
                    return;
                }
            }
        }
    }

    public void gameOver() {
        JOptionPane.showMessageDialog(null, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        placePipesTimer.stop();
        gameloop.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -10;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
