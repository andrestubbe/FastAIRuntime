package demo;

import fastairuntime.FastAIRuntime;
import fastairuntime.FastCommand;
import fastairuntime.FastObservation;
import fastairuntime.tools.FileSaveTool;
import fastairuntime.tools.KeyboardTypeTool;
import fastairuntime.tools.WindowsAppTool;
import java.util.Map;

public final class RuntimeDemo {

    public static void main(String[] args) {
        System.out.println("=== Starting Deterministic FastAIRuntime Demo ===");

        FastAIRuntime runtime = new FastAIRuntime();
        runtime.register(new WindowsAppTool());
        runtime.register(new KeyboardTypeTool());
        runtime.register(new FileSaveTool());

        // 1. Start Notepad
        FastObservation obs1 = runtime.execute(new FastCommand("windows.open_app", Map.of("path", "notepad.exe")));
        System.out.println("Step 1 (Open App): success=" + obs1.success() + ", msg=" + obs1.message());

        // 2. Type Text
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {} // wait for Notepad focus
        FastObservation obs2 = runtime.execute(new FastCommand("keyboard.type", Map.of("text", "Hello World")));
        System.out.println("Step 2 (Type): success=" + obs2.success() + ", msg=" + obs2.message());

        // 3. Save mock file
        FastObservation obs3 = runtime.execute(new FastCommand("file.save", Map.of(
            "path", "data/hello_runtime.txt",
            "content", "Hello from FastAIRuntime!"
        )));
        System.out.println("Step 3 (Save): success=" + obs3.success() + ", msg=" + obs3.message());
    }
}
