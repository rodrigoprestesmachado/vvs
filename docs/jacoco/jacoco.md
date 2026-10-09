---
layout: default
title: JaCoCo
parent: Teste de componente
grand_parent: Teste de desenvolvimento
nav_order: 14
---

# JaCoCo 🪲

<center>
    <iframe src="https://vvs.rpmhub.dev/jacoco/slides/index.html#/"
    title="JaCoCo"
    width="90%" height="500" style="border:none;">
    </iframe>
</center>

Os slides acima apresentam a cobertura de código com o JaCoCo num projeto
Quarkus. Nas seções a seguir, a mesma ideia aparece com mais calma e, nos
exercícios, você acende o relatório de um `FareService` aos poucos.
{: .fs-3 }

## O besouro-lanterna

Imagine o código como um museu de vidro, à noite. Cada método é uma sala.
Cada `if` é uma porta. Os testes são os visitantes: eles entram, atravessam
um corredor e saem.
{: .fs-3 }

O [JaCoCo](https://www.jacoco.org/jacoco/) (*Java Code Coverage*) é o
besouro-lanterna que vai atrás desses visitantes. Onde alguém pisou, o chão
acende. O relatório é o mapa dessa luz.
{: .fs-3 }

- Sala **verde**: o método foi percorrido por inteiro.
- Porta **âmbar**: alguém entrou na sala, mas deixou uma porta fechada.
- Sala **escura**: nenhum teste chegou lá.
{: .fs-3 }

Esse é o argumento do slide **O besouro-lanterna**. A moral fica para o
fim da página: acender todas as salas não prova que o museu está sem
vazamento. Cobertura mede o que foi executado. Quem diz se o resultado
está certo é o `assert`.
{: .fs-3 }

## O que o besouro acende

O relatório HTML mostra vários contadores. Você não precisa decorar a
fórmula de cada um. Precisa saber o que está olhando. O slide **O que ele
mede** resume os cinco que mais aparecem.
{: .fs-3 }

- **Instrução.** A menor marca no chão: uma instrução de bytecode. Várias
  instruções cabem numa única linha de Java.
- **Linha.** O corredor. Uma linha conta como coberta quando pelo menos uma
  instrução dela rodou.
- **Ramo.** Cada porta. Um `if`, um `else` e cada saída de um `switch` são
  ramos. O losango desenhado na linha é esse contador.
- **Método.** A sala. Entra na conta quando o método é chamado.
- **Classe.** A ala. Entra na conta quando pelo menos um método dela roda.
{: .fs-3 }

Há ainda a coluna de complexidade (ciclomática): um número de caminhos
independentes. Ela ajuda a perceber um método cheio de portas. Não é a
meta dos exercícios desta página.
{: .fs-3 }

### Linha e ramo não são a mesma coisa

A linha do `if` acende assim que a condição é avaliada, mesmo que só um
lado tenha sido tomado. O ramo só fica verde quando as duas portas abrem.
Por isso a cobertura de linhas pode parecer boa enquanto a de ramos ainda
está pela metade. O slide **O losango âmbar** mostra o caso do frete:
{: .fs-3 }

```java
if (weightKg <= 5) {
    return baseFare();
}
return baseFare() + 1500;
```

Um teste com 2 kg executa a linha do `if` e o primeiro `return`. O losango
continua âmbar, porque ninguém passou pela porta do pacote pesado.
{: .fs-3 }

## Como ler as cores

Abra o `index.html` do relatório e entre na classe. O JaCoCo pinta o fonte
assim (slide **As cores do relatório**):
{: .fs-3 }

- **Verde:** tudo o que está naquela linha foi executado.
- **Amarelo:** a linha rodou, mas algum ramo dela ficou de fora. O losango
  ao lado da linha é a porta ainda fechada.
- **Vermelho:** nenhum teste passou por ali.
{: .fs-3 }

No topo da página, as barras repetem os contadores da seção anterior. Use
a de ramos quando quiser saber se as portas foram abertas, e a de linhas
quando quiser uma leitura rápida dos corredores.
{: .fs-3 }

## Por que a extensão, e não o plugin sozinho

Em muitos projetos Maven o JaCoCo entra como `jacoco-maven-plugin`, com os
goals `prepare-agent` e `report`. Numa aplicação Quarkus esse caminho
sozinho não enxerga os testes `@QuarkusTest`: o framework carrega as
classes com o próprio classloader, e o agente do plugin não acompanha essa
carga.
{: .fs-3 }

A extensão [`quarkus-jacoco`](https://quarkus.io/guides/tests-with-coverage/)
faz esse trabalho. Ela prepara o agente, grava a execução e gera o HTML.
O slide **Por que a extensão no Quarkus?** é essa ideia em três frases.
{: .fs-3 }

Não ligue a extensão e o `jacoco-maven-plugin` ao mesmo tempo sem a
configuração da nota lá embaixo. Os dois instrumentam a mesma classe, e o
teste quebra com erro de classe já instrumentada.
{: .fs-3 }

## Ligando o besouro no projeto

No `pom.xml`, a dependência fica no escopo de teste. A versão vem do BOM
do Quarkus, então não é preciso declará-la. O slide **Como ligar** traz o
mesmo bloco.
{: .fs-3 }

```xml
<dependency>
    <groupId>io.quarkus</groupId>
    <artifactId>quarkus-jacoco</artifactId>
    <scope>test</scope>
</dependency>
```

Gere o relatório com o ciclo que executa os testes:
{: .fs-3 }

```bash
./mvnw verify
```

### O relatório

Dois arquivos importam:
{: .fs-3 }

- `target/jacoco-report/index.html` é o mapa. Abra no navegador.
- `target/jacoco-quarkus.exec` são os dados brutos. O HTML é montado a
  partir dele.
{: .fs-3 }

O diretório do HTML é o valor padrão de `quarkus.jacoco.report-location`.
Se você mudar essa propriedade, o `index.html` passa a nascer no caminho
novo. O slide **Onde fica o relatório** lista os dois arquivos.
{: .fs-3 }

## Salas que não precisam de visita

Um DTO, um código gerado ou uma classe só de configuração acendem pouco e
dizem pouco sobre a regra de negócio. Dá para tirá-los do mapa com
`quarkus.jacoco.excludes`, em `src/main/resources/application.properties`.
O padrão segue o do guia do Quarkus: `*` e `?` funcionam como curinga.
{: .fs-3 }

```properties
quarkus.jacoco.excludes=**/dto/**/*
```

`**/dto/**/*` deixa de fora as classes do pacote `dto` e dos pacotes
abaixo dele. `quarkus.jacoco.includes` faz o contrário: quando está
ausente, tudo entra. O slide **Salas que o besouro pode ignorar** é esse
recorte.
{: .fs-3 }

## Luz acesa não é museu sem vazamento

Três limites valem mais do que perseguir 100% (slide **Luz acesa não é
museu sem vazamento**):
{: .fs-3 }

- Cobertura alta com assert fraco só prova que o código rodou. Um teste
  que chama `quote(2.0)` e não confere o valor devolvido acende a sala e
  deixa o vazamento quieto.
- Cobrir cada ramo de um método enorme é sinal de que o método tem portas
  demais, não de que a meta é 100%.
- O modo nativo do Quarkus não gera esse relatório. A cobertura desta
  página é a dos testes na JVM.
{: .fs-3 }

---

**Testes que não sobem o Quarkus.** A extensão, sozinha, cobre testes
`@QuarkusTest`. Um teste JUnit puro, sem essa anotação, precisa do
`jacoco-maven-plugin` apontando para o mesmo arquivo de dados e ignorando
o classloader do Quarkus. Só use os dois juntos com essa configuração, como
no [guia de cobertura do Quarkus](https://quarkus.io/guides/tests-with-coverage/):
{: .fs-3 }

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.12</version>
    <executions>
        <execution>
            <id>default-prepare-agent</id>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
            <configuration>
                <exclClassLoaders>*QuarkusClassLoader</exclClassLoaders>
                <destFile>${project.build.directory}/jacoco-quarkus.exec</destFile>
                <append>true</append>
            </configuration>
        </execution>
    </executions>
</plugin>
```

Os exercícios abaixo usam só `@QuarkusTest`. A dependência da extensão
basta.
{: .fs-3 }

---

## Exercícios práticos: a tarifa de frete

Você vai criar um projeto Quarkus e observar o `FareService` acender no
relatório. Os exercícios vão do mais simples ao mais exigente. Resolva na
ordem. Depois de cada um, rode `./mvnw verify`, abra
`target/jacoco-report/index.html` e só avance quando o que o enunciado
pede estiver visível no mapa. O slide **Exercícios, um corredor por vez**
é a lista curta.
{: .fs-3 }

### Preparação do projeto

Crie um projeto Quarkus do zero. Não é necessário clonar repositório.
{: .fs-3 }

```bash
mvn io.quarkus.platform:quarkus-maven-plugin:3.15.1:create \
    -DprojectGroupId=dev.ifrs.jacoco \
    -DprojectArtifactId=fare-jacoco \
    -DclassName="dev.ifrs.jacoco.GreetingResource" \
    -Dextensions="resteasy-reactive"
cd fare-jacoco
code .
```

O projeto gerado já traz um `GreetingResource` e um teste que o cobre.
Essa sala começa verde. O trabalho é com o frete.
{: .fs-3 }

Copie as três classes abaixo.
{: .fs-3 }

```java
package dev.ifrs.jacoco;

public class InvalidWeightException extends RuntimeException {

    public InvalidWeightException(String message) {
        super(message);
    }
}
```

```java
package dev.ifrs.jacoco;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FareService {

    public int baseFare() {
        return 1000;
    }

    public int quote(double weightKg) {
        if (weightKg <= 0) {
            throw new InvalidWeightException(
                    "O peso precisa ser maior que zero.");
        }
        if (weightKg <= 5) {
            return baseFare();
        }
        return baseFare() + 1500;
    }
}
```

```java
package dev.ifrs.jacoco.dto;

public class FareRequest {

    public double weightKg;

    public String summary() {
        return "peso=" + weightKg;
    }
}
```

`FareRequest` é um DTO de propósito. Nenhum teste deve chamá-lo. Ele existe
para o exercício 5.
{: .fs-3 }

### Exercício 1: achar a sala escura
{: .fw-500 }

Adicione `quarkus-jacoco` ao `pom.xml`, no escopo `test`, como na seção
**Ligando o besouro no projeto**. Rode `./mvnw verify` e abra
`target/jacoco-report/index.html`. Localize `FareService`: `baseFare` e
`quote` devem estar vermelhos, porque nenhum teste os chamou.
{: .fs-3 }

### Exercício 2: acender um método inteiro
{: .fw-500 }

Crie `src/test/java/dev/ifrs/jacoco/FareServiceTest.java` com um
`@QuarkusTest` que injeta `FareService` e confere `baseFare()`.
{: .fs-3 }

```java
package dev.ifrs.jacoco;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class FareServiceTest {

    @Inject
    FareService service;

    @Test
    public void shouldReturnBaseFare() {
        assertEquals(1000, service.baseFare());
    }
}
```

Rode `./mvnw verify` de novo. No relatório, `baseFare` fica verde: a sala
não tem porta, então uma visita basta. `quote` continua vermelho.
{: .fs-3 }

### Exercício 3: o losango âmbar
{: .fw-500 }

Acrescente um teste para o pacote leve e pare para olhar o mapa antes de
cobrir o pacote pesado.
{: .fs-3 }

```java
@Test
public void shouldChargeBaseFareForLightPackage() {
    assertEquals(1000, service.quote(2.0));
}
```

No relatório, a linha `if (weightKg <= 5)` fica amarela e o losango, âmbar:
só a porta do peso até 5 kg abriu. Anote o percentual de linhas e o de
ramos de `quote`. Você usa esses dois números no exercício 6.
{: .fs-3 }

Agora acrescente o pacote pesado.
{: .fs-3 }

```java
@Test
public void shouldChargeExtraForHeavyPackage() {
    assertEquals(2500, service.quote(8.0));
}
```

O losango de `weightKg <= 5` fica verde. O de `weightKg <= 0` continua
âmbar: os dois testes passaram pela condição, mas nenhum entrou no
`throw`.
{: .fs-3 }

### Exercício 4: a porta da exceção
{: .fw-500 }

Cubra o peso que não é positivo. Importe `assertThrows` de
`org.junit.jupiter.api.Assertions`.
{: .fs-3 }

```java
@Test
public void shouldRejectNonPositiveWeight() {
    assertThrows(InvalidWeightException.class, () -> service.quote(0));
}
```

Os dois losangos de `quote` ficam verdes. A sala do frete foi percorrida
por inteiro, e cada teste ainda confere o resultado ou a exceção.
{: .fs-3 }

### Exercício 5: fechar o depósito
{: .fw-500 }

`FareRequest.summary` aparece no relatório, vermelho. Essa classe não é
regra de negócio. Em `src/main/resources/application.properties`, exclua o
pacote e rode `./mvnw verify` outra vez.
{: .fs-3 }

```properties
quarkus.jacoco.excludes=**/dto/**/*
```

`FareRequest` sai do relatório. `FareService` permanece, com a cobertura
que você construiu nos exercícios anteriores.
{: .fs-3 }

### Exercício 6: uma frase sobre linha e ramo
{: .fw-500 }

Sem escrever código novo, volte aos percentuais que você anotou no
exercício 3, depois só do teste com 2 kg. Escreva uma frase dizendo por
que a cobertura de linhas de `quote` e a de ramos não coincidem. A pista
está na seção **Linha e ramo não são a mesma coisa**: a linha do `if` já
conta como executada quando uma única porta abre.
{: .fs-3 }

## Teste seus conhecimentos 🧠

Revise o texto e os slides e responda às questões teóricas abaixo.
{: .fs-3 }

<center>
    <iframe src="https://vvs.rpmhub.dev/jacoco/slides/questions.html"
        title="Questões sobre JaCoCo"
        width="90%" height="500"
        style="border:none;">
    </iframe>
</center>
{: .fs-3 }

## Referências

* JaCoCo. Documentação. Disponível em: [https://www.jacoco.org/jacoco/trunk/doc/](https://www.jacoco.org/jacoco/trunk/doc/).
* Quarkus. Measuring the coverage of your tests. Disponível em: [https://quarkus.io/guides/tests-with-coverage/](https://quarkus.io/guides/tests-with-coverage/).
{: .fs-3 }

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
