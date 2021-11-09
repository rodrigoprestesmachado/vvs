/**
 * VVS by Rodrigo Prestes Machado
 *
 * VVS is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package dev.vvs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppTest {

    @Mock
    DataBase base;

    @Test
    public void create() {
        // definindo o comportamento do método createUser
        when(base.createUser("Rodrigo")).thenReturn("Rodrigo");

        // TODO ...

        assertEquals("Rodrigo", base.createUser("Rodrigo"));
    }

    @Test
    public void delete() {
        when(base.deleteUser(5L)).thenReturn(false);

        // TODO ...
        // base.deleteUser(5L);

        assertEquals(false, base.deleteUser(5L));
    }

    @Test
    public void deleteProblem() {
        when(base.deleteUser(-1L)).thenThrow(new IllegalArgumentException());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            base.deleteUser(-1L);
        });
    }
}
