#!/usr/bin/env bash
# Cria os bancos e o usuario e carrega o SQL do login e do game.
# ATENCAO: apaga e recria os dois bancos. Use so na primeira instalacao ou para zerar o servidor.
source "$(dirname "$0")/_comum.sh"
carregar_env

read -r -p "Isto APAGA os bancos $DB_LOGIN e $DB_GAME. Digite 'sim' para continuar: " ok
[[ "$ok" == "sim" ]] || { echo "Cancelado."; exit 1; }

$MYSQL_ADMIN <<SQL
DROP DATABASE IF EXISTS \`$DB_LOGIN\`;
DROP DATABASE IF EXISTS \`$DB_GAME\`;
CREATE DATABASE \`$DB_LOGIN\` DEFAULT CHARACTER SET utf8mb4;
CREATE DATABASE \`$DB_GAME\` DEFAULT CHARACTER SET utf8mb4;
CREATE USER IF NOT EXISTS '$DB_USER'@'localhost' IDENTIFIED BY '$DB_PASS';
ALTER USER '$DB_USER'@'localhost' IDENTIFIED BY '$DB_PASS';
GRANT ALL ON \`$DB_LOGIN\`.* TO '$DB_USER'@'localhost';
GRANT ALL ON \`$DB_GAME\`.* TO '$DB_USER'@'localhost';
FLUSH PRIVILEGES;
SQL

export MYSQL_PWD="$DB_PASS"
mysql_app() { mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" "$@"; }

echo "Carregando login..."
for f in "$RAIZ"/l2jfree-login/sql/*.sql; do mysql_app "$DB_LOGIN" < "$f"; done

echo "Carregando game..."
for f in "$RAIZ"/l2jfree-datapack/sql/*.sql "$RAIZ"/l2jfree-datapack/sql/custom/*.sql; do
	if grep -q "SET GLOBAL" "$f"; then
		# precisa de privilegio de admin (ex.: enchant_skill_trees.sql cria funcoes temporarias)
		$MYSQL_ADMIN "$DB_GAME" < "$f" > /dev/null
	else
		mysql_app "$DB_GAME" < "$f" > /dev/null
	fi
done

# Tabelas do l2paulera (motor de eventos etc.), se existirem
if compgen -G "$RAIZ/sql/*.sql" > /dev/null; then
	echo "Carregando tabelas do l2paulera..."
	for f in "$RAIZ"/sql/*.sql; do mysql_app "$DB_GAME" < "$f"; done
fi

echo "Bancos prontos."
