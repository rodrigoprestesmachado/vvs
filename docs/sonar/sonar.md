# Inspeções estáticas: SonarLint 👓

O [SonarLint](https://www.sonarlint.org) é uma ferramenta de inspeção estática que auxilia um programador na identificação e correções de problemas de codificação. O [SonarLint](https://www.sonarlint.org) está disponível para diversas IDEs (_Integrated Development Environment_) como por exemplo: [VSCode](https://code.visualstudio.com), [Eclipse](https://www.eclipse.org/), [Intellij](https://www.jetbrains.com/pt-br/idea/), entre outros e, possui um conjunto grande de [regras](https://rules.sonarsource.com) para análise de código JavaScript, TypeScript, Python, Java, HTML, PHP, entre muitas outras. 😀

## SonarLint no VSCode 🖥️

Para instalar o SonarLint no VSCode, basta procurar o pacote no [Marketplace](https://marketplace.visualstudio.com/items?itemName=SonarSource.sonarlint-vscode) e clicar no botão `Install`. Alternativamente, você pode utilizar o próprio Visual Studio Code para instalar o SonarLint por meio das extensões, nesse caso, pesquise por SonarLint que você encontrará a extensão para VSCode, a imagem abaixo ilustra esse processo:

<center>
<a href="https://marketplace.visualstudio.com/items?itemName=SonarSource.sonarlint-vscode"><img src="imgs/sonar.png" alt="SonarLint no VS Code" width="50%" height="50%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
Figura 1 - Instalação do SonarLint no VSCode
</center>

Faça um teste para verificar se o SonarLint está configurado no seu VSCode. Primeiro, abra um terminal e digite o seguinte comando do Maven:

    mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4

O comando acima é utilizado para criarmos um projeto Java simples a partir do Maven. Durante a criação do projeto, o Maven irá perguntar sobre o [grupoId](https://maven.apache.org/guides/mini/guide-naming-conventions.html) (uma identificação do seu projeto dentro de todos projetos Maven) e o [artifactId](https://maven.apache.org/guides/mini/guide-naming-conventions.html) (o nome do seu .jar sem a versão). Depois de escolher o grupoId e o artifactId, o Maven irá criar um diretório, utilize o comando `code [nome do artifactId]` para abrir o projeto no VSCode.

⚠️ **Nota:** para fazer esse teste, se certifique que o VSCode esteja instalado com as extensões do Java, caso não esteja, assista o início desse [vídeo](https://www.youtube.com/embed/ZgTtVUx6dcQ).

Uma vez que você tenha aberto o seu projeto no VSCode, procure a classe `App.java` dentro do diretório `src` e adicione o seguinte método:

```java
public int foo(int a) {
  int b = 12;
  if (a == 1) {
    return b;
  }
  return b;  // Noncompliant
}
```

Se o SonarLint estiver rodando com sucesso, você verá um alerta conforme a figura abaixo: 👊

<center>
<img src="imgs/vscode.png" alt="SonarLint no VS Code" width="60%" height="60%" border=0 style="border:0; text-decoration:none; outline:none"><br/>
Figura 2 - SonarLint no VSCode
</center>

O alerta ocorreu pois o método `foo` possui dois retornos, e a regra [Methods returns should not be invariant](https://rules.sonarsource.com/java/type/Code%20Smell/RSPEC-3516) foi acionada.

O SonarLint possui um conjunto grande de [regras](https://rules.sonarsource.com/) para análise de código, que podem ser utilizadas para identificar problemas de codificação. No caso de Java, por exemplo, atualmente o SonarLint possui mais de 600 regras divididas entre as categorias vulnerabilidades, defeitos, segurança e boas práticas. ⬆️

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">Creative Commons Atribuição 4.0 Internacional</a>
</center>