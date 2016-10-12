package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.OrientadorMedicoDTOPesquisa;
import br.com.trasmontano.trasmontanoassociadomobile.adapter.RedeCredenciadaAdapter;


public class RedeCredenciadaActivity extends AppCompatActivity implements Serializable, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 0;
    private RecyclerView rvRedeCredenciada;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = RedeCredenciadaActivity.class.getSimpleName();
    private LocationRequest mLocationRequest;
    private double longitudeAtual = 0.0;
    private double latitudeAtual = 0.0;
    private String cdDescricao;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API).build();
        mLocationRequest =
                LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10 * 1000) // 10 segundos, em milissegundos
                        .setFastestInterval(1 * 1000);  // 1 segundo, em milissegundos
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rede_credenciada);
        this.rvRedeCredenciada = (RecyclerView) findViewById(R.id.rvRedeCredenciada);

        overridePendingTransition(R.anim.slide_esquerda, R.anim.slide_direita);
        ArrayList<OrientadorMedicoDTOPesquisa> lstPesquisa = (ArrayList<OrientadorMedicoDTOPesquisa>) getIntent().getSerializableExtra("redeCredenciada");
        cdDescricao = lstPesquisa.get(0).getCdCategoria().toString();
        ArrayList pesquisa = new ArrayList();
        for (OrientadorMedicoDTOPesquisa dto : lstPesquisa) {
            pesquisa.add(dto);
        }
        rvRedeCredenciada.setLayoutManager(new LinearLayoutManager(this));
        rvRedeCredenciada.setHasFixedSize(true);
        rvRedeCredenciada.setAdapter(new RedeCredenciadaAdapter(RedeCredenciadaActivity.this, pesquisa, OnClickRedeCredenciada()));
    }

    private RedeCredenciadaAdapter.RedeCredenciadaOnClickListener OnClickRedeCredenciada() {
        return new RedeCredenciadaAdapter.RedeCredenciadaOnClickListener() {
            @Override
            public void OnClickVerMapa(View view, int index) {
                //Lista de permissões necessárias
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    AlertDialog.Builder b = new AlertDialog.Builder(RedeCredenciadaActivity.this);
                    b.setMessage("Habilite o GPS para o funcionamento do mapa, isso poderá acarretar no seu consumo de dados.");
                    b.setNeutralButton("OK", null);
                    b.show();
                } else {
                    // Se não possui permissão
                    if (ContextCompat.checkSelfPermission(RedeCredenciadaActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(RedeCredenciadaActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                            AlertDialog.Builder b = new AlertDialog.Builder(RedeCredenciadaActivity.this);
                            b.setMessage("Para que o aplicativo funcione corretamento necessitamos das permissões necessárias.");
                            b.setNeutralButton("OK", null);
                            b.show();
                        } else {
                            // Solicita a permissão
                            ActivityCompat.requestPermissions(RedeCredenciadaActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        }
                    } else {
                        // Tudo OK, podemos prosseguir.

                        ArrayList<String> nomeEndereco = new ArrayList<String>();
                        TextView tvNomeFantasia = (TextView) view.findViewById(R.id.tvNomeFantasia);
                        TextView tvEndereco = (TextView) view.findViewById(R.id.tvEndereco);
                        TextView tvBairro = (TextView) view.findViewById(R.id.tvBairro);
                        TextView tvCidade = (TextView) view.findViewById(R.id.tvCidade);
                        TextView tvCep = (TextView) view.findViewById(R.id.tvCep);
                        TextView tvLatitude = (TextView) view.findViewById(R.id.tvLatitude);
                        TextView tvLongitude = (TextView) view.findViewById(R.id.tvLongitude);
                        String latitude = tvLatitude.getText().toString();
                        String longitude = tvLongitude.getText().toString();
                        String nome = tvNomeFantasia.getText().toString();
                        String endereco = tvEndereco.getText().toString().replace("-", ",") + "," + tvBairro.getText().toString() + "," + tvCidade.getText().toString().replace("-", ",") + "," + tvCep.getText().toString();
                        //Toast.makeText(RedeCredenciadaActivity.this,endereco,Toast.LENGTH_LONG).show();
                        nomeEndereco.add(0, nome);
                        nomeEndereco.add(1, endereco);
                        nomeEndereco.add(2, latitude);
                        nomeEndereco.add(3, longitude);
                        Intent i = new Intent(RedeCredenciadaActivity.this, MapsActivity.class);
                        Bundle b = new Bundle();
                        b.putSerializable("endereco", nomeEndereco);
                        i.putExtras(b);
                        startActivity(i);
                    }
                }
            }

            @Override
            public void OnClickComoChegar(View view, int index) {
                //Toast.makeText(RedeCredenciadaActivity.this,"Como",Toast.LENGTH_LONG).show();
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    AlertDialog.Builder b = new AlertDialog.Builder(RedeCredenciadaActivity.this);
                    b.setMessage("Habilite o GPS para o funcionamento do mapa, isso pode acarretar no seu consumo de dados.");
                    b.setNeutralButton("OK", null);
                    b.show();
                } else {
                    // Se não possui permissão
                    if (ContextCompat.checkSelfPermission(RedeCredenciadaActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        // Verifica se já mostramos o alerta e o usuário negou na 1ª vez.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(RedeCredenciadaActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            // Caso o usuário tenha negado a permissão anteriormente, e não tenha marcado o check "nunca mais mostre este alerta"
                            AlertDialog.Builder b = new AlertDialog.Builder(RedeCredenciadaActivity.this);
                            b.setMessage("Para que o aplicativo funcione corretamento necessitamos das permissões necessárias.");
                            b.setNeutralButton("OK", null);
                            b.show();
                        } else {
                            // Solicita a permissão
                            ActivityCompat.requestPermissions(RedeCredenciadaActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        }
                    } else {
                        TextView tvNomeFantasia = (TextView) view.findViewById(R.id.tvNomeFantasia);
                        TextView tvEndereco = (TextView) view.findViewById(R.id.tvEndereco);
                        TextView tvBairro = (TextView) view.findViewById(R.id.tvBairro);
                        TextView tvCidade = (TextView) view.findViewById(R.id.tvCidade);
                        TextView tvCep = (TextView) view.findViewById(R.id.tvCep);
                        TextView tvLatitude = (TextView) view.findViewById(R.id.tvLatitude);
                        TextView tvLongitude = (TextView) view.findViewById(R.id.tvLongitude);
                        String latit = tvLatitude.getText().toString();
                        String longit = tvLongitude.getText().toString();
                        String endereco = tvEndereco.getText().toString().replace("-", ",") + "," + tvBairro.getText().toString() + "," + tvCidade.getText().toString().replace("-", ",") + "," + tvCep.getText().toString();

                        Geocoder coder = new Geocoder(RedeCredenciadaActivity.this, Locale.getDefault());
                        double longitude = 0.0;
                        double latitude = 0.0;
                        double latitude_cur = latitudeAtual;
                        double longitude_cur = longitudeAtual;
                        try {
                            if (!latit.equals("") && !longit.equals("")) {
                                longitude = Double.parseDouble(longit);
                                latitude = Double.parseDouble(latit);
                                overridePendingTransition(R.anim.slide_esquerda, R.anim.slide_direita);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + latitude_cur + "," + longitude_cur + "&daddr=" + latitude + "," + longitude));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                startActivity(intent);
                            } else {
                                ArrayList<Address> end = (ArrayList<Address>) coder.getFromLocationName(endereco, 1);
                                for (Address add : end) {
                                    if (add != null) {
                                        longitude = add.getLongitude();
                                        latitude = add.getLatitude();
                                        overridePendingTransition(R.anim.slide_esquerda, R.anim.slide_direita);
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + latitude_cur + "," + longitude_cur + "&daddr=" + latitude + "," + longitude));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                                        startActivity(intent);
                                    } else {
                                        finish();
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void OnClickInformacoes(View view, int index) {
                ArrayList<String> conteudo = new ArrayList<String>();
                TextView tvCodigoCredenciado = (TextView)view.findViewById(R.id.tvCodigoCredenciado);
                TextView tvCodigoFilial = (TextView)view.findViewById(R.id.tvCodigoFilial);
                TextView tvRazaoSocial = (TextView)view.findViewById(R.id.tvRazaoSocial);
                TextView tvNomeFantasia = (TextView)view.findViewById(R.id.tvNomeFantasia);
                TextView tvTipoCredenciado = (TextView)view.findViewById(R.id.tvTipoCredenciado);
                TextView tvCnpjCpf = (TextView)view.findViewById(R.id.tvCnpjCpf);
                TextView tvSite = (TextView)view.findViewById(R.id.tvSite);
                TextView tvEmail = (TextView)view.findViewById(R.id.tvEmail);

                conteudo.add(0,tvCodigoCredenciado.getText().toString());
                conteudo.add(1,tvCodigoFilial.getText().toString());
                conteudo.add(2,tvRazaoSocial.getText().toString());
                conteudo.add(3,tvNomeFantasia.getText().toString());
                conteudo.add(4,tvTipoCredenciado.getText().toString());
                conteudo.add(5,tvCnpjCpf.getText().toString());
                conteudo.add(6,cdDescricao);
                conteudo.add(7,tvSite.getText().toString());
                conteudo.add(8,tvEmail.getText().toString());

                Bundle b = new Bundle();
                b.putSerializable("informacao",conteudo);
                Intent i = new Intent(RedeCredenciadaActivity.this,RedeCredenciadaOrientadorInf.class);
                i.putExtras(b);
                startActivity(i);
                //Toast.makeText(RedeCredenciadaActivity.this, "Info", Toast.LENGTH_LONG).show();

            }
        };
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Serviços de localização conectado.");
        Location location;
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
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            handleNewLocation(location);
        }
    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        latitudeAtual = location.getLatitude();
        longitudeAtual = location.getLongitude();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Os serviços de localização suspensa Por favor volte a ligar.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Iniciar uma atividade que tenta resolver o erro
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "conexão Serviços de localização falhou com o código" + connectionResult.getErrorCode());
        }
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
        handleNewLocation(location);
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("marcador"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
