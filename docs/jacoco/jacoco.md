---
layout: default
title: JaCoCo
parent: Teste de desenvolvimento
nav_order: 12
---

# JaCoCo 📊

O **JaCoCo** (_Java Code Coverage_) é uma ferramenta de análise de cobertura de
código para Java. Ele é utilizado para medir a cobertura de código de um
projeto, ou seja, a porcentagem de código que é executada durante a execução
dos testes. O JaCoCo é um plugin do Maven e pode ser configurado no arquivo
`pom.xml` do projeto.

## Configuração 🖥️

Para configurar o JaCoCo no Maven, é necessário adicionar o plugin `jacoco-maven-plugin` no arquivo `pom.xml` do projeto. O plugin
`jacoco-maven-plugin` possui um goal `report` que gera um relatório de
cobertura de código. Esse relatório é gerado na pasta `target/site/jacoco`
do projeto.

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <id>preparation</id>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>verify</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

No exemplo acima, o plugin `jacoco-maven-plugin` é configurado para executar
na fase `verify` do ciclo de vida _default_ do Maven. Isso significa que o
relatório de cobertura de código será gerado após a execução dos testes. O _goal_
`prepare-agent` é utilizado para preparar o ambiente para a execução dos testes.
Note que cada execução necessita de um identificador (`id`) próprio.

Para adicionar o relatório no Jacoco no cilco de vida de _site_, é necessário
primeiro adicionar o plugin `maven-site-plugin` no arquivo `pom.xml` do projeto.
Este plugin é responsável por gerar o site do projeto, que inclui o relatório
de cobertura de código.

```xml
 <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-site-plugin</artifactId>
    <version>4.0.0-M13</version>
</plugin>
```

Depois de adicionar o plugin `maven-site-plugin`, é necessário adicionar a tag
`reporting` no arquivo `pom.xml` do projeto. A tag `reporting` é utilizada para
configurar os relatórios que serão gerados no ciclo de vida _site_ do Maven.
Observe o exemplo abaixo:

```xml
<reporting>
    <plugins>
        <plugin>
            <groupId>org.jacoco</groupId>
            <artifactId>jacoco-maven-plugin</artifactId>
            <version>0.8.11</version>
        </plugin>
    </plugins>
</reporting>
```

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">Creative Commons Atribuição 4.0 Internacional</a>
</center>
