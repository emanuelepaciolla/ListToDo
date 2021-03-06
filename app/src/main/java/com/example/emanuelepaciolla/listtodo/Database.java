package com.example.emanuelepaciolla.listtodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Emanuele Paciolla on 23/02/2017.
 */

public class Database extends SQLiteOpenHelper {

    // Notes Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_BODY = "body";
    private static final String KEY_DATE_SCADENZA = "datascadenza";
    private static final String KEY_IS_SPECIAL = "isSpecial";
    private static final String KEY_COLOR = "Color";

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 9;

    // Database Name
    private static final String DATABASE_NAME = "notes";

    // Contacts table name
    private static final String TABLE_NOTES = "contacts";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTE_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_BODY + " TEXT, " + KEY_DATE_SCADENZA + " TEXT, " + KEY_IS_SPECIAL + " TEXT, " + KEY_COLOR + " TEXT " + ")";
        System.out.println(CREATE_NOTE_TABLE);
        db.execSQL(CREATE_NOTE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        // Create tables again
        onCreate(db);
    }

    public long addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitolo());
        values.put(KEY_BODY, note.getTesto());
        values.put(KEY_DATE_SCADENZA, note.getDatascadenza());
        values.put(KEY_IS_SPECIAL, note.getIsState());
        values.put(KEY_COLOR, note.getColor());

        // Inserting Row
        long ritorno = db.insert(TABLE_NOTES, null, values);
        db.close(); // Closing database connection
        return ritorno;
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notesList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                // Adding note to list
                System.out.println(note);
                note.setIsState(cursor.getString(4));
                note.setColor(cursor.getString(5));
                System.out.println("Valore " + cursor.getString(4));
                notesList.add(note);

            } while (cursor.moveToNext());
        }

        // return notes list
        return notesList;
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + "= ?", new String[]{String.valueOf(note.getId())});
        db.close();
    }
    public int updateNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, note.getTitolo());
        values.put(KEY_BODY, note.getTesto());
        values.put(KEY_DATE_SCADENZA, note.getDatascadenza());
        values.put(KEY_IS_SPECIAL, note.getIsState());
        values.put(KEY_COLOR, note.getColor());
        // updating row

        System.out.println("Valore della query" + values);
        return db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }



    public int updateSpecial(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_IS_SPECIAL, note.getIsState());

        return db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }
}