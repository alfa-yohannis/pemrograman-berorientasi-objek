package pertemuan_06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import net.sourceforge.plantuml.FileFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @TempDir
    Path tempDir;

    @Test
    void generateFromDirectoryRendersSvg() throws IOException {
        Path inputDir = tempDir.resolve("input");
        Path outputDir = tempDir.resolve("output");
        Path nested = inputDir.resolve("nested");
        Files.createDirectories(nested);

        Files.writeString(inputDir.resolve("top-level.puml"), """
                @startuml
                Alice -> Bob: Top
                @enduml
                """);

        Files.writeString(nested.resolve("nested.puml"), """
                @startuml
                Alice -> Bob: Nested
                @enduml
                """);

        int processedFiles = App.generateFromDirectory(inputDir, outputDir, FileFormat.SVG);

        assertEquals(1, processedFiles);
        assertTrue(Files.exists(outputDir.resolve("top-level.svg")));
        assertFalse(Files.exists(outputDir.resolve("nested.svg")));
    }

    @Test
    void generateFromDirectorySupportsParameterizedFormat() throws IOException {
        Path inputDir = tempDir.resolve("input-png");
        Path outputDir = tempDir.resolve("output-png");
        Files.createDirectories(inputDir);
        Files.writeString(inputDir.resolve("diagram.puml"), """
                @startuml
                Bob -> Alice: Pong
                @enduml
                """);

        int processedFiles = App.generateFromDirectory(inputDir, outputDir, FileFormat.PNG);

        assertEquals(1, processedFiles);
        assertTrue(Files.exists(outputDir.resolve("diagram.png")));
    }
}
