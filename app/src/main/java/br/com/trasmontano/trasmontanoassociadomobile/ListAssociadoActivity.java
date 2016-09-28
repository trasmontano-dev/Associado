package br.com.trasmontano.trasmontanoassociadomobile;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.Associado;
import br.com.trasmontano.trasmontanoassociadomobile.adapter.AssociadoLoginAdapter;
import dmax.dialog.SpotsDialog;
import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.Query;

public class ListAssociadoActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    SpotsDialog spotsDialog;
    private ImageButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_associado);
        spotsDialog = new SpotsDialog(this, R.style.LoaderCustom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        overridePendingTransition(R.anim.slide_down, R.anim.splash);
        recyclerView = (RecyclerView) findViewById(R.id.rvAssociados);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        CarregaLista(this );

        fab = (ImageButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ListAssociadoActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    public void CarregaLista(Context context) {
        spotsDialog.show();
        List<Associado> lst = Query.all(Associado.class).get().asList();
        recyclerView.setAdapter(new AssociadoLoginAdapter(this, lst, onClickAssociado()));
        spotsDialog.dismiss();
    }

    private AssociadoLoginAdapter.AssociadoOnClickListener onClickAssociado() {
        return new AssociadoLoginAdapter.AssociadoOnClickListener() {

            @Override
            public void OnClickAssociado(View view, int index) {

                TextView t =  (TextView) view.findViewById(R.id.tvMatricula);

                SharedPreferences.Editor editor = getSharedPreferences("MATRICULA_SELECIONADA_NA_LISTA", MODE_PRIVATE).edit();
                editor.putString("matricula", t.getText().toString());
                editor.commit();

                Intent i = new Intent(ListAssociadoActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void OnClickMenu(View view, int index) {
                showFilterPopup(view, index);

            }
        };
    }

    private void showFilterPopup(View v, long id) {

        PopupMenu popup;
        popup = new PopupMenu(ListAssociadoActivity.this,v);
        final String idMenu = Long.toString(id);
        TextView t  =  (TextView) v.findViewById(R.id.tvMatricula);
        final String mat = t.getText().toString();
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
                                        Associado a = Query.one(Associado.class, "select * from associado where usuario=?", mat).get();
                                        if(a != null)
                                        {
                                            a.deleteAsync(new Model.OnDeletedCallback() {
                                                @Override
                                                public void onDeleted() {
                                                    CarregaLista(ListAssociadoActivity.this);
                                                    Toast.makeText(ListAssociadoActivity.this, "Excluído com sucesso! ", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }

                                        //Toast.makeText(ListAssociadoActivity.this, "Apagar! " + idMenu, Toast.LENGTH_SHORT).show();

                                        break;
                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //Toast.makeText(ListAssociadoActivity.this, "Não Apagar! " + idMenu, Toast.LENGTH_SHORT).show();
                                        return;
                                    // break;

                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(ListAssociadoActivity.this);
                        builder.setMessage("Você realmente deseja excluir este Registro?");
                        builder.setPositiveButton("Sim", dialiog);
                        builder.setNegativeButton("Não", dialiog);
                        builder.show();

                        //Toast.makeText(ListAssociadoActivity.this, "Falha ao Excluido! ", Toast.LENGTH_SHORT).show();
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

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mn_voltar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_voltar) {
            spotsDialog.show();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            spotsDialog.dismiss();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
*/
}
