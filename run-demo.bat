@echo off
echo [FastAIRuntime] Compiling and running deterministic demo...
call mvn compile exec:java -D"exec.mainClass"="demo.RuntimeDemo"
pause
