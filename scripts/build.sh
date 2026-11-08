#!/usr/bin/env bash
# Compila tudo e monta dist/login e dist/game.
# Arquivos locais (config/local, hexid.txt, log) ficam preservados.
source "$(dirname "$0")/_comum.sh"

(cd "$RAIZ/l2jfree-module" && mvn -B -q -DskipTests -Dmaven.javadoc.skip=true -Dmaven.source.skip=true install)

mkdir -p "$DIST/login" "$DIST/game"
TMP="$(mktemp -d)"
trap 'rm -rf "$TMP"' EXIT

unzip -qo "$RAIZ"/l2jfree-login/target/l2jfree-login-*-dist.zip -d "$TMP/login"
unzip -qo "$RAIZ"/l2jfree-core/target/l2jfree-core-*-dist.zip -d "$TMP/game"
unzip -qo "$RAIZ"/l2jfree-datapack/target/l2jfree-datapack-*-dist.zip -d "$TMP/game"

# copia por cima, sem apagar o que e local
cp -a "$TMP/login/loginserver/." "$DIST/login/"
cp -a "$TMP/game/gameserver/." "$DIST/game/"

echo "Pronto: $DIST/login e $DIST/game"
