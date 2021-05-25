---
layout: default
title: 404
nav_exclude: true
---
# Introdução

rodrigo.prestes@poa.ifrs.edu.br
<!-- .element: style="margin-bottom:150px;" -->

Tela cheia? pressione a tecla 'F'
<!-- .element: style="font-size: small;" -->

Versão em PDF? [aqui](?print-pdf)
<!-- .element: style="font-size: small;" -->



## Índice

* Verificação e validação

* Inspeções e testes

* Testes em sistemas

* Referências



## Verificação e validação


## Verificação e validação

* A aplicação de testes em um programa é a maneira mais comum de verificar se ele atende a especificação e, portanto, realiza o que o cliente deseja
<!-- .element: class="fragment" -->

* Denomina-se verificação e validação (VV) o processo de análise de um programa
<!-- .element: class="fragment" -->

* Atividades de verificação e validação ocorrem em todos os estágios do processo de desenvolvimento de um sistema
<!-- .element: class="fragment" -->

* Portanto, inicia nos requisitos e continua ao longo do projeto, codificação e teste do produto
<!-- .element: class="fragment" -->


## Verificação e validação

* A verificação e a validação pode ser entendida por meio de duas perguntas:
<!-- .element: class="fragment" -->

  * Verificação: Estamos construindo o produto corretamente?
  <!-- .element: class="fragment" -->

  * Validação: Estamos construindo o produto correto?
  <!-- .element: class="fragment" -->

* O foco da verificação está em analisar se o *software* se encontra de acordo com as especificações
<!-- .element: class="fragment" -->

* Por outro lado, o processo de validação tem o objetivo de assegurar que um sistema atende as expectativas do cliente
<!-- .element: class="fragment" -->


## Verificação e Validação

* O objetivo da VV é atingir um nível de confiabilidade entre o *software* e os seus usuários
<!-- .element: class="fragment" -->

* O nível de confiabilidade depende do propósito do sistema, das expectativas dos clientes e/ou do ambiente de mercado:
<!-- .element: class="fragment" -->

  * Propósito do sistema: o nível de confiabilidade depende de quão crítico é o sistema. Por exemplo, trata-se de um protótipo ou um *software* que trabalhe com operações financeiras?
  <!-- .element: class="fragment" -->

  * Clientes: nível de tolerância dos usuários
  <!-- .element: class="fragment" -->

  * Ambiente de Mercado: programas concorrentes, preço que os clientes estão dispostos a pagar e/ou cronograma exigido
  <!-- .element: class="fragment" -->



## Inspeções e testes


## Inspeções e testes

* No processo de VV existem duas abordagens complementares: inspeções e testes
<!-- .element: class="fragment" -->

* Inspeções de software ou revisões por pares: analisam e verificam representações de sistema como documentos de requisitos, diagramas de projeto e código fonte dos programas
<!-- .element: class="fragment" -->

* Utilizamos revisões (manuais ou automatizadas) durante todas as etapas do processo. Denomina-se este tipo de revisão como estática, ou seja, aquelas onde não se coloca um sistema em execução
<!-- .element: class="fragment" -->


## Inspeções e testes

* Teste de software: envolve executar um sistema por meio de dados fictícios e analisar o seu comportamento, ou seja, trata-se de uma abordagem dinâmica
<!-- .element: class="fragment" -->

* Podemos analisar um sistema de maneira dinâmica apenas quando um protótipo ou versão executável estiver disponível
<!-- .element: class="fragment" -->

* Nesse sentido, o desenvolvimento incremental se torna interessante uma vez que testes podem ser realizados em uma versão inicial do sistema e continuar ao longo de todo o desenvolvimento
<!-- .element: class="fragment" -->


## Inspeções e testes

* Técnicas estáticas incluem análise de código fonte automatizada e verificação formal
<!-- .element: class="fragment" -->

* Porém, técnicas estáticas podem somente verificar a correspondência entre o programa e sua especificação
<!-- .element: class="fragment" -->

* Não é possível utilizar técnicas estáticas para verificar propriedade como desempenho e confiabilidade
<!-- .element: class="fragment" -->


## Inspeções e testes

![](img/inspecaoteste.png)
<!-- .element: style="height: 250px; margin-top: 50px" -->

Visão geral sobre o processo de inspeções e testes. Fonte: SOMMERVILLE.
<!-- .element: style="font-size: small; color: white" -->



## Testes em sistemas


## Testes em sistemas

* Embora técnicas estáticas sejam muito utilizadas, as práticas dinâmicas serão sempre o método principal de verificação e validação
<!-- .element: class="fragment" -->

* Existem dois tipos distintos de teste que podem ser usados em estágios diferentes no processo de software:
<!-- .element: class="fragment" -->
  * Testes de validação: possuem a finalidade de demonstrar que o *software* faz o que o cliente deseja, portanto, atendendo aos requisitos
  <!-- .element: class="fragment" -->

  * Testes de defeitos: revelam problemas no sistema em vez de simular o seu uso operacional. O objetivo neste momento é encontrar problemas entre o programa e a sua especificação
  <!-- .element: class="fragment" -->


## Depuração

* A verificação e validação possuem finalidades distintas da depuração
<!-- .element: class="fragment" -->
  * Processos de teste são dedicados a estabelecer a existência de defeitos em um sistema
  <!-- .element: class="fragment" -->

  * Por outro lado, depuração é um processo que localiza e corrige defeitos
  <!-- .element: class="fragment" -->

* Note, portanto, que os processos de VV e de depuração normalmente são intercalados
<!-- .element: class="fragment" -->


## Depuração

* Não existe método simples de depuração de programas
<!-- .element: class="fragment" -->

* Muitas vezes o defeito está distante de onde a falha ocorreu
<!-- .element: class="fragment" -->

* Por vezes, é necessário criar um teste adicional que reproduza os dados no momento que o defeito aconteceu
<!-- .element: class="fragment" -->

* Por intermédio de um *debugger*, ocasionalmente deve-se executar linha por linha para se encontrar o problema
<!-- .element: class="fragment" -->


## Teste de regressão

* Depois que o defeito foi descoberto e corrigido é necessário reavaliar o sistema por meio de uma nova execução de testes
<!-- .element: class="fragment" -->
  * Se surgirem novos defeitos quando juntarmos (*merge*) uma alteração ao sistema, então podemos afirmar que o sistema regrediu
  <!-- .element: class="fragment" -->

  * Executar toda a bateria de testes no sistema a cada nova alteração pode ser uma tarefa dispendiosa
  <!-- .element: class="fragment" -->

  * portanto, existe uma necessidade de automatização para se poder conferir qualidade ao sistema
  <!-- .element: class="fragment" -->

  * Um bom plano de teste deve relacionar as funcionalidades de um programa e os casos de testes (matriz de rastreabilidade)
  <!-- .element: class="fragment" -->



## Referências

SOMMERVILLE, Ian. [Engenharia de software](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5030950&acesso=aHR0cHM6Ly9taWRkbGV3YXJlLWJ2LmFtNC5jb20uYnIvU1NPL2lmcnMvOTc4ODU0MzAyNDk3NA==&label=acesso%20restrito), 10ª ed. Editora Pearson 768, cap. 8 ISBN 9788543024974.
<!-- .element: style="margin-bottom:50px;" -->

SOMMERVILLE, Ian. [Slides](https://iansommerville.com/software-engineering-book/slides/) do capítulo 8 (em inglês).
<!-- .element: style="margin-bottom:50px;" -->

SOMMERVILLE, Ian. [Vídeos](https://iansommerville.com/software-engineering-book/videos/imp/) do capítulo 8 (em inglês).
<!-- .element: style="margin-bottom:50px;" -->