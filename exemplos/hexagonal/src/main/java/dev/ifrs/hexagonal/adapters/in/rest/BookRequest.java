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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO de entrada para criação e atualização de livros via REST.
 */
public final class BookRequest {

    /** ISBN-10 (no POST pode vir no corpo). */
    private final String isbn;
    /** Título. */
    private final String title;
    /** Autor. */
    private final String author;
    /** Ano de publicação. */
    private final int publicationYear;
    /** Exemplares disponíveis. */
    private final int copiesAvailable;

    /**
     * @param isbnValue    ISBN-10
     * @param titleValue   título
     * @param authorValue  autor
     * @param year         ano de publicação
     * @param copies       exemplares disponíveis
     */
    @JsonCreator
    public BookRequest(
            @JsonProperty("isbn") final String isbnValue,
            @JsonProperty("title") final String titleValue,
            @JsonProperty("author") final String authorValue,
            @JsonProperty("publicationYear") final int year,
            @JsonProperty("copiesAvailable") final int copies) {
        this.isbn = isbnValue;
        this.title = titleValue;
        this.author = authorValue;
        this.publicationYear = year;
        this.copiesAvailable = copies;
    }

    /**
     * @return ISBN-10
     */
    public String isbn() {
        return isbn;
    }

    /**
     * @return título
     */
    public String title() {
        return title;
    }

    /**
     * @return autor
     */
    public String author() {
        return author;
    }

    /**
     * @return ano de publicação
     */
    public int publicationYear() {
        return publicationYear;
    }

    /**
     * @return exemplares disponíveis
     */
    public int copiesAvailable() {
        return copiesAvailable;
    }
}
