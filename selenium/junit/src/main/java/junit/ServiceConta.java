package junit;

public class ServiceConta {
    private GerenciarConta gerenciarConta;

    public void setGerenciarConta(GerenciarConta gerenciar){
        this.gerenciarConta = gerenciar;
    }

    public void transfer(String remetenteId, String beneficiarioId, long quantia){
        Conta remetente = gerenciarConta.encontrarContaPeloUsuario(remetenteId);
        Conta beneficiario = gerenciarConta.encontrarContaPeloUsuario(beneficiarioId);

        remetente.debito(quantia);
        beneficiario.credito(quantia);
        this.gerenciarConta.atualizarConta(remetente);
        this.gerenciarConta.atualizarConta(beneficiario);
    }
}