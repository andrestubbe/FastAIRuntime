package fastairuntime.tools;

import fastairuntime.FastObservation;
import fastairuntime.FastTool;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

public final class DirectoryListTool implements FastTool {

    @Override
    public String name() {
        return "dir.list";
    }

    @Override
    public FastObservation execute(Map<String, Object> args) {
        String pathStr = (String) args.get("path");
        if (pathStr == null || pathStr.isEmpty()) {
            return new SimpleObservation(false, "path argument is missing.");
        }
        try {
            String content = Files.list(Path.of(pathStr))
                    .map(p -> p.getFileName().toString() + (Files.isDirectory(p) ? "/" : ""))
                    .collect(Collectors.joining("\n"));
            return new SimpleObservation(true, "Directory contents:\n" + content);
        } catch (Exception e) {
            return new SimpleObservation(false, "Failed to list directory: " + e.getMessage());
        }
    }

    private record SimpleObservation(boolean success, String message) implements FastObservation {}
}
