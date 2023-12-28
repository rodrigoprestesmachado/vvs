<!-- .slide:  data-background-opacity="0.1" data-background-image="https://media.itpro.co.uk/image/upload/v1570816546/itpro/2019/02/software_shutterstock_1290773869.jpg"
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

* Rest Assured é uma biblioteca Java para automação de testes de APIs REST.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Permite que você envie requisições HTTP e valide as respostas.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

*  Amplamente utilizado no mundo da automação de testes de API.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Por que Rest Assured?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Simples de usar e altamente configurável.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Pode ser facilmente integrado com estruturas de teste como o JUnit.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Possui recursos poderosos para validação de resposta.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Validação de Respostas
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Rest Assured permite que você valide respostas de várias maneiras, incluindo:
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Validação de código de status.
  <!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Validação de cabeçalhos.
  <!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Validação de corpo da resposta (JSON/XML).
  <!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Validação de campos específicos.
  <!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo básico de validação de resposta
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

```java
    @Test
    public void testHelloEndpoint() {
        // given prepara a requisição
        given()
          // when faz a requisição
          .when().get("/hello")
          // then valida a resposta
          .then()
            // Verifica se o código de status é 200
            .statusCode(200)
            // Verifica se o corpo da resposta é "hello"
            .body(is("hello"));
    }
```
<!-- .element: style="background-color: white; margin-bottom:50px; font-size: 20px; font-family: arial; color:black"  -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo básico de validação de resposta
<!-- .element: style="margin-bottom:30px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* **given()** é o ponto de partida para construir uma solicitação HTTP no Rest
Assured. É usado para definir pré-condições ou configurações para a solicitação.
Neste caso, não há pré-condições específicas definidas.
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* **.when().get("/hello")** é a parte onde você está realmente fazendo a
solicitação HTTP. Nesse caso, está sendo feita uma solicitação GET para
o endpoint "/hello". Isso simula uma chamada GET a uma API no endpoint
especificado.
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* **.then()** é usado para definir as validações que você deseja realizar na
resposta, ou seja, se a resposta atende a certos critérios.
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* **.statusCode(200)** é uma validação que verifica se o código de status HTTP
da resposta é igual a 200. Se o código de status não for 200, o teste falhará.
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

* **.body(is("hello"))** é outra validação que verifica se o corpo da resposta
contém a string "hello". Se o corpo da resposta não contiver exatamente "hello",
o teste falhará.
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo de validação de JSON
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

```java
    @Test
    public void validateJsonFields() {
        given()
            .when().get("https://api.example.com/user/123")
            .then()
                // Verifica se o código de status é 200
                .statusCode(200)
                // Verifica se o campo "id" é igual a 123
                .body("id", equalTo(123))
                // Verifica se o campo "name" é igual a "John Doe"
                .body("name", equalTo("John Doe"))
                // Verifica se o campo "email" contém "@example"
                .body("email", containsString("@example.com"));
    }
```
<!-- .element: style="background-color: white; margin-bottom:50px; font-size: 20px; font-family: arial; color:black"  -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo de validação de JSON
<!-- .element: style="margin-bottom:30px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* **.body("id", equalTo(123))** verifica se o campo "id" no corpo da resposta
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

  * **.body** é um método sobrecarregado que pode ser usado para verificar se um
  campo específico no corpo da resposta é igual a um valor específico. Nesse caso,
  estamos verificando se o campo "id" é igual a 123.
  <!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

  * **.equalTo(123)** é um método que verifica se o valor do campo é igual a 123.
  <!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#F5F5F5" -->

  * **.containsString("@example.com")** é um método que verifica se o valor do
  campo contém a string "@example.com".
  <!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo de modificação de cabeçalhos da requisição
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

```java
    @Test
    public void requestWithJWTHeader() {
        // Define a URL da API
        String apiUrl = "https://api.example.com/resource";

        // Define o JWT token
        String jwtToken = "your_jwt_token_here";

        // Define o cabeçalho da requisição
        Response response = given()
            // .header é um método sobrecarregado que pode ser usado para
            // definir um cabeçalho específico na requisição.
            .header("Authorization", "Bearer " + jwtToken)
            .when().get(apiUrl);

        //  Valida a resposta
        response.then().statusCode(200);
    }
```
<!-- .element: style="background-color: white; margin-bottom:50px; font-size: 18px; font-family: arial; color:black"  -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Exemplo de validação de Cookies
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

```java
   @Test
    public void validateCookies() {
        // Define a URL da API
        String apiUrl = "https://api.example.com/resource";

        // Realiza a requisição
        Response response = given()
            .when()
            .get(apiUrl);

        // Valida o código de status
        response.then().statusCode(200);

        // Valida a presença de um cookie específico
        response.then().cookie("session_cookie");

        // Validar o valor de um cookie específico
        response.then().cookie("user_id", equalTo("123"));
    }
```
<!-- .element: style="background-color: white; margin-bottom:50px; font-size: 18px; font-family: arial; color:black"  -->


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
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Quarkus: Testing your application. Disponível em: https://pt.quarkus.io/guides/
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">Creative Commons Atribuição 4.0 Internacional</a>
</center>
  <!-- .element: style="margin-top:150px; font-size: 15px; font-family: Bradley Hand" -->