package guiClient.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CustomJTextArea extends JTextArea {
    private final BufferedImage bufferedImage;
    private final TexturePaint texturePaint;

    public CustomJTextArea (File file) throws IOException {
        super();
        bufferedImage = ImageIO.read(file);
        Rectangle rect = new Rectangle(0, 0, bufferedImage.getWidth(null), bufferedImage.getHeight(null));
        texturePaint = new TexturePaint(bufferedImage, rect);
        setOpaque(false);
    }
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(texturePaint);
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
