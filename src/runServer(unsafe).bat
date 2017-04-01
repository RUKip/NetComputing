@echo off
title This runs RMI Server
cls
rem This is gonna be the file we run for the java code
echo Welcome to batch scripting!
javac *.java
rmic DatabaseImpl
java -Djava.security.policy=policyT.policy -Djava.rmi.server.hostname="192.168.0.9" Server 
rem this can change depending on your ip address, so check always!!
pause