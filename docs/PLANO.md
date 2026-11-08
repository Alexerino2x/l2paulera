# Plano do servidor

## Base escolhida

**L2JFree CT2.3 (Gracia Final), portado para Java 21** — fork
[lucasx0/l2jfree-java21-gracia-final](https://github.com/lucasx0/l2jfree-java21-gracia-final)
(commit `2928146`, julho/2025), licença GPLv3.

Por quê:

- É Gracia Final de verdade: aceita protocolo do cliente até 87 (`MaxProtocolRevision` em
  `l2jfree-core/config/server.properties`). As bases mantidas hoje (L2J Mobius) pulam de
  Interlude para Epilogue (CT2.4), que exige outro cliente.
- Já roda em Java 21 e Maven, com MySQL 8. O repositório original do L2JFree
  (bitbucket `lord_rex/l2jfree-ct2.3`) não existe mais.
- Traz pronto o que o motor de eventos precisa: `InstanceManager` (instâncias), o sistema de
  restrições globais (`model/restriction/global`, ganchos para morte, PvP, uso de item,
  teleporte etc.) e eventos antigos (TvT, CTF, DM) para usar como referência.

Riscos: é um fork pequeno, sem CI. A primeira tarefa é compilar, criar o banco e logar com
um cliente Gracia Final; o que quebrar será corrigido aqui.

## Estrutura

| Pasta | Conteúdo |
|---|---|
| `l2jfree-module` | POM agregador (rodar `mvn install` daqui) |
| `l2j-commons`, `l2j-mmocore`, `l2jfree-scripting-engines` | bibliotecas do core |
| `l2jfree-login` | login server (Java, config, SQL) |
| `l2jfree-core` | game server (Java, config) |
| `l2jfree-datapack` | datapack (XML/CSV, scripts, HTML, SQL) |

## Hospedagem (notebook + playit.gg)

- Login (porta 2106) e game (porta 7777) expostos por dois túneis TCP do playit.gg.
- `ExternalHostname` do game server = endereço do túnel do playit para a 7777. O cliente
  conecta no login pelo endereço do túnel da 2106.
- Endereços, senhas do banco e `hexid.txt` ficam só no notebook (`config/local/`, ignorado
  pelo git). Os arquivos versionados têm só valores de exemplo.

## Etapas (um PR cada)

1. **Base** (este PR): importação do código + documentação.
2. **Build e configuração local**: compilar e corrigir o que quebrar; overrides em
   `config/local/*.properties` (lidos por cima dos padrões); scripts Linux para criar o banco
   e subir os servidores; guia do playit.gg.
3. **Sandbox enxuto**: desligar o que não usamos (eventos antigos, IRC, lottery, Elayne,
   autoevents, Seven Signs opcional), ajustar threads e pool de conexões para notebook.
4. **Motor de eventos (núcleo)**: registro de modos, inscrição pela cidade (NPC e
   `.bg`), fila por tipo, teleporte para instância, times, placar, recompensa em pontos,
   retorno à cidade. Tabelas `event_rank` (pontos por jogador e por tipo de evento) e
   `event_match`.
5. **Modos**: TvT, Deathmatch, Last Man Standing, Capture the Flag, cada um como módulo
   que implementa a mesma interface.
6. **Rank e loja**: ranking geral e por tipo de evento (NPC/HTML e comando), loja
   (multisell) paga com pontos de rank.
