---
layout: default
title: Teste de componente
parent: Teste de desenvolvimento
has_children: true
nav_order: 11
---

# Teste de componente

Como visto anteriormente, os teste de componente sucedem os testes unitários, ou seja, trata-se de uma etapa onde classes são combinadas a fim de formar módulos a serem testados. Um dos plugins do Maven mais famosos para executar testes de integração é o [Failsafe](https://maven.apache.org/surefire/maven-failsafe-plugin/). A principal diferença entre o [Failsafe](https://maven.apache.org/surefire/maven-failsafe-plugin/) e o [Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/) é que no primeiro, se um teste falhar o processo de construção do sistema (*build*) não será comprometido.

O plugin Failsafe tem apenas dois objetivos:

* failsafe:integration-test - executa os testes de integração de um aplicativo
* failsafe:verify - verifica se os testes de integração de um aplicativo foram aprovados

Assim, se o Failsave estiver configurado em um projeto Maven, basta executar, por exemplo, o comando `verify` para que o Failsave possa ser executado:

    mvn verify

## Referências

SOMMERVILLE, Ian. [Engenharia de software](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5030950&acesso=aHR0cHM6Ly9taWRkbGV3YXJlLWJ2LmFtNC5jb20uYnIvU1NPL2lmcnMvOTc4ODU0MzAyNDk3NA==&label=acesso%20restrito), 10ª ed. Editora Pearson 768, cap. 8 ISBN 9788543024974.

MACHADO, Rodrigo Prestes. [Desenvolvimento de software, v.3 programação de sistemas web orientada a objetos em Java](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5020683&acesso=aHR0cHM6Ly9pbnRlZ3JhZGEubWluaGFiaWJsaW90ZWNhLmNvbS5ici9ib29rcy85Nzg4NTgyNjAzNzEw&label=acesso%20restrito). Porto Alegre Bookman 2016 (Tekne). ISBN 9788582603710.

<center>
<a href="https://github.com/rodrigoprestesmachado" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>