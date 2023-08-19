package br.ufpe.cin.residencia.banco;
import java.util.List;
import android.app.Application;
import br.ufpe.cin.residencia.banco.conta.Conta;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import br.ufpe.cin.residencia.banco.conta.ContaRepository;

//Ver anotações TODO no código
public class BancoViewModel extends AndroidViewModel {
    private ContaRepository repository;

    public BancoViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
    }

    void transferir(String numeroContaOrigem, String numeroContaDestino, double valor) throws Exception {
        Conta contaOrigem = repository.buscarPeloNumero(numeroContaOrigem);
        Conta contaDestino = repository.buscarPeloNumero(numeroContaDestino);

        if (contaOrigem == null || contaDestino == null) {
            throw new Exception("As contas informadas não foram encontradas.");
        }

        if( contaOrigem.getSaldo() < valor){
            throw new Exception("Saldo insuficiente na conta de origem.");
        }
        contaOrigem.debitar(valor);
        contaDestino.creditar(valor);
        repository.atualizar(contaOrigem);
        repository.atualizar(contaDestino);
    }

    void creditar(String numeroConta, double valor) throws Exception{
        //TODO implementar creditar em conta (lembrar de salvar no BD o objeto Conta modificado)
        Conta conta = repository.buscarPeloNumero(numeroConta);

        if(conta == null){
            throw new Exception("A conta informada não foi encontrada.");
        }
        conta.creditar(valor);
        repository.atualizar(conta);
    }

    void debitar(String numeroConta, double valor) throws Exception{
        //TODO implementar debitar em conta (lembrar de salvar no BD o objeto Conta modificado)
        Conta conta = repository.buscarPeloNumero(numeroConta);

        if(conta == null) {
            throw new Exception("A conta informada não foi encontrada.");
        }

        if(conta.getSaldo() < valor ){
            throw new Exception("Saldo insuficiente na conta.");
        }
        repository.atualizar(conta);
    }

    void buscarPeloNome(String nomeCliente) {
        //TODO implementar busca pelo nome do Cliente
        // PARTE 11 DO PROJETO
        // USAR LISTA PARA ENCONTRAR TODOS OS USUARIOS PELO NOME

        List<Conta> contasEncontradas = (List<Conta>) repository.buscarPorNomeCliente(nomeCliente);
    }

    void buscarPeloCPF(String cpfCliente) {
        //TODO implementar busca pelo CPF do Cliente
        // PARTE 11 DO PROJETO
        // USAR LISTA PARA ENCONTRAR TODOS OS USAURIOS PELO CPF

        List<Conta> contasEncontras = (List<Conta>) repository.buscarPeloCPF(cpfCliente);
    }

    void buscarPeloNumero(String numeroConta) {
        //TODO implementar busca pelo número da Conta
        // PARTE 11 DO PROJETO
        // USAR LISTA PARA ENCONTRAR TODOS OS USUARIOS PELO NUMERO

        List<Conta> contasEncontras = (List<Conta>) repository.buscarPeloNumero(numeroConta);
    }

}
