package com.example.msoruco.admincontactos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;


import src.helper.Database;
import src.vo.ContactoVO;

public class MainActivity extends AppCompatActivity  {

    private EditText campoNombre;
    private EditText campoCorreo;
    private EditText campoTelefono;
    private Button botonGuardar;
    private Button botonLimpiar;
    private Button botonContactos;
    public static Database mDb;
    private boolean flagActualiza = false;
    private String TAG = "MAIN_ACT";
    private ContactoVO contactoVO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDb = new Database(this);
        mDb.open();

        setContentView(R.layout.activity_main);
        //Obtengo Botones
        botonGuardar = (Button) findViewById(R.id.botonGuardar);
        botonLimpiar = (Button) findViewById(R.id.botonLimpiar);
        botonContactos = (Button) findViewById(R.id.botonContactos);

        campoNombre = (EditText) findViewById(R.id.txtNombre);
        campoCorreo = (EditText) findViewById(R.id.txtCorreo);
        campoTelefono = (EditText) findViewById(R.id.txtTelefono);


        if(getIntent().getParcelableExtra("contacto") != null){
            Log.d(TAG, "Extra existe");
            flagActualiza = true;
            Bundle b = getIntent().getExtras();
            contactoVO = b.getParcelable("contacto");
            campoNombre.setText(contactoVO.getNombre());
            campoCorreo.setText(contactoVO.getCorreo());
            campoTelefono.setText(String.valueOf(contactoVO.getTelefono()));
        }else{
            Log.d(TAG, "Extra no existe");
        }




        botonGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String nombre = campoNombre.getText().toString().trim();
                String correo = campoCorreo.getText().toString().trim();
                int telefono = Integer.parseInt(campoTelefono.getText().toString().trim());
                String mensaje = null;

                if (correo != "" && nombre != "" && correo != null && nombre != null && mDb != null) {
                    if(flagActualiza){
                        ContactoVO contacto = new ContactoVO(contactoVO.getId(), nombre, correo, telefono);
                        boolean resp = Database.mContactoDAO.update(contacto);
                        if(resp){
                            mensaje =  "Usuario actualizado Correctamente!!";
                        }else {
                            mensaje =  "Problemas al actualizar el usuario!!";
                        }
                    }else{
                        ContactoVO contactoVO = new ContactoVO(0, nombre, correo, telefono);
                        boolean resp = Database.mContactoDAO.add(contactoVO);
                        if(resp){
                            mensaje =  "Usuario guardado Correctamente!!";
                        }else {
                            mensaje =  "Problemas al guardar el usuario!!";
                        }
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        botonLimpiar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                campoNombre.setText("");
                campoTelefono.setText("");
                campoCorreo.setText("");
                Toast toast = Toast.makeText(getApplicationContext(), "Formulario Resetado!!", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        botonContactos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ArrayList<ContactoVO> contactos;
                contactos = (ArrayList<ContactoVO>) Database.mContactoDAO.list();
                // Creating an intent to open the activity StudentViewActivity
                Intent intent = new Intent(MainActivity.this, ListadoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("contactos", contactos);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        mDb.close();
        super.onDestroy();
    }
}
