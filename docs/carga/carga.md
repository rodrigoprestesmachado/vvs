---
layout: default
title: K6
nav_order: 11
---

# Teste de estresse: K6

## Sobre o K6

O [K6](https://k6.io/) é uma ferramenta de teste de carga de código aberto
construída para fazer testes de desempenho escaláveis, automatizados e de baixo
custo. Usando o K6, você pode testar a capacidade de resposta de seus back-ends
e APIs sob carga pesada.

## Instalação do K6 com snap

O K6 pode ser instalado com o [snap](https://snapcraft.io), para instalar o K6
execute o seguinte comando:

```bash
sudo snap install k6
```

Para mais informações sobre a instalação do K6, acesse a [documentação oficial](https://k6.io/docs/getting-started/installation).

## Exemplo

O exemplo a seguir é um teste de carga simples que simula 10 usuários visitando:

```javascript
// Exemplo do script do k6
import http from 'k6/http';
import { check, sleep } from 'k6';

export default function() {
  let res = http.get('https://www.example.com/');
  check(res, { 'status is 200': (r) => r.status === 200 });
  sleep(1);
}
```

Este _script_ faz uma solicitação HTTP GET para o site "example.com", verifica
se a resposta tem um status de 200 usando a função `check()`, e define um
intervalo de espera de 1 segundo entre as solicitações usando a função
`sleep()`. Você pode executar este _script_ com o k6 usando o comando `k6 run
script.js` no terminal.

## Executando o teste de carga

Para executar o teste de carga, execute o seguinte comando:

```bash
k6 run --vus 10 --duration 30s script.js
```

O comando acima executa o _script_ `script.js` com 10 usuários virtuais (VUs)
por 30 segundos. Para mais informações sobre a execução do K6, acesse a
[documentação oficial](https://k6.io/docs/getting-started/running-k6).

## Resultados do teste de carga

O K6 gera um relatório de teste de carga em HTML, para visualizar o relatório
gerado, execute o seguinte comando:

```bash
k6 run --out html=report.html script.js
```

O comando acima gera um relatório HTML chamado `report.html` com os resultados
do teste de carga. Para mais informações sobre os relatórios do K6, acesse a
[documentação oficial](https://k6.io/docs/getting-started/results-output).

## Exemplo com três etapas de carga:

O exemplo a seguir é um teste de carga que simula 10 usuários visitando o site
"example.com" em três etapas de carga:

```javascript
import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  stages: [
    { duration: '1m', target: 50 }, // ramp up to 50 users
    { duration: '2m', target: 50 }, // stay at 50 users
    { duration: '1m', target: 0 }, // ramp down to 0 users
  ],
  thresholds: {
    // 95% of requests must complete below 500ms
    http_req_duration: ['p(95)<500'],
    // 99% of static asset requests must complete below 200ms
    'http_req_duration{staticAsset:yes}': ['p(99)<200'],
    // 99% of dynamic requests must complete below 500ms
    'http_req_duration{staticAsset:no}': ['p(99)<500'],
    // at least 100 requests per second
    http_reqs: ['rate>100'],
  },
};

export default function() {
  let res = http.get('https://www.example.com/');
  check(res, { 'status is 200': (r) => r.status === 200 });

  sleep(Math.random() * 3); // random sleep between 0-3 seconds

  res = http.get('https://www.example.com/assets/styles.css');
  check(res, {
    'status is 200': (r) => r.status === 200,
    'content type is CSS': (r) => r.headers['Content-Type'] === 'text/css',
  });

  sleep(Math.random() * 2); // random sleep between 0-2 seconds
}
```

Este _script_ simula uma carga de usuários em um site, com três etapas distintas:
_ramp-up_, _stay at_ carga constante e _ramp-down_. O número de usuários virtuais
começa em 0 e aumenta gradualmente até 50 durante a primeira etapa. O teste
mantém 50 usuários virtuais durante a segunda etapa e, em seguida, diminui
gradualmente até 0 na última etapa.

As configurações do _thresholds_ incluem limites de tempo para a duração das
solicitações HTTP, bem como uma taxa mínima de solicitações por segundo. O teste
define três limiares de duração da solicitação HTTP, cada um com um tempo
máximo permitido de conclusão. Ele também verifica se as respostas HTTP têm o
status correto e se o tipo de conteúdo é o esperado.

O script também inclui a função `sleep()`, que adiciona um tempo de espera
aleatório entre as solicitações para simular o comportamento humano mais natural.

Este exemplo é apenas uma demonstração básica do que é possível com o k6.
Existem muitas outras funções e configurações que você pode usar para
personalizar seus testes de carga. O k6 tem uma documentação boa, então
sinta-se convidado para explorar mais e criar seus próprios testes de carga
personalizados.

# Referências bibliográficas

K6 - Load Testing Tool. Disponível em: <https://k6.io/>. Acesso em: 07 mai. 2023.

SOMMERVILLE, Ian. [Engenharia de software](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5030950&acesso=aHR0cHM6Ly9taWRkbGV3YXJlLWJ2LmFtNC5jb20uYnIvU1NPL2lmcnMvOTc4ODU0MzAyNDk3NA==&label=acesso%20restrito), 10ª ed. Editora Pearson 768, cap. 8 ISBN 9788543024974.

MACHADO, Rodrigo Prestes. [Desenvolvimento de software, v.3 programação de sistemas web orientada a objetos em Java](https://biblioteca.ifrs.edu.br/pergamum_ifrs/biblioteca_s/acesso_login.php?cod_acervo_acessibilidade=5020683&acesso=aHR0cHM6Ly9pbnRlZ3JhZGEubWluaGFiaWJsaW90ZWNhLmNvbS5ici9ib29rcy85Nzg4NTgyNjAzNzEw&label=acesso%20restrito). Porto Alegre Bookman 2016 (Tekne). ISBN 9788582603710.

<center>
<a href="https://github.com/rodrigoprestesmachado" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">Creative Commons Atribuição 4.0 Internacional</a>
</center>