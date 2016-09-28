package br.com.trasmontano.trasmontanoassociadomobile;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //private Callback<List<OrientadorMedicoGeoLocalizacao>> callbackGeoLocalizacao;
    double longitude = 0.0;
    double latitude = 0.0;
    private GoogleMap mMap;
    private String endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        overridePendingTransition(R.anim.slide_esquerda, R.anim.slide_direita);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //mMap = findViewById(R.id.map);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ArrayList<String> nomeEndereco = getIntent().getStringArrayListExtra("endereco");
        String endereco = nomeEndereco.get(1).toString();
        String nome = nomeEndereco.get(0).toString();
        String latit = nomeEndereco.get(2).toString();
        String longit = nomeEndereco.get(3).toString();

        if (!nomeEndereco.get(0).toString().equals("")) {
            try {
                if (!latit.equals("") && !longit.equals("")) {
                    LatLng local = new LatLng(Double.parseDouble(latit), Double.parseDouble(longit));
                    mMap.addMarker(new MarkerOptions().position(local).title(nome));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 16.0f));
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 11.0f));
                } else {
                    Geocoder coder = new Geocoder(this, Locale.getDefault());
                    ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(endereco, 1);
                    for (Address add : adresses) {
                        if (add != null) {//Controls to ensure it is right address such as country etc.
                            longitude = add.getLongitude();
                            latitude = add.getLatitude();
                        }
                        LatLng local = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(local).title(nome));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 16.0f));
                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 11.0f));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Geocoder coder = new Geocoder(this, Locale.getDefault());
            double longitude = 0.0;
            double latitude = 0.0;
            try {
                ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(endereco, 1);
                for (Address add : adresses) {
                    if (add != null) {//Controls to ensure it is right address such as country etc.
                        longitude = add.getLongitude();
                        latitude = add.getLatitude();

                        LatLng local = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(local).title(nome));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 16.0f));
                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 11.0f));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
