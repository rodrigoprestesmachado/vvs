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
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockitoSpyStubTest {

    @Spy
    private final List<String> list = new ArrayList<>();

    @Test
    public void shouldReturnDifferentSizeWhenStubbed() {

        // 1 - Estamos sobrescrevendo o comportamento original do método size()
        // (stub)
        when(list.size()).thenReturn(100);

        list.add("one");
        list.add("two");

        verify(list, times(2)).add(anyString());

        verify(list).add("one");
        verify(list).add("two");

        // 2- Nesse caso, não podemos mais esperar que o método size retorne 2
        Assertions.assertEquals(100, list.size());
    }

}
