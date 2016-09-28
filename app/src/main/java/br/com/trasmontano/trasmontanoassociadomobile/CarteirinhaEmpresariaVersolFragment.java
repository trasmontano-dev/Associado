package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.DadosCarteirinha;
import br.com.trasmontano.trasmontanoassociadomobile.adapter.AssociadoLoginAdapter;
import br.com.trasmontano.trasmontanoassociadomobile.adapter.CarenciaAdapter;
import br.com.trasmontano.trasmontanoassociadomobile.network.APIClient;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CarteirinhaEmpresariaVersolFragment extends Fragment {

    private Callback<List<DadosCarteirinha>> callbackCarteirinhaTemporaria;
    private RecyclerView rvCarencia;
    private  String Matricula;
    private  String Dependente;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carteirinha_empresarial_verso, container, false);

        rvCarencia = (RecyclerView)view.findViewById(R.id.rvCarencias);
        rvCarencia.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCarencia.setHasFixedSize(true);


        SharedPreferences prefs = this.getActivity().getSharedPreferences("DADOS_LOGIN", Context.MODE_PRIVATE);
        if (prefs.contains("CodigoUsuario")) {
            Matricula =  prefs.getString("CodigoUsuario", "");
            if(Matricula.length() > 6)
            {
                Dependente = Matricula.substring(6,8);
            }
            else
            {
                Dependente = "00";
            }
        }

        configureInformacaoAssociadoCallback();

        new APIClient().getRestService().getDadosCarteirinhaTemporaria(Matricula.substring(0,6),
                Dependente, callbackCarteirinhaTemporaria);



        return view;
    }

    private void configureInformacaoAssociadoCallback() {
        callbackCarteirinhaTemporaria = new Callback<List<DadosCarteirinha>>() {

            @Override
            public void success(List<DadosCarteirinha> dadosCarteirinha, Response response) {
                rvCarencia.setAdapter(new CarenciaAdapter(getContext(), dadosCarteirinha));
                rvCarencia.setItemAnimator(new DefaultItemAnimator());


            }


            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
                Toast.makeText(getContext(), "Falha ao conectar no servidor", Toast.LENGTH_LONG).show();
            }


        };
    }
}
