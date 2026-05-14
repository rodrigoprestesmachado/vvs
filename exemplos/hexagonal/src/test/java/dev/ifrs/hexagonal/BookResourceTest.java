package dev.ifrs.hexagonal;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

/**
 * Testes de API REST para {@code /books} em modo Quarkus (dev/test).
 */
@QuarkusTest
class BookResourceTest {

    // ISBN-10 válido: 1×0+2×2+3×0+4×1+5×6+6×1+7×6+8×2+9×2+10×10 = 220 → 220÷11 = 20 ✓
    private static final String ISBN = "020161622X";

    /** ISBN-10 válido e não cadastrado nos cenários desta classe (para esperar 404). */
    private static final String UNKNOWN_VALID_ISBN = "0134685997";

    private static final String VALID_BOOK_JSON = """
            {
              "isbn": "%s",
              "title": "The Pragmatic Programmer",
              "author": "Andrew Hunt",
              "publicationYear": 1999,
              "copiesAvailable": 3
            }
            """.formatted(ISBN);

    /**
     * POST deve retornar 201 e corpo com dados do livro criado.
     */
    @Test
    void shouldReturn201WhenAddingNewBook() {
        given()
                .contentType(ContentType.JSON)
                .body(VALID_BOOK_JSON)
                .when()
                .post("/books")
                .then()
                .statusCode(201)
                .body("isbn", is(ISBN))
                .body("title", is("The Pragmatic Programmer"));
    }

    /**
     * Segundo POST com o mesmo ISBN deve retornar 409 (conflito).
     */
    @Test
    void shouldReturn409WhenIsbnAlreadyExists() {
        given().contentType(ContentType.JSON).body(VALID_BOOK_JSON).post("/books");

        given()
                .contentType(ContentType.JSON)
                .body(VALID_BOOK_JSON)
                .when()
                .post("/books")
                .then()
                .statusCode(409)
                .body("message", notNullValue());
    }

    /**
     * ISBN inválido no corpo deve produzir 400 com mensagem de erro.
     */
    @Test
    void shouldReturn400WhenIsbnIsInvalid() {
        String invalidJson = """
                {
                  "isbn": "0-306-40615-9",
                  "title": "The Pragmatic Programmer",
                  "author": "Andrew Hunt",
                  "publicationYear": 1999,
                  "copiesAvailable": 3
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(invalidJson)
                .when()
                .post("/books")
                .then()
                .statusCode(400)
                .body("message", notNullValue());
    }

    /**
     * GET em {@code /books} deve retornar 200.
     */
    @Test
    void shouldReturn200WithListWhenGettingAllBooks() {
        given()
                .when()
                .get("/books")
                .then()
                .statusCode(200);
    }

    /**
     * PUT em livro existente deve retornar 200 com campos atualizados.
     */
    @Test
    void shouldReturn200WhenUpdatingExistingBook() {
        given().contentType(ContentType.JSON).body(VALID_BOOK_JSON).post("/books");

        String updatedJson = """
                {
                  "title": "The Pragmatic Programmer 2nd Ed",
                  "author": "Andrew Hunt",
                  "publicationYear": 2019,
                  "copiesAvailable": 5
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(updatedJson)
                .when()
                .put("/books/{isbn}", ISBN)
                .then()
                .statusCode(200)
                .body("title", is("The Pragmatic Programmer 2nd Ed"))
                .body("publicationYear", is(2019));
    }

    /**
     * PUT com ISBN inexistente deve retornar 404.
     */
    @Test
    void shouldReturn404WhenUpdatingNonExistentBook() {
        given()
                .contentType(ContentType.JSON)
                .body(VALID_BOOK_JSON)
                .when()
                .put("/books/{isbn}", UNKNOWN_VALID_ISBN)
                .then()
                .statusCode(404)
                .body("message", notNullValue());
    }

    /**
     * DELETE de livro existente deve retornar 204.
     */
    @Test
    void shouldReturn204WhenDeletingExistingBook() {
        given().contentType(ContentType.JSON).body(VALID_BOOK_JSON).post("/books");

        given()
                .when()
                .delete("/books/{isbn}", ISBN)
                .then()
                .statusCode(204);
    }

    /**
     * DELETE com ISBN inexistente deve retornar 404.
     */
    @Test
    void shouldReturn404WhenDeletingNonExistentBook() {
        given()
                .when()
                .delete("/books/{isbn}", "000-0-00-000000-0")
                .then()
                .statusCode(404);
    }
}
