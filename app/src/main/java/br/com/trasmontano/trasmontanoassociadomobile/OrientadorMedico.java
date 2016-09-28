package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.DTOParametrosOrientador;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.OrientadorMedicoDTO;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.OrientadorMedicoDTOPesquisa;
import br.com.trasmontano.trasmontanoassociadomobile.network.APIClient;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class OrientadorMedico extends AppCompatActivity implements Serializable {

    private Callback<List<OrientadorMedicoDTO>> callbackDescricaoPlanos;

    private Callback<List<OrientadorMedicoDTO>> callbackDescricaoDasRegioes;

    private Callback<List<OrientadorMedicoDTO>> callbackDescricaoDosMunicipios;

    private Callback<List<OrientadorMedicoDTO>> callbackDescricaoDasEspecialidade;

    private Callback<List<OrientadorMedicoDTOPesquisa>> callbackOrientadorMedicoMobile;

    private DTOParametrosOrientador dtoParametrosOrientador;
    private Spinner spnPlano;
    private Spinner spnRegiao;
    private Spinner spnMunicipio;
    //private Spinner spnBairro;
    private Spinner spnEspecialidade;
    private Spinner spnTipoServico;

    private String codPlano;
    private String codRegiao;
    private String codMunicipio;
    //private String codBairro;
    private String codEspecialidade;
    private String codTipoServico;

    private EditText edtNomeCnpjFantasia;
    private EditText edtNumeroCnpj;
    private EditText edtNumeroConselhoRegional;

    private Button btnPesquisar;
    private Button btnVoltar;

    private ArrayList planos;
    private ArrayList regioes;
    private ArrayList municipios;
    //private ArrayList bairros;
    private ArrayList especialidades;
    private ArrayList tipoServicos;
    private ArrayList<OrientadorMedicoDTOPesquisa> pesquisaOrientadorMedico;

    private OrientadorMedicoDTO municipio;
    private OrientadorMedicoDTO especialidade;
    private String nome;
    private SpotsDialog spotsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientador_medico);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //overridePendingTransition(R.anim.slide_direita, R.anim.slide_esquerda);

        spotsDialog = new SpotsDialog(OrientadorMedico.this, R.style.LoaderCustom);
        spotsDialog.show();
        spnPlano = (Spinner)findViewById(R.id.spnPlano);
        spnRegiao = (Spinner)findViewById(R.id.spnRegiao);
        spnMunicipio = (Spinner)findViewById(R.id.spnMunicipio);
        spnEspecialidade = (Spinner)findViewById(R.id.spnEspecialidade);
        spnTipoServico = (Spinner)findViewById(R.id.spnTipoServico);

        edtNomeCnpjFantasia = (EditText)findViewById(R.id.edtNomeCnpjFantasia);
        edtNumeroCnpj = (EditText)findViewById(R.id.edtNumeroCnpj);
        edtNumeroConselhoRegional = (EditText)findViewById(R.id.edtConselhoRegional);

        planos = new ArrayList();
        OrientadorMedicoDTO p = new OrientadorMedicoDTO();
        p.setDsCategoria("Escolha...");
        p.setCdCategoria("-1");
        planos.add(p);
        this.configurecallbackDescricaoPlanos();
        new APIClient().getRestService().getDescricaoPlanos(callbackDescricaoPlanos);

        //spnRegiao
        regioes = new ArrayList();
        OrientadorMedicoDTO r = new OrientadorMedicoDTO();
        r.setCdCategoria("-1");
        r.setDsCategoria("Todas");
        regioes.add(r);
        spnRegiao.setEnabled(false);

        //spnMunicipio
        municipios = new ArrayList();
        municipio = new OrientadorMedicoDTO();
        municipio.setCdCategoria("-1");
        municipio.setDsCategoria("Todos");
        municipios.add(municipio);
        spnMunicipio.setEnabled(false);

        //spnEspecialidade
        especialidades = new ArrayList();
        especialidade = new OrientadorMedicoDTO();
        especialidade.setCdCategoria("-1");
        especialidade.setDsCategoria("Escolha...");
        especialidades.add(especialidade);

        configurecallbackDescricaoDasEspecialidade();
        new APIClient().getRestService().getDescricaoDasEspecialidade(callbackDescricaoDasEspecialidade);

        //spnTipoServico
        tipoServicos = new ArrayList();
        OrientadorMedicoDTO s = new OrientadorMedicoDTO();
        s.setCdCategoria("-1");
        s.setDsCategoria("Tipo de Serviço");
        tipoServicos.add(s);

        OrientadorMedicoDTO s1 = new OrientadorMedicoDTO();
        s1.setCdCategoria("1");
        s1.setDsCategoria("Clínicas");
        tipoServicos.add(s1);

        OrientadorMedicoDTO s2 = new OrientadorMedicoDTO();
        s2.setCdCategoria("2");
        s2.setDsCategoria("Hospitais");
        tipoServicos.add(s2);

        OrientadorMedicoDTO s3 = new OrientadorMedicoDTO();
        s3.setCdCategoria("1");
        s3.setDsCategoria("Exames / SADT");
        tipoServicos.add(s3);

        ArrayAdapter tipoServicosAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, tipoServicos);
        tipoServicosAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        spnTipoServico.setAdapter(tipoServicosAdapter);

        spnPlano.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                //nome = parent.getItemAtPosition(posicao).toString();
                OrientadorMedicoDTO p = (OrientadorMedicoDTO) parent.getSelectedItem();
                codPlano = p.getCdCategoria().toString();

                if (!codPlano.equals("-1")) {
                    configurecallbackDescricaoDasRegioes(codPlano);
                    new APIClient().getRestService().getDescricaoDasRegioes(codPlano,callbackDescricaoDasRegioes);
                    spnRegiao.setEnabled(true);
                    ArrayAdapter regioesAdapter = new ArrayAdapter(OrientadorMedico.this, android.R.layout.simple_spinner_item, regioes);
                    regioesAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
                    spnRegiao.setAdapter(regioesAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnRegiao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                //nome = parent.getItemAtPosition(posicao).toString();
                OrientadorMedicoDTO p = (OrientadorMedicoDTO) parent.getSelectedItem();
                codRegiao = p.getCdCategoria().toString();

                if (!codRegiao.equals("-1")) {
                    configurecallbackDescricaoDosMunicipios(codPlano, codRegiao);
                    new APIClient().getRestService().getDescricaoDosMunicipios(codPlano, codRegiao, callbackDescricaoDosMunicipios);
                    spnMunicipio.setEnabled(true);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnMunicipio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                //nome = parent.getItemAtPosition(posicao).toString();
                OrientadorMedicoDTO p = (OrientadorMedicoDTO) parent.getSelectedItem();
                codMunicipio = p.getCdCategoria().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnEspecialidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                //nome = parent.getItemAtPosition(posicao).toString();
                OrientadorMedicoDTO p = (OrientadorMedicoDTO) parent.getSelectedItem();
                codEspecialidade = p.getCdCategoria().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnTipoServico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                //pega nome pela posição
                //nome = parent.getItemAtPosition(posicao).toString();
                OrientadorMedicoDTO p = (OrientadorMedicoDTO) parent.getSelectedItem();
                codTipoServico = p.getCdCategoria().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnPesquisar = (Button)findViewById(R.id.btPesquisar);
        btnVoltar = (Button)findViewById(R.id.btVoltar);
        btnPesquisar.setOnClickListener(new OnClickPesquisarListener());
        btnVoltar.setOnClickListener(new OnClickVoltarListener());
    }

    public class OnClickPesquisarListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            String nomeCnpjFantasia = edtNomeCnpjFantasia.getText().toString();
            String numeroCnpj = edtNumeroCnpj.getText().toString();
            String tipoServico = edtNumeroConselhoRegional.getText().toString();
            if (codPlano.equals("-1"))
            {
                AlertDialog.Builder b = new AlertDialog.Builder(OrientadorMedico.this);
                b.setMessage("Campo plano é obrigatório!");
                b.setNeutralButton("OK",null);
                b.show();
            }
            else if (codEspecialidade.equals("-1"))
            {
                AlertDialog.Builder b = new AlertDialog.Builder(OrientadorMedico.this);
                b.setMessage("Campo especialidade é obrigatório!");
                b.setNeutralButton("OK",null);
                b.show();
            }
            else
            {
                spotsDialog.show();
                pesquisaOrientadorMedico = new ArrayList();
                String textoRegiao;
                String textoMunicipio;
                String tipoAtendimento;
                if (codRegiao.equals("-1"))
                { textoRegiao = ""; }
                else{textoRegiao = spnRegiao.getSelectedItem().toString();}
                if (codMunicipio.equals("-1"))
                {textoMunicipio = "";}
                else{textoMunicipio = spnMunicipio.getSelectedItem().toString();}
                if (spnTipoServico.getSelectedItem().toString().equals("Tipo de Serviço"))
                {tipoAtendimento = "";}
                else{tipoAtendimento = spnTipoServico.getSelectedItem().toString();}

                dtoParametrosOrientador = new DTOParametrosOrientador();

                dtoParametrosOrientador.setRegiao(textoRegiao);
                dtoParametrosOrientador.setCidade(textoMunicipio);
                dtoParametrosOrientador.setBairro("");
                dtoParametrosOrientador.setDsCategoria(spnPlano.getSelectedItem().toString());
                dtoParametrosOrientador.setEspecialidade(spnEspecialidade.getSelectedItem().toString());
                dtoParametrosOrientador.setTipoAtendimento(tipoAtendimento);
                dtoParametrosOrientador.setNomeFantasia(edtNomeCnpjFantasia.getText().toString());
                dtoParametrosOrientador.setcNPJCPF(edtNumeroCnpj.getText().toString());
                dtoParametrosOrientador.setcRM(edtNumeroConselhoRegional.getText().toString());
                dtoParametrosOrientador.setOrdem("2");

                configurecallbackOrientadorMedicoMobile();
                new APIClient().getRestService().getGridOrientadorMedicoMobile(dtoParametrosOrientador,callbackOrientadorMedicoMobile);
            }
        }
    }
    public class OnClickVoltarListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            finish();
        }
    }
    private void configurecallbackOrientadorMedicoMobile()
    {
        callbackOrientadorMedicoMobile = new Callback<List<OrientadorMedicoDTOPesquisa>>() {
            @Override
            public void success(List<OrientadorMedicoDTOPesquisa> orientadorMedicoDTOPesquisas, Response response) {

                for (OrientadorMedicoDTOPesquisa dto : orientadorMedicoDTOPesquisas)
                {
                    pesquisaOrientadorMedico.add(dto);
                }
                if (orientadorMedicoDTOPesquisas.size() > 0)
                {
                    Intent i = new Intent(OrientadorMedico.this, RedeCredenciadaActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("redeCredenciada",pesquisaOrientadorMedico);
                    i.putExtras(b);
                    spotsDialog.dismiss();
                    startActivity(i);
                }
                else
                {
                    spotsDialog.dismiss();
                    AlertDialog.Builder b = new AlertDialog.Builder(OrientadorMedico.this);
                    b.setMessage("Não encontrado nada com esses parametros informados!");
                    b.setNeutralButton("OK", null);
                    b.show();
                }
            }
            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
                Toast.makeText(OrientadorMedico.this, "Falha ao conectar no servidor", Toast.LENGTH_LONG).show();
            }
        };
    }
    private void configurecallbackDescricaoPlanos() {

        callbackDescricaoPlanos = new Callback<List<OrientadorMedicoDTO>>() {

            @Override
            public void success(List<OrientadorMedicoDTO> orientadorMedicoDTOs, Response response) {
                for (OrientadorMedicoDTO tweet : orientadorMedicoDTOs)
                {
                    planos.add(tweet);
                }
                ArrayAdapter planosAdapter = new ArrayAdapter(OrientadorMedico.this, android.R.layout.simple_spinner_item, planos);
                planosAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
                spnPlano.setAdapter(planosAdapter);
            }
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
                Toast.makeText(OrientadorMedico.this, "Falha ao conectar no servidor", Toast.LENGTH_LONG).show();
                spotsDialog.dismiss();
            }

        };
    }

    private void configurecallbackDescricaoDasEspecialidade()
    {
        callbackDescricaoDasEspecialidade = new Callback<List<OrientadorMedicoDTO>>() {
            @Override
            public void success(List<OrientadorMedicoDTO> orientadorMedicoDTOs, Response response) {
                for (OrientadorMedicoDTO tweet : orientadorMedicoDTOs)
                {
                    especialidades.add(tweet);
                }
                ArrayAdapter especialidadesAdapter = new ArrayAdapter(OrientadorMedico.this, android.R.layout.simple_spinner_item, especialidades);
                especialidadesAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
                spnEspecialidade.setAdapter(especialidadesAdapter);
                spotsDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
                Toast.makeText(OrientadorMedico.this, "Falha ao conectar no servidor", Toast.LENGTH_LONG).show();
                spotsDialog.dismiss();
            }
        };

    }
    private void configurecallbackDescricaoDasRegioes(String codPlano)
    {
        callbackDescricaoDasRegioes = new Callback<List<OrientadorMedicoDTO>>() {
            @Override
            public void success(List<OrientadorMedicoDTO> orientadorMedicoDTOs, Response response) {
                for (OrientadorMedicoDTO tweet : orientadorMedicoDTOs)
                {
                    regioes.add(tweet);
                }
                ArrayAdapter municipiosAdapter = new ArrayAdapter(OrientadorMedico.this, android.R.layout.simple_spinner_item, municipios);
                municipiosAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
                spnMunicipio.setAdapter(municipiosAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
                Toast.makeText(OrientadorMedico.this, "Falha ao conectar no servidor", Toast.LENGTH_LONG).show();
            }
        };
    }
    private void configurecallbackDescricaoDosMunicipios(String codPlano, String codRegiao)
    {
        callbackDescricaoDosMunicipios = new Callback<List<OrientadorMedicoDTO>>() {
            @Override
            public void success(List<OrientadorMedicoDTO> orientadorMedicoDTOs, Response response) {
                for (OrientadorMedicoDTO tweet : orientadorMedicoDTOs)
                {
                    municipios.add(tweet);
                }
                ArrayAdapter municipiosAdapter = new ArrayAdapter(OrientadorMedico.this, android.R.layout.simple_spinner_item, municipios);
                municipiosAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
                spnMunicipio.setAdapter(municipiosAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
                Toast.makeText(OrientadorMedico.this, "Falha ao conectar no servidor", Toast.LENGTH_LONG).show();
            }
        };

    }
}

