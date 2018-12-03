package com.erikligai.doctorplzsaveme.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.erikligai.doctorplzsaveme.Adapters.RecyclerSelectBodyLocationAdapter;
import com.erikligai.doctorplzsaveme.Adapters.RecyclerViewPhotoAdapter;
import com.erikligai.doctorplzsaveme.R;

public class SelectByLocationActivity extends AppCompatActivity {
    /** displays bodylocation photos
     *  allows user to click on a photo to select a body location photo for a photo in record.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_by_location);

        // recycler view uses RecyclerSelectBodyLocationAdapter.
        RecyclerView photoRecycler = findViewById(R.id.PhotoRecyclerView2);
        photoRecycler.setHasFixedSize(true);
        RecyclerSelectBodyLocationAdapter adapter = new RecyclerSelectBodyLocationAdapter(this);
        photoRecycler.setAdapter(adapter);
        photoRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}
