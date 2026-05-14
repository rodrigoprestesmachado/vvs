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

/**
 * Porta de saída: notificações assíncronas relacionadas ao ciclo de vida do
 * livro (ex.: e-mail).
 */
public interface EmailNotification {

    /**
     * Chamado após cadastro bem-sucedido.
     *
     * @param book livro registrado
     */
    void notifyRegistration(Book book);

    /**
     * Chamado após atualização bem-sucedida.
     *
     * @param book livro atualizado
     */
    void notifyUpdate(Book book);

    /**
     * Chamado após exclusão bem-sucedida.
     *
     * @param isbn ISBN do livro removido
     */
    void notifyDeletion(String isbn);
}
