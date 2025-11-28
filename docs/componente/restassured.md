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

Rest Assured é uma biblioteca Java para automação de testes de APIs REST. Ela permite enviar requisições HTTP e validar as respostas de forma simples e poderosa, sendo amplamente utilizada no mundo da automação de testes de API.

### Por que usar Rest Assured?

- Simples de usar e altamente configurável
- Integra-se facilmente com frameworks de teste como JUnit
- Possui recursos avançados para validação de respostas

### Validação de Respostas
Com Rest Assured, é possível validar:
- Código de status HTTP
- Cabeçalhos
- Corpo da resposta (JSON/XML)
- Campos específicos

### Exemplo básico de validação de resposta

```java
@Test
public void testHelloEndpoint() {
    given()
    .when().get("/hello")
    .then()
        .statusCode(200)
        .body(is("hello"));
}
```

Neste exemplo:
- `given()` prepara a requisição
- `when().get("/hello")` executa a chamada HTTP
- `then()` define as validações
- `statusCode(200)` verifica se o status é 200
- `body(is("hello"))` verifica se o corpo da resposta é "hello"

### Exemplo de validação de JSON

```java
@Test
public void validateJsonFields() {
    given()
        .when().get("https://api.example.com/user/123")
        .then()
            .statusCode(200)
            .body("id", equalTo(123))
            .body("name", equalTo("John Doe"))
            .body("email", containsString("@example.com"));
}
```

Neste exemplo:
- A requisição GET é feita para o endpoint do usuário
- Valida o status 200
- Verifica se o campo `id` é 123
- Verifica se o campo `name` é "John Doe"
- Verifica se o campo `email` contém "@example.com"

### Modificando cabeçalhos da requisição

```java
@Test
public void requestWithJWTHeader() {
    String apiUrl = "https://api.example.com/resource";
    String jwtToken = "your_jwt_token_here";
    Response response = given()
        .header("Authorization", "Bearer " + jwtToken)
        .when().get(apiUrl);
    response.then().statusCode(200);
}
```

No exemplo acima:
- Prepara o método de teste
- Inicia a requisição
- Adiciona o cabeçalho de autorização com token JWT

### Validação de Cookies

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

No exemplo acima:
- Prepara o método de teste
- Inicia a requisição
- Executa a requisição
- Valida o status 200
- Verifica se o cookie 'session_cookie' está presente
- Verifica se o cookie 'user_id' tem o valor '123'  

### Testando autenticação básica

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

O código acima:
- Prepara o método de teste
- Inicia a requisição
- Define autenticação básica HTTP
- Indica o momento de executar a requisição
- Realiza um GET no endpoint restrito
- Inicia as validações da resposta
- Espera que o status HTTP seja 200 (OK)
- Valida o campo 'mensagem' no corpo da resposta

### Enviando parâmetros de consulta (query params)

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

O código acima:
- Prepara o método de teste
- Inicia a requisição
- Adiciona parâmetro de consulta 'idade=30'
- Adiciona parâmetro de consulta 'cidade=Porto Alegre'
- Executa a requisição
- Realiza GET no endpoint de usuários
- Inicia as validações
- Espera status 200
- Verifica se a lista retornada não está vazia

### Testando upload de arquivo

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

O código acima:
- Prepara o método de teste
- Inicia a requisição
- Adiciona arquivo ao corpo da requisição (multipart)
- Executa a requisição
- Realiza POST para upload
- Inicia as validações
- Espera status 201 (criado)
- Verifica mensagem de sucesso

### Validando resposta de erro (exemplo: 404)

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

O método acima:
- Inicia a requisição
- Executa a requisição
- Busca usuário inexistente
- Inicia as validações
- Espera status 404 (não encontrado)
- Valida mensagem de erro

### Enviando dados em formato JSON (POST)

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

O método acima:
- JSON do novo usuário
- Inicia a requisição
- Define o tipo de conteúdo como JSON
- Adiciona o corpo da requisição
- Executa a requisição
- Realiza POST para criar usuário
- Inicia as validações
- Espera status 201 (criado)
- Valida se o nome retornado é "Maria"


## Referências

- [Documentação Oficial do Rest Assured](https://rest-assured.io/)


<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>