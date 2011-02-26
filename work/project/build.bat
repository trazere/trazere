@echo off

@setlocal

REM Compute the project home.
set PROJECT_HOME=%~dp0

REM Check ANT_HOME.
if not "%ANT_HOME%" == "" goto endAntHome
echo Please set the ANT_HOME environment variable.
goto end
:endAntHome

REM Run the Ant script.
set ANT_OPTS=-Xmx1024m -Djava.endorsed.dirs=%PROJECT_HOME%\endorsed
call "%ANT_HOME%\bin\ant.bat" %*

:end
@endlocal
REM pause
