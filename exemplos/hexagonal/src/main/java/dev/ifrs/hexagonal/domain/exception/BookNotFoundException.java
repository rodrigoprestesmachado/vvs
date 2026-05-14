/**
 * PW2 by Rodrigo Prestes Machado
 *
 * PW2 is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 *
*/
package dev.ifrs.hexagonal.domain.exception;

/**
 * Indica que nenhum livro existe para o ISBN solicitado.
 */
public class BookNotFoundException extends RuntimeException {

    /**
     * @param isbn ISBN não encontrado
     */
    public BookNotFoundException(final String isbn) {
        super("Book with ISBN " + isbn + " not found");
    }
}
