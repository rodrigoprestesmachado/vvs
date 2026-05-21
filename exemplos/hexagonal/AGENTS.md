# Contexto do projeto (para assistentes de IA)

## Finalidade

Aplicação **Quarkus** de exemplo que implementa um **CRUD de livros** com **arquitetura hexagonal** (Ports and Adapters): o núcleo de domínio é independente de frameworks; **portas de entrada** definem casos de uso; **portas de saída** abstraem persistência e notificações; **adaptadores** implementam REST, JPA reativo e um “e-mail” baseado em log.

## Stack técnica

- **Java 25**, **Maven**, **Quarkus** (~3.34, BOM importado no `pom.xml`).
- **REST** (`quarkus-rest`): recurso principal em `adapters.in.rest.BookResource`, base path **`/books`**.
- **Persistência**: Hibernate **Reactive** + **Panache** (`quarkus-hibernate-reactive-panache`), cliente **MySQL reativo** (`quarkus-reactive-mysql-client`).
- **Dev Services**: MySQL via Docker em dev/test (`application.properties`: `quarkus.datasource.db-kind=mysql`, usuário/senha `pw2`, porta devservices **3306** por padrão).
- **MapStruct** (`BookMapper`): entidade JPA ↔ modelo de domínio.
- **Lombok** em entidade JPA (`BookEntity`).
- **Jackson** para JSON; **SmallRye OpenAPI** / Swagger UI.
- **Testes**: `quarkus-junit`, RestAssured (`./mvnw test`); E2E da UI Vue com **Playwright** em `frontend/e2e/` (`./mvnw verify -Pe2e`, perfil Maven `e2e`). Docker necessário (Dev Services MySQL).

## Layout de pacotes (`src/main/java/dev/ifrs/hexagonal`)

- **`domain.model`**: agregado `Book` (validação ISBN-10, builder para persistência).
- **`domain.exception`**: exceções de domínio/validação mapeadas para HTTP no adaptador REST.
- **`domain.ports.in`**: interfaces de casos de uso (`AddBookUseCase`, `ListBookUseCase`, `UpdateBookUseCase`, `DeleteBookUseCase`).
- **`domain.ports.out`**: `BookRepository`, `EmailNotification`.
- **`domain.service`**: `BooksService` — implementa todas as portas de entrada e orquestra repositório + notificador.
- **`adapters.config`**: `ApplicationBeans` — CDI: injeta repositório/notificador, instancia `BooksService`, expõe cada caso de uso com `@Produces`.
- **`adapters.in.rest`**: `BookResource`, DTOs (`BookRequest`, `BookResponse`), `BookExceptionMapper` (erros JSON com campo `message`).
- **`adapters.out.persistence`**: `PanacheBookRepository`, `BookEntity`, `BookMapper`.
- **`adapters.out.notification`**: `LogEmailNotification` — implementa `EmailNotification` com logs.

Há **`package-info.java`** em vários pacotes (exigência de documentação / Checkstyle).

## Comportamento relevante

- Criação/atualização usam `Book.of(...)` para validar invariantes.
- Exceções comuns: ISBN duplicado, livro não encontrado, dados inválidos — mapeadas para códigos HTTP no `BookExceptionMapper` (incluindo desembrulhar `CompletionException` na cadeia de causas).
- Geração de schema Hibernate em dev: `drop-and-create` (ver `application.properties`).

## Comandos úteis

- Desenvolvimento: `./mvnw quarkus:dev` (raiz do módulo `hexagonal`).
- Testes API: `./mvnw test`.
- Testes E2E (UI): `./mvnw verify -Pe2e` ou `cd frontend && npm run test:e2e` (com Quarkus em `quarkus:dev`).
- Checkstyle: `./mvnw checkstyle:check` (plugin configurado com Checkstyle recente no `pom.xml`).

## Convenções para alterações

- Manter separação **domínio / portas / adaptadores**; não importar JAX-RS ou JPA dentro de `domain.*`.
- Respeitar **Checkstyle** (regras estilo Sun) ao editar código existente.
- DTOs REST e domínio: nomes de propriedades JSON alinhados aos getters/`@JsonProperty` onde aplicável.

## Documentação humana

Ver **`README.md`** na raiz deste projeto para explicação voltada a pessoas e passos de execução.
