#!/usr/bin/env bash
# Para login e game iniciados pelo start.sh.
source "$(dirname "$0")/_comum.sh"

for nome in game login; do
	pidf="$DIST/$nome.pid"
	[[ -f "$pidf" ]] || continue
	pid="$(cat "$pidf")"
	# pega o java filho antes, para-o loop e depois o java (o java salva tudo ao receber TERM)
	filhos="$(pgrep -P "$pid" || true)"
	kill -TERM "$pid" 2>/dev/null || true
	[[ -n "$filhos" ]] && kill -TERM $filhos 2>/dev/null || true
	rm -f "$pidf"
	echo "$nome parado"
done
