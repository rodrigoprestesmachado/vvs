<!-- .slide:  data-background-opacity="0.3" data-background-image="img/title.jpg"
data-transition="convex"  -->
# Teste de Desenvolvimento
<!-- .element: style="margin-bottom:100px; font-size: 50px; color:white; font-family: Marker Felt;" -->

Pressione 'F' para tela cheia
<!-- .element: style="font-size: small; color:white;" -->

[versão em pdf](?print-pdf)
<!-- .element: style="font-size: small;" -->



<!-- .slide: data-background="#C9E66A" data-transition="zoom" -->
## Introdução 🛫
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Introdução
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Testes são desenvolvidos para mostrar que um programa faz o que ele realmente deveria fazer
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Para realizar um teste, é necessário executar um programa com dados artificiais
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Depois de executar um teste, é necessário analisar os resultados para verificar se existem erros, anomalias, etc.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Existem dois tipos de testes: validação e defeitos
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Introdução
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

![imagem](img/teste.png)
<!-- .element height="50%" width="50%" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Introdução
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* O desenvolvimento de testes incluem todas as fases do desenvolvimento de um software
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* O testador de um software é geralmente o programador que escreveu o código
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Em alguns processos de desenvolvimento, o testador e o programador trabalham em pares, onde cada programador possui um testador para auxiliar no processo de teste
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Entretanto, em sistemas críticos, são utilizados grupos separados de programadores e testadores
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Introdução
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Durante o desenvolvimento, os testes podem ser executados testes em três níveis de granularidade, são eles:
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* **Teste unitários** – onde unidades de programas ou classes são testadas. Assim, este tipo de teste enfatiza objetos e seus métodos
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* **Teste de componente** – onde algumas unidades são integradas para criar componentes. Teste de componentes dão foco ao funcionamento de interfaces
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* **Teste de sistema** – quando todos os componentes de um sistema são integrados e o sistema é testado como um todo. O teste de sistema tem foco na interação entre componentes
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->



<!-- .slide: data-background="#C9E66A" data-transition="zoom" -->
## Teste Unitário 🔍
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste Unitário
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Teste de uma unidade é o processo de testar trechos de código como métodos ou classes
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Assim, o teste chama estas rotinas com diversos parâmetros de entrada com o objetivo de tentar identificar falhas
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Se por exemplo, você realizar um teste num objeto, é necessário criar testes para analisar todas os serviços do objeto
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste Unitário
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Assim, em um objeto é importante testar:
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Todas as operações associadas com o objeto
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Atribuir e analisar todos os valores de atributos
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Colocar o objeto em diversos estados com o objetivo de simular todos os eventos que façam com que o objeto troque de estado
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste Unitário
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Imagine uma classe que implemente uma estação meteorológica
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Para testar o estado de um objeto é necessário definir um modelo de estados
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Usando um modelo, você conseguirá identificar sequências de estados que deverão ser testadas
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Exemplos de estados para uma estação:
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->
  * Shutdown → Running → Shutdown
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Shutdown → Running → Collecting → Transmitting → Running→Shutdown
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste Unitário
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* As vezes um objeto a ser testado possui algum tipo de dependência, como por exemplo, um banco de dados
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Neste caso, você pode escolher utilizar objetos Mock
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Mock, é um objeto com a mesma interface de um objeto real, porém, utilizado para simular funcionalidades
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Assim, um objeto Mock de um banco de dados deve possuir itens organizados no formato de um array
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste Unitário
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Teste geralmente é algo caro e que gasta tempo de trabalho
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Assim, é importante criar casos de teste efetivos:
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Casos de teste devem mostrar que o trecho de código faz o que ele deveria fazer
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Se existem defeitos, o teste deve revelar estes problemas
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Assim, normalmente escrevemos casos de testes para analisar o funcionamento normal de um sistema e, baseado na experiência de programação, criar testes para revelar problemas (usando entradas anormais)
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste Unitário
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Existem duas estratégias para ajudar a escolher casos de teste:
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * **Teste de partição** – onde identificamos grupos de inputs com características comuns e que devem serem processadas da mesma maneira. Devem ser criados casos de teste para cada tipo de entrada de dado
  <!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * **Teste baseado em orientação** – onde se utiliza uma orientação ou guia (_guideline_). Esta orientação reflete uma experiência passada sobre os tipos de erros que os programadores geralmente cometem
  <!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste Unitário
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* A entrada e saída de um programa frequentemente caem num número de classes de características similares
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Exemplo de classes: número, números negativos, strings, etc.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Os programas, geralmente se comportam da mesma maneira se a entrada de dado for da mesma classe
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Por exemplo, se um método espera dois números positivos, então, o código deve se comportar na mesma maneira para qualquer número positivo
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste Unitário
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

![imagem](img/domain.png)
<!-- .element height="50%" width="50%" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste Unitário
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* As classes também são chamadas de partições ou domínios
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Uma boa ideia é escolher testes que exercitem o limite de uma partição e também valores intermediários
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Por exemplo, se você identificar uma partição de números positivos, então, o valor zero pode ser um ponto no limite
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste Unitário
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

![imagem](img/partition.png)
<!-- .element height="50%" width="50%" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste Unitário
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Você identificará as partições através da especificação do sistema, da documentação de usuário ou da sua experiência anterior
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Quando você utiliza, por exemplo, a especificação para criar as partições, então dizemos que isto é um "_**black-box testing**_"
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Entretanto, pode ser útil olhar o código para identificar partições (ex.: olhar as exceções), neste caso, dizemos que este é um "_**white-box testing**_"
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste Unitário
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Você também pode utilizar guias para ajudar a escolher os casos de teste. Os guias possuem conhecimento de quais testes são realmente efetivos
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Por exemplo, se você estiver testando um método que contém uma lista (_array_)
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Teste sequências com um único valor
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Use diferentes sequências de diferentes tamanhos em testes diferentes
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Derive testes de modo que exercite os primeiros elementos, meio e fim da sequência. Esta abordagem revela problemas de limites na partição
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide:  data-background-opacity="1" data-background-image="img/dicasUnitario.png" data-transition="zoom"  -->



<!-- .slide: data-background="#C9E66A" data-transition="zoom" -->
## Teste de componente 🏠
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Componentes 🏠
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Os componentes de software consistem frequentemente em vários objetos que interagem entre sí. 😊
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* O teste de componente deve se concentrar em mostrar que a interface (ou interfaces) do componente se comporta de acordo com a sua especificação, por exemplo:
<!-- .element: style="margin-bottom:25px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

![](img/testedecomponentes.png)
<!-- .element: style="height: 250px; margin-top: 20px" -->

Fonte: SOMMERVILLE, Ian. [Engenharia de software](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5030950&acesso=aHR0cHM6Ly9taWRkbGV3YXJlLWJ2LmFtNC5jb20uYnIvU1NPL2lmcnMvOTc4ODU0MzAyNDk3NA==&label=acesso%20restrito), 10ª ed., Editora Pearson, cap. 8., Figura 8.7.
<!-- .element: style="margin-bottom:25px; font-size: 10px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Tipos de interface 🖱️
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* **Interface de parâmetro:** são interfaces onde passamos dados ou referências a funções de um componente para o outro.
<!-- .element: style="margin-bottom:40px; font-size: 18px; font-family: arial; color:#F5F5F5" -->

* **Interface de memória compartilhada:** são interfaces nas quais um bloco de memória é compartilhado por um componente. Os dados são colocamos na memória por um subsistema e recuperados por outro subsistema (por exemplo: em sistemas embarcados).
<!-- .element: style="margin-bottom:40px; font-size: 18px; font-family: arial; color:#F5F5F5" -->

* **Interface de procedimento:** são interfaces em que um component encapsula um conjunto de procedimentos. Objetos e/ou componentes reusáveis têm esse formato de interface.
<!-- .element: style="margin-bottom:40px; font-size: 18px; font-family: arial; color:#F5F5F5" -->

* **Interface de passagem de mensagens:** são interfaces em que um componente solicita um serviço de outro componente por meio da passagem de uma mensagem (por exemplo: um Web services).
<!-- .element: style="margin-bottom:20px; font-size: 18px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Erros de interfaces 🐛
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* **Mau uso da interface:** Um componente chama outro componente e comete um erro no uso de sua interface. Por exemplo, enviar dados errados (tipo, ordem, valores, etc.) em uma interface de parâmetro.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* **Má compreensão da interface:** Um componente não compreende a interface e faz suposições sobre o seu comportamento.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->

* **Erros de temporização:** Um componente, por possuir uma velocidade diferente, pode consumir dados obsoletos.
<!-- .element: style="margin-bottom:50px; font-size: 22px; font-family: arial; color:#F5F5F5" -->


<!-- .slide:  data-background-opacity="0.9" data-background-image="img/dicasComponentes.png" data-transition="zoom"  -->



<!-- .slide: data-background="#C9E66A" data-transition="zoom" -->
## Teste de sistema 🖥️
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste de sistema 🖥️
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* O teste de sistema durante o desenvolvimento envolve a integração dos componentes para criar uma versão do sistema
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Esse tipo de teste verifica se os componentes são compatíveis, se interagem corretamente e se transferem os dados certos no momento certo por meio de suas interfaces.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Existem duas diferença entre o teste de sistema e de componentes:
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Os componentes reutilizados podem ser integrados com componentes recém-desenvolvidos.
  <!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

  * Componentes desenvolvidos por diferentes membros do time podem ser integrados nessa fase.
  <!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste de sistema 🖥️
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Todos os sistemas possuem comportamentos emergentes. Isso significa que algumas funcionalidades e características se tornam óbvias somente quando os componentes são unidos.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Em virtude de seu foco nas interações, o teste baseado em casos de uso é uma abordagem eficaz para o teste de sistema.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Caso seja desenvolvido um diagrama de sequência para modelar a implementação do caso de uso, será possível observar os objetos ou os componentes envolvidos na interação.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste de sistema 🖥️
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* Na maioria dos sistemas, é difícil saber o quanto de teste de sistema é essencial e quando se deve parar de testar.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* É impossível testar exaustivamente, ao ponto em que toda a sequência possível de execução seja testada.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Assim, as empresas de _software_ devem ter políticas para escolher o conjunto de teste. Por exemplo, grau de cobertura, entrada de dados dos usuários, casos de uso críticos, etc.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste de sistema 🖥️
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* O teste de sistema automatizado tende a ser mais difícil do que o teste automatizado de unidade ou de componente
<!-- .element: style="margin-bottom:70px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* A razão de implementar um sistema pode ser a geração de saídas grandes ou que não possam ser previstas com facilidade.
<!-- .element: style="margin-bottom:70px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Teste de sistema 🖥️
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* O teste de sistema envolve a integração de diferentes componentes
<!-- .element: style="margin-bottom:70px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* Uma abordagem incremental para integração e teste deve sempre ser usada: se um componente é integrado, o sistema é testado novamente, e assim por diante.
<!-- .element: style="margin-bottom:70px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

* A integração e os teste incrementais são fundamentais para os métodos ágeis, cujos testes de regressão são executados a cada novo incremento.
<!-- .element: style="margin-bottom:70px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## Referências 📚
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

* SOMMERVILLE, Ian. [Engenharia de software](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5030950&acesso=aHR0cHM6Ly9taWRkbGV3YXJlLWJ2LmFtNC5jb20uYnIvU1NPL2lmcnMvOTc4ODU0MzAyNDk3NA==&label=acesso%20restrito), 10ª ed. Editora Pearson 768 ISBN 9788543024974.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
  <!-- .element: style="margin-top:150px; font-size: 15px; font-family: Bradley Hand" -->