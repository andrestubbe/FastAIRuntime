package fastairuntime.tools;

import fastairuntime.FastObservation;
import fastairuntime.FastTool;
import java.io.IOException;
import java.util.Map;

public final class WindowsAppTool implements FastTool {

    @Override
    public String name() {
        return "windows.open_app";
    }

    @Override
    public FastObservation execute(Map<String, Object> args) {
        String path = (String) args.get("path");
        if (path == null || path.isEmpty()) {
            return new SimpleObservation(false, "Path argument is missing.");
        }
        try {
            new ProcessBuilder(path).start();
            return new SimpleObservation(true, "App started successfully: " + path);
        } catch (IOException e) {
            return new SimpleObservation(false, "Failed to start app: " + e.getMessage());
        }
    }

    private record SimpleObservation(boolean success, String message) implements FastObservation {}
}
