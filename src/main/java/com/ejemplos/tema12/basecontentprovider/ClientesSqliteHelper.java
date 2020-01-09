package com.ejemplos.tema12.basecontentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Portatil on 13/12/2014.
 */
public class ClientesSqliteHelper extends SQLiteOpenHelper {
    String cadena="CREATE TABLE clientes " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " nombre TEXT, " +
            " telefono TEXT, " +
            " email TEXT );";

    public ClientesSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String nombre, telefono, email;

        db.execSQL(cadena);
        //Insertamos 15 clientes de ejemplo
        for(int i=1; i<=15; i++)
        {
         nombre = "Cliente" + i;  telefono = "900-123-00" + i;  email = "email" + i + "@mail.com";
         db.execSQL("INSERT INTO clientes (nombre, telefono, email) " + "VALUES ('" + nombre + "', '" + telefono +"', '" +email + "');");
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS clientes");
        db.execSQL(cadena);
    }
}
