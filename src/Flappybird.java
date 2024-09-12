import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Flappybird extends JPanel implements ActionListener,KeyListener{
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
        int width=birdwidth;
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

    Flappybird() {
        setPreferredSize(new Dimension(boardwidth, boardheight));
        setFocusable(true);
        addKeyListener(this);
       
        bgImage = new ImageIcon("Images\\flappybirdbg.png").getImage();
        birdImage = new ImageIcon("Images\\flappybird.png").getImage();
        topPipeImg = new ImageIcon("Images\\toppipe.png").getImage();
        bottomPipeImg = new ImageIcon("Images\\bottompipe.png").getImage();

        bird = new Bird(birdImage);
        
        gameloop = new Timer(1000 / 60, this);
        gameloop.start();

    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, boardwidth, boardheight, null);
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
    }

    public void move() {
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -20;
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyReleased(KeyEvent e) {}
}
