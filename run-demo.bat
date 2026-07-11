@echo off
call mvn compile exec:java -D"exec.mainClass"="demo.RuntimeDemo" -q
pause
