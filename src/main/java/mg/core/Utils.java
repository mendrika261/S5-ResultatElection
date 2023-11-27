package mg.core;

import lombok.Cleanup;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {

    public static String readFile(String filepath) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        return stringBuilder.toString();
    }

    public static void writeFile(String filepath, String content) throws IOException {
        Path path = Path.of(filepath);
        Files.writeString(path, content);
    }

    public static String[][] readCsv(String path) throws IOException {
        String separator = ",";

        String content = readFile(path);
        String[] lines = content.split("\n");
        String[][] data = new String[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            data[i] = lines[i].split(separator);
        }
        return data;
    }

    public static String[] listFiles(String directoryPath) {
        try {
            return Files.list(Paths.get(directoryPath))
                    .map(Path::toString)
                    .toList()
                    .toArray(new String[0]);
        } catch (IOException e) {
            throw  new RuntimeException("Erreur lors de la lecture du dossier " + directoryPath);
        }
    }
}
