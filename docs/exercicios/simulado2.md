---
layout: default
title: Segunda Avaliação
parent: Simulados
nav_order: 22
---

# Segunda Avaliação 📝

Questões da segunda prova: teste de componente com REST Assured, cobertura com JaCoCo, teste de sistema com Playwright e teste de lançamento.
{: .fs-3 }

## Questões práticas

### REST Assured

1. Escreva um teste com REST Assured e JUnit para `GET /conta/deposito`. Use `queryParam` para `saldo=100` e `valor=50`. Mantenha `given()`, `when()` e `then()`. Valide o status `200` e o campo `resultado` igual a `150.0` com `equalTo`.
{: .fs-3 }

2. Escreva dois testes para `POST /conta/fatura`.
   * No primeiro, envie `contentType("application/json")` e o corpo `[20.0, 35.5, 10.0]`. Valide o status `200` e que o campo `resultado` é maior que `60`, com `greaterThan`.
   * No segundo, envie uma lista vazia (`[]`) e valide o status `400`.
{: .fs-3 }

### JaCoCo

3. Em `Book.validate` existe o ramo abaixo. Escreva o teste JUnit `shouldRejectNullAuthor`, com `assertThrows`, que executa o lado `author == null`. Em uma frase, diga por que o losango dessa linha continua âmbar quando o único teste usa o autor em branco (`""`).

```java
if (author == null || author.isBlank()) {
    throw new InvalidBookException("Author cannot be blank");
}
```
{: .fs-3 }

4. `BookRequest` aparece no relatório sem cobertura: nenhum teste unitário carrega essa classe, e ela é a forma do JSON, não a regra do livro. Escreva a configuração do goal `report` do `jacoco-maven-plugin` que exclui `**/BookRequest.class`. Diga o que permanece no relatório depois de rodar o teste outra vez.
{: .fs-3 }

### Playwright

5. Escreva um teste JUnit anotado com `@QuarkusTest` e `@UsePlaywright`. O método recebe `Page page`, abre `http://localhost:8080/` e confere, com `getByRole` e `assertThat`, que o botão **Novo livro** está visível.
{: .fs-3 }

6. O livro de ISBN `0134685991` já existe. Escreva o trecho que recusa a exclusão: registre `page.onDialog` com `dismiss` antes do clique em **Excluir** e confira que a linha desse ISBN permanece visível.
{: .fs-3 }

### Teste de lançamento

7. O requisito é: "ao salvar um livro válido, o sistema confirma a criação". No teste baseado em requisitos, cada requisito deriva um conjunto de testes. Escreva três verificações que esse requisito deve gerar.
{: .fs-3 }

8. Escreva um cenário curto e realista: uma bibliotecária cadastra um livro, percebe o ano errado e corrige. O cenário deve ser uma história com a qual um usuário verdadeiro se relacione. Em uma frase, diga por que o teste de lançamento usa cenários.
{: .fs-3 }

## Questões teóricas

### REST Assured e teste de componente

1. Qual é a principal diferença entre teste unitário e teste de componente?
* A) O teste unitário verifica o sistema inteiro; o teste de componente verifica só um método privado.
* B) O teste unitário exercita métodos e classes. O teste de componente reúne vários objetos e se concentra em mostrar que a interface do componente se comporta como a especificação.
* C) Os dois testam a mesma coisa; o teste de componente apenas usa o navegador.
* D) O teste de componente mede cobertura de ramos, e o teste unitário mede só linhas.
{: .fs-3 }

2. Quais são os quatro tipos de interface considerados no teste de componente?
* A) Parâmetro, memória compartilhada, procedimento e passagem de mensagens.
* B) GET, POST, PUT e DELETE.
* C) Linha, ramo, método e classe.
* D) Alfa, beta, aceitação e estresse.
{: .fs-3 }

3. Qual alternativa descreve um erro de temporização na interface de um componente?
* A) O chamador envia parâmetros com tipo, ordem ou valor errados.
* B) O chamador faz suposições incorretas sobre o comportamento da interface.
* C) Um componente, por ter velocidade diferente, consome dados obsoletos.
* D) O relatório de cobertura pinta uma linha de verde.
{: .fs-3 }

4. Na estrutura do REST Assured, o que cada etapa faz?
* A) `given()` dispara a chamada HTTP, `when()` valida o status e `then()` monta o corpo.
* B) `given()` prepara o contexto (cabeçalhos, parâmetros e corpo), `when()` dispara a chamada (`get`, `post` e os demais verbos) e `then()` valida o resultado.
* C) `given()` abre o navegador, `when()` clica no botão e `then()` grava o vídeo.
* D) `given()` mede a cobertura, `when()` gera o HTML e `then()` aplica o limite mínimo.
{: .fs-3 }

5. O e-mail retornado precisa conter o trecho `@example.com`, sem ser idêntico à string inteira. Qual matcher se usa?
* A) `equalTo("@example.com")`.
* B) `containsString("@example.com")`.
* C) `greaterThan("@example.com")`.
* D) `startsWith(200)`.
{: .fs-3 }

6. Como a aula envia um token JWT, e o que `.auth().basic(usuario, senha)` faz?
* A) O JWT vai em `.header("Authorization", "Bearer " + token)`. A autenticação básica envia usuário e senha em Base64 no cabeçalho, e não é segura sem HTTPS.
* B) O JWT vai em `.queryParam("token", jwt)`. A autenticação básica gera um JWT novo a cada chamada.
* C) O JWT vai em `.cookie("session_cookie")`, que também confere o valor. A autenticação básica criptografa o corpo com AES.
* D) O REST Assured dispensa cabeçalho: ele simula o servidor e não envia a requisição.
{: .fs-3 }

### Playwright e teste de sistema

7. Qual alternativa descreve o teste de sistema durante o desenvolvimento?
* A) Isola uma classe e substitui as dependências por objetos simulados.
* B) Integra os componentes, confere se são compatíveis, se interagem corretamente e se transferem os dados certos no momento certo. A integração é incremental: a cada componente integrado, o sistema é testado de novo.
* C) É feito fora do time de desenvolvimento, com um grupo maior de usuários.
* D) Conta instruções de bytecode para decidir se o `assert` passou.
{: .fs-3 }

8. Por que o caso de uso é uma base eficaz para o teste de sistema?
* A) Porque lista apenas regras internas de um método, sem interação.
* B) Porque o teste de sistema olha para as interações. Alguns comportamentos só ficam visíveis quando os componentes estão unidos. O caso de uso descreve esse uso, e um diagrama de sequência, quando existe, mostra os objetos e componentes envolvidos.
* C) Porque garante que toda sequência possível de execução foi testada.
* D) Porque substitui a especificação e dispensa observar o resultado na tela.
{: .fs-3 }

9. Na analogia da aula, como REST Assured e Playwright se repartem o trabalho?
* A) Os dois conferem só o JSON, sem abrir o navegador.
* B) O REST Assured confere a API. O Playwright dirige o navegador de verdade e confere o que a pessoa vê: botão, campo e mensagem.
* C) O Playwright mede ramos do JaCoCo. O REST Assured grava o vídeo da falha.
* D) O Playwright substitui o teste de componente, e o REST Assured só serve para SOAP.
{: .fs-3 }

10. O que a espera automática do Playwright muda em relação a `Thread.sleep`?
* A) O `click()` espera o elemento ficar visível e habilitado, e o `assertThat` espera o texto aparecer. O teste observa a tela em vez de adivinhar um tempo.
* B) O teste precisa de um `Thread.sleep` antes de cada clique para o Codegen funcionar.
* C) A espera automática desliga o navegador e passa a ler só o corpo HTTP.
* D) `Thread.sleep` é obrigatório quando o teste roda em Firefox.
{: .fs-3 }

11. Qual seletor a aula manda tentar primeiro?
* A) `page.locator("#isbn")`, porque o CSS acompanha a cor do layout.
* B) `page.getByRole(...)`, porque fala a língua da tela. CSS fica como último recurso.
* C) `page.getByTestId` em todo campo, mesmo quando o botão tem papel e nome.
* D) O seletor gravado pelo Codegen deve ser trocado por classe CSS antes de ir para o JUnit.
{: .fs-3 }

12. Sobre `@QuarkusTest`, `@UsePlaywright` e o banco dos testes paralelos, qual alternativa está de acordo com a aula?
* A) `@QuarkusTest` sobe o Quarkus e o MySQL. `@UsePlaywright` entrega um `Page` novo, vindo de um `BrowserContext` limpo. A gaveta da sessão é limpa, mas o banco é compartilhado: navegadores em paralelo usam ISBNs diferentes para não colidir na chave.
* B) `@UsePlaywright` sobe o MySQL e `@QuarkusTest` injeta o `Page`. O mesmo ISBN nos três navegadores evita conflito.
* C) Um único `Playwright` estático pode ser compartilhado entre threads no teste `@ParameterizedTest` concorrente.
* D) O arquivo `.webm` aparece no disco no momento do `page.navigate`, antes de o contexto fechar.
{: .fs-3 }

### JaCoCo

13. O que o relatório do JaCoCo mostra, e o que ele não mostra?
* A) Mostra que o resultado de cada teste está certo. O `assert` é opcional quando a cobertura chega a 100%.
* B) Mostra o que foi executado. Quem confere se o resultado está certo é o `assert`. Acender todas as luzes não prova que a casa está em ordem.
* C) Mostra o tempo de resposta dos endpoints e a carga máxima do teste de estresse.
* D) Mostra quantos defeitos ainda existem, um por linha vermelha.
{: .fs-3 }

14. No relatório HTML, o que significa uma linha amarela com um losango?
* A) Nenhum teste passou por ali.
* B) A linha foi executada e todos os caminhos dela também.
* C) A linha rodou, mas algum ramo ficou de fora. O losango é a porta ainda fechada.
* D) O teste que passa por essa linha falhou na asserção.
{: .fs-3 }

15. Por que a cobertura de linhas de um `if` com `||` pode subir enquanto a de ramos fica pela metade?
* A) Porque a linha do `if` já conta como executada quando a condição é avaliada, mesmo que só uma porta tenha sido tomada. O ramo só fica verde quando as duas portas abrem.
* B) Porque linha e ramo são o mesmo contador com dois nomes.
* C) Porque ramos só existem em teste de sistema.
* D) Porque o JaCoCo ignora operadores `||`.
{: .fs-3 }

16. Por que, no cadastro de livros, a extensão `quarkus-jacoco` e o `jacoco-maven-plugin` precisam do `exclClassLoaders`?
* A) Porque o plugin Maven deixou de existir e a extensão só funciona no modo nativo.
* B) Porque o `@QuarkusTest` carrega as classes pelo `QuarkusClassLoader`. O agente do `prepare-agent` não vê essa porta. Sem `exclClassLoaders`, os dois instrumentam a mesma classe e o teste quebra.
* C) Porque a extensão cobre qualquer teste JUnit, com ou sem Quarkus, e o plugin só gera o HTML do modo nativo.
* D) Porque o `report` do plugin apaga o `assert` dos testes unitários.
{: .fs-3 }

17. Onde fica o HTML quando se roda só `./mvnw test -Dtest=BooksServiceTest`, com o goal `report` do plugin na fase `test`?
* A) `target/site/jacoco/index.html`.
* B) `src/main/resources/index.html`.
* C) `target/jacoco-report/index.html`, que é o relatório da extensão quando há `@QuarkusTest`.
* D) `target/surefire-reports/TEST-BooksE2ETest.xml`.
{: .fs-3 }

### Teste de lançamento

18. Qual é a característica central do teste de lançamento?
* A) É um teste interno do time de desenvolvimento, feito para subir a cobertura de código.
* B) É um processo feito fora do time de desenvolvimento, em geral com clientes, usuários ou outros times, para conferir se o sistema cumpre os requisitos e é bom o bastante para ser usado.
* C) É a execução do JaCoCo na fase `test` do Maven.
* D) É só o teste de estresse da infraestrutura.
{: .fs-3 }

19. Qual alternativa descreve os três tipos de teste com usuário?
* A) No alfa, um grupo selecionado trabalha perto do time. No beta, uma versão vai para um grupo maior. Na aceitação, clientes e desenvolvedores decidem se o sistema entra em produção.
* B) O alfa é com o público geral, o beta fica só dentro do time e a aceitação dispensa o cliente.
* C) O alfa é automatizado no navegador, o beta é teste de carga e a aceitação é do gerente de projeto.
* D) Os três são o mesmo teste e não envolvem o time de desenvolvimento.
{: .fs-3 }

20. O que é o teste baseado em requisitos?
* A) Uma abordagem sistemática em que cada requisito deriva um conjunto de testes.
* B) Um teste que aumenta a carga até o desempenho ficar inaceitável.
* C) A contagem de ramos de um `if`.
* D) A revisão do código pelo próprio autor, sem executar o sistema.
{: .fs-3 }

21. Sobre desempenho e estresse no teste de lançamento, qual alternativa está de acordo com o material?
* A) O desempenho aumenta a carga até ficar inaceitável e pode usar um perfil operacional, por exemplo 50% de um tipo de requisição, 25% de outro e 25% de um terceiro. O estresse testa fora dos limites do projeto, como passar de 300 transações por segundo, para ver o comportamento de falha sem romper os dados e para achar defeitos que só aparecem com o sistema carregado.
* B) Desempenho e estresse são sinônimos: os dois param exatamente no limite projetado.
* C) O estresse só confere a cor do relatório de cobertura.
* D) O perfil operacional substitui o requisito e dispensa observar a falha.
{: .fs-3 }

22. Por que o teste de usuário é essencial, e o que é um cenário?
* A) É essencial porque o ambiente do usuário afeta confiabilidade, desempenho, usabilidade e robustez. Um cenário é uma história realista de um jeito de usar o sistema, com a qual os verdadeiros usuários conseguem se relacionar.
* B) É essencial para gerar o arquivo `.exec` do JaCoCo. Um cenário é um mock de banco de dados.
* C) É essencial só quando a cobertura de linhas está abaixo de 80%. Um cenário é a lista de cookies da resposta HTTP.
* D) O teste de usuário substitui o teste de sistema feito pelo time durante o desenvolvimento e não olha para usabilidade.
{: .fs-3 }

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
