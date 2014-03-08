package com.futureworks.fontaniegos.ui.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureworks.fontaniegos.Model.Item;
import com.futureworks.fontaniegos.R;
import com.squareup.picasso.Picasso;

public class ActivityDetalleItem extends ActionBarActivity {

    /** Data */
    private Item        item;

    /** UI */
    private ImageView   imgHeader;
    private TextView    textTitulo;
    private TextView    textAutor;
    private TextView    textFecha;
    private TextView    textDescripcion;

    /** TypeFaces */
    private Typeface    mRobotoLight;
    private Typeface    mRobotoMedium;
    private Typeface    mRobotoRegular;
    private Typeface    mRobotoBold;

    /** Share */
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_item);

        // Recogemos el Object Item que enviamos desde la Activity
        // anterior
        this.item = getIntent().getExtras().getParcelable("Item");

        // Inicializacion de variables UI
        this.imgHeader = (ImageView) findViewById(R.id.imgTitulo);
        this.textTitulo = (TextView) findViewById(R.id.textTituloDetalle);
        this.textAutor = (TextView) findViewById(R.id.textAutorDetalle);
        this.textFecha = (TextView) findViewById(R.id.textFechaDetalle);
        this.textDescripcion = (TextView) findViewById(R.id.textDescription);

        // Cargamos la imagen
        Picasso.with(getApplicationContext()).load(this.item.getImg()).into(this.imgHeader);

        // Inicializamos los tipos de fuentes (TypeFaces)
        this.mRobotoLight = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
        this.mRobotoMedium = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        this.mRobotoRegular = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
        this.mRobotoBold = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");

        // Aplicamos el tipo de fuente a el componente TextView
        this.textTitulo.setTypeface(this.mRobotoMedium);
        this.textAutor.setTypeface(this.mRobotoBold);
        this.textFecha.setTypeface(this.mRobotoLight);
        this.textDescripcion.setTypeface(this.mRobotoRegular);

        // Rellenamos los campos de textos con sus respectivos datos
        this.textTitulo.setText(this.item.getTitulo());
        this.textAutor.setText(this.item.getAutor());
        this.textFecha.setText(this.item.getDate());
        this.textDescripcion.setText(this.item.getTexto());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_detalle_item, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = new ShareActionProvider(this);
        MenuItemCompat.setActionProvider(item, mShareActionProvider);

/*        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, this.item.getTitulo());

        mShareActionProvider.setShareIntent(shareIntent);*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.d("HHH", "Hola item menu");

        if (id == R.id.action_settings) {

            Log.d("HHH", "Hola item menu 3 ");

            return true;
        } else if (id == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, this.item.getTitulo());

            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(shareIntent);
            }

            Log.d("HHH", "Hola item menu 2");

            return  true;

        }
        return super.onOptionsItemSelected(item);
    }

    // Somewhere in the application.
    public void doShare(Intent shareIntent) {
        // When you want to share set the share intent.
        mShareActionProvider.setShareIntent(shareIntent);
    }

}
