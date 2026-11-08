# L2JFree CT2.3 - Java 21 Gracia Final

## 🚀 **Visão Geral**

Este é o projeto L2JFree CT2.3 completamente migrado para **Java 21**, com todas as dependências atualizadas e otimizações modernas aplicadas.

## ✅ **Principais Melhorias**

### 🔧 **Migração Completa para Java 21**
- ✅ Compilação com Java 21
- ✅ Todas as dependências atualizadas
- ✅ Plugins Maven modernizados
- ✅ 28 problemas de compilação corrigidos

### 📦 **Dependências Atualizadas**
- **MySQL Connector**: `5.1.36` → `8.0.33`
- **Commons Lang3**: `3.4` → `3.12.0`
- **Commons IO**: `1.4` → `2.11.0`
- **C3P0**: `0.9.5.1` → `0.9.5.5`
- **JUnit**: `3.8.2` → `4.13.2`
- **Trove4J**: Substituído por APIs nativas Java

### 🛠️ **Correções Aplicadas**
- ✅ Operadores diamante redundantes
- ✅ Campos marcados como `final`
- ✅ Padrões `instanceof` modernos
- ✅ Multi-catch específico
- ✅ Streams Java 8+
- ✅ Parâmetros `--add-opens` para Java 21

## 🎯 **Requisitos**

- **Java 21** (JDK)
- **Maven 3.9+**
- **MySQL 8.0+**

## 🚀 **Instalação e Execução**

### 1. **Clone o repositório:**
```bash
git clone https://github.com/lucasx0/l2jfree-java21-gracia-final.git
cd l2jfree-java21-gracia-final
```

### 2. **Configure o banco de dados:**
```bash
# Execute o script de configuração do MySQL
setup_database.bat
```

### 3. **Compile o projeto:**
```bash
# Use o script de build
build_project.bat

# Ou use Maven diretamente
mvn clean install
```

### 4. **Execute os servidores:**

#### **Login Server:**
```bash
cd l2jfree-login
java -server -Xmx512m --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED -cp "target\l2jfree-login-1.3.0.jar;target\lib\*" com.l2jfree.loginserver.L2LoginServer
```

#### **Game Server:**
```bash
cd l2jfree-core
java -server -Xmx1024m --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED -cp "target\l2jfree-core-1.3.0.jar;target\lib\*" com.l2jfree.gameserver.GameServer
```

## 📁 **Estrutura do Projeto**

```
l2jfree-java21-gracia-final/
├── l2jfree-module/          # Módulo principal
├── l2j-commons/             # Bibliotecas comuns
├── l2j-mmocore/             # Core MMO
├── l2jfree-scripting-engines/ # Engines de script
├── l2jfree-login/           # Servidor de Login
├── l2jfree-core/            # Servidor de Jogo
├── l2jfree-datapack/        # Datapack
├── setup_database.bat       # Script de setup do banco
├── build_project.bat        # Script de build
└── README.md               # Este arquivo
```

## 🔧 **Configuração do Banco de Dados**

### **MySQL Configuration:**
- **Host**: `localhost:3306`
- **Database Login**: `l2jfree_login`
- **Database Game**: `l2jfree_game`
- **User**: `root`
- **Password**: `admin` (altere conforme necessário)

## 🎮 **Conectando ao Servidor**

- **Login Server**: `localhost:2106`
- **Game Server**: `localhost:7777`

## 🛠️ **Desenvolvimento**

### **Compilação:**
```bash
mvn clean compile
```

### **Testes:**
```bash
mvn test
```

### **Empacotamento:**
```bash
mvn package
```

## 📝 **Changelog**

### **v1.3.0 - Java 21 Migration**
- Migração completa para Java 21
- Todas as dependências atualizadas
- Correção de 28 problemas de compilação
- Otimizações de performance
- Suporte a recursos modernos do Java

## 🤝 **Contribuindo**

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 **Licença**

Este projeto está sob a licença GPL v3. Veja o arquivo `LICENSE` para mais detalhes.

## 🌟 **Agradecimentos**

- L2JFree Team pelo projeto original
- Comunidade Java pela evolução da linguagem
- Todos os contribuidores que ajudaram na migração

---

**Projeto migrado com sucesso para Java 21! 🚀**