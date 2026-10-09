package meme;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

// Concrete Product.
public class ComicPanelMeme implements Meme {
    @Override
    public BufferedImage render(List<String> captions) {
        MemeDrawing.validate(captions, 2);
        BufferedImage image = MemeDrawing.canvas();
        Graphics2D g = image.createGraphics();
        try {
            MemeDrawing.background(g);
            g.setColor(java.awt.Color.GRAY);
            g.drawLine(400, 20, 400, 580);
            MemeDrawing.face(g, 120, 140);
            MemeDrawing.face(g, 520, 140);
            MemeDrawing.caption(g, captions.get(0), new Rectangle(25, 360, 350, 210));
            MemeDrawing.caption(g, captions.get(1), new Rectangle(425, 360, 350, 210));
            return image;
        } finally {
            g.dispose();
        }
    }
}
