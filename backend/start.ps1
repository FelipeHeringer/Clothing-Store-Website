# ============================================================
#  start.ps1 - Script de inicializacao do backend
#  Clothing Store - Spring Boot + MySQL (Docker)
# ============================================================

$JAVA_HOME_PATH = "C:\Program Files\Microsoft\jdk-21.0.11.10-hotspot"
$BACKEND_DIR    = "$PSScriptRoot\clothing_store"
$COMPOSE_DIR    = $PSScriptRoot

# ---- Variaveis de ambiente JWT (altere para producao!) ----
$JWT_SECRET      = "MEZmGi/JLooGwYqcOc7KOYI90+vNSWprg6AgLw/UUEc="
$ACCESS_EXP      = "3600000"    # 1 hora em ms
$REFRESH_EXP     = "86400000"   # 24 horas em ms
$SERVER_PORT     = "8080"

# ============================================================

function Write-Header {
    Write-Host ""
    Write-Host "=============================================" -ForegroundColor Cyan
    Write-Host "   Clothing Store - Backend Startup Script   " -ForegroundColor Cyan
    Write-Host "=============================================" -ForegroundColor Cyan
    Write-Host ""
}

function Write-Step($msg) {
    Write-Host "[>] $msg" -ForegroundColor Yellow
}

function Write-Ok($msg) {
    Write-Host "[OK] $msg" -ForegroundColor Green
}

function Write-Fail($msg) {
    Write-Host "[ERRO] $msg" -ForegroundColor Red
}

# ============================================================
Write-Header

# 1. Configura JAVA_HOME para o Java 21
Write-Step "Configurando Java 21..."
if (Test-Path "$JAVA_HOME_PATH\bin\java.exe") {
    $env:JAVA_HOME = $JAVA_HOME_PATH
    $env:PATH = "$env:JAVA_HOME\bin;" + $env:PATH
    $javaVer = & java -version 2>&1 | Select-Object -First 1
    Write-Ok "Java: $javaVer"
} else {
    Write-Fail "Java 21 nao encontrado em: $JAVA_HOME_PATH"
    Write-Host "Instale o Java 21 e ajuste o caminho JAVA_HOME_PATH no script." -ForegroundColor Red
    pause
    exit 1
}

# 2. Verifica se o Docker esta rodando
Write-Step "Verificando Docker..."
$dockerRunning = docker info 2>&1
if ($LASTEXITCODE -ne 0) {
    Write-Fail "Docker nao esta rodando! Abra o Docker Desktop e tente novamente."
    pause
    exit 1
}
Write-Ok "Docker esta rodando."

# 3. Sobe o container MySQL
Write-Step "Iniciando MySQL via Docker Compose..."
Set-Location $COMPOSE_DIR
docker compose up -d 2>&1 | Out-Null

# Aguarda o MySQL ficar pronto (max 30s)
$maxWait = 30
$waited  = 0
Write-Host "    Aguardando MySQL inicializar..." -ForegroundColor DarkGray
while ($waited -lt $maxWait) {
    $health = docker inspect --format "{{.State.Status}}" mysql-vertice 2>&1
    if ($health -eq "running") {
        $ping = docker exec mysql-vertice mysqladmin ping -uroot -pPoiLkjmnb25@ --silent 2>&1
        if ($LASTEXITCODE -eq 0) { break }
    }
    Start-Sleep -Seconds 2
    $waited += 2
    Write-Host "    ... $waited s" -ForegroundColor DarkGray
}

if ($waited -ge $maxWait) {
    Write-Fail "MySQL nao respondeu em $maxWait segundos. Verifique o container."
    pause
    exit 1
}
Write-Ok "MySQL pronto e aceitando conexoes."

# 4. Define variaveis de ambiente da aplicacao
Write-Step "Configurando variaveis de ambiente..."
$env:JWT_SECRET_KEY             = $JWT_SECRET
$env:ACCESS_TOKEN_EXPIRATION    = $ACCESS_EXP
$env:REFRESH_TOKEN_EXPIRATION   = $REFRESH_EXP
$env:SERVER_PORT                = $SERVER_PORT
Write-Ok "Variaveis configuradas (porta: $SERVER_PORT)."

# 5. Inicia o Spring Boot
Write-Host ""
Write-Host "=============================================" -ForegroundColor Cyan
Write-Host "  Iniciando Spring Boot na porta $SERVER_PORT..." -ForegroundColor Cyan
Write-Host "  Pressione Ctrl+C para parar o servidor     " -ForegroundColor Cyan
Write-Host "=============================================" -ForegroundColor Cyan
Write-Host ""

Set-Location $BACKEND_DIR
.\mvnw.cmd spring-boot:run
