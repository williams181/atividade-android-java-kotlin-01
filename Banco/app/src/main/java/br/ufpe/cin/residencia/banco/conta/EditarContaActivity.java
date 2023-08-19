package br.ufpe.cin.residencia.banco.conta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class EditarContaActivity extends AppCompatActivity {

    public static final String KEY_NUMERO_CONTA = "numeroDaConta";
    ContaViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_conta);
        viewModel = new ViewModelProvider(this).get(ContaViewModel.class);

        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        Button btnRemover = findViewById(R.id.btnRemover);
        EditText campoNome = findViewById(R.id.nome);
        EditText campoNumero = findViewById(R.id.numero);
        EditText campoCPF = findViewById(R.id.cpf);
        EditText campoSaldo = findViewById(R.id.saldo);
        campoNumero.setEnabled(false);

        Intent i = getIntent();
        String numeroConta = i.getStringExtra(KEY_NUMERO_CONTA);
        //TODO usar o número da conta passado via Intent para recuperar informações da conta
        // Etapa 8 do projeto
        // Buscar a conta pelo número usando o ContaViewModel
        Conta conta = viewModel.buscarPeloNumero(numeroConta);
        if (conta != null) {
            campoNome.setText(conta.getNomeCliente());
            campoNumero.setText(conta.getNumero());
            campoCPF.setText(conta.getCpfCliente());
            campoSaldo.setText(String.valueOf(conta.getSaldo()));
        }

        btnAtualizar.setText("Editar");
        btnAtualizar.setOnClickListener(
                v -> {
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();
                    //TODO Validação dos campos antes de atualizar a conta
                    //TODO: Incluir validações aqui, antes de criar um objeto Conta. Se todas as validações passarem, aí sim monta um objeto Conta.
                    //TODO: chamar o método que vai atualizar a conta no Banco de Dados
                    //TODO: Etapa 9 do Projeto
                    if (nomeCliente.isEmpty() || cpfCliente.isEmpty() || saldoConta.isEmpty()) {
                        Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        double saldo = Double.parseDouble(saldoConta);
                        conta.setNomeCliente(nomeCliente);
                        conta.setCpfCliente(cpfCliente);
                        conta.setSaldo(saldo);

                        viewModel.atualizar(conta); // Atualizar a conta no Banco de Dados

                        Toast.makeText(this, "Conta atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish(); // Finalizar a atividade após a atualização bem-sucedida
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Saldo deve ser um número válido", Toast.LENGTH_SHORT).show();
                    }
                });

        btnRemover.setOnClickListener(v -> {
            //TODO implementar remoção da conta
            // Estapa 10 do projetto

            viewModel.remover(conta);
            Toast.makeText(this, "Conta removida com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}