---
layout: default
title: Maven
nav_order: 16
---

# Maven 🧰

O Maven é uma ferramenta de automação de compilação utilizada principalmente
em projetos Java. Foi criada pela Apache Software Foundation no ano de 2002 e
é uma das ferramentas mais utilizadas para construir projetos Java. O Maven
permite que você compile, teste, empacote, instale e implante projetos.
Ele também fornece maneiras de gerenciar dependências e configurações de
projeto.

## Principais conceitos 📚

Para automatizar a construção de um projeto, o Maven utiliza um conceito de
ciclo de vida. Um **ciclo de vida** é uma sequência de _fases_ que
são executadas em ordem a fim de construir um projeto. Cada **fase** é
responsável pela execução de uma ou mais tarefas específica.

O principais ciclos de vida com suas respectivas fases no Maven são: _default_, _clean_ e _site_.

* O ciclo de vida _default_ compreende as fases: _validate_, _compile_,
  _test_, _package_, _verify_, _install_ e _deploy_.
* Já o ciclo de vida _clean_ inclui as fases _pre-clean_, _clean_
  e _post-clean_.
* Finalmente, o ciclo de vida _site_ inclui as fases _pre-site_, _site_
  e _post-site_.

O ciclo de default é o mais utilizado e é o que será abordado neste material.

* _validade_: valida o projeto, como por exemplo, verificar se os recursos
  necessários estão disponíveis.
* _compile_: compila o código fonte do projeto.
* _test_: executa os testes unitários do projeto.
* _package_: empacota o código compilado em um formato específico, como por
  exemplo, um JAR (_Java Archive_) ou WAR (_Web Archive_).
* _verify_: executa uma análise de qualidade no projeto, como por exemplo,
  inspeções de código, teste de integração e cobertura.
* _install_: instala o pacote no repositório local, para que ele possa ser
    utilizado como dependência em outros projetos.
* _deploy_: implanta o projeto.

Cada fase pode utilizar um ou mais _plugins_. Um **plugin** do Maven
adiciona funcionalidades na fase em que é executado. Por exemplo, o plugin `maven-pmd-plugin` adiciona a funcionalidade de análise estática de código na
fase `verify` do ciclo de vida _default_

Um plugin pode ter um conjunto de _goals_. Um **goal** é uma tarefa específica
que um plugin executa. Por exemplo, o plugin `maven-pmd-plugin` tem o goal
`pmd` que executa a análise estática de código. Porém, esse mesmo _plugin_
possui outros _goals_ como `check` e `cpd`. Todos esses _goals_ podem ser
executados na fase `verify` do ciclo de vida _default_.

## Configuração 🖥️

Para configurar o Maven em um projeto, é necessário criar um arquivo `pom.xml`
(_Projeto Object Model_) na raiz do projeto. O arquivo `pom.xml` é um arquivo de configuração do Maven que contém informações sobre o projeto, como por exemplo, o nome do projeto, a versão, a descrição, as dependências, os plugins, entre outras informações.

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">Creative Commons Atribuição 4.0 Internacional</a>
</center>



