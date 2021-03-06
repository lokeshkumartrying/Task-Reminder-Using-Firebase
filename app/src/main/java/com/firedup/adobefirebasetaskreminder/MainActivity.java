package com.firedup.adobefirebasetaskreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView title_page, subtitle_page, end_page;
    DatabaseReference reference;
    RecyclerView ourdoes;
    ArrayList<MyDoes> list;
    DoesAdapter doesAdapter;
    Button btnAddNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title_page = findViewById(R.id.titlepage);
        subtitle_page = findViewById(R.id.subtitlepage);
        end_page = findViewById(R.id.endpage);
        btnAddNew = findViewById(R.id.btnAddNew);

        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.ttf");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");


      title_page.setTypeface(MMedium);
       subtitle_page.setTypeface(MLight);
       end_page.setTypeface(MLight);
        btnAddNew.setTypeface(MLight);

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,NewTaskAct.class);
                startActivity(a);
            }
        });
        ourdoes = findViewById(R.id.ourdoes);

        list = new ArrayList<MyDoes>();

        reference = FirebaseDatabase.getInstance().getReference().child("DoesApp");
//        FirebaseRecyclerOptions<MyDoes> options =
//                new FirebaseRecyclerOptions.Builder<MyDoes>()
//                        .setQuery(reference, MyDoes.class)
//                        .build();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    MyDoes p = dataSnapshot1.getValue(MyDoes.class);

                    list.add(p);
                }

                LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                ourdoes.setLayoutManager(llm);

                doesAdapter = new DoesAdapter(MainActivity.this, list);
                ourdoes.setAdapter(doesAdapter);
                doesAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });
//        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<MyDoes, DoesAdapter.MyViewHolder>(options) {
//            @NonNull
//            @Override
//            public DoesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.item_does, parent, false);
//
//                return new DoesAdapter.MyViewHolder(view);
//            }
//            // ...
//
//            @Override
//            public void onDataChanged() {
//                // Called each time there is a new data snapshot. You may want to use this method
//                // to hide a loading spinner or check for the "no documents" state and update your UI.
//                // ...
//            }
//
//            @Override
//            public void onError(DatabaseError e) {
//                // Called when there is an error getting data. You may want to update
//                // your UI to display an error message to the user.
//                // ...
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull DoesAdapter.MyViewHolder holder, int position, @NonNull MyDoes model) {
//
//            }
//        };
    }
}