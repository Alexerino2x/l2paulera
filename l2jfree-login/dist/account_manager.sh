#!/bin/sh

. ./setenv.sh
java -Djava.util.logging.config.file=console.cfg --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --add-opens java.base/java.text=ALL-UNNAMED --add-opens java.desktop/java.awt.font=ALL-UNNAMED com.l2jfree.loginserver.tools.accountmanager.AccountManager