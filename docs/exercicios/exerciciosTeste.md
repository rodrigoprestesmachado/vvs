---
layout: default
title: Exercícios
parent: Teste de desenvolvimento
nav_order: 19
---

# Exercícios sobre JUnit e Mockito

Neste exercício você vai praticar a criação de testes unitários com JUnit e
Mockito. O cenário é um sistema de logística que gerencia veículos e cargas.
O exercício está dividido em duas partes: na primeira, você escreve testes
usando apenas o JUnit; na segunda, você utiliza o Mockito para simular uma
dependência externa.
{: .fs-3 }

## Parte 1 — Testes com JUnit

### Contexto

A classe `Vehicle` representa um veículo capaz de transportar cargas até um
limite máximo de peso. A classe já está implementada e possui os seguintes
membros:
{: .fs-3 }

| Membro | Descrição |
|---|---|
| `Vehicle(int weightLimit)` | Construtor que define o limite máximo de peso do veículo. |
| `addWeight(Load load)` | Adiciona uma carga ao veículo. |
| `checkWeightLimit()` | Retorna `true` se o peso total das cargas estiver dentro do limite, `false` caso contrário. |
{: .fs-3 }

### O que fazer

1. Abra o projeto `logistics` (veja como obter o código abaixo).
2. Crie uma classe de teste chamada `VehicleTest`.
3. Implemente **pelo menos três cenários de teste** que cubram as situações
   descritas a seguir.
{: .fs-3 }

### Cenários sugeridos

* **Adicionar cargas** — adicione uma ou mais cargas ao veículo e verifique se
  a lista de cargas contém a quantidade esperada.
* **Peso dentro do limite** — adicione cargas cujo peso total não exceda o
  limite e verifique se `checkWeightLimit()` retorna `true`.
* **Peso acima do limite** — adicione cargas cujo peso total ultrapasse o
  limite e verifique se `checkWeightLimit()` retorna `false`.
* **Veículo vazio** — sem adicionar nenhuma carga, verifique se
  `checkWeightLimit()` retorna `true`.
{: .fs-3 }

### Dicas

* Dê nomes descritivos aos seus métodos de teste (por exemplo,
  `deveRetornarTrueQuandoPesoDentroDoLimite`).
* Execute os testes e, caso algum falhe, depure o código até que todos passem.
{: .fs-3 }

### Código do projeto

Para obter o código inicial, clone o repositório e abra o projeto:
{: .fs-3 }

```bash
git clone -b dev https://github.com/rodrigoprestesmachado/vvs
code exemplos/logistics
```

---

## Parte 2 — Testes com Mockito

### Contexto

Imagine que o projeto evoluiu e a lógica de validação de peso foi extraída
para um serviço externo chamado `WeightService`. Agora, a classe `Vehicle`
**depende** desse serviço para verificar se o peso total é aceitável. Como
não queremos depender da implementação real do serviço durante os testes,
vamos usar o **Mockito** para simulá-lo.
{: .fs-3 }

A interface `WeightService` possui o seguinte método:
{: .fs-3 }

```java
public interface WeightService {
    /**
     * Verifica se o total de peso está dentro do limite permitido.
     */
    boolean isWeightAllowed(int totalWeight, int maxWeightLimit);
}
```
{: .fs-3 }

A classe `Vehicle` foi modificada para receber o serviço como dependência:
{: .fs-3 }

```java
public class Vehicle {

    private int maximumWeightLimit;
    private List<Load> loads = new ArrayList<>();
    private WeightService weightService;

    public Vehicle(int weightLimit, WeightService weightService) {
        this.maximumWeightLimit = weightLimit;
        this.weightService = weightService;
    }

    public void addWeight(Load load) {
        loads.add(load);
    }

    public boolean checkWeightLimit() {
        int total = loads.stream().mapToInt(Load::getWeight).sum();
        return weightService.isWeightAllowed(total, maximumWeightLimit);
    }

    public int getMaximumWeightLimit() {
        return maximumWeightLimit;
    }

    public List<Load> getLoads() {
        return loads;
    }
}
```
{: .fs-3 }

### O que fazer

Crie uma classe de teste que utilize `@ExtendWith(MockitoExtension.class)` e
declare um mock de `WeightService` com `@Mock`. Implemente testes que cubram
os três cenários abaixo:
{: .fs-3 }

**Cenário 1 — ✅ Peso dentro do limite**
* Configure o mock para que `isWeightAllowed(...)` retorne `true`.
* Adicione cargas ao veículo e chame `checkWeightLimit()`.
* Verifique se o método retorna `true`.
* Use `verify(...)` para confirmar que o mock foi chamado com os valores
  esperados.
{: .fs-3 }

**Cenário 2 — ❌ Peso acima do limite**
* Configure o mock para que `isWeightAllowed(...)` retorne `false`.
* Adicione cargas ao veículo e chame `checkWeightLimit()`.
* Verifique se o método retorna `false`.
{: .fs-3 }

**Cenário 3 — ⚠️ Serviço lança exceção**
* Configure o mock para que `isWeightAllowed(...)` lance uma
  `RuntimeException` usando `when(...).thenThrow(...)`.
* Verifique se a exceção é propagada corretamente ao chamar
  `checkWeightLimit()`, utilizando `assertThrows`.
{: .fs-3 }

---

## Dicas para resolver o exercício com IA

Se estiver utilizando uma IA como apoio, experimente as seguintes abordagens:
{: .fs-3 }

1. **Peça explicações conceituais** — por exemplo, "O que é um mock e por que
   ele é útil em testes unitários?"
2. **Solicite analogias** — analogias ajudam a fixar conceitos abstratos.
3. **Peça exemplos simples** — peça que o conceito seja aplicado em um
   problema diferente do exercício.
4. **Solicite exemplos de código comentados** — para entender cada linha.
5. **Peça ajuda para depuração** — cole o erro e pergunte o que ele significa.
{: .fs-3 }

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
