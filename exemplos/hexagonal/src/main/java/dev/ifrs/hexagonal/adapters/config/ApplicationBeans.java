/**
 * PW2 by Rodrigo Prestes Machado
 *
 * PW2 is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 *
*/
package dev.ifrs.hexagonal.adapters.config;

import dev.ifrs.hexagonal.domain.ports.in.AddBookUseCase;
import dev.ifrs.hexagonal.domain.ports.in.DeleteBookUseCase;
import dev.ifrs.hexagonal.domain.ports.in.ListBookUseCase;
import dev.ifrs.hexagonal.domain.ports.in.UpdateBookUseCase;
import dev.ifrs.hexagonal.domain.ports.out.BookRepository;
import dev.ifrs.hexagonal.domain.ports.out.EmailNotification;
import dev.ifrs.hexagonal.domain.service.BooksService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

/**
 * Composition root da aplicação: responsável pelo wiring das dependências do
 * domínio.
 * <p>
 * {@link BooksService} implementa as quatro portas de entrada. Uma única
 * instância é criada em {@link PostConstruct} e exposta via producers que
 * retornam cada interface separadamente, evitando ambiguidade de tipos no CDI
 * e respeitando o princípio da segregação de interface (ISP).
 */
@ApplicationScoped
public class ApplicationBeans {

    /** Repositório de livros (porta de saída). */
    @Inject
    private BookRepository repository;

    /** Notificador de eventos (porta de saída). */
    @Inject
    private EmailNotification notifier;

    /** Serviço único que implementa todos os casos de uso de livros. */
    private BooksService service;

    /**
     * Constrói o serviço de domínio com o repositório e o notificador
     * injetados.
     */
    @PostConstruct
    void init() {
        service = new BooksService(repository, notifier);
    }

    /**
     * Expõe o caso de uso de cadastro de livro para injeção CDI.
     *
     * @return implementação de {@link AddBookUseCase}
     */
    @Produces
    @ApplicationScoped
    public AddBookUseCase addBookUseCase() {
        return service;
    }

    /**
     * Expõe o caso de uso de listagem de livros para injeção CDI.
     *
     * @return implementação de {@link ListBookUseCase}
     */
    @Produces
    @ApplicationScoped
    public ListBookUseCase getListBookUseCase() {
        return service;
    }

    /**
     * Expõe o caso de uso de atualização de livro para injeção CDI.
     *
     * @return implementação de {@link UpdateBookUseCase}
     */
    @Produces
    @ApplicationScoped
    public UpdateBookUseCase updateBookUseCase() {
        return service;
    }

    /**
     * Expõe o caso de uso de exclusão de livro para injeção CDI.
     *
     * @return implementação de {@link DeleteBookUseCase}
     */
    @Produces
    @ApplicationScoped
    public DeleteBookUseCase deleteBookUseCase() {
        return service;
    }
}
