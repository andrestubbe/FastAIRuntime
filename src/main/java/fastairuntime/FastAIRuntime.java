package fastairuntime;

import java.util.HashMap;
import java.util.Map;

public final class FastAIRuntime {

    private final Map<String, FastTool> registry = new HashMap<>();

    public void register(FastTool tool) {
        registry.put(tool.name(), tool);
    }

    public FastObservation execute(FastCommand command) {
        FastTool tool = registry.get(command.toolName());
        if (tool == null) {
            return new FastObservation() {
                @Override public boolean success() { return false; }
                @Override public String message() { return "Tool not found: " + command.toolName(); }
            };
        }
        try {
            return tool.execute(command.args());
        } catch (Exception e) {
            return new FastObservation() {
                @Override public boolean success() { return false; }
                @Override public String message() { return "Execution error: " + e.getMessage(); }
            };
        }
    }
}
