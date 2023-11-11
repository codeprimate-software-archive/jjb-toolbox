@echo off

rem Check for the JAVA_HOME environment variable.
if not exist "%JAVA_HOME%" goto :no_java_home

set CWD_DIR=%~dp0
set BUILD_DIR=%CWD_DIR%build
set JAVAC_CMD=%JAVA_HOME%\bin\javac
set LOCAL_CLASSPATH=%CLASSPATH%;%BUILD_DIR%
set OPTIONS=-classpath %LOCAL_CLASSPATH% -d %BUILD_DIR%
set SRC_DIR=src

rem Clean build directory
if exist "%BUILD_DIR%" rmdir /S /Q %BUILD_DIR%

rem Create build directory
mkdir %BUILD_DIR%

echo compiling...
set SUCCESS=false
cd %SRC_DIR%
for /D %%i in (*) do call :compile_src %%i
cd ..
set SUCCESS=true
goto :exit


rem Compile all project src JAVA files.
:compile_src
if "%1" == "." goto :exit
if "%1" == ".." goto :exit
if not exist "%1\%SRC_DIR%" goto :exit
echo %1\%SRC_DIR%...
%JAVAC_CMD% %OPTIONS% %1\%SRC_DIR%\*.java
goto :exit


rem Report error message about no JAVA_HOME environment variable.
:no_java_home
echo The JAVA_HOME environment variable is not specified.
set SUCCESS=false
goto :exit

rem Change back to previous directory and exit.
:exit
echo done
if "%SUCCESS%"=="true" cd %CWD_DIR%

