package TicTacToe.View.GUI.SplashScreen;

import java.awt.*;
import javax.swing.*;

public class customJPanel extends JPanel {

    private Image image;
    private int w,h;
    public customJPanel(Image img){
        //reads the image
        {
            this.image = img;
            Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            setLayout(null);

        }
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(w,h);
    }
    //this will draw the image
    public void paintComponent(Graphics g)
    {
        g.drawImage(image,0,0,this);
    }
}

