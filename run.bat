@echo off
if not exist bin mkdir bin
javac -d bin src/LibrarySystem.java
if %ERRORLEVEL% EQU 0 (
    java -cp bin LibrarySystem
) else (
    echo Compilation failed!
    pause
)
