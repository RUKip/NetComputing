@echo off
title This runs second
cls
rem This is gonna be the file we run for the java code
echo Server started
java Server
start cmd.exe /k java Client