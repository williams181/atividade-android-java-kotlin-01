package br.ufpe.cin.residencia.banco.conta;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.ufpe.cin.residencia.banco.BancoDB;

//Ver métodos anotados com TODO
public class ContaViewModel extends AndroidViewModel {

    private ContaRepository repository;
    public LiveData<List<Conta>> contas;
    private MutableLiveData<Conta> _contaAtual = new MutableLiveData<>();
    public LiveData<Conta> contaAtual = _contaAtual;

    public ContaViewModel(@NonNull Application application) {
        super(application);
        this.repository = new ContaRepository(BancoDB.getDB(application).contaDAO());
        this.contas = repository.getContas();
    }

    void inserir(Conta c) {
        new Thread(() -> repository.inserir(c)).start();
    }

    public void atualizar(Conta c) {
        repository.atualizar(c);
    }

    public void remover(Conta c) {
        repository.remover(c);
    }

    public Conta buscarPeloNumero(String numeroConta) {
        return repository.buscarPeloNumero(numeroConta);
    }

    public LiveData<List<Conta>> getContas() {
        return contas;
    }
}
