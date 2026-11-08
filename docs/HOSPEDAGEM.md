# Instalar e rodar no notebook

Os scripts são para Linux (bash). No Windows, rode dentro do WSL2.

## 1. Dependências

```bash
sudo apt install openjdk-21-jdk maven mariadb-server unzip
```

## 2. Configuração da máquina

```bash
cp scripts/servidor.env.exemplo scripts/servidor.env
nano scripts/servidor.env   # senha do banco, endereço e porta do playit
```

`scripts/servidor.env` e tudo em `dist/` ficam fora do git. Senhas e endereços nunca
vão para o repositório.

## 3. Compilar, configurar e criar o banco

```bash
./scripts/build.sh        # compila e monta dist/login e dist/game
./scripts/configurar.sh   # gera dist/*/config/local/ a partir do servidor.env
./scripts/install-db.sh   # APAGA e recria os bancos (só na primeira vez ou para zerar)
```

Como funciona a config local: qualquer `config/local/<arquivo>.properties` sobrescreve as
chaves de mesmo nome do `config/<arquivo>.properties` versionado. O
`config/local/subnets.properties`, se existir, substitui o `subnets.properties` inteiro.

Sempre que mudar o `servidor.env`, rode `./scripts/configurar.sh` de novo e reinicie.

## 4. Subir e parar

```bash
./scripts/start.sh   # login + game em segundo plano, reinicia se cair
./scripts/stop.sh
tail -f dist/game/log/stdout.log
```

As contas são criadas automaticamente no primeiro login (`AutoCreateAccounts = True`).
Para virar GM, com o servidor parado:

```sql
UPDATE l2jfree_game.characters SET accesslevel = 200 WHERE char_name = 'SeuChar';
```

## 5. playit.gg (sem abrir portas no roteador)

1. Instale o agente do playit no notebook (https://playit.gg/download) e vincule à sua conta.
2. Crie **dois túneis TCP**:
   - **Login**: local `127.0.0.1:2106`.
   - **Game**: anote a porta pública que o playit der (ex.: `algo-123.gl.joinplayit.gg:41234`)
     e configure o destino local como `127.0.0.1:41234` (a mesma porta).
3. No `scripts/servidor.env`:
   ```
   EXTERNAL_HOST=algo-123.gl.joinplayit.gg
   GAME_PORT=41234
   ```
4. `./scripts/configurar.sh`, depois `./scripts/stop.sh && ./scripts/start.sh`.

Por que a mesma porta: o login manda para o cliente o IP e a porta do game, e a porta enviada
é a porta em que o game escuta. Se o game escutar na mesma porta pública do túnel, o cliente
chega certo tanto pela internet quanto no próprio notebook.

Por que todo mundo recebe o endereço do playit: pelo túnel, as conexões chegam ao servidor
vindas do próprio agente (127.0.0.1). O `configurar.sh` gera um `subnets.properties` local
que manda sempre o endereço externo.

## 6. Cliente

Cliente Lineage 2 **Gracia Final** (protocolo 83 a 87). O cliente precisa apontar para o
endereço do túnel de **login** e para a porta pública dele. Ainda falta testar com um
cliente real qual o jeito mais simples de fazer isso (l2.ini editado ou system customizado);
este guia será atualizado depois do primeiro teste.
