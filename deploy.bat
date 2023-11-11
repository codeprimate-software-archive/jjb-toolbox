@echo off

set JAVA_LIB_DIR=%~d0\commonlib\java\lib
set JAR_FILE=dist\JAF.jar

rem if exist ".\buildJAF.bat" call buildJAF.bat

rem Determine whether the JAR file exist.
if not exist "%JAR_FILE%" goto :no_jar_file

rem Copy the JAF.jar file to commonlib\java\lib
echo Copying %JAR_FILE% to %JAVA_LIB_DIR%
copy /Y %JAR_FILE% %JAVA_LIB_DIR%
goto :exit

rem Report error message about no JAR file.
:no_jar_file
echo %JAR_FILE% does not exist!
goto :exit

:exit
