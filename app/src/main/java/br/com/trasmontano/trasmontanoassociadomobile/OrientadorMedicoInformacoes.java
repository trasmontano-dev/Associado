package br.com.trasmontano.trasmontanoassociadomobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class OrientadorMedicoInformacoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientador_medico_informacoes);
        try{
            ArrayList<String> conteudo = new ArrayList<>();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
