package com.example.emanuelepaciolla.listtodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;



/**
 * Created by Emanuele Paciolla on 01/03/2017.
 */

public class Main_Activity extends AppCompatActivity {

    Intent intent;

    Database D;

    static final String ADD = "add";

    NoteAdapter adapter;
    RecyclerView noteRecycleView;
    LinearLayoutManager layoutManager;

    final int REQUESTSTARTACTIVITYONRESULT = 10;
    static int add = 1;
    static int edit = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        noteRecycleView = (RecyclerView) findViewById(R.id.mainRV);
        adapter = new NoteAdapter();
        layoutManager= new LinearLayoutManager(this);
        noteRecycleView.setLayoutManager(layoutManager);
        noteRecycleView.setAdapter(adapter);



        FloatingActionButton inseriscinotaBTN = (FloatingActionButton) findViewById(R.id.bottone_inserisci);
        inseriscinotaBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main_Activity.this, AddActivity.class);
                i.putExtra(ADD, add);
                startActivityForResult(i, REQUESTSTARTACTIVITYONRESULT);
            }
        });

        D = new Database(this);
        adapter.setData(D.getAllNotes());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data.getIntExtra(AddActivity.REQUEST, -1) == AddActivity.REQUEST_ADD){
            Note nota = new Note(data.getStringExtra("Titolo"), data.getStringExtra("Testo"), data.getStringExtra("Scadenza"));
            long x = D.addNote(nota);
            nota.setId(x);
            adapter.addNote(nota);
        } else if (resultCode == Activity.RESULT_OK && data.getIntExtra(AddActivity.REQUEST, -1) == AddActivity.REQUEST_EDIT){
            int posizione = data.getIntExtra("position", -1);
            Note nota = adapter.getNote(posizione);
            nota.setTitolo(data.getStringExtra("Titolo"));
            nota.setTesto(data.getStringExtra("Testo"));
            nota.setDatascadenza(data.getStringExtra("Scadenza"));
            System.out.println(posizione);
            D.updateNote(nota);
            adapter.updateNote( nota, posizione);
        } else if (resultCode == Activity.RESULT_OK && data.getIntExtra(AddActivity.REQUEST, -1) == AddActivity.REQUEST_DELETE){
            int posizione = data.getIntExtra("position", -1);
            System.out.println(posizione + "Sto nella richiesta di eliminazione");
            D.deleteNote(adapter.getNote(posizione));
            adapter.deleteNote(posizione);
        }
    }
}
