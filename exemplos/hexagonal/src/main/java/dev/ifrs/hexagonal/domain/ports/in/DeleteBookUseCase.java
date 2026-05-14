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

import java.util.concurrent.CompletableFuture;

/**
 * Porta de entrada: exclusão de livro por ISBN.
 */
public interface DeleteBookUseCase {

    /**
     * Remove o livro identificado pelo ISBN.
     *
     * @param isbn ISBN-10
     * @return conclusão assíncrona
     */
    CompletableFuture<Void> delete(String isbn);
}
