# Configurações

Essa página tem o objetivo de orientar sobre a instalação das ferramentas que serão utilizadas na disciplina de Verificação e Validação de Sistemas.

## Java

Os exemplos e ferramentas de teste da disciplina serão demonstrados em projetos escritos em Java. Dessa forma, o primeiro passo é instalar uma máquina virtual Java. Se você estiver utilizando Linux instale o [OpenJDK](https://openjdk.java.net):

    apt install default-jre

Se você estiver usando windows uma dica é utilizar o [Chocolatey](https://chocolatey.org) e instalar o openjdk:

    choco install openjdk

No maxOS, o openjdk pode ser instalado com o [HomeBrew](https://brew.sh):

    brew install openjdk

Verifique se o Java foi instalado corretamente por meio da instrução `java -version`.

## Apache Maven

O [Apache Maven](https://maven.apache.org), ou maven, é uma ferramenta de automação de projetos que permite acelerar diversos processos de desenvolvimento de um sistema, como por exemplo: compilação, empacotamento, instalação, verificação, teste, entre outros. Assim, essa ferramenta será fundamental no decorrer da disciplina uma vez que irá possibilitar uma automação e, consequentemente, a construção de integração contínua (*Continuous Integration* - CI) em projetos Java. No Ubunto, instale o maven por meio do comando:

    apt install maven

No windows: 

    choco install maven

macOS:

    brew install maven

Para verificar se o maven foi instalado com sucesso execute o comando `mvn -version`.

## Git

O git também necessita ser instalado uma vez que todos os exemplos da disciplina estão disponíveis no Github. Assim, no Ubuntu instale o Git por meio do comando:

    apt install git

Windows:

    choco install git.install

macOS:
    
    brew install git

Para ter certeza que o git esteja instalado corretamente digite `git --version`.

## Docker

Em um determinado momento da disciplina será necessário instalar o [Docker](https://docs.docker.com/get-docker/) na sua máquina.
Depois de instalar o Docker, para testar podemos baixar uma imagem do Ubuntu por meio do comando [pull](https://docs.docker.com/engine/reference/commandline/pull/):

    docker pull ubuntu

Para verificar se a imagem está disponível na sua máquina, digite a instrução [images](https://docs.docker.com/engine/reference/commandline/images/):

    docker images

Como resultado, o Docker listará as imagens disponíveis na sua máquina, como por exemplo:

```shell
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
ubuntu              latest              4e2eef94cd6b        2 weeks ago         73.9MB
```

Assim, para criar um container do Ubuntu a partir da imagem que foi baixada, execute o comando [run](https://docs.docker.com/engine/reference/commandline/run/):

    docker run --name vvs -it -p 127.0.0.1:80:9080 ubuntu

**Fique atento:** O comando `run` cria e coloca um container em execução.

O argumento `-name vvs` permite atribuir nome para o container, por sua vez, o argumento `-it`, possibilita entrar num "modo interativo" do container. Já o argumento `-p` indica uma porta que será aberta pelo container. No exemplo acima, poderemos acessar o container através do ip `127.0.0.1` (localhost) na porta `80`, assim, quando o container receber uma requisição na porta `80` ela será internamente direcionada para a porta `9080`.

Depois de executar o comando acima, você entrará como root em um terminal do Ubuntu. Para sair do terminal basta que você digite a instrução `exit`. Quando você digitar `exit` no terminal do Ubuntu o Docker irá parar ([stop](https://docs.docker.com/engine/reference/commandline/stop/)) a execução do seu container. Assim, para colocar novamente o container em execução use o comando [start](https://docs.docker.com/engine/reference/commandline/start/) da seguinte maneira:

    docker start -i vvs

Note que o argumento `-i` do comando `start` que permite entrar no terminal do Ubuntu. Para saber quais os containers que estão rodando na sua máquina utilize o [ps](https://docs.docker.com/engine/reference/commandline/ps/):

    docker ps

### Alguns comandos úteis do docker

Para todos os containers em execução:

    docker stop $(docker ps -a -q)

Exclui todos os containers:

     docker rm $(docker ps -a -q)

Apaga todas as imagens:

    docker rmi $(docker images -q)

Eliminar todos os volumes. Uma explicação rápida, no Docker, um volume maneira um diretório entre o host e o container, esse conceito será melhor explorado quando utilizarmos uma composição de container com a ferramenta [docker-compose](https://docs.docker.com/compose/).

    docker volume rm $(docker volume ls -q)

___
**Para saber mais:** Todos os comandos relacionados com o Docker podem ser utilizados por meio de ferramentas com interface de usuário gráfica. O Docker possui um [dashboard](https://docs.docker.com/desktop/dashboard/) para Windows e Mac que facilita bastante a tarefa de trabalhar com imagens e containers do Docker. Outra dica de ferramenta é a [extensão](https://marketplace.visualstudio.com/items?itemName=ms-azuretools.vscode-docker) do Docker para [VS Code](https://code.visualstudio.com) feita pela Microsoft. A extensão possui todas as funcionalidades do dashboard com a vantagem de poder ser utilizadas em todos os sistemas operacionais (Windows, Linux e Mac) e estar integrada a um ambiente de desenvolvimento.

---

## Testando o ambiente com o projeto TPACK

Depois de instalar todas as ferramentas, podemos fazer um teste para verificar se já podemos compilar, empacotar e executar um projeto java:. Primeiro, faça um [clone](https://git-scm.com/docs/git-clone) do projeto Tpack que servirá como um dos exemplos da disciplina:

    git clone https://github.com/rodrigoprestesmachado/tpack
    cd tpack

---
**Para saber mais:** O Tpack é um projeto simples que implementa um questionário sobre o conhecimento de conteúdo, pedagógico e tecnológico de professores. As tecnologias que esse projeto utiliza são: [Micro serviço](https://microprofile.io) em Java rodando em cima do [Open Liberty](https://openliberty.io), interface Web/PWA em [Vue](https://vuejs.org) [Typescript](https://www.typescriptlang.org) e banco de dados [MySQL](https://www.mysql.com).

---

Para compilar e empacotar o projeto, ou seja, criar um arquivo .jar/.war, execute os [plugins](https://maven.apache.org/plugins/index.html) `clean` e `package` do Maven no mesmo diretório que se encontra o arquivo arquivo `pom.xml`:

    mvn clean package

**Fique atento:** o maven necessita baixar todas as dependências do projeto, assim, a primeira compilação pode ser demorada.

Se tudo ocorrer bem, o maven irá criar uma diretório chamado `target`. Dentro desse diretório existirá um arquivo chamado `tpack.jar` que poderá ser executado da seguinte forma:

    java -jar target/tpack.jar

Como não colocamos um MySQL em execução, nesse primeiro momento não conseguiremos ver o sistema Tpack em sua plenitude. Apesar disso, podemos verificar se Web Service do TPACK está rodando, nesse sentido abra e digite a seguinte URL no seu navegador:

   [http://localhost/openapi/ui/](http://localhost/openapi/ui/)

Se você conseguir visualizar uma aplicação chamada Swagger UI, então significa que você conseguiu compilar e rodar o projeto.

## Referências

JEFERSON FERNANDO NORONHA VITALINO, Marcus André Nunes Castro. [Descomplicando o Docker](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5033249&acesso=aHR0cHM6Ly9taWRkbGV3YXJlLWJ2LmFtNC5jb20uYnIvU1NPL2lmcnMvOTc4ODU3NDUyOTAyOA==&label=acesso%20restrito) - 2ª Edição. Editora Brasport 152, cap. 1, ISBN 9788574529028.

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">Creative Commons Atribuição 4.0 Internacional</a>
</center>