@echo off

@setlocal

REM Compute the project home.
set PROJECT_HOME=%~dp0

@endlocal

REM Set the Ant options.
set ANT_OPTS=-Xmx1024m -Djava.endorsed.dirs=%PROJECT_HOME%\endorsed
