@echo off
chcp 65001 >nul
cls

echo ⚡ Building Main Project...
call mvn clean install -DskipTests -q
if %ERRORLEVEL% NEQ 0 ( echo ❌ Build failed. & pause & exit /b %ERRORLEVEL% )

echo 🛠  Compiling Demo...
cd examples\Demo
call mvn compile -q
if %ERRORLEVEL% NEQ 0 ( echo ❌ Compile failed. & cd ..\.. & pause & exit /b %ERRORLEVEL% )

echo 🚀 Running Demo...
call mvn exec:java -D"exec.mainClass"="demo.RuntimeDemo" -q

cd ..\..
pause
