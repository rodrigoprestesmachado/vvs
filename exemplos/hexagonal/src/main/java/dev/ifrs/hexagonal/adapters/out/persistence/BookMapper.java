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

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.ifrs.hexagonal.domain.model.Book;

/**
 * MapStruct: conversão entre entidade JPA {@link BookEntity} e modelo de
 * domínio {@link Book}.
 */
@Mapper(componentModel = "cdi")
public interface BookMapper {

    /**
     * Mapeia domínio para persistência; o id é ignorado (gerido pelo Panache).
     *
     * @param book agregado
     * @return entidade para gravação
     */
    @Mapping(target = "id", ignore = true)
    BookEntity toEntity(Book book);

    /**
     * Mapeia linha de banco para domínio.
     *
     * @param entity registro persistido
     * @return livro de domínio
     */
    Book toDomain(BookEntity entity);

}
