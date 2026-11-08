#!/usr/bin/env bash
# Funcoes e caminhos usados pelos outros scripts.
set -euo pipefail

RAIZ="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
DIST="$RAIZ/dist"
ENV_FILE="$RAIZ/scripts/servidor.env"

carregar_env() {
	if [[ ! -f "$ENV_FILE" ]]; then
		echo "Falta $ENV_FILE. Copie scripts/servidor.env.exemplo e ajuste." >&2
		exit 1
	fi
	# shellcheck disable=SC1090
	source "$ENV_FILE"
}

JAVA_OPTS_COMUNS=(
	-Dfile.encoding=UTF-8
	--add-opens java.base/java.lang=ALL-UNNAMED
	--add-opens java.base/java.util=ALL-UNNAMED
	--add-opens java.base/java.lang.reflect=ALL-UNNAMED
	--add-opens java.base/java.text=ALL-UNNAMED
	--add-opens java.desktop/java.awt.font=ALL-UNNAMED
)
