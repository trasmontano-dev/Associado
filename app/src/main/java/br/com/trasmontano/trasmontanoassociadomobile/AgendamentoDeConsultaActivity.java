package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.AgendaMedica;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.AgendaMedicaAssociado;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.AgendamentoMedicoWebParametros;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Associado;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.DadosConsulta;
import br.com.trasmontano.trasmontanoassociadomobile.network.APIClient;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AgendamentoDeConsultaActivity extends AppCompatActivity {
    Spinner spnEspecialidade;
    Spinner spnLocalidade;
    Spinner spnMedicos;
    int tipo = 0;
    SpotsDialog spotsDialog;
    ImageButton imbPeriodoInicio;
    ImageButton imbPeriodoFim;
    EditText etPeriodoInicio;
    EditText etPeriodoFim;
    Button btNovaConsulta;
    Button btFiltrar;
    private Callback<List<AgendamentoMedicoWebParametros>> callbackAgendamentoMedicoWebParametros;
    private Callback<Associado> callbackAgendamentoMedicoWebDadosAssociado;
    private Callback<List<DadosConsulta>> callbackAgendamentoMedicoWeb;

    private static List<AgendamentoMedicoWebParametros> lstEspecialidade;
    private static List<AgendamentoMedicoWebParametros> lstLocalidades;
    private static List<AgendamentoMedicoWebParametros> lstMedicos;
    public static String cdEspecialidade;
    public static String dsEspecialidade;
    public static String limiteConsAnual;


    public  static List<DadosConsulta> lstDadosConsulta;

    WebView wvGrafico;
    String strURL;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendamento_de_consulta);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.slide_esquerda, R.anim.slide_direita);

        spotsDialog = new SpotsDialog(this, R.style.LoaderCustom);

        spnEspecialidade = (Spinner) findViewById(R.id.spnEspecialidades);
        spnLocalidade = (Spinner) findViewById(R.id.spnLocalidade);
        spnMedicos = (Spinner) findViewById(R.id.spnMedicos);
        imbPeriodoInicio = (ImageButton) findViewById(R.id.imbPeriodoInicio);
        imbPeriodoFim = (ImageButton) findViewById(R.id.imbPeriodoFim);
        etPeriodoInicio = (EditText) findViewById(R.id.etPeriodoInicio);
        etPeriodoFim = (EditText) findViewById(R.id.etPeriodoFim);
        btNovaConsulta = (Button) findViewById(R.id.btNovaConsulta);
        btFiltrar = (Button) findViewById(R.id.btFiltrar);

        btFiltrar.requestFocus();


        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String dia = String.valueOf(day);
        String mes = String.valueOf(month + 1);
        String paddeddia = "00".substring(dia.length()) + dia;
        String paddedmes = "00".substring(mes.length()) + mes;

        etPeriodoInicio.setText(paddeddia + "/" + paddedmes + "/" + year);

        c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        dia = String.valueOf(day);
        mes = String.valueOf(month + 1);
        paddeddia = "00".substring(dia.length()) + dia;
        paddedmes = "00".substring(mes.length()) + mes;

        etPeriodoFim.setText(paddeddia + "/" + paddedmes + "/" + year);


        imbPeriodoInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraData(etPeriodoInicio);
            }
        });
        imbPeriodoFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraData(etPeriodoFim);
            }
        });

        spnEspecialidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                configureInformacaoAgendamentoMedicoWebParametrosCallback();
                if (position != 0) {
                    AgendamentoMedicoWebParametros a = lstEspecialidade.get(position - 1);
                    tipo = 1;

                    spotsDialog.show();

                    new APIClient().getRestService().getAgendamentoMedicoWebParametros((byte) 1,
                            Integer.parseInt(a.getCdDescricao()), 0, callbackAgendamentoMedicoWebParametros);

                    LimpaSpinerMedicos();
                } else {
                    LimpaSpinerLocalidade();
                    LimpaSpinerMedicos();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnLocalidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    AgendamentoMedicoWebParametros a = lstLocalidades.get(position - 1);
                    AgendamentoMedicoWebParametros a1 = lstEspecialidade.get(spnEspecialidade.getSelectedItemPosition() - 1);
                    tipo = 2;
                    spotsDialog.show();
                    new APIClient().getRestService().getAgendamentoMedicoWebParametros((byte) 2,
                            Integer.parseInt(a1.getCdDescricao()), Integer.parseInt(a.getCdDescricao()), callbackAgendamentoMedicoWebParametros);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btNovaConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AgendamentoDeConsultaActivity.this, AgendamentoDeConsultaActivity.class);
                startActivity(i);
            }
        });
        btFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spnEspecialidade.getSelectedItemPosition() == 0) {
                    Toast.makeText(AgendamentoDeConsultaActivity.this, "Selecione pelo menos uma especialidade", Toast.LENGTH_LONG).show();
                    return;
                }
                spotsDialog.show();
                SharedPreferences prefs = getSharedPreferences("DADOS_LOGIN", MODE_PRIVATE);
                String mat = prefs.getString("CodigoUsuario", "");
                String cdDependente = prefs.getString("CodigoDependente", "");

                configureCallbackAgendamentoMedicoWebDadosAssociado();

                new APIClient().getRestService().getAgendamentoMedicoWebDadosAssociado(mat,
                        cdDependente, callbackAgendamentoMedicoWebDadosAssociado);

            }
        });

        ArrayList lst = new ArrayList<String>();

        lst.add("TODAS");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AgendamentoDeConsultaActivity.this, android.R.layout.simple_spinner_item, lst);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnLocalidade.setAdapter(dataAdapter);

        lst.clear();

        lst.add("TODOS");
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnMedicos.setAdapter(dataAdapter);


        spotsDialog.show();

        configureInformacaoAgendamentoMedicoWebParametrosCallback();
        tipo = 0;
        new APIClient().getRestService().getAgendamentoMedicoWebParametros((byte) 0,
                0, 0, callbackAgendamentoMedicoWebParametros);

    }

    private void configureCallbackAgendamentoMedicoWeb() {
        callbackAgendamentoMedicoWeb = new Callback<List<DadosConsulta>>() {

            @Override
            public void success(List<DadosConsulta> dadosConsultas, Response response) {
                lstDadosConsulta = dadosConsultas;
                if (lstDadosConsulta.size() == 0) {
                    Toast.makeText(AgendamentoDeConsultaActivity.this, "Não há agenda disponível no momento para essa pesquisa", Toast.LENGTH_LONG).show();
                    spotsDialog.dismiss();
                    return;
                }
                Intent i = new Intent(AgendamentoDeConsultaActivity.this, ListConsultaActivity.class);
                startActivity(i);
                spotsDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                spotsDialog.dismiss();
                Toast.makeText(AgendamentoDeConsultaActivity.this, "Falha ao conectar ao servidor", Toast.LENGTH_LONG).show();
            }
        };
    }

    private void configureCallbackAgendamentoMedicoWebDadosAssociado() {
        callbackAgendamentoMedicoWebDadosAssociado = new Callback<Associado>() {
            @Override
            public void success(Associado associado, Response response) {
                if (associado.getSituacao().equalsIgnoreCase("A") || associado.getSituacao().equalsIgnoreCase("S")) {
                    spotsDialog.show();
                    String cdLocalidade = "0";
                    String cdMedico = "0";
                    AgendamentoMedicoWebParametros l = null;
                    AgendamentoMedicoWebParametros m = null;
                    AgendamentoMedicoWebParametros e = lstEspecialidade.get(spnEspecialidade.getSelectedItemPosition() - 1);
                    dsEspecialidade = e.getDsDescricao();
                    cdEspecialidade = e.getCdDescricao();

                    if (spnLocalidade.getSelectedItemPosition() != 0) {
                        l = lstLocalidades.get(spnLocalidade.getSelectedItemPosition() - 1);
                        cdLocalidade = l.getCdDescricao();
                    }
                    if (spnMedicos.getSelectedItemPosition() != 0) {
                        m = lstMedicos.get(spnMedicos.getSelectedItemPosition() - 1);
                        cdMedico = m.getCdDescricao();
                    }
                    configureCallbackAgendamentoMedicoWeb();

                    if (spnLocalidade.getSelectedItemPosition() == 0 && spnMedicos.getSelectedItemPosition() == 0) {
                        new APIClient().getRestService().getAgendamentoMedicoWeb(etPeriodoInicio.getText().toString().replace("/", "-"), etPeriodoFim.getText().toString().replace("/", "-"), Integer.parseInt(e.getCdDescricao()), 0, 0, associado.getSexo(), associado.getIdade(), associado.getTipo() == 1, Integer.parseInt(cdLocalidade), callbackAgendamentoMedicoWeb);
                    } else {
                        new APIClient().getRestService().getAgendamentoMedicoWeb(etPeriodoInicio.getText().toString().replace("/", "-"), etPeriodoFim.getText().toString().replace("/", "-"), Integer.parseInt(e.getCdDescricao()), Integer.parseInt(l.getCdDescricao()), Integer.parseInt(cdMedico), associado.getSexo(), associado.getIdade(), associado.getTipo() == 1, Integer.parseInt(cdLocalidade), callbackAgendamentoMedicoWeb);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                spotsDialog.dismiss();
                Toast.makeText(AgendamentoDeConsultaActivity.this, "Falha ao conectar ao servidor", Toast.LENGTH_LONG).show();
            }
        };
    }

    private void configureInformacaoAgendamentoMedicoWebParametrosCallback() {
        callbackAgendamentoMedicoWebParametros = new Callback<List<AgendamentoMedicoWebParametros>>() {
            @Override
            public void success(List<AgendamentoMedicoWebParametros> agendamentoMedicoWebParametroses, Response response) {
                //lstEspecialidade = agendamentoMedicoWebParametroses;
                ArrayList lst = new ArrayList<String>();

                if (tipo == 0) {
                    lst.add("ESCOLHA UMA OPÇÃO");
                } else if (tipo == 1) {
                    lst.add("TODAS");
                } else if (tipo == 2) {
                    lst.add("TODOS(AS)");
                }

                for (AgendamentoMedicoWebParametros a : agendamentoMedicoWebParametroses) {
                    lst.add(a.getDsDescricao());
                }

                if (tipo == 0) {
                    lstEspecialidade = agendamentoMedicoWebParametroses;
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AgendamentoDeConsultaActivity.this, android.R.layout.simple_spinner_item, lst);

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spnEspecialidade.setAdapter(dataAdapter);
                } else if (tipo == 1) {
                    lstLocalidades = agendamentoMedicoWebParametroses;
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AgendamentoDeConsultaActivity.this, android.R.layout.simple_spinner_item, lst);

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spnLocalidade.setAdapter(dataAdapter);
                } else if (tipo == 2) {
                    lstMedicos = agendamentoMedicoWebParametroses;
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AgendamentoDeConsultaActivity.this, android.R.layout.simple_spinner_item, lst);

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spnMedicos.setAdapter(dataAdapter);
                }
                spotsDialog.dismiss();
            }


            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(AgendamentoDeConsultaActivity.this, "Falha ao conectar o servidor", Toast.LENGTH_LONG);
                spotsDialog.dismiss();
            }
        };
    }

    public void LimpaSpinerLocalidade() {
        ArrayList lst = new ArrayList<String>();
        lst.add("TODAS");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AgendamentoDeConsultaActivity.this, android.R.layout.simple_spinner_item, lst);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnLocalidade.setAdapter(dataAdapter);    }

    public void LimpaSpinerMedicos() {
        ArrayList lst = new ArrayList<String>();
        lst.add("TODOS(AS)");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AgendamentoDeConsultaActivity.this, android.R.layout.simple_spinner_item, lst);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnMedicos.setAdapter(dataAdapter);
    }

    public void MostraData(EditText editText) {
        DialogFragment newFragment = new DataPickerFragment(editText);
        newFragment.show(getSupportFragmentManager(), "dataPicker");
    }
}
