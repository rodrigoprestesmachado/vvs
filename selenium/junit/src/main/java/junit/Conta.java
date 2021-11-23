package junit;
public class Conta {
    private String contaId;
    private long saldo;

    public Conta(String contaId, long saldoInicial){
        this.contaId = contaId;
        this.saldo = saldoInicial;
    }
    public void debito(long quantia){
        this.saldo -= quantia;
    }
    public void credito(long quantia){
        this.saldo += quantia;
    }
    public long getSaldo(){
        return this.saldo;
    }
    public String getContaId(){
        return this.contaId;
    }
}