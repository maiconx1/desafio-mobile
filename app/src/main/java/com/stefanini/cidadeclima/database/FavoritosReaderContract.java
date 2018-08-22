package com.stefanini.cidadeclima.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Criado por Maicon Dias Castro em 21/08/2018.
 */
public final class FavoritosReaderContract {
    private FavoritosReaderContract() {}

    public static class Favorito implements BaseColumns {
        public static final String TABLE_NAME = "favorito";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NOME = "nome";
    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Favorito.TABLE_NAME + " (" +
                    Favorito._ID + " INTEGER PRIMARY KEY," +
                    Favorito.COLUMN_ID + " INTEGER," +
                    Favorito.COLUMN_NOME + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Favorito.TABLE_NAME;
}
