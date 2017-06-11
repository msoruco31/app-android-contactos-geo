package src.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import src.helper.DbContentProvider;
import src.iface.IContactoDAO;
import src.iface.IContactoSchema;
import src.vo.ContactoVO;

/**
 * Created by msoruco on 06-06-2017.
 */

public class ContactoDAO extends DbContentProvider implements IContactoDAO, IContactoSchema {

    private Cursor cursor;
    private ContentValues initialValues;
    private String  TAG = "ContactoDAO";

    public ContactoDAO(SQLiteDatabase db, Cursor cursor) {
        super(db);
    }

    public ContactoDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public ContactoVO getById(int contactoId) {
        ContactoVO contactoVO = null;
        final String selectionArgs[] = { String.valueOf(contactoId) };
        final String selection = COLUMN_ID + " = ?";

        cursor = super.query(CONTACTO_TABLE, USER_COLUMNS, selection,
                selectionArgs, COLUMN_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                contactoVO = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return contactoVO;
    }

    @Override
    public List<ContactoVO> list() {
        List<ContactoVO> contactosList = new ArrayList<ContactoVO>();
        cursor = super.query(CONTACTO_TABLE, USER_COLUMNS, null, null, COLUMN_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ContactoVO contactoVO = cursorToEntity(cursor);
                contactosList.add(contactoVO);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return contactosList;
    }


    @Override
    public boolean add(ContactoVO contactoVO) {
        // set values
        setContentValue(contactoVO);
        try {
            return super.insert(CONTACTO_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(ContactoVO contactoVO) {
        // set values
        setContentValue(contactoVO);
        try {
            Log.d(TAG, "VO Recibido: "+contactoVO.toString());
            final String selectionArgs[] = { String.valueOf(contactoVO.getId()) };
            final String selection = COLUMN_ID + " = ?";
            return super.update(CONTACTO_TABLE, getContentValue(), selection, selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }

    }

    @Override
    public boolean delete(int contactoId) {
        return false;
    }

    @Override
    public boolean deleteAllUsers() {
        return false;
    }

    @Override
    protected ContactoVO cursorToEntity(Cursor cursor) {

        int idIndex;
        int nombreIndex;
        int emailIndex;
        int telefonoIndex;


        String nombre = "";
        String correo = "";
        int telefono = 0;
        int id = 0;
        ContactoVO contactoVO;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                id = cursor.getInt(idIndex);
            }
            if (cursor.getColumnIndex(COLUMN_NOMBRE) != -1) {
                nombreIndex = cursor.getColumnIndexOrThrow(COLUMN_NOMBRE);
                nombre = cursor.getString(nombreIndex);
            }
            if (cursor.getColumnIndex(COLUMN_EMAIL) != -1) {
                emailIndex = cursor.getColumnIndexOrThrow(COLUMN_EMAIL);
                correo = cursor.getString(emailIndex);
            }
            if (cursor.getColumnIndex(COLUMN_TELEFONO) != -1) {
                telefonoIndex = cursor.getColumnIndexOrThrow(COLUMN_TELEFONO);
                telefono = cursor.getInt(telefonoIndex);
            }
            contactoVO = new ContactoVO(id, nombre, correo, telefono);
            return contactoVO;
        }

        return null;
    }


    private void setContentValue(ContactoVO contactoVO) {
        initialValues = new ContentValues();
        initialValues.put(COLUMN_NOMBRE, contactoVO.getNombre());
        initialValues.put(COLUMN_EMAIL, contactoVO.getCorreo());
        initialValues.put(COLUMN_TELEFONO, contactoVO.getTelefono());
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

}
