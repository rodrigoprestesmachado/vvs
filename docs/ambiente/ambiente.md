---
layout: default
title: Configuração do ambiente
nav_order: 2
---

# Configuração do ambiente

Essa página tem o objetivo de orientar sobre a instalação das ferramentas que
serão utilizadas na disciplina de Verificação e Validação de Sistemas.

## Java ♨️

Antes de iniciarmos, se faz necessário verificar se você possui uma JVM
(*Java Virtual Maquine*) e um JDK (*Java Development Kit*) 11 ou superior
instalado na sua máquina:

    java -version

Para verificar se o JDK que está instalado digite:

    javac -version

Se os comandos acima retornarem a versão do Java e do compilador, significa que
tem que a sua máquina possui o primeiro requisito para rodar o Quarkus.

Porém, se você não tiver o Java instalado na sua máquina, recomendo que você
utilize o gerenciador de SDKs [SDKMAN](https://sdkman.io) para instalar o Java.

Para instalá-lo, abra um terminal e copie e cole o seguinte comando:

    curl -s "https://get.sdkman.io" | bash

Com o SDKMAN instalado, utilize o seguinte comando para instalar o Java:

    sdk install java

O SDKMAN é uma ferramenta que permite a instalação de diversas versões do Java,
tais como: OpenJDK, Oracle JDK, Temurin, GraalVM, etc. Assim, a grande vantagem
de se utilizar o SDKMAN é que ele alterar a versão do Java que está sendo
utilizada na sua máquina de maneira descomplicada. Para saber mais sobre o
SDKMAN, por favor, consulte a [documentação](https://sdkman.io) da ferramenta.

## Maven 🌐

O Maven é uma ferramenta de automação de compilação utilizada principalmente
para projetos Java. Ele é utilizado para gerar um artefato (geralmente um
arquivo `.jar` ou `.war`) a partir do código fonte do projeto. Além disso,
o Maven gerencia as dependências do projeto, o ciclo de vida do *build*, teste e
a até mesmo a distribuição do projeto.

Para instalar o Maven utilizando o SDKMAN, digite o seguinte comando:

    sdk install maven

## Git 🐙

O git também necessita ser instalado uma vez que todos os exemplos da disciplina
 estão disponíveis no Github. Assim, no Ubuntu instale o Git por meio do
 comando:

    apt install git

Windows:

    choco install git.install

macOS:

    brew install git

Para ter certeza que o git esteja instalado corretamente digite `git --version`.

## Docker 🐳

Em um determinado momento da disciplina será necessário instalar o
[Docker](https://docs.docker.com/get-docker/) na sua máquina.
Depois de instalar o Docker, para testar podemos baixar uma imagem do Ubuntu por
meio do comando [pull](https://docs.docker.com/engine/reference/commandline/pull/):

    docker pull ubuntu

Para verificar se a imagem está disponível na sua máquina, digite a instrução
[images](https://docs.docker.com/engine/reference/commandline/images/):

    docker images

Como resultado, o Docker listará as imagens disponíveis na sua máquina, como
por exemplo:

```shell
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
ubuntu              latest              4e2eef94cd6b        2 weeks ago         73.9MB
```

Assim, para criar um container do Ubuntu a partir da imagem que foi baixada,
execute o comando
[run](https://docs.docker.com/engine/reference/commandline/run/):

    docker run --name vvs -it -p 127.0.0.1:80:9080 ubuntu

**Fique atento:** O comando `run` cria e coloca um container em execução.

O argumento `-name vvs` permite atribuir nome para o container, por sua vez, o
argumento `-it`, possibilita entrar num "modo interativo" do container. Já o
argumento `-p` indica uma porta que será aberta pelo container. No exemplo
acima, poderemos acessar o container através do ip `127.0.0.1` (localhost) na
porta `80`, assim, quando o container receber uma requisição na porta `80` ela
será internamente direcionada para a porta `9080`.

Depois de executar o comando acima, você entrará como root em um terminal do
Ubuntu. Para sair do terminal basta que você digite a instrução `exit`. Quando
você digitar `exit` no terminal do Ubuntu o Docker irá parar o container. Para
([stop](https://docs.docker.com/engine/reference/commandline/stop/)) a execução
 do seu container. Assim, para colocar novamente o container em execução use o
 comando [start](https://docs.docker.com/engine/reference/commandline/start/)
 da seguinte maneira:

    docker start -i vvs

Note que o argumento `-i` do comando `start` que permite entrar no terminal do
Ubuntu. Para saber quais os containers que estão rodando na sua máquina utilize
o [ps](https://docs.docker.com/engine/reference/commandline/ps/):

    docker ps

### Alguns comandos úteis do docker 📜

Para todos os containers em execução:

    docker stop $(docker ps -a -q)

Exclui todos os containers:

     docker rm $(docker ps -a -q)

Apaga todas as imagens:

    docker rmi $(docker images -q)

Eliminar todos os volumes. Uma explicação rápida, no Docker, um volume maneira
um diretório entre o host e o container, esse conceito será melhor explorado
quando utilizarmos uma composição de container com a ferramenta
 [docker-compose](https://docs.docker.com/compose/).

    docker volume rm $(docker volume ls -q)

___
**Para saber mais:** Todos os comandos relacionados com o Docker podem ser
utilizados por meio de ferramentas com interface de usuário gráfica. O Docker
possui um [dashboard](https://docs.docker.com/desktop/dashboard/) para Window
e Mac que facilita bastante a tarefa de trabalhar com imagens e containers do
Docker. Outra dica de ferramenta é a
[extensão](https://marketplace.visualstudio.com/items?itemName=ms-azuretools.vscode-docker)
do Docker para [VS Code](https://code.visualstudio.com) feita pela Microsoft.
A extensão possui todas as funcionalidades do dashboard com a vantagem de poder
ser utilizadas em todos os sistemas operacionais (Windows, Linux e Mac) e estar
integrada a um ambiente de desenvolvimento.

---

## Referências 📚

JEFERSON FERNANDO NORONHA VITALINO, Marcus André Nunes Castro. [Descomplicando o Docker](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5033249&acesso=aHR0cHM6Ly9taWRkbGV3YXJlLWJ2LmFtNC5jb20uYnIvU1NPL2lmcnMvOTc4ODU3NDUyOTAyOA==&label=acesso%20restrito) - 2ª Edição. Editora Brasport 152, cap. 1, ISBN 9788574529028.

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>