import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Flappybird extends JPanel{
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

    Flappybird() {
        setPreferredSize(new Dimension(boardwidth, boardheight));
       
        bgImage = new ImageIcon("Images\\flappybirdbg.png").getImage();
        birdImage = new ImageIcon("Images\\flappybird.png").getImage();
        topPipeImg = new ImageIcon("Images\\toppipe.png").getImage();
        bottomPipeImg = new ImageIcon("Images\\bottompipe.png").getImage();

        bird = new Bird(birdImage);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, boardwidth, boardheight, null);
        g.drawImage(bird.img, birdX, birdY, birdwidth, birdheight, this);
    }
}
