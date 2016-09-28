package br.com.trasmontano.trasmontanoassociadomobile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.Calendar;
import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.Alarme;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Associado;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.LogMedicamentosTomados;
import br.com.trasmontano.trasmontanoassociadomobile.adapter.AlarmeAdapter;
import br.com.trasmontano.trasmontanoassociadomobile.adapter.LogAlarmeAdapter;
import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.Query;

public class ListLogAlarmeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvNome;
    private TextView tvNomeMedicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_log_alarme);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = (RecyclerView) findViewById(R.id.rvLogAlarme);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        tvNome = (TextView) findViewById(R.id.tvNome);
        tvNomeMedicamento = (TextView) findViewById(R.id.tvNomeMedicamento);

        Intent intent = getIntent();

        Bundle params = intent.getExtras();

        int id = params.getInt("id");

        Alarme a = Query.one(Alarme.class, "select * from alarme where id=?", id).get();
        tvNome.setText("Paciente: " + a.getNomePaciente());
        tvNomeMedicamento.setText("Medicamento: " + a.getNomeMedicamento());

        CarregaLista(id);
    }

    public void CarregaLista(int id) {
        //List<LogMedicamentosTomados> lst = Query.all(LogMedicamentosTomados.class).get().asList();
        List<LogMedicamentosTomados> lst =  Query.many(LogMedicamentosTomados.class, "select * from logMedicamentosTomados where idAlarme=?", id).get().asList();
        recyclerView.setAdapter(new LogAlarmeAdapter(this, lst, OnclickView()));

    }

    private LogAlarmeAdapter.LogAlarmeOnClickListener OnclickView() {
        return new LogAlarmeAdapter.LogAlarmeOnClickListener() {
            @Override
            public void OnClickView(View view, int index) {

            }

            @Override
            public void OnClickShineButton(View view, int index) {
                ShineButton shineButton = (ShineButton) view.findViewById(R.id.shineButtonTomei);
                TextView tvData = (TextView)view.findViewById(R.id.tvData);
                TextView tvHora = (TextView)view.findViewById(R.id.tvHora);
                TextView tvId = (TextView)view.findViewById(R.id.tvId);

                LogMedicamentosTomados l = Query.one(LogMedicamentosTomados.class, "select * from logMedicamentosTomados where id=?", tvId.getText().toString()).get();


                if(shineButton.isChecked())
                {
                    l.setTomei(1);
                    l.save();
                    /*Calendar c = Calendar.getInstance();

                    int hours = c.get(c.HOUR_OF_DAY);
                    int minutes = c.get(c.MINUTE);
                    int day = c.get(c.DAY_OF_MONTH);
                    int month = c.get(c.MONTH);
                    int year = c.get(c.YEAR);


                    String hora = String.valueOf(hours);
                    String minuto = String.valueOf(minutes);
                    String dia = String.valueOf(day);
                    String mes = String.valueOf(month + 1);
                    String ano = String.valueOf(year);

                    String data = "00".substring(dia.length()) + dia  + "/" + "00".substring(mes.length()) + mes + "/" + "0000".substring(ano.length()) + ano;

                    l.setDataTomouMedicamento(data);
                    l.setHoraTomouMedicamento(hora + ":" + minuto);
                    tvData.setText(data);
                    tvHora.setText("00".substring(hora.length()) + hora + ":" + "00".substring(minuto.length()) + minuto);
                    l.save();
                    recyclerView.refreshDrawableState();*/
                }
                else
                {

                    l.setTomei(0);
                    l.save();

                }
            }
        };
    }
    @Override
    public void onBackPressed() {
        // Otherwise defer to system default behavior.
        super.onBackPressed();

        Intent i = new Intent(ListLogAlarmeActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }


}
