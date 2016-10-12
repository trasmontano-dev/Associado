package br.com.trasmontano.trasmontanoassociadomobile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.network.APIClient;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RedeCredenciadaOrientadorInf extends AppCompatActivity {

    private Callback<List<String>> callbackEspecialidadesOrientadorWebApiMobile;

    ListView lvInformacoes;
    TextView tvNomeFantasiaInf;
    TextView tvCnpjCpfInf;
    TextView tvTipoAtendimentoInf;
    TextView tvEmailInf;
    TextView tvSiteInf;
    ScrollView scrInformacoes;
    private ArrayList Especialidades;
    private SpotsDialog spotsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rede_credenciada_orientador_inf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spotsDialog = new SpotsDialog(RedeCredenciadaOrientadorInf.this, R.style.LoaderCustom);
        lvInformacoes = (ListView)findViewById(R.id.lvEspecialidades);
        tvNomeFantasiaInf = (TextView) findViewById(R.id.tvNomeFantasiaInf);
        tvCnpjCpfInf = (TextView)findViewById(R.id.tvCnpjCpfInf);
        tvTipoAtendimentoInf = (TextView)findViewById(R.id.tvTipoAtendimentoInf);
        tvEmailInf = (TextView)findViewById(R.id.tvEmailInf);
        tvSiteInf = (TextView)findViewById(R.id.tvSiteInf);
        scrInformacoes = (ScrollView)findViewById(R.id.scrInformacoes);
        Especialidades = new ArrayList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        scrInformacoes.smoothScrollTo(0,0);
        ArrayList<String> conteudo = new ArrayList<>();
        conteudo = getIntent().getStringArrayListExtra("informacao");
        int codCredenciado = Integer.parseInt(conteudo.get(0).toString());
        int codFilial = Integer.parseInt(conteudo.get(1).toString());
        int cdDescricao = Integer.parseInt(conteudo.get(6).toString());
        String nomeFantasia = conteudo.get(2).toString();
        String CnpjCpf = conteudo.get(5).toString();
        String TipoEstabelecimento = conteudo.get(4).toString();
        String Email = conteudo.get(8).toString();
        String Site = conteudo.get(7).toString();
        tvNomeFantasiaInf.setText(nomeFantasia);
        tvCnpjCpfInf.setText(CnpjCpf);
        tvTipoAtendimentoInf.setText(TipoEstabelecimento);
        tvEmailInf.setText(Email);
        tvSiteInf.setText(Site);

        this.callbackConfigureEspecialidades(codCredenciado,codFilial,cdDescricao);
        new APIClient().getRestService().especialidadesOrientadorWebApiMobile(codCredenciado,codFilial,cdDescricao,callbackEspecialidadesOrientadorWebApiMobile);

    }
    private void callbackConfigureEspecialidades(int codigoCredenciado, int codigoFilial, int cdDescricao)
    {
        callbackEspecialidadesOrientadorWebApiMobile = new Callback<List<String>>() {
            @Override
            public void success(List<String> strings, Response response) {
                for (String t : strings)
                {
                    Especialidades.add(t);
                }
                ArrayAdapter planosAdapter = new ArrayAdapter(RedeCredenciadaOrientadorInf.this, android.R.layout.simple_spinner_item, Especialidades);
                planosAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
                lvInformacoes.setAdapter(planosAdapter);
                this.setListViewHeightBasedOnChildren(lvInformacoes);
            }
            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
                Toast.makeText(RedeCredenciadaOrientadorInf.this, "Falha ao conectar no servidor", Toast.LENGTH_LONG).show();
                spotsDialog.dismiss();
            }
            public void setListViewHeightBasedOnChildren(ListView listView) {
                ListAdapter listAdapter = listView.getAdapter();
                if (listAdapter == null) {
                    //pre-condition
                    return;
                }
                int totalHeight = 0;
                for (int i = 0; i < listAdapter.getCount(); i++) {
                    View listItem = listAdapter.getView(i, null, listView);
                    listItem.measure(0, 0);
                    totalHeight += listItem.getMeasuredHeight();
                }
                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 20;
                listView.setLayoutParams(params);
                listView.requestLayout();
            }
        };
    }
}
