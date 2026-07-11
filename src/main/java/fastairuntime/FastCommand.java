package fastairuntime;

import java.util.Map;

public record FastCommand(String toolName, Map<String, Object> args) {}
