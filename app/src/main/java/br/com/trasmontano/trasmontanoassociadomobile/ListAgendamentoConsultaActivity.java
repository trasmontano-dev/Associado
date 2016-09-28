package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.AgendaMedicaAssociado;
import br.com.trasmontano.trasmontanoassociadomobile.adapter.AgendamentoConsultaAdapter;
import br.com.trasmontano.trasmontanoassociadomobile.network.APIClient;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ListAgendamentoConsultaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton imbDeleteAgendamentoConsulta;
    SpotsDialog spotsDialog;
    private Callback<List<AgendaMedicaAssociado>> callbackAgendaMedicaAssociado;
    private Callback<String> callbackCancelamentoDeConsulta;
    private ImageButton imAddConsultaMedica;
    private String cdDependente = "";
    private String mat = "";
    private String perfilUsuario = "";
    private String nomeUsuario = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_agendamento_consulta);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        recyclerView = (RecyclerView) findViewById(R.id.rvAgendamentoDeConsulta);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        spotsDialog = new SpotsDialog(this, R.style.LoaderCustom);
        spotsDialog.show();


        imAddConsultaMedica = (ImageButton) findViewById(R.id.imAddConsultaMedica);

        imAddConsultaMedica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListAgendamentoConsultaActivity.this, AgendamentoDeConsultaActivity.class);
                startActivity(i);
                finish();
            }
        });

        configureInformacaoAgendaMedicaAssociadoCallback();

        SharedPreferences prefs = getSharedPreferences("DADOS_LOGIN", MODE_PRIVATE);
        cdDependente = prefs.getString("CodigoDependente", "");
        mat = prefs.getString("CodigoUsuario", "");
        perfilUsuario = prefs.getString("PerfilUsuario", "");
        nomeUsuario = prefs.getString("NomeUsuario", "");


        new APIClient().getRestService().getAngendaMedicaAssociado(mat,
                cdDependente, 0, callbackAgendaMedicaAssociado);
    }


    private void configureInformacaoAgendaMedicaAssociadoCallback() {
        callbackAgendaMedicaAssociado = new Callback<List<AgendaMedicaAssociado>>() {
            @Override
            public void success(List<AgendaMedicaAssociado> agendaMedicaAssociados, Response response) {
                recyclerView.setAdapter(new AgendamentoConsultaAdapter(ListAgendamentoConsultaActivity.this, agendaMedicaAssociados, OnclickButtonDelete()));
                spotsDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                spotsDialog.dismiss();
                Toast.makeText(ListAgendamentoConsultaActivity.this, "Falha ao conectar ao servidor", Toast.LENGTH_LONG).show();
            }
        };
    }

    private void configureInformacaoCancelamentoConsultaMedicaCallback() {
        callbackCancelamentoDeConsulta = new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Toast.makeText(ListAgendamentoConsultaActivity.this, "Sucesso cancelamento " + s, Toast.LENGTH_LONG).show();
                Intent i = new Intent(ListAgendamentoConsultaActivity.this, ListAgendamentoConsultaActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ListAgendamentoConsultaActivity.this, "Falha ao cancelar", Toast.LENGTH_LONG).show();
            }
        };
    }

    private AgendamentoConsultaAdapter.DeleteConsultaOnClickListener OnclickButtonDelete() {
        return new AgendamentoConsultaAdapter.DeleteConsultaOnClickListener() {
            @Override
            public void OnClickImageButtonDelete(View view, int index) {
                Toast.makeText(ListAgendamentoConsultaActivity.this, "Deletar", Toast.LENGTH_LONG).show();
                ConfirmaCancelamentoConsulta(view, index);
            }
        };
    }

    public void ConfirmaCancelamentoConsulta(final View view, int index) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        configureInformacaoCancelamentoConsultaMedicaCallback();
                        TextView tvId = (TextView) view.findViewById(R.id.tvId);
                        TextView tvIdAgenda = (TextView) view.findViewById(R.id.tvIdAgenda);
                        TextView tvDataHora = (TextView) view.findViewById(R.id.tvDataHora);
                        TextView tvLocalidade = (TextView) view.findViewById(R.id.tvLocalidade);
                        TextView tvEspecialidade = (TextView) view.findViewById(R.id.tvEspecialidade);
                        String assunto = "Id Agenda: " + " " + tvId.getText().toString() + " " + "Horário: " + " " + tvDataHora.getText().toString() + " " + tvLocalidade.getText().toString() + " " + "Especialidade: " + " " + tvEspecialidade.getText().toString() + " " + "CANCELAMENTO PELO MOBILE";

                        String localidade = Normalizer.normalize(tvLocalidade.getText().toString(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                        String especialidade = Normalizer.normalize(tvEspecialidade.getText().toString(), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

                         new APIClient().getRestService().setCancelamentoDeConsulta(mat, cdDependente, Integer.parseInt(tvId.getText().toString()), mat, mat, '0', nomeUsuario, "Cancelado", tvDataHora.getText().toString(), localidade, especialidade, tvIdAgenda.getText().toString() ,callbackCancelamentoDeConsulta);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirma o Cancelamento da Consulta?").setPositiveButton("Sim", dialogClickListener)
                .setNegativeButton("Não", dialogClickListener).show();
    }


}
