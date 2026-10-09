package meme;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

// Shared drawing and validation. No format selection happens here.
public class MemeDrawing {
    public static BufferedImage canvas() {
        return new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
    }

    public static void background(Graphics2D g) {
        g.setColor(new Color(25, 35, 55));
        g.fillRect(0, 0, 800, 600);
    }

    public static void face(Graphics2D g, int x, int y) {
        g.setColor(new Color(255, 200, 70));
        g.fillOval(x, y, 160, 160);
        g.setColor(Color.BLACK);
        g.fillOval(x + 45, y + 45, 15, 15);
        g.fillOval(x + 100, y + 45, 15, 15);
        g.drawArc(x + 40, y + 65, 80, 60, 180, 180);
    }

    public static void validate(List<String> captions, int count) {
        if (captions == null || captions.size() != count) {
            throw new IllegalArgumentException("Need exactly " + count + " caption(s).");
        }
        for (String text : captions) {
            if (text == null || text.isBlank()) {
                throw new IllegalArgumentException("Caption cannot be empty.");
            }
            if (text.length() > 2000) {
                throw new IllegalArgumentException("Caption is too long.");
            }
        }
    }

    // Fixed 28 px font: wrap words or reject text that cannot fit.
    public static void caption(Graphics2D g, String text, Rectangle box) {
        Font font = new Font("SansSerif", Font.BOLD, 28);
        g.setFont(font);
        g.setColor(Color.WHITE);
        String clean = text.strip().replaceAll("\\s+", " ");
        if (font.canDisplayUpTo(clean) != -1) {
            throw new IllegalArgumentException("Font cannot display this caption.");
        }
        FontMetrics fm = g.getFontMetrics();
        List<String> lines = new ArrayList<>();
        String line = "";
        for (String word : clean.split(" ")) {
            if (width(g, word) > box.width) {
                throw new IllegalArgumentException("A word is too wide. Shorten it.");
            }
            String next = line.isEmpty() ? word : line + " " + word;
            if (width(g, next) <= box.width) {
                line = next;
            } else {
                lines.add(line);
                line = word;
            }
        }
        lines.add(line);
        if (lines.size() * fm.getHeight() > box.height) {
            throw new IllegalArgumentException("Too many lines. Shorten the caption.");
        }
        int y = box.y + fm.getAscent();
        for (String current : lines) {
            Rectangle2D bounds = font.createGlyphVector(
                    g.getFontRenderContext(), current).getVisualBounds();
            int x = (int) Math.ceil(box.x + (box.width - bounds.getWidth()) / 2
                    - bounds.getX());
            if (x + bounds.getX() < box.x || x + bounds.getMaxX() > box.getMaxX()
                    || y + bounds.getY() < box.y || y + bounds.getMaxY() > box.getMaxY()) {
                throw new IllegalArgumentException("Text does not fit in its area.");
            }
            g.drawString(current, x, y);
            y += fm.getHeight();
        }
    }

    private static double width(Graphics2D g, String text) {
        Rectangle2D bounds = g.getFont().createGlyphVector(
                g.getFontRenderContext(), text).getVisualBounds();
        return Math.max(g.getFontMetrics().stringWidth(text), bounds.getWidth());
    }
}
