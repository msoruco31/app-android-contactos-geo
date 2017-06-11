package com.example.msoruco.admincontactos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import src.helper.Database;
import src.vo.ContactoVO;

/**
 * Created by msoruco on 09-06-2017.
 */

public class ListadoActivity extends AppCompatActivity {

    private String TAG = "LIST_ACT";
    private TableLayout tl;
    private TableRow tr;
    private TextView tvId, tvNombre,tvCorreo, tvTelefono;
    public static Database mDb;
    int idContacto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_contactos);

        mDb = new Database(this);
        mDb.open();

        Bundle b = getIntent().getExtras();
        ArrayList<ContactoVO> contactos = b.getParcelableArrayList("contactos");
        Iterator<ContactoVO> iterator = contactos.iterator();



        tl = (TableLayout) findViewById(R.id.maintable);

        /***************************/

        while(iterator.hasNext()){
            ContactoVO contactoVO =  iterator.next();
            Log.d(TAG, contactoVO.toString());

            //Agrego a Vista
            tr = new TableRow(this);
            tr.setClickable(true);

            TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);

            lp.setMargins(10,10,10,10);
            tr.setLayoutParams(lp);

            tvId = new TextView(this);
            tvId.setText(String.valueOf(contactoVO.getId()));
            tr.addView(tvId);  // Adding textView to tablerow.

            tvNombre = new TextView(this);
            tvNombre.setText(contactoVO.getNombre());
            tr.addView(tvNombre);  // Adding textView to tablerow.

            tvCorreo = new TextView(this);
            tvCorreo.setText(contactoVO.getCorreo());
            tr.addView(tvCorreo);  // Adding textView to tablerow.

            tvTelefono = new TextView(this);
            tvTelefono.setText(String.valueOf(contactoVO.getTelefono()));
            tr.addView(tvTelefono);  // Adding textView to tablerow.

            Log.d(TAG, "ID CONTACTO: "+ idContacto+"___"+tvNombre.getText());
            tr.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    TableRow t = (TableRow) v;
                    TextView tvId = (TextView) t.getChildAt(0);
                    int idContacto = Integer.parseInt(tvId.getText().toString());
                    Log.d(TAG, "Editar: "+idContacto);
                    ContactoVO contacto = Database.mContactoDAO.getById(idContacto);
                    Log.d(TAG, "Contcato a editar: "+ contacto.toString());

                    Intent intent = new Intent(ListadoActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("contacto", contacto);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });

            tl.addView(tr, lp);

        }

    }
}
