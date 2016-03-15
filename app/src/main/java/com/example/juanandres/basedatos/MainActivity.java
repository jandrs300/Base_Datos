package com.example.juanandres.basedatos;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button guadar,ver,buscar;
    private EditText Text_nombre, Text_phone, TXT_buscar;
    private ListView lista;
    private DateBaseManager manager;
    private Cursor cursor;
    private SimpleCursorAdapter adapter;

    String[] from;
    int[] to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new DateBaseManager(this);
        lista = (ListView)findViewById(R.id.listView);
        guadar = (Button) findViewById(R.id.btn_guardar);
        ver = (Button)findViewById(R.id.btn_ver);
        buscar = (Button) findViewById(R.id.btn_buscar);
        Text_nombre = (EditText) findViewById(R.id.edtxt_nombre);
        Text_phone = (EditText) findViewById(R.id.edttxt_phone);
        TXT_buscar = (EditText)findViewById(R.id.edtText_nombre_buscar);

        guadar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //manager.insertar(Text_nombre.getText().toString(),Text_phone.getText().toString());
                manager.modificarTelefono(Text_nombre.getText().toString(),Text_phone.getText().toString());
                actualizar();
            }
        });

        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lista.setAdapter(adapter);

            }
        });


        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BuscarTask().execute();
            }
        });
    }

    private void actualizar() {
        from = new String[]{manager.CN_NAME,manager.CN_PHONE};
        to = new int[]{android.R.id.text1,android.R.id.text2};
        cursor = manager.cargarCursorContactos();
        adapter = new SimpleCursorAdapter(this,android.R.layout.two_line_list_item,cursor,from,to,0);
    }

    private class BuscarTask extends AsyncTask<Void,Void,Void>{



        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplication(),"Buscando....",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            cursor = manager.buscarContacto(TXT_buscar.getText().toString());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.changeCursor(cursor);
            Toast.makeText(getApplication(),"Finalizo....",Toast.LENGTH_SHORT).show();
        }
    }
}
