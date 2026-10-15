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

Rest Assured é uma biblioteca Java criada para automatizar testes de **APIs REST**. Com ela, podemos enviar requisições HTTP (GET, POST, PUT, DELETE, etc.) e verificar se as respostas voltam exatamente como esperávamos.

Em outras palavras: em vez de abrir um cliente HTTP manualmente, disparar a chamada e olhar o resultado com os próprios olhos, o Rest Assured faz isso de forma automática, repetível e dentro dos nossos testes JUnit.

### Uma analogia para entender 🍕

Imagine que uma API REST é um **restaurante** e que você é o cliente:

* A **requisição** é o seu pedido ao garçom ("quero uma pizza de mussarela").
* A **resposta** é o prato que chega à mesa.
* O **teste** é a sua avaliação: o pedido chegou? É realmente uma pizza? É de mussarela? Está na temperatura certa?

O Rest Assured é o cliente experiente que faz o pedido, recebe o prato e confere cada detalhe automaticamente, sem que você precise fazer isso na mão a cada refeição.

### Por que usar Rest Assured?

* **Sintaxe fluente e legível**: o código se parece com a descrição do teste em português ou inglês (`given... when... then...`).
* **Integração natural com JUnit**: roda junto com os outros testes do seu projeto.
* **Validações poderosas**: permite verificar status, cabeçalhos, cookies, JSON, XML, tempo de resposta, entre outros.
* **Pouca configuração**: basta adicionar a dependência no `pom.xml` e começar a escrever testes.

### A estrutura Given, When, Then

Todo teste com Rest Assured segue um padrão chamado **BDD** (Behavior Driven Development), que organiza o teste em três etapas bem definidas:

| Etapa     | Significado                                        | Analogia do restaurante         |
|-----------|-----------------------------------------------------|----------------------------------|
| `given()` | Dado um contexto (cabeçalhos, parâmetros, corpo)    | Como você se prepara para pedir |
| `when()`  | Quando uma ação acontece (GET, POST, etc.)          | O momento em que faz o pedido   |
| `then()`  | Então valide o resultado                            | A conferência do prato          |

> 💡 **Resumo rápido:** se você conseguir ler um teste em voz alta como "dado isso, quando eu fizer aquilo, então deve acontecer isto", você já entendeu a lógica do Rest Assured.

### O que podemos validar?

Com Rest Assured é possível validar:

* O **código de status HTTP** (200, 201, 404, 500, etc.).
* Os **cabeçalhos** da resposta.
* O **corpo** da resposta (em JSON ou XML).
* **Campos específicos** dentro do JSON.
* **Cookies** retornados pelo servidor.
* O **tempo de resposta**.

Nas próximas seções, cada um desses pontos vai aparecer em um exemplo prático, sempre seguindo a mesma lógica: primeiro mostramos o código, depois explicamos linha a linha o que ele faz.

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

Em APIs reais, muitos endpoints exigem autenticação por token. É como entrar em uma área VIP de uma boate: você precisa apresentar o selo (token) na porta.

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
        .get("/api/area/restrita")
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
|----------------------------|-----------------------------------------------------|
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

## Exercícios de fixação 📝

Agora que você já viu os nove exemplos acima, chegou a hora de praticar. Para manter o foco apenas no Rest Assured (sem se preocupar com banco de dados ou regras de negócio complexas), os exercícios a seguir usam uma classe Java bem simples: uma regra bancária exposta como API REST, com alguns endpoints que fazem contas básicas de depósito, saque e juros.

Cada exercício abaixo revisita, na prática, um conceito já apresentado em um dos exemplos anteriores. Implemente a classe abaixo em um projeto Quarkus (ou outro framework de sua preferência) e escreva os testes usando Rest Assured e JUnit.

**A classe `ContaBancariaResource`:**

```java
@Path("/conta")
public class ContaBancariaResource {

    @GET
    @Path("/deposito")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deposito(@QueryParam("saldo") double saldo, @QueryParam("valor") double valor) {
        return Response.ok(new Resultado(saldo + valor)).build();
    }

    @GET
    @Path("/saque")
    @Produces(MediaType.APPLICATION_JSON)
    public Response saque(@QueryParam("saldo") double saldo, @QueryParam("valor") double valor) {
        if (valor > saldo) {
            return Response.status(400)
                    .entity(new Erro("Saldo insuficiente"))
                    .build();
        }
        return Response.ok(new Resultado(saldo - valor)).build();
    }

    @POST
    @Path("/fatura")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fatura(List<Double> compras) {
        if (compras == null || compras.isEmpty()) {
            return Response.status(400)
                    .entity(new Erro("A lista de compras não pode ser vazia"))
                    .build();
        }
        double total = compras.stream().mapToDouble(Double::doubleValue).sum();
        return Response.ok(new Resultado(total)).build();
    }

    @GET
    @Path("/rendimento/{meses}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rendimento(@PathParam("meses") int meses) {
        double valorInicial = 1000;
        double taxaMensal = 0.01;
        double montante = valorInicial + (valorInicial * taxaMensal * meses);
        return Response.ok(new Resultado(montante)).build();
    }

    @GET
    @Path("/extrato")
    @Produces(MediaType.APPLICATION_JSON)
    public Response extrato(
            @QueryParam("saldo") double saldo,
            @HeaderParam("Authorization") String token) {
        if (token == null || token.isBlank()) {
            return Response.status(401)
                    .entity(new Erro("Autenticação obrigatória"))
                    .build();
        }
        double taxaManutencao = saldo * 0.02;
        return Response.ok(new Resultado(saldo - taxaManutencao)).build();
    }
}
```

Os registros auxiliares usados nas respostas:

```java
public record Resultado(double resultado) { }

public record Erro(String mensagem) { }
```

> 💡 Não se preocupe em copiar o projeto inteiro. O importante aqui é só ter esses endpoints no ar (em `http://localhost:8080`) para você treinar os testes com Rest Assured.

### Exercício 1: Status code e query params

*Revisa os exemplos 1 e 6.* Escreva um teste que chame `GET /conta/deposito?saldo=100&valor=50` e valide:

* O status `200`.
* O campo `resultado` é igual a `150.0`.

### Exercício 2: Validando erro de negócio

*Revisa o exemplo 8.* Escreva um teste que chame `GET /conta/saque?saldo=100&valor=150` e valide:

* O status `400`.
* O campo `mensagem` é igual a `"Saldo insuficiente"`.

Em seguida, escreva um segundo teste para `GET /conta/saque?saldo=100&valor=40` e valide que o status é `200` e o campo `resultado` é igual a `60.0`.

### Exercício 3: Enviando JSON no corpo (POST)

*Revisa o exemplo 9.* Envie uma requisição `POST /conta/fatura` com o corpo:

```json
[20.0, 35.5, 10.0]
```

Valide que:

* O status é `200`.
* O campo `resultado` é maior que `60` (use `greaterThan(60.0)`).

Depois, envie uma lista vazia (`[]`) e valide que o status retornado é `400`.

### Exercício 4: Parâmetro na URL (path param)

*Revisa o exemplo 2.* Escreva um teste para `GET /conta/rendimento/5` e valide:

* O status `200`.
* O campo `resultado` é igual a `1050.0` (juros simples de 1% ao mês sobre `1000`, por 5 meses).

### Exercício 5: Autenticação obrigatória

*Revisa os exemplos 3 e 5.* Crie um teste para `GET /conta/extrato?saldo=500` que:

* Envie o cabeçalho `Authorization: Bearer abc.123.xyz`.
* Espere o status `200` e o campo `resultado` igual a `490.0` (saldo com a taxa de manutenção de 2% já descontada).

Em seguida, crie um segundo teste, **sem** o cabeçalho, e verifique que o status retornado é `401`.

### Exercício 6: Desafio 🚀

Combine dois endpoints em um único fluxo de teste:

1. Chame `GET /conta/deposito?saldo=100&valor=400` e **capture o campo `resultado`** da resposta (deve ser `500.0`).
2. Use esse valor como `saldo` em uma nova chamada a `GET /conta/extrato`, enviando o cabeçalho de autenticação.
3. Valide que o resultado final é `490.0`.

> 💡 **Dica:** use `.extract().path("resultado")` para capturar o valor retornado pelo primeiro endpoint.

---

## Questões teóricas 📚

Antes de seguir para a próxima seção, confira seu entendimento sobre os conceitos apresentados. As questões abaixo não exigem código, apenas o domínio da teoria.

<center>
<iframe src="https://vvs.rpmhub.dev/componente/questions.html"
    title="Questões teóricas: Rest Assured" width="90%" height="500" style="border:none;"></iframe>
</center>

---

## Referências

* [Documentação oficial do Rest Assured](https://rest-assured.io/)
* [Hamcrest Matchers](http://hamcrest.org/JavaHamcrest/)
* [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
