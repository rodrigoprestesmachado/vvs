/**
 * PW2 by Rodrigo Prestes Machado
 *
 * PW2 is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 *
*/
package dev.ifrs.hexagonal.domain.service;

import dev.ifrs.hexagonal.domain.exception.BookAlreadyRegisteredException;
import dev.ifrs.hexagonal.domain.exception.BookNotFoundException;
import dev.ifrs.hexagonal.domain.model.Book;
import dev.ifrs.hexagonal.domain.ports.in.AddBookUseCase;
import dev.ifrs.hexagonal.domain.ports.in.DeleteBookUseCase;
import dev.ifrs.hexagonal.domain.ports.in.ListBookUseCase;
import dev.ifrs.hexagonal.domain.ports.in.UpdateBookUseCase;
import dev.ifrs.hexagonal.domain.ports.out.BookRepository;
import dev.ifrs.hexagonal.domain.ports.out.EmailNotification;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Serviço de aplicação que implementa os casos de uso de livros, orquestrando
 * repositório e notificações.
 */
public class BooksService implements AddBookUseCase, ListBookUseCase,
        UpdateBookUseCase, DeleteBookUseCase {

    /** Acesso à persistência de livros. */
    private final BookRepository repository;
    /** Canal de notificações (e-mail, log, etc.). */
    private final EmailNotification notifier;

    /**
     * @param bookRepository   persistência de livros
     * @param emailNotification notificações de eventos (ex.: e-mail)
     */
    public BooksService(
            final BookRepository bookRepository,
            final EmailNotification emailNotification) {
        this.repository = bookRepository;
        this.notifier = emailNotification;
    }

    /**
     * Cadastra um livro novo; falha se o ISBN já existir.
     *
     * @param book livro a persistir
     * @return livro salvo após notificação de cadastro
     */
    @Override
    public CompletableFuture<Book> add(final Book book) {
        return repository.findByIsbn(book.getIsbn())
                .thenCompose(opt -> {
                    if (opt.isPresent()) {
                        return CompletableFuture.failedFuture(
                                new BookAlreadyRegisteredException(
                                        book.getIsbn()));
                    }
                    return repository.save(book)
                            .thenApply(saved -> {
                                notifier.notifyRegistration(saved);
                                return saved;
                            });
                });
    }

    /**
     * Lista todos os livros e notifica o registro de cada um (comportamento
     * atual do domínio).
     *
     * @return lista completa
     */
    @Override
    public CompletableFuture<List<Book>> listAll() {
        return repository.findAllBooks()
                .thenApply(books -> {
                    books.forEach(notifier::notifyRegistration);
                    return books;
                });
    }

    /**
     * Atualiza dados do livro identificado pelo ISBN.
     *
     * @param book dados novos (ISBN deve existir)
     * @return livro atualizado
     */
    @Override
    public CompletableFuture<Book> update(final Book book) {
        return repository.findByIsbn(book.getIsbn())
                .thenCompose(opt -> {
                    if (opt.isEmpty()) {
                        return CompletableFuture.failedFuture(
                                new BookNotFoundException(book.getIsbn()));
                    }
                    return repository.update(book)
                            .thenApply(updated -> {
                                notifier.notifyUpdate(updated);
                                return updated;
                            });
                });
    }

    /**
     * Remove o livro pelo ISBN.
     *
     * @param isbn identificador do livro
     * @return conclusão vazia quando removido
     */
    @Override
    public CompletableFuture<Void> delete(final String isbn) {
        return repository.findByIsbn(isbn)
                .thenCompose(opt -> {
                    if (opt.isEmpty()) {
                        return CompletableFuture.failedFuture(
                                new BookNotFoundException(isbn));
                    }
                    return repository.deleteByIsbn(isbn)
                            .thenApply(ignored -> {
                                notifier.notifyDeletion(isbn);
                                return null;
                            });
                });
    }
}
