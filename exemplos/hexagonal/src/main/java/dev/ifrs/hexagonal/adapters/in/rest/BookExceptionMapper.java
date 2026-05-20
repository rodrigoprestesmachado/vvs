/**
 * PW2 by Rodrigo Prestes Machado
 *
 * PW2 is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 *
*/
package dev.ifrs.hexagonal.adapters.in.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.ifrs.hexagonal.domain.exception.BookAlreadyRegisteredException;
import dev.ifrs.hexagonal.domain.exception.BookNotFoundException;
import dev.ifrs.hexagonal.domain.exception.InvalidBookException;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

/**
 * Mapeia exceções de domínio e validação para respostas HTTP com corpo JSON
 * de erro.
 * <p>
 * Falhas vindas de {@link java.util.concurrent.CompletableFuture} chegam
 * frequentemente como {@link java.util.concurrent.CompletionException}; por
 * isso percorremos a cadeia de {@link Throwable#getCause()}.
 * <p>
 * {@link WebApplicationException} (p.ex. {@code NotFoundException} lançada
 * pelo roteamento JAX-RS para recursos estáticos não encontrados) é repassada
 * diretamente com seu status HTTP original, sem ser registrada como erro.
 */
@Provider
public class BookExceptionMapper implements ExceptionMapper<RuntimeException> {

    /** Log de mapeamento de erros. */
    private static final Logger LOG =
            Logger.getLogger(BookExceptionMapper.class);

    /**
     * Traduz a exceção em status HTTP e {@link ErrorResponse}.
     *
     * @param exception erro em tempo de execução
     * @return resposta REST apropriada
     */
    @Override
    public Response toResponse(final RuntimeException exception) {
        for (Throwable t = exception; t != null; t = t.getCause()) {
            if (t instanceof BookAlreadyRegisteredException) {
                BookAlreadyRegisteredException e =
                        (BookAlreadyRegisteredException) t;
                LOG.infof("Conflito: %s", e.getMessage());
                return Response.status(Response.Status.CONFLICT)
                        .entity(new ErrorResponse(e.getMessage()))
                        .build();
            }
            if (t instanceof BookNotFoundException) {
                BookNotFoundException e = (BookNotFoundException) t;
                LOG.infof("Não encontrado: %s", e.getMessage());
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse(e.getMessage()))
                        .build();
            }
            if (t instanceof InvalidBookException) {
                InvalidBookException e = (InvalidBookException) t;
                LOG.infof("Validação: %s", e.getMessage());
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponse(e.getMessage()))
                        .build();
            }
            if (t instanceof WebApplicationException) {
                WebApplicationException wae = (WebApplicationException) t;
                return wae.getResponse();
            }
        }
        LOG.error("Erro não tratado na API REST", exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("An unexpected error occurred"))
                .build();
    }

    /**
     * Corpo JSON de erro com mensagem legível ao cliente.
     */
    public static final class ErrorResponse {

        /** Texto enviado ao cliente. */
        private final String message;

        /**
         * @param text texto explicativo
         */
        public ErrorResponse(final String text) {
            this.message = text;
        }

        /**
         * Mensagem de erro (propriedade JSON {@code message}).
         *
         * @return texto para o cliente
         */
        @JsonProperty("message")
        public String getMessage() {
            return message;
        }
    }
}
