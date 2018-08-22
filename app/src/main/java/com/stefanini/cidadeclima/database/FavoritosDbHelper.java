package com.stefanini.cidadeclima.database;

import android.content.Context;
import android.database.sqlite.*;

import static com.stefanini.cidadeclima.database.FavoritosReaderContract.SQL_CREATE_ENTRIES;
import static com.stefanini.cidadeclima.database.FavoritosReaderContract.SQL_DELETE_ENTRIES;

/**
 * Criado por Maicon Dias Castro em 21/08/2018.
 */
public class FavoritosDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Clima.db";

    public FavoritosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
