#!/usr/bin/env bash
# Sobe login e game em segundo plano, reiniciando se cairem.
# Logs em dist/login/log e dist/game/log. Pare com scripts/stop.sh.
source "$(dirname "$0")/_comum.sh"
carregar_env

rodar_em_loop() {
	local nome="$1" dir="$2" xmx="$3" classe="$4"
	(
		cd "$dir"
		mkdir -p log
		export CLASSPATH="./libs/*:./config/:./*:."
		while true; do
			java -server -Xmx"$xmx" "${JAVA_OPTS_COMUNS[@]}" "$classe" >> log/stdout.log 2>&1 && break
			echo "[$(date '+%F %T')] $nome caiu, reiniciando em 10s" >> log/stdout.log
			sleep 10
		done
	) &
	echo $! > "$DIST/$nome.pid"
	echo "$nome iniciado (log: $dir/log/stdout.log)"
}

[[ -f "$DIST/login/config/local/loginserver.properties" ]] || { echo "Rode scripts/configurar.sh antes." >&2; exit 1; }

rodar_em_loop login "$DIST/login" "$XMX_LOGIN" com.l2jfree.loginserver.LoginServer
sleep 5
rodar_em_loop game "$DIST/game" "$XMX_GAME" com.l2jfree.gameserver.GameServer
