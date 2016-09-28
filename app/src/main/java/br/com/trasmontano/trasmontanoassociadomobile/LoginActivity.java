package br.com.trasmontano.trasmontanoassociadomobile;

import android.*;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.AgendaMedicaAssociado;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Associado;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Login;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Preferencias;
import br.com.trasmontano.trasmontanoassociadomobile.Fingerprint.FingerprintHandler;
import br.com.trasmontano.trasmontanoassociadomobile.network.APIClient;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.emilsjolander.sprinkles.Query;

public class LoginActivity extends AppCompatActivity {
    private Button btEntrar;

    private Button btCadastrar;
    public static TextInputLayout tiUsuario;
    private TextInputLayout tisenha;
    private String redirecionarPara;
    SpotsDialog spotsDialog;

    private Callback<Login> callbackUsuario;

    private static final String KEY_NAME = "example_key";
    private static FingerprintManager fingerprintManager;
    private static KeyguardManager keyguardManager;
    private static KeyStore keyStore;
    private static KeyGenerator keyGenerator;
    private static Cipher cipher;
    private static FingerprintManager.CryptoObject cryptoObject;
    private ImageView ivBiometria;

    public static String mtr = "";
    public static String numTel = "";
    Bundle params = null;
    public boolean pedirSensor = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mtr = "";
        numTel = "";
        Intent intent = getIntent();

        params = intent.getExtras();

        if (params != null) {
            redirecionarPara = params.getString("redirecionarPara");
        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.slide_esquerda, R.anim.slide_direita);

        spotsDialog = new SpotsDialog(this, R.style.LoaderCustom);

        configureInformacaoAssociadoCallback();

        btEntrar = (Button) findViewById(R.id.entrarLogin);
        btCadastrar = (Button) findViewById(R.id.btnCadastrarLogin);
        tiUsuario = (TextInputLayout) findViewById(R.id.tiUsuario);
        tisenha = (TextInputLayout) findViewById(R.id.tiSenha);
        ivBiometria = (ImageView) findViewById(R.id.ivBiometria);
        ivBiometria.setVisibility(View.INVISIBLE);

        SharedPreferences prefs = getSharedPreferences("MATRICULA_SELECIONADA_NA_LISTA", MODE_PRIVATE);
        if (prefs.contains("matricula")) {
            tiUsuario.getEditText().setText(prefs.getString("matricula", ""));
            mtr = prefs.getString("matricula", "");
            tisenha.requestFocus();
        } else {
            tiUsuario.requestFocus();
        }
        SharedPreferences.Editor editor = getSharedPreferences("MATRICULA_SELECIONADA_NA_LISTA", MODE_PRIVATE).edit();
        editor.remove("matricula");
        editor.apply();

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tiUsuario.getEditText().getText().toString().equals("") || tisenha.getEditText().getText().toString().equals("")) {
                    tisenha.setError("Usuario/Senha campos obrigatórios");
                    return;
                } else {
                    spotsDialog.show();
                    new APIClient().getRestService().getLoginAssociado(tiUsuario.getEditText().getText().toString(),
                            tisenha.getEditText().getText().toString(), callbackUsuario);
                }
            }
        });


        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotsDialog.show();
                Intent i = new Intent(LoginActivity.this, CadastarActivity.class);
                startActivity(i);
                spotsDialog.dismiss();

            }
        });


        GetPermissao();

        if (pedirSensor)
            ValidaExisteSensorBiometrico();


    }

    public void ValidaExisteSensorBiometrico() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Fingerprint API only available on from Android 6.0 (M)
            FingerprintManager fingerprintManager = (FingerprintManager) this.getSystemService(Context.FINGERPRINT_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            if (!fingerprintManager.isHardwareDetected()) {
                ivBiometria.setVisibility(View.INVISIBLE);
                // Device doesn't support fingerprint authentication
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                // User hasn't enrolled any fingerprints to authenticate with
            } else {

                if (ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.READ_PHONE_STATE)
                        == PackageManager.PERMISSION_GRANTED) {
                    StartFingerprint();

                    Animation animationBio = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.biometria);
                    animationBio.setRepeatMode(Animation.RESTART);
                    animationBio.setRepeatCount(Animation.INFINITE);
                    ivBiometria.setVisibility(View.VISIBLE);
                    ivBiometria.startAnimation(animationBio);

                } else {
                    GetPermissao();
                }
            }
        }
    }

    public void GetPermissao() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ivBiometria.setVisibility(View.INVISIBLE);

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_PHONE_STATE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_PHONE_STATE},
                        0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.

            }
        } else {
            TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            numTel = manager.getLine1Number().toString();
            String teste = manager.getSimSerialNumber();
            String teste1 = manager.getNetworkOperator();

            if (numTel.equalsIgnoreCase("")) {
                ivBiometria.setVisibility(View.INVISIBLE);
                pedirSensor = false;
            } else {
                pedirSensor = true;
                Toast.makeText(this, manager.getLine1Number().toString(), Toast.LENGTH_LONG).show();
            }

            //ivBiometria.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                numTel = manager.getLine1Number().toString();

                if (numTel.equalsIgnoreCase("")) {
                    ivBiometria.setVisibility(View.INVISIBLE);
                    pedirSensor = false;
                } else {
                    pedirSensor = true;
                }

                Toast.makeText(this, manager.getLine1Number().toString(), Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void configureInformacaoAssociadoCallback() {
        callbackUsuario = new Callback<Login>() {

            @Override
            public void success(Login login, Response response) {

                if (login.UsuarioValido == true & login.SenhaValida == true & login.getBloqueado() == 0) {
                if (login.getSituacao().equalsIgnoreCase("A") || login.getSituacao().equalsIgnoreCase("S")) {

                    if (login.getStatusAcesso() == 1 || login.getStatusAcesso() == 3) {
                        if (login.getQtdeBloquear() < 0) {
                            Toast.makeText(LoginActivity.this, "Senha Expirada.", Toast.LENGTH_LONG).show();
                        } else {

                            Associado a = Query.one(Associado.class, "select * from associado where usuario=?", login.getCodigoUsuario()).get();
                            if (a == null) {
                                a = new Associado();
                                a.setUsuario(login.getCodigoUsuario());
                                a.save();
                            }

                            GuardarDadosLoginAssociado(login, redirecionarPara);
                            finish();
                            Intent intent = new Intent(LoginActivity.this, MainLogadoActivity.class);
                            startActivity(intent);

                        }
                    } else {
                        if (login.getStatusAcesso() == 2)
                            Toast.makeText(LoginActivity.this, "Usuário bloqueado temporariamente.", Toast.LENGTH_LONG).show();
                        else if (login.getStatusAcesso() == 3) {
                            Toast.makeText(LoginActivity.this, "Senha Expirada.", Toast.LENGTH_LONG).show();
                        } else if (login.getStatusAcesso() == 4) {
                            Toast.makeText(LoginActivity.this, "Você possui cadastro? Se sim verifique os dados informados. Senão faça o cadastro em nosso site. Em caso de dúvida Entre em contato Trasmontano Saúde!", Toast.LENGTH_LONG).show();

                        } else if (login.getStatusAcesso() == 5) {
                            int qtdTentativa = login.getTentativasAcesso() - login.getQtdeBloquear();
                            Toast.makeText(LoginActivity.this, "Restam " + qtdTentativa + " tentativas Senha incorreta.", Toast.LENGTH_LONG).show();

                        } else if (login.getStatusAcesso() == 6)
                            Toast.makeText(LoginActivity.this, "Conta não validada, aguardando validação.", Toast.LENGTH_LONG).show();

                    }


                } else {
                    if (login.UsuarioValido == false) {
                        Toast.makeText(LoginActivity.this, "Usuario Inválido", Toast.LENGTH_LONG).show();
                        tiUsuario.setError("Usuário Inválido");
                    } else if (login.SenhaValida == false) {
                        Toast.makeText(LoginActivity.this, "Senha Inválida", Toast.LENGTH_LONG).show();
                        tisenha.setError("Senha Inválida");
                    } else {
                        Toast.makeText(LoginActivity.this, "Login/Senha Inválidos", Toast.LENGTH_LONG).show();
                        tisenha.setError("Usuario/Senha Inválidos");
                    }
                }
                spotsDialog.dismiss();
                } else {
                    Toast.makeText(LoginActivity.this, "Você possui cadastro? Se sim verifique os dados informados. Senão faça o cadastro em nosso site. Em caso de dúvida Entre em contato Trasmontano Saúde!", Toast.LENGTH_LONG).show();
                }
                spotsDialog.dismiss();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
                Toast.makeText(LoginActivity.this, "Falha ao conectar no servidor", Toast.LENGTH_LONG).show();
                spotsDialog.dismiss();
            }
        };
    }

    public void GuardarDadosLoginAssociado(Login login, String redirecionarPara) {

        Preferencias p = new Preferencias(this);
        p.GuardarDadosLoginAssociado(login, redirecionarPara);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void StartFingerprint() {
        keyguardManager =
                (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        fingerprintManager =
                (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);


        if (!keyguardManager.isKeyguardSecure()) {

            Toast.makeText(this,
                    "Lock screen security not enabled in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,
                    "Fingerprint authentication permission not enabled",
                    Toast.LENGTH_LONG).show();

            return;
        }

        if (!fingerprintManager.hasEnrolledFingerprints()) {

            // This happens when no fingerprints are registered.
            Toast.makeText(this,
                    "Register at least one fingerprint in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (!fingerprintManager.hasEnrolledFingerprints()) {

            // This happens when no fingerprints are registered.
            Toast.makeText(this,
                    "Register at least one fingerprint in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }

        generateKey();

        if (cipherInit()) {
            cryptoObject = new FingerprintManager.CryptoObject(cipher);
            FingerprintHandler helper = new FingerprintHandler(this);

            helper.startAuth(fingerprintManager, cryptoObject);

        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore");
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException e) {
            throw new RuntimeException(
                    "Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | IOException e) {
            throw new RuntimeException(e);
        } catch (java.security.cert.CertificateException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        } catch (java.security.cert.CertificateException e) {
            e.printStackTrace();
            return false;
        }

    }
}
