package meme;

import java.awt.image.BufferedImage;
import java.util.List;

// Product: every meme has the same render method.
public interface Meme {
    BufferedImage render(List<String> captions);
}
