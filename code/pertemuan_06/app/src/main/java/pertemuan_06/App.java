package pertemuan_06;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceFileReader;

// Converts top-level .puml files from an input folder into an output folder.
public class App {
    private static final String PUML_EXTENSION = ".puml";
    private static final String DEFAULT_OUTPUT_FORMAT = "svg";

    // Starts the CLI app and reads arguments: inputDir outputDir [format].
    public static void main(String[] args) throws IOException {
        System.out.println("PlantUML Batch Renderer");

        System.setProperty("java.awt.headless", "true");

        Path inputDir = Path.of(args[0]);
        Path outputDir = Path.of(args[1]);

        FileFormat outputFormat = parseFormat(args.length == 3 ? args[2] : DEFAULT_OUTPUT_FORMAT);
        System.out.printf("Output format: %s%n", outputFormat.toString());
        int processedFiles = generateFromDirectory(inputDir, outputDir, outputFormat);
        System.out.printf("Processed %d PlantUML file(s).%n", processedFiles);
    }

    // Finds top-level .puml files in inputDir and renders them to outputDir.
    static int generateFromDirectory(Path inputDir, Path outputDir, FileFormat outputFormat) throws IOException {
        inputDir = inputDir.toAbsolutePath().normalize();
        outputDir = outputDir.toAbsolutePath().normalize();

        Files.createDirectories(outputDir);

        List<Path> pumlFiles;
        try (Stream<Path> files = Files.list(inputDir)) {
            pumlFiles = files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().toLowerCase().endsWith(PUML_EXTENSION))
                    .sorted()
                    .toList();
        }

        for (Path pumlFile : pumlFiles) {
            renderOneFile(pumlFile, outputDir, outputFormat);
        }

        return pumlFiles.size();
    }

    // Renders one .puml file using the selected output format.
    private static void renderOneFile(Path pumlFile, Path outputDir, FileFormat outputFormat)
            throws IOException {
        System.out.printf("Processing: %s%n", pumlFile.getFileName());
        FileFormatOption formatOption = new FileFormatOption(outputFormat);
        new SourceFileReader(pumlFile.toFile(), outputDir.toFile(), formatOption).getGeneratedImages();
    }

    // Converts text format (e.g. "svg", "png") into PlantUML FileFormat.
    private static FileFormat parseFormat(String rawFormat) {
        String normalized = rawFormat.trim().toUpperCase(Locale.ROOT).replace('-', '_');
        return FileFormat.valueOf(normalized);
    }
}
