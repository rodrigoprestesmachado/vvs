# Livros — exemplo em arquitetura hexagonal (Quarkus)

API REST de **cadastro de livros** (CRUD) usada como exemplo didático de **arquitetura hexagonal**: o domínio não depende de framework, banco ou HTTP; adaptadores conectam o núcleo ao mundo externo.

## O que o projeto faz

- Expõe recursos HTTP em `/books` para criar, listar, atualizar e remover livros.
- Valida **ISBN-10** e regras básicas de negócio no **domínio** (`Book`, exceções de domínio).
- Persiste dados em **MySQL** de forma **reativa** (Hibernate Reactive + Panache).
- Inclui um adaptador de “e-mail” que apenas **registra eventos em log** (substituto simples de envio real).
- Documentação OpenAPI e Swagger UI (quando a aplicação está em execução).

Pacotes principais:

| Camada | Pacote (resumo) |
|--------|------------------|
| Domínio | `domain.model`, `domain.service`, `domain.exception`, `domain.ports.*` |
| Adaptador de entrada | `adapters.in.rest` (JAX-RS, DTOs, mapeamento de erros) |
| Adaptador de saída | `adapters.out.persistence`, `adapters.out.notification` |
| Composição (CDI) | `adapters.config` |

## Pré-requisitos

- **Java 25** (conforme `maven.compiler.release` no `pom.xml`).
- **Docker** (recomendado): o Quarkus **Dev Services** sobe um container MySQL automaticamente em desenvolvimento e nos testes, sem você configurar um banco manualmente.
- **Maven** embutido: o projeto inclui `./mvnw` (Linux/macOS) e `mvnw.cmd` (Windows).

> Se a porta **3306** já estiver em uso no seu computador, o container do MySQL pode falhar ao subir. Libere a porta ou ajuste `quarkus.datasource.devservices.port` em `src/main/resources/application.properties`.

## Como executar em modo desenvolvimento

Na raiz do projeto:

```bash
./mvnw quarkus:dev
```

- A API sobe (porta HTTP padrão do Quarkus: **8080**, salvo configuração contrária).
- O Dev UI fica em: <http://localhost:8080/q/dev/> (apenas em dev).

Endpoints REST (prefixo comum do recurso):

- `GET /books` — lista todos os livros.
- `POST /books` — cria um livro (corpo JSON com ISBN, título, autor, ano, exemplares).
- `PUT /books/{isbn}` — atualiza um livro existente.
- `DELETE /books/{isbn}` — remove pelo ISBN.

Documentação interativa:

- Swagger UI costuma estar em <http://localhost:8080/q/swagger-ui/> (com `quarkus.swagger-ui.always-include=true`).

## Testes

```bash
./mvnw test
```

Os testes de integração usam o mesmo stack (Docker para MySQL). Garanta Docker em execução e porta disponível para o serviço de banco.

## Empacotar e rodar o JAR

```bash
./mvnw package
java -jar target/quarkus-app/quarkus-run.jar
```

(O layout padrão Quarkus copia dependências para `target/quarkus-app/lib/`.)

Para **über-jar**:

```bash
./mvnw package -Dquarkus.package.jar.type=uber-jar
java -jar target/*-runner.jar
```

## Verificação de estilo (Checkstyle)

O build de verificação inclui o goal do Checkstyle (configuração Sun). Exemplo:

```bash
./mvnw checkstyle:check
```

## Native (opcional)

Requer GraalVM / setup nativo do Quarkus. Ver [documentação Quarkus sobre native](https://quarkus.io/guides/maven-tooling).

## Licença do cabeçalho nos fontes

Vários arquivos citam a licença **Creative Commons BY 4.0** do material PW2 (Rodrigo Prestes Machado). Ajuste ou remova conforme a política do seu repositório.

## Referências

- [Quarkus](https://quarkus.io/)
- [Arquitetura hexagonal (Ports and Adapters)](https://alistair.cockburn.us/hexagonal-architecture/)
