@echo off

set BUILD_DIR=build
set DIST_DIR=%~dp0dist
set JAR_FILE=JAF.jar

rem if exist ".\compileJAF.bat" call compileJAF.bat

rem Check for the JAVA_HOME environment variable.
if not exist "%JAVA_HOME%" goto :no_java_home

rem Determine if the build directory exist and report error if it does not.
if not exist %BUILD_DIR% goto :no_build_dir

rem Determine if the JAR file already exist and delete it if it does.
if not exist %DIST_DIR% mkdir %DIST_DIR%
if exist %DIST_DIR%\%JAR_FILE% del %DIST_DIR%\%JAR_FILE%

rem Change working directory to where we want the JAR command executed.
cd %BUILD_DIR%

rem Execute the JAR command to build the JAF.jar file.
%JAVA_HOME%\bin\jar -cvf %DIST_DIR%\%JAR_FILE% jjb/toolbox/ org/java/
set SUCCESS=true
goto :exit

rem Report error message about no JAVA_HOME environment variable.
:no_java_home
echo The JAVA_HOME environment variable is not specified.
set SUCCESS=false
goto :exit

rem Report error message about the non-existent build directory.
:no_build_dir
echo The %BUILD_DIR% does not exist.
set SUCCESS=false
goto :exit

rem Change back to previous directory and exit.
:exit
if "%SUCCESS%"=="true" cd ..

