package fastairuntime.tools;

import fastairuntime.FastObservation;
import fastairuntime.FastTool;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

public final class FileEditTool implements FastTool {

    @Override
    public String name() {
        return "file.edit";
    }

    @Override
    public String description() {
        return "Edits an existing file by replacing target text with new replacement text. Args: {path: String, target: String, replacement: String}";
    }

    @Override
    public FastObservation execute(Map<String, Object> args) {
        String path = (String) args.get("path");
        String target = (String) args.get("target");
        String replacement = (String) args.get("replacement");

        if (path == null || target == null || replacement == null) {
            return new SimpleObservation(false, "Missing required arguments: path, target, and replacement are required.");
        }

        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            return new SimpleObservation(false, "File not found: " + path);
        }

        try {
            String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            if (!content.contains(target)) {
                return new SimpleObservation(false, "Target string not found in file: " + path);
            }
            String updated = content.replace(target, replacement);
            Files.writeString(file.toPath(), updated, StandardCharsets.UTF_8);
            return new SimpleObservation(true, "Successfully updated file: " + path);
        } catch (IOException e) {
            return new SimpleObservation(false, "Failed to edit file: " + e.getMessage());
        }
    }

    private record SimpleObservation(boolean success, String message) implements FastObservation {}
}
