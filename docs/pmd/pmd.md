---
layout: default
title: Análise estática - PMD e Checkstyle
nav_order: 5
---

# PMD e Checkstyle

<center>
    <iframe src="https://vvs.rpmhub.dev/pmd/slides/index.html#/"
    title="PMD e Checkstyle"
    width="90%" height="500" style="border:none;">
    </iframe>
</center>

## PMD

Todos os artefatos de um projeto podem ser inspecionados, como por exemplo:
requisitos, modelos UML, trechos de código, etc. Os artefatos podem ser
analisados manualmente por meio de revisões por pares e/ou *checklists*. Porém
quando pensamos em inspeções em código fonte, atualmente contamos com um grande
conjunto de ferramentas capazes de realizar esse tipo específico de análise.

Nesse sentido, o [PMD](https://pmd.github.io) é uma ferramenta capaz de analisar
códigos fonte normalmente escritos em Java. O PDM possui um conjunto grande de
[regras](https://pmd.github.io/pmd-6.27.0/pmd_rules_java.html) de análise que
são capazes de apurar desde o estilo do código até questões mais complexas como
segurança e desempenho. Além disso, existe a possibilidade de se criar novas
regras no PMD, ou seja, essa característica garante uma boa flexibilidade e,
consequentemente, uma possibilidade de adaptar seu uso em diferentes tipos de
projeto Java.

Uma ferramenta como o PMD pode ser utilizada durante a fase de desenvolvimento
e/ou fazer parte das etapas de construção e instalação de um sistema, assim, as
próximas seções são dedicadas para mostrar o uso do PMD nesses dois contextos.

---
**Para saber mais:** O PMD está sendo utilizado como um exemplo nesse documento,
porém, existem muitas outras ferramentas capazes de fazer o mesmo tipo de
análise, um exemplo disso é o [SonarLint](https://www.sonarlint.org), que
também possui possui extensões para [Vscode](https://marketplace.visualstudio.com/items?itemName=SonarSource.sonarlint-vscode),
Eclipse, IntelliJ, entre outros. Também cabe salientar que ferramentas de
inspeção de código estático não são uma exclusividade de Java, ou seja, em
praticamente todas as linguagens você encontrará sistemas desse tipo.

---

### PMD no Vscode

O vídeo abaixo mostra um exemplo simples de como podemos configurar e utilizar o
PDM dentro do Vscode. O vídeo tem o foco em três pontos, são eles: (1) a
criação de um projeto por meio dos plugins [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) e [Maven for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-maven)
para VScode, (2) a configuração de regras para o PDM e o uso das regras dentro
do VScode por intermédio do plugin
[Apex PMD](https://marketplace.visualstudio.com/items?itemName=chuckjonas.apex-pmd).

<center>
    <iframe
    width="560" height="315"
    src="https://www.youtube.com/embed/ZgTtVUx6dcQ"
    frameborder="0"
    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
    allowfullscreen>
    </iframe>
</center>

### Regras do PMD

Como foi mostrado no vídeo, o PDM possui um conjunto grande de regras, assim,
os exemplos abaixo mostram regras consideradas úteis por diversos programadores,
são elas:

---
**Nota:** as regras e explicações abaixo foram escritas de maneira colaborativa.

---

1. Best Practices
1. Code Style
1. Design
1. Documentation
1. Error Prone
1. Multithreading
1. Performance
1. Security
1. Additional rulesets
1. PMD com o Maven

### PMD no Maven

O PDM possui um [plugin](https://maven.apache.org/plugins/maven-pmd-plugin/)
para Maven, ou seja, existe a possibilidade de se incorporar inspeções estáticas
dentro do processo de integração contínua. Portanto, antes mesmo de compilarmos
um código, podemos realizar uma análise e, por meio de parâmetros de qualidade,
decidir se iremos ou não continuar com a integração de um novo trecho de
código (funcionalidade, correção de defeitos, etc.) em um sistema.

O plugin do PMD para o Maven possui diversos *[goals](https://maven.apache.org/plugins/maven-pmd-plugin/#goals-overview)*,
alguns exemplos:

1. `pmd:pmd` - cria um relatório do PMD com base nos conjuntos de regras e
   configurações definidas no plugin.
2. `pmd:cpd` - gera um relatório para o Copy/Paste Detector (CPD) do PMD. Em
   outras palavras, o CPD é capaz de encontrar trechos de código duplicados.
3. `pmd:check` - verifica se o relatório PMD está vazio, se não, falha o
   processo de *build*.
4. `pmd:cpd-check` - verifica se o relatório de CPD está vazio, se não, falha o
   processo de *build*. Esse *goal* é executado por padrão quando `pmd:cpd` for
   invocado.

Um *goal* de um _plugin_ representa uma tarefa específica que contribui para a
construção e gerenciamento de um projeto. A ordem de execução depende da ordem
em que o(s) goal(s) e as fases de construção (*build*) são invocadas.

No Maven, existe a ideia de que um ciclo _default_
compreende um conjunto de fases. As fases padrão de um projeto Maven são:
*validate*, *compile*, *test*, *package*, *verify*, *install* e *deploy*. Cada
fase é composta por um conjunto de *goals*.

Por exemplo, a fase *validate* é composta por um conjunto de *goals* que
verificam a validade do projeto, como por exemplo, a verificação de dependências
e a verificação de propriedades do projeto. A fase *compile* é composta por um
conjunto de *goals* que compila o código-fonte do projeto. A fase *test* é
composta por um conjunto de *goals* que executam testes unitários do projeto.
A fase *package* é composta por um conjunto de *goals* que empacotam o código
compilado em um formato específico, como por exemplo, um JAR ou um WAR. A fase
*verify* é composta por um conjunto de *goals* que verificam a qualidade do
código-fonte do projeto. A fase *install* é composta por um conjunto de *goals*
que instalam o artefato do projeto no repositório local do Maven. A fase
*deploy* é composta por um conjunto de *goals* que copiam o artefato do projeto
para um repositório remoto.

Uma curiosidade, testes de integração e um teste de cobertura de código podem
ser executados durante a fase *verify* do ciclo _default_ do Maven.
Assim, na fase de *verify* é possível encadear uma série de *goals* e,
consequentemente, ferramentas de análise estática de código, teste de cobertura
de código, testes de integração, entre outros.

Assim, para configurar o PMD em um projeto Java/Maven, podemos que declarar o
_plugin_ do PMD dentro da seção de *plugins* do `pom.xml`, localizado na
seção `<build>`, observe o exemplo:

```xml
<build>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-pmd-plugin</artifactId>
        <version>3.21.2</version>
        <configuration>
            <rulesets>
                <ruleset>/category/java/bestpractices.xml</ruleset>
                <ruleset>pmd.xml</ruleset>
                <ruleset>https://raw.githubusercontent.com/rodrigoprestesmachado/tpack/master/pmd.xml</ruleset>
            </rulesets>
        </configuration>
    </plugin>
</build>
```

O exemplo acima faz com que o PMD seja executado durante o ciclo _default_
durante a fase *verify* do Maven. O PMD irá verificar o código-fonte do projeto
com base nas regras definidas no _plugin_. No exemplo acima, o _plugin_ do PMD
no Maven possui um conjunto de regras [pré-definidas](https://maven.apache.org/plugins/maven-pmd-plugin/examples/usingRuleSets.html)
chamadas de *best practices* (`/category/java/bestpractices.xml`). Porém,
podemos adicionar um conjunto de regras próprias por meio de um arquivo XML
e/ou por meio de uma URL.

Depois de modificar o `pom.xml` de um projeto, podemos executar (no diretório do
`pom.xml`) um dos *goals* do plugin do PMD diretamente, por exemplo:

    mvn pmd:pmd

Note que no exemplo acima, estamos executando apenas um *goal* do plugin, ou
seja, não estamos considerando um ciclo _default_ completo. Depois de
executado, o relatório do PMD estará disponível dentro de `target/pmd.xml`.

Caso você deseje fazer com que o PMD execute um *goal* específico durante o
ciclo _default_, você pode adicionar o *goal* `pmd:pmd` dentro da seção de
`<executions>` do plugin do PMD no `pom.xml` do projeto, observe o exemplo:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-pmd-plugin</artifactId>
    <version>3.21.2</version>
    <executions>
        <execution>
            <phase>verify</phase>
            <goals>
                <goal>pmd</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <rulesets>
            <ruleset>/category/java/bestpractices.xml</ruleset>
            <ruleset>pmd.xml</ruleset>
            <ruleset>https://raw.githubusercontent.com/rodrigoprestesmachado/tpack/master/pmd.xml</ruleset>
        </rulesets>
    </configuration>
</plugin>
```

O exemplo acima mostra a configuração do plugin do PMD no Maven. O *goal*
`pmd:pmd` é executado automaticamente durante a fase `verify` do ciclo
_default_. Se quisermos que o PMD execute mais de um *goal* durante o ciclo
de *build*, basta adicionar mais de um *goal* dentro da seção
`<goals>` do `<execution>`. Da mesma forma, se quisermos que o PMD execute em
mais de uma fase do ciclo de *build*, basta adicionar mais de um `<execution>`.

Porém, além do ciclo _default_, o Maven possui outros ciclos, como por
exemplo, o ciclo de *site*. O ciclo de *site* é composto por um conjunto de
fases que geram relatórios do projeto, como por exemplo, relatórios de testes,
relatórios de cobertura de código, relatórios de análise estática de código,
entre outros. Assim, o PMD pode ser executado durante o ciclo de *site* do
Maven, por exemplo, para gerar um relatório HTML com os problemas encontrados
no código-fonte.

**Nota:** Por padrão o Maven possui três ciclos principais, são eles: *clean*,
*default* e *site*.

### Checkstyle

O [Checkstyle](https://checkstyle.org) é uma ferramenta que ajuda a garantir que
o código Java de um projeto esteja em conformidade com um conjunto de regras
pré-definidas. O Checkstyle é altamente configurável e pode ser feito para
atender às necessidades específicas de um projeto. O Checkstyle pode ser
integrado em um processo de *build* por meio de plugins para Maven, Ant, Gradle,
entre outros.

O Checkstyle possui um conjunto de regras [pré-definidas](https://checkstyle.sourceforge.io/checks.html)
que podem ser utilizadas para verificar a qualidade do código. Além disso, o
Checkstyle permite que regras personalizadas sejam criadas e utilizadas.

Para configurar o Checkstyle em um projeto Java/Maven, temos que declarar o
plugin do Checkstyle dentro da seção de plugins do pom.xml do projeto da
seguinte maneira:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>3.1.1</version>
    <configuration>
        <configLocation>checkstyle.xml</configLocation>
    </configuration>
</plugin>
```

O exemplo acima mostra a configuração do plugin do Checkstyle no Maven. O
`checkstyle.xml` é um arquivo de configuração que contém as regras que o
Checkstyle usará para verificar a qualidade do código. O arquivo de configuração
pode ser personalizado para atender às necessidades específicas de um projeto.

Caso você não tenha um arquivo de configuração, o Checkstyle possui um conjunto
de regras [pré-definidas](https://checkstyle.sourceforge.io/checks.html) que
podem ser utilizadas para verificar a qualidade do código.

Caso você deseje colocar fazer com que o Checkstyle execute automaticamente
durante o ciclo de *build*, você pode adicionar o *goal* `checkstyle:check`
dentro da seção de `<executions>` do plugin do Checkstyle no `pom.xml` do
projeto, observe o exemplo:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>3.1.1</version>
    <executions>
        <execution>
            <phase>verify</phase>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <configLocation>checkstyle.xml</configLocation>
    </configuration>
</plugin>
```

## Exercício

1. Crie um projeto Java/Maven e adicione o plugin do PMD no `pom.xml` do projeto.

    Você pode criar um projeto Maven com o Quarkus ou por meio do comando abaixo:

    ```bash
    mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
    ```

2. Execute o PMD no Maven e verifique o relatório gerado.
3. Adicione o plugin do Checkstyle no `pom.xml` do projeto.
4. Execute o Checkstyle no Maven e verifique o relatório gerado.
5. Crie um arquivo de configuração para o Checkstyle e adicione regras
   personalizadas.
6. Execute o PMD e o Checkstyle automaticamente durante o ciclo de *build* do
   Maven com o comando:

    ```bash
    mvn verify
    ```

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">Creative Commons Atribuição 4.0 Internacional</a>
</center>
