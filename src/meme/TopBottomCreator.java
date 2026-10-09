package meme;

// Concrete Creator.
public class TopBottomCreator extends MemeCreator {
    @Override
    public Meme createMeme() {
        return new TopBottomMeme();
    }
}
