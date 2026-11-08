# L2JFree CT2.3 - Atualização para Java 21

Este documento descreve as alterações realizadas para migrar o projeto L2JFree CT2.3 do Java 8 para Java 21.

## Principais Alterações

### 1. Configuração do Maven (l2jfree-module/pom.xml)
- Atualizado `maven.compiler.source` e `maven.compiler.target` para 21
- Adicionado suporte a `--enable-preview` para recursos experimentais do Java 21
- Atualizadas versões dos plugins Maven para compatibilidade com Java 21:
  - `maven-compiler-plugin`: 3.11.0
  - `maven-surefire-plugin`: 3.1.2
  - `maven-resources-plugin`: 3.3.1
  - `maven-javadoc-plugin`: 3.5.0
  - `maven-jar-plugin`: 3.3.0
  - `maven-assembly-plugin`: 3.6.0
  - `maven-source-plugin`: 3.3.0
  - `maven-dependency-plugin`: 3.6.0

### 2. Dependências Atualizadas
- `commons-lang3`: 3.4 → 3.12.0
- `commons-io`: 1.4 → 2.11.0
- `c3p0`: 0.9.5.1 → 0.9.5.5
- `mysql-connector-java`: 5.1.36 → 8.0.33
- `trove4j`: 2.1.0 → 3.0.3
- `junit`: 3.8.2 → 4.13.2

### 3. Configurações do MySQL
- Atualizado driver: `com.mysql.jdbc.Driver` → `com.mysql.cj.jdbc.Driver`
- Adicionados parâmetros de conexão necessários para o novo driver:
  - `useSSL=false`
  - `serverTimezone=UTC`
  - `allowPublicKeyRetrieval=true`

### 4. Scripts de Lançamento (.bat e .sh)
Adicionados parâmetros JVM necessários para Java 21:
- `--add-opens java.base/java.lang=ALL-UNNAMED`
- `--add-opens java.base/java.util=ALL-UNNAMED`
- `--add-opens java.base/java.lang.reflect=ALL-UNNAMED`
- `--add-opens java.base/java.text=ALL-UNNAMED`
- `--add-opens java.desktop/java.awt.font=ALL-UNNAMED`

Arquivos atualizados:
- `l2jfree-core/dist/gameserver_launcher.bat`
- `l2jfree-core/dist/gameserver_loop.sh`
- `l2jfree-login/dist/loginserver_launcher.bat`
- `l2jfree-login/dist/loginserver_loop.sh`
- `l2jfree-core/dist/database_installer.bat`
- `l2jfree-core/dist/database_installer.sh`
- `l2jfree-login/dist/database_installer.bat`
- `l2jfree-login/dist/database_installer.sh`
- `l2jfree-login/dist/account_manager.bat`
- `l2jfree-login/dist/account_manager.sh`
- `l2jfree-login/dist/register_gameserver.bat`
- `l2jfree-login/dist/register_gameserver.sh`

### 5. Configurações de Propriedades
- `l2jfree-login/config/loginserver.properties`
- `l2jfree-core/config/server.properties`

## Considerações Importantes

### Compatibilidade com Java 21
- Removido parâmetro deprecated `-XX:PermSize` (não mais necessário)
- Atualizado `forkMode` para `forkCount` no maven-surefire-plugin
- Adicionados argumentos `--add-opens` para permitir acesso de reflection

### Potenciais Problemas
1. **Reflection**: Alguns códigos que usam reflection podem precisar dos parâmetros `--add-opens`
2. **SecurityManager**: Removido no Java 21, mas não identificado uso no projeto
3. **APIs Internas**: Não foram encontradas importações de APIs internas do Sun/Oracle

### Requisitos do Sistema
- **Java JDK 21** (ou superior)
- **Maven 3.6+**
- **MySQL 8.0+** (recomendado para compatibilidade total)

## Compilação

Para compilar o projeto após as alterações:

```bash
mvn clean install
```

## Execução

Os scripts de lançamento foram atualizados para incluir os parâmetros necessários do Java 21. Use os scripts normalmente:

### Windows:
```cmd
gameserver_launcher.bat
loginserver_launcher.bat
```

### Linux/Unix:
```bash
./gameserver_launcher.sh
./loginserver_launcher.sh
```

## Notas Adicionais

- A biblioteca `javolution` (5.4.1) foi mantida na versão original conforme recomendação do projeto
- Todos os links HTTP foram atualizados para HTTPS quando possível
- O suporte a `--enable-preview` foi adicionado para permitir uso de recursos experimentais

## Testes

Após a migração, recomenda-se:
1. Testar a compilação completa
2. Verificar a conectividade com o banco de dados
3. Testar o funcionamento básico do servidor
4. Executar testes de carga se aplicável

---

**Data da Migração:** 04/07/2025
**Versão do Java:** 21.0.7
