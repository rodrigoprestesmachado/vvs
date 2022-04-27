/**
 * VVS by Rodrigo Prestes Machado
 *
 * VVS is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package dev.rpmhub;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockitoMockTest {

    // 2 - Cria um objeto mock da interface (ou classe) DataBase
    @Mock
    DataBase base;

    @Test
    public void create() {
        // 3 - define o comportamento do método createUser
        when(base.createUser("Rodrigo")).thenReturn("Rodrigo");

        // TODO ... código do método de teste

        assertEquals("Rodrigo", base.createUser("Rodrigo"));
    }

    @Test
    public void delete() {
        when(base.deleteUser(5L)).thenReturn(false);

        // TODO ... código do método de teste

        assertEquals(false, base.deleteUser(5L));
    }

    @Test
    public void deleteProblem() {
        // 4 - define que o método deleteUser irá lançar uma exceção se receber um valor
        // negativo
        when(base.deleteUser(-1L)).thenThrow(new IllegalArgumentException());

        // TODO ... código do método de teste

        // 5 - verifica se a exceção lançada é igual a esperada
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            base.deleteUser(-1L);
        });
    }
}
