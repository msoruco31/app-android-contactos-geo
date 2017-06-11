package src.helper;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import src.DAO.ContactoDAO;
import src.iface.IContactoSchema;

public class Database {
    private static final String TAG = "db_stoTomas";
    private static final String DATABASE_NAME = "contactos.db";
    private DatabaseHelper mDbHelper;

    // Increment DB Version on any schema change
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;
    public static ContactoDAO mContactoDAO;



    public Database open() throws SQLException {
        mDbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase mDb = mDbHelper.getWritableDatabase();
        mDbHelper.onCreate(mDb);
        mContactoDAO = new ContactoDAO(mDb);
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public Database(Context context) {
        this.mContext = context;
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.e(TAG, "Base de datos Creada: " + IContactoSchema.CONTACTOS_TABLE_CREATE);
            //db.execSQL("DROP TABLE contactos;");
            //db.execSQL("DROP TABLE contacto;");
            db.execSQL(IContactoSchema.CONTACTOS_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            Log.w(TAG, "Upgrading database from version "
                    + oldVersion + " to "
                    + newVersion + " which destroys all old data");

            db.execSQL("DROP TABLE IF EXISTS "
                    + IContactoSchema.CONTACTO_TABLE);
            onCreate(db);

        }
    }

}