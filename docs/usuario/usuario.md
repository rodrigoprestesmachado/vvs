---
layout: default
title: Teste de lançamento
parent: Teste de lançamento e integração contínua
nav_order: 19
---

# Teste de lançamento 🔍

<center>
<iframe src="https://vvs.rpmhub.dev/usuario/slides/index.html#/" title="Teste de lançamento e usuário" width="90%" height="500" style="border:none;"></iframe>
</center>

## Teste de lançamento

O teste de lançamento (release) é um processo de teste que deve ser realizado fora do time de desenvolvimento. Normalmente, esse tipo de teste é realizado com clientes, usuários do sistema ou outros times de desenvolvimento. O objetivo do teste de lançamento é conferir a validade para garantir que um sistema cumpra seus requisitos e seja bom o bastante para ser usado.

### Teste baseado em requisitos

O teste baseado em requisitos é uma abordagem sistemática para o projeto de casos de teste. Cada requisito deve derivar um conjunto de testes.

### Teste de cenário

Um cenário é uma história que descreve uma maneira como o sistema poderia ser usado. Deve ser realista, e os verdadeiros usuários sejam capazes de se relacionar com o cenário.

### Teste de desempenho

Normalmente esse tipo de teste aumenta a carga até o desempenho do sistema ficar inaceitável. Assim como outros tipos de teste, o de desempenho se preocupa tanto em mostrar que o sistema cumpre os requisitos quanto em descobrir problemas e defeitos no sistema. Muitas vezes, se faz necessário construir um perfil operacional, por exemplo, 50% das requisições do tipo A, 25% do tipo B e 25% do tipo C.

Uma maneira eficaz é testar fora dos limites do projeto do software, esse processo é conhecido como teste de estresse. Por exemplo, o sistema foi projetado para suportar 300 transações por segundo, assim, testa-se até 300 transações e depois acima desse limite.

O teste de estresse ajuda em duas coisas:

* Testar o comportamento de falha do sistema. Nessas circunstâncias, uma falha não pode, por exemplo, causar uma ruptura nos dados do sistema.
* Revelar defeitos que só aparecem quando o sistema está plenamente carregado, ou seja, mostrar combinações incomuns causadas pelo estresse.

## Teste de usuário

O teste de usuário é um estágio no processo de teste no qual os usuários fornecem entradas e conselhos sobre os testes do sistema. Pode ser caracterizado como um processo informal, no qual os usuários finais experimentam um novo produto. O teste de usuário é essencial, pois influências do ambiente do usuário podem ter um efeito importante na confiabilidade, desempenho, usabilidade e robustez do sistema.

Existem três tipos de testes com usuário:

* **Teste alfa**: um grupo selecionado de usuários trabalha em estreita colaboração com o time de desenvolvimento.
* **Teste beta**: uma versão do software é disponibilizada para um grupo maior de usuários.
* **Teste de aceitação**: os clientes testam junto com os desenvolvedores para decidir se o sistema está pronto ou não para entrar em produção.

## Referências 📚

SOMMERVILLE, Ian. [Engenharia de software](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5030950&acesso=aHR0cHM6Ly9taWRkbGV3YXJlLWJ2LmFtNC5jb20uYnIvU1NPL2lmcnMvOTc4ODU0MzAyNDk3NA==&label=acesso%20restrito), 10ª ed. Editora Pearson 768, cap. 8 ISBN 9788543024974.

SOMMERVILLE, Ian. [Slides](https://iansommerville.com/software-engineering-book/slides/) do capítulo 8 (em inglês).

SOMMERVILLE, Ian. [Vídeos](https://iansommerville.com/software-engineering-book/videos/imp/) do capítulo 8 (em inglês).

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>