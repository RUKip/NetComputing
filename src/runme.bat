@echo off
title This runs first
cls
rem This is gonna be the file we run for the java code
echo Welcome to batch scripting!
javac *.java
rmic DatabaseImpl
rmiregistry 6666
start cmd.exe /k java Server
start cmd.exe /k java Client