# l2paulera

Servidor privado de Lineage 2 **Gracia Final** (CT2.3) para jogar com os amigos, sem monetização.

Foco: sandbox enxuto (performance) e eventos no estilo battleground (TvT, Deathmatch, Last Man
Standing, Capture the Flag), com inscrição pela cidade, rank por tipo de evento e loja paga com
pontos de rank.

- Base: L2JFree CT2.3 portado para Java 21 (GPLv3). Detalhes e etapas em [docs/PLANO.md](docs/PLANO.md).
- Documentação original do fork: [docs/upstream](docs/upstream).

## Requisitos

- JDK 21, Maven 3.9+, MySQL 8 (ou MariaDB 10.6+)
- Cliente Lineage 2 Gracia Final (protocolo 83 a 87)

## Compilar

```bash
cd l2jfree-module
mvn install -DskipTests
```

O guia completo de instalação, banco e playit.gg vem no PR da etapa 2.
