@echo off
title This runs first
cls
rem This is gonna be the file we run for the java code
echo Welcome to batch scripting!
javac *.java
rmic AdderRemote
rmiregistry 5000
start cmd.exe /k java MyServer
start cmd.exe /k java MyClient