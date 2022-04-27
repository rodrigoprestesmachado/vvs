/**
 * VVS by Rodrigo Prestes Machado
 *
 * VVS is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package dev.rpmhub;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockitoSpyTest {

    @Spy
    private final List<String> list = new ArrayList<>();

    @Test
    public void shouldAddItemsToListSuccessfully() {
        // 1 - estamos fazendo algumas operações no objeto que estamos espionando
        // onde cada chamada é rastreada pelo Mockito.
        list.add("one");
        list.add("two");

        // 2- o método verify analisa se algumas das condições especificadas
        // foram atendidas
        verify(list, times(2)).add(anyString());

        // 3 - verificando se o método add foi chamado com o valor esperado
        verify(list).add("one");
        verify(list).add("two");

        // 4 - a assertiva prova que o método add foi chamado na instância real
        Assertions.assertEquals(2, list.size());
    }

}
