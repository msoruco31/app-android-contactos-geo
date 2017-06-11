package src.iface;

/**
 * Created by msoruco on 08-06-2017.
 */

public interface IContactoSchema {
    String CONTACTO_TABLE = "contactos";
    String COLUMN_ID = "_id";
    String COLUMN_NOMBRE = "nombre";
    String COLUMN_EMAIL = "mail";
    String COLUMN_TELEFONO = "telefono";
    String CONTACTOS_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + CONTACTO_TABLE
            + " ("
            + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_NOMBRE
            + " TEXT NOT NULL, "
            + COLUMN_EMAIL
            + " TEXT,"
            + COLUMN_TELEFONO
            + " BIGINT "
            + ")";

    String[] USER_COLUMNS = new String[] { COLUMN_ID,
            COLUMN_NOMBRE, COLUMN_EMAIL, COLUMN_TELEFONO };
}
