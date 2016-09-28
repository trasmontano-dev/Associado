package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.CredenciadosFavoritos;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Emergencia;
import br.com.trasmontano.trasmontanoassociadomobile.adapter.AssociadoLoginAdapter;
import br.com.trasmontano.trasmontanoassociadomobile.adapter.EmergenciaAdapter;
import br.com.trasmontano.trasmontanoassociadomobile.network.APIClient;
import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.Query;

public class ListEmergenciaActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    String matricula;
    String latitude;
    String longitude;
    String favoritos = null;
    Bundle params;
    private RecyclerView recyclerView;
    SpotsDialog spotsDialog;
    Callback<List<Emergencia>> callbackEmergencia;
    Callback<List<Emergencia>> callbackFavoritos;
    private GoogleApiClient mGoogleApiClient;
    private Button btLigarPolicia;
    private Button btLigarSamu;
    private Button btLigarBombeiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_emergencia);

        btLigarPolicia = (Button) findViewById(R.id.btLigarPolicia);
        btLigarSamu = (Button) findViewById(R.id.btLigarSamu);
        btLigarBombeiro = (Button) findViewById(R.id.btLigarBombeiro);

        btLigarPolicia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "190"));
                if (ActivityCompat.checkSelfPermission(ListEmergenciaActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });
        btLigarSamu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "192"));
                if (ActivityCompat.checkSelfPermission(ListEmergenciaActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });
        btLigarBombeiro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "193"));
                if (ActivityCompat.checkSelfPermission(ListEmergenciaActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API).build();

        recyclerView = (RecyclerView) findViewById(R.id.rvEmergencia);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        params = intent.getExtras();

        if (params != null) {
            matricula = params.getString("matricula");
            favoritos = params.getString("favoritos");

            if(favoritos != null)
            {
                configureFavoritosCallback();
                String credenciados = "";

                List<CredenciadosFavoritos> lst = Query.many(CredenciadosFavoritos.class, "select * from favoritos where matricula=?", matricula).get().asList();
                for (CredenciadosFavoritos c: lst) {
                    credenciados += c.getCodigoCredenciado() + "-" + c.getCodigoFilial() + ",";
                }

                new APIClient().getRestService().getCredenciadosFavoritosMobile(matricula,
                        credenciados ,callbackFavoritos);
            }
            else
            {
                latitude = params.getString("latitude");
                longitude = params.getString("longitude");

                configureEmergenciaCallback();

                new APIClient().getRestService().getHospitaisEmergenciaMobile(matricula,
                        latitude, longitude ,callbackEmergencia);
            }


        }
    }


    private void configureEmergenciaCallback() {
        callbackEmergencia = new Callback<List<Emergencia>>() {

            @Override
            public void success(List<Emergencia> emergencias, Response response) {
                recyclerView.setAdapter(new EmergenciaAdapter(ListEmergenciaActivity.this, emergencias, onClickEmergencia()));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ListEmergenciaActivity.this, "Falha ao conectar ao servidor", Toast.LENGTH_LONG).show();
            }
        };
    }

    private void configureFavoritosCallback() {
        callbackFavoritos = new Callback<List<Emergencia>>() {

            @Override
            public void success(List<Emergencia> emergencias, Response response) {
                recyclerView.setAdapter(new EmergenciaAdapter(ListEmergenciaActivity.this, emergencias, onClickEmergencia()));
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ListEmergenciaActivity.this, "Falha ao conectar ao servidor", Toast.LENGTH_LONG).show();
            }
        };
    }

    private EmergenciaAdapter.EmergenciaOnClickListener onClickEmergencia() {
        return new EmergenciaAdapter.EmergenciaOnClickListener() {

            @Override
            public void OnClickButtonComoChegar(View view, int index) {

                TextView lat = (TextView)view.findViewById(R.id.tvLatitude);
                TextView lon = (TextView)view.findViewById(R.id.tvLongitude);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + latitude + "," + longitude + "&daddr=" + lat.getText().toString() + "," + lon.getText().toString()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);

            }

            @Override
            public void OnClickButtonLigar(View view, int index) {

                TextView t = (TextView) view.findViewById(R.id.tvTelefone);
                String phone = t.getText().toString();

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                if (ActivityCompat.checkSelfPermission(ListEmergenciaActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ListEmergenciaActivity.this, "O aplicativo não possui permissão para fazer ligações, favor checar as permissões.", Toast.LENGTH_LONG).show();
                    return;
                }
                startActivity(callIntent);
            }

            @Override
            public void OnClickButtonFavoritos(View view, int index) {

                ShineButton s = (ShineButton)view.findViewById(R.id.shineButtonFavoritos);
                Toast.makeText(ListEmergenciaActivity.this, String.valueOf(s.isChecked()), Toast.LENGTH_LONG).show();
                TextView tvCodigoCredenciado = (TextView)view.findViewById(R.id.tvCodigoCredenciado);
                TextView tvCodigoFilial = (TextView)view.findViewById(R.id.tvCodigoFilial);
                String cdCredenciado = tvCodigoCredenciado.getText().toString();
                String cdFilial = tvCodigoFilial.getText().toString();
                CredenciadosFavoritos c = Query.one(CredenciadosFavoritos.class, "select * from favoritos where matricula=? AND codigoCredenciado=? AND codigoFilial=?", matricula, cdCredenciado, cdFilial).get();

                if(c == null)
                {
                    c = new CredenciadosFavoritos();
                    c.setMatricula(matricula);
                    c.setCodigoCredenciado(tvCodigoCredenciado.getText().toString());
                    c.setCodigoFilial(tvCodigoFilial.getText().toString());
                    c.save();
                }
                else
                {
                    c.deleteAsync(new Model.OnDeletedCallback() {
                        @Override
                        public void onDeleted() {

                            Toast.makeText(ListEmergenciaActivity.this, "Excluído com sucesso! ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        };

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    private void setUpMapIfNeeded() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "RedeCredenciada Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.com.trasmontano.trasmontanoassociadomobile/http/host/path")
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "RedeCredenciada Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://br.com.trasmontano.trasmontanoassociadomobile/http/host/path")
        );
        AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
