package com.erikligai.doctorplzsaveme.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.erikligai.doctorplzsaveme.Models.Problem;
import com.erikligai.doctorplzsaveme.R;

import java.util.ArrayList;

/**
 *  care provider activity to show patients problems in a recycler view.
 */

public class CPProblemListActivity extends AppCompatActivity {
    private CPProblemAdapter adapter;
    private ArrayList<Problem> problems = new ArrayList<>();

    //sample problem list
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpproblem_list);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.problems);

        RecyclerView problemRView = findViewById(R.id.problems_recyclerview);
        problemRView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        problemRView.setLayoutManager(layoutManager);

        adapter = new CPProblemAdapter(problems);
        problemRView.setAdapter(adapter);
        adapter.setOnEntryClickListener(new CPProblemAdapter.OnEntryClickListener() {
            @Override
            public void onEntryClick(int position) {
                Intent intent = new Intent(getApplicationContext(), CPViewProblemActivity.class);
                intent.putExtra("Pos", position);
                Log.d("rview", Integer.toString(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_search:
                // User chose the "Settings" item, show the app settings UI...
                //calls ProblemSearchActivity
                Intent intent = new Intent(this, CPProblemSearchActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
