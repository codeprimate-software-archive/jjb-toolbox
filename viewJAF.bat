echo off

set DIST_DIR=%~dp0dist
set JAR_FILE=JAF.jar

rem Clear the screen
cls

rem Determine if the JAF JAR file already exist.  Delete if it does.
if not exist %DIST_DIR%\%JAR_FILE% goto :jar_file_not_found

rem Change working directory to where we want the JAR command executed.
cd %DIST_DIR%

rem Execute the JAR command to build the JAF.jar file.
%JAVA_HOME%\bin\jar -tvf %DIST_DIR%\%JAR_FILE% | more
set EXE_SUCCESS=true
goto :exit

rem Change back to previous directory.

:jar_file_not_found
echo The %JAR_FILE% file could not be found.
set EXE_SUCCESS=false
goto :exit

:exit
if "%EXE_SUCCESS%"=="true" cd ..
