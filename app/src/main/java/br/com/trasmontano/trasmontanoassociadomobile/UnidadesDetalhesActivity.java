package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.UnidadeMedica;
import se.emilsjolander.sprinkles.Query;

public class UnidadesDetalhesActivity extends AppCompatActivity {

    private int codigoUnidade;
    private UnidadeMedica unidadeMedica;
    private TextView textViewUnidade;
    private TextView textViewEndereco;
    private TextView textViewBairro;
    private TextView textViewCidade;
    private TextView textViewCep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidades_detalhes);

        Intent intentUnidades = getIntent();
        codigoUnidade = Integer.parseInt(intentUnidades.getExtras().getString("IdUnidade"));
        CarregarObjetos();
        SelecionarUnidade();
    }

    private void CarregarObjetos()
    {
        textViewUnidade = (TextView) findViewById(R.id.twUnidade);
        textViewEndereco = (TextView) findViewById(R.id.twEndereco);
        textViewBairro = (TextView) findViewById(R.id.twBairro);
        textViewCidade = (TextView) findViewById(R.id.twCidade);
        textViewCep = (TextView) findViewById(R.id.twCep);
    }

    private void SelecionarUnidade()
    {
        String endereco;
        unidadeMedica = Query.one(UnidadeMedica.class, "SELECT * FROM tbLocalidade WHERE Codigo = ?", codigoUnidade).get();
        textViewUnidade.setText(unidadeMedica.getLocalidade());
        endereco = unidadeMedica.getEndereco();

        if (!IsNullOrEmptyString(unidadeMedica.getNumero())) {
            endereco += unidadeMedica.getNumero();
            if (!IsNullOrEmptyString(unidadeMedica.getComplemento()))
                endereco += unidadeMedica.getComplemento();
        }

        textViewEndereco.setText(endereco);
        textViewBairro.setText(unidadeMedica.getBairro());
        textViewCidade.setText(unidadeMedica.getCidade());
        textViewCep.setText(unidadeMedica.getCep());
    }

    private boolean IsNullOrEmptyString(String parametro)
    {
        return (parametro == null || parametro.trim().length() == 0);
    }
}