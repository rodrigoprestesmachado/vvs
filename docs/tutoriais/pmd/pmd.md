
# Verificação estática

Antes de iniciarmos o assunto sobre verificação estática, iremos conhecer o conceito de *Fork* existente em sistemas de versionamento de código. Uma vez entendido o funciona de um *Fork*, no final desse documento iremos apresentar um exemplo onde iremos utilizar o *Fork* para poder verificar estaticamente um código.

## GitHub Fork

No Github, você pode utilizar o projeto de outra pessoa para estudar, contribuir ou utilizar como ponto de partida para o desenvolvimento de um novo projeto. Para isso, usamos a ferramenta *Fork* para criar uma cópia do projeto de outra pessoa. Iremos utilizar o *Fork* do GitHub para que vocês possam realizar os testes e encontrar erros nos repositórios de exemplo da disciplina. No GitHub, a funcionalidade de *Fork* (bifurcação) se localiza no canto direto superior da página de um repositório, observe o item 1 da figura 1.

Depois de criar um *Fork* de um repositório você deve fazer um `git clone` do projeto para a sua máquina para poder trabalhar com os arquivos. Uma vez criado, o clone permite que você altere os arquivos no editor de sua preferência (ex.: vscode) e versione o código por meio das operações [commit](https://docs.github.com/pt/desktop/contributing-and-collaborating-using-github-desktop/committing-and-reviewing-changes-to-your-project) (alterações no código localmente) e [push]( https://docs.github.com/pt/desktop/contributing-and-collaborating-using-github-desktop/pushing-changes-to-github) (enviar os commits para o repositório remoto).

<center>
<img src="imgs/fork.png" width="70%" height="70%">

Figura 1 - Interface Web do GitHub
</center>

Depois de fazer alterações nos códigos do projeto, você poderá solicitar que as mudanças sejam incorporadas no projeto original. Para fazer isso, temos que criar um *Pull Request*, veja a localização dessa funcionalidade no item 2 da figura 1.Depois de criar um *Pull Request*, o GitHub mostrará um banner avisando que você realizou uma Pull Request para o projeto original, conforme mostra o item 3 da figura 1.

Porém, para que as suas mudanças sejam incorporadas, o dono do projeto principal necessitará aceitar e, consequentemente fazer uma operação de [*merge*](https://git-scm.com/docs/git-merge) para unir a nova modificação no código principal, ou seja, aceitar ou não a requisição depende do dono do projeto original.

**Para saber:** Existe um [tutorial](https://guides.github.com/activities/forking/) do GitHub específico sobre esse assunto onde você poderá realizar um primeiro Fork de um projeto chamado [Spoon-Knife](https://github.com/octocat/Spoon-Knife) e verificar na prática o funcionamento desse recurso.

## PMD

Depois que você entendeu o conceito de *Fork*, iremos iniciar a trabalhar com a ideia de verificar estaticamente um código, ou seja, analisar um programa sem colocá-lo em execução. O [PMD](https://pmd.github.io) é uma ferramenta capaz de analisar códigos fonte normalmente escritos em Java. O PDM possui um conjunto de [regras](https://pmd.github.io/pmd-6.27.0/pmd_rules_java.html) de análise que são capazes de apurar desde o estilo do código até questões mais complexas como segurança e desempenho. Uma ferramenta como o PMD pode ser utilizada durante a fase de desenvolvimento e/ou fazer parte das etapas de construção e instalação de um sistema, assim, as próximas seções são dedicadas para mostrar o uso do PMD nesses dois contextos.

**Nota:** O PMD está sendo utilizado como um exemplo, existem muitas outras ferramentas capazes de fazer o mesmo tipo de análise, um exemplo disso é o [SonarLint](https://www.sonarlint.org), que também possui possui extensões para [Vscode](https://marketplace.visualstudio.com/items?itemName=SonarSource.sonarlint-vscode), Eclipse, IntelliJ, entre outros. Também cabe salientar que ferramentas de verificação de código estático não são uma exclusividade de Java, ou seja, em praticamente todas as linguagens você encontrará sistemas desse tipo.

## PMD no Vscode

O vídeo abaixo mostra um exemplo simples de como podemos configurar e também utilizar o PDM dentro do Vscode. O vídeo tem o foco em três pontos, são eles: (1) a criação de um projeto por meio dos plugins [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) e [Maven for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-maven) para VScode, (2) a configuração de regras para o PDM e o uso das regras dentro do VScode por intermédio de um plugin do [Apex PMD](https://marketplace.visualstudio.com/items?itemName=chuckjonas.apex-pmd).

<center>
    <iframe
    width="560" height="315"
    src="https://www.youtube.com/embed/ZgTtVUx6dcQ"
    frameborder="0"
    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
    allowfullscreen>
    </iframe>
</center>

## Regras do PMD

Como foi mostrado no vídeo, o PDM possui um conjunto grande de regras, os exemplos abaixo apresentam algumas que programadores consideraram interessantes e/ou úteis, são elas:

    Nota: as regras e explicações abaixo foram escritas de forma colaborativa, as referências e créditos estão disponibilizadas ao final de cada regra.

1. Best Practices
2. Code Style
3. Design
4. Documentation
5. Error Prone
6. Multithreading
7. Performance
8. Security
9. Additional rulesets

## PMD com o Maven

O PDM possui um [plugin](https://pmd.github.io/pmd-6.27.0/pmd_userdocs_tools_maven.html) para Maven, portanto, uma verificação estática pode ser incorporada dentro do processo de integração contínua, ou **seja**, podemos realizar uma análise do código até mesmo antes de compilarmos.

## Codebeat