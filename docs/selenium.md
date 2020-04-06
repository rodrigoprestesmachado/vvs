# Selenium IDE e Side Runner

## Criação de testes com o Selenium IDE

* Acesse: https://www.seleniumhq.org/selenium-ide/
* Instale o plugin do Selenium para Chrome ou Firefox
* No Chrome o Selenium será disponibilizado como um ícone no final da barra de endereço, como mostra a Figura 1

![Acesso ao Selenium IDE no Chrome](https://github.com/rodrigoprestesmachado/vvs/blob/master/selenium/img/seleniumExtension.png)
 
Figura 1 - Acesso ao Selenium IDE no Chrome

* Depois de acessar, escolha a opção “Record a new test in a new Project” para criar um caso de teste
* Informe um nome para o projeto, como exemplo, informe “IFRS”
* Logo, informe a URL do sistema que será testado, por exemplo "http://www.poa.ifrs.edu.br
* Como botão de gravação ligado, navegue pela página no IFRS até chegar no curso de sistemas para internet
* Utilizando o comando `assert text` verifique se a página do curso possui a informação de "Carga horária"
* Logo, atribua e salve o caso de teste com a extensão `.side`. Um exemplo, de um caso de teste para a página do curso de sistemas para a internet pode ser encontrado [aqui](https://github.com/rodrigoprestesmachado/vvs/blob/master/selenium/ifrs.side)

## Instalação do Selenium-side-runner

O Selenium Side Runner é uma aplicação que permite chamar a execução de um navegador na linha de comando e, assim, executar casos de testes no formato `.side`

### Instalação do nodejs no Ubuntu
`sudo apt update`

`sudo apt install nodejs`
 
### Instalação do npm no Ubuntu
`sudo apt install npm`

### Instalação do Selenium Side Runner no Ubuntu
`sudo npm install -g selenium-side-runner`

### Instalação do driver para Firefox ou Chrome
Firefox: `sudo npm install -g geckodriver`

Chrome: `sudo npm install -g chromedriver`

### Finalmente para executar o Side Runner no Firefox ou Chrome
`selenium-side-runner -c "browserName=firefox"` [ifrs.side](https://github.com/rodrigoprestesmachado/vvs/blob/master/selenium/code/ifrs.side)

`selenium-side-runner -c "browserName=chrome"` [ifrs.side](https://github.com/rodrigoprestesmachado/vvs/blob/master/selenium/code/ifrs.side)

**NOTA:** não execute o selenium-side-runner com o sudo!

## Selenium e Junit

Para rodar casos/suíte de teste do Selenium por intermédio do Junit, temos que primeiro exportar o teste da IDE, conforme ilustra os passos um e dois da imagem abaixo:

![](https://github.com/rodrigoprestesmachado/vvs/blob/master/selenium/img/SeleniumJunitExport.png)

Logo, escolha a linguagem “Junit” para poder obter um código em Java.

![](https://github.com/rodrigoprestesmachado/vvs/blob/master/selenium/img/SeleniumJunit.png)

Agora, temos que compilar a classe para que possamos executar o código. A classe que obtivemos da IDE possui duas dependências: (1) Junit 4 e (2) Selenium Java. O Junit 4 pode ser obtido no repositório do [Maven](https://mvnrepository.com) em [Junit 4](https://mvnrepository.com/artifact/junit/junit) e o [Selenium Java](https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java).

Apesar de existir nas IDE ferramentas gráficas, pode-se adicionar as dependências de maneira manual no `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>junit</groupId>
  	<artifactId>junit</artifactId>
  	<version>4.13-rc-1</version>
    </dependency>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
  	<artifactId>selenium-java</artifactId>
  	<version>4.0.0-alpha-3</version>
    </dependency>
</dependencies>
```

Por padrão, o código em Java utiliza o Chrome Driver para executar os testes. Assim, certifique-se que o Chrome e o Chrome Driver estão instalados. Instruções de como instalar o Chrome Driver podem ser obtidas em [ChromeDriver](https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver).

Como exemplo, o código Java gerado para [submarino.side](https://github.com/rodrigoprestesmachado/vvs/blob/master/selenium/code/submarino.side) pode ser observado no arquivo [IPhone11.java](https://github.com/rodrigoprestesmachado/vvs/blob/master/selenium/code/IPhone11Test.java). Um projeto do Java no Eclipse já configurado para rodar testes do Selenium com o Junit pode ser baixado no [repositório](https://github.com/rodrigoprestesmachado/vvs/tree/master/selenium/code/SeleniumJunit).

Para executar o projeto do Eclipse na linha de comando, siga o tutorial do [Junit](https://github.com/rodrigoprestesmachado/vvs/wiki/Junit)