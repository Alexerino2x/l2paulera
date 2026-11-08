@echo off
echo ===============================================
echo L2JFree CT2.3 - Database Setup Script
echo ===============================================
echo.

echo Verificando se o MySQL está acessível...
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo AVISO: Comando mysql não encontrado no PATH.
    echo Tentando localizar MySQL...
    
    REM Tentar caminhos comuns do MySQL
    if exist "C:\Program Files\MySQL\MySQL Server 9.3\bin\mysql.exe" (
        set MYSQL_PATH="C:\Program Files\MySQL\MySQL Server 9.3\bin\mysql.exe"
        echo MySQL encontrado em: C:\Program Files\MySQL\MySQL Server 9.3\bin
        goto :mysql_found
    )
    if exist "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" (
        set MYSQL_PATH="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
        echo MySQL encontrado em: C:\Program Files\MySQL\MySQL Server 8.0\bin
        goto :mysql_found
    )
    if exist "C:\Program Files\MySQL\MySQL Server 5.7\bin\mysql.exe" (
        set MYSQL_PATH="C:\Program Files\MySQL\MySQL Server 5.7\bin\mysql.exe"
        echo MySQL encontrado em: C:\Program Files\MySQL\MySQL Server 5.7\bin
        goto :mysql_found
    )
    if exist "C:\xampp\mysql\bin\mysql.exe" (
        set MYSQL_PATH="C:\xampp\mysql\bin"
        echo MySQL encontrado em XAMPP: %MYSQL_PATH%
        goto :mysql_found
    )
    
    echo.
    echo ERRO: MySQL não encontrado nos caminhos padrão.
    echo Por favor, informe o caminho completo do MySQL ou adicione ao PATH.
    echo.
    pause
    exit /b 1
) else (
    set MYSQL_PATH=mysql
)

:mysql_found
echo MySQL encontrado!
%MYSQL_PATH% --version

echo.
echo ===============================================
echo Configuração do Banco de Dados MySQL
echo ===============================================
echo.

echo IMPORTANTE: Certifique-se de que o MySQL está rodando!
echo.
echo Usando senha padrão: admin
set MYSQL_PASSWORD=admin

echo.
echo Testando conexão com MySQL...
echo SHOW DATABASES; | %MYSQL_PATH% -u root -p%MYSQL_PASSWORD% >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo ERRO: Não foi possível conectar ao MySQL.
    echo Verifique se:
    echo 1. O MySQL está rodando
    echo 2. A senha está correta
    echo 3. O usuário root tem permissões
    echo.
    pause
    exit /b 1
)

echo Conexão MySQL OK!
echo.

echo Criando bancos de dados...
echo.

REM Criar banco de dados para login server
echo Criando banco l2jfree_login...
echo CREATE DATABASE IF NOT EXISTS l2jfree_login; | %MYSQL_PATH% -u root -p%MYSQL_PASSWORD%

REM Criar banco de dados para game server  
echo Criando banco l2jfree_game...
echo CREATE DATABASE IF NOT EXISTS l2jfree_game; | %MYSQL_PATH% -u root -p%MYSQL_PASSWORD%

echo.
echo Instalando esquemas SQL...
echo.

REM Instalar esquema do login server
if exist "l2jfree-login\sql\accounts.sql" (
    echo Instalando esquema do login server...
    %MYSQL_PATH% -u root -p%MYSQL_PASSWORD% l2jfree_login < "l2jfree-login\sql\accounts.sql"
)

if exist "l2jfree-login\sql\gameservers.sql" (
    echo Instalando tabela de gameservers...
    %MYSQL_PATH% -u root -p%MYSQL_PASSWORD% l2jfree_login < "l2jfree-login\sql\gameservers.sql"
)

REM Instalar esquema do game server
if exist "l2jfree-datapack\sql\characters.sql" (
    echo Instalando esquema de characters...
    %MYSQL_PATH% -u root -p%MYSQL_PASSWORD% l2jfree_game < "l2jfree-datapack\sql\characters.sql"
)

if exist "l2jfree-datapack\sql\items.sql" (
    echo Instalando esquema de items...
    %MYSQL_PATH% -u root -p%MYSQL_PASSWORD% l2jfree_game < "l2jfree-datapack\sql\items.sql"
)

echo.
echo ===============================================
echo Banco de dados MySQL configurado com sucesso!
echo ===============================================
echo.
echo Bancos criados:
echo - l2jfree_login (Login Server)
echo - l2jfree_game (Game Server)
echo.
echo Configure os arquivos de propriedades para usar:
echo - URL: jdbc:mysql://localhost:3306/l2jfree_login
echo - URL: jdbc:mysql://localhost:3306/l2jfree_game
echo - Driver: com.mysql.cj.jdbc.Driver
echo - User: root
echo - Password: %MYSQL_PASSWORD%
echo.
goto :end

:end
echo.
echo ===============================================
echo Configuração do banco concluída!
echo ===============================================
pause
