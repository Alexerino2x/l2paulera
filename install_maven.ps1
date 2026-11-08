# L2JFree CT2.3 - Maven Installation Script (PowerShell)
# Executa automaticamente a instalação do Maven usando Chocolatey

Write-Host "===============================================" -ForegroundColor Yellow
Write-Host "L2JFree CT2.3 - Maven Installation Script" -ForegroundColor Yellow
Write-Host "===============================================" -ForegroundColor Yellow
Write-Host ""

# Verificar se Maven já está instalado
try {
    $mvnVersion = & mvn --version 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Maven já está instalado:" -ForegroundColor Green
        Write-Host $mvnVersion -ForegroundColor Green
        Write-Host ""
        Read-Host "Pressione Enter para continuar"
        exit 0
    }
} catch {
    Write-Host "Maven não está instalado. Procedendo com a instalação..." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Instalando Chocolatey e Maven..." -ForegroundColor Yellow
Write-Host ""

try {
    # Verificar se Chocolatey está instalado
    try {
        $chocoVersion = & choco --version 2>$null
        if ($LASTEXITCODE -eq 0) {
            Write-Host "Chocolatey já está instalado: $chocoVersion" -ForegroundColor Green
        }
    } catch {
        Write-Host "Instalando Chocolatey..." -ForegroundColor Yellow
        
        # Definir política de execução temporariamente
        Set-ExecutionPolicy Bypass -Scope Process -Force
        
        # Instalar Chocolatey
        [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
        Invoke-Expression ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))
        
        # Recarregar o PATH
        $env:PATH = [System.Environment]::GetEnvironmentVariable("PATH", "Machine") + ";" + [System.Environment]::GetEnvironmentVariable("PATH", "User")
        
        Write-Host "Chocolatey instalado com sucesso!" -ForegroundColor Green
    }
    
    Write-Host ""
    Write-Host "Instalando Maven via Chocolatey..." -ForegroundColor Yellow
    
    # Instalar Maven
    & choco install maven -y
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "Maven instalado com sucesso!" -ForegroundColor Green
        Write-Host ""
        
        # Recarregar o PATH
        $env:PATH = [System.Environment]::GetEnvironmentVariable("PATH", "Machine") + ";" + [System.Environment]::GetEnvironmentVariable("PATH", "User")
        
        Write-Host "Verificando instalação do Maven..." -ForegroundColor Yellow
        try {
            $mvnVersion = & mvn --version 2>$null
            if ($LASTEXITCODE -eq 0) {
                Write-Host "Maven instalado e funcionando:" -ForegroundColor Green
                Write-Host $mvnVersion -ForegroundColor Green
            } else {
                Write-Host "Maven instalado, mas pode ser necessário reiniciar o terminal." -ForegroundColor Yellow
            }
        } catch {
            Write-Host "Maven instalado, mas pode ser necessário reiniciar o terminal." -ForegroundColor Yellow
        }
        
        Write-Host ""
        Write-Host "===============================================" -ForegroundColor Green
        Write-Host "Instalação concluída!" -ForegroundColor Green
        Write-Host "===============================================" -ForegroundColor Green
        Write-Host ""
        Write-Host "Próximos passos:" -ForegroundColor Yellow
        Write-Host "1. Reinicie o terminal ou abra um novo terminal" -ForegroundColor White
        Write-Host "2. Execute: .\build_project.bat" -ForegroundColor White
        Write-Host ""
        
    } else {
        Write-Host "Erro ao instalar Maven via Chocolatey." -ForegroundColor Red
        Write-Host "Tente instalar manualmente ou use outro método." -ForegroundColor Red
    }
    
} catch {
    Write-Host "Erro durante a instalação: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
    Write-Host "===============================================" -ForegroundColor Red
    Write-Host "Instalação Manual Necessária" -ForegroundColor Red
    Write-Host "===============================================" -ForegroundColor Red
    Write-Host ""
    Write-Host "Opções para instalar Maven:" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "1. Instalação manual:" -ForegroundColor White
    Write-Host "   - Baixe Maven de https://maven.apache.org/download.cgi" -ForegroundColor White
    Write-Host "   - Extraia para C:\Program Files\Apache\Maven" -ForegroundColor White
    Write-Host "   - Adicione C:\Program Files\Apache\Maven\bin ao PATH" -ForegroundColor White
    Write-Host ""
    Write-Host "2. Usar winget (Windows Package Manager):" -ForegroundColor White
    Write-Host "   - Execute: winget install Apache.Maven" -ForegroundColor White
    Write-Host ""
}

Write-Host ""
Read-Host "Pressione Enter para continuar"
