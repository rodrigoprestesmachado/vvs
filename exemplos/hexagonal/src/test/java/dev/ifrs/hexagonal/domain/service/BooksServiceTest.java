package dev.ifrs.hexagonal.domain.service;

import dev.ifrs.hexagonal.domain.exception.BookAlreadyRegisteredException;
import dev.ifrs.hexagonal.domain.exception.BookNotFoundException;
import dev.ifrs.hexagonal.domain.exception.InvalidBookException;
import dev.ifrs.hexagonal.domain.model.Book;
import dev.ifrs.hexagonal.domain.ports.out.BookRepository;
import dev.ifrs.hexagonal.domain.ports.out.EmailNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários de {@link BooksService} com repositório e notificador em memória.
 */
class BooksServiceTest {

    // ISBN-10 válido: 1×0+2×3+3×0+4×6+5×4+6×0+7×6+8×1+9×5+10×2 = 165 → 165÷11 = 15 ✓
    private static final Book SAMPLE = Book.of("0-306-40615-2", "Clean Code", "Robert Martin", 2008, 5);

    private List<String> registered;
    private List<String> updated;
    private List<String> deleted;
    private BooksService service;

    /**
     * Reinicia listas de rastreamento de notificações antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        registered = new ArrayList<>();
        updated = new ArrayList<>();
        deleted = new ArrayList<>();
    }

    /**
     * Cenários do caso de uso {@link dev.ifrs.hexagonal.domain.ports.in.AddBookUseCase}.
     */
    @Nested
    class Add {

        /**
         * Novo ISBN: persiste e dispara notificação de cadastro.
         */
        @Test
        void shouldSaveAndNotifyWhenIsbnIsNew() throws ExecutionException, InterruptedException {
            service = new BooksService(repositoryWith(), notifier());

            Book saved = service.add(SAMPLE).get();

            assertEquals(SAMPLE.getIsbn(), saved.getIsbn());
            assertTrue(registered.contains(SAMPLE.getIsbn()));
        }

        /**
         * ISBN já existente: falha com {@link BookAlreadyRegisteredException}.
         */
        @Test
        void shouldFailWhenIsbnAlreadyExists() {
            service = new BooksService(repositoryWith(SAMPLE), notifier());

            ExecutionException ex = assertThrows(ExecutionException.class,
                    () -> service.add(SAMPLE).get());

            assertInstanceOf(BookAlreadyRegisteredException.class, ex.getCause());
        }

        /**
         * Falha na persistência: não deve chamar notificação de cadastro.
         */
        @Test
        void shouldNotNotifyWhenSaveFails() {
            service = new BooksService(repoWithSaveError(), notifier());

            assertThrows(ExecutionException.class, () -> service.add(SAMPLE).get());
            assertTrue(registered.isEmpty());
        }
    }

    /**
     * Cenários do caso de uso {@link dev.ifrs.hexagonal.domain.ports.in.GetAllBooksUseCase}.
     */
    @Nested
    class ListAll {

        /**
         * Deve retornar a lista e notificar cada ISBN (comportamento atual do serviço).
         */
        @Test
        void shouldReturnAllBooksAndNotifyEach() throws ExecutionException, InterruptedException {
            service = new BooksService(repositoryWith(SAMPLE), notifier());

            List<Book> books = service.listAll().get();

            assertEquals(1, books.size());
            assertEquals(SAMPLE.getIsbn(), books.get(0).getIsbn());
            assertTrue(registered.contains(SAMPLE.getIsbn()));
        }
    }

    /**
     * Cenários do caso de uso {@link dev.ifrs.hexagonal.domain.ports.in.UpdateBookUseCase}.
     */
    @Nested
    class Update {

        /**
         * Livro existente: atualiza e notifica.
         */
        @Test
        void shouldUpdateAndNotifyWhenBookExists() throws ExecutionException, InterruptedException {
            Book newData = Book.of(SAMPLE.getIsbn(), "Clean Code 2nd Ed", "Robert Martin", 2020, 10);
            service = new BooksService(repositoryWith(SAMPLE), notifier());

            Book result = service.update(newData).get();

            assertEquals("Clean Code 2nd Ed", result.getTitle());
            assertEquals(2020, result.getPublicationYear());
            assertTrue(updated.contains(SAMPLE.getIsbn()));
        }

        /**
         * Livro inexistente: {@link BookNotFoundException}.
         */
        @Test
        void shouldFailWhenBookDoesNotExist() {
            service = new BooksService(repositoryWith(), notifier());

            ExecutionException ex = assertThrows(ExecutionException.class,
                    () -> service.update(SAMPLE).get());

            assertInstanceOf(BookNotFoundException.class, ex.getCause());
            assertTrue(updated.isEmpty());
        }
    }

    /**
     * Cenários do caso de uso {@link dev.ifrs.hexagonal.domain.ports.in.DeleteBookUseCase}.
     */
    @Nested
    class Delete {

        /**
         * Exclusão com ISBN existente notifica remoção.
         */
        @Test
        void shouldDeleteAndNotifyWhenBookExists() throws ExecutionException, InterruptedException {
            service = new BooksService(repositoryWith(SAMPLE), notifier());

            service.delete(SAMPLE.getIsbn()).get();

            assertTrue(deleted.contains(SAMPLE.getIsbn()));
        }

        /**
         * ISBN inexistente: {@link BookNotFoundException}.
         */
        @Test
        void shouldFailWhenBookDoesNotExist() {
            service = new BooksService(repositoryWith(), notifier());

            ExecutionException ex = assertThrows(ExecutionException.class,
                    () -> service.delete(SAMPLE.getIsbn()).get());

            assertInstanceOf(BookNotFoundException.class, ex.getCause());
            assertTrue(deleted.isEmpty());
        }
    }

    /**
     * Regras de validação de {@link Book#of(String, String, String, int, int)}.
     */
    @Nested
    class BookValidation {

        private static final String VALID_ISBN = "0-306-40615-2";

        /**
         * ISBN nulo deve ser rejeitado.
         */
        @Test
        void shouldRejectNullIsbn() {
            assertThrows(InvalidBookException.class,
                    () -> Book.of(null, "Clean Code", "Robert Martin", 2008, 5));
        }

        /**
         * Comprimento incorreto após normalização.
         */
        @Test
        void shouldRejectIsbnWithWrongLength() {
            assertThrows(InvalidBookException.class,
                    () -> Book.of("123", "Clean Code", "Robert Martin", 2008, 5));
        }

        /**
         * Posições 1–9 devem ser dígitos.
         */
        @Test
        void shouldRejectIsbnWithNonDigitInFirstNinePositions() {
            assertThrows(InvalidBookException.class,
                    () -> Book.of("A306406152", "Clean Code", "Robert Martin", 2008, 5));
        }

        /**
         * Dígito verificador incorreto.
         */
        @Test
        void shouldRejectIsbnWithInvalidCheckDigit() {
            // ISBN certo seria 0-306-40615-2; trocar o dígito verificador por 9 invalida
            assertThrows(InvalidBookException.class,
                    () -> Book.of("0-306-40615-9", "Clean Code", "Robert Martin", 2008, 5));
        }

        /**
         * Dígito verificador X (10) deve ser aceito quando válido.
         */
        @Test
        void shouldAcceptIsbnWithCheckDigitX() {
            // 020161622X: 1×0+2×2+3×0+4×1+5×6+6×1+7×6+8×2+9×2+10×10 = 220 → 220÷11 = 20 ✓
            assertDoesNotThrow(
                    () -> Book.of("020161622X", "The Pragmatic Programmer", "Andrew Hunt", 1999, 3));
        }

        /**
         * Hífens e espaços são aceitos na entrada.
         */
        @Test
        void shouldAcceptIsbnWithHyphens() {
            assertDoesNotThrow(
                    () -> Book.of("0-306-40615-2", "Clean Code", "Robert Martin", 2008, 5));
        }

        /**
         * Número negativo de exemplares inválido.
         */
        @Test
        void shouldRejectNegativeCopies() {
            assertThrows(InvalidBookException.class,
                    () -> Book.of(VALID_ISBN, "Clean Code", "Robert Martin", 2008, -1));
        }

        /**
         * Ano de publicação além do permitido.
         */
        @Test
        void shouldRejectFuturePublicationYear() {
            assertThrows(InvalidBookException.class,
                    () -> Book.of(VALID_ISBN, "Clean Code", "Robert Martin", 9999, 5));
        }

        /**
         * Título em branco inválido.
         */
        @Test
        void shouldRejectBlankTitle() {
            assertThrows(InvalidBookException.class,
                    () -> Book.of(VALID_ISBN, "", "Robert Martin", 2008, 5));
        }
    }

    // --- helpers ---

    /**
     * Repositório em memória pré-carregado com os livros informados.
     *
     * @param books estado inicial
     * @return adaptador {@link BookRepository}
     */
    private BookRepository repositoryWith(Book... books) {
        List<Book> store = new ArrayList<>(List.of(books));
        return new BookRepository() {
            /** {@inheritDoc} */
            @Override
            public CompletableFuture<Optional<Book>> findByIsbn(String isbn) {
                return CompletableFuture.completedFuture(
                        store.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst());
            }

            /** {@inheritDoc} */
            @Override
            public CompletableFuture<Book> save(Book book) {
                store.add(book);
                return CompletableFuture.completedFuture(book);
            }

            /** {@inheritDoc} */
            @Override
            public CompletableFuture<List<Book>> findAllBooks() {
                return CompletableFuture.completedFuture(List.copyOf(store));
            }

            /** {@inheritDoc} */
            @Override
            public CompletableFuture<Book> update(Book book) {
                store.replaceAll(b -> b.getIsbn().equals(book.getIsbn()) ? book : b);
                return CompletableFuture.completedFuture(book);
            }

            /** {@inheritDoc} */
            @Override
            public CompletableFuture<Void> deleteByIsbn(String isbn) {
                store.removeIf(b -> b.getIsbn().equals(isbn));
                return CompletableFuture.completedFuture(null);
            }
        };
    }

    /**
     * Repositório cujo {@code save} falha sempre (simula erro de banco).
     *
     * @return adaptador com falha em escrita
     */
    private BookRepository repoWithSaveError() {
        return new BookRepository() {
            /** {@inheritDoc} */
            @Override
            public CompletableFuture<Optional<Book>> findByIsbn(String isbn) {
                return CompletableFuture.completedFuture(Optional.empty());
            }

            /** {@inheritDoc} */
            @Override
            public CompletableFuture<Book> save(Book book) {
                return CompletableFuture.failedFuture(new RuntimeException("DB error"));
            }

            /** {@inheritDoc} */
            @Override
            public CompletableFuture<List<Book>> findAllBooks() {
                return CompletableFuture.completedFuture(List.of());
            }

            /** {@inheritDoc} */
            @Override
            public CompletableFuture<Book> update(Book book) {
                return CompletableFuture.failedFuture(new RuntimeException("DB error"));
            }

            /** {@inheritDoc} */
            @Override
            public CompletableFuture<Void> deleteByIsbn(String isbn) {
                return CompletableFuture.failedFuture(new RuntimeException("DB error"));
            }
        };
    }

    /**
     * Notificador que grava ISBNs nas listas {@link #registered}, {@link #updated} e {@link #deleted}.
     *
     * @return adaptador {@link EmailNotification}
     */
    private EmailNotification notifier() {
        return new EmailNotification() {
            /** {@inheritDoc} */
            @Override
            public void notifyRegistration(Book book) {
                registered.add(book.getIsbn());
            }

            /** {@inheritDoc} */
            @Override
            public void notifyUpdate(Book book) {
                updated.add(book.getIsbn());
            }

            /** {@inheritDoc} */
            @Override
            public void notifyDeletion(String isbn) {
                deleted.add(isbn);
            }
        };
    }
}
