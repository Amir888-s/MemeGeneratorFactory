package meme;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.imageio.ImageIO;

// Creator: subclasses decide which meme to create.
public abstract class MemeCreator {
    public abstract Meme createMeme(); // Factory Method

    public BufferedImage generate(List<String> captions) {
        Meme meme = createMeme();
        return meme.render(captions);
    }

    public void save(List<String> captions, Path file) throws IOException {
        BufferedImage image = generate(captions);
        Files.createDirectories(file.toAbsolutePath().getParent());
        if (!ImageIO.write(image, "png", file.toFile())) {
            throw new IOException("Cannot save PNG.");
        }
    }
}
