package br.ufpe.cin.residencia.banco;
import java.util.List;
import java.util.ArrayList;
import android.app.Application;
import br.ufpe.cin.residencia.banco.conta.Conta;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.WorkerThread;
import br.ufpe.cin.residencia.banco.conta.ContaAdapter;
import br.ufpe.cin.residencia.banco.conta.ContaRepository;

//Ver anotações TODO no código
public class BancoViewModel extends AndroidViewModel {
    private ContaRepository repository;
    Double saldoTotal = 0.0;
    List<Conta> contas;

    public BancoViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
    }


    // TODO: ETAPA 11 DO PROJETO ( TRANSFERIR )
    void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) throws Exception {
        Conta contaOrigem = repository.buscarPeloNumero(numeroContaOrigem);
        Conta contaDestino = repository.buscarPeloNumero(numeroContaDestino);


        if (contaDestino == null || contaOrigem == null){
            throw new Exception("As contas informadas não foram encontradas.");
        } else if (valor > contaOrigem.getSaldo()) {
            throw new Exception("Saldo insuficiente na conta de origem.");


        } else{
            contaOrigem.debitar(valor);
            contaDestino.creditar(valor);
            repository.atualizar(contaOrigem);
            repository.atualizar(contaDestino);
        }
    }

    // TODO: ETAPA 11 DO PROJETO ( CREDITAR )
    @WorkerThread
    void creditar(String numeroConta, double valor) throws Exception {
        Conta conta = repository.buscarPeloNumero(numeroConta);
        System.out.println(conta);
        if (conta == null){
            throw new Exception("A conta informada não foi encontrada.");
        } else{
            conta.creditar(valor);
            repository.atualizar(conta);
        }
    }

    // TODO: ETAPA 11 DO PROJETO ( DEBITAR )
    void debitar(String numeroConta, double valor) throws Exception {
        Conta conta = repository.buscarPeloNumero(numeroConta);
        if(conta == null){
            throw new Exception("A conta informada não foi encontrada.");
        } else if (valor > conta.getSaldo()) {
            throw new Exception("Saldo insuficiente na conta.");
        } else{
            repository.atualizar(conta);
        }
    }

    // TODO: ETAPA 13 A 14 MODIFICADO
    void buscarPeloNome(String nomeCliente, ContaAdapter adapter) {
        rodarEmBackground(() -> {
            List<Conta> nome = (List<Conta>) repository.buscarPorNomeCliente(nomeCliente);
            adapter.submitList(nome);
        });
    }

    // TODO: ETAPA 13 A 14 MODIFICADO
    void buscarPeloCPF(String cpfCliente, ContaAdapter adapter) {
        rodarEmBackground( () -> {
            List<Conta> CPF = repository.buscarPeloCPF(cpfCliente);
            adapter.submitList(CPF);
        });
    }

    // TODO: ETAPA 13 A 14 MODIFICADO
    void buscarPeloNumero(String numeroConta, ContaAdapter adapter) {
        rodarEmBackground(() -> {
            Conta conta = repository.buscarPeloNumero(numeroConta);
            List<Conta> lista = new ArrayList<Conta>();
            lista.add(conta);
            adapter.submitList(lista);
        });
    }

    // TODO: PARTE 15 DO PROJETO
    void saldoTotal() {
        rodarEmBackground ( () -> {
            saldoTotal = BancoDB.getDB(getApplication()).contaDAO().saldoTotal();
        });
    }

    // TODO: PARTE 15 DO PROJETO
    public Double getSaldoTotal() {
        saldoTotal();
        return saldoTotal;
    }

    private void rodarEmBackground(Runnable r) {
        new Thread(r).start();
    }

}
