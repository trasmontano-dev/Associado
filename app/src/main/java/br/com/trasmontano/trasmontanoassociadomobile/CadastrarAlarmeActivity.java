package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import net.colindodd.toggleimagebutton.ToggleImageButton;

import java.io.File;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.Alarme;
import br.com.trasmontano.trasmontanoassociadomobile.Util.AlarmUtil;
import br.com.trasmontano.trasmontanoassociadomobile.Util.NotificationUtil;
import livroandroid.lib.utils.FileUtils;
import retrofit.RestAdapter;
import se.emilsjolander.sprinkles.Query;

public class CadastrarAlarmeActivity extends AppCompatActivity {

    private static final int FILE_SELECT_CODE = 0;
    ImageButton btMp3;
    LinearLayout lnMp3;
    EditText etMp3;
    ImageButton ibtIntervaloDe;
    LinearLayout layIntervaloDe;
    ImageButton ibtDataInicio;
    LinearLayout layDataInicio;
    ImageButton ibtHoraInicio;
    LinearLayout layHoraInicio;
    ToggleImageButton tibSound;
    ToggleImageButton tibVibration;
    Spinner spinner;
    EditText etDataInicio;
    EditText etHoraInicio;
    EditText etHoraInvervalo;
    Button btCadastrarAlarme;
    TextInputLayout tiNomePaciente;
    TextInputLayout tiNomeMedicamento;
    TextInputLayout tiDescMedicamento;
    TextInputLayout tiDataInicio;
    TextInputLayout tiHoraInicio;
    TextInputLayout tiIntervaloDe;
    EditText etQuantidade;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_alarme);

       /* Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar1);
        myToolbar.setTitleTextColor(android.graphics.Color.WHITE);

        setSupportActionBar(myToolbar);*/

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.slide_esquerda, R.anim.slide_direita);
        lnMp3 = (LinearLayout) findViewById(R.id.lnMp3);
        etMp3 = (EditText) findViewById(R.id.etMp3);
        btMp3 = (ImageButton) findViewById(R.id.imbMp3);
        tiNomePaciente = (TextInputLayout) findViewById(R.id.tiNomePaciente);
        tiNomeMedicamento = (TextInputLayout) findViewById(R.id.tiNomeMedicamento);
        tiDescMedicamento = (TextInputLayout) findViewById(R.id.tiDescricaoMedicamento);
        tiDataInicio = (TextInputLayout) findViewById(R.id.tiDataInicio);
        tiHoraInicio = (TextInputLayout) findViewById(R.id.tiHoraInicio);
        tiIntervaloDe = (TextInputLayout) findViewById(R.id.tiIntervaloDe);
        btCadastrarAlarme = (Button) findViewById(R.id.btCadastrarAlarme);
        ibtIntervaloDe = (ImageButton) findViewById(R.id.imbIntervaloDe);
        ibtHoraInicio = (ImageButton) findViewById(R.id.imbHoraInicio);
        ibtDataInicio = (ImageButton) findViewById(R.id.imbDataInicio);
        layIntervaloDe = (LinearLayout) findViewById(R.id.layIntervaloDe);
        layDataInicio = (LinearLayout) findViewById(R.id.layDataInicio);
        layHoraInicio = (LinearLayout) findViewById(R.id.layHoraInicio);
        etHoraInvervalo = (EditText) findViewById(R.id.etHoraInicio);
        etDataInicio = (EditText) findViewById(R.id.etDataInicio);
        etHoraInvervalo = (EditText) findViewById(R.id.etIntervaloDe);
        etHoraInicio = (EditText) findViewById(R.id.etHoraInicio);
        spinner = (Spinner) findViewById(R.id.spinner);
        etQuantidade = (EditText) findViewById(R.id.etQuantidade);
        imageView = (ImageView) findViewById(R.id.imagem);

        imageView.requestFocus();

        ArrayList lst = new ArrayList<String>();

        lst.add("Comprimido");
        lst.add("Gotas");
        lst.add("Sache");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(CadastrarAlarmeActivity.this, android.R.layout.simple_spinner_item, lst);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);


        tibSound = (ToggleImageButton) findViewById(R.id.timbSound);
        tibVibration = (ToggleImageButton) findViewById(R.id.timbVibration);


        lnMp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        btMp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        btCadastrarAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Alarme a = new Alarme();
                a.setNomeMedicamento(tiNomeMedicamento.getEditText().getText().toString());
                a.setDescricaoMedicamento(tiDescMedicamento.getEditText().getText().toString());
                a.setDosagem(spinner.getSelectedItem().toString());
                a.setQuantidade(etQuantidade.getText().toString());
                a.setDataInicio(tiDataInicio.getEditText().getText().toString());
                a.setHoraInicio(tiHoraInicio.getEditText().getText().toString());
                a.setIntervaloDe(tiIntervaloDe.getEditText().getText().toString());
                a.setNomePaciente(tiNomePaciente.getEditText().getText().toString());
                a.setNomeMusica(etMp3.getText().toString());
                if (tibSound.isChecked())
                    a.setTocar(1);
                else
                    a.setTocar(0);
                if (tibVibration.isChecked())
                    a.setVibrar(1);
                else
                    a.setVibrar(0);

                a.setAtivo(1);

                String[] parts = a.getHoraInicio().split(":");
                String[] partsIntervalo = a.getIntervaloDe().split(":");
                String[] partsData = a.getDataInicio().split("/");

                int horaInicio = Integer.parseInt(parts[0]);
                int minutoInicio = Integer.parseInt(parts[1]);
                int horaIntervalo = Integer.parseInt(partsIntervalo[0]);
                int minutoIntervalo = Integer.parseInt(partsIntervalo[1]);
                int dia = Integer.parseInt(partsData[0]);
                int mes = Integer.parseInt(partsData[1]);
                int ano = Integer.parseInt(partsData[2]);


                long alarmeInicio = retornaHoraMillisecond(horaInicio, minutoInicio, dia, mes, ano);
                //long alarmeIntervalo = retornaHoraMillisecond(horaIntervalo, minutoIntervalo);
                long alarmeIntervalo = retornaIntervaloMillisecond(horaIntervalo, minutoIntervalo);

                if (alarmeInicio == 0) {
                    Toast.makeText(CadastrarAlarmeActivity.this, "Voce esta agendando um alame com Data/Hora anterior a data atual", Toast.LENGTH_LONG).show();
                    return;
                }
                int quantidade = 0;
                if (horaIntervalo != 0)
                    quantidade = 24 / horaIntervalo;

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, horaInicio);
                calendar.set(Calendar.MINUTE, minutoInicio);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.DAY_OF_MONTH, dia);
                calendar.set(Calendar.MONTH, mes - 1);
                calendar.set(Calendar.YEAR, ano);

                String horarios = "";


                for (int i = 0; i < quantidade; i++) {
                    int hours = calendar.get(Calendar.HOUR_OF_DAY);
                    int minutes = calendar.get(Calendar.MINUTE);

                    String hora = String.valueOf(hours);
                    String minuto = String.valueOf(minutes);

                    horarios += String.valueOf("00".substring(hora.length()) + hora + ":") + "00".substring(minuto.length()) + minuto + " - ";

                    calendar.add(Calendar.HOUR_OF_DAY, horaIntervalo);
                    calendar.add(Calendar.MINUTE, minutoIntervalo);
                }
                if (horaIntervalo != 0)
                    a.setHorarios(horarios.substring(0, horarios.length() - 3));
                else
                    a.setHorarios("");

                if (a.save()) {

                    List<Alarme> lst = Query.all(Alarme.class).get().asList();
                    // Alarme alarme = lst.get(lst.size() - 1);

                    int id = (int) a.getId();

                    Intent intent = new Intent(AlarmeBroadcastReceiver.ACTION);
                    intent.putExtra("id", id);
                    intent.putExtra("paciente", a.getNomePaciente());
                    intent.putExtra("medicamento", a.getNomeMedicamento());
                    intent.putExtra("mp3", a.getNomeMusica());
                    intent.putExtra("vibrar", a.getVibrar());
                    intent.putExtra("tocar", a.getTocar());
                    AlarmUtil.scheduleRepeat(CadastrarAlarmeActivity.this, intent, alarmeInicio, alarmeIntervalo, id);
                    Intent i = new Intent(CadastrarAlarmeActivity.this, ListAlarmeActivity.class);

                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(CadastrarAlarmeActivity.this, "Falha ao Cadastrar Alarme", Toast.LENGTH_LONG).show();
                }

            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date hora = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
        String horaFormatada = sdf.format(hora);

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        Date data = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
        String dataFormatada = sdf1.format(data);

        tiHoraInicio.getEditText().setText(horaFormatada);
        tiDataInicio.getEditText().setText(dataFormatada);
        /*etDataInicio.setInputType(InputType.TYPE_NULL);
        etHoraInicio.setInputType(InputType.TYPE_NULL);
        etHoraInvervalo.setInputType(InputType.TYPE_NULL);*/

        ibtIntervaloDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraHora(etHoraInvervalo);
            }
        });

        layIntervaloDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraHora(etHoraInvervalo);
            }
        });

        ibtDataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraData();
            }
        });

        layDataInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraData();
            }
        });

        ibtHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraHora(etHoraInicio);
            }
        });

        layHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostraHora(etHoraInicio);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String path = "";
                    try {
                        path = getPath(this, uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    if (path != null) {

                        String extensao = path.substring(path.length() - 4, path.length());

                        if (extensao.equalsIgnoreCase(".mp3"))
                            etMp3.setText(path);
                        else
                            Toast.makeText(this, "Favor selecionar somente arquivo .mp3", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/mp3");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Por favor instale File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public long retornaHoraMillisecond(int hora, int minuto, int dia, int mes, int ano) {
        Calendar atual = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minuto);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.DAY_OF_MONTH, dia);
        calendar.set(Calendar.MONTH, mes - 1);
        calendar.set(Calendar.YEAR, ano);

        long time = calendar.getTimeInMillis();

        if (atual.after(calendar))
            return 0;
        else
            return time;

    }

    public long retornaIntervaloMillisecond(int hora, int minuto) {
        Calendar calendar = Calendar.getInstance();
        long time;
        if (hora != 0) {
            calendar.set(Calendar.HOUR_OF_DAY, hora);
            calendar.set(Calendar.MINUTE, minuto);
            time = calendar.getTimeInMillis();
        } else {
            time = minuto * 60 * 1000;
        }

        return time;
    }


    public void MostraData() {
        DialogFragment newFragment = new DataPickerFragment(etDataInicio);
        newFragment.show(getSupportFragmentManager(), "dataPicker");
    }

    public void MostraHora(EditText tvHora) {
        DialogFragment newFragment = new TimerPickerFragment(tvHora);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
