package br.com.trasmontano.trasmontanoassociadomobile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.AgendaMedicaAssociado;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Associado;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.DadosConsulta;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Login;
import br.com.trasmontano.trasmontanoassociadomobile.network.APIClient;
import dmax.dialog.SpotsDialog;
import livroandroid.lib.utils.ImageResizeUtils;
import livroandroid.lib.utils.SDCardUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.emilsjolander.sprinkles.Query;

public class CadastarActivity extends AppCompatActivity {
    private Button btCadastrar;
    private TextInputLayout tiMatricula;
    private TextInputLayout tiCPF;
    private TextInputLayout tiDataNascimento;
    private TextInputLayout tiEmail;
    private TextInputLayout tiConfirmaEmail;
    private TextInputLayout tiSenha;
    private TextInputLayout tiRepetirSenha;
    private TextInputLayout tiLembrarSenha;
    private CircularImageView imageView;
    private File file;
    private ImageButton imbData;
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private Callback<String> callbackCallbackLoginAssociado;
    SpotsDialog spotsDialog;
    private Callback<String> callbackUsuarioExiste;
    private Callback<Login> callbackUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imageView = (CircularImageView) findViewById(R.id.imagem);
        btCadastrar = (Button) findViewById(R.id.btCadastrarAssociado);
        spotsDialog = new SpotsDialog(this, R.style.LoaderCustom);
        tiMatricula = (TextInputLayout) findViewById(R.id.tiMatricula);

        tiCPF = (TextInputLayout) findViewById(R.id.tiCPF);
        tiDataNascimento = (TextInputLayout) findViewById(R.id.tiDataNascimento);
        tiEmail = (TextInputLayout) findViewById(R.id.tiEmail);
        tiConfirmaEmail = (TextInputLayout) findViewById(R.id.tiConfirmaEmail);
        tiSenha = (TextInputLayout) findViewById(R.id.tiSenha);
        tiRepetirSenha = (TextInputLayout) findViewById(R.id.tiRepetirSenha);
        tiLembrarSenha = (TextInputLayout) findViewById(R.id.tiLembrarSenha);

        ImageButton b = (ImageButton) findViewById(R.id.btAbrirCamera);

        imbData = (ImageButton) findViewById(R.id.imbData);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        imbData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                //get current date time with Date()
                Date date = new Date();
                //System.out.println(dateFormat.format(date));

                file = SDCardUtils.getPrivateFile(getBaseContext(), dateFormat.format(date) + ".jpg", Environment.DIRECTORY_PICTURES);
                // Chama a intent informando o arquivo para salvar a foto
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(i, 0);
            }
        });

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotsDialog.show();

                if (VaidarDados()) {
                    configureLoginAssociadoCallback();

                    String cdDependente = "00";

                    Associado a = null;

                    if (tiMatricula.getEditText().getText().toString().length() > 6) {
                        cdDependente = tiMatricula.getEditText().getText().toString().substring(6, 8);

                        a = Query.one(Associado.class, "select * from associado where usuario=?", tiMatricula.getEditText().getText().toString() + cdDependente).get();
                    } else {
                        a = Query.one(Associado.class, "select * from associado where usuario=?", tiMatricula.getEditText().getText().toString()).get();
                    }
                    if (a == null) {
                        new APIClient().getRestService().setLoginAssociado(tiSenha.getEditText().getText().toString(), tiRepetirSenha.getEditText().getText().toString(), tiLembrarSenha.getEditText().getText().toString(), tiDataNascimento.getEditText().getText().toString(), tiMatricula.getEditText().getText().toString(), tiEmail.getEditText().getText().toString(), tiRepetirSenha.getEditText().getText().toString(), tiCPF.getEditText().getText().toString(), cdDependente, callbackCallbackLoginAssociado);

                    } else {
                        spotsDialog.dismiss();
                        Toast.makeText(CadastarActivity.this, "Usuário já possui cadastro.", Toast.LENGTH_LONG).show();
                    }


                   /* Associado a = new Associado();
                    a.setUsuario(tiMatricula.getEditText().getText().toString());
                    a.setCpf(tiCPF.getEditText().getText().toString());
                    a.setDataNascimento(tiDataNascimento.getEditText().getText().toString());
                    a.setEmail(tiEmail.getEditText().getText().toString());
                    a.setLembreteSenha(tiLembrarSenha.getEditText().getText().toString());

                    if (file != null && file.exists()) {
                        a.setCaminhoImagem(file.toString());
                    }                   */
                }

            }
        });


        if (savedInstanceState != null) {
            // Se girou a tela recupera o estado
            file = (File) savedInstanceState.getSerializable("file");
            showImage(file);
        }

        imageView.requestFocus();

    }

    private void configureInformacaoAssociadoCallback() {
        callbackUsuario = new Callback<Login>() {

            @Override
            public void success(Login login, Response response) {

                Associado a = new Associado();
                a.setUsuario(tiMatricula.getEditText().getText().toString());
                a.setCpf(tiCPF.getEditText().getText().toString());
                a.setDataNascimento(tiDataNascimento.getEditText().getText().toString());
                a.setEmail(tiEmail.getEditText().getText().toString());
                a.setLembreteSenha(tiLembrarSenha.getEditText().getText().toString());
                a.setNome(login.getNomeUsuario());

                if (file != null && file.exists()) {
                    a.setCaminhoImagem(file.toString());
                }

                a.save();

                Intent i = new Intent(CadastarActivity.this, LoginActivity.class);
                startActivity(i);

                spotsDialog.dismiss();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
                //Toast.makeText(LoginActivity.this, "Falha ao conectar no servidor", Toast.LENGTH_LONG).show();
                spotsDialog.dismiss();
            }
        };
    }


    private void configureLoginAssociadoCallback() {
        callbackCallbackLoginAssociado = new Callback<String>() {


            @Override
            public void success(String s, Response response) {
                Toast.makeText(CadastarActivity.this, s, Toast.LENGTH_LONG).show();
                configureInformacaoAssociadoCallback();
                new APIClient().getRestService().getLoginAssociado(tiMatricula.getEditText().getText().toString(),
                        tiSenha.getEditText().getText().toString(), callbackUsuario);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(CadastarActivity.this, "Falha ao conectar ao servidor", Toast.LENGTH_LONG).show();
                spotsDialog.dismiss();
            }
        };

    }

    public boolean VaidarDados() {
        if (tiDataNascimento.getEditText().getText().equals("")) {
            tiDataNascimento.setError("Campo data nascimento obrigatório");
            spotsDialog.dismiss();
            return false;
        }
        if (tiEmail.getEditText().getText().equals("")) {
            tiEmail.setError("Campo email obrigatório");
            spotsDialog.dismiss();
            return false;
        }
        if (tiConfirmaEmail.getEditText().getText().equals("")) {
            tiConfirmaEmail.setError("Campo repetir email obrigatório");
            spotsDialog.dismiss();
            return false;
        }

        if (!tiEmail.getEditText().getText().toString().equals(tiConfirmaEmail.getEditText().getText().toString())) {
            tiConfirmaEmail.setError("Campo email divergente do anterior");
            spotsDialog.dismiss();
            return false;
        }
        if (tiSenha.getEditText().getText().equals("")) {
            tiSenha.setError("Campo senha obrigatório");
            spotsDialog.dismiss();
            return false;
        }
        if (tiRepetirSenha.getEditText().getText().equals("")) {
            tiRepetirSenha.setError("Campo repetir senha obrigatório");
            spotsDialog.dismiss();
            return false;
        }
        if (!tiSenha.getEditText().getText().toString().equals(tiRepetirSenha.getEditText().getText().toString())) {
            tiRepetirSenha.setError("Campo repetir senha divergente do anterior");
            spotsDialog.dismiss();
            return false;
        }
        if (tiLembrarSenha.getEditText().getText().equals("")) {
            tiLembrarSenha.setError("Campo lembrar senha obrigatório");
            spotsDialog.dismiss();
            return false;
        }

        return true;
    }

    private void showImage(File file) {
        if (file != null && file.exists()) {
            Log.d("foto", file.getAbsolutePath());

            int w = imageView.getWidth();
            int h = imageView.getHeight();
            Bitmap bitmap = ImageResizeUtils.getResizedImage(Uri.fromFile(file), w, h, false);
            // Toast.makeText(this, "w/h:" + imgView.getWidth() + "/" + imgView.getHeight() + " > " + "w/h:" + bitmap.getWidth() + "/" + bitmap.getHeight(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "file:" + file, Toast.LENGTH_LONG).show();

            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && file != null) {
            showImage(file);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Salvar o estado caso gire a tela
        outState.putSerializable("file", file);
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, year, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            tiDataNascimento.getEditText().setText(selectedDay + "/" + (selectedMonth + 1) + "/"
                    + selectedYear);
        }
    };
}
