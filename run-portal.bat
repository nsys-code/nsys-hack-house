@echo off

SET NSYS_BUNDLE_DIR=%CD%\nsys-hack-house-bundle\target\nsys-hack-house

IF EXIST %NSYS_BUNDLE_DIR% GOTO CONTINUE

echo You need to build Nsys #HackTheHouse at first! Please run command 'build.bat' ... >&2
GOTO EXIT

:CONTINUE
%NSYS_BUNDLE_DIR%\bin\nsys-container.bat run

:EXIT