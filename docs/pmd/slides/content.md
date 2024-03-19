<!-- .slide:  data-background-opacity="0.3" data-background-image="img/title.jpg"
data-transition="convex"  -->
# PMD e Checkstyle
<!-- .element: style="margin-bottom:100px; font-size: 50px; color:white; font-family: Marker Felt;" -->

Pressione 'F' para tela cheia
<!-- .element: style="font-size: small; color:white;" -->

[versão em pdf](?print-pdf)
<!-- .element: style="font-size: small;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O que é o PMD?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

- O PMD é uma ferramenta de análise estática de código.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

- Ele ajuda os desenvolvedores a escreverem código de melhor qualidade.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

- O PMD verifica o código-fonte em busca de possíveis problemas, como
_bugs_, _code smells_ e violações de convenções de codificação.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## PMD no Maven
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

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
                <ruleset>https://raw.githubusercontent.com/rodrigoprestesmachado/tpack/master/pmd.xml</ruleset>
            </rulesets>
        </configuration>
    </plugin>
```
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: Courier New;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
## O que é o Checkstyle?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

 - O Checkstyle é uma ferramenta de análise estática de código.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

- Ajuda os desenvolvedores a escreverem código de melhor qualidade.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

- O Checkstyle verifica o código-fonte em busca de possíveis problemas, como
formatação, documentação, convenções de codificação, entre outros.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Checkstyle no Maven
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <version>3.1.1</version>
    <executions>
        <execution>
            <phase>verify</phase>
            <goals>
                <goal>checkstyle:check</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <configLocation>checkstyle.xml</configLocation>
    </configuration>
</plugin>
```
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: Courier New;" -->


<!-- .slide: data-background="white" data-transition="convex"  -->
## Como interromper o ciclo *default* do Maven?
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:black" -->

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
        <failOnViolation>true</failOnViolation>
        <maxAllowedViolations>5</maxAllowedViolations>
        <rulesets>
            <ruleset>https://raw.githubusercontent.com/rodrigoprestesmachado/tpack/master/pmd.xml</ruleset>
        </rulesets>
    </configuration>
</plugin>
```
<!-- .element: style="margin-bottom:50px; font-size: 20px; font-family: Courier New;" -->


<!-- .slide: data-background="#185449" data-transition="convex"  -->
# Referências 📚
<!-- .element: style="margin-bottom:50px; font-size: 40px; font-family: Marker Felt; color:#F5F5F5" -->

SOMMERVILLE, Ian. [Engenharia de software](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5030950&acesso=aHR0cHM6Ly9taWRkbGV3YXJlLWJ2LmFtNC5jb20uYnIvU1NPL2lmcnMvOTc4ODU0MzAyNDk3NA==&label=acesso%20restrito), 10ª ed. Editora Pearson 768, cap. 8 ISBN 9788543024974.
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

SOMMERVILLE, Ian. [Slides](https://iansommerville.com/software-engineering-book/slides/) do capítulo 8 (em inglês).
<!-- .element: style="margin-bottom:50px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

SOMMERVILLE, Ian. [Vídeos](https://iansommerville.com/software-engineering-book/videos/imp/) do capítulo 8 (em inglês).
<!-- .element: style="margin-bottom:40px; font-size: 23px; font-family: arial; color:#F5F5F5" -->

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">Creative Commons Atribuição 4.0 Internacional</a>
</center>
  <!-- .element: style="margin-top:150px; font-size: 15px; font-family: Bradley Hand" -->