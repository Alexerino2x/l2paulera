@echo off
echo ===============================================
echo L2JFree CT2.3 - Servidor Startup Script
echo ===============================================
echo.

REM Verificar se o Java 21 está instalado
echo Verificando Java...
java -version
echo.

REM Verificar se o build foi feito
echo Verificando build...
if not exist "l2jfree-core\target\l2jfree-core-1.3.0.jar" (
    echo ERRO: Build não encontrado! Execute primeiro:
    echo "C:\Program Files\apache-maven-3.9.10\bin\mvn.cmd" clean install
    echo.
    pause
    exit /b 1
)

echo Build encontrado! Iniciando servidores...
echo.

REM Abrir terminal para Login Server
echo Iniciando Login Server...
start "L2JFree Login Server" cmd /k "cd /d l2jfree-login\dist && loginserver_launcher.bat"

REM Aguardar um pouco antes de iniciar o Game Server
echo Aguardando 5 segundos antes de iniciar o Game Server...
timeout /t 5 /nobreak

REM Abrir terminal para Game Server
echo Iniciando Game Server...
start "L2JFree Game Server" cmd /k "cd /d l2jfree-core\dist && gameserver_launcher.bat"

echo.
echo ===============================================
echo Servidores iniciados!
echo ===============================================
echo.
echo Login Server: Terminal separado
echo Game Server: Terminal separado
echo.
echo Para parar os servidores, feche os terminais correspondentes.
echo.
pause
