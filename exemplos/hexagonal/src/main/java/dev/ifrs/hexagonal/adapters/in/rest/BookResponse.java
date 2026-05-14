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

import dev.ifrs.hexagonal.domain.model.Book;

/**
 * DTO de saída JSON com os dados do livro no domínio.
 */
public final class BookResponse {

    /** ISBN-10. */
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
    public BookResponse(
            final String isbnValue,
            final String titleValue,
            final String authorValue,
            final int year,
            final int copies) {
        this.isbn = isbnValue;
        this.title = titleValue;
        this.author = authorValue;
        this.publicationYear = year;
        this.copiesAvailable = copies;
    }

    /**
     * Converte o agregado de domínio para resposta REST.
     *
     * @param book origem
     * @return DTO serializável
     */
    public static BookResponse from(final Book book) {
        return new BookResponse(
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear(),
                book.getCopiesAvailable());
    }

    /**
     * @return ISBN-10
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @return título
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return autor
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return ano de publicação
     */
    public int getPublicationYear() {
        return publicationYear;
    }

    /**
     * @return exemplares disponíveis
     */
    public int getCopiesAvailable() {
        return copiesAvailable;
    }
}
