---
layout: default
title: Exercícios
parent: Teste de desenvolvimento
nav_order: 18
---

# Exercício sobre Junit

Abaixo, temos um exercício para praticar a criação de testes unitários com o
framework Junit. O exercício consiste em criar testes para um sistema de
logística que gerencia veículos e cargas.
{: .fs-3 }

## Logistics

Você foi designado para criar testes unitários para a classe `Vehicle`, que
representa um veículo capaz de transportar cargas até um limite máximo de peso.
A classe `Vehicle` já está implementada, mas agora é necessário garantir que
todas as funcionalidades do veículo estejam corretas por meio de testes
unitários.
{: .fs-3 }

### Instruções:

Examine a classe `Vehicle` fornecida no projeto logistics. Ela possui três
métodos: `addWeight`, `checkWeightLimit`, e um construtor. Crie uma classe de
teste chamada `VehicleTest` para testar os métodos da classe `Vehicle`. Utilize
o framework JUnit para escrever seus testes.
Implemente pelo menos três cenários de teste para cada método da classe
`Vehicle`. Considere os seguintes casos:
{: .fs-3 }


- Adição de cargas ao veículo.
- Verificação do limite de peso com diferentes cargas.
- Verificação do limite de peso com um veículo vazio.
- Garanta que seus testes cubram todos os possíveis cenários e que os métodos da
classe `Vehicle` estejam funcionando conforme o esperado.
{: .fs-3 }

Execute os testes e verifique se todos eles passam com sucesso. Caso algum teste
falhe, depure o código da classe `Vehicle` para corrigir o problema e execute os
testes novamente até que todos passem.
{: .fs-3 }

Certifique-se de que seus testes abranjam uma variedade de casos de uso para
garantir que o `Vehicle` funcione corretamente em todas as situações. Isso
inclui testar diferentes combinações de cargas e verificar se o limite de peso
é respeitado. Certifique-se de que os nomes dos testes sejam descritivos e
explicativos.
{: .fs-3 }

### Código do projeto

Para obter um código inicial para o projeto, acesse o repositório do GitHub:
{: .fs-3 }

```bash
git clone -b dev https://github.com/rodrigoprestesmachado/vvs
code exemplos/logistics
```

## Continuação do Logistics com Mockito

Imagine que o projeto evoluiu e você decidiu extrair a lógica de validação de
peso para um serviço externo chamado `WeightService`. Agora, a classe `Vehicle`
dependerá desse serviço para validar se o peso total é aceitável. Essa
dependência será mockada com Mockito nos testes.
{: .fs-3 }

Nova interface WeightService.java:
{: .fs-3 }

```java
/**
 * Serviço responsável por validar regras de peso.
 */
public interface WeightService {
    /**
     * Verifica se o total de peso está dentro do limite permitido.
     */
    boolean isWeightAllowed(int totalWeight, int maxWeightLimit);
}
```
{: .fs-3 }


Classe Vehicle.java modificada para usar o serviço:
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
        // delega a verificação ao serviço externo
        return weightService.isWeightAllowed(total, maximumWeightLimit);
    }

    public int getMaximumWeightLimit() {
        return maximumWeightLimit;
    }

    public List<Load> getLoads() {
        return loads;
    }
}
````
{: .fs-3 }

Assim, você deve criar testes unitários para a classe `Vehicle` usando Mockito
para simular o comportamento de `WeightService`.
{: .fs-3 }

Os testes devem cobrir os seguintes cenários:
{: .fs-3 }

* ✅ Peso dentro do limite
  * O serviço retorna true para isWeightAllowed(...).
  * O método checkWeightLimit() deve retornar true.
  * Verifique que o mock foi chamado com os valores esperados.
{: .fs-3 }

* ❌ Peso acima do limite
  * O serviço retorna false.
  * O método checkWeightLimit() deve retornar false.
{: .fs-3 }

* ⚠️ Serviço lança exceção
  * Simule que isWeightAllowed(...) lança uma exceção (RuntimeException).
  * Verifique que a exceção é propagada corretamente.
{: .fs-3 }

### Dicas para resolver o exercício com IA:

* Pergunte por explicaçoes conceituais.
* Peça exemplos simples onde esse conceito é aplicado em outros problemas.
* Solicite analogias para entender melhor.
* Peça exemplos de código com explicações.
* Solicite ajuda para depuração de código.

<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>

