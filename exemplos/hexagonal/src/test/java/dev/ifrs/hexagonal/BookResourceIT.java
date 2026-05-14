package dev.ifrs.hexagonal;

import io.quarkus.test.junit.QuarkusIntegrationTest;

/**
 * Executa os mesmos testes de {@link BookResourceTest} em modo integração (aplicação empacotada).
 */
@QuarkusIntegrationTest
class BookResourceIT extends BookResourceTest {
    // Execute the same tests but in packaged mode.
}
