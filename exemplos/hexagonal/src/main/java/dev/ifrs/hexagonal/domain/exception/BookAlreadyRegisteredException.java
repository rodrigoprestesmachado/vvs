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
 * Indica tentativa de cadastro com ISBN já existente.
 */
public class BookAlreadyRegisteredException extends RuntimeException {

    /**
     * @param isbn ISBN duplicado
     */
    public BookAlreadyRegisteredException(final String isbn) {
        super("Book with ISBN " + isbn + " is already registered");
    }
}
