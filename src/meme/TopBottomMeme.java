package meme;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

// Concrete Product.
public class TopBottomMeme implements Meme {
    @Override
    public BufferedImage render(List<String> captions) {
        MemeDrawing.validate(captions, 2);
        BufferedImage image = MemeDrawing.canvas();
        Graphics2D g = image.createGraphics();
        try {
            MemeDrawing.background(g);
            MemeDrawing.face(g, 320, 220);
            MemeDrawing.caption(g, captions.get(0), new Rectangle(30, 30, 740, 150));
            MemeDrawing.caption(g, captions.get(1), new Rectangle(30, 420, 740, 150));
            return image;
        } finally {
            g.dispose();
        }
    }
}
