@echo off
title This runs first
cls
rem This is gonna be the file we run for the java code
echo Welcome to batch scripting!
javac *.java
javac Sockets/*.java
rmic DatabaseImpl
pause