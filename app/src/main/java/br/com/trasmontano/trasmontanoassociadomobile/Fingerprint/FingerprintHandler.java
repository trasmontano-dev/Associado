package br.com.trasmontano.trasmontanoassociadomobile.Fingerprint;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.Associado;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Login;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Preferencias;
import br.com.trasmontano.trasmontanoassociadomobile.LoginActivity;
import br.com.trasmontano.trasmontanoassociadomobile.MainLogadoActivity;
import br.com.trasmontano.trasmontanoassociadomobile.network.APIClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.emilsjolander.sprinkles.Query;

/**
 * Created by rbarbosa on 23/08/2016.
 */

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends
        FingerprintManager.AuthenticationCallback {

    private CancellationSignal cancellationSignal;
    private Context appContext;
    FingerprintManager.CryptoObject cryptoObject;
    private static final String SECRET_MESSAGE = "Very secret message";
    private Callback<Login> callbackUsuario;

    public FingerprintHandler(Context context) {
        appContext = context;
    }

    public void startAuth(FingerprintManager manager,
                          FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();
        this.cryptoObject = cryptoObject;
        if (ActivityCompat.checkSelfPermission(appContext,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId,
                                      CharSequence errString) {
        Toast.makeText(appContext,
                "Authentication error\n" + errString,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId,
                                     CharSequence helpString) {
        Toast.makeText(appContext, helpString,  Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(appContext,
                "Digital Não Confere.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {


        byte[] encrypted = new byte[0];
        try {
            encrypted = result.getCryptoObject().getCipher().doFinal(SECRET_MESSAGE.getBytes());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        //Base64.encodeToString(encrypted, 0 /* flags */);
        Log.i("cod", Base64.encodeToString(encrypted, 0 /* flags */));

        configureInformacaoAssociadoCallback();

        new APIClient().getRestService().getLoginAssociadoBiometria(LoginActivity.tiUsuario.getEditText().getText().toString(),
                LoginActivity.numTel, callbackUsuario);

        Toast.makeText(appContext,
                "Digital Ok: ",
                Toast.LENGTH_LONG).show();
        /*Toast.makeText(appContext,
                "Authentication succeeded.",
                Toast.LENGTH_LONG).show();*/

    }

    private void configureInformacaoAssociadoCallback() {
        callbackUsuario = new Callback<Login>() {

            @Override
            public void success(Login login, Response response) {

                if (login.UsuarioValido == true & login.SenhaValida == true & login.getBloqueado() == 0) {
                    if (login.getSituacao().equalsIgnoreCase("A") | login.getSituacao().equalsIgnoreCase("S")) {

                        Associado a = Query.one(Associado.class, "select * from associado where usuario=?", login.getCodigoUsuario()).get();
                        if (a == null) {
                            a = new Associado();
                            a.setUsuario(login.getCodigoUsuario());
                            a.save();
                        }

                        GuardarDadosLoginAssociado(login, "");

                        Intent intent = new Intent(appContext, MainLogadoActivity.class);
                        appContext.startActivity(intent);
                    } else {
                        if (login.UsuarioValido == false) {
                            Toast.makeText(appContext, "Usuario Inválido", Toast.LENGTH_LONG).show();
                            //tiUsuario.setError("Usuário Inválido");
                        } else if (login.SenhaValida == false) {
                            Toast.makeText(appContext, "Senha Inválida", Toast.LENGTH_LONG).show();
                            //tisenha.setError("Senha Inválida");
                        } else {
                            Toast.makeText(appContext, "Login/Senha Inválidos", Toast.LENGTH_LONG).show();
                            //tisenha.setError("Usuario/Senha Inválidos");
                        }
                    }
                    //spotsDialog.dismiss();
                } else {
                    if (login.UsuarioValido == false) {
                        Toast.makeText(appContext, "Usuario Inválido", Toast.LENGTH_LONG).show();
                        //tiUsuario.setError("Usuário Inválido");
                    } else if (login.SenhaValida == false) {
                        Toast.makeText(appContext, "Senha Inválida", Toast.LENGTH_LONG).show();
                        //tisenha.setError("Senha Inválida");
                    } else {
                        Toast.makeText(appContext, "Login/Senha Inválidos", Toast.LENGTH_LONG).show();
                        //tisenha.setError("Usuario/Senha Inválidos");
                    }
                }
                //spotsDialog.dismiss();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
                Toast.makeText(appContext, "Falha ao conectar no servidor", Toast.LENGTH_LONG).show();
                //spotsDialog.dismiss();
            }
        };
    }

    public void GuardarDadosLoginAssociado(Login login, String redirecionarPara) {

        Preferencias p = new Preferencias(appContext);
        p.GuardarDadosLoginAssociado(login, redirecionarPara);
    }
}

