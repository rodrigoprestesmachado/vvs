/**
 * PW2 by Rodrigo Prestes Machado
 *
 * PW2 is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 *
*/
package dev.ifrs.hexagonal.domain.ports.out;

import dev.ifrs.hexagonal.domain.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Porta de saída: persistência reativa de livros.
 */
public interface BookRepository {

    /**
     * Insere um novo livro.
     *
     * @param book domínio
     * @return livro persistido
     */
    CompletableFuture<Book> save(Book book);

    /**
     * Busca por ISBN.
     *
     * @param isbn identificador
     * @return livro ou vazio
     */
    CompletableFuture<Optional<Book>> findByIsbn(String isbn);

    /**
     * @return todos os livros cadastrados
     */
    CompletableFuture<List<Book>> findAllBooks();

    /**
     * Atualiza registro existente.
     *
     * @param book dados novos
     * @return domínio atualizado
     */
    CompletableFuture<Book> update(Book book);

    /**
     * Remove pelo ISBN.
     *
     * @param isbn identificador
     * @return conclusão
     */
    CompletableFuture<Void> deleteByIsbn(String isbn);
}
