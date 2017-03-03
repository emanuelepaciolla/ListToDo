package com.example.emanuelepaciolla.listtodo;

import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Emanuele Paciolla on 01/03/2017.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    ArrayList<Note> notes = new ArrayList<>();
    private String POSITION = "POSITION";

    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nota_card, parent, false);
        return new NoteViewHolder(v);
    }

    public void addNote(Note nota) {
        notes.add(0, nota);
        notifyItemChanged(0);
    }

    public void updateNote(Note note, int position) {
        notes.set(position, note);
        notifyItemChanged(position);
    }

    public void deleteNote(int position){
        notes.remove(position);
        notifyItemChanged(position);
    }

    public void setData(ArrayList<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.notetitolo.setText(note.getTitolo());
        holder.notecorpo.setText(note.getTesto());
        holder.dataCreazione.setText(note.getDatacreazione());
        holder.datascadenza.setText(note.getDatascadenza());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView notetitolo, notecorpo, dataCreazione, datascadenza;

        NoteViewHolder(View itemView) {
            super(itemView);
            notetitolo = (TextView) itemView.findViewById(R.id.idtitolonotacard);
            notecorpo = (TextView) itemView.findViewById(R.id.idcorpocard);
            dataCreazione = (TextView) itemView.findViewById(R.id.idcreazionecard);
            datascadenza = (TextView) itemView.findViewById(R.id.datascadenzacard);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(v.getContext(), AddActivity.class);
                    int posizione = getAdapterPosition();
                    System.out.println(posizione);
                    intent.putExtra("posizione", posizione);
                    intent.putExtra("Titolo", notes.get(posizione).getTitolo());
                    intent.putExtra("Testo", notes.get(posizione).getTesto());
                    intent.putExtra("datascadenza", notes.get(posizione).getDatascadenza());
                    intent.putExtra(Main_Activity.ADD, Main_Activity.edit);
                    ((AppCompatActivity)v.getContext()).startActivityForResult(intent, 10);
                    return false;
                }
            });
        }
    }
}

