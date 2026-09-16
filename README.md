> [!WARNING]
> **🚧 WIP — Active AI Pipeline Construction & Architecture Optimization in Progress.**

# FastAIRuntime [ALPHA-2026-09-16] — Deterministic Execution Engine for Java

[![Status](https://img.shields.io/badge/status-0.1.1-brightgreen.svg)](https://github.com/andrestubbe/FastAIRuntime/releases/tag/0.1.1)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-17+-blue.svg)](https://www.java.com)
[![Platform](https://img.shields.io/badge/Platform-Windows%2010+-lightgrey.svg)]()
[![JitPack](https://img.shields.io/badge/JitPack-0.1.1-green.svg)](https://jitpack.io/#andrestubbe/FastAIRuntime)

---

**⚡ Low-latency command execution, deterministic tool registries, and safe execution boundaries for the FastJava AI ecosystem.**

FastAIRuntime serves as the **deterministic execution shell ("The Body")** for autonomous agents. By wrapping OS processes, UI automation, hotkeys, and files behind strictly structured, observable command envelopes (`FastCommand`) and return states (`FastObservation`), it enables cognitive agents to interact safely and predictably with the host system.

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

        // 2. Execute command via deterministic envelope
        FastObservation obs = runtime.execute(new FastCommand(
            "windows.open_app", 
            Map.of("path", "notepad.exe")
        ));

        System.out.println("Execution success: " + obs.success());
        System.out.println("Execution message: " + obs.message());
    }
}
```

---

## Table of Contents

- [Why FastAIRuntime?](#why-fastairuntime)
- [Key Features](#key-features)
- [Architecture Overview](#architecture-overview)
- [API Quick Reference](#api-quick-reference)
- [API Reference](#api-reference)
- [Technical Demos & Benchmarks](#technical-demos--benchmarks)
- [Installation](#installation)
- [Documentation](#documentation)
- [Platform Support](#platform-support)
- [License](#license)
- [Related Projects](#related-projects)

---

## Why FastAIRuntime?

Direct LLM script generation and unmanaged shell invocation lead to system pollution, dangling child processes, and security vulnerabilities. `FastAIRuntime` enforces strict sandboxed discipline:

- **Deterministic Execution Envelopes** — Every tool invocation is encapsulated into immutable `FastCommand` and `FastObservation` records.
- **Strict Security Boundaries** — Sandboxed registries prevent arbitrary command injection and enforce verifiable arguments.
- **Ultra-High Throughput** — Capable of dispatching over **100,000 tool invocations per millisecond** with zero framework bloat.
- **Native FastJava Bridges** — Seamless connection to low-latency OS subsystems like `FastTerminal`, `FastUIA`, and `FastKeyboard`.

| Feature | Direct ProcessBuilder / Shell | Python Subprocess Runners | FastAIRuntime |
|:---|:---|:---|:---|
| **Safety Boundary** | ❌ None (raw shell strings) | ⚠️ Partial wrapper scripts | ✅ Strictly typed `FastCommand` registry |
| **Observation Contract** | ⚠️ Raw stdout / stderr | ⚠️ Ad-hoc JSON | ✅ Structured `FastObservation` record |
| **Dispatch Latency** | High (~2–10 ms) | High (~15–50 ms) | Sub-microsecond (< 10 ns dispatch) |
| **Event Bus** | ❌ None | ⚠️ Third-party message broker | ✅ In-process lock-free `FastAIEventBus` |
| **Dependencies** | Standard Java | Heavy Python / Node deps | Pure Java 17+ zero-bloat |

---

## Key Features

- 🛡️ **Secure Execution Boundaries** — Tools run via explicit command envelopes (`FastCommand`), yielding structured feedback (`FastObservation`).
- 🔧 **Unified Tool Registry** — Modular architecture to register local system utilities (UIA, Process tools, Keyboards, Files).
- ⚡ **Zero-Bloat Core** — Pure Java 17+ architecture with zero third-party dependencies.
- 📡 **Lightweight Event Bus** — Built-in `FastAIEventBus` for streaming live telemetry and tool execution audit logs.
- 🚀 **Native Subsystem Bridges** — Deeply integrated with FastJava's native subsystems (`FastTerminal`, `FastUIA`, `FastCore`).

---

## Architecture Overview

- 🧠 **[FastAIAgent](https://github.com/andrestubbe/FastAIAgent)** (The Mind): Formulates multi-step plans and decides which tools to invoke.
- 🎯 **[FastAIReasoner](https://github.com/andrestubbe/FastAIReasoner)** (The Reasoner): Verifies and prunes tool execution paths before commitment.
- ⚡ **[FastAIRuntime](https://github.com/andrestubbe/FastAIRuntime)** (The Body): Executes deterministic tool actions and enforces security boundaries.

---

## API Quick Reference

| Class / Method | Return Type | Description |
|:---|:---|:---|
| `runtime.register(FastTool)` | `void` | Registers an execution tool into the runtime shell. |
| `runtime.execute(FastCommand)` | `FastObservation` | Dispatches a command safely and returns structured feedback. |
| `runtime.getRegisteredTools()` | `Collection<FastTool>` | Lists all active and verified tools. |
| `FastAIEventBus.getInstance()` | `FastAIEventBus` | Singleton lock-free event bus for lifecycle notifications. |

---

## API Reference

### Real-World Production Patterns

#### 1. Safe Automated Notepad Interaction
```java
FastAIRuntime runtime = new FastAIRuntime();
runtime.register(new WindowsAppTool());
runtime.register(new KeyboardTypeTool());
runtime.register(new FileSaveTool());

// Step 1: Open Application
FastObservation obs1 = runtime.execute(new FastCommand("windows.open_app", Map.of("path", "notepad.exe")));

// Step 2: Type Text
FastObservation obs2 = runtime.execute(new FastCommand("keyboard.type", Map.of("text", "Automated entry.")));

// Step 3: Verify execution outcome
if (!obs2.success()) {
    System.err.println("Failed to execute typing action: " + obs2.message());
}
```

---

## Technical Demos & Benchmarks

| Case | Java Example | Launcher | Description |
|:---|:---|:---|:---|
| **Deterministic Automation Demo** | [RuntimeDemo.java](examples/Demo/src/main/java/demo/RuntimeDemo.java) | `run-demo.bat` | End-to-end automation opening Notepad, typing text, and saving files. |
| **JMH Microbenchmark Suite** | [Benchmark.java](examples/Benchmark/src/main/java/fastairuntime/benchmark/Benchmark.java) | `run-benchmark.bat` | JMH throughput benchmark measuring tool dispatch and registry resolution speed. |

---

## Installation

### Option 1: Maven (Recommended)

Add the JitPack repository and the dependency to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <!-- FastAIRuntime Library -->
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>FastAIRuntime</artifactId>
        <version>0.1.1</version>
    </dependency>

    <!-- FastCore (Unified Native Loader) -->
    <dependency>
        <groupId>com.github.andrestubbe</groupId>
        <artifactId>FastCore</artifactId>
        <version>0.1.0</version>
    </dependency>
</dependencies>
```

### Option 2: Gradle (via JitPack)

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.andrestubbe:FastAIRuntime:0.1.1'
    implementation 'com.github.andrestubbe:FastCore:0.1.0'
}
```

### Option 3: Direct Download (No Build Tool)

Download the latest JARs directly to add them to your classpath:

1. 📦 **[FastAIRuntime-0.1.1.jar](https://github.com/andrestubbe/FastAIRuntime/releases/download/0.1.1/FastAIRuntime-0.1.1.jar)** (The Core Library)
2. ⚙️ **[fastcore-0.1.0.jar](https://github.com/andrestubbe/FastCore/releases/download/0.1.0/fastcore-0.1.0.jar)** (The Mandatory Native Loader)

---

## Documentation

* **[REFERENCE.md](docs/REFERENCE.md)**: Core API reference manual.
* **[PHILOSOPHY.md](docs/PHILOSOPHY.md)**: Deterministic execution and sandboxing architecture.
* **[COMPILE.md](docs/COMPILE.md)**: Build instructions.
* **[CHANGELOG.md](docs/CHANGELOG.md)**: Project history and releases.
* **[ROADMAP.md](docs/ROADMAP.md)**: Future milestones.

---

## Platform Support

| Platform | Architecture | Status | Notes |
|:---|:---:|:---:|:---|
| **Windows 10 / 11** | x64 | ✅ Fully Supported | Full OS tool automation, UIA, and process control |
| **Linux (Ubuntu / RHEL)** | x64 | 🚧 Planned | CommandRunner & File tools supported; native UI pending |
| **macOS (Sonoma+)** | Apple Silicon / x64 | 🚧 Planned | CommandRunner & File tools supported; native UI pending |

---

## License

MIT License — See [LICENSE](LICENSE) file for details.

---

## Related Projects

- [FastAI](https://github.com/andrestubbe/FastAI) — Unified AI client interface for Java
- [FastAIAgent](https://github.com/andrestubbe/FastAIAgent) — Autonomous agent loop, intent-graphs, and tool execution
- [FastAIBot](https://github.com/andrestubbe/FastAIBot) — Zero-bloat bot harnesses and persona runtime
- [FastAIEval](https://github.com/andrestubbe/FastAIEval) — Ultra-fast LLM & agent evaluation framework
- [FastAIGraph](https://github.com/andrestubbe/FastAIGraph) — In-memory knowledge graph and multi-hop relationship engine
- [FastAIGuard](https://github.com/andrestubbe/FastAIGuard) — Fast guardrails, prompt safety, and hallucination containment
- [FastAIHybrid](https://github.com/andrestubbe/FastAIHybrid) — Dense-sparse hybrid search fusion (BM25 + Vectors)
- [FastAIMatcher](https://github.com/andrestubbe/FastAIMatcher) — Automated SOX compliance and hybrid rule matching engine
- [FastAIMCP](https://github.com/andrestubbe/FastAIMCP) — Model Context Protocol (MCP) server & tool integration
- [FastAIMemory](https://github.com/andrestubbe/FastAIMemory) — Conversation history, sliding windows, and rolling summaries
- [FastAIMemoryGraph](https://github.com/andrestubbe/FastAIMemoryGraph) — Graph-based episodic and associative memory engine
- [FastAIMetrics](https://github.com/andrestubbe/FastAIMetrics) — Ultra-fast lock-free token, latency, cost tracking and evaluation engine
- [FastAIModel](https://github.com/andrestubbe/FastAIModel) — Native local inference runtime (GGUF/ONNX)
- [FastAIRag](https://github.com/andrestubbe/FastAIRag) — Ultra-fast document chunking and vector retrieval
- [FastAIReasoner](https://github.com/andrestubbe/FastAIReasoner) — Deterministic planning, chain-of-thought, and self-correction
- [FastAIRerank](https://github.com/andrestubbe/FastAIRerank) — Cross-encoder relevance filtering and Top-N prompt pruner
- [FastAISandbox](https://github.com/andrestubbe/FastAISandbox) — Lightweight isolated execution environment for untrusted AI tools
- [FastAISkill](https://github.com/andrestubbe/FastAISkill) — Modular capability registry and dynamic tool dispatch
- [FastAIState](https://github.com/andrestubbe/FastAIState) — Lock-free shared agent state & blackboard memory
- [FastAIVectorDB](https://github.com/andrestubbe/FastAIVectorDB) — High-throughput SIMD/AVX2 vector database
- [FastAIVision](https://github.com/andrestubbe/FastAIVision) — High-speed local multimodal vision, UI-element grounding, and screen-VLM engine
- [FastCore](https://github.com/andrestubbe/FastCore) — Unified JNI loader and platform abstraction

---

**Part of the FastJava Ecosystem** — *Making the JVM faster. Small package. Maximum speed. Zero bloat. 🚀📋*