@echo off

echo ** compile **
call compileJAF.bat
echo ** build **
call buildJAF.bat
echo ** deploy **
call deployJAF.bat

:exit
echo done
