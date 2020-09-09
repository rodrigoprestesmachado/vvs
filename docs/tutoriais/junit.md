---
layout: default
title: Junit
nav_order: 2
---
# Junit

## Junit na linha de comando

1. Baixe o [.jar](https://github.com/junit-team/junit4/wiki/download-and-install) do Junit

1. Ajuste o projeto do Eclipse:
   - Para rodar na linha de comando, as versões 3 e 4 do Junit necessitam que os testes estendam a classe TestCase e as classes container estendam TestSuite
  
   - As classes container devem implementar o método suíte para registrar as classes de teste

```java
public static Test suite() {
    final TestSuite s = new TestSuite();
    s.addTestSuite(Test.class);
    s.addTestSuite(Test2.class);
    s.addTestSuite(Test3.class);
    return s;
}
```

3. Exporte um .jar do projeto do eclipse para o mesmo diretório do .jar do Junit

3. Execute o seguinte comando (exemplo): `java -cp .:* junit.textui.TestRunner edu.ifrs.AllTests`
   - `java` = invoca a máquina virtual
   - `-cp` = adiciona no classpath
   - `.` = indica o diretório local
   - `: ` = separador
   - `* ` = indica todos os .jar no diretório. Nota, utilize `;` no Windows
   - `junit.textui.TestRunner` = classe do Junit responsável por rodar os testes
   - `edu.ifrs.AllTests` = nome da nossa classe (ou suíte) de teste