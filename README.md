# Simple Meme Generator Factory

This is the simpler Java solution for the Meme Generator Factory bonus task. It keeps Factory Method, three real PNG meme formats and layout validation. Java 17 or newer is required. No Maven or external libraries.

## Run in IntelliJ

1. Extract this ZIP and open the MemeGeneratorFactory_Simple folder.
2. Choose a Java 17+ JDK as Project SDK.
3. Right-click src → Mark Directory as → Sources Root.
4. Right-click test → Mark Directory as → Test Sources Root.
5. Open src/meme/Main.java and run main with the green button.
6. Choose 1, 2 or 3 in the Run console, then enter your captions.
7. The program prints the full path of output/my-meme.png. Open that image.

To create all three examples, set Program arguments to --demo in the Main run configuration. To run the 24 checks, open test/meme/SimpleTests.java and run its main method.

## Run the included JAR

From the extracted project folder:

```sh
java -jar MemeGeneratorFactory.jar
```

For examples:

```sh
java -jar MemeGeneratorFactory.jar --demo
```

On Windows you can also double-click run.bat or demo.bat if Java is installed and available in PATH.

## Build from source

Linux or macOS:

```sh
mkdir -p out
javac -encoding UTF-8 -d out src/meme/*.java test/meme/*.java
java -cp out meme.Main
java -cp out meme.SimpleTests
```

Windows PowerShell:

```powershell
New-Item -ItemType Directory -Force out
$sourceFiles = Get-ChildItem src/meme/*.java, test/meme/*.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $sourceFiles
java -cp out meme.Main
java -cp out meme.SimpleTests
```

## What became simpler

- 10 source classes instead of 13.
- One shared MemeDrawing helper replaces the separate layout and canvas helpers.
- Standard IllegalArgumentException replaces the custom exception class.
- A fixed 28 px font replaces the font shrinking loop. Words wrap, or the caption is rejected.
- Simple if/else setup replaces menu records and a format registry.
- Comments explain the pattern roles directly.

Both versions meet the task's rule that excessively long text must be rejected or shrunk and must never overflow. This version chooses rejection when wrapping is insufficient. It does not shrink the font.

## Format rules

| Choice | Format | Captions |
| --- | --- | --- |
| 1 | TopBottomMeme | Exactly 2 |
| 2 | SingleCaptionMeme | Exactly 1 |
| 3 | ComicPanelMeme | Exactly 2 |

Blank/null captions and captions exceeding 2000 characters are rejected. Actual font metrics determine word widths and total line height. Glyph bounds are also checked before drawing. A word that is too wide or too many wrapped lines causes an error. Spaces and line breaks are normalized. Unavailable characters are rejected. PNG writing starts after successful rendering.

The output uses built-in simple face drawings, so no image files need downloading. Different installed fonts can slightly change wrapping, but every machine validates the actual font it uses.

## Read the explanation

Open docs/EXPLANATION_RU_EN.md for the class roles, a small code example, the steps for adding the third format and a short defence explanation. The same file contains the UML diagram in Mermaid format. docs/EXTENSION_PROOF.txt records the extension check.

The implementation covers the technical criteria. The live defence point requires your own explanation and demonstration.
