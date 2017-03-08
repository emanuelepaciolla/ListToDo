package com.example.emanuelepaciolla.listtodo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Emanuele Paciolla on 01/03/2017.
 */
public class AddActivity extends AppCompatActivity{

    final String TITOLO = "Titolo";
    final String TESTO = "Testo";
    final String SCADENZA = "Scadenza";
    public static final String REQUEST= "Richiesta";
    public static final int REQUEST_ADD = 1;
    public static final int REQUEST_EDIT = 2;
    public static final int REQUEST_DELETE = 3;

    EditText titoloEditText, testoEditText, datascadenzaEditText;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        intent = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.include);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        titoloEditText = (EditText) findViewById(R.id.inserisci_titolo);
        testoEditText = (EditText) findViewById(R.id.inserisci_corpo);
        datascadenzaEditText = (EditText) findViewById(R.id.inserisci_scadenza);
        final Calendar calendario = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                calendario.set(Calendar.YEAR, year);
                calendario.set(Calendar.MONTH, monthOfYear);
                calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String data = calendario.get(Calendar.YEAR) + "/" +  (calendario.get(Calendar.MONTH)+1) + "/" + calendario.get(Calendar.DAY_OF_MONTH);
                datascadenzaEditText.setText(data);
            }

        };
        datascadenzaEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddActivity.this, date, calendario
                        .get(Calendar.YEAR), calendario.get(Calendar.MONTH),
                        calendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (intent.getIntExtra(Main_Activity.ADD, Main_Activity.add) == Main_Activity.add) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_confirm, menu);
        } else if (intent.getIntExtra(Main_Activity.ADD, Main_Activity.edit) == Main_Activity.edit){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_edit, menu);
            Intent i = getIntent();
            titoloEditText.setText(i.getStringExtra("Titolo"));
            testoEditText.setText(i.getStringExtra("Testo"));
            datascadenzaEditText.setText(i.getStringExtra("datascadenza"));
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() ==  R.id.action_confirm) {
            inserisciNote();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            setResult(Activity.RESULT_CANCELED);
            finish();
        } else if (item.getItemId() == R.id.button_edit){
            updateNote();
            finish();
        } else if (item.getItemId() == R.id.button_delete){
            deleteNote();
            finish();
        }return super.onOptionsItemSelected(item);
    }

    private void inserisciNote() {
        Intent intent = new Intent();
        intent.putExtra(TITOLO, titoloEditText.getText().toString());
        intent.putExtra(TESTO, testoEditText.getText().toString());
        intent.putExtra(SCADENZA, datascadenzaEditText.getText().toString());
        intent.putExtra(REQUEST, REQUEST_ADD);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    private void updateNote(){
        Intent intent = new Intent();
        Intent i = getIntent();
        int posizione = i.getIntExtra("posizione", -1);
        intent.putExtra(TITOLO, titoloEditText.getText().toString());
        intent.putExtra(TESTO, testoEditText.getText().toString());
        intent.putExtra(SCADENZA, datascadenzaEditText.getText().toString());
        intent.putExtra("position", posizione);
        intent.putExtra(REQUEST, REQUEST_EDIT);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    private void deleteNote(){
        Intent intent = new Intent();
        Intent i = getIntent();
        int posizione = i.getIntExtra("posizione", -1);
        intent.putExtra("position", posizione);
        intent.putExtra(REQUEST, REQUEST_DELETE);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
