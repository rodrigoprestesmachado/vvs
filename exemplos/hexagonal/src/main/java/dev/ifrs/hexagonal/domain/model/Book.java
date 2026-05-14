/**
 * PW2 by Rodrigo Prestes Machado
 *
 * PW2 is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
 *
*/
package dev.ifrs.hexagonal.domain.model;

import dev.ifrs.hexagonal.domain.exception.InvalidBookException;

import java.time.Year;

/**
 * Agregado de domínio que representa um livro catalogado pela biblioteca.
 * <p>
 * A criação validada ocorre apenas via {@link #of(String, String, String, int,
 * int)}; o builder é destinado ao mapeamento a partir da persistência.
 */
public final class Book {

    /** Comprimento de um ISBN-10 após normalização. */
    private static final int ISBN_10_LEN = 10;
    /** Quantidade de dígitos antes do dígito verificador. */
    private static final int ISBN_FIRST_NINE = 9;
    /** Índice (base 0) do dígito verificador. */
    private static final int ISBN_CHECK_INDEX = 9;
    /** Módulo da soma ponderada ISBN-10. */
    private static final int ISBN_MODULUS = 11;
    /** Peso do último dígito na soma. */
    private static final int ISBN_WEIGHT_LAST = 10;
    /** Valor numérico da letra X no dígito verificador. */
    private static final int ISBN_CHAR_X_VALUE = 10;

    /** ISBN-10 normalizado. */
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
     * Construtor interno usado pelo {@link Builder#build()}.
     *
     * @param builder origem dos campos imutáveis
     */
    private Book(final Builder builder) {
        this.isbn = builder.isbn;
        this.title = builder.title;
        this.author = builder.author;
        this.publicationYear = builder.publicationYear;
        this.copiesAvailable = builder.copiesAvailable;
    }

    /**
     * Único ponto de criação validado; use em toda lógica de negócio.
     *
     * @param isbn             ISBN-10 (hífens opcionais)
     * @param title            título não vazio
     * @param author           autor não vazio
     * @param publicationYear  ano entre 1 e o ano corrente
     * @param copiesAvailable  exemplares disponíveis (não negativo)
     * @return livro válido
     * @throws InvalidBookException se algum invariante for violado
     */
    public static Book of(
            final String isbn,
            final String title,
            final String author,
            final int publicationYear,
            final int copiesAvailable) {
        validate(isbn, title, author, publicationYear, copiesAvailable);
        return builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .publicationYear(publicationYear)
                .copiesAvailable(copiesAvailable)
                .build();
    }

    /**
     * Inicia o builder para montagem a partir da persistência (ex.: MapStruct).
     * Dados vindos do banco já foram validados na entrada; não há revalidação
     * aqui.
     *
     * @return novo builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Valida invariantes de negócio do livro.
     *
     * @param isbn             ISBN-10
     * @param title            título
     * @param author           autor
     * @param publicationYear  ano
     * @param copiesAvailable  exemplares
     * @throws InvalidBookException se algum campo for inválido
     */
    private static void validate(
            final String isbn,
            final String title,
            final String author,
            final int publicationYear,
            final int copiesAvailable) {
        validateIsbn10(isbn);
        if (title == null || title.isBlank()) {
            throw new InvalidBookException("Title cannot be blank");
        }
        if (author == null || author.isBlank()) {
            throw new InvalidBookException("Author cannot be blank");
        }
        int currentYear = Year.now().getValue();
        if (publicationYear < 1 || publicationYear > currentYear) {
            throw new InvalidBookException(
                    "Publication year must be between 1 and " + currentYear);
        }
        if (copiesAvailable < 0) {
            throw new InvalidBookException(
                    "Copies available cannot be negative");
        }
    }

    /**
     * Valida formato e dígito verificador de ISBN-10.
     *
     * @param isbn ISBN bruto ou normalizado
     * @throws InvalidBookException se o ISBN for inválido
     */
    private static void validateIsbn10(final String isbn) {
        if (isbn == null) {
            throw new InvalidBookException("ISBN cannot be null");
        }

        String clean = isbn.replaceAll("[\\s\\-]", "");

        if (clean.length() != ISBN_10_LEN) {
            String msg = "ISBN-10 must have exactly 10 characters after "
                    + "removing hyphens (got: " + clean.length() + ")";
            throw new InvalidBookException(msg);
        }

        int sum = 0;
        for (int i = 0; i < ISBN_FIRST_NINE; i++) {
            char c = clean.charAt(i);
            if (!Character.isDigit(c)) {
                throw new InvalidBookException(
                        "ISBN-10 positions 1–9 must be digits");
            }
            sum += (i + 1) * Character.getNumericValue(c);
        }

        char last = clean.charAt(ISBN_CHECK_INDEX);
        if (last == 'X' || last == 'x') {
            sum += ISBN_WEIGHT_LAST * ISBN_CHAR_X_VALUE;
        } else if (Character.isDigit(last)) {
            sum += ISBN_WEIGHT_LAST * Character.getNumericValue(last);
        } else {
            throw new InvalidBookException(
                    "ISBN-10 check digit must be 0–9 or X");
        }

        if (sum % ISBN_MODULUS != 0) {
            throw new InvalidBookException(
                    "Invalid ISBN-10: check digit does not match");
        }
    }

    /**
     * @return ISBN-10 do livro
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
     * @return quantidade de exemplares disponíveis
     */
    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    /**
     * Builder fluente para construir {@link Book} sem passar pela validação de
     * {@link #of}; uso típico: mapeamento ORM → domínio.
     */
    public static final class Builder {

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

        /** Construtor privado; use {@link Book#builder()}. */
        private Builder() {
        }

        /**
         * @param value ISBN-10
         * @return este builder
         */
        public Builder isbn(final String value) {
            this.isbn = value;
            return this;
        }

        /**
         * @param value título
         * @return este builder
         */
        public Builder title(final String value) {
            this.title = value;
            return this;
        }

        /**
         * @param value autor
         * @return este builder
         */
        public Builder author(final String value) {
            this.author = value;
            return this;
        }

        /**
         * @param year ano de publicação
         * @return este builder
         */
        public Builder publicationYear(final int year) {
            this.publicationYear = year;
            return this;
        }

        /**
         * @param copies exemplares disponíveis
         * @return este builder
         */
        public Builder copiesAvailable(final int copies) {
            this.copiesAvailable = copies;
            return this;
        }

        /**
         * Materializa o {@link Book} a partir do estado atual do builder.
         *
         * @return nova instância de domínio
         */
        public Book build() {
            return new Book(this);
        }
    }
}
