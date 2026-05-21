---
layout: default
title: Playwright
parent: Teste de sistema
grand_parent: Teste de desenvolvimento
nav_order: 19
---

## 1. Visão geral

O [Playwright](https://playwright.dev) é uma ferramenta de teste de sistema
desenvolvida pela Microsoft que permite a construção de testes E2E
(_end-to-end_) com interface Web. Assim como o [Selenium](https://www.selenium.dev)
e o [Cypress](https://www.cypress.io), ele simula a interação de um usuário com
a aplicação no navegador.

### 1.1. Por que usar o Playwright?

| Característica | Descrição |
|---|---|
| Multi-navegador | Suporta Chromium (Chrome/Edge), Firefox e WebKit (Safari) com a mesma API |
| Multi-linguagem | JavaScript, TypeScript, Python, Java e .NET |
| Auto-wait | Aguarda automaticamente que elementos estejam prontos antes de agir |
| Execução paralela | Roda múltiplos testes simultaneamente |
| Recursos de debug | Trace viewer, modo UI, screenshots e gravação de vídeo |
| Codegen | Gera código automaticamente a partir da interação com a página |

### 1.2. Pré-requisitos

- [Node.js](https://nodejs.org) versão 18 ou superior
- Editor de código (recomendado: [VS Code](https://code.visualstudio.com))

---

## 2. Instalação

Para criar um novo projeto Playwright, execute:

```sh
mkdir yourproject
cd yourproject
npm init playwright@latest
```

O assistente fará algumas perguntas:

1. **TypeScript ou JavaScript?** (recomendado: TypeScript)
2. **Diretório de testes?** (padrão: `tests/`)
3. **Adicionar workflow do GitHub Actions?** (opcional)
4. **Instalar navegadores?** (recomendado: sim)

### 2.1. Estrutura gerada

Após a instalação, o projeto terá a seguinte estrutura:

```
yourproject/
├── tests/                    # seus testes ficam aqui
│   └── example.spec.ts
├── tests-examples/           # exemplos prontos
├── playwright.config.ts      # configuração principal
├── package.json
└── node_modules/
```

<center>
  <img src="img/playwright.png" alt="Estrutura do projeto Playwright" width="50%" height="50%" border=0 style="border:0; text-decoration:none; outline:none" /><br/>
  Figura 1 — Estrutura de arquivos gerada pelo Playwright
</center>

---

## 3. Primeiro teste

Um teste no Playwright é uma função `test()` declarada em um arquivo
`*.spec.ts` dentro do diretório `tests/`. Veja o exemplo mínimo:

```ts
import { test, expect } from '@playwright/test';

test('título da página inicial', async ({ page }) => {
  await page.goto('https://playwright.dev');
  await expect(page).toHaveTitle(/Playwright/);
});
```

### 3.1. Anatomia do teste

| Elemento | Função |
|---|---|
| `import { test, expect }` | Importa o runner e a função de assertiva |
| `test('nome', async ({ page }) => {...})` | Define um teste; `page` é a aba do navegador |
| `await` | Toda ação é assíncrona — sempre use `await` |
| `page.goto(url)` | Navega para uma URL |
| `expect(...).toHaveTitle(...)` | Assertiva: verifica o título |

---

## 4. Playwright Codegen

A forma mais rápida de criar um teste é **gravar a interação** com a interface.
O **Codegen** abre o navegador, observa o que você faz e gera o código
automaticamente — sem precisar de configuração prévia.

### 4.1. Iniciando o Codegen

Comando básico (abre uma página em branco):

```sh
npx playwright codegen
```

Abrindo direto em uma URL específica:

```sh
npx playwright codegen https://poa.ifrs.edu.br
```

### 4.2. Interface do Codegen

Ao executar o comando, duas janelas aparecem lado a lado:

1. **Navegador** — onde você interage normalmente (clica, digita, navega).
2. **Playwright Inspector** — painel que exibe o código gerado em tempo real.

<center>
  <img src="img/codegen.png" alt="Playwright Codegen" width="80%" height="80%" border=0 style="border:0; text-decoration:none; outline:none" /><br/>
  Figura 2 — Codegen: navegador (esquerda) e Inspector (direita)
</center>

### 4.3. Fluxo de uso passo a passo

1. **Inicie a gravação** — assim que o Codegen abre, ele já está gravando
   (ícone vermelho ativo no Inspector).
2. **Interaja com a página** — clique em links, preencha formulários, navegue
   normalmente. Cada ação vira uma linha de código.
3. **Adicione assertivas** — use os botões da barra do Inspector
   (ver seção 4.4).
4. **Pause se necessário** — clique no botão vermelho para pausar/retomar.
5. **Copie o código** — clique em **Copy** no Inspector e cole em um arquivo
   `tests/*.spec.ts`.

### 4.4. Criando assertivas

O Inspector possui três botões para criar assertivas sem escrever código:

| Botão | Método gerado | O que verifica |
|---|---|---|
| Assert visibility | `toBeVisible()` | O elemento está visível na tela |
| Assert text | `toHaveText()` | O elemento contém o texto esperado |
| Assert value | `toHaveValue()` | Um campo de formulário tem o valor esperado |

**Como usar:** clique no botão da assertiva desejada e, em seguida, clique
sobre o elemento na página. A linha é adicionada automaticamente ao código.

### 4.5. Seletores gerados

O Codegen prioriza seletores **semânticos** (baseados em como o usuário
enxerga a interface) em vez de seletores CSS, tornando os testes mais
robustos a mudanças de layout:

| Prioridade | Método | Exemplo |
|---|---|---|
| 1ª | `getByRole()` | `page.getByRole('button', { name: 'Buscar' })` |
| 2ª | `getByText()` | `page.getByText('Editais')` |
| 3ª | `getByLabel()` | `page.getByLabel('Pesquisa')` |
| 4ª | `getByPlaceholder()` | `page.getByPlaceholder('Digite aqui')` |
| 5ª | `getByTestId()` | `page.getByTestId('search-btn')` |
| Fallback | `locator()` (CSS) | `page.locator('#filter-search')` |

### 4.6. Opções úteis da linha de comando

| Opção | Exemplo | Descrição |
|---|---|---|
| `--browser` | `--browser firefox` | Navegador (chromium, firefox, webkit) |
| `--output` | `--output tests/teste.spec.ts` | Salva direto em arquivo |
| `--target` | `--target python-pytest` | Idioma de saída (JS, TS, Python, Java, C#) |
| `--device` | `--device "iPhone 13"` | Emula um dispositivo móvel |
| `--viewport-size` | `--viewport-size "375,812"` | Tamanho da janela |
| `--lang` | `--lang "pt-BR"` | Idioma do navegador |
| `--color-scheme` | `--color-scheme dark` | Tema claro ou escuro |

**Exemplo combinando opções** — gravando em um iPhone 13 com Firefox e
salvando direto em arquivo:

```sh
npx playwright codegen \
  --browser firefox \
  --device "iPhone 13" \
  --output tests/mobile.spec.ts \
  https://poa.ifrs.edu.br
```

### 4.7. Exemplo de código gerado

O teste abaixo foi produzido pelo Codegen ao acessar o site do IFRS, clicar
em editais e buscar por editais de pesquisa:

```ts
import { test, expect } from '@playwright/test';

test('busca editais de pesquisa', async ({ page }) => {
  await page.goto('https://poa.ifrs.edu.br');
  await page.getByRole('link', { name: 'Editais' }).click();
  await page.getByLabel('Pesquisar').fill('pesquisa');
  await page.getByRole('button', { name: 'Buscar' }).click();
  await expect(
    page.getByRole('link', {
      name: 'Edital 36/2023 - Bolsas de Pesquisa: publicado o resultado final',
    })
  ).toBeVisible();
});
```

Pontos a observar:

- **`async/await`** — toda ação é assíncrona e deve ser precedida de `await`.
- **`getByRole()`** — seletor semântico baseado no papel ARIA do elemento.
- **`expect(...).toBeVisible()`** — assertiva com _auto-wait_: aguarda até
  o timeout configurado antes de falhar.

---

## 5. Executando os testes

### 5.1. Modos de execução

| Comando | O que faz |
|---|---|
| `npx playwright test` | Roda todos os testes em modo _headless_ (sem janela) |
| `npx playwright test --ui` | Abre o **modo UI**, com timeline e watch mode |
| `npx playwright test --headed` | Roda com o navegador visível |
| `npx playwright test --debug` | Inicia o **Playwright Inspector** para depurar |
| `npx playwright test login.spec.ts` | Roda apenas um arquivo |
| `npx playwright test -g "login"` | Roda testes cujo nome contém "login" |

### 5.2. Modo UI

O modo UI é o mais recomendado durante o desenvolvimento. Ele oferece:

- Lista de testes navegável
- Timeline com cada ação executada
- Watch mode (re-executa ao salvar o arquivo)
- Inspeção do DOM em cada passo

```sh
npx playwright test --ui
```

<center>
  <img src="img/playwright2.png" alt="Modo UI do Playwright" width="50%" height="50%" border=0 style="border:0; text-decoration:none; outline:none" /><br/>
  Figura 3 — Modo UI do Playwright
</center>

### 5.3. Relatório HTML

Após a execução, gere e abra o relatório com:

```sh
npx playwright show-report
```

<center>
  <img src="img/playwright3.png" alt="Relatório do Playwright" width="50%" height="50%" border=0 style="border:0; text-decoration:none; outline:none" /><br/>
  Figura 4 — Relatório HTML de execução
</center>

---

## 6. Configuração do projeto

Toda a configuração do Playwright fica em `playwright.config.ts`. Esta seção
mostra os ajustes mais comuns.

### 6.1. Gravação de vídeo

Útil para investigar falhas. Adicione dentro do bloco `use`:

```ts
import { defineConfig } from '@playwright/test';

export default defineConfig({
  use: {
    video: 'retain-on-failure',
  },
});
```

Valores possíveis:

| Valor | Comportamento |
|---|---|
| `'off'` | Não grava (padrão) |
| `'on'` | Grava sempre |
| `'retain-on-failure'` | Grava todos, mantém só os que falharam |
| `'on-first-retry'` | Grava apenas no primeiro retry |

Os vídeos são salvos em `test-results/`.

### 6.2. Iniciar um servidor automaticamente

Se sua aplicação precisa de um servidor HTTP rodando, o Playwright pode
iniciá-lo antes dos testes e desligá-lo ao final. Isso substitui pacotes como
`start-server-and-test`.

```ts
import { defineConfig } from '@playwright/test';

export default defineConfig({
  webServer: {
    command: 'npm start',
    url: 'http://localhost:8080',
    reuseExistingServer: !process.env.CI,
  },
  use: {
    baseURL: 'http://localhost:8080',
    video: 'retain-on-failure',
  },
});
```

O `package.json` correspondente:

```json
{
  "scripts": {
    "start": "http-server",
    "test": "playwright test"
  },
  "devDependencies": {
    "@playwright/test": "^1.44.0"
  },
  "dependencies": {
    "http-server": "^14.0.0"
  }
}
```

Para executar tudo:

```sh
npm test
```

---

## 8. Referências

- [Playwright — site oficial](https://playwright.dev)
- [Codegen — documentação](https://playwright.dev/docs/codegen)
- [Locators — guia de seletores](https://playwright.dev/docs/locators)
- [Best practices](https://playwright.dev/docs/best-practices)

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
