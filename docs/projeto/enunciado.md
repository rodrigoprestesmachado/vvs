---
layout: default
title: Enunciado do Projeto
parent: Estudo por Projeto
nav_order: 1
---

# Enunciado do Projeto 📝

## Objetivo

O objetivo deste trabalho é aplicar, em um projeto de software real,
os conceitos e técnicas de Verificação e Validação estudados ao longo da
disciplina: planejamento de testes, análise estática, teste unitário, teste
de componente e teste de sistema.

## Formação de grupos

O trabalho pode ser desenvolvido individualmente ou em grupos, conforme
definido pelo professor no início do semestre.

## Escolha do projeto

Cada grupo deve escolher **um projeto de software qualquer** para servir de
base para as atividades de verificação e validação. O projeto pode ser:

* Um sistema desenvolvido pelo próprio grupo (em qualquer linguagem, mas
  recomenda-se Java, já que é a linguagem utilizada nos exemplos da
  disciplina);
* Um projeto de código aberto já existente, desde que o grupo tenha acesso
  ao código-fonte e possa adicionar testes;
* Um projeto de outra disciplina do curso, desde que autorizado pelo
  professor.

O importante é que o projeto escolhido tenha **funcionalidades suficientes**
para permitir a criação de um plano de teste, testes unitários, testes de
componente e testes de sistema com sentido.
{: .fs-3 }

## Entregáveis

O trabalho deve ser entregue em um repositório Git (por exemplo, no GitHub),
contendo, no mínimo, os seguintes artefatos:

### 1. Plano de Teste

Um arquivo em formato **Markdown** (`plano-de-teste.md`), na raiz do
repositório ou em uma pasta `docs/`, contendo pelo menos:

* Escopo do teste (o que será e o que não será testado);
* Estratégia de teste (unitário, componente, sistema, estático, e demais
  técnicas empregadas);
* Critérios de entrada e saída (quando os testes começam e quando são
  considerados concluídos);
* Riscos e mitigação;
* Cronograma ou divisão de tarefas entre os membros do grupo (se aplicável);
* Ambiente e ferramentas utilizadas (linguagem, framework de teste, ferramenta
  de análise estática, etc.).

### 2. Análise Estática

Configuração e execução de, pelo menos, **uma ferramenta de análise
estática** (por exemplo, [PMD](../pmd/pmd.md), [Checkstyle](../estatica/estatica.md)
ou [SonarQube](../sonar/sonar.md)), integrada ao processo de build do
projeto (Maven, Gradle, npm, etc.). O relatório gerado pela ferramenta deve
ser disponibilizado no repositório ou seu resultado deve ser reproduzível
por quem for avaliar o trabalho.

### 3. Teste Unitário

Implementação de testes unitários que cubram as principais classes/funções
do domínio do sistema, utilizando um framework apropriado (por exemplo,
[JUnit 5](../unitario/junit.md) com [Mockito](../unitario/mock.md), no caso
de projetos Java). Recomenda-se o uso de uma ferramenta de cobertura de
código, como o [JaCoCo](../jacoco/jacoco.md), para demonstrar a cobertura
alcançada.

### 4. Teste de Componente

Implementação de testes que validem componentes do sistema de forma isolada,
mas em um nível de granularidade maior que o teste unitário — por exemplo,
testes de API REST com [RestAssured](../componente/restassured.md), testes de
repositório/banco de dados, ou testes de módulos que integrem múltiplas
classes.

### 5. Teste de Sistema

Escrita de testes de sistema que validem o comportamento do software como um
todo, do ponto de vista do usuário final, utilizando ferramentas como
[Selenium](../selenium/selenium.md), [Cypress](../cypress/cypress.md) ou
[Playwright](../playwirght/playwright.md), conforme a natureza do projeto
(web, API, desktop, etc.).

## Pontuação extra

Grupos que desejarem pontuação extra podem, adicionalmente, implementar:

* **Testes de carga**, utilizando ferramentas como [K6](../carga/carga.md),
  avaliando o comportamento do sistema sob diferentes níveis de carga; e/ou
* **Testes de segurança**, avaliando vulnerabilidades básicas do sistema
  (por exemplo, injeção de dados, autenticação/autorização, exposição de
  dados sensíveis), com o uso de ferramentas de análise de segurança ou
  testes manuais documentados.

Os testes extras também devem estar documentados no plano de teste e possuir
evidências de execução (relatórios, capturas de tela, logs, etc.).
{: .fs-3 }

## Apresentação

Ao final do semestre, cada grupo deverá apresentar o projeto, o plano de
teste elaborado e os resultados obtidos com as atividades de verificação e
validação implementadas, conforme data definida pelo professor.

## Critérios de avaliação

A avaliação do trabalho seguirá a [Rubrica de Avaliação](rubrica.md)
disponibilizada nesta seção.

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
