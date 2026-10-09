package meme;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

// Concrete Product.
public class SingleCaptionMeme implements Meme {
    @Override
    public BufferedImage render(List<String> captions) {
        MemeDrawing.validate(captions, 1);
        BufferedImage image = MemeDrawing.canvas();
        Graphics2D g = image.createGraphics();
        try {
            MemeDrawing.background(g);
            MemeDrawing.face(g, 320, 130);
            MemeDrawing.caption(g, captions.get(0), new Rectangle(30, 360, 740, 210));
            return image;
        } finally {
            g.dispose();
        }
    }
}
