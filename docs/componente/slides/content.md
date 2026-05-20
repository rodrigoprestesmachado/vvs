<!-- .slide:  data-background-opacity="0.1" data-background-image="https://t4.ftcdn.net/jpg/05/18/53/19/360_F_518531918_oYY8KSe4BpIPHLWVx46UaOqddEdviSD6.jpg"
data-transition="convex"  -->
# Introdução ao Rest Assured
<!-- .element: style="margin-bottom:100px; font-size: 50px; color:white; font-family: Marker Felt;" -->

Pressione 'F' para tela cheia
<!-- .element: style="font-size: small; color:white;" -->

[versão em pdf](?print-pdf)
<!-- .element: style="font-size: small;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O que é o Rest Assured?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Rest Assured é uma **biblioteca Java** para automação de testes de APIs REST.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Permite **enviar requisições HTTP** e **validar as respostas** da API.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* É uma das ferramentas mais usadas no mundo da automação de testes de API.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Uma analogia 🍕
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

Pense em uma API REST como um **restaurante**:
<!-- .element: style="margin-bottom:30px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* A **requisição** é o seu pedido ao garçom.
<!-- .element: style="margin-bottom:20px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* A **resposta** é o prato que chega à mesa.
<!-- .element: style="margin-bottom:20px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* O **teste** é a sua avaliação: chegou? É o prato certo? Está na temperatura certa?
<!-- .element: style="margin-bottom:40px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

O Rest Assured é o **cliente experiente** que faz o pedido, recebe o prato e confere cada detalhe automaticamente. 🤖
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Por que Rest Assured?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* **Sintaxe fluente e legível**: o teste se lê quase como uma frase em inglês.
<!-- .element: style="margin-bottom:30px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* **Integração natural com JUnit**: roda junto com os demais testes do projeto.
<!-- .element: style="margin-bottom:30px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* **Validações poderosas**: status, cabeçalhos, JSON, XML, cookies, tempo de resposta…
<!-- .element: style="margin-bottom:30px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* **Pouca configuração**: basta adicionar a dependência no `pom.xml`.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Estrutura Given, When, Then
<!-- .element: style="margin-bottom:30px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

Todo teste segue um padrão chamado **BDD** (Behavior Driven Development):
<!-- .element: style="margin-bottom:30px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* **given()** → Dado um contexto (cabeçalhos, parâmetros, corpo) → *como você se prepara para pedir.*
<!-- .element: style="margin-bottom:25px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* **when()** → Quando uma ação acontece (GET, POST...) → *o momento em que faz o pedido.*
<!-- .element: style="margin-bottom:25px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* **then()** → Então valide o resultado → *a conferência do prato que chega.*
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O que podemos validar?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* **Código de status HTTP** (200, 201, 404, 500…).
<!-- .element: style="margin-bottom:25px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* **Cabeçalhos** da resposta.
<!-- .element: style="margin-bottom:25px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* **Corpo** da resposta (JSON ou XML).
<!-- .element: style="margin-bottom:25px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* **Campos específicos** dentro do JSON.
<!-- .element: style="margin-bottom:25px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* **Cookies** retornados pelo servidor.
<!-- .element: style="margin-bottom:25px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* **Tempo de resposta** da API.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo 1: validação básica
<!-- .element: style="margin-bottom:30px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

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
<!-- .element: style="background-color: white; margin-bottom:30px; font-size: 20px; font-family: arial; color:black"  -->

Lendo como uma frase: *"Dada uma requisição, quando eu fizer GET em `/hello`, então o status deve ser 200 e o corpo deve ser `hello`."*
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo 1: explicação
<!-- .element: style="margin-bottom:30px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* **given()** → prepara a requisição (mesmo vazio, é boa prática manter).
<!-- .element: style="margin-bottom:25px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* **.when().get("/hello")** → dispara a chamada HTTP do tipo GET.
<!-- .element: style="margin-bottom:25px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* **.then()** → inicia a etapa de validação.
<!-- .element: style="margin-bottom:25px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* **.statusCode(200)** → confere se o servidor respondeu com sucesso.
<!-- .element: style="margin-bottom:25px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* **.body(is("hello"))** → confere se o corpo é exatamente `hello`.
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo 2: validando campos JSON
<!-- .element: style="margin-bottom:20px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

Imagine que `/user/123` devolve:
<!-- .element: style="margin-bottom:10px; font-size: 18px; font-family: arial; color:#F5F5F5" -->

```json
{ "id": 123, "name": "John Doe", "email": "john@example.com" }
```
<!-- .element: style="background-color: white; margin-bottom:15px; font-size: 16px; font-family: arial; color:black"  -->

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
<!-- .element: style="background-color: white; margin-bottom:30px; font-size: 17px; font-family: arial; color:black"  -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo 2: explicação
<!-- .element: style="margin-bottom:30px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* **equalTo(123)** → o campo `id` precisa ser **exatamente** 123.
<!-- .element: style="margin-bottom:30px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* **equalTo("John Doe")** → o campo `name` precisa ser **exatamente** essa string.
<!-- .element: style="margin-bottom:30px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* **containsString("@example.com")** → o campo `email` precisa **conter** esse trecho (não precisa ser idêntico).
<!-- .element: style="margin-bottom:30px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

💡 **Dica:** `equalTo` é estrito; `containsString` é mais flexível.
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#C9E66A" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo 3: cabeçalhos (JWT) 🔐
<!-- .element: style="margin-bottom:20px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

Token JWT é como o **selo da área VIP** de uma boate. Você precisa apresentar na porta para entrar.
<!-- .element: style="margin-bottom:15px; font-size: 18px; font-family: arial; color:#F5F5F5" -->

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
<!-- .element: style="background-color: white; margin-bottom:20px; font-size: 17px; font-family: arial; color:black"  -->

`.header(...)` adiciona o cabeçalho de autenticação. Se o token for válido, o servidor libera o recurso.
<!-- .element: style="margin-bottom:30px; font-size: 18px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo 4: cookies 🍪
<!-- .element: style="margin-bottom:20px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

Cookies são como **pulseiras de festa**: o servidor entrega, e depois confere se você ainda está com ela.
<!-- .element: style="margin-bottom:15px; font-size: 18px; font-family: arial; color:#F5F5F5" -->

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
<!-- .element: style="background-color: white; margin-bottom:20px; font-size: 17px; font-family: arial; color:black"  -->

`cookie("nome")` verifica se existe; `cookie("nome", equalTo("valor"))` verifica também o valor.
<!-- .element: style="margin-bottom:30px; font-size: 18px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo 5: autenticação básica
<!-- .element: style="margin-bottom:20px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

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
<!-- .element: style="background-color: white; margin-bottom:20px; font-size: 18px; font-family: arial; color:black"  -->

`.auth().basic(...)` envia usuário e senha codificados em Base64 no cabeçalho.
<!-- .element: style="margin-bottom:15px; font-size: 18px; font-family: arial; color:#F5F5F5" -->

⚠️ **Atenção:** sem HTTPS, as credenciais viajam praticamente em texto plano.
<!-- .element: style="margin-bottom:30px; font-size: 18px; font-family: arial; color:#C9E66A" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo 6: query params
<!-- .element: style="margin-bottom:20px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

Query params são os trechos depois da `?` na URL:
`/api/usuarios?idade=30&cidade=Porto+Alegre`
<!-- .element: style="margin-bottom:15px; font-size: 18px; font-family: arial; color:#F5F5F5" -->

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
<!-- .element: style="background-color: white; margin-bottom:20px; font-size: 17px; font-family: arial; color:black"  -->

`body("size()", greaterThan(0))` verifica se a lista retornada tem ao menos um item.
<!-- .element: style="margin-bottom:30px; font-size: 18px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo 7: upload de arquivo 📎
<!-- .element: style="margin-bottom:20px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

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
<!-- .element: style="background-color: white; margin-bottom:20px; font-size: 18px; font-family: arial; color:black"  -->

`.multiPart(...)` anexa o arquivo no formato `multipart/form-data`. É o mesmo formato usado por formulários web com `<input type="file">`.
<!-- .element: style="margin-bottom:30px; font-size: 18px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo 8: testando erros (404)
<!-- .element: style="margin-bottom:20px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

Nem todo teste verifica o caminho feliz. Também é importante testar o que dá errado.
<!-- .element: style="margin-bottom:15px; font-size: 18px; font-family: arial; color:#F5F5F5" -->

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
<!-- .element: style="background-color: white; margin-bottom:20px; font-size: 18px; font-family: arial; color:black"  -->

Buscamos um ID que sabemos não existir e validamos a mensagem de erro do contrato da API.
<!-- .element: style="margin-bottom:30px; font-size: 18px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo 9: POST com JSON
<!-- .element: style="margin-bottom:20px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

```java
@Test
public void criaNovoUsuario() {
    String novoUsuario =
        "{\"nome\":\"Maria\",\"email\":\"maria@email.com\"}";

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
<!-- .element: style="background-color: white; margin-bottom:20px; font-size: 17px; font-family: arial; color:black"  -->

`.contentType("application/json")` avisa ao servidor que estamos enviando JSON no corpo.
<!-- .element: style="margin-bottom:30px; font-size: 18px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Principais matchers (Hamcrest)
<!-- .element: style="margin-bottom:30px; font-size: 38px; font-family: Marker Felt; color:#F5F5F5" -->

| Matcher                 | Para que serve                            |
|-------------------------|-------------------------------------------|
| `equalTo(x)`            | Valor exatamente igual a `x`              |
| `containsString("abc")` | A string contém o trecho `"abc"`          |
| `startsWith("abc")`     | A string começa com `"abc"`               |
| `endsWith("xyz")`       | A string termina com `"xyz"`              |
| `greaterThan(10)`       | Valor numérico maior que 10               |
| `lessThan(10)`          | Valor numérico menor que 10               |
| `hasItems("a", "b")`    | A lista contém os itens `"a"` e `"b"`     |
| `notNullValue()`        | O valor não pode ser nulo                 |
| `is(...)`               | Açúcar sintático (mesmo efeito de `equalTo`) |
<!-- .element: style="background-color: white; margin-bottom:30px; font-size: 18px; font-family: arial; color:black"  -->


<!-- .slide: data-background="#C9E66A" data-transition="zoom"  -->
# Exercícios 📝
<!-- .element: style="margin-bottom:30px; font-size: 45px; font-family: Marker Felt;" -->

Considere uma API REST em `http://localhost:8080` para uma loja virtual. Implemente os testes com Rest Assured + JUnit.
<!-- .element: style="margin-bottom:30px; font-size: 22px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="convex"  -->
## Exercício 1: Status code
<!-- .element: style="margin-bottom:30px; font-size: 38px; font-family: Marker Felt;" -->

Escreva um teste que verifique se o endpoint `GET /produtos` retorna o status HTTP `200`.
<!-- .element: style="margin-bottom:30px; font-size: 24px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="convex"  -->
## Exercício 2: Validação de campos
<!-- .element: style="margin-bottom:20px; font-size: 38px; font-family: Marker Felt;" -->

A API responde a `GET /produtos/1` com:
<!-- .element: style="margin-bottom:10px; font-size: 22px; font-family: arial;" -->

```json
{ "id": 1, "nome": "Notebook", "preco": 3500.00 }
```
<!-- .element: style="background-color: white; margin-bottom:15px; font-size: 18px; font-family: arial; color:black"  -->

Valide:
* O status `200`.
* O campo `nome` é igual a `"Notebook"`.
* O campo `preco` é maior que `3000`.
<!-- .element: style="margin-bottom:30px; font-size: 22px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="convex"  -->
## Exercício 3: Query params
<!-- .element: style="margin-bottom:30px; font-size: 38px; font-family: Marker Felt;" -->

Escreva um teste para
`GET /produtos?categoria=eletronicos&precoMax=5000`
que verifique:
<!-- .element: style="margin-bottom:20px; font-size: 22px; font-family: arial;" -->

* O status `200`.
* A resposta contém pelo menos um produto (`size()` maior que 0).
<!-- .element: style="margin-bottom:30px; font-size: 22px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="convex"  -->
## Exercício 4: Criação (POST)
<!-- .element: style="margin-bottom:20px; font-size: 38px; font-family: Marker Felt;" -->

Envie `POST /produtos` com o corpo:
<!-- .element: style="margin-bottom:10px; font-size: 22px; font-family: arial;" -->

```json
{ "nome": "Mouse", "preco": 120.00 }
```
<!-- .element: style="background-color: white; margin-bottom:15px; font-size: 18px; font-family: arial; color:black"  -->

Valide:
* O status é `201`.
* O campo `nome` retornado é `"Mouse"`.
* O campo `id` não é nulo.
<!-- .element: style="margin-bottom:30px; font-size: 22px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="convex"  -->
## Exercício 5: Autenticação
<!-- .element: style="margin-bottom:30px; font-size: 38px; font-family: Marker Felt;" -->

Crie um teste para `GET /pedidos` que:
* Envie o cabeçalho `Authorization: Bearer abc.123.xyz`.
* Espere o status `200`.
<!-- .element: style="margin-bottom:25px; font-size: 22px; font-family: arial;" -->

Crie um **segundo teste**, sem o cabeçalho, e verifique que o status retornado é `401`.
<!-- .element: style="margin-bottom:30px; font-size: 22px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="convex"  -->
## Exercício 6: Erro 404
<!-- .element: style="margin-bottom:30px; font-size: 38px; font-family: Marker Felt;" -->

Escreva um teste para `GET /produtos/99999` que verifique:
<!-- .element: style="margin-bottom:20px; font-size: 22px; font-family: arial;" -->

* O status `404`.
* O corpo contém o campo `erro` com a mensagem `"Produto não encontrado"`.
<!-- .element: style="margin-bottom:30px; font-size: 22px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="convex"  -->
## Exercício 7: Desafio 🚀
<!-- .element: style="margin-bottom:30px; font-size: 38px; font-family: Marker Felt;" -->

Combine vários conceitos em um único teste:
<!-- .element: style="margin-bottom:20px; font-size: 22px; font-family: arial;" -->

1. Faz `POST /login` enviando JSON com `usuario` e `senha`, e **captura o token JWT** retornado.
2. Usa esse token para fazer `GET /pedidos`.
3. Valida status `200` e ao menos um pedido na lista.
<!-- .element: style="margin-bottom:20px; font-size: 21px; font-family: arial;" -->

💡 Dica: use `.extract().path("token")` para capturar o valor.
<!-- .element: style="margin-bottom:30px; font-size: 20px; font-family: arial;" -->


<!-- .slide: data-background="#C9E66A" data-transition="zoom"  -->
# Questões 📚
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt;" -->

<center>
<iframe src="https://vvs.rpmhub.dev/componente/slides/questions.html" title="Rest Assured" width="90%" height="500" style="border:none;"></iframe>
</center>


<!-- .slide: data-background="#185449" data-transition="convex"  -->
# Referências 📚
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Rest Assured. Disponível em: https://rest-assured.io
<!-- .element: style="margin-bottom:30px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Hamcrest Matchers. Disponível em: http://hamcrest.org/JavaHamcrest/
<!-- .element: style="margin-bottom:30px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Quarkus: Testing your application. Disponível em: https://pt.quarkus.io/guides/
<!-- .element: style="margin-bottom:30px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* JUnit 5 User Guide. Disponível em: https://junit.org/junit5/docs/current/user-guide/
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
  <!-- .element: style="margin-top:150px; font-size: 15px; font-family: Bradley Hand" -->
