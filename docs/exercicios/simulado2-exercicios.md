---
layout: default
title: Exercícios de Revisão
parent: Segunda Avaliação
grand_parent: Simulados
nav_order: 1
---

# Exercícios de Revisão 📝

Esta página reúne os links para os exercícios práticos e teóricos da disciplina que serão avaliados na segunda prova.
{: .fs-3 }

## Índice

* [REST Assured](#rest-assured)
* [JaCoCo](#jacoco)
* [Playwright](#playwright)
* [Teste de lançamento](#teste-de-lançamento)
{: .fs-3 }

## REST Assured

Teste de componente com a API da conta bancária. Os conceitos de interface do componente estão nos slides de teste de desenvolvimento.
{: .fs-3 }

### Exercícios práticos

* [Exercício 1. Status code e query params](https://vvs.rpmhub.dev/componente/restassured.html#exercício-1-status-code-e-query-params)
* [Exercício 2. Validando erro de negócio](https://vvs.rpmhub.dev/componente/restassured.html#exercício-2-validando-erro-de-negócio)
* [Exercício 3. Enviando JSON no corpo (POST)](https://vvs.rpmhub.dev/componente/restassured.html#exercício-3-enviando-json-no-corpo-post)
* [Exercício 4. Parâmetro na URL (path param)](https://vvs.rpmhub.dev/componente/restassured.html#exercício-4-parâmetro-na-url-path-param)
* [Exercício 5. Autenticação obrigatória](https://vvs.rpmhub.dev/componente/restassured.html#exercício-5-autenticação-obrigatória)
* [Exercício 6. Desafio](https://vvs.rpmhub.dev/componente/restassured.html#exercício-6-desafio-)
{: .fs-3 }

### Exercícios teóricos

* [Questões de REST Assured](https://vvs.rpmhub.dev/componente/restassured.html#questões-teóricas-)
* [Slides de teste de desenvolvimento (componente e sistema)](https://vvs.rpmhub.dev/desenvolvimento/teste.html)
{: .fs-3 }

## JaCoCo

Cobertura do cadastro de livros, no teste unitário de `Book.validate`.
{: .fs-3 }

### Exercícios práticos

* [Exercício 1. Localizar o ramo do autor](https://vvs.rpmhub.dev/jacoco/jacoco.html#exercício-1-localizar-o-ramo-do-autor)
* [Exercício 2. Cobrir o autor em branco](https://vvs.rpmhub.dev/jacoco/jacoco.html#exercício-2-cobrir-o-autor-em-branco)
* [Exercício 3. Cobrir `author == null`](https://vvs.rpmhub.dev/jacoco/jacoco.html#exercício-3-cobrir-author--null)
* [Exercício 4. Cobrir `publicationYear < 1`](https://vvs.rpmhub.dev/jacoco/jacoco.html#exercício-4-cobrir-publicationyear--1)
* [Exercício 5. Excluir `BookRequest` do relatório](https://vvs.rpmhub.dev/jacoco/jacoco.html#exercício-5-excluir-bookrequest-do-relatório)
* [Exercício 6. Uma frase sobre linha e ramo](https://vvs.rpmhub.dev/jacoco/jacoco.html#exercício-6-uma-frase-sobre-linha-e-ramo)
{: .fs-3 }

### Exercícios teóricos

* [Questões de JaCoCo](https://vvs.rpmhub.dev/jacoco/jacoco.html#teste-seus-conhecimentos-)
{: .fs-3 }

## Playwright

Teste de sistema no cadastro de livros e na lista de tarefas.
{: .fs-3 }

### Exercícios práticos

* [Exercício 1. O botão está lá](https://vvs.rpmhub.dev/playwirght/playwright.html#exercício-1-o-botão-está-lá)
* [Exercício 2. O formulário abre](https://vvs.rpmhub.dev/playwirght/playwright.html#exercício-2-o-formulário-abre)
* [Exercício 3. O fluxo feliz](https://vvs.rpmhub.dev/playwirght/playwright.html#exercício-3-o-fluxo-feliz)
* [Exercício 4. ISBN travado](https://vvs.rpmhub.dev/playwirght/playwright.html#exercício-4-isbn-travado)
* [Exercício 5. Recusar a exclusão](https://vvs.rpmhub.dev/playwirght/playwright.html#exercício-5-recusar-a-exclusão)
* [Exercício 6. Desafio](https://vvs.rpmhub.dev/playwirght/playwright.html#exercício-6-desafio)
* [Exercício 7. Gravar o filme](https://vvs.rpmhub.dev/playwirght/playwright.html#exercício-7-gravar-o-filme)
* [Exercício 8. Três navegadores ao mesmo tempo](https://vvs.rpmhub.dev/playwirght/playwright.html#exercício-8-três-navegadores-ao-mesmo-tempo)
* [Exercício 9. A página abriu](https://vvs.rpmhub.dev/playwirght/playwright.html#exercício-9-a-página-abriu)
* [Exercício 10. Concluída, filtro e limpeza](https://vvs.rpmhub.dev/playwirght/playwright.html#exercício-10-concluída-filtro-e-limpeza)
{: .fs-3 }

### Exercícios teóricos

* [Questões de Playwright](https://vvs.rpmhub.dev/playwirght/playwright.html#questões-teóricas)
{: .fs-3 }

## Teste de lançamento

A página do tema explica lançamento, teste baseado em requisitos, cenário, desempenho, estresse e teste de usuário. Ela não traz exercícios práticos nem questões teóricas. O questionário dos slides em `usuario/slides/questions.html` trata de verificação e validação, conteúdo da primeira prova. As questões abaixo são **novas**.
{: .fs-3 }

### Exercícios práticos novos

1. **Novo.** O sistema foi projetado para 300 transações por segundo. Descreva um teste de estresse: o que se observa até esse limite e o que se observa acima dele. Inclua as duas finalidades do teste de estresse.
{: .fs-3 }

2. **Novo.** Escreva um cenário realista de uso de um sistema que você conhece (uma história curta). Diga qual requisito esse cenário ajuda a conferir.
{: .fs-3 }

### Exercícios teóricos novos

1. **Novo.** O teste de lançamento é feito por quem, e com que objetivo?
* A) Pelo próprio autor do código, para elevar a cobertura de linhas.
* B) Fora do time de desenvolvimento, em geral com clientes, usuários ou outros times, para conferir se o sistema cumpre os requisitos e está bom o bastante para ser usado.
* C) Só pela ferramenta de cobertura, na fase `test`.
* D) Só por um script de carga, sem olhar requisitos.
{: .fs-3 }

2. **Novo.** O que caracteriza o teste baseado em requisitos?
* A) Cada requisito deriva um conjunto de testes.
* B) Um único teste cobre todos os requisitos do sistema.
* C) Os testes nascem só do código, sem olhar a especificação.
* D) O requisito é substituído pelo perfil operacional.
{: .fs-3 }

3. **Novo.** O que torna um cenário adequado ao teste de lançamento?
* A) Ser uma história realista de um jeito de usar o sistema, com a qual os verdadeiros usuários conseguem se relacionar.
* B) Ser um diagrama de classes do componente.
* C) Ser um mock que devolve sempre o mesmo valor.
* D) Ser a lista de linhas vermelhas do JaCoCo.
{: .fs-3 }

4. **Novo.** Qual alternativa diferencia desempenho e estresse?
* A) O desempenho aumenta a carga até ficar inaceitável e pode seguir um perfil operacional, como 50%, 25% e 25%. O estresse passa do limite do projeto.
* B) Os dois param no primeiro teste que fica verde.
* C) O estresse mede ramos de um `if`. O desempenho mede cookies.
* D) O perfil operacional é uma forma de autenticação básica.
{: .fs-3 }

5. **Novo.** O teste de estresse ajuda a ver o quê?
* A) O comportamento de falha, que não deve romper os dados, e defeitos que só aparecem com o sistema plenamente carregado.
* B) A ordem dos seletores `getByRole` e `locator`.
* C) Se o `BrowserContext` guardou o cookie do teste anterior.
* D) Se o goal `report` excluiu um DTO.
{: .fs-3 }

6. **Novo.** Por que o teste de usuário importa, e como se chamam os três tipos?
* A) O ambiente do usuário afeta confiabilidade, desempenho, usabilidade e robustez. Os tipos são alfa (grupo selecionado, perto do time), beta (grupo maior) e aceitação (clientes e desenvolvedores decidem se o sistema entra em produção).
* B) O teste de usuário só mede bytecode. Os tipos são linha, ramo e método.
* C) Alfa, beta e aceitação são três nomes para o mesmo teste automatizado de API.
* D) O teste de usuário dispensa o cliente e ocorre antes de existir requisito.
{: .fs-3 }

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
