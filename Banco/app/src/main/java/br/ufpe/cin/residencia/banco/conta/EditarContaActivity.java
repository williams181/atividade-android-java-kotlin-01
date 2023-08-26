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
            //TODO: ETAPA 8 DO PROJETO
            String cpfConta = i.getStringExtra("CPFDaConta");
            String nomeConta = i.getStringExtra("NomeDaConta");
            String saldosConta = i.getStringExtra("SaldoDaConta");
            campoNumero.setText(numeroConta);
            campoCPF.setText(cpfConta);
            campoNome.setText(nomeConta);
            campoSaldo.setText(saldosConta);
        }

        btnAtualizar.setText("Editar");
        btnAtualizar.setOnClickListener(
                v -> {
                    String nomeCliente = campoNome.getText().toString();
                    String cpfCliente = campoCPF.getText().toString();
                    String saldoConta = String.valueOf(Double.valueOf(campoSaldo.getText().toString()));
                    //TODO Validação dos campos antes de atualizar a conta
                    //TODO: Incluir validações aqui, antes de criar um objeto Conta. Se todas as validações passarem, aí sim monta um objeto Conta.
                    //TODO: chamar o método que vai atualizar a conta no Banco de Dados
                    //TODO: Etapa 9 do Projeto
                    try {
                        if (!isValidNome(nomeCliente)) {
                            throw new Exception("Nome inválido");
                        }
                        if (!isValidCPF(cpfCliente)) {
                            throw new Exception("CPF inválido");
                        }
                        if (!isValidSaldo(saldoConta)) {
                            throw new Exception("Saldo inválido");
                        }
                        if (!isValidNumeroConta(numeroConta)) {
                            throw new Exception("Número de conta inválido");
                        }
                        Conta c = new Conta(numeroConta, Double.valueOf(saldoConta), nomeCliente, cpfCliente);
                        viewModel.atualizar(c);
                        Toast.makeText(this, "Conta atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        btnRemover.setOnClickListener(v -> {
            // TODO: ETAPA 10 DO PROJETO
            String nomeCliente = campoNome.getText().toString();
            String cpfCliente = campoCPF.getText().toString();
            String saldoConta = campoSaldo.getText().toString();

            try {
                Conta c = new Conta(numeroConta, Double.valueOf(saldoConta), nomeCliente, cpfCliente);
                viewModel.remover(c);
            } catch (Exception e) {
                campoCPF.setError("CPF inválido"); // esta mensagem não será exibida ao usuário
                campoCPF.requestFocus();


                campoNome.setError("Nome inválido"); // esta mensagem não será exibida ao usuário
                campoNome.requestFocus();

                campoSaldo.setError("Saldo inválido"); // esta mensagem não será exibida ao usuário
                campoSaldo.requestFocus();
            }
        });
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
