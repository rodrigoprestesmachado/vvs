package junit;

public interface GerenciarConta {
    Conta encontrarContaPeloUsuario(String userId);
    void atualizarConta(Conta conta);
}
