package br.com.trasmontano.trasmontanoassociadomobile;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import se.emilsjolander.sprinkles.Migration;
import se.emilsjolander.sprinkles.Sprinkles;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;

/**
 * Created by rbarbosa on 08/07/2016.
 */
public class PDVAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Sprinkles sprinkles = Sprinkles.init(getApplicationContext());

        sprinkles.addMigration(new Migration() {
            @Override
            protected void onPreMigrate() {
                // do nothing
            }

            @Override
            protected void doMigration(SQLiteDatabase db) {
                db.execSQL(
                        "CREATE TABLE associado (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "usuario TEXT, " +
                                "dataNascimento TEXT, " +
                                "cpf TEXT, " +
                                "nome TEXT, " +
                                "caminhoImagem TEXT " +
                                ")"
                );

                db.execSQL(

                        "CREATE TABLE alarme (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "nomeMedicamento TEXT, " +
                                "nomePaciente TEXT, " +
                                "nomeMusica TEXT, " +
                                "descricaoMedicamento TEXT, " +
                                "dosagem TEXT, " +
                                "quantidade TEXT, " +
                                "dataInicio TEXT, " +
                                "horaInicio TEXT, " +
                                "intervaloDe TEXT, " +
                                "horarios TEXT, " +
                                "ativo INTEGER, " +
                                "tocar INTEGER, " +
                                "vibrar INTEGER " +
                        ")"
                );

                db.execSQL(
                        "CREATE TABLE logMedicamentosTomados (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "idAlarme INTEGER, " +
                                "tomei INTEGER, " +
                                "usuario TEXT, " +
                                "nomeMedicamento TEXT, " +
                                "dataTomouMedicamento TEXT, " +
                                "horaTomouMedicamento TEXT " +
                        ")"
                );

                db.execSQL(
                        "CREATE TABLE favoritos (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "matricula TEXT, " +
                                "codigoCredenciado TEXT, " +
                                "codigoFilial TEXT " +
                                ")"
                );

                db.execSQL(
                        "CREATE TABLE tbLocalidade(" +
                                "Codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "Localidade TEXT, " +
                                "Endereco TEXT, " +
                                "Numero TEXT, " +
                                "Complemento TEXT, " +
                                "Cep TEXT, " +
                                "Bairro TEXT, " +
                                "Cidade TEXT, " +
                                "Estado TEXT, " +
                                "Imagem TEXT, " +
                                "Latitude TEXT, " +
                                "Longitude TEXT " +
                                ")"
                );

            }

            @Override
            protected void onPostMigrate() {
                // do nothing
            }
        });
    }

}
