# FastAIRuntime API Reference

## Core Classes

### `FastAIRuntime`
The primary deterministic execution engine for registering tools and executing command envelopes.

* `register(FastTool tool)`: Registers a local tool implementation.
* `execute(FastCommand command)`: Executes a structured command envelope and returns a `FastObservation`.
* `getTools()`: Returns all registered tools.

### `FastCommand`
Represents an immutable command execution request:
* `name()`: The unique command identifier (e.g. `windows.open_app`).
* `parameters()`: Map of key-value arguments for tool execution.

### `FastObservation`
Structured output returned from a tool execution:
* `success()`: Boolean indicator of execution status.
* `output()`: String payload or serialized result.
* `error()`: Error description if execution failed.
