package junit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AppMockitoTest{
    @Mock
    private GerenciarConta mockAccountManager;

    @Test
    void testTransferOk(){
        Conta remetenteConta = new Conta("1", 200);
        Conta beneficiarioConta = new Conta("2", 100);

        Mockito.lenient().when(mockAccountManager.encontrarContaPeloUsuario("1")).thenReturn(remetenteConta);
        Mockito.lenient().when(mockAccountManager.encontrarContaPeloUsuario("2")).thenReturn(beneficiarioConta);

        ServiceConta serviceConta = new ServiceConta();
        serviceConta.setGerenciarConta(mockAccountManager);
        serviceConta.transfer("1", "2", 50);
        
        assertEquals(150, remetenteConta.getSaldo());
        assertEquals(150, beneficiarioConta.getSaldo());
    }

    @Test
    void testList(){
        @SuppressWarnings("unchecked")
        List<String> mockList = mock(List.class);
        mockList.add("Primeiro");
        when(mockList.get(0)).thenReturn("Mockito");
        when(mockList.get(1)).thenReturn("Junit");
        assertEquals("Mockito", mockList.get(0));
        assertEquals("Junit", mockList.get(1));
    }
}