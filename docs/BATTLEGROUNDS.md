# Battlegrounds

Motor de eventos no estilo das battlegrounds do WoW. Código em
`l2jfree-core/src/main/java/com/l2paulera/bg`, configuração em
`l2jfree-core/config/battleground.properties`, tabelas em `sql/battleground.sql`.

## Para o jogador

| Comando | O que faz |
|---|---|
| `.bg` | abre a janela: modos, quantos estão na fila, entrar, rank, seus pontos |
| `.bg entrar tvt` | entra na fila de um modo (`tvt`, `dm`) |
| `.bg sair` | sai da fila, ou sai da partida e volta para onde estava |
| `.bg placar` | placar da partida atual |
| `.bg rank` / `.bg rank tvt` | top 15 geral ou de um modo |

Fluxo: entra na fila de dentro da cidade → quando a fila atinge o mínimo, contagem de 30s →
todos vão para uma instância própria (várias partidas podem rodar ao mesmo tempo na mesma
arena) → 20s de preparação (zona de paz) → combate → resultado → todos voltam para onde
estavam.

Na partida: sem karma, sem perda de exp, sem pet, sem SoE, renascimento automático (nos modos
com renascimento), nome colorido pelo time, party só com o próprio time.

## Pontos de rank

Por partida: vitória 30, derrota 10, empate 15, mais 2 por abate (no máximo 20). Quem sai no
meio não ganha pontos e a partida conta como derrota.

Cada modo tem seu rank (`bg_rank.mode`). O rank geral é a soma dos modos. `points` é o saldo
que a loja vai gastar; `points_total` nunca diminui e é o que ordena o rank.

## Modos

| id | Modo | Times | Fim |
|---|---|---|---|
| `tvt` | Team vs Team | 2 | 30 abates do time ou fim do tempo |
| `dm` | Deathmatch | cada um por si | 20 abates ou fim do tempo |

Próximos: Last Man Standing e Capture the Flag.

## Criar um modo novo

Implemente `com.l2paulera.bg.mode.BattlegroundMode` (ou estenda `AbstractMode`) e registre em
`BattlegroundManager` com `register(new MeuModo())`. O motor cuida de fila, instância,
teleporte, tempo, renascimento, placar, pontos e retorno. O modo só decide como pontua
(`onKill`, `onTick`), quando acaba (`isFinished`) e quem vence (`getWinners`).

## Banco já existente

`install-db.sh` já carrega `sql/battleground.sql`. Num banco criado antes:

```bash
mysql -u l2j -p l2jfree_game < sql/battleground.sql
```
