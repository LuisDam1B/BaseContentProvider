package com.ejemplos.tema12.basecontentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.clienteslibreria.Clientes;

/**
 * Created by Portatil on 13/12/2014.
 */
public class ContentProviderClientes extends ContentProvider {


    Uri CONTENT_URI = Clientes.CONTENT_URI;
    final int CLIENTES = 1, CLIENTES_ID = 2;
    String TABLA_CLIENTES = "clientes";
    private ClientesSqliteHelper bDSH;
    UriMatcher uriMatcher;

    @Override
    public boolean onCreate() {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.ejemplos.tema12.basecontentprovider","clientes", CLIENTES);
        uriMatcher.addURI("com.ejemplos.tema12.basecontentprovider","clientes/#", CLIENTES_ID);
        bDSH= new ClientesSqliteHelper(getContext(),"bdClientes",null,1);

        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == CLIENTES_ID) where = "_id=" + uri.getLastPathSegment();
        SQLiteDatabase db = bDSH.getReadableDatabase(); //.getWritableDatabase();
        Cursor c = db.query(TABLA_CLIENTES, projection, where,selectionArgs, null, null, sortOrder);
        return c;
    }

    @Override
    public String getType(Uri uri) {

        int match = uriMatcher.match(uri);

        switch (match) {
            case CLIENTES:
                return "vnd.android.cursor.dir/vnd.com.ejemplos.tema12.basecontentprovider";
            case CLIENTES_ID:
                return "vnd.android.cursor.item/vnd.com.ejemplos.tema12.basecontentprovider";
            default:
                throw  new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long regId = 1;
        SQLiteDatabase db = bDSH.getWritableDatabase();
        regId = db.insert(TABLA_CLIENTES, null, values);
        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);

        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cont;
        String where = selection;

        if(uriMatcher.match(uri) == CLIENTES_ID) where = "_id=" + uri.getLastPathSegment();
        SQLiteDatabase db = bDSH.getWritableDatabase();
        cont = db.delete(TABLA_CLIENTES, where, selectionArgs);

        return cont;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int cont;
        String where = selection;
        if(uriMatcher.match(uri) == CLIENTES_ID) where = "_id=" + uri.getLastPathSegment();
        SQLiteDatabase db = bDSH.getWritableDatabase();
        cont = db.update(TABLA_CLIENTES, values, where, selectionArgs);

        return cont;
    }

    @Override
    public void shutdown() {
        super.shutdown();
        bDSH.close();
    }

    /*//Clase interna para declarar las constantes de columna
    public static final class Clientes implements BaseColumns
    {
        public static final String COL_NOMBRE = "nombre", COL_TELEFONO = "telefono", COL_EMAIL = "email";
    }*/

}
