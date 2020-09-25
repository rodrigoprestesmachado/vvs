
# Verificação estática

## GitHub Fork

No Github, você pode utilizar o projeto de outra pessoa para estudar, contribuir ou utilizar como ponto de partida para o desenvolvimento de um novo projeto. Para isso, usamos a ferramenta *Fork* para criar uma cópia do projeto de outra pessoa. Iremos utilizar o *Fork* do GitHub para que vocês possam realizar os testes e encontrar erros nos repositórios de exemplo da disciplina. No GitHub, a funcionalidade de *Fork* (bifurcação) se localiza no canto direto superior da página de um repositório, observe o item 1 da figura 1.

Depois de criar um *Fork* de um repositório você deve fazer um `git clone` do projeto para a sua máquina para poder trabalhar com os arquivos. Uma vez criado, o clone permite que você altere os arquivos no editor de sua preferência (ex.: vscode) e versione o código por meio das operações [commit](https://docs.github.com/pt/desktop/contributing-and-collaborating-using-github-desktop/committing-and-reviewing-changes-to-your-project) (alterações no código localmente) e [push]( https://docs.github.com/pt/desktop/contributing-and-collaborating-using-github-desktop/pushing-changes-to-github) (enviar os commits para o repositório remoto).

<center>
<img src="imgs/fork.png" width="80%" height="80%">

Figura 1 - Interface Web do GitHub
</center>

Depois de fazer alterações nos códigos do projeto, você poderá solicitar que as mudanças sejam incorporadas no projeto original. Para fazer isso, temos que criar um *Pull Request*, veja a localização dessa funcionalidade no item 2 da figura 1.Depois de criar um *Pull Request*, o GitHub mostrará um banner avisando que você realizou uma Pull Request para o projeto original, conforme mostra o item 3 da figura 1.

Porém, para que as suas mudanças sejam incorporadas, o dono do projeto principal necessitará aceitar e, consequentemente fazer uma operação de [*merge*](https://git-scm.com/docs/git-merge) para unir a nova modificação no código principal, ou seja, aceitar ou não a requisição depende do dono do projeto original.

Existe um [tutorial](https://guides.github.com/activities/forking/) do GitHub específico sobre esse assunto onde você poderá realizar um Fork de um projeto chamado [Spoon-Knife](https://github.com/octocat/Spoon-Knife) e verificar na prática o funcionamento desse recurso.

## PMD

O [PMD](https://pmd.github.io) é uma ferramenta capaz de analisar códigos fonte normalmente escritos em Java. O PDM possui um conjunto de [regras](https://pmd.github.io/pmd-6.27.0/pmd_rules_java.html) de verificação que são capazes de apurar desde o estilo do código até questões mais complexas como segurança e desempenho.

## Plugin do PDM para Maven

O PDM possui um [plugin](https://pmd.github.io/pmd-6.27.0/pmd_userdocs_tools_maven.html) 