package com.inages.ingesapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.inages.ingesapp.data.IngesContract.IngeEntry;

public class IngesDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Inges.db";
    public SQLiteDatabase sqLiteDatabase;

    public IngesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase.execSQL("CREATE TABLE " + IngeEntry.TABLE_NAME + " ("
                + IngeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + IngeEntry.ID + " TEXT NOT NULL,"
                + IngeEntry.NAME + " TEXT NOT NULL,"
                + IngeEntry.SPECIALTY + " TEXT NOT NULL,"
                + IngeEntry.PHONE_NUMBER + " TEXT NOT NULL,"
                + IngeEntry.BIO + " TEXT NOT NULL,"
                + IngeEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + IngeEntry.ID + "))");

        ContentValues values = new ContentValues();
        // Pares clave-valor
        values.put(IngeEntry.ID, "L-001");
        values.put(IngeEntry.NAME, "Carlos solarte");
        values.put(IngeEntry.SPECIALTY, "Ingeniero");

        values.put(IngeEntry.PHONE_NUMBER, "300 200 1111");

        values.put(IngeEntry.BIO, "Carlos es una profesional con 5 años de trayectoria...");    values.put(IngeEntry.AVATAR_URI, "carlos_solarte.jpg");
        // Insertar...
        sqLiteDatabase.insert(IngeEntry.TABLE_NAME, null, values);


    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Comandos SQL
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveInge(Inge inge) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                IngeEntry.TABLE_NAME,
                null,
                inge.toContentValues());

    }


    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockInge(sqLiteDatabase, new Inge("Carlos Perez", "Ingeniero penalista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en casos penales.",
                "carlos_perez.jpg"));
        mockInge(sqLiteDatabase, new Inge("Daniel Samper", "Ingeniero accidentes de tráfico",
                "300 200 2222", "Gran profesional con experiencia de 5 años en accidentes de tráfico.",
                "daniel_samper.jpg"));
        mockInge(sqLiteDatabase, new Inge("Lucia Aristizabal", "Ingeniero de derechos laborales",
                "300 200 3333", "Gran profesional con más de 3 años de experiencia en defensa de los trabajadores.",
                "lucia_aristizabal.jpg"));
        mockInge(sqLiteDatabase, new Inge("Marina Acosta", "Ingeniero de familia",
                "300 200 4444", "Gran profesional con experiencia de 5 años en casos de familia.",
                "marina_acosta.jpg"));
        mockInge(sqLiteDatabase, new Inge("Olga Ortiz", "Ingeniero de administración pública",
                "300 200 5555", "Gran profesional con experiencia de 5 años en casos en expedientes de urbanismo.",
                "olga_ortiz.jpg"));
        mockInge(sqLiteDatabase, new Inge("Pamela Briger", "Ingeniero fiscalista",
                "300 200 6666", "Gran profesional con experiencia de 5 años en casos de derecho financiero",
                "pamela_briger.jpg"));
        mockInge(sqLiteDatabase, new Inge("Rodrigo Benavidez", "Ingeniero Mercantilista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en redacción de contratos mercantiles",
                "rodrigo_benavidez.jpg"));
        mockInge(sqLiteDatabase, new Inge("Tom Bonz", "Ingeniero penalista",
                "300 200 1111", "Gran profesional con experiencia de 5 años en casos penales.",
                "tom_bonz.jpg"));
    }

    public long mockInge(SQLiteDatabase db, Inge inge) {
        return db.insert(
                IngeEntry.TABLE_NAME,
                null,
                inge.toContentValues());
    }

    public Cursor getAllInges() {
        return getReadableDatabase()
                .query(
                        IngeEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getIngeById(String IngeId) {
        Cursor c = getReadableDatabase().query(
                IngeEntry.TABLE_NAME,
                null,
                IngeEntry.ID + " LIKE ?",
                new String[]{IngeId},
                null,
                null,
                null);
        return c;
    }


    public int deleteINGE(String INGEId) {
        return getWritableDatabase().delete(
                IngeEntry.TABLE_NAME,
                IngeEntry.ID + " LIKE ?",
                new String[]{INGEId});
    }

    public int updateINGE(Inge INGE, String INGEId) {
        return getWritableDatabase().update(
                IngeEntry.TABLE_NAME,
                INGE.toContentValues(),
                IngeEntry.ID + " LIKE ?",
                new String[]{INGEId}
        );
    }

    public long saveINGE(Inge INGE) {
        return getWritableDatabase().insert(
                IngeEntry.TABLE_NAME,
                null,
                INGE.toContentValues());

    }


}
