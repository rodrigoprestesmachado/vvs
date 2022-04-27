/**
 * VVS by Rodrigo Prestes Machado
 *
 * VVS is licensed under a
 * Creative Commons Attribution 4.0 International License.
 * You should have received a copy of the license along with this
 * work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package dev.rpmhub.injectmock;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockitoInjectMocksTest {

    // 1 - a interface Network, que é uma dependência da classe Communication, será simulada
    @Mock
    Network network;

    // 2 - a anotação @InjectMocks permite criar um mock da classe Communication e resolver
    // a dependência Network
    @InjectMocks
    Communication communication;

    @Test
    public void injectMocksTest() {
        when(communication.send("message")).thenReturn(true);
        Assertions.assertEquals(true, communication.send("message"));
    }

}
