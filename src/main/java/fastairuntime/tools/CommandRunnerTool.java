package fastairuntime.tools;

import fastairuntime.FastObservation;
import fastairuntime.FastTool;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

public final class CommandRunnerTool implements FastTool {

    @Override
    public String name() {
        return "os.run_command";
    }

    @Override
    public FastObservation execute(Map<String, Object> args) {
        String command = (String) args.get("command");
        if (command == null || command.isEmpty()) {
            return new SimpleObservation(false, "command argument is missing.");
        }
        try {
            // Run via cmd.exe on Windows for built-in commands like dir, ping, etc.
            Process process = new ProcessBuilder("cmd.exe", "/c", command).start();
            
            String output = new BufferedReader(new InputStreamReader(process.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));
            String error = new BufferedReader(new InputStreamReader(process.getErrorStream()))
                    .lines().collect(Collectors.joining("\n"));
            
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return new SimpleObservation(true, "Command succeeded:\n" + output);
            } else {
                return new SimpleObservation(false, "Command failed (exit " + exitCode + "):\n" + error + "\n" + output);
            }
        } catch (Exception e) {
            return new SimpleObservation(false, "Error running command: " + e.getMessage());
        }
    }

    private record SimpleObservation(boolean success, String message) implements FastObservation {}
}
