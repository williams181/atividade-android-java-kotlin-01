package br.ufpe.cin.residencia.banco.conta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import br.ufpe.cin.residencia.banco.R;

//Ver anotações TODO no código
public class AdicionarContaActivity extends AppCompatActivity {

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

        btnAtualizar.setText("Inserir");
        btnRemover.setVisibility(View.GONE);

        btnAtualizar.setOnClickListener(
                v -> {
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String numeroConta = campoNumero.getText().toString();
                    String saldoConta = campoSaldo.getText().toString();
                    //TODO: Incluir validações aqui, antes de criar um objeto Conta (por exemplo, verificar que digitou um nome com pelo menos 5 caracteres, que o campo de saldo tem de fato um número, assim por diante). Se todas as validações passarem, aí sim cria a Conta conforme linha abaixo.
                    if (isValidNome(nomeCliente) && isValidCPF(cpfCliente) && isValidNumeroConta(numeroConta) && isValidSaldo(saldoConta)) {
                        Conta c = new Conta(numeroConta, Double.valueOf(saldoConta), nomeCliente, cpfCliente);
                        viewModel.inserir(c); // Salvar a conta no banco de dados
                        Toast.makeText(this, "Conta inserida com sucesso!", Toast.LENGTH_SHORT).show();
                        finish(); // Finalizar a atividade após a inserção bem-sucedida
                    } else {
                        Toast.makeText(this, "Preencha os campos corretamente.", Toast.LENGTH_SHORT).show();
                    }
                    // Etapa 4 do projeto


                }
        );


    }

    private boolean isValidNome(String nome) {
        // Implemente a validação do nome aqui, por exemplo:
        return nome != null && nome.length() >= 5;
    }
    private boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            Toast.makeText(this, "CPF precisa ter 11 dígitos.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean isValidNumeroConta(String numeroConta) {
        if (numeroConta == null || numeroConta.isEmpty()) {
            Toast.makeText(this, "Informe o número da conta.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean isValidSaldo(String saldo) {
        try {
            double valor = Double.parseDouble(saldo);
            return valor >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}