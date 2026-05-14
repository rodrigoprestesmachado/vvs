/**
 * PW2 by Rodrigo Prestes Machado
 *
 * PW2 is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 *
*/
package dev.ifrs.hexagonal.adapters.in.rest;

import dev.ifrs.hexagonal.domain.model.Book;
import dev.ifrs.hexagonal.domain.ports.in.AddBookUseCase;
import dev.ifrs.hexagonal.domain.ports.in.DeleteBookUseCase;
import dev.ifrs.hexagonal.domain.ports.in.ListBookUseCase;
import dev.ifrs.hexagonal.domain.ports.in.UpdateBookUseCase;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Recurso JAX-RS REST para operações CRUD de livros.
 */
@Path("/books")
public class BookResource {

    /** Cadastro de livros. */
    @Inject
    private AddBookUseCase addBookUseCase;

    /** Listagem de livros. */
    @Inject
    private ListBookUseCase listBooksUseCase;

    /** Atualização de livros. */
    @Inject
    private UpdateBookUseCase updateBookUseCase;

    /** Exclusão de livros. */
    @Inject
    private DeleteBookUseCase deleteBookUseCase;

    /**
     * Cria um livro a partir do corpo JSON.
     *
     * @param request dados do livro
     * @return 201 com o livro criado
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @WithTransaction
    public Uni<Response> addBook(final BookRequest request) {
        Book book = Book.of(
                request.isbn(),
                request.title(),
                request.author(),
                request.publicationYear(),
                request.copiesAvailable());

        return Uni.createFrom().completionStage(addBookUseCase.add(book))
                .map(saved -> Response.status(Response.Status.CREATED)
                        .entity(BookResponse.from(saved))
                        .build());
    }

    /**
     * Retorna todos os livros cadastrados.
     *
     * @return 200 com lista em JSON
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @WithSession
    public Uni<Response> getAllBooks() {
        return Uni.createFrom().completionStage(listBooksUseCase.listAll())
                .map(books -> Response.ok(
                        books.stream().map(BookResponse::from).toList())
                        .build());
    }

    /**
     * Atualiza um livro existente.
     *
     * @param isbn    ISBN na URL
     * @param request novos dados (título, autor, ano, exemplares)
     * @return 200 com o livro atualizado
     */
    @PUT
    @Path("/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @WithTransaction
    public Uni<Response> updateBook(
            @PathParam("isbn") final String isbn,
            final BookRequest request) {
        Book book = Book.of(
                isbn,
                request.title(),
                request.author(),
                request.publicationYear(),
                request.copiesAvailable());

        return Uni.createFrom().completionStage(
                        updateBookUseCase.update(book))
                .map(updated -> Response.ok(
                        BookResponse.from(updated)).build());
    }

    /**
     * Remove o livro pelo ISBN.
     *
     * @param isbn identificador
     * @return 204 sem corpo em caso de sucesso
     */
    @DELETE
    @Path("/{isbn}")
    @WithTransaction
    public Uni<Response> deleteBook(@PathParam("isbn") final String isbn) {
        return Uni.createFrom().completionStage(deleteBookUseCase.delete(isbn))
                .map(ignored -> Response.noContent().build());
    }
}
