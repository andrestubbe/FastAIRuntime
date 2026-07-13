package fastairuntime;

import java.util.Map;

public interface FastTool {
    String name();
    default String description() { return ""; }
    FastObservation execute(Map<String, Object> args);
}
