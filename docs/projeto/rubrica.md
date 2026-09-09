---
layout: default
title: Rubrica de Avaliação
parent: Estudo por Projeto
nav_order: 2
---

# Rubrica de Avaliação 📊

A avaliação do projeto final considera **7 critérios**, totalizando **10,0
pontos**. Os dois últimos pontos extras (teste de carga e teste de
segurança) são **opcionais** e somam pontuação adicional, podendo elevar a
nota final acima de 10,0 a critério do professor, ou compensar eventuais
perdas em outros critérios, conforme definido em aula.

## Critérios de avaliação

| Critério | Peso | Insuficiente (0) | Regular (0,5) | Bom (0,75) | Excelente (1,0 / 1,5) |
|---|---|---|---|---|---|
| **1. Plano de Teste** (`.md`) | 1,5 | Não existe ou não segue o formato Markdown. | Existe, mas está incompleto (falta escopo, estratégia ou critérios). | Contempla escopo, estratégia e critérios, com pequenas lacunas. | Completo: escopo, estratégia, critérios de entrada/saída, riscos, ferramentas e cronograma bem definidos. |
| **2. Análise Estática** | 1,5 | Não implementada. | Ferramenta configurada, mas não executada ou sem relatório. | Ferramenta configurada e executada, com relatório gerado. | Integrada ao build, relatório disponível e principais apontamentos corrigidos/justificados. |
| **3. Teste Unitário** | 2,0 | Não implementado. | Poucos testes, sem cobrir casos relevantes. | Testes cobrem as principais classes/métodos, com uso correto de asserts e/ou mocks. | Boa cobertura (mensurada, ex. JaCoCo), incluindo casos de sucesso, falha e exceção. |
| **4. Teste de Componente** | 2,0 | Não implementado. | Testes superficiais, sem isolar o componente adequadamente. | Testes validam o componente (API, repositório, módulo) de forma isolada. | Testes robustos, cobrindo múltiplos cenários (sucesso, erro, borda) e integrados ao build. |
| **5. Teste de Sistema** | 2,0 | Não implementado. | Testes manuais ou automatizados muito básicos (ex. 1 fluxo simples). | Testes automatizados cobrindo os principais fluxos do usuário. | Testes automatizados cobrindo fluxos principais e alternativos, com relatório/evidência de execução. |
| **6. Documentação e Organização do Repositório** | 1,0 | Repositório desorganizado, sem README ou instruções de execução. | Repositório organizado, mas com documentação mínima. | Repositório organizado com README explicando como rodar os testes. | Repositório bem organizado, documentado e com instruções claras de execução de cada tipo de teste. |
| **7. Apresentação do Projeto** | não avaliado nos itens acima | Não se apresentou ou não soube explicar o trabalho. | Apresentação superficial, com dificuldade em responder perguntas. | Apresentação clara, demonstrando entendimento do projeto e dos testes. | Apresentação clara, domínio do conteúdo e capacidade de justificar as decisões de teste tomadas. |

**Total: 10,0 pontos**
{: .fs-3 }

## Pontuação extra (opcional)

| Critério extra | Pontos extras | Descrição |
|---|---|---|
| **Teste de Carga** | + 0,5 | Implementação de testes de carga (ex. [K6](../carga/carga.md)), com definição de cenários (usuários virtuais, duração) e relatório de resultados. |
| **Teste de Segurança** | + 0,5 | Avaliação de vulnerabilidades básicas do sistema (ex. injeção, autenticação/autorização, exposição de dados), com evidências e documentação dos achados. |

Cada item extra soma até **0,5 ponto**, totalizando até **1,0 ponto** extra
na nota final do trabalho.
{: .fs-3 }

## Observações gerais

* Todos os artefatos (plano de teste, relatórios de análise estática,
  testes e evidências) devem estar versionados no repositório do grupo.
* A nota de cada critério é proporcional ao peso indicado, considerando os
  níveis de desempenho descritos na tabela.
* Trabalhos entregues fora do prazo, sem justificativa prévia acordada com
  o professor, estão sujeitos a penalização conforme critério da disciplina.

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
