package meme;

// Concrete Creator.
public class ComicPanelCreator extends MemeCreator {
    @Override
    public Meme createMeme() {
        return new ComicPanelMeme();
    }
}
