#!/usr/bin/env bash
# Gera dist/*/config/local/ a partir de scripts/servidor.env.
# Esses arquivos sobrescrevem as chaves de mesmo nome dos .properties versionados.
source "$(dirname "$0")/_comum.sh"
carregar_env

[[ -d "$DIST/login/config" && -d "$DIST/game/config" ]] || { echo "Rode scripts/build.sh antes." >&2; exit 1; }

JDBC_OPTS="useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"

mkdir -p "$DIST/login/config/local" "$DIST/game/config/local"

cat > "$DIST/login/config/local/loginserver.properties" <<CFG
# Gerado por scripts/configurar.sh. Nao versionar.
URL = jdbc:mysql://$DB_HOST:$DB_PORT/$DB_LOGIN?$JDBC_OPTS
Login = $DB_USER
Password = $DB_PASS
# O game server so conecta pelo localhost (LoginHostname = 127.0.0.1), entao aceitar
# o registro automatico e seguro e evita o passo manual do hexid.
AcceptNewGameServer = True
CFG

cat > "$DIST/game/config/local/server.properties" <<CFG
# Gerado por scripts/configurar.sh. Nao versionar.
URL = jdbc:mysql://$DB_HOST:$DB_PORT/$DB_GAME?$JDBC_OPTS
Login = $DB_USER
Password = $DB_PASS
GameserverPort = $GAME_PORT
ExternalHostname = $EXTERNAL_HOST
InternalHostname = 127.0.0.1
CFG

if [[ "$EXTERNAL_HOST" == "127.0.0.1" ]]; then
	rm -f "$DIST/game/config/local/subnets.properties"
else
	# Pelo playit, toda conexao chega vinda do agente local (127.0.0.1). Por isso todo
	# mundo, inclusive voce no proprio notebook, recebe o endereco do playit.
	cat > "$DIST/game/config/local/subnets.properties" <<CFG
# Gerado por scripts/configurar.sh. Nao versionar.
Subnet = External, 0.0.0.0/0
CFG
fi

chmod 600 "$DIST"/*/config/local/*.properties
echo "Config local gerada em dist/login/config/local e dist/game/config/local"
