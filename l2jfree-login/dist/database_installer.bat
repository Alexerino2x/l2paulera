@echo off
SET OLDCLASSPATH=%CLASSPATH%
call setenv.bat

REM -------------------------------------
start javaw --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --add-opens java.base/java.text=ALL-UNNAMED --add-opens java.desktop/java.awt.font=ALL-UNNAMED com.l2jfree.tools.dbinstaller.LauncherLS
REM -------------------------------------

SET CLASSPATH=%OLDCLASSPATH%