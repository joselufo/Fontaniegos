package com.futureworks.fontaniegos.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureworks.fontaniegos.Model.Item;
import com.futureworks.fontaniegos.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by TeRRo on 05/03/14.
 */
public class ItemsAdapter extends ArrayAdapter<Item> {

    private ArrayList<Item>         list;
    private Item                    item;
    private Context                 ctxt;

    /** TypeFaces */
    private Typeface    mRobotoLight;
    private Typeface    mRobotoMedium;
    private Typeface    mRobotoRegular;
    private Typeface    mRobotoBold;

    /**
     * Holder
     */
    static class ViewHolder {
        private TextView    textTitulo;
        private TextView    textDate;
        private ImageView   imgItem;
    }

    public ItemsAdapter(Context context, int resource, ArrayList<Item> objects) {
        super(context, resource, objects);

        this.list = objects;
        this.ctxt = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rootView = convertView;

        if (rootView == null) {
            LayoutInflater inflate = (LayoutInflater) this.ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rootView = inflate.inflate(R.layout.adapter_list_items, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.textTitulo = (TextView) rootView.findViewById(R.id.textTituloListItems);
            holder.textDate = (TextView) rootView.findViewById(R.id.textDateListItems);
            holder.imgItem = (ImageView) rootView.findViewById(R.id.imgItemList);

            rootView.setTag(holder);
        }

        ViewHolder holder = (ViewHolder) rootView.getTag();
        this.item = list.get(position);

        // Inicializamos los tipos de fuentes (TypeFaces)
        this.mRobotoLight = Typeface.createFromAsset(this.ctxt.getAssets(), "Roboto-Light.ttf");
        this.mRobotoMedium = Typeface.createFromAsset(this.ctxt.getAssets(), "Roboto-Medium.ttf");
        this.mRobotoRegular = Typeface.createFromAsset(this.ctxt.getAssets(), "Roboto-Regular.ttf");
        this.mRobotoBold = Typeface.createFromAsset(this.ctxt.getAssets(), "Roboto-Bold.ttf");

        // Aplicamos el tipo de fuente a el componente TextView
        holder.textTitulo.setTypeface(this.mRobotoMedium);
        holder.textDate.setTypeface(this.mRobotoLight);

        holder.textTitulo.setText(this.item.getTitulo());
        holder.textDate.setText(this.item.getDate());

        Picasso.with(this.ctxt).load(this.item.getImg()).into(holder.imgItem);

        return rootView;
    }
}

