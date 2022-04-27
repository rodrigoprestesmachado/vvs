/**
 * VVS by Rodrigo Prestes Machado
 *
 * VVS is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package dev.rpmhub;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockitoCaptorTest {

    @Mock
    private List<String> list;

    @Captor
    private ArgumentCaptor<String> valueCaptor;

    @Test
    public void shouldCaptureListParameters() {

        // 1 - Primeiro, adicionamos dois valores String à nossa lista: "um", "dois".
        // Depois, usando o método Verify(...) com o ArgumentCaptor para capturar essas strings.
        list.add("one");
        list.add("two");
        verify(list, times(2)).add(valueCaptor.capture());

        // 2 - ArgumentCaptor possui dois métodos getValue() e getAllValues():
        //    O getValue() pode ser usado quando capturamos um argumento de uma única chamada de método e
        // retornará o último valor capturado.
        //    O getAllValues() retorna a lista de argumentos que foram passados para o método
        List<String> allValues = valueCaptor.getAllValues();

        Assertions.assertTrue(allValues.contains("one"));
        Assertions.assertTrue(allValues.contains("two"));
        // 3 - retorna o último valor capturado
        Assertions.assertEquals("two", valueCaptor.getValue());
    }

}
