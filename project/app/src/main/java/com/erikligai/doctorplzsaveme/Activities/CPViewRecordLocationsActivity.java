package com.erikligai.doctorplzsaveme.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.erikligai.doctorplzsaveme.Models.Patient;
import com.erikligai.doctorplzsaveme.Models.Problem;
import com.erikligai.doctorplzsaveme.Models.Record;
import com.erikligai.doctorplzsaveme.R;
import com.erikligai.doctorplzsaveme.backend.Backend;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class CPViewRecordLocationsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {
    private GoogleMap mMap;

    /** BACKEND TESTING
     Patient p = new Patient("Joe Hepp","id-100", "heppelle@", "123456");
     Problem problem = new Problem("Title", "Leg hurt");
     Record record = new Record("Leg 1", "Leg hurt");
     problem.addRecord(record);
     p.addProblem(problem);
     for Record rec in problem.getRecords(){
     LatLng latlng = rec.getGeolocation();
     }
     /** BACKEND TESTING */

    //private static final LatLng VAN = new LatLng(49.246292, -123.116226);
    private Marker Van;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpview_record_locations);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map3);
        mapFragment.getMapAsync(this);
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

    //TODO: 1. Talk to backend. Get all records for patient.
    //TODO: 2. For each record, try getting the geolocation.
    //TODO: 3. if record has a geolocation info, create a marker from it.
    private String patientID;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        Intent intent = getIntent();
        patientID = intent.getStringExtra("patientID");
        Patient patient = Backend.getInstance().GetCPPatient(patientID);
        int i,j;
        ArrayList<Problem> problems = patient.getProblemList();
        for (i = 0; i < problems.size(); i++) {
            ArrayList<Record> records = problems.get(i).getRecords();
            for( j = 0; j < records.size(); j++) {
                if (records.get(j).getGeolocation() != null) {
                    Van = mMap.addMarker(new MarkerOptions().position(records.get(j).getGeolocation()).title(problems.get(i).getTitle()).snippet(records.get(j).getTitle()));

                    List<Integer> index = new ArrayList<Integer>();
                    index.add(i);
                    index.add(j);
                    Van.setTag(index);

                    mMap.setOnMarkerClickListener(this);
                    mMap.setOnInfoWindowClickListener(this);
                }

            }
        }
        if (Van != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Van.getPosition(), 10));
        }
        //for record in recordList
        // LatLng location = record.getGeolocation();
        // mMap.addMarker(new MarkerOptions().position(location).title(record.getProblem));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(location)); // move camera to last added location.
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        //int num = (int) marker.getTag();                         // get data from marker(probably recordID)
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //int num = (int) marker.getTag();                                // get data from marker(probably recordID)
        Intent intent = new Intent(CPViewRecordLocationsActivity.this, CPViewRecordActivity.class);
        List<Integer> index = (List<Integer>) marker.getTag();
        intent.putExtra("problemIndex", index.get(0));
        intent.putExtra("recordIndex", index.get(1));
        intent.putExtra("patientID",patientID);
        Log.i("P",Integer.toString(index.get(0)));
        Log.i("R",Integer.toString(index.get(1)));
        startActivity(intent);
    }

}
