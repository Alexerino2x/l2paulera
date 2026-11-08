@echo off
echo ===============================================
echo L2JFree CT2.3 - Maven Download e Instalação
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

echo Preparando para instalar Maven...
echo.

REM Criar diretório para Maven
set MAVEN_HOME=C:\Program Files\Apache\Maven
set MAVEN_VERSION=3.9.8

echo Criando diretório: %MAVEN_HOME%
mkdir "%MAVEN_HOME%" 2>nul

echo.
echo ===============================================
echo Download do Maven %MAVEN_VERSION%
echo ===============================================
echo.

REM Baixar Maven usando PowerShell
echo Baixando Maven %MAVEN_VERSION%...
powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri 'https://dlcdn.apache.org/maven/maven-3/%MAVEN_VERSION%/binaries/apache-maven-%MAVEN_VERSION%-bin.zip' -OutFile 'apache-maven-%MAVEN_VERSION%-bin.zip'}"

if not exist "apache-maven-%MAVEN_VERSION%-bin.zip" (
    echo ERRO: Falha ao baixar Maven!
    echo.
    echo Baixe manualmente de:
    echo https://maven.apache.org/download.cgi
    echo.
    pause
    exit /b 1
)

echo.
echo Extraindo Maven...
powershell -Command "& {Expand-Archive -Path 'apache-maven-%MAVEN_VERSION%-bin.zip' -DestinationPath 'C:\Program Files\Apache\' -Force}"

if not exist "C:\Program Files\Apache\apache-maven-%MAVEN_VERSION%" (
    echo ERRO: Falha ao extrair Maven!
    pause
    exit /b 1
)

echo.
echo Configurando Maven...
REM Renomear diretório para Maven
if exist "%MAVEN_HOME%" rmdir /s /q "%MAVEN_HOME%"
rename "C:\Program Files\Apache\apache-maven-%MAVEN_VERSION%" "Maven"

echo.
echo Adicionando Maven ao PATH...
REM Adicionar ao PATH do sistema
setx PATH "%PATH%;%MAVEN_HOME%\bin" /M

echo.
echo Limpando arquivos temporários...
del "apache-maven-%MAVEN_VERSION%-bin.zip" 2>nul

echo.
echo ===============================================
echo Maven instalado com sucesso!
echo ===============================================
echo.
echo Localização: %MAVEN_HOME%
echo.
echo IMPORTANTE: 
echo - Reinicie o terminal ou abra um novo terminal
echo - Execute: mvn --version para verificar
echo - Execute: build_project.bat para compilar o projeto
echo.
pause
