# Livros — exemplo em arquitetura hexagonal (Quarkus)

API REST de **cadastro de livros** (CRUD) usada como exemplo didático de **arquitetura hexagonal**: o domínio não depende de framework, banco ou HTTP; adaptadores conectam o núcleo ao mundo externo.

## O que o projeto faz

- Interface web em **Vue 3** com **Bulma** na raiz **`/`** (CRUD completo sobre `/books`).
- Expõe recursos HTTP em `/books` para criar, listar, atualizar e remover livros.
- Valida **ISBN-10** e regras básicas de negócio no **domínio** (`Book`, exceções de domínio).
- Persiste dados em **MySQL** de forma **reativa** (Hibernate Reactive + Panache).
- Inclui um adaptador de “e-mail” que apenas **registra eventos em log** (substituto simples de envio real).
- Documentação OpenAPI e Swagger UI (quando a aplicação está em execução).
- Testes automatizados da **API** (JUnit + RestAssured) e da **interface web** (Playwright E2E).

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
- **Node.js** (opcional): só é necessário se você desenvolver o front isoladamente com `npm run dev`; no fluxo Maven o plugin `frontend-maven-plugin` instala Node/npm automaticamente e executa o build do Vue.

> Se a porta **3306** já estiver em uso no seu computador, o container do MySQL pode falhar ao subir. Libere a porta ou ajuste `quarkus.datasource.devservices.port` em `src/main/resources/application.properties`.

## Como executar em modo desenvolvimento

Na raiz do projeto:

```bash
./mvnw quarkus:dev
```

O Maven compila o front-end Vue na fase `generate-resources` (artefatos em `src/main/resources/META-INF/resources/`) antes de subir a aplicação. Para pular esse passo (por exemplo, se ainda não alterou o front):

```bash
./mvnw quarkus:dev -Dskip.frontend=true
```

- Aplicação e interface: <http://localhost:8080/> (porta HTTP padrão do Quarkus: **8080**).
- A API REST continua em `/books` (o front usa `fetch` relativo ao mesmo host).
- O Dev UI fica em: <http://localhost:8080/q/dev/> (apenas em dev).

### Desenvolvimento só do front-end (hot reload)

Com o back-end já em execução (`./mvnw quarkus:dev` em outro terminal):

```bash
cd frontend
npm install
npm run dev
```

O Vite sobe em <http://localhost:5173/> e encaminha `/books` para `http://localhost:8080` (proxy no `vite.config.js`).

Para gerar os estáticos manualmente (sem Maven):

```bash
cd frontend
npm install
npm run build
```

A saída vai para `src/main/resources/META-INF/resources/` (servida pelo Quarkus na raiz `/`).

Endpoints REST (prefixo comum do recurso):

- `GET /books` — lista todos os livros.
- `POST /books` — cria um livro (corpo JSON com ISBN, título, autor, ano, exemplares).
- `PUT /books/{isbn}` — atualiza um livro existente.
- `DELETE /books/{isbn}` — remove pelo ISBN.

Documentação interativa:

- Swagger UI costuma estar em <http://localhost:8080/q/swagger-ui/> (com `quarkus.swagger-ui.always-include=true`).

## Testes

O projeto inclui dois tipos de teste automatizado:

| Tipo | Ferramenta | O que valida | Comando |
|------|------------|--------------|---------|
| API e domínio | JUnit, RestAssured (`@QuarkusTest`) | Endpoints `/books`, códigos HTTP, regras expostas pela API | `./mvnw test` |
| Interface web (E2E) | [Playwright](https://playwright.dev/) | Fluxo CRUD na UI Vue (`/`) contra a API real | `./mvnw verify -Pe2e` |

Em ambos os casos o Quarkus usa **Dev Services** para subir o MySQL em Docker. Garanta **Docker em execução** e as portas **8080** (HTTP) e **3306** (MySQL devservices) livres.

### API e domínio

Testes em `src/test/java/` (por exemplo `BookResourceTest`, `BooksServiceTest`):

```bash
./mvnw test
```

### Interface web (Playwright)

Os testes E2E ficam em `frontend/e2e/` (spec principal: `books.spec.js`). A configuração está em `frontend/playwright.config.js`.

**Via Maven** (recomendado): compila o front, instala o Chromium, sobe o Quarkus em modo dev e executa o Playwright. Use o perfil Maven `e2e`:

```bash
./mvnw verify -Pe2e
```

O perfil roda, nesta ordem: testes unitários/integração Java (`test`), instalação do browser (`setup:e2e`), testes E2E (`test:e2e`). A primeira execução pode demorar mais (download do Chromium e subida do MySQL).

> **Não** combine `-Pe2e` com `-DskipTests`: o `frontend-maven-plugin` interpreta `skipTests` e pula os testes npm, incluindo o Playwright.

**Somente o front** (com o back-end já em execução):

```bash
./mvnw quarkus:dev
```

Em outro terminal:

```bash
cd frontend
npm install
npm run setup:e2e    # uma vez (ou após atualizar @playwright/test)
npm run test:e2e     # sobe o Quarkus automaticamente se nada estiver na 8080
```

Se a aplicação já estiver rodando e você quiser evitar uma segunda instância:

```bash
BASE_URL=http://localhost:8080 npm run test:e2e
```

**Modo interativo** (depurar passo a passo no navegador):

```bash
cd frontend
npx playwright test --ui
```

Relatório HTML após falhas: `npx playwright show-report` (gerado em `frontend/playwright-report/`).

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
