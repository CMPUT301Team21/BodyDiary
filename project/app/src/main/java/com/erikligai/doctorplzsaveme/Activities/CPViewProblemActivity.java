package com.erikligai.doctorplzsaveme.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.erikligai.doctorplzsaveme.Adapters.commentAdapter;
import com.erikligai.doctorplzsaveme.Models.Comment;
import com.erikligai.doctorplzsaveme.Models.Problem;
import com.erikligai.doctorplzsaveme.R;
import com.erikligai.doctorplzsaveme.backend.Backend;

import java.util.ArrayList;

public class CPViewProblemActivity extends AppCompatActivity {

    private commentAdapter adapter;
    // Define Views
    private RecyclerView commentList;
    private TextView problemTitle;
    private TextView problemDescription;
    private Button viewRecordsBtn;
    private FloatingActionButton fab;

    private Problem problem;
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private int problemPos;
    private String Pos;
    private String patientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cp_view_problem);

        problemTitle = findViewById(R.id.cpProblemTitle);
        problemDescription = findViewById(R.id.cpProblemDescription);
        viewRecordsBtn = findViewById(R.id.cpProblemRecords);
        fab = findViewById(R.id.comment_fab);

        Intent intent = getIntent();
        Pos = intent.getStringExtra("problemID");
        patientID = intent.getStringExtra("patientID");

        problemPos = Integer.parseInt(Pos);

        Backend backend = Backend.getInstance();
        problem = backend.GetCPPatientProblem(patientID,problemPos);

        comments = problem.getComments();

        problemTitle.setText(problem.getTitle());
        problemDescription.setText(problem.getDescription());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("fab", "add comment");
                //calls AddRecordActivity
                Intent intent = new Intent(view.getContext(), CPAddCommentActivity.class);
                intent.putExtra("ProblemPos", problemPos);
                intent.putExtra("patientId",patientID);
                startActivity(intent);
            }
        });

        viewRecordsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openCPRecordActivity();
            }
        });

        initRecyclerView(patientID);
    }

    private void initRecyclerView(String patientID) {
//        Log.d(TAG, "initRecyclerView: init");

        commentList = findViewById(R.id.commentRecyclerView);
        // display recyclerview
        commentList.setVisibility(View.VISIBLE);
        adapter = new commentAdapter(comments, this);
        commentList.setAdapter(adapter);
        commentList.setLayoutManager(new LinearLayoutManager(this));
    }

    public void openCPRecordActivity(){
        Intent intent = new Intent(this,CPRecordActivity.class);
        intent.putExtra("patientID", patientID); // attach patient id to intent
        intent.putExtra("problemID", Pos);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}