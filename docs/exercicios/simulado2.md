---
layout: default
title: Segunda Avaliação
parent: Simulados
nav_order: 22
---

# Segunda Avaliação 📝

## Questões práticas

1. Utilizando Mockito, crie um teste unitário em que você simula (mocka) um repositório chamado UsuarioRepository.
O mock deve retornar um objeto Usuario quando o método buscarPorId(1) for chamado.
{: .fs-3 }

2. Escreva um teste usando RestAssured que envie uma requisição GET para o endpoint /api/usuarios e verifique se o código de status retornado é 200.
O teste deve usar a estrutura básica given() / when() / then().
{: .fs-3 }

3. Usando RestAssured, escreva um teste que envie uma requisição POST para o endpoint /login enviando um JSON com usuário e senha, e verifique se o status retornado é 201.
Use o padrão given() / when() / then() e envie o corpo:
{
  "usuario": "admin",
  "senha": "senha123"
}
{: .fs-3 }

4. Crie um teste unitário usando Mockito no qual:
* A classe UsuarioService depende de um UsuarioRepository.
* Use @Mock para simular o UsuarioRepository.
* Use @InjectMocks para criar automaticamente uma instância real de UsuarioService com o mock injetado.
* No teste, faça o método repository.contarUsuarios() retornar o valor 5 e verifique se o serviço retorna esse mesmo valor ao chamar service.totalUsuarios().
{: .fs-3 }

## Questões teóricas

1. Qual é a principal diferença entre um teste de componente e um teste unitário?
* A) Testes unitários verificam o sistema como um todo, enquanto testes de componente verificam apenas funções individuais.
* B) Testes unitários exigem conexão com banco de dados, enquanto testes de componente nunca utilizam dependências externas.
* C) Testes de componente verificam um módulo ou parte maior do sistema em conjunto, enquanto testes unitários verificam partes menores e isoladas como funções ou métodos.
* D) Testes de componente sempre incluem testes de interface gráfica, enquanto testes unitários não testam interfaces.
{: .fs-3 }

2. O que um teste de componente costuma identificar que um teste unitário isolado pode não detectar?
* A) Erros relacionados ao desempenho da aplicação em ambiente de produção.
* B) Problemas na interação entre partes internas de um módulo, como falhas no fluxo de dados entre classes.
* C) Falhas de layout ou responsividade na interface do usuário.
* D) Problemas de segurança relacionados a ataques externos.
{: .fs-3 }

3. Qual das alternativas representa corretamente a função de um componente HTTP dentro de uma arquitetura de serviços?
* A) Gerenciar apenas conexões WebSocket, sem lidar com requisições tradicionais.
* B) Processar requisições e respostas HTTP, incluindo rotas, headers, métodos e códigos de status.
* C) Executar código diretamente no banco de dados para reduzir latência.
* D) Substituir completamente APIs REST por filas de mensagens assíncronas.
{: .fs-3 }

4. Qual é a principal diferença entre um teste de componente e um teste de sistema?
* A) Testes de sistema verificam apenas uma classe isolada, enquanto testes de componente verificam o sistema inteiro.
* B) Testes de componente validam uma parte funcional do software (como um módulo ou serviço), enquanto testes de sistema testam todo o aplicativo integrado de ponta a ponta.
* C) Testes de sistema precisam sempre simular dependências, enquanto testes de componente utilizam apenas dependências reais.
* D) Testes de componente sempre exigem interface gráfica, enquanto testes de sistema nunca envolvem UI.
{: .fs-3 }

5. Qual das alternativas descreve um tipo de erro que um teste de sistema é especialmente eficaz em identificar?
* A) Erros internos de implementação de um método isolado.
* B) Falhas de integração e fluxo completo entre múltiplos componentes, como um processo que quebra ao atravessar várias APIs ou módulos.
* C) Problemas de tipagem em funções puras antes de compilar o código.
* D) Erros relacionados apenas ao design visual da interface do usuário.
{: .fs-3 }

6. Por que casos de uso são frequentemente utilizados como base para testes de sistema?
* A) Porque detalham apenas regras técnicas internas, essenciais para validar métodos isolados.
* B) Porque representam o comportamento esperado do sistema em cenários completos, permitindo criar testes que reproduzem o uso real.
* C) Porque substituem completamente a especificação funcional do sistema.
* D) Porque são focados exclusivamente na arquitetura interna, sem considerar interações do usuário.
{: .fs-3 }

7. Qual é a principal diferença entre os plugins Maven Surefire e Maven Failsafe?
* A) O Surefire executa testes de carga, enquanto o Failsafe executa apenas testes unitários.
* B) O Surefire é usado apenas para testes manuais, enquanto o Failsafe é usado para testes automatizados.
* C) O Failsafe substitui completamente o Surefire em qualquer tipo de projeto Maven.
* D) O Surefire executa testes durante a fase test, enquanto o Failsafe executa testes de integração durante as fases integration-test e verify.
{: .fs-3 }

8. Em um projeto Maven configurado de forma convencional, em qual fase do ciclo de build os testes de sistema normalmente são executados?
* A) Durante a fase compile.
* B) Durante a fase test, junto com os testes unitários.
* C) Durante as fases integration-test e verify, geralmente por meio do plugin Failsafe.
* D) Apenas na fase install, após gerar o artefato final.
{: .fs-3 }

9. Em um projeto Maven, em qual fase os testes de API escritos com ferramentas como RestAssured são normalmente executados?
* A) Na fase compile, antes da geração do código.
* B) Na fase test, sempre junto com os testes unitários.
* C) Nas fases integration-test e verify, quando são tratados como testes de integração via Failsafe.
* D) Apenas na fase install, depois de empacotar o artefato.
{: .fs-3 }

10. Qual é a característica central do teste de lançamento (release) conforme definido no processo de desenvolvimento?
* A) É um teste interno realizado exclusivamente pelo time de desenvolvimento para garantir cobertura de código.
* B) É um processo de teste conduzido fora do time de desenvolvimento, geralmente com clientes, usuários ou outros times, para validar se o sistema atende aos requisitos e está pronto para uso.
* C) É um teste automatizado obrigatório executado durante a fase test do Maven.
* D) É um teste focado apenas em desempenho e carga para validar a infraestrutura do sistema.
{: .fs-3 }

11. Qual alternativa descreve corretamente os três tipos de testes com usuário?
* A) Teste-alfa é feito por um grupo selecionado de usuários próximos ao time; teste-beta libera o software para um grupo maior de usuários; e teste de aceitação envolve clientes e desenvolvedores decidindo se o sistema está pronto para produção.
* B) Teste-alfa ocorre com o público geral; teste-beta ocorre apenas dentro do time; e teste de aceitação é feito sem envolvimento dos clientes.
* C) Teste-alfa é um teste totalmente automatizado; teste-beta é um teste de carga; e teste de aceitação é exclusivo para o gerente de projeto.
* D) Os três testes são equivalentes e todos envolvem apenas usuários finais sem participação do time de desenvolvimento.
{: .fs-3 }

12. Qual é uma prática comum ao escrever testes com RestAssured?
* A) Utilizar RestAssured para mockar o comportamento do servidor, sem enviar requisições reais.
* B) Configurar a URL base, endpoints, parâmetros e validações de resposta usando uma API fluente que facilita a leitura do teste.
* C) Executar testes apenas por linha de comando, já que RestAssured não funciona com JUnit ou TestNG.
* D) Utilizar RestAssured exclusivamente para testar serviços SOAP.
{: .fs-3 }

13. Por que ferramentas como RestAssured são importantes no contexto de testes automatizados de APIs REST?
* A) Porque eliminam completamente a necessidade de manter documentação da API.
* B) Porque permitem validar o comportamento de APIs por meio de testes automatizados, garantindo que os serviços atendam seus contratos e respondam corretamente a diferentes cenários.
* C) Porque substituem servidores web, permitindo executar a API diretamente dentro dos testes.
* D) Porque são obrigatórias em qualquer projeto Maven que exponha endpoints REST.
{: .fs-3 }

14. Sobre o uso de mocks em testes unitários, qual afirmação é mais correta?
* A) Mocks servem para simular dependências externas ou pesadas (como banco de dados ou chamadas de rede), permitindo testar uma unidade de código isoladamente.
* B) Mocks tornam desnecessários os testes de integração, porque já garantem que tudo funcionará corretamente em produção.
* C) O uso de mocks é desaconselhável quando o método testado depende somente de lógica interna e não de dependências externas.
* D) Mocks devem sempre retornar valores aleatórios para aumentar a cobertura de testes e revelar mais bugs.
{: .fs-3 }

15. Qual é o principal risco de usar mocks em excesso durante testes unitários?
* A) Aumentar o tempo de execução dos testes, tornando-os tão lentos quanto testes de sistema.
* B) Permitir que dependências reais sejam executadas por engano durante o teste.
* C) Impedir o uso de ferramentas como JUnit ou TestNG.
* D) Criar testes que validam apenas comportamentos simulados, não a integração real entre componentes, podendo gerar uma falsa sensação de segurança.
{: .fs-3 }

16. Em qual situação o uso de mocks é mais apropriado?
* A) Quando o código depende de serviços externos ou recursos lentos, e é necessário isolar a lógica para garantir um teste rápido e determinístico.
* B) Quando se deseja testar todo o fluxo do sistema de ponta a ponta.
* C) Quando a unidade de código não possui dependências externas e toda a lógica está contida em um único método.
* D) Quando se quer validar configurações reais de infraestrutura, como banco de dados, filas e serviços remotos.
{: .fs-3 }

17. Qual alternativa explica corretamente a diferença entre @Mock, @InjectMocks e @Spy no Mockito?
* A) @Mock cria objetos reais, @Spy cria objetos falsos e @InjectMocks impede injeção de dependências.
* B) @Mock cria um objeto totalmente simulado; @Spy cria um objeto parcial que usa comportamento real salvo quando sobrescrito; @InjectMocks injeta automaticamente mocks e spies nas dependências da classe sendo testada.
* C) @Mock e @Spy são equivalentes, apenas com sintaxe diferente; @InjectMocks serve para criar logs de teste.
* D) @InjectMocks substitui a necessidade de @Mock e @Spy, pois cria todos os objetos simulados automaticamente.
{: .fs-3 }

18. Qual é o propósito da anotação @InjectMocks no Mockito?
* A) Criar um mock completo da classe sob teste, ignorando todas as suas dependências.
* B) Criar automaticamente mocks para todas as dependências da classe, sem necessidade de usar @Mock.
* C) Criar uma instância real da classe sob teste e injetar automaticamente nela os objetos anotados com @Mock ou @Spy.
* D) Forçar que todos os métodos da classe testada sejam sobrescritos com comportamentos simulados.
{: .fs-3 }

19. Em qual situação o uso de @Spy é mais recomendado do que @Mock?
* A) Quando se deseja simular completamente todas as chamadas a métodos, sem executar nenhuma lógica real.
* B) Quando é importante testar parte do comportamento real de um objeto, mas ainda assim controlar ou verificar chamadas específicas a alguns métodos.
* C) Quando a classe testada não possui dependências e não precisa de simulação.
* D) Quando se quer garantir que nenhum método seja chamado durante o teste.
{: .fs-3 }


<center>
<a href="https://rpmhub.dev" target="blanck"><img src="../imgs/logo.png" alt="Rodrigo Prestes Machado" width="3%" height="3%" border=0 style="border:0; text-decoration:none; outline:none"></a><br/>
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">CC BY 4.0 DEED</a>
</center>
