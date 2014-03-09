package com.futureworks.fontaniegos.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.futureworks.fontaniegos.model.Item;
import com.futureworks.fontaniegos.R;
import com.futureworks.fontaniegos.ui.activities.ActivityDetalleItem;
import com.futureworks.fontaniegos.ui.activities.MainActivity;
import com.futureworks.fontaniegos.ui.adapter.ItemsAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by TeRRo on 05/03/14.
 */
public class FragmentMain extends Fragment {

    private static final String URL = "http://ayuntamientofuentes.com/novedades/";

    private ArrayList<Item> list;

    //Variables de la UI
    private ListView        listView;
    private ItemsAdapter    mAdapter;

    private MainActivity    mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.mActivity = (MainActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inicializacion de variables;
        list = new ArrayList<Item>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Inicializacion de variables de la UI
        this.listView = (ListView) rootView.findViewById(R.id.listViewItems);

        //Listener ListView
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("Click", "He pulsado en un item");

                Intent i = new Intent(getActivity(), ActivityDetalleItem.class);
                i.putExtra("Item", list.get(position));
                startActivity(i);

            }
        });

        new GetData().execute();

        return rootView;
    }

    /**
     * Parsea los datos directamente del DOM de la pagina web
     */
    private class GetData extends AsyncTask<Void, Void, Void> {

        String[] urls = {"http://ayuntamientofuentes.com/novedades/", "http://ayuntamientofuentes.com/novedades/2/"};

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mActivity.progressOn();

        }

        @Override
        protected Void doInBackground(Void... params) {

            //Nos conectamos al website
            try {

                for (int i = 0; i < urls.length; i++) {
                    Document document = Jsoup.connect(urls[i]).get();

                    Elements listaNoticias = document.select("div.ficha_prenews");

                    Item item;

                    for (int o = 0; o < listaNoticias.size(); o++) {

                        Log.d("WebScrapping", Integer.toString(o));

                        item = new Item();
                        StringBuilder textoFinal = new StringBuilder();

                        Element noticia = listaNoticias.get(o);

                        Element titular = noticia.select("div.titu_prenews").first();
                        Element fechaNoticia = noticia.select("div.date_prenews").first();
                        Element descripcion = noticia.select("div.txt_prenews").first();

                        Element etiquetaA = descripcion.select("a").first();
                        String link = etiquetaA.attr("href");

                        Element image = noticia.select("img").first();
                        String img = image.attr("src");

                        Document detalle = Jsoup.connect(link).get();
                        Element autor = detalle.select("div.from_name").first();
                        Elements elementP = detalle.select("p");

                        //Recorremos todos los elementos que haya con la etiqueta "p"
                        //para recoger el contenido de la noticia
                        for (int h = 0; h < elementP.size(); h++) {
                            Element textoPequeño = elementP.get(h);

                            //Si no es el primer elemento
                            if (h != 0) {
                                textoFinal.append(System.getProperty("line.separator"));
                                textoFinal.append(System.getProperty("line.separator"));
                            }

                            textoFinal.append(textoPequeño.text());

                            int line = elementP.size() - h;
                            if (line == 1) {
                                textoFinal.append(System.getProperty("line.separator"));
                                textoFinal.append(System.getProperty("line.separator"));

                                Log.d("WebScrapping", "Entro");
                            }

                        }

                        //Rellenamos el objeto Item con los datos recogidos del DOM
                        item.setTitulo(titular.text());
                        item.setDate(fechaNoticia.text());
                        item.setTextIntro(descripcion.text());
                        item.setLink(link);
                        item.setImg(img);

                        if (autor != null) {
                            item.setAutor(autor.text());
                        }

                        item.setTexto(textoFinal.toString());

                        //Añadimos el item seteado a la lista de Items
                        list.add(item);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    mAdapter = new ItemsAdapter(getActivity(), R.id.listViewItems, list);
                    listView.setAdapter(mAdapter);

                }
            });

            mActivity.progressOff();

        }
    }
}
