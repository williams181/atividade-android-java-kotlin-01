package br.ufpe.cin.residencia.banco.conta;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import java.util.List;

//Ver anotações TODO no código
public class ContaRepository {
    private ContaDAO dao;
    private LiveData<List<Conta>> contas;

    public ContaRepository(ContaDAO dao) {
        this.dao = dao;
        this.contas = dao.contas();
    }

    public LiveData<List<Conta>> getContas() {
        return contas;
    }

    @WorkerThread
    public void inserir(Conta c) {
        dao.adicionar(c);
    }

    @WorkerThread
    public void atualizar(Conta c) {

        dao.atualizarConta(c);
    }

    @WorkerThread
    public void remover(Conta c) {

        dao.removerConta(c);
    }

    @WorkerThread
// Etapa 6 do projeto
    public List<Conta> buscarPorNomeCliente(String nomeCliente) {
        //TODO implementar busca
        return (List<Conta>) dao.buscarPorNomeCliente(nomeCliente).getValue();
    }
    @WorkerThread
    public List<Conta> buscarPeloCPF(String cpfCliente) {
        //TODO implementar busca
        return (List<Conta>) dao.buscarPorCPFCliente(cpfCliente).getValue();
    }
    @WorkerThread
    public Conta buscarPeloNumero(String numeroConta) {
        //TODO implementar busca
        return dao.buscarPorNumero(numeroConta).getValue();
    }

}
