package meme;

// Concrete Creator.
public class SingleCaptionCreator extends MemeCreator {
    @Override
    public Meme createMeme() {
        return new SingleCaptionMeme();
    }
}
