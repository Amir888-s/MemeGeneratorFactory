package meme;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equals("--demo")) {
            new TopBottomCreator().save(List.of("WHEN THE CODE COMPILES", "ON THE FIRST TRY"),
                    Path.of("output/top-bottom.png"));
            new SingleCaptionCreator().save(List.of("IT WORKED ON MY COMPUTER"),
                    Path.of("output/single-caption.png"));
            new ComicPanelCreator().save(List.of("BEFORE FACTORY METHOD", "AFTER FACTORY METHOD"),
                    Path.of("output/comic-panel.png"));
            System.out.println("Three PNG examples saved in output.");
            return;
        }
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("1. Top and bottom text");
            System.out.println("2. Single caption");
            System.out.println("3. Comic panel");
            System.out.print("Choose a format: ");
            String choice = input.nextLine();
            MemeCreator creator;
            int count;
            // Only the application setup chooses a creator.
            if (choice.equals("1")) {
                creator = new TopBottomCreator();
                count = 2;
            } else if (choice.equals("2")) {
                creator = new SingleCaptionCreator();
                count = 1;
            } else if (choice.equals("3")) {
                creator = new ComicPanelCreator();
                count = 2;
            } else {
                System.out.println("Invalid choice.");
                return;
            }
            List<String> captions = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                System.out.print("Caption " + (i + 1) + ": ");
                captions.add(input.nextLine());
            }
            try {
                creator.save(captions, Path.of("output/my-meme.png"));
                System.out.println("Saved: " + Path.of("output/my-meme.png").toAbsolutePath());
            } catch (IllegalArgumentException ex) {
                System.out.println("Rejected: " + ex.getMessage());
            }
        }
    }
}
