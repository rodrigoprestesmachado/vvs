# Simulado 1 — Revisão das Questões (para importação no Moodle)

Arquivo de importação: [`simulado1_moodle.xml`](./simulado1_moodle.xml)

**Categoria Moodle:** `$course$/Simulado 1`
**Total de questões:** 15 (múltipla escolha, 4 alternativas, 1 correta cada)
**Dificuldade:** 12 fáceis (80%) / 3 difíceis (20%)
**Análise de código:** 12 questões (80%) — mínimo exigido era 70%

| # | Tema | Dificuldade | Código? |
|---|------|--------------|---------|
| Q01 | JUnit | Fácil | Sim |
| Q02 | JUnit | Fácil | Sim |
| Q03 | JUnit | Fácil | Sim |
| Q04 | JUnit | **Difícil** | Sim |
| Q05 | JUnit | **Difícil** | Sim |
| Q06 | Mockito | Fácil | Sim |
| Q07 | Mockito | Fácil | Sim |
| Q08 | Mockito | **Difícil** | Sim |
| Q09 | V&V (teoria) | Fácil | Não |
| Q10 | V&V (teoria) | Fácil | Não |
| Q11 | V&V (teoria) | Fácil | Não |
| Q12 | Maven | Fácil | Sim |
| Q13 | Maven | Fácil | Sim |
| Q14 | Checkstyle/PMD | Fácil | Sim |
| Q15 | JUnit | Fácil | Sim |

---

## Q01 — Calculadora.multiplicar (JUnit)

Considere a classe abaixo:

```java
public class Calculadora {
    public int multiplicar(int a, int b) {
        return a * b;
    }
}
```

Qual das alternativas abaixo é um teste unitário JUnit 5 **correto** para verificar que `multiplicar(3, 4)` retorna 12?

- A) `assertEquals(7, calculadora.multiplicar(3, 4));`
- **B) `assertEquals(12, calculadora.multiplicar(3, 4));` ✅**
- C) `assertTrue(calculadora.multiplicar(3, 4));`
- D) `assertNull(calculadora.multiplicar(3, 4));`

> **Por que está correta:** o valor esperado (12) é comparado ao resultado do método.

---

## Q02 — UtilTexto.contarCaracteres (JUnit)

```java
public class UtilTexto {
    public int contarCaracteres(String texto) {
        return texto.length();
    }
}
```

Qual asserção verifica corretamente que `contarCaracteres("java")` retorna 4?

- **A) `assertEquals(4, util.contarCaracteres("java"));` ✅**
- B) `assertEquals("java", util.contarCaracteres(4));`
- C) `assertTrue(util.contarCaracteres("java") == "4");`
- D) `assertEquals(5, util.contarCaracteres("java"));`

> **Por que está correta:** "java" possui 4 caracteres.

---

## Q03 — Verificador.ehPositivo (JUnit)

```java
public class Verificador {
    public boolean ehPositivo(int numero) {
        return numero > 0;
    }
}
```

Teste incompleto:

```java
@Test
void testEhPositivo() {
    Verificador v = new Verificador();
    // linha 1
    // linha 2
}
```

Qual alternativa completa corretamente as linhas 1 e 2, verificando que `ehPositivo(5)` é `true` e `ehPositivo(-3)` é `false`?

- **A)**
  ```java
  assertTrue(v.ehPositivo(5));
  assertFalse(v.ehPositivo(-3));
  ``` ✅
- B)
  ```java
  assertFalse(v.ehPositivo(5));
  assertTrue(v.ehPositivo(-3));
  ```
- C)
  ```java
  assertTrue(v.ehPositivo(-3));
  assertFalse(v.ehPositivo(5));
  ```
- D) `assertEquals(5, v.ehPositivo(-3));`

> **Por que está correta:** 5 é positivo (true) e -3 não é positivo (false).

---

## Q04 — Calculadora.dividir — assertThrows (JUnit)

```java
public class Calculadora {
    public int dividir(int a, int b) {
        return a / b;
    }
}
```

Qual trecho de teste verifica corretamente, usando `assertThrows`, que `dividir(10, 0)` lança `ArithmeticException`?

- **A) `assertThrows(ArithmeticException.class, () -> calculadora.dividir(10, 0));` ✅**
- B) `assertThrows(ArithmeticException.class, calculadora.dividir(10, 0));`
- C) `assertThrows(() -> calculadora.dividir(10, 0), ArithmeticException.class);`
- D) `assertEquals(ArithmeticException.class, calculadora.dividir(10, 0).getClass());`

> **Por que está correta:** assertThrows recebe a classe da exceção e uma lambda com a chamada que deve lançá-la.

---

## Q05 — Repositorio.buscar — assertThrows com mensagem (JUnit)

```java
public class Repositorio {
    public Object buscar(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return null;
    }
}
```

Qual trecho de teste verifica corretamente **o tipo da exceção** e **a mensagem** lançada quando `buscar(null)` é chamado?

- **A)**
  ```java
  IllegalArgumentException ex = assertThrows(
      IllegalArgumentException.class,
      () -> repositorio.buscar(null)
  );
  assertEquals("ID não pode ser nulo", ex.getMessage());
  ``` ✅
- B)
  ```java
  assertThrows(
      IllegalArgumentException.class,
      () -> repositorio.buscar(null).getMessage()
  );
  ```
- C) `assertEquals("ID não pode ser nulo", repositorio.buscar(null));`
- D) `assertThrows(NullPointerException.class, () -> repositorio.buscar(null));`

> **Por que está correta:** assertThrows retorna a exceção capturada, permitindo verificar sua mensagem com getMessage().

---

## Q06 — @ExtendWith(MockitoExtension.class) (Mockito)

```java
@ExtendWith(MockitoExtension.class)
class RepositorioServiceTest {

    @Mock
    private Repositorio repositorio;

    // ...testes...
}
```

Qual é a finalidade da anotação `@ExtendWith(MockitoExtension.class)` nesse trecho?

- **A) Habilitar o uso das anotações do Mockito, como `@Mock`, na classe de teste. ✅**
- B) Executar os testes em paralelo automaticamente.
- C) Substituir o JUnit por outro framework de testes.
- D) Gerar relatórios de cobertura de código.

> **Por que está correta:** essa extensão do JUnit 5 inicializa os mocks anotados com @Mock.

---

## Q07 — when().thenReturn() (Mockito)

```java
@Mock
private Repositorio repositorio;

@Test
void testBuscarNome() {
    when(repositorio.buscarNome(1)).thenReturn("Maria");
    assertEquals("Maria", repositorio.buscarNome(1));
}
```

O que a linha `when(repositorio.buscarNome(1)).thenReturn("Maria")` faz?

- A) Executa o método real `buscarNome(1)` do repositório.
- **B) Configura o mock para retornar "Maria" sempre que `buscarNome(1)` for chamado. ✅**
- C) Verifica se o método `buscarNome` já foi chamado anteriormente.
- D) Cria uma nova instância real de `Repositorio`.

> **Por que está correta:** essa é a definição de comportamento simulado do Mockito.

---

## Q08 — @Mock vs @Spy (Mockito)

```java
@Mock
private Repositorio repositorioMock;

@Spy
private Repositorio repositorioSpy = new Repositorio();
```

Com base no trecho, qual afirmação está correta sobre a diferença entre `repositorioMock` e `repositorioSpy`?

- A) Ambos executam sempre o código real dos métodos.
- **B) `repositorioMock` é totalmente simulado; `repositorioSpy` envolve uma instância real, permitindo sobrescrever comportamentos pontuais. ✅**
- C) `repositorioSpy` não pode ter métodos configurados com `when().thenReturn()`.
- D) `repositorioMock` precisa de uma instância real para funcionar, enquanto `repositorioSpy` não.

> **Por que está correta:** essa é a diferença fundamental entre @Mock e @Spy no Mockito.

---

## Q09 — Definição de V&V (teoria)

O que representa "V&V" no contexto de desenvolvimento de software?

- A) Versão e Velocidade
- **B) Validação e Verificação ✅**
- C) Visualização e Versatilidade
- D) Valor e Viabilidade

> **Por que está correta:** V&V significa Validação e Verificação.

---

## Q10 — Verificação vs Validação (teoria)

Qual afirmativa descreve melhor a diferença entre verificação e validação?

- A) Verificação se concentra no código em execução, enquanto validação se concentra somente em documentos.
- B) Verificação é uma atividade dinâmica; validação é estática.
- **C) Verificação avalia se o produto atende requisitos e especificações; validação se preocupa se atende às necessidades do usuário. ✅**
- D) Verificação é feita somente por testes manuais; validação é sempre automatizada.

> **Por que está correta:** essa é a distinção clássica entre verificação e validação.

---

## Q11 — Inspeções em V&V (teoria)

O que são inspeções no contexto de V&V?

- A) Testes automatizados para garantir desempenho sob carga.
- **B) Revisões formais e sistemáticas de artefatos como documentos ou código, procurando erros e inconsistências. ✅**
- C) Simulações com usuários finais para verificar usabilidade.
- D) Compilação e execução de código em diferentes ambientes.

> **Por que está correta:** essa é a definição de inspeções.

---

## Q12 — Plugin no pom.xml (Maven)

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-checkstyle-plugin</artifactId>
            <version>3.3.0</version>
        </plugin>
    </plugins>
</build>
```

Em qual arquivo de um projeto Maven esse trecho XML normalmente é declarado?

- A) `settings.xml`
- **B) `pom.xml` ✅**
- C) `build.gradle`
- D) `project.json`

> **Por que está correta:** o pom.xml é o arquivo central que contém configurações, dependências e plugins do projeto.

---

## Q13 — Ciclo de vida clean (Maven)

Saída de um comando executado no terminal:

```
$ mvn clean
[INFO] --- maven-clean-plugin:3.2.0:clean ---
[INFO] Deleting target directory
```

Qual ciclo de vida do Maven está sendo executado nesse comando?

- A) `default`
- B) `site`
- **C) `clean` ✅**
- D) `verify`

> **Por que está correta:** o ciclo clean é responsável por limpar artefatos de builds anteriores, como o diretório target.

---

## Q14 — Checkstyle integrado ao Maven

```xml
<plugin>
    <artifactId>maven-checkstyle-plugin</artifactId>
    <executions>
        <execution>
            <phase>validate</phase>
            <goals><goal>check</goal></goals>
        </execution>
    </executions>
</plugin>
```

Qual é o objetivo dessa configuração integrada ao ciclo de build do Maven?

- A) Executar testes unitários automaticamente na fase `validate`.
- **B) Garantir que o processo de build verifique automaticamente se o código segue padrões de estilo antes de avançar. ✅**
- C) Substituir o compilador Java padrão.
- D) Gerar relatórios de cobertura de testes.

> **Por que está correta:** essa é a principal vantagem de integrar o Checkstyle ao ciclo de build do Maven.

---

## Q15 — Anotação @Disabled (JUnit)

```java
@Disabled
@Test
void testFuturaFuncionalidade() {
    assertTrue(false);
}
```

Qual é o efeito da anotação `@Disabled` nesse método?

- A) O teste é executado normalmente, mas o resultado é ignorado.
- **B) O método de teste não será executado durante a execução da suíte de testes. ✅**
- C) O teste será executado apenas em ambiente de produção.
- D) A anotação força o teste a sempre passar.

> **Por que está correta:** @Disabled impede a execução do método de teste anotado.
