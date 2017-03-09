package com.example.emanuelepaciolla.listtodo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Note {
    private String titolo="";
    private String dataultimamodifica;
    private String testo="";
    private String datascadenza;
    private String datacreazione;
    private String isState;
    private long id;

    public Note(int id, String titolo, String testo, String datascadenza) {
        this.id = id;
        this.titolo = titolo;
        this.testo = testo;
        this.datascadenza = datascadenza;
        Date date= Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String x = format.format(date);
        datacreazione=x;
        isState = "false";
    }

    public Note(String titolo, String testo, String datascadenza) {
        this.titolo = titolo;
        this.testo = testo;
        this.datascadenza = datascadenza;
        Date date= Calendar.getInstance().getTime();
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String x = format.format(date);
        datacreazione=x;
        isState = "false";
    }

    public Note() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIsState() {

        return isState;
    }

    public void setIsState(String isState) {
        this.isState = isState;
    }

    public String getDatacreazione() {
        return datacreazione;
    }

    public void setDatacreazione(String datacreazione) {
        this.datacreazione = datacreazione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }


    public String getDataultimamodifica() {
        return dataultimamodifica;
    }

    public void setDataultimamodifica(String dataultimamodifica) {
        this.dataultimamodifica = dataultimamodifica;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getDatascadenza() {
        return datascadenza;
    }

    public void setDatascadenza(String datascadenza) {
        this.datascadenza = datascadenza;
    }

    @Override
    public String toString() {
        return "Note{" +
                "titolo='" + titolo + '\'' +
                ", testo='" + testo + '\'' +
                ", datascadenza='" + datascadenza + '\'' +
                ", datacreazione='" + datacreazione + '\'' +
                ", isState='" + isState + '\'' +
                ", id=" + id +
                '}';
    }
}
