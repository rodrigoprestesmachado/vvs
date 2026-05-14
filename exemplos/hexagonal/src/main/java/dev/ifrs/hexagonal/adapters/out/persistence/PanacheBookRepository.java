/**
 * PW2 by Rodrigo Prestes Machado
 *
 * PW2 is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 *
*/
package dev.ifrs.hexagonal.adapters.out.persistence;

import dev.ifrs.hexagonal.domain.exception.BookNotFoundException;
import dev.ifrs.hexagonal.domain.model.Book;
import dev.ifrs.hexagonal.domain.ports.out.BookRepository;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Implementação reativa do repositório de livros com Hibernate Reactive
 * Panache.
 */
@ApplicationScoped
public class PanacheBookRepository implements BookRepository,
        PanacheRepository<BookEntity> {

    /** MapStruct: entidade JPA ↔ domínio. */
    @Inject
    private BookMapper bookMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<Optional<Book>> findByIsbn(final String isbn) {
        return find("isbn", isbn)
                .firstResult()
                .map(e -> Optional.ofNullable(e).map(bookMapper::toDomain))
                .subscribeAsCompletionStage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<Book> save(final Book book) {
        BookEntity entity = bookMapper.toEntity(book);
        return Panache.withTransaction(() -> persist(entity))
                .map(bookMapper::toDomain)
                .subscribeAsCompletionStage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<List<Book>> findAllBooks() {
        return listAll()
                .map(list -> list.stream().map(bookMapper::toDomain).toList())
                .subscribeAsCompletionStage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<Book> update(final Book book) {
        return Panache.<Book>withTransaction(() ->
                find("isbn", book.getIsbn())
                        .firstResult()
                        .onItem().ifNull().failWith(
                                () -> new BookNotFoundException(book.getIsbn()))
                        .invoke(entity -> {
                            entity.setTitle(book.getTitle());
                            entity.setAuthor(book.getAuthor());
                            entity.setPublicationYear(
                                    book.getPublicationYear());
                            entity.setCopiesAvailable(
                                    book.getCopiesAvailable());
                        })
                        .map(bookMapper::toDomain)
        ).subscribeAsCompletionStage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<Void> deleteByIsbn(final String isbn) {
        return Panache.<Void>withTransaction(() ->
                find("isbn", isbn)
                        .firstResult()
                        .onItem().ifNull().failWith(
                                () -> new BookNotFoundException(isbn))
                        .flatMap(entity -> entity.delete())
        ).subscribeAsCompletionStage();
    }
}
