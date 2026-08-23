# FastAIRuntime 0.1.0 — Deterministic Execution Engine for Java

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Windows%2010+-lightgrey.svg)]()
[![JitPack](https://img.shields.io/badge/JitPack-ready-green.svg)](https://jitpack.io/#andrestubbe)

---

**💡 Low-latency command execution and tool registries — Built for reliable local-first agent control in the FastJava AI ecosystem.**

FastAIRuntime is a **deterministic execution shell** that manages system automation tools. By wrapping processes, hotkeys, and files behind strictly structured, observable execution boundaries (tools/commands), it enables cognitive agents to interact safely with the host operating system.

---

## Technical Features

- **🛡️ Secure Execution Boundaries** — Tools run via explicit command envelopes (`FastCommand`), yielding structured feedback (`FastObservation`).
- **🔧 Unified Tool Registry** — Modular architecture to register local system utilities (UIA, Process tools, Keyboards).
- **⚡ Zero Bloat** — Pure Java 17+ core with no external dependencies.
- **🚀 Native Integrations** — Deeply integrated with FastJava's native subsystems (FastTerminal, FastUIA).

---

## Quick Start

```java
import fastairuntime.*;
import fastairuntime.tools.*;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {
        FastAIRuntime runtime = new FastAIRuntime();
        
        // 1. Register deterministic OS tools
        runtime.register(new WindowsAppTool());
        runtime.register(new KeyboardTypeTool());

        // 2. Execute command
        FastObservation obs = runtime.execute(new FastCommand(
            "windows.open_app", 
            Map.of("path", "notepad.exe")
        ));
        
        System.out.println("Execution success: " + obs.success());
    }
}
```

---

## Installation

### Maven (JitPack)
Add JitPack repository and dependency to your `pom.xml`:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>FastAIRuntime</artifactId>
        <version>0.1.0</version>
    </dependency>
</dependencies>
```

---

## Related Projects

- [FastAI](https://github.com/andrestubbe/FastAI) — Unified AI client interface for Java
- [FastAIAgent](https://github.com/andrestubbe/FastAIAgent) — Autonomous agent loop, intent-graphs, and tool execution
- [FastAIBot](https://github.com/andrestubbe/FastAIBot) — Zero-bloat bot harnesses and persona runtime
- [FastAIGraph](https://github.com/andrestubbe/FastAIGraph) — In-memory knowledge graph and multi-hop relationship engine
- [FastAIHybrid](https://github.com/andrestubbe/FastAIHybrid) — Dense-sparse hybrid search fusion (BM25 + Vectors)
- [FastAIMCP](https://github.com/andrestubbe/FastAIMCP) — Model Context Protocol (MCP) server & tool integration
- [FastAIMemory](https://github.com/andrestubbe/FastAIMemory) — Conversation history, sliding windows, and rolling summaries
- [FastAIModel](https://github.com/andrestubbe/FastAIModel) — Native local inference runtime (GGUF/ONNX)
- [FastAIRag](https://github.com/andrestubbe/FastAIRag) — Ultra-fast document chunking and vector retrieval
- [FastAIReasoner](https://github.com/andrestubbe/FastAIReasoner) — Deterministic planning, chain-of-thought, and self-correction
- [FastAIRerank](https://github.com/andrestubbe/FastAIRerank) — Cross-encoder relevance filtering and Top-N prompt pruner
- [FastAIRuntime](https://github.com/andrestubbe/FastAIRuntime) — Sandboxed process runner and tool-calling execution pipeline
- [FastAIVectorDB](https://github.com/andrestubbe/FastAIVectorDB) — High-throughput SIMD/AVX2 vector database
- [FastCore](https://github.com/andrestubbe/FastCore) — Unified JNI loader and platform abstraction

---

**Part of the FastJava Ecosystem** — *Making the JVM faster. Small package. Maximum speed. Zero bloat. 🚀📋*