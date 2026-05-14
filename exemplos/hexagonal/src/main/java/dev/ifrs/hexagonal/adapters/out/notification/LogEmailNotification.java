/**
 * PW2 by Rodrigo Prestes Machado
 *
 * PW2 is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 *
*/
package dev.ifrs.hexagonal.adapters.out.notification;

import dev.ifrs.hexagonal.domain.model.Book;
import dev.ifrs.hexagonal.domain.ports.out.EmailNotification;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

/**
 * Adaptador de notificação por e-mail que registra eventos em log
 * (substituto simples de envio real).
 */
@ApplicationScoped
public class LogEmailNotification implements EmailNotification {

    /** Logger da aplicação. */
    private static final Logger LOG =
            Logger.getLogger(LogEmailNotification.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyRegistration(final Book book) {
        LOG.infof(
                "Book registered: %s (ISBN: %s)",
                book.getTitle(),
                book.getIsbn());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyUpdate(final Book book) {
        LOG.infof(
                "Book updated: %s (ISBN: %s)",
                book.getTitle(),
                book.getIsbn());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyDeletion(final String isbn) {
        LOG.infof("Book deleted: ISBN %s", isbn);
    }
}
