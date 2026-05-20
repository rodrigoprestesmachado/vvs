---
layout: default
title: Rest Assured
parent: Teste de componente
grand_parent: Teste de desenvolvimento
nav_order: 12
---

# Rest Assured 🔍

<center>
<iframe src="https://vvs.rpmhub.dev/componente/slides/index.html#/"
    title="Rest Assured" width="90%" height="500" style="border:none;"></iframe>
</center>

## O que é o Rest Assured?

Rest Assured é uma biblioteca Java criada para automatizar testes de **APIs REST**. Com ela, podemos enviar requisições HTTP (GET, POST, PUT, DELETE, etc.) e verificar se as respostas voltam como esperávamos.

### Uma analogia para entender

Imagine que uma API REST é um **restaurante** e que você é o cliente:

* A **requisição** é o seu pedido ao garçom ("quero uma pizza de mussarela").
* A **resposta** é o prato que chega à mesa.
* O **teste** é a sua avaliação: o pedido chegou? É realmente uma pizza? É de mussarela? Está na temperatura certa?

O Rest Assured é o "cliente experiente" que faz o pedido, recebe o prato e confere cada detalhe automaticamente, sem que você precise fazer isso na mão a cada refeição.

### Por que usar Rest Assured?

* **Sintaxe fluente e legível**: o código se parece com a descrição do teste em inglês (`given... when... then...`).
* **Integração natural com JUnit**: roda junto com os outros testes do seu projeto.
* **Validações poderosas**: permite verificar status, cabeçalhos, cookies, JSON, XML, tempo de resposta, entre outros.
* **Pouca configuração**: basta adicionar a dependência no `pom.xml` e começar a escrever testes.

### A estrutura Given, When, Then

Todo teste com Rest Assured segue um padrão chamado **BDD (Behavior Driven Development)**, que organiza o teste em três etapas:

| Etapa     | Significado                                        | Analogia do restaurante         |
|-----------|----------------------------------------------------|---------------------------------|
| `given()` | Dado um contexto (cabeçalhos, parâmetros, corpo)   | Como você se prepara para pedir |
| `when()`  | Quando uma ação acontece (GET, POST, etc.)         | O momento em que faz o pedido   |
| `then()`  | Então valide o resultado                           | A conferência do prato          |

### O que podemos validar?

Com Rest Assured é possível validar:

* O **código de status HTTP** (200, 201, 404, 500, etc.).
* Os **cabeçalhos** da resposta.
* O **corpo** da resposta (em JSON ou XML).
* **Campos específicos** dentro do JSON.
* **Cookies** retornados pelo servidor.
* O **tempo de resposta**.

---

## Exemplos passo a passo

### 1. Exemplo básico: validando status e corpo da resposta

```java
@Test
public void testHelloEndpoint() {
    given()
    .when()
        .get("/hello")
    .then()
        .statusCode(200)
        .body(is("hello"));
}
```

**Lendo o código como uma frase:**
> *"Dada uma requisição, quando eu fizer um GET em `/hello`, então o status deve ser 200 e o corpo deve ser `hello`."*

* `given()` → prepara a requisição (mesmo sem nada configurado, é boa prática manter).
* `when().get("/hello")` → dispara a chamada HTTP do tipo GET.
* `then()` → inicia a etapa de validação.
* `statusCode(200)` → confere se o servidor respondeu com sucesso.
* `body(is("hello"))` → confere se o corpo retornado é exatamente `hello`.

### 2. Validando campos de um JSON

Imagine que a API devolveu este JSON ao consultar `/user/123`:

```json
{
  "id": 123,
  "name": "John Doe",
  "email": "john@example.com"
}
```

Podemos validar campo a campo assim:

```java
@Test
public void validateJsonFields() {
    given()
    .when()
        .get("https://api.example.com/user/123")
    .then()
        .statusCode(200)
        .body("id", equalTo(123))
        .body("name", equalTo("John Doe"))
        .body("email", containsString("@example.com"));
}
```

**O que está acontecendo:**

* `equalTo(123)` → o campo `id` precisa ser **exatamente** 123.
* `equalTo("John Doe")` → o campo `name` precisa ser **exatamente** essa string.
* `containsString("@example.com")` → o campo `email` precisa **conter** esse trecho (não precisa ser idêntico).

> 💡 **Dica:** `equalTo` é estrito; `containsString` é mais flexível. Escolha conforme o que faz sentido para o seu teste.

### 3. Enviando cabeçalhos personalizados (ex.: token JWT)

Em APIs reais, muitos endpoints exigem autenticação por token. É como entrar em uma área VIP de uma boate: você precisa apresentar o **selo** (token) na porta.

```java
@Test
public void requestWithJWTHeader() {
    String apiUrl = "https://api.example.com/resource";
    String jwtToken = "seu_token_jwt_aqui";

    Response response = given()
        .header("Authorization", "Bearer " + jwtToken)
    .when()
        .get(apiUrl);

    response.then().statusCode(200);
}
```

**O que está acontecendo:**

* `.header("Authorization", "Bearer " + jwtToken)` → adiciona o cabeçalho de autenticação na requisição.
* O servidor lê esse cabeçalho e, se o token for válido, libera o acesso ao recurso.

### 4. Validação de cookies

```java
@Test
public void validateCookies() {
    String apiUrl = "https://api.example.com/resource";

    Response response = given().when().get(apiUrl);

    response.then().statusCode(200);
    response.then().cookie("session_cookie");
    response.then().cookie("user_id", equalTo("123"));
}
```

**O que está acontecendo:**

* `cookie("session_cookie")` → verifica apenas se o cookie existe.
* `cookie("user_id", equalTo("123"))` → verifica se o cookie existe **e** se tem o valor `"123"`.

> 🍪 **Analogia:** cookies são como pulseiras de festa. Quem entra recebe uma, e o sistema confere se você ainda está com ela quando volta da pista.

### 5. Autenticação básica HTTP

```java
@Test
public void autenticaUsuarioComSucesso() {
    given()
        .auth().basic("usuario", "senha")
    .when()
        .get("/api/area-restrita")
    .then()
        .statusCode(200)
        .body("mensagem", equalTo("Acesso permitido"));
}
```

**O que está acontecendo:**

* `.auth().basic("usuario", "senha")` → envia usuário e senha codificados em Base64 no cabeçalho da requisição.
* Se as credenciais forem válidas, o servidor devolve status 200 e a mensagem esperada.

> ⚠️ **Atenção:** autenticação básica não é segura sem HTTPS, porque as credenciais viajam praticamente em texto plano.

### 6. Enviando parâmetros de consulta (query params)

Query params são aqueles trechos depois da `?` na URL, como em:
`/api/usuarios?idade=30&cidade=Porto+Alegre`

```java
@Test
public void buscaUsuariosPorFiltro() {
    given()
        .queryParam("idade", 30)
        .queryParam("cidade", "Porto Alegre")
    .when()
        .get("/api/usuarios")
    .then()
        .statusCode(200)
        .body("size()", greaterThan(0));
}
```

**O que está acontecendo:**

* `.queryParam("idade", 30)` → adiciona `idade=30` na URL.
* `.queryParam("cidade", "Porto Alegre")` → adiciona `cidade=Porto+Alegre`.
* `body("size()", greaterThan(0))` → verifica se a lista retornada tem pelo menos um item.

### 7. Upload de arquivo

```java
@Test
public void uploadArquivoComSucesso() {
    given()
        .multiPart("arquivo", new File("caminho/para/arquivo.txt"))
    .when()
        .post("/api/upload")
    .then()
        .statusCode(201)
        .body("mensagem", containsString("Upload realizado"));
}
```

**O que está acontecendo:**

* `.multiPart("arquivo", new File(...))` → anexa um arquivo no corpo da requisição usando o formato `multipart/form-data`. É o mesmo formato usado por formulários web com `<input type="file">`.
* O status `201 Created` indica que o recurso foi criado com sucesso.

### 8. Validando resposta de erro (404)

Nem todo teste verifica o caminho feliz. Também é importante garantir que a API responde corretamente quando algo dá errado.

```java
@Test
public void retornaErroQuandoUsuarioNaoExiste() {
    given()
    .when()
        .get("/api/usuarios/99999")
    .then()
        .statusCode(404)
        .body("erro", equalTo("Usuário não encontrado"));
}
```

**O que está acontecendo:**

* Buscamos um usuário com um ID que sabemos não existir.
* Esperamos status `404 Not Found`.
* Verificamos se a mensagem de erro corresponde ao contrato da API.

### 9. Enviando dados em formato JSON (POST)

```java
@Test
public void criaNovoUsuario() {
    String novoUsuario = "{\"nome\":\"Maria\",\"email\":\"maria@email.com\"}";

    given()
        .contentType("application/json")
        .body(novoUsuario)
    .when()
        .post("/api/usuarios")
    .then()
        .statusCode(201)
        .body("nome", equalTo("Maria"));
}
```

**O que está acontecendo:**

* `.contentType("application/json")` → avisa ao servidor que estamos enviando JSON no corpo.
* `.body(novoUsuario)` → envia o JSON.
* `.post("/api/usuarios")` → cria o novo recurso.
* O status `201` confirma a criação, e validamos que o nome retornado é `"Maria"`.

---

## Resumo dos principais matchers

Os **matchers** são as expressões que aparecem dentro de `body(...)`, `cookie(...)`, etc. Eles vêm da biblioteca **Hamcrest**, que o Rest Assured usa por baixo dos panos.

| Matcher                   | Para que serve                                     |
|---------------------------|----------------------------------------------------|
| `equalTo(x)`              | Valor exatamente igual a `x`                       |
| `containsString("abc")`   | A string contém o trecho `"abc"`                   |
| `startsWith("abc")`       | A string começa com `"abc"`                        |
| `endsWith("xyz")`         | A string termina com `"xyz"`                       |
| `greaterThan(10)`         | Valor numérico maior que 10                        |
| `lessThan(10)`            | Valor numérico menor que 10                        |
| `hasItems("a", "b")`      | A lista contém os itens `"a"` e `"b"`              |
| `notNullValue()`          | O valor não pode ser nulo                          |
| `is(...)`                 | Açúcar sintático (mesmo efeito de `equalTo`)       |

---

## Exercícios 📝

Considere o sistema de uma **biblioteca** que expõe uma API REST em `http://localhost:8080` para gerenciar o acervo de livros (CRUD completo). Implemente os testes usando Rest Assured e JUnit.

**Endpoints disponíveis:**

| Método | Endpoint        | Descrição                          |
|--------|-----------------|------------------------------------|
| GET    | `/books`        | Lista todos os livros              |
| GET    | `/books/{id}`   | Busca um livro pelo ID             |
| POST   | `/books`        | Cadastra um novo livro             |
| PUT    | `/books/{id}`   | Atualiza um livro existente        |
| DELETE | `/books/{id}`   | Remove um livro do acervo          |
| POST   | `/login`        | Autentica o bibliotecário          |
| GET    | `/loans`        | Lista os empréstimos (autenticado) |

**Modelo do recurso `Book` (em JSON):**

```json
{
  "id": 1,
  "title": "Dom Casmurro",
  "author": "Machado de Assis",
  "year": 1899,
  "available": true
}
```

### Exercício 1: Status code

Escreva um teste que verifique se o endpoint `GET /books` retorna o status HTTP `200`.

### Exercício 2: Validação de campo simples

A API responde a `GET /books/1` com:

```json
{
  "id": 1,
  "title": "Dom Casmurro",
  "author": "Machado de Assis",
  "year": 1899,
  "available": true
}
```

Escreva um teste que valide:

* O status `200`.
* O campo `title` é igual a `"Dom Casmurro"`.
* O campo `author` é igual a `"Machado de Assis"`.
* O campo `year` é menor que `1900`.
* O campo `available` é igual a `true`.

### Exercício 3: Query params

Escreva um teste para `GET /books?author=Machado+de+Assis&available=true` que verifique:

* O status `200`.
* A resposta contém pelo menos um livro (`size()` maior que 0).
* Todos os livros retornados têm `available` igual a `true`.

> 💡 **Dica:** para validar uma propriedade em todos os itens da lista, use `body("available", everyItem(equalTo(true)))`.

### Exercício 4: Criação de livro (POST)

Envie uma requisição `POST /books` com o corpo:

```json
{
  "title": "1984",
  "author": "George Orwell",
  "year": 1949,
  "available": true
}
```

Valide que:

* O status é `201`.
* O campo `title` retornado é `"1984"`.
* O campo `id` não é nulo (use `notNullValue()`).

### Exercício 5: Atualização de livro (PUT)

Suponha que o livro com `id = 1` já existe. Envie `PUT /books/1` com o corpo:

```json
{
  "title": "Dom Casmurro",
  "author": "Machado de Assis",
  "year": 1899,
  "available": false
}
```

Valide que:

* O status é `200`.
* O campo `available` agora é `false` (o livro foi emprestado).

### Exercício 6: Remoção de livro (DELETE)

Crie um teste que envie `DELETE /books/1` e valide:

* O status retornado é `204 No Content`.

Em seguida, faça um `GET /books/1` no mesmo teste e verifique que agora a resposta é `404`.

### Exercício 7: Autenticação obrigatória

Crie um teste para `GET /loans` que:

* Envie o cabeçalho `Authorization: Bearer abc.123.xyz`.
* Espere o status `200`.

Em seguida, crie um segundo teste, **sem** o cabeçalho, e verifique que o status retornado é `401` (não autorizado).

### Exercício 8: Erro 404

Escreva um teste para `GET /books/99999` que verifique:

* O status `404`.
* O corpo contém o campo `error` com a mensagem `"Book not found"`.

### Exercício 9: Desafio 🚀

Combine vários conceitos em um único fluxo de teste que represente o trabalho real de um bibliotecário:

1. Faça `POST /login` enviando JSON com `username` e `password`, e **capture o token JWT** retornado.
2. Use esse token para fazer `POST /books` e cadastrar um novo livro. Capture o `id` retornado.
3. Faça `GET /books/{id}` para confirmar que o livro foi criado corretamente.
4. Faça `DELETE /books/{id}` para remover o livro.
5. Valide que cada etapa retornou o status esperado.

> 💡 **Dica:** use `.extract().path("token")` para capturar o token e `.extract().path("id")` para capturar o ID do livro recém criado.

---

## Referências

* [Documentação oficial do Rest Assured](https://rest-assured.io/)
* [Hamcrest Matchers](http://hamcrest.org/JavaHamcrest/)
* [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
