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
 * Erro de validação de dados de livro (regras de domínio ou formato).
 */
public class InvalidBookException extends RuntimeException {

    /**
     * @param message detalhe da violação
     */
    public InvalidBookException(final String message) {
        super(message);
    }
}
