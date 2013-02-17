@echo off

REM  Copyright 2006-2013 Julien Dufour
REM
REM  Licensed under the Apache License, Version 2.0 (the "License");
REM  you may not use this file except in compliance with the License.
REM  You may obtain a copy of the License at
REM
REM      http://www.apache.org/licenses/LICENSE-2.0
REM
REM  Unless required by applicable law or agreed to in writing, software
REM  distributed under the License is distributed on an "AS IS" BASIS,
REM  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
REM  See the License for the specific language governing permissions and
REM  limitations under the License.

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
