package fastairuntime.tools;

import fastairuntime.FastObservation;
import fastairuntime.FastTool;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class FileSaveTool implements FastTool {

    @Override
    public String name() {
        return "file.save";
    }

    @Override
    public FastObservation execute(Map<String, Object> args) {
        String pathStr = (String) args.get("path");
        String content = (String) args.get("content");
        if (pathStr == null || content == null) {
            return new SimpleObservation(false, "Arguments path or content missing.");
        }
        try {
            Path path = Path.of(pathStr);
            Path parent = path.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent);
            }
            Files.writeString(path, content);
            return new SimpleObservation(true, "File saved at: " + path.toAbsolutePath());
        } catch (IOException e) {
            return new SimpleObservation(false, "Failed to save file: " + e.getMessage());
        }
    }

    private record SimpleObservation(boolean success, String message) implements FastObservation {}
}
