package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.Associado;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.CredenciadosFavoritos;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Emergencia;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Login;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Preferencias;
import br.com.trasmontano.trasmontanoassociadomobile.network.APIClient;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.emilsjolander.sprinkles.Query;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {

    private Button btLogar;
    private Button btCarteirinhaSemLogin;
    private Button btAgendamentoConsultaSemLogin;
    private Button btAlarmeMedicamentos;
    private Button btOrientadorMedico;
    private Button btEmergencia;
    private Button btUnidades;
    private Button btCredenciadosFavoritos;
    SpotsDialog spotsDialog;
    private String redirecionarPara;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Context context;
    private Location location = null;
    private Callback<Login> callbackUsuario;
    private List<Associado> lstAssociado;


    CarouselView carouselView;

    int[] sampleImages = {R.drawable.banner_um, R.drawable.banner, R.drawable.banner_quatro, R.drawable.banner_dois};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);

        } else
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        spotsDialog = new SpotsDialog(this, R.style.LoaderCustom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        configureInformacaoAssociadoCallback();

        btLogar = (Button) findViewById(R.id.btLogar);
        btCarteirinhaSemLogin = (Button) findViewById(R.id.btCarteirinhaSemLogin);
        btAgendamentoConsultaSemLogin = (Button) findViewById(R.id.btAgendamentoConsultaSemLogin);
        btEmergencia = (Button) findViewById(R.id.btEmergencia);
        btUnidades = (Button) findViewById(R.id.btUnidades);
        btCredenciadosFavoritos = (Button) findViewById(R.id.btCredenciadosFavoritos);

        btAgendamentoConsultaSemLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionarPara = "AgendamentoConsulta";
                showInputDialog();
            }
        });

        btCarteirinhaSemLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirecionarPara = "CarteirinhaTemporaria";
                showInputDialog();
            }
        });

        btUnidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UnidadesActivity.class);
                startActivity(intent);
            }
        });

        btAlarmeMedicamentos = (Button) findViewById(R.id.btAlarmeMedicamentos);

        btAlarmeMedicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ListAlarmeActivity.class);
                startActivity(i);
            }
        });

        btOrientadorMedico = (Button) findViewById(R.id.btOrientador);
        btOrientadorMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, OrientadorMedico.class);
                startActivity(i);
            }
        });

        btEmergencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);

                } else {
                    Associado a = null;
                    lstAssociado = Query.all(Associado.class).get().asList();

                    if (lstAssociado.size() == 0) {
                        Toast.makeText(MainActivity.this, "Nenhum usuário cadastrado", Toast.LENGTH_LONG).show();
                        return;
                    } else if (lstAssociado.size() == 1) {
                        a = lstAssociado.get(0);
                        CarregarEmergencia(a.getUsuario());
                    } else if (lstAssociado.size() > 1) {
                        showInputDialogEmergencia();
                    }


                    /*locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, MainActivity.this);
                    if (location != null) {
                        Intent i = new Intent(MainActivity.this, ListEmergenciaActivity.class);
                        Bundle params = new Bundle();

                        params.putString("matricula", "554020");
                        params.putString("latitude", String.valueOf(location.getLatitude()));
                        params.putString("longitude", String.valueOf(location.getLongitude()));
                        i.putExtras(params);
                        startActivity(i);
                    }*/
                }


            }
        });

        btCredenciadosFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialogFavoritos();
            }
        });

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logar();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void CarregarEmergencia(String matricula) {


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, MainActivity.this);
        if (location != null) {
            Intent i = new Intent(MainActivity.this, ListEmergenciaActivity.class);
            Bundle params = new Bundle();

            params.putString("matricula", matricula);
            params.putString("latitude", String.valueOf(location.getLatitude()));
            params.putString("longitude", String.valueOf(location.getLongitude()));
            i.putExtras(params);
            startActivity(i);
        }

    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);

        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_central_atendimento) {
            Intent i = new Intent(MainActivity.this, CentralDeAtendimentoActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Logar();
        } else if (id == R.id.nav_carteirinha_sem_logar) {
            redirecionarPara = "CarteirinhaTemporaria";
            showInputDialog();
        } else if (id == R.id.nav_home) {


        } else if (id == R.id.nav_agendamentoConsulta) {
            redirecionarPara = "AgendamentoConsulta";
            showInputDialog();

        } else if (id == R.id.nav_alarme_medicamentos) {
            Intent i = new Intent(MainActivity.this, ListAlarmeActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_centralAtendimento) {
            Intent i = new Intent(MainActivity.this, CentralDeAtendimentoActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void Logar() {
        spotsDialog.show();
        lstAssociado = Query.all(Associado.class).get().asList();

        if (lstAssociado.size() > 0) {
            Intent i = new Intent(MainActivity.this, ListAssociadoActivity.class);
            startActivity(i);
            spotsDialog.dismiss();
        } else {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            spotsDialog.dismiss();
        }
    }

    public void CarteirinhaSemLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        Bundle params = new Bundle();

        params.putString("redirecionarPara", "CarteirinhaTemporaria");
        intent.putExtras(params);
        startActivity(intent);
    }

    protected void showInputDialogEmergencia() {

        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog_emergencia, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final Spinner spinner = (Spinner) promptView.findViewById(R.id.spinner);

        ArrayList lst = new ArrayList<String>();

        for (Associado item : lstAssociado) {
            lst.add(item.getUsuario());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, lst);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Toast.makeText(MainActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                        CarregarEmergencia(spinner.getSelectedItem().toString());

                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();


    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText etSenha = (EditText) promptView.findViewById(R.id.etSenha);
        final EditText etLogin = (EditText) promptView.findViewById(R.id.etLogin);


        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (etLogin.getText().toString().equals("") || etLogin.getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this, "Login/Senha Obrigatórios", Toast.LENGTH_LONG).show();
                        } else {
                            spotsDialog.show();
                            new APIClient().getRestService().getLoginAssociado(etLogin.getText().toString(),
                                    etSenha.getText().toString(), callbackUsuario);
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    protected void showInputDialogFavoritos() {

        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog_emergencia, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final Spinner spinner = (Spinner) promptView.findViewById(R.id.spinner);

        ArrayList lst = new ArrayList<String>();

        lstAssociado = Query.all(Associado.class).get().asList();
        for (Associado item : lstAssociado) {
            lst.add(item.getUsuario());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, lst);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(MainActivity.this, spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                        List<CredenciadosFavoritos> c1 = Query.many(CredenciadosFavoritos.class, "select * from favoritos where matricula=?", spinner.getSelectedItem().toString()).get().asList();
                        if(c1.size() > 0)
                        {
                            Toast.makeText(MainActivity.this, "Maior que 0", Toast.LENGTH_LONG).show();
                            Bundle params = new Bundle();
                            Intent i = new Intent(MainActivity.this, ListEmergenciaActivity.class);
                            params.putString("matricula", spinner.getSelectedItem().toString());
                            params.putString("favoritos", "true");
                            i.putExtras(params);

                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Voce não possui nenhum credenciado favorito", Toast.LENGTH_LONG).show();
                            return;
                        }
                        //List<CredenciadosFavoritos> c = Query.all(CredenciadosFavoritos.class).get().asList();


                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();


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

                        GuardarDadosLoginAssociado(login, redirecionarPara);
                        finish();
                        Intent intent = new Intent(MainActivity.this, MainLogadoActivity.class);
                        startActivity(intent);
                    } else {
                        if (login.UsuarioValido == false) {
                            Toast.makeText(MainActivity.this, "Usuario Inválido", Toast.LENGTH_LONG).show();

                        } else if (login.SenhaValida == false) {
                            Toast.makeText(MainActivity.this, "Senha Inválida", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Login/Senha Inválidos", Toast.LENGTH_LONG).show();

                        }
                    }
                    spotsDialog.dismiss();
                } else {
                    if (login.UsuarioValido == false) {
                        Toast.makeText(MainActivity.this, "Usuario Inválido", Toast.LENGTH_LONG).show();

                    } else if (login.SenhaValida == false) {
                        Toast.makeText(MainActivity.this, "Senha Inválida", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(MainActivity.this, "Login/Senha Inválidos", Toast.LENGTH_LONG).show();

                    }
                }
                spotsDialog.dismiss();

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
                Toast.makeText(MainActivity.this, "Falha ao conectar no servidor", Toast.LENGTH_LONG).show();
                spotsDialog.dismiss();
            }
        };
    }

    public void GuardarDadosLoginAssociado(Login login, String redirecionarPara) {

        Preferencias p = new Preferencias(this);
        p.GuardarDadosLoginAssociado(login, redirecionarPara);

    }

    @Override
    public void onLocationChanged(Location location) {
        /*Toast.makeText(MainActivity.this, "Latitude: " + location.getLatitude() + " | Longitude: " + location.getLongitude(), Toast.LENGTH_LONG).show();*/
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                    //Start your service here
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                } else
                    Toast.makeText(MainActivity.this, "Para utilizar este recurso é necessário uso do gps", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
