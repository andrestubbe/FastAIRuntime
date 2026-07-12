package fastairuntime.tools;

import fastairuntime.FastObservation;
import fastairuntime.FastTool;
import java.io.IOException;
import java.util.Map;

public final class WindowsCloseAppTool implements FastTool {

    @Override
    public String name() {
        return "windows.close_app";
    }

    @Override
    public FastObservation execute(Map<String, Object> args) {
        String processName = (String) args.get("process_name");
        if (processName == null || processName.isEmpty()) {
            return new SimpleObservation(false, "process_name argument is missing.");
        }
        if (!processName.toLowerCase().endsWith(".exe")) {
            processName += ".exe";
        }
        try {
            Process process = new ProcessBuilder("taskkill", "/IM", processName, "/F").start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return new SimpleObservation(true, "App closed successfully: " + processName);
            } else {
                // Exit code 128 usually means process not found
                return new SimpleObservation(false, "Failed to close app (exit code " + exitCode + "): " + processName);
            }
        } catch (Exception e) {
            return new SimpleObservation(false, "Error closing app: " + e.getMessage());
        }
    }

    private record SimpleObservation(boolean success, String message) implements FastObservation {}
}
