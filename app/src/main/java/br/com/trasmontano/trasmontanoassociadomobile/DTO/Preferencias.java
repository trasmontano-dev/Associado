package br.com.trasmontano.trasmontanoassociadomobile.DTO;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import br.com.trasmontano.trasmontanoassociadomobile.LoginActivity;
import se.emilsjolander.sprinkles.Query;

/**
 * Created by rbarbosa on 01/08/2016.
 */
public class Preferencias   {
    private Context context;

    public Preferencias(Context context) {
        this.context = context;
    }


    public  void GuardarDadosLoginAssociado(Login login, String redirecionarPara) {
        String mat = "";
        String cdDependente = "";
        SharedPreferences.Editor editor = context.getSharedPreferences("DADOS_LOGIN", context.MODE_PRIVATE).edit();
        editor.putString("Bloqueado", Integer.toString(login.getBloqueado()));
        editor.putString("ExpiraEm", Integer.toString(login.getExpiraEm()));
        editor.putString("StatusAcesso", Integer.toString(login.getStatusAcesso()));
        editor.putString("DataUltimoAcesso", login.getDataUltimoAcesso());
        if(login.getCodigoUsuario().length() > 6)
        {
            editor.putString("CodigoUsuario", login.getCodigoUsuario().substring(0, 6));
            editor.putString("CodigoDependente", login.getCodigoUsuario().substring(6, 8));
            mat = login.getCodigoUsuario().substring(0, 6);
            cdDependente = login.getCodigoUsuario().substring(6, 8);
        }
        else
        {
            editor.putString("CodigoUsuario", login.getCodigoUsuario());
            editor.putString("CodigoDependente", "00");

            mat = login.getCodigoUsuario();
            cdDependente = "00";
        }

        editor.putString("NomeUsuario", login.getNomeUsuario());
        editor.putString("Email", login.getEmail());
        editor.putString("PerfilUsuario", login.getPerfilUsuario());
        editor.putString("TipoPlano", login.getTipoPlano());

        if (redirecionarPara != "")
            editor.putString("redirecionarPara", redirecionarPara);

        editor.commit();

    }
}
