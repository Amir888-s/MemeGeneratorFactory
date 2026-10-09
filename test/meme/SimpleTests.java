package meme;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import javax.imageio.ImageIO;

public class SimpleTests {
    private static int checks;

    public static void main(String[] args) throws Exception {
        List<MemeCreator> creators = List.of(new TopBottomCreator(),
                new SingleCaptionCreator(), new ComicPanelCreator());
        for (int i = 0; i < creators.size(); i++) {
            MemeCreator creator = creators.get(i);
            List<String> captions = i == 1 ? List.of("HELLO") : List.of("HELLO", "WORLD");
            BufferedImage image = creator.generate(captions);
            check(image.getWidth() == 800 && image.getHeight() == 600);
            rejects(() -> creator.generate(null));
            rejects(() -> creator.generate(List.of()));
            rejects(() -> creator.generate(List.of("A", "B", "C")));
            List<String> blank = i == 1 ? List.of(" ") : List.of(" ", "OK");
            rejects(() -> creator.generate(blank));
            List<String> wide = i == 1 ? List.of("W".repeat(100))
                    : List.of("W".repeat(100), "OK");
            rejects(() -> creator.generate(wide));
            List<String> tall = i == 1 ? List.of("word ".repeat(200))
                    : List.of("word ".repeat(200), "OK");
            rejects(() -> creator.generate(tall));
        }
        BufferedImage image = MemeDrawing.canvas();
        Graphics2D g = image.createGraphics();
        Rectangle box = new Rectangle(100, 100, 220, 200);
        try {
            MemeDrawing.caption(g, "HELLO WORLD HELLO WORLD", box);
            // This caption must wrap at 220 px, and must stay inside its box.
            boolean secondLine = false;
            boolean outside = false;
            int lineHeight = g.getFontMetrics().getHeight();
            for (int y = 0; y < 600; y++) {
                for (int x = 0; x < 800; x++) {
                    if ((image.getRGB(x, y) & 0xffffff) != 0) {
                        if (!box.contains(x, y)) outside = true;
                        if (y >= box.y + lineHeight) secondLine = true;
                    }
                }
            }
            check(secondLine);
            check(!outside);
        } finally { g.dispose(); }
        Path folder = Files.createTempDirectory("simple-meme-");
        Path file = folder.resolve("test.png");
        try {
            new ComicPanelCreator().save(List.of("BEFORE", "AFTER"), file);
            check(ImageIO.read(file.toFile()) != null);
        } finally {
            Files.deleteIfExists(file);
            Files.deleteIfExists(folder);
        }
        System.out.println("PASS: " + checks + " checks");
    }

    private static void check(boolean condition) {
        if (!condition) throw new AssertionError("Check failed");
        checks++;
    }

    private static void rejects(Runnable action) {
        try { action.run(); }
        catch (IllegalArgumentException expected) { checks++; return; }
        throw new AssertionError("Expected rejection");
    }
}
