package br.com.trasmontano.trasmontanoassociadomobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.Alarme;
import br.com.trasmontano.trasmontanoassociadomobile.Util.AlarmUtil;
import br.com.trasmontano.trasmontanoassociadomobile.adapter.AlarmeAdapter;
import se.emilsjolander.sprinkles.Query;
import se.emilsjolander.sprinkles.Model;


public class ListAlarmeActivity extends AppCompatActivity {

    private ImageButton imAddAlarme;
    private RecyclerView recyclerView;
    public static TextView tvHora;
    private static String idItemSelecionado;
    private static Switch swAtivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_alarme);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.slide_down, R.anim.slide_up);
        recyclerView = (RecyclerView) findViewById(R.id.rvAlarme);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        imAddAlarme = (ImageButton) findViewById(R.id.imAddAlarme);

        imAddAlarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ListAlarmeActivity.this, CadastrarAlarmeActivity.class);
                startActivity(i);
                finish();

            }
        });

        CarregaLista();

    }


    public void CarregaLista() {
        List<Alarme> lst = Query.all(Alarme.class).get().asList();
        recyclerView.setAdapter(new AlarmeAdapter(this, lst, OnClickToggle()));

    }

    private AlarmeAdapter.AlarmeOnClickListener OnClickToggle() {
        return new AlarmeAdapter.AlarmeOnClickListener() {
            @Override
            public void OnClickToggle(View view, int index) {

                TextView tvId = (TextView) view.findViewById(R.id.tvId);
                Switch swAtivo = (Switch) view.findViewById(R.id.swAtivo);
                Alarme a = Query.one(Alarme.class, "select * from alarme where id=?", tvId.getText()).get();

                a.setId(Long.parseLong(tvId.getText().toString()));
                int id = (int) a.getId();
                if (swAtivo.isChecked()) {

                   /* String[] parts = a.getHoraInicio().split(":");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(parts[1]));*/
                    Intent intent = new Intent(AlarmeBroadcastReceiver.ACTION);

                    a.setAtivo(1);

                    String[] parts = a.getHoraInicio().split(":");
                    String[] partsIntervalo = a.getIntervaloDe().split(":");

                    int horaInicio = Integer.parseInt(parts[0]);
                    int minutoInicio = Integer.parseInt(parts[1]);
                    int horaIntervalo = Integer.parseInt(partsIntervalo[0]);
                    int minutoIntervalo = Integer.parseInt(partsIntervalo[1]);

                    long alarmeInicio = retornaHoraMillisecond(horaInicio, minutoInicio);
                    //long alarmeIntervalo = retornaHoraMillisecond(horaIntervalo, minutoIntervalo);
                    long alarmeIntervalo =  retornaIntervaloMillisecond(horaIntervalo, minutoIntervalo);

                    intent.putExtra("id", id);
                    intent.putExtra("paciente", a.getNomePaciente());
                    intent.putExtra("medicamento", a.getNomeMedicamento());

                    AlarmUtil.scheduleRepeat(ListAlarmeActivity.this, intent, alarmeInicio, alarmeIntervalo, id);


                } else {
                    Intent intent = new Intent(AlarmeBroadcastReceiver.ACTION);
                    AlarmUtil.cancel(ListAlarmeActivity.this, intent, id);
                    a.setAtivo(0);
                }

                a.save();
            }

            @Override
            public void OnClickView(View view, int index) {
                showFilterPopupView(view, index);
            }

            @Override
            public void OnClickLixeira(View view, int index) {
                showFilterPopupLixeira(view, index);
            }


        };
    }


    public long retornaHoraMillisecond(int hora, int minuto) {
        Calendar calendar = Calendar.getInstance();
        //if (hora != 0)
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        //if (minuto != 0)
        calendar.set(Calendar.MINUTE, minuto);

        long time = calendar.getTimeInMillis();

        return time;
    }

    public long retornaIntervaloMillisecond(int hora, int minuto) {
        Calendar calendar = Calendar.getInstance();
        long time;
        if (hora != 0)
        {
            calendar.set(Calendar.HOUR_OF_DAY, hora);
            calendar.set(Calendar.MINUTE, minuto);
            time = calendar.getTimeInMillis();
        }
        else
        {
            time = minuto * 60 * 1000;
        }

        return time;
    }

    public void CancelaAlarme(int idAlarme)
    {
        Intent intent = new Intent(AlarmeBroadcastReceiver.ACTION);
        String id = String.valueOf(idAlarme);

        AlarmUtil.cancel(ListAlarmeActivity.this, intent, Integer.parseInt(id));

    }

    private void showFilterPopupLixeira(View v, long index) {

        PopupMenu popup = new PopupMenu(ListAlarmeActivity.this, v);

        TextView t = (TextView) v.findViewById(R.id.tvId);
        final String id = t.getText().toString();
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.mn_excluir, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mn_excluir:

                        DialogInterface.OnClickListener dialiog = new DialogInterface.OnClickListener() {
                            // RestauranteDAO dao = new RestauranteDAO(Lista_Restaurantes_Activity.this);
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        Alarme a = Query.one(Alarme.class, "select * from alarme where id=?", id).get();

                                        Intent intent = new Intent(AlarmeBroadcastReceiver.ACTION);
                                        String id = String.valueOf(a.getId());
                                        AlarmUtil.cancel(ListAlarmeActivity.this, intent, Integer.parseInt(id));

                                        if (a != null) {
                                            a.deleteAsync(new Model.OnDeletedCallback() {
                                                @Override
                                                public void onDeleted() {
                                                    CarregaLista();
                                                    Toast.makeText(ListAlarmeActivity.this, "Excluído com sucesso! ", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:

                                        break;

                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(ListAlarmeActivity.this);
                        builder.setMessage("Você realmente deseja excluir este Alarme?");
                        builder.setPositiveButton("Sim", dialiog);
                        builder.setNegativeButton("Não", dialiog);
                        builder.show();

                        return false;
                    default:
                        return false;
                }
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }

    private void showFilterPopupView(View v, long index) {

        PopupMenu popup = new PopupMenu(ListAlarmeActivity.this, v);

        TextView t = (TextView) v.findViewById(R.id.tvId);
        final int id = Integer.parseInt(t.getText().toString());
        // Inflate the menu from xml
        popup.getMenuInflater().inflate(R.menu.menu_lista_login, popup.getMenu());
        // Setup menu item selection
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mn_excluir:

                        DialogInterface.OnClickListener dialiog = new DialogInterface.OnClickListener() {
                            // RestauranteDAO dao = new RestauranteDAO(Lista_Restaurantes_Activity.this);
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        Alarme a = Query.one(Alarme.class, "select * from alarme where id=?", id).get();

                                        Intent intent = new Intent(AlarmeBroadcastReceiver.ACTION);
                                        String id = String.valueOf(a.getId());
                                        AlarmUtil.cancel(ListAlarmeActivity.this, intent, Integer.parseInt(id));
                                        AlarmUtil.cancel(ListAlarmeActivity.this, intent, -1);

                                        if (a != null) {
                                            a.deleteAsync(new Model.OnDeletedCallback() {
                                                @Override
                                                public void onDeleted() {
                                                    CarregaLista();
                                                    Toast.makeText(ListAlarmeActivity.this, "Excluído com sucesso! ", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:

                                        break;

                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(ListAlarmeActivity.this);
                        builder.setMessage("Você realmente deseja excluir este Alarme?");
                        builder.setPositiveButton("Sim", dialiog);
                        builder.setNegativeButton("Não", dialiog);
                        builder.show();

                        return false;
                    case R.id.mn_detalhes:
                        Intent i = new Intent(ListAlarmeActivity.this, ListLogAlarmeActivity.class);
                        i.putExtra("id", id);
                        startActivity(i);
                        return false;
                    default:
                        return false;
                }
            }
        });
        // Handle dismissal with: popup.setOnDismissListener(...);
        // Show the menu
        popup.show();
    }




}
