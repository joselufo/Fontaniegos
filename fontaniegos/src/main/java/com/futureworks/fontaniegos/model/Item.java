package com.futureworks.fontaniegos.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TeRRo on 05/03/14.
 */
public class Item implements Parcelable{

    private String titulo;
    private String date;
    private String textIntro;
    private String link;
    private String img;
    private String autor;
    private String texto;

    //Constructor
    public Item() {
    }

    public Item(String titulo, String date, String textIntro, String link, String img, String autor, String texto) {
        this.titulo = titulo;
        this.date = date;
        this.textIntro = textIntro;
        this.link = link;
        this.img = img;
        this.autor = autor;
        this.texto = texto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTextIntro() {
        return textIntro;
    }

    public void setTextIntro(String textIntro) {
        this.textIntro = textIntro;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    protected Item(Parcel in) {
        titulo = in.readString();
        date = in.readString();
        textIntro = in.readString();
        link = in.readString();
        img = in.readString();
        autor = in.readString();
        texto = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(date);
        dest.writeString(textIntro);
        dest.writeString(link);
        dest.writeString(img);
        dest.writeString(autor);
        dest.writeString(texto);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };


}
