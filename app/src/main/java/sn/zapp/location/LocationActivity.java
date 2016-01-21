package sn.zapp.location;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import sn.zapp.R;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Toolbar mToolbar;
    private FloatingActionButton mFab = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setFab((FloatingActionButton) findViewById(R.id.fab_location));
        getFab().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMapsIntent();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void startMapsIntent() {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=51.287177, 6.196295");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
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

        // Add a marker in Sydney and move the camera
        LatLng hausUhle = new LatLng(51.287177, 6.196295);
        mMap.addMarker(new MarkerOptions().position(hausUhle).title("Marker in Bracht"));
        mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(hausUhle, 17.0f) );
    }

    public FloatingActionButton getFab() {
        return mFab;
    }

    public void setFab(FloatingActionButton fab) {
        this.mFab = fab;
    }

    @Override
    protected void onDestroy() {
        Runtime.getRuntime().gc();
        super.onDestroy();
    }
}
