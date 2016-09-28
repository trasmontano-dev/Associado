package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.Associado;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.DadosConsulta;
import br.com.trasmontano.trasmontanoassociadomobile.adapter.AssociadoLoginAdapter;
import br.com.trasmontano.trasmontanoassociadomobile.adapter.ConsultaAdapter;
import br.com.trasmontano.trasmontanoassociadomobile.network.APIClient;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Header;
import se.emilsjolander.sprinkles.Query;

public class ListConsultaActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Callback<String> callbackAgendamentoDeConsulta;
    SpotsDialog spotsDialog;
    DadosConsulta dados = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_consulta);
        spotsDialog = new SpotsDialog(this, R.style.LoaderCustom);
        spotsDialog.show();

        recyclerView = (RecyclerView) findViewById(R.id.rvConsulta);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        overridePendingTransition(R.anim.slide_esquerda, R.anim.slide_direita);


        List<DadosConsulta> lst = AgendamentoDeConsultaActivity.lstDadosConsulta;

        CarregaLista(this, lst );

    }

    public void CarregaLista(Context context, List<DadosConsulta> lst) {
        spotsDialog.show();
        recyclerView.setAdapter(new ConsultaAdapter(this, lst, onClickAgendar()));
        spotsDialog.dismiss();
    }

    private ConsultaAdapter.AgendarConsultaOnClickListener onClickAgendar(){
        return new ConsultaAdapter.AgendarConsultaOnClickListener()
        {
            @Override
            public void OnClickImageButtonAgendar(View view, int index) {
               /* @Header("matricula") String matricula ,
                @Header("cdDependente") String cdDependente,
                @Header("dataParaAgendar") String dataParaAgendar,
                @Header("especialidade") String especialidade,
                @Header("localidade") char localidade,
                @Header("cdEspecialidade") String cdEspecialidade,
                @Header("cbos") String cbos,
                @Header("cdMedico") String cdMedico,
                @Header("idAgendaMedica") String idAgendaMedica,
                @Header("nmMedico") String nmMedico,
                @Header("idAgenda") String idAgenda,*/

                ConfirmaAgendamentoConsulta(view, index);
            }
        };
    }

    public void ConfirmaAgendamentoConsulta(final View view, int index) {
        dados = AgendamentoDeConsultaActivity.lstDadosConsulta.get(index);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        spotsDialog.show();
                        Toast.makeText(ListConsultaActivity.this, "Agendar", Toast.LENGTH_LONG).show();
                        configureAgendamentoDeConsultaCallback();

                        SharedPreferences prefs = getSharedPreferences("DADOS_LOGIN", MODE_PRIVATE);

                        String  mat = prefs.getString("CodigoUsuario", "");
                        String cdDependente = prefs.getString("CodigoDependente", "");

                        String TipoPlano = prefs.getString("PerfilUsuario", "");

                        String data = dados.getDataHoraAgendamento().substring(8,10) + "/" + dados.getDataHoraAgendamento().substring(5,7) + "/" + dados.getDataHoraAgendamento().substring(0,4);
                        String hora = dados.getDataHoraAgendamento().substring(11,16);

                        String especialidade = Normalizer.normalize(AgendamentoDeConsultaActivity.dsEspecialidade, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                        String localidade = Normalizer.normalize(dados.getDsLocalidade(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                        String nmMedico = Normalizer.normalize(dados.getNmMedico(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

                        new APIClient().getRestService().setAgendamentoDeConsulta(mat,cdDependente, data + " " + hora, especialidade, localidade,AgendamentoDeConsultaActivity.cdEspecialidade, dados.getCbos(), dados.getCdMedico(), dados.getID(), nmMedico,dados.getIdAgenda(),TipoPlano ,dados.getLimiteConsAnual() ,callbackAgendamentoDeConsulta);


                      break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(ListConsultaActivity.this, "Não Agendar", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirma o Agendamento da Consulta?").setPositiveButton("Sim", dialogClickListener)
                .setNegativeButton("Não", dialogClickListener).show();
    }

    private void configureAgendamentoDeConsultaCallback() {
        callbackAgendamentoDeConsulta = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
               /* Toast.makeText(ListAgendamentoConsultaActivity.this, "Sucesso cancelamento " + s, Toast.LENGTH_LONG).show();
                Intent i = new Intent(ListAgendamentoConsultaActivity.this, ListAgendamentoConsultaActivity.class);
                startActivity(i);*/
                spotsDialog.dismiss();
                Toast.makeText(ListConsultaActivity.this, s, Toast.LENGTH_LONG).show();
                Intent i = new Intent(ListConsultaActivity.this, ListAgendamentoConsultaActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
               // Toast.makeText(ListAgendamentoConsultaActivity.this, "Falha ao cancelar", Toast.LENGTH_LONG).show();
                spotsDialog.dismiss();
                Toast.makeText(ListConsultaActivity.this, "Falha ao agendar " + error, Toast.LENGTH_LONG).show();
            }
        };
    }
}
