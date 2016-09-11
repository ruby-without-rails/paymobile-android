package br.com.frmichetti.carhollics.android.view.activity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import br.com.frmichetti.carhollics.android.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the firebaseUser will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the firebaseUser has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //-23.227003, -45.903738
        LatLng latLng = new LatLng(-23.227003, -45.903738);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {

            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 30);

            for (Address address : addresses) {

                Log.i("ADDRESS-POSTAL-CODE", ((address.getPostalCode() != null) && (address.getPostalCode().length() > 5) ? address.getPostalCode() : ""));

                Log.i("ADDRESS-STREET", ((address.getPostalCode() != null) && (address.getPostalCode().length() > 5) && (address.getAddressLine(0) != null) ? address.getAddressLine(0) : ""));
            }

            mMap.addMarker(new MarkerOptions().position(latLng).title("Casa").draggable(false).visible(true));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));

        } catch (IOException e) {

            Log.e("ERROR", e.toString());
        }


    }
}
