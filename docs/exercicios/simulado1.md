---
layout: default
title: Primeira Avaliação
parent: Simulados
nav_order: 21
---


# Primeira Avaliação 📝

## Questões práticas

1. Crie um teste unitário em JUnit 5 que verifique se o método multiplicar(int a, int b)
da classe Calculadora retorna corretamente o resultado da multiplicação de dois números.
{: .fs-3 }

2. Implemente um teste unitário em JUnit 5 para o método contarCaracteres(String texto) da classe UtilTexto.
   * O teste deve verificar se, ao passar a string "java", o método retorna o valor 4.
{: .fs-3 }

3. Implemente um teste em JUnit 5 para a classe CarrinhoDeCompras.
   * Antes de cada teste, deve ser criada uma nova instância de CarrinhoDeCompras usando o método anotado com @BeforeEach.
   * Escreva um teste que adicione dois produtos ao carrinho e verifique se o tamanho da lista de produtos é igual a 2.
{: .fs-3 }

## Questões teóricas

1. O que representa “V&V” no contexto de desenvolvimento de software?
* A) Versão e Velocidade
* B) Validação e Verificação
* C) Visualização e Versatilidade
* D) Valor e Viabilidade
{: .fs-3 }

2. Qual afirmativa descreve melhor a diferença entre verificação e validação?
* A) Verificação se concentra no código em execução, enquanto validação se concentra somente em documentos.
* B) Verificação é uma atividade dinâmica; validação é estática.
* C) Verificação avalia se o produto atende requisitos e especificações; validação se preocupa se atende às necessidades do usuário.
* D) Verificação é feita somente por testes manuais; validação é sempre automatizada.
{: .fs-3 }

3. O que são inspeções no contexto de V&V?
* A) Testes automatizados para garantir desempenho sob carga.
* B) Revisões formais e sistemáticas de artefatos como documentos ou código, procurando erros e inconsistências.
* C) Simulações com usuários finais para verificar usabilidade.
* D) Compilação e execução de código em diferentes ambientes.
{: .fs-3 }

4. Qual dos seguintes é um objetivo dos testes, conforme descrito no texto?
* A) Modificar requisitos que se mostraram inadequados.
* B) Reduzir o número de desenvolvedores necessários.
* C) Criar artefatos que substituam documentos de design.
* D) Executar o software em condições específicas para ver se ele se comporta como esperado.
{: .fs-3 }

5. No planejamento de V&V, o que significa “rastreabilidade de requisitos”?
* A) Mapear os requisitos do produto para as atividades de V&V, garantindo que cada requisito seja verificado e validado.
* B) Rastrear os autores dos requisitos para responsabilização.
* C) Medir a quantidade de requisitos alterados durante o desenvolvimento.
* D) Registrar cada requisito em uma ferramenta de versionamento de código.

6. Em que momento a validação deve ocorrer no ciclo de vida do software?
* A) Apenas após a entrega final ao cliente
* B) Somente na fase de testes de integração
* C) Ao longo de todo o processo de desenvolvimento, garantindo que o produto em construção atenda às necessidades do usuário
* D) Antes da definição de requisitos, para confirmar as metas do projeto
{: .fs-3 }

7. Qual é a principal consequência de não realizar atividades adequadas de V&V durante o desenvolvimento de software?
* A) Aumento da velocidade de entrega do produto.
* B) Redução dos custos de manutenção a longo prazo.
* C) Possibilidade de entregar um produto que não atende aos requisitos ou às necessidades do usuário.
* D) Maior compatibilidade com diferentes sistemas operacionais.
{: .fs-3 }

8. Qual dos ciclos de vida do Maven é responsável por limpar artefatos criados em builds anteriores?
* A) default
* B) site
* C) clean
* D) verify
{: .fs-3 }

9. Qual é a fase do ciclo de vida default do Maven em que são executados testes unitários?
* A) validate
* B) compile
* C) test
* D) package
{: .fs-3 }

10. Sobre plugins e goals no Maven: qual das afirmações abaixo está de acordo com o conteúdo explicado?
* A) Um plugin é uma tarefa específica, e um goal é o conjunto de plugins que compõem uma fase.
* B) Um goal é uma tarefa específica executada por um plugin; um plugin pode ter vários goals.
* C) Plugins servem só para empacotar o código e não para análise estática.
* D) Goals apenas determinam a versão do Maven utilizada no projeto.
{: .fs-3 }

11. Em um projeto Maven, qual arquivo central contém as configurações, dependências e informações do projeto?
* A) build.gradle
* B) settings.xml
* C) project.json
* D) pom.xml
{: .fs-3 }

12. Qual é a função do comando mvn package no Maven?
* A) Compilar o código-fonte do projeto.
* B) Executar os testes de integração.
* C) Empacotar o código compilado em um formato distribuível (como JAR ou WAR).
* D) Instalar dependências no repositório remoto.
{: .fs-3 }

13. Em que formato o PMD pode gerar relatórios de análise?
* A) Apenas em PDF
* B) Somente em HTML
* C) Em diversos formatos, como XML, CSV e HTML
* D) Exclusivamente em JSON
{: .fs-3 }

14. Qual é o principal objetivo do Checkstyle?
* A) Detectar falhas de segurança no código Java.
* B) Garantir que o código siga padrões de estilo e boas práticas de formatação.
* C) Executar testes unitários automaticamente.
* D) Gerar relatórios de cobertura de código.
{: .fs-3 }

15. Qual é uma vantagem de integrar o Checkstyle ao ciclo de build do Maven?
* A) O Maven passa a compilar o código mais rapidamente.
* B) O Checkstyle substitui o compilador Java, evitando erros de sintaxe.
* C) O processo de build pode garantir automaticamente que o código siga padrões de estilo antes de ser aceito.
* D) A execução de testes unitários se torna opcional.
{: .fs-3 }

16. Qual é o principal objetivo do JUnit?
* A) Facilitar a criação de interfaces gráficas em Java.
* B) Automatizar testes unitários para validar o comportamento do código.
* C) Compilar o código Java com maior eficiência.
* D) Substituir o Maven no gerenciamento de dependências.
{: .fs-3 }

17. No JUnit 5, qual anotação deve ser usada para marcar um método como um caso de teste?
* A) @RunTest
* B) @Testing
* C) @Test
* D) @Execute
{: .fs-3 }


18. Qual anotação do JUnit é utilizada para executar um método antes de cada teste?
* A) @BeforeAll
* B) @BeforeEach
* C) @InitTest
* D) @Setup
{: .fs-3 }


19. O que acontece quando uma asserção falha em um teste JUnit?
* A) O teste continua normalmente até o fim.
* B) O compilador ignora o erro.
* C) O resultado do teste é marcado como “inconclusivo”.
* D) O teste é interrompido e marcado como falho.
{: .fs-3 }

20. Qual anotação do JUnit 5 deve ser usada para indicar que um método de teste não deve ser executado?
* A) @Disabled
* B) @IgnoreTest
* C) @Skip
* D) @NoRun
{: .fs-3 }

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
