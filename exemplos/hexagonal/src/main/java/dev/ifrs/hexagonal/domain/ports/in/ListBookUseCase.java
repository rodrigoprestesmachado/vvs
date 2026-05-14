/**
 * PW2 by Rodrigo Prestes Machado
 *
 * PW2 is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 *
*/
package dev.ifrs.hexagonal.domain.ports.in;

import dev.ifrs.hexagonal.domain.model.Book;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Porta de entrada: listagem de todos os livros.
 */
public interface ListBookUseCase {

    /**
     * @return todos os livros cadastrados
     */
    CompletableFuture<List<Book>> listAll();
}
