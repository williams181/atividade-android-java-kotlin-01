package br.ufpe.cin.residencia.banco.conta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.lifecycle.Observer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class ContasActivity extends AppCompatActivity {
    ContaAdapter adapter;
    ContaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas);
        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.rvContas);
        adapter = new ContaAdapter(getLayoutInflater());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel.getContas().observe(this, todasContas ->{
            adapter.submitList(todasContas);
        });

        Button adicionarConta = findViewById(R.id.btn_Adiciona);
        adicionarConta.setOnClickListener(
                v -> startActivity(new Intent(this, AdicionarContaActivity.class))
        );
        // Etapa 1 concluida adicionando o Observar LiveData contas
        viewModel.getContas().observe(this, contas -> {
            // Atualiza a lista de contas no adapter
            adapter.setContas(contas);
        });
    }
    //TODO Neste arquivo ainda falta implementar o código que atualiza a lista de contas automaticamente na tela
}