package br.ufpe.cin.residencia.banco;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import br.ufpe.cin.residencia.banco.cliente.ClientesActivity;
import br.ufpe.cin.residencia.banco.conta.ContasActivity;

//Ver anotações TODO no código
public class MainActivity extends AppCompatActivity {
    BancoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(BancoViewModel.class);

        Button contas = findViewById(R.id.btnContas);
        Button clientes = findViewById(R.id.btnClientes);
        Button transferir = findViewById(R.id.btnTransferir);
        Button debitar = findViewById(R.id.btnDebitar);
        Button creditar = findViewById(R.id.btnCreditar);
        Button pesquisar = findViewById(R.id.btnPesquisar);
        TextView totalBanco = findViewById(R.id.totalDinheiroBanco);

        //Remover a linha abaixo se for implementar a parte de Clientes
        clientes.setEnabled(false);

        contas.setOnClickListener(
                v -> startActivity(new Intent(this, ContasActivity.class))
        );
        clientes.setOnClickListener(
                v -> startActivity(new Intent(this, ClientesActivity.class))
        );
        transferir.setOnClickListener(
                v -> startActivity(new Intent(this, TransferirActivity.class))
        );
        creditar.setOnClickListener(
                v -> startActivity(new Intent(this, CreditarActivity.class))
        );
        debitar.setOnClickListener(
                v -> startActivity(new Intent(this, DebitarActivity.class))
        );
        pesquisar.setOnClickListener(
                v -> startActivity(new Intent(this, PesquisarActivity.class))
        );

        // TODO: PARTE 15 DO PROJETO
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {sleep(5000); // Atualiza o valor total do banco a cada 5 milisegundos
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Double saldoTotal = viewModel.getSaldoTotal();
                                if (saldoTotal != null) {
                                    totalBanco.setText(Double.toString(saldoTotal));
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }}