package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.Associado;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.DadosCarteirinha;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Login;
import br.com.trasmontano.trasmontanoassociadomobile.network.APIClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.emilsjolander.sprinkles.Query;

public class CarteirinhaActivity extends AppCompatActivity {
    private Button btFrenteVerso;
    //private Callback<List<DadosCarteirinha>> callbackCarteirinhaTemporaria;
    String TipoPlano;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carteirinha);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        SharedPreferences prefs = getSharedPreferences("DADOS_LOGIN", MODE_PRIVATE);
        String nome = prefs.getString("NomeUsuario", "");
        String mat = prefs.getString("CodigoUsuario", "");
        String redirecionarPara = prefs.getString("redirecionarPara", "");
        TipoPlano = prefs.getString("PerfilUsuario", "");

        if (savedInstanceState == null) {
            if(TipoPlano.equalsIgnoreCase("associado") || TipoPlano.equalsIgnoreCase("Associado Dependente"))
            {
                CarteirinhaAssociadoPFisica();
            }
            else
            {
                CarteirinhaEmpresarial();
            }
            /*CarteirinhaEmpresarialFrenteFragment cf = new CarteirinhaEmpresarialFrenteFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            //transaction.setCustomAnimations(R.anim.fade_in ,R.anim.fade_out);
            transaction.setCustomAnimations(R.anim.slide_esquerda, R.anim.slide_direita);
            transaction.replace(R.id.maincontainer, cf);
            transaction.commit();*/
        }

        btFrenteVerso = (Button) findViewById(R.id.btFrenteVerso);

        btFrenteVerso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teste = btFrenteVerso.getText().toString();
                if (btFrenteVerso.getText().toString().equalsIgnoreCase("Verso")) {
                    btFrenteVerso.setText("Frente");
                    CarteirinhaEmpresariaVersolFragment cv = new CarteirinhaEmpresariaVersolFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                    transaction.replace(R.id.maincontainer, cv);
                    transaction.commit();

                } else {
                    btFrenteVerso.setText("Verso");
                    if(TipoPlano.equalsIgnoreCase("associado") || TipoPlano.equalsIgnoreCase("Associado Dependente"))
                    {
                        CarteirinhaAssociadoPFisica();
                    }
                    else
                    {
                        CarteirinhaEmpresarial();
                    }

                }

            }
        });
    }

    public void CarteirinhaAssociadoPFisica()
    {
        CarteirinhaPessoaFisicaFrenteFragment cf = new CarteirinhaPessoaFisicaFrenteFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.maincontainer, cf);
        transaction.commit();
    }
    public void CarteirinhaEmpresarial()
    {
        CarteirinhaEmpresarialFrenteFragment cf = new CarteirinhaEmpresarialFrenteFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.maincontainer, cf);
        transaction.commit();
    }

}
