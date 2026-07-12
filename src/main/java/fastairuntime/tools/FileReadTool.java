package fastairuntime.tools;

import fastairuntime.FastObservation;
import fastairuntime.FastTool;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class FileReadTool implements FastTool {

    @Override
    public String name() {
        return "file.read";
    }

    @Override
    public FastObservation execute(Map<String, Object> args) {
        String pathStr = (String) args.get("path");
        if (pathStr == null || pathStr.isEmpty()) {
            return new SimpleObservation(false, "path argument is missing.");
        }
        try {
            String content = Files.readString(Path.of(pathStr));
            return new SimpleObservation(true, "File content:\n" + content);
        } catch (Exception e) {
            return new SimpleObservation(false, "Failed to read file: " + e.getMessage());
        }
    }

    private record SimpleObservation(boolean success, String message) implements FastObservation {}
}
