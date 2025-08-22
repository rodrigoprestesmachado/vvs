---
layout: default
title: Maven
parent: Análise Estática
nav_order: 5
---

# Maven 🧰

O Maven é uma ferramenta de automação e gerenciamento de projetos, bastante
utilizada no ecossistema Java. Foi criada pela Apache Software Foundation no ano
de 2002 e é uma das ferramentas mais utilizadas para construir projetos Java. O
Maven permite que você compile, teste, empacote, instale e implante projetos.
Além disso, o Maven fornece maneiras de gerenciar dependências que são baixadas
automaticamente da internet.

## Principais conceitos 📚

Para automatizar a construção de um projeto, o Maven utiliza um conceito de
ciclo de vida. Um **ciclo de vida** é uma sequência de _fases_ que
são executadas em ordem determinada. Cada **fase** é responsável pela execução
de uma ou mais tarefas específica.

Os principais ciclos de vida com suas respectivas fases no Maven são: _default_,
_clean_ e _site_.

* O ciclo de vida _default_ compreende as fases: _validate_, _compile_,
  _test_, _package_, _verify_, _install_ e _deploy_.
* Já o ciclo de vida _clean_ inclui as fases _pre-clean_, _clean_
  e _post-clean_.
* Finalmente, o ciclo de vida _site_ inclui as fases _pre-site_, _site_
  e _post-site_.

A [Figura 1](http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/rodrigoprestesmachado/vvs/dev/docs/maven/map.puml) a seguir mostra um mapa conceitual sobre os ciclos de
vidas e as fases do Maven.

<center>
    <a href="http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/rodrigoprestesmachado/vvs/dev/docs/maven/map.puml" target="blanck">
        <img src="http://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/rodrigoprestesmachado/vvs/dev/docs/maven/map.puml" alt="Maven" width="35%" height="35%"/>
    </a>
    <br/>
    Figura 1 - Mapa conceitual sobre os ciclos de vidas e as fases do Maven
</center>


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
adiciona funcionalidades na fase em que é executado. Por exemplo, o plugin
`maven-pmd-plugin` adiciona a funcionalidade de análise estática de código na
fase `verify` do ciclo de vida _default_

Um plugin pode ter um conjunto de _goals_. Um **goal** é uma tarefa específica
que um plugin executa. Por exemplo, o plugin `maven-pmd-plugin` tem o goal
`pmd` que executa a análise estática de código. Porém, esse mesmo _plugin_
possui outros _goals_ como `check` e `cpd`. Todos esses _goals_ podem ser
executados na fase `verify` do ciclo de vida _default_.

## Configuração 🖥️

Para configurar o Maven em um projeto, é necessário criar um arquivo `pom.xml`
(_Projeto Object Model_) na raiz do projeto. O arquivo `pom.xml` é um arquivo de
configuração do Maven que contém informações sobre o projeto, como por exemplo,
o nome do projeto, a versão, a descrição, as dependências, os plugins, entre
outras informações.

Podemos personalizar o ciclo existente conectando plugins às fases ou criando
metas específicas. Por exemplo, se quisermos adicionar o analisador estático
[PMD](https://pmd.github.io/pmd/index.html) ao nosso projeto, podemos fazer isso
usando o Maven:

```xml
<!-- Analisador estático PMD -->
<plugin>
  <!-- Declaração do plugin -->
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-pmd-plugin</artifactId>
  <version>3.21.2</version>
	<executions>
    <execution>
      <id>pmd-verify</id>  <!-- Identificador interno -->
      <phase>verify</phase> <!-- Fase que irá ser executado -->
      <goals>
      <goal>check</goal> <!-- Goal ou ferramenta que será executado -->
      </goals>
    </execution>
  </executions>
  <!-- Configurações do plugin -->
  <configuration>
    <failOnViolation>true</failOnViolation>
    <printFailingErrors>true</printFailingErrors>
    </configuration>
</plugin>
```

Da mesma forma, podemos adicionar o plugin [Checkstyle](https://maven.apache.org/plugins/maven-checkstyle-plugin/)
ao nosso projeto:

```xml
<plugin>
  <!-- Declaração do plugin -->
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-checkstyle-plugin</artifactId>
  <version>3.3.1</version>
  <executions>
    <execution>
      <id>checkstyle-verify</id>
      <phase>verify</phase> <!-- Fase que irá ser executado -->
      <goals>
        <goal>check</goal> <!-- Ferramenta -->
      </goals>
    </execution>
  </executions>
  <configuration>
    <encoding>UTF-8</encoding>
    <consoleOutput>true</consoleOutput>
    <failsOnError>true</failsOnError>
  </configuration>
</plugin>
```

## Exercício 🏋️

1. Crie um projeto Java usando o Maven (pode ser um projeto Quarkus).
1. Adicione o PMD e o Checkstyle ao projeto.
1. Inclua as três classes abaixo no projeto.
1. Execute a fase verify do Maven para identificar possíveis problemas no código.

```Java
package br.com.exemplo.modelo;

import java.util.Date;

public class Cliente {
    public String nome;
    public String email;
    public int idade;
    private String codigoInterno;
    private Date cadastro;

    public Cliente(String n, String e, int i) {
        this.nome = n;
        this.email = e;
        this.idade = i;
        cadastro = new Date();
        String temporariaQueNaoUsa = "xxx";
    }

    public boolean valida() {
        if (nome == null || email == null) {
            System.out.println("Dados inválidos");
            return false;
        }
        if (nome == "") {
            System.out.println("Nome vazio");
        }
        if (idade < 0 || idade > 200) {
            System.out.println("Idade estranha");
        }
        return true;
    }

    public String getEmail() {
        if (email == null) {
            email = "";
        }
        return email.trim();
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Cliente) {
            Cliente c = (Cliente) o;
            return this.email == c.email;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return 42;
    }

    public void Atualiza(){ }
}
```

```java
package br.com.exemplo.modelo;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    public List<String> itens = new ArrayList<>();
    public double valorTotal;
    private String status = "NOVO";

    public void adicionarItem(String nome, double preco){
        if (nome == null || nome.length() == 0) {
            System.out.println("Item sem nome");
        }
        itens.add(nome);
        valorTotal += preco;
        if (valorTotal > 1000) { System.out.println("Pedido grande!"); }
    }

    public String resumo(){
        String s = "Pedido: ";
        for (int i=0;i<itens.size();i++){
            s = s + itens.get(i) + ", ";
        }
        return s;
    }

    public void processar(){
        if ("NOVO".equals(status)) {
            System.out.println("Processando pedido novo...");
            status = "PROCESSANDO";
        }
        if ("PROCESSANDO".equals(status)) {
            System.out.println("Ainda processando...");
        }

        int tipo = 2;
        switch (tipo) {
            case 1: System.out.println("Tipo 1"); break;
            case 2: System.out.println("Tipo 2"); break;
        }

        try {
            if (valorTotal < 0) throw new IllegalStateException("valor negativo?");
        } catch (Exception e) {
        } finally {
            if (itens.size() == 0) { return; }
        }
    }

    public void FecharPedido(boolean notificarCliente){
        if (!"PROCESSANDO".equals(status)) { System.out.println("Estado inválido"); }
        status = "FECHADO";
        if (notificarCliente) {
            System.out.println("Notificando cliente...");
        }
        if (false) {
            System.out.println("Nunca executa");
        }
    }
}
```

```java
package br.com.exemplo.servico;

import br.com.exemplo.modelo.Pedido;

public class PedidoService {

    public synchronized boolean ProcessaOuNao(Pedido p, int t){
        if (p == null) return false;
        boolean ok = false;

        if (t > 10) {
            ok = true;
        } else {
            if (t <= 10) { ok = false; }
        }

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!ok) {
            return false;
        }

        System.out.println("Processando pedido no serviço...");
        p.FecharPedido(true);
        return true;
    }

    public String buscaStatus(Pedido p){
        if (p == null) {
            return null;
        }
        return null;
    }
}
```

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
