@echo off
echo ===============================================
echo L2JFree CT2.3 - Maven Installation Script
echo ===============================================
echo.

REM Verificar se Maven já está instalado
where mvn >nul 2>&1
if %errorlevel% equ 0 (
    echo Maven já está instalado:
    mvn --version
    echo.
    pause
    exit /b 0
)

echo Maven não está instalado. Tentando instalar automaticamente...
echo.

REM Verificar se Chocolatey está instalado
where choco >nul 2>&1
if %errorlevel% equ 0 (
    echo Chocolatey encontrado. Instalando Maven...
    choco install maven -y
    echo.
    echo Maven instalado via Chocolatey!
    echo Reinicie o terminal e execute build_project.bat
    pause
    exit /b 0
)

REM Verificar se Scoop está instalado
where scoop >nul 2>&1
if %errorlevel% equ 0 (
    echo Scoop encontrado. Instalando Maven...
    scoop install maven
    echo.
    echo Maven instalado via Scoop!
    echo Reinicie o terminal e execute build_project.bat
    pause
    exit /b 0
)

echo.
echo ===============================================
echo Instalação Manual Necessária
echo ===============================================
echo.
echo Nem Chocolatey nem Scoop foram encontrados.
echo.
echo Opções para instalar Maven:
echo.
echo 1. Instalar Chocolatey (recomendado):
echo    - Abra PowerShell como administrador
echo    - Execute: Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))
echo    - Execute: choco install maven
echo.
echo 2. Instalar Scoop:
echo    - Execute: Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
echo    - Execute: Invoke-RestMethod get.scoop.sh | Invoke-Expression
echo    - Execute: scoop install maven
echo.
echo 3. Instalação manual:
echo    - Baixe Maven de https://maven.apache.org/download.cgi
echo    - Extraia para C:\Program Files\Apache\Maven
echo    - Adicione C:\Program Files\Apache\Maven\bin ao PATH
echo.
echo Após instalar Maven, execute build_project.bat
echo.
pause
