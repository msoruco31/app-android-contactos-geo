package src.vo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by msoruco on 06-06-2017.
 */

public class ContactoVO implements Parcelable{

    private String nombre;
    private String correo;
    private  int telefono;
    private int id;

    private  ContactoVO(Parcel in) {
        nombre = in.readString();
        correo = in.readString();
        telefono = in.readInt();
        id = in.readInt();
    }

    /**
     * A constructor that initializes the Student object
     **/
    public ContactoVO(int id, String nombre, String correo, int telefono){
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public static final Creator<ContactoVO> CREATOR = new Creator<ContactoVO>() {
        @Override
        public ContactoVO createFromParcel(Parcel in) {
            return new ContactoVO(in);
        }

        @Override
        public ContactoVO[] newArray(int size) {
            return new ContactoVO[size];
        }
    };

    public String getNombre() {
        return nombre;
    }
    public String getCorreo() {
        return correo;
    }
    public int getTelefono() {
        return telefono;
    }
    public int getId() {
        return id;
    }
    @Override
    public String toString() {
        return "ContactoVO{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono=" + telefono +
                ", id=" + id +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(correo);
        dest.writeInt(telefono);
        dest.writeInt(id);
    }
}
