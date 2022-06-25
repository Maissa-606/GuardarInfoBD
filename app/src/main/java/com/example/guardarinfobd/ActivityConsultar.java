package com.example.guardarinfobd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.guardarinfobd.Configuracion.SQLiteConexion;
import com.example.guardarinfobd.Configuracion.Transacciones;

public class ActivityConsultar extends AppCompatActivity
{

    SQLiteConexion conexion;
    EditText id, nombre, apellidos, edad, correo, direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        //Llamar a la conexion de bd sqlite
        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);


        Button btnbuscar_consulta = (Button) findViewById(R.id.btnbuscar_consulta);
        Button btneliminar_consulta =  (Button) findViewById(R.id.btneliminar_consulta);
        Button btnactualizar_consulta =  (Button) findViewById(R.id.btnactualizar_consulta);


        id = (EditText) findViewById(R.id.txtid_consulta);

        nombre = (EditText) findViewById(R.id.txtnombre_consulta);

        apellidos = (EditText) findViewById(R.id.txtapellidos_consulta);

        edad = (EditText) findViewById(R.id.txtedad_consulta);

        correo = (EditText) findViewById(R.id.txtcorreo_consulta);

        direccion = (EditText) findViewById(R.id.txtdireccion_consulta);


        btnbuscar_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buscar();

            }
        });

        btnactualizar_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actualizar();
            }
        });

        btneliminar_consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Eliminar();
            }
        });
    }

    private void Buscar()
    {
        SQLiteDatabase db = conexion.getWritableDatabase();

        //Parametros de configuracion de la sentencia SELECT
        String[] params = {id.getText().toString()};

        //Parametro de la busqueda
        String[] fields = {Transacciones.nombres,
                Transacciones.apellidos,
                Transacciones.edad,
                Transacciones.correo,
                Transacciones.direccion};
        String wherecond = Transacciones.id + "=?";
        try
        {
            Cursor cdata = db.query(Transacciones.tablaPersonas, fields, wherecond, params, null, null, null);

            cdata.moveToFirst();

            nombre.setText(cdata.getString(0));
            apellidos.setText(cdata.getString(1));
            edad.setText(cdata.getString(2));
            correo.setText(cdata.getString(3));
            direccion.setText(cdata.getString(4));

            Toast.makeText(getApplicationContext(), "Consulta exitosa", Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex)
        {
            Limpiar();
            Toast.makeText(getApplicationContext(), "Consulta no encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    private void Eliminar()
    {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String [] params = {id.getText().toString()};
        String wherecond = Transacciones.id + "=?";
        db.delete(Transacciones.tablaPersonas, wherecond, params);
        Toast.makeText(getApplicationContext(), "Consulta elminiada", Toast.LENGTH_SHORT).show();
        Limpiar();

    }

    private void Actualizar()
    {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String [] params = {id.getText().toString()};
        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombres, nombre.getText().toString());
        valores.put(Transacciones.apellidos, apellidos.getText().toString());
        valores.put(Transacciones.edad, edad.getText().toString());
        valores.put(Transacciones.correo, correo.getText().toString());
        valores.put(Transacciones.direccion, direccion.getText().toString());
        db.update(Transacciones.tablaPersonas, valores, Transacciones.id + "=?", params);
        Toast.makeText(getApplicationContext(), "Consulta actualizada", Toast.LENGTH_SHORT).show();
        Limpiar();
    }

    private void Limpiar()
    {
        nombre.setText(null);
        apellidos.setText(null);
        edad.setText(null);
        correo.setText(null);
        direccion.setText(null);
    }
}