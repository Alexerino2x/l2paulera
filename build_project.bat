@echo off
echo ===============================================
echo L2JFree CT2.3 - Build Script (Java 21)
echo ===============================================
echo.

REM Definir o caminho do Maven
set MAVEN_HOME=C:\Program Files\apache-maven-3.9.10
set PATH=%PATH%;%MAVEN_HOME%\bin

REM Verificar se Maven está funcionando
echo Verificando Maven...
"%MAVEN_HOME%\bin\mvn" --version
if %errorlevel% neq 0 (
    echo ERRO: Maven não está funcionando corretamente
    pause
    exit /b 1
)

echo Maven encontrado e funcionando!
echo.

REM Verificar versão do Java
echo Versão do Java:
java -version
echo.

REM Navegar para o diretório do módulo principal
echo Navegando para o diretório do módulo principal...
cd /d "%~dp0l2jfree-module"

echo.
echo ===============================================
echo Iniciando build do projeto...
echo ===============================================
echo.

REM Executar clean install
echo Executando: mvn clean install
"%MAVEN_HOME%\bin\mvn" clean install

if %errorlevel% neq 0 (
    echo.
    echo ERRO: Build falhou!
    echo.
    pause
    exit /b 1
)

echo.
echo ===============================================
echo Build concluído com sucesso!
echo ===============================================
echo.

REM Voltar ao diretório raiz
cd /d "%~dp0"

echo Arquivos gerados:
echo - l2jfree-core/target/l2jfree-core-1.3.0.jar
echo - l2jfree-login/target/l2jfree-login-1.3.0.jar
echo - l2jfree-datapack/target/l2jfree-datapack-1.3.0.zip
echo.

pause
