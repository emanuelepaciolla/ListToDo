package com.example.emanuelepaciolla.listtodo;

import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

    public Note getNote(int position) {
        return notes.get(position);
    }

    public void addNote(Note nota) {
        notes.add(nota);
        notifyDataSetChanged();

    }

    public void updateNote(Note note, int position) {
        notes.set(position, note);
        notifyItemChanged(position);
    }

    public void deleteNote(int position){
        notes.remove(position);
        notifyDataSetChanged();
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
        if (notes.get(position).getIsState().equals("false")){
            holder.star.setVisibility(View.VISIBLE);
            holder.starblack.setVisibility(View.GONE);
        } else if (notes.get(position).getIsState().equals("true")){
            holder.star.setVisibility(View.GONE);
            holder.starblack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView notetitolo, notecorpo, dataCreazione, datascadenza;
        ImageButton star = (ImageButton) itemView.findViewById(R.id.star_preferences);
        ImageButton starblack = (ImageButton) itemView.findViewById(R.id.star_preferences_black);
        ImageButton color = (ImageButton) itemView.findViewById(R.id.palette_color);

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

            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posizione = getAdapterPosition();
                    if (notes.get(posizione).getIsState().equals("false")){
                        notes.get(posizione).setIsState("true");
                        star.setVisibility(View.GONE);
                        starblack.setVisibility(View.VISIBLE);
                        Note nota = notes.get(posizione);
                        System.out.println(nota);
                        ((Main_Activity)v.getContext()).D.updateNote(nota);
                    }
                }
            });
            starblack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posizione = getAdapterPosition();
                    if (notes.get(posizione).getIsState().equals("true")){
                        notes.get(posizione).setIsState("false");
                        starblack.setVisibility(View.GONE);
                        star.setVisibility(View.VISIBLE);
                        Note nota = notes.get(posizione);
                        System.out.println(nota);
                        ((Main_Activity)v.getContext()).D.updateNote(nota);
                    }
                }
            });
        }
    }
}

