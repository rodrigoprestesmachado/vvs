/**
 * PW2 by Rodrigo Prestes Machado
 *
 * PW2 is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 *
*/
package dev.ifrs.hexagonal.adapters.out.persistence;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade JPA para armazenamento de livros; estende
 * {@link io.quarkus.hibernate.reactive.panache.PanacheEntity} para id e
 * operações Panache.
 */
@Entity
@Getter
@Setter
public class BookEntity extends PanacheEntity {

    /** ISBN-10. */
    private String isbn;
    /** Título. */
    private String title;
    /** Autor. */
    private String author;
    /** Ano de publicação. */
    private int publicationYear;
    /** Exemplares disponíveis. */
    private int copiesAvailable;

}
