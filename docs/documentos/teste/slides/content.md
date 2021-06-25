<!-- .slide: data-background-image="https://i.ytimg.com/vi/NtOwzU5Rpp8/maxresdefault.jpg" 
data-transition="convex"
-->
# Teste de software
<!-- .element: style="margin-bottom:200px; font-size: 60px; color:white;" -->

Pressione 'F' para tela cheia
<!-- .element: style="margin-bottom:10px; font-size: 15px; color:white;" -->

[versão em pdf](?print-pdf)
<!-- .element: style="margin-bottom 25px; font-size: 15px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Introdução
<!-- .element: style="margin-bottom:50px; color:white; font-size: 45px;" -->

* O teste é desenvolvido para mostrar que um programa faz o que ele realmente deveria fazer
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Para realizar um teste, é necessário executar um programa com dados artificiais
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Depois de executar um teste, é necessário analisar os resultados para verificar se existem erros, anomalias, etc.
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Existem dois tipos de testes: validação e defeitos
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Introdução
<!-- .element: style="margin-bottom:50px; color:white; font-size: 45px;" -->

![imagem](img/teste.png) 
<!-- .element height="50%" width="50%" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Introdução
<!-- .element: style="margin-bottom:50px; color:white; font-size: 45px;" -->

* O desenvolvimento de testes incluem todas as fases do desenvolvimento de um software
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* O testador de um software é geralmente o programador que escreveu o código
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Em alguns processos de desenvolvimento, o testador e o programador trabalham em pares, onde cada programador possui um testador para auxiliar no processo de teste
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Entretanto, em sistemas críticos, são utilizados grupos separados de programadores e testadores
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Introdução
<!-- .element: style="margin-bottom:50px; color:white; font-size: 45px;" -->

* Durante o desenvolvimento os testes podem ser executados testes em três níveis de granularidade
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->
  
* **Teste unitários** – onde unidades de programas ou classes são testadas. Assim, este tipo de teste enfatiza objetos e seus métodos
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* **Teste de componente** – onde algumas unidades são integradas para criar componentes. Teste de componentes dão foco ao funcionamento de interfaces
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* **Teste de sistema** – quando todos os componentes de um sistema são integrados e o sistema é testado como um todo. O teste de sistema tem foco na interação entre componentes
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Teste Unitário
<!-- .element: style="margin-bottom:60px; color:white; font-size: 45px;" -->

* Teste de uma unidade é o processo de testar trechos de código como métodos ou classes
<!-- .element: style="margin-bottom:50px; font-size: 25px; color:white;" -->

* Assim, o teste chama estas rotinas com diversos parâmetros de entrada com o objetivo de tentar identificar falhas
<!-- .element: style="margin-bottom:50px; font-size: 25px; color:white;" -->

* Se por exemplo, você realizar um teste num objeto, é necessário criar testes para analisar todas os serviços do objeto
<!-- .element: style="margin-bottom:50px; font-size: 25px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Teste Unitário
<!-- .element: style="margin-bottom:50px; color:white; font-size: 45px;" -->

* Assim, em um objeto é importante testar:
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

  * Todas as operações associadas com o objeto
  <!-- .element: style="margin-bottom:30px; font-size: 23px; color:white;" -->
  
  * Atribuir e analisar todos os valores de atributos
  <!-- .element: style="margin-bottom:30px; font-size: 23px; color:white;" -->
  
  * Colocar o objeto em diversos estados com o objetivo de simular todos os eventos que façam com que o objeto troque de estado
  <!-- .element: style="margin-bottom:30px; font-size: 23px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Teste Unitário
<!-- .element: style="margin-bottom:30px; color:white; font-size: 45px;" -->

* Imagine uma classe que implementa uma estação meteorológica
<!-- .element: style="margin-bottom:20px; font-size: 25px; color:white;" -->

* É necessário definir casos de testes para todos os métodos da classe
<!-- .element: style="margin-bottom:20px; font-size: 25px; color:white;" -->

* Para testar o estado da classe é necessário definir um modelo de estados
<!-- .element: style="margin-bottom:20px; font-size: 25px; color:white;" -->

* Usando um modelo, você conseguirá identificar sequências de estados que deverão ser testadas
<!-- .element: style="margin-bottom:20px; font-size: 25px; color:white;" -->

* Exemplos de estados para uma estação:
<!-- .element: style="margin-bottom:20px; font-size: 25px; color:white;" -->
  * Shutdown → Running → Shutdown
  <!-- .element: style="margin-bottom:20px; font-size: 23px; color:white;" -->

  * Shutdown → Running → Collecting → Transmitting → Running→Shutdown
  <!-- .element: style="margin-bottom:20px; font-size: 23px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Teste Unitário
<!-- .element: style="margin-bottom:30px; color:white; font-size: 45px;" -->

* As vezes um objeto a ser testado possui algum tipo de dependência, como por exemplo, um banco de dados
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Neste caso, você pode escolher utilizar objetos Mock
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Mock, é um objeto com a mesma interface de um objeto real, porém, utilizado para simular funcionalidades
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Assim, um objeto Mock de um banco de dados deve possuir itens organizados no formato de um array
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Teste Unitário
<!-- .element: style="margin-bottom:30px; color:white; font-size: 45px;" -->

* Teste geralmente é algo caro e que gasta tempo de trabalho
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Assim, é importante criar casos de teste efetivos:
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->
  
  * Casos de teste devem mostrar que o trecho de código faz o que ele deveria fazer
  <!-- .element: style="margin-bottom:30px; font-size: 22px; color:white;" -->
  
  * Se existem defeitos, o teste deve revelar estes problemas
  <!-- .element: style="margin-bottom:30px; font-size: 22px; color:white;" -->
  
  * Assim, normalmente escrevemos casos de testes para analisar o funcionamento normal de um sistema e, baseado na experiência de programação, criar testes para revelar problemas (usando entradas anormais)
  <!-- .element: style="margin-bottom:30px; font-size: 22px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Teste Unitário
<!-- .element: style="margin-bottom:30px; color:white; font-size: 45px;" -->

* Existem duas estratégias para ajudar a escolher casos de teste:
<!-- .element: style="margin-bottom:50px; font-size: 25px; color:white;" -->

  * **Teste de partição** – onde identificamos grupos de inputs com características comuns e que devem serem processadas da mesma maneira. Devem ser criados casos de teste para cada tipo de entrada de dado
  <!-- .element: style="margin-bottom:40px; font-size: 22px; color:white;" -->

  * **Teste baseado em orientação** – onde se utiliza uma orientação ou guia (_guideline_). Esta orientação reflete uma experiência passada sobre os tipos de erros que os programadores geralmente cometem
  <!-- .element: style="margin-bottom:40px; font-size: 22px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Teste Unitário
<!-- .element: style="margin-bottom:30px; color:white; font-size: 45px;" -->

* A entrada e saída de um programa frequentemente caem num número de classes de características similares
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Exemplo de classes: número, números negativos, strings, etc.
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Os programas, geralmente se comportam da mesma maneira se a entrada de dado for da mesma classe
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Por exemplo, se um método espera dois números positivos, então, o código deve se comportar na mesma maneira para qualquer número positivo
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Teste Unitário
<!-- .element: style="margin-bottom:30px; color:white; font-size: 45px;" -->

![imagem](img/domain.png) 
<!-- .element height="50%" width="50%" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Teste Unitário
<!-- .element: style="margin-bottom:30px; color:white; font-size: 45px;" -->

* As classes também são chamadas de partições ou domínios
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Uma boa ideia é escolher testes que exercitem o limite de uma partição e também valores intermediários
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Por exemplo, se você identificar uma partição de números positivos, então, o valor zero pode ser um ponto no limite
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Teste Unitário
<!-- .element: style="margin-bottom:30px; color:white; font-size: 45px;" -->

![imagem](img/partition.png) 
<!-- .element height="50%" width="50%" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Teste Unitário
<!-- .element: style="margin-bottom:30px; color:white; font-size: 45px;" -->

* Você identificará as partições através da especificação do sistema, da documentação de usuário ou da sua experiência anterior
<!-- .element: style="margin-bottom:50px; font-size: 25px; color:white;" -->

* Quando você utiliza, por exemplo, a especificação para criar as partições, então dizemos que isto é um "_black-box testing_"
<!-- .element: style="margin-bottom:50px; font-size: 25px; color:white;" -->

* Entretanto, pode ser útil olhar o código para identificar partições (ex.: olhar as exceções), neste caso, dizemos que este é um "_white-box testing_"
<!-- .element: style="margin-bottom:50px; font-size: 25px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Teste Unitário
<!-- .element: style="margin-bottom:30px; color:white; font-size: 45px;" -->

* Você também pode utilizar guias para ajudar a escolher os casos de teste. Os guias possuem conhecimento de quais testes são realmente efetivos
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

* Por exemplo, se você estiver testando um método que contém uma lista (_array_)
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->
  
  * Teste sequências com um único valor
  <!-- .element: style="margin-bottom:40px; font-size: 23px; color:white;" -->
  
  * Use diferentes sequências de diferentes tamanhos em testes diferentes
  <!-- .element: style="margin-bottom:40px; font-size: 23px; color:white;" -->
  
  * Derive testes de modo que exercite os primeiros elementos, meio e fim da sequência. Esta abordagem revela problemas de limites na partição
  <!-- .element: style="margin-bottom:40px; font-size: 23px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Teste Unitário
<!-- .element: style="margin-bottom:30px; color:white; font-size: 45px;" -->

* Algumas ideias gerais para teste:
<!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

  * Escolha entradas que forcem o sistema a gerar todas as mensagens de erro possíveis
  <!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->

  * Verifique entradas que causem buffers overflow
  <!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->
  
  * Repita a mesma entrada ou série inúmeras vezes
  <!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->
  
  * Force saídas inválidas
  <!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->
  
  * Force resultados de computação muito grandes ou muito pequenos
  <!-- .element: style="margin-bottom:40px; font-size: 25px; color:white;" -->


<!-- .slide: data-background="#21301C" data-transition="zoom" -->
## Referências
<!-- .element: style="margin-bottom:30px; color:white; font-size: 45px;" -->

* SOMMERVILLE, Ian. [Engenharia de software](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5030950&acesso=aHR0cHM6Ly9taWRkbGV3YXJlLWJ2LmFtNC5jb20uYnIvU1NPL2lmcnMvOTc4ODU0MzAyNDk3NA==&label=acesso%20restrito), 10ª ed. Editora Pearson 768 ISBN 9788543024974.