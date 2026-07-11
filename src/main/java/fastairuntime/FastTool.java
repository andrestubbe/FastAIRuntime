package fastairuntime;

import java.util.Map;

public interface FastTool {
    String name();
    FastObservation execute(Map<String, Object> args);
}
