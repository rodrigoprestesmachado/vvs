---
layout: default
title: Enunciado do Projeto
parent: Estudo por Projeto
nav_order: 1
---

# Enunciado do Projeto 📝

## Objetivo

O objetivo deste trabalho é aplicar, em um projeto de software real, os
conceitos e técnicas de Verificação e Validação estudados ao longo da
disciplina: planejamento de testes, análise estática, teste unitário, teste
de componente e teste de sistema.
{: .fs-3 }

## Caráter do trabalho

O trabalho é **individual**. Cada estudante deve escolher, desenvolver e
testar seu próprio projeto, sendo o único responsável pelos artefatos
entregues.
{: .fs-3 }

## Escolha do projeto

Cada estudante deve escolher **um projeto de software qualquer** para
servir de base para as atividades de verificação e validação. O projeto
pode ser:
{: .fs-3 }

* Um sistema desenvolvido pelo próprio estudante (em qualquer linguagem, mas
  recomenda-se Java, já que é a linguagem utilizada nos exemplos da
  disciplina);
* Um projeto de código aberto já existente, desde que o estudante tenha
  acesso ao código-fonte e possa adicionar testes;
* Um projeto de outra disciplina do curso, desde que autorizado pelo
  professor.
{: .fs-3 }

O importante é que o projeto escolhido tenha **funcionalidades suficientes**
para permitir a criação de um plano de teste, testes unitários, testes de
componente e testes de sistema com sentido.
{: .fs-3 }

## Etapa 1: Definição do projeto (PDF)

Antes de iniciar a implementação dos testes, cada estudante deve entregar um
arquivo em **PDF**, indicando:
{: .fs-3 }

* Qual será o **sistema** escolhido (nome e uma breve descrição do que ele
  faz);
* Qual **linguagem de programação** será utilizada;
* Qual **framework** (ou frameworks) o sistema foi ou será construído.
{: .fs-3 }

Essa entrega deve ser feita conforme o prazo definido pelo professor e serve
como validação prévia de que o projeto escolhido é adequado para as
atividades da disciplina.
{: .fs-3 }

## Etapa 2: Entregáveis finais

O trabalho deve ser entregue em um repositório Git (por exemplo, no GitHub),
contendo, no mínimo, os seguintes artefatos:
{: .fs-3 }

### 1. Plano de Teste

Um arquivo em formato **Markdown** (`plano-de-teste.md`), na raiz do
repositório ou em uma pasta `docs/`, contendo pelo menos:
{: .fs-3 }

* Escopo do teste (o que será e o que não será testado);
* Estratégia de teste (unitário, componente, sistema, estático, e demais
  técnicas empregadas);
* Critérios de entrada e saída (quando os testes começam e quando são
  considerados concluídos);
* Riscos e mitigação;
* Cronograma das atividades;
* Ambiente e ferramentas utilizadas (linguagem, framework de teste, ferramenta
  de análise estática, etc.).
{: .fs-3 }

### 2. Análise Estática

Configuração e execução de, pelo menos, **uma ferramenta de análise
estática** (por exemplo, [PMD](../pmd/pmd.md), [Checkstyle](../estatica/estatica.md)
ou [SonarQube](../sonar/sonar.md)), integrada ao processo de build do
projeto (Maven, Gradle, npm, etc.). O relatório gerado pela ferramenta deve
ser disponibilizado no repositório ou seu resultado deve ser reproduzível
por quem for avaliar o trabalho.
{: .fs-3 }

### 3. Teste Unitário

Implementação de testes unitários que cubram as principais classes ou
funções do domínio do sistema, utilizando um framework apropriado (por
exemplo, [JUnit 5](../unitario/junit.md) com [Mockito](../unitario/mock.md),
no caso de projetos Java). Recomenda-se o uso de uma ferramenta de
cobertura de código, como o [JaCoCo](../jacoco/jacoco.md), para demonstrar a
cobertura alcançada.
{: .fs-3 }

### 4. Teste de Componente

Implementação de testes que validem componentes do sistema de forma
isolada, mas em um nível de granularidade maior que o teste unitário, por
exemplo testes de API REST com [RestAssured](../componente/restassured.md),
testes de repositório ou banco de dados, ou testes de módulos que integrem
múltiplas classes.
{: .fs-3 }

### 5. Teste de Sistema

Escrita de testes de sistema que validem o comportamento do software como
um todo, do ponto de vista do usuário final, utilizando ferramentas como
[Selenium](../selenium/selenium.md), [Cypress](../cypress/cypress.md) ou
[Playwright](../playwirght/playwright.md), conforme a natureza do projeto
(web, API, desktop, etc.).
{: .fs-3 }

## Pontuação extra

Estudantes que desejarem pontuação extra podem, adicionalmente, implementar:
{: .fs-3 }

* **Testes de carga**, utilizando ferramentas como [K6](../carga/carga.md),
  avaliando o comportamento do sistema sob diferentes níveis de carga; e/ou
* **Testes de segurança**, avaliando vulnerabilidades básicas do sistema
  (por exemplo, injeção de dados, autenticação ou autorização, exposição de
  dados sensíveis), com o uso de ferramentas de análise de segurança ou
  testes manuais documentados.
{: .fs-3 }

Os testes extras também devem estar documentados no plano de teste e
possuir evidências de execução (relatórios, capturas de tela, logs, etc.).
{: .fs-3 }

## Critérios de avaliação

Não haverá apresentação do projeto ao final do semestre. A avaliação será
feita exclusivamente com base nos artefatos entregues (PDF da Etapa 1 e
repositório da Etapa 2), conforme a [Rubrica de Avaliação](rubrica.md)
disponibilizada nesta seção.
{: .fs-3 }

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
