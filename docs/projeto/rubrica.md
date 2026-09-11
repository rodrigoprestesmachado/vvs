---
layout: default
title: Rubrica de Avaliação
parent: Estudo por Projeto
nav_order: 2
---

# Rubrica de Avaliação 📊

O trabalho é individual e avaliado em duas etapas: a **Etapa 1** (definição
do projeto, em PDF) e a **Etapa 2** (entrega final, no repositório Git). A
avaliação considera **5 critérios**, totalizando **10,0 pontos**. Os dois
últimos pontos extras (teste de carga e teste de segurança) são
**opcionais** e somam pontuação adicional, conforme definido em aula.
{: .fs-3 }

## Critérios de avaliação

| Critério | Peso | Insuficiente (0) | Regular | Bom | Excelente |
|---|---|---|---|---|---|
| **1. Plano de Teste** (`.md`) | 1,5 | Não existe ou não segue o formato Markdown. | Existe, mas está incompleto (falta escopo, estratégia ou critérios). | Contempla escopo, estratégia e critérios, com pequenas lacunas. | Completo: escopo, estratégia, critérios de entrada e saída, riscos, ferramentas e cronograma bem definidos. |
| **2. Análise Estática** | 1,5 | Não implementada. | Ferramenta configurada, mas não executada ou sem relatório. | Ferramenta configurada e executada, com relatório gerado. | Integrada ao build, relatório disponível e principais apontamentos corrigidos ou justificados. |
| **3. Teste Unitário** | 3,0 | Não implementado. | Poucos testes, sem cobrir casos relevantes e sem uso de mocks onde seriam necessários. | Testes cobrem as principais classes e métodos, com uso correto de asserts e de mocks (Mockito) para isolar dependências externas. | Boa cobertura (mensurada, por exemplo com JaCoCo), incluindo casos de sucesso, falha e exceção, com uso criterioso de mocks para isolar unidades. |
| **4. Teste de Componente** | 2,5 | Não implementado. | Testes superficiais, sem isolar o componente adequadamente. | Testes validam o componente (API, repositório, módulo) de forma isolada. | Testes robustos, cobrindo múltiplos cenários (sucesso, erro, borda) e integrados ao build. |
| **5. Teste de Sistema** | 1,5 | Não implementado. | Testes automatizados muito básicos (por exemplo, um único fluxo simples). | Testes automatizados cobrindo os principais fluxos do usuário. | Testes automatizados cobrindo fluxos principais e alternativos, com relatório ou evidência de execução. |

**Total: 10,0 pontos**
{: .fs-3 }

Não há apresentação de projeto ao final do semestre. A avaliação é feita
exclusivamente com base nos artefatos entregues.
{: .fs-3 }

## Pontuação extra (opcional)

| Critério extra | Pontos extras | Descrição |
|---|---|---|
| **Teste de Carga** | + 0,5 | Implementação de testes de carga (por exemplo com [K6](../carga/carga.md)), com definição de cenários (usuários virtuais, duração) e relatório de resultados. |
| **Teste de Segurança** | + 0,5 | Avaliação de vulnerabilidades básicas do sistema (por exemplo injeção, autenticação ou autorização, exposição de dados), com evidências e documentação dos achados. |

Cada item extra soma até **0,5 ponto**, totalizando até **1,0 ponto** extra
na nota final do trabalho.
{: .fs-3 }

## Observações gerais

* Todos os artefatos (PDF da Etapa 1, plano de teste, relatórios de análise
  estática, testes e evidências) devem estar versionados ou entregues nos
  formatos e prazos indicados no enunciado.
* A nota de cada critério é proporcional ao peso indicado, considerando os
  níveis de desempenho descritos na tabela.
* Trabalhos entregues fora do prazo, sem justificativa prévia acordada com
  o professor, estão sujeitos a penalização conforme critério da disciplina.
{: .fs-3 }

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
