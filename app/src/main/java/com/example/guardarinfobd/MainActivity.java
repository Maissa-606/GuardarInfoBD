package com.example.guardarinfobd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.guardarinfobd.Configuracion.SQLiteConexion;
import com.example.guardarinfobd.Configuracion.Transacciones;

public class MainActivity extends AppCompatActivity
{

    EditText nombre, apellidos, edad, correo, direccion;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nombre = (EditText) findViewById(R.id.txtnombre_agregar);
        apellidos =(EditText) findViewById(R.id.txtapellidos_agregar);
        edad = (EditText)  findViewById(R.id.txtedad_agregar);
        correo = (EditText) findViewById(R.id.txtcorreo_agregar);
        direccion = (EditText) findViewById(R.id.txtdireccion_agregar);
        Button btnsalvar = (Button) findViewById(R.id.btnguardar_agregar);
        Button btnconsulta=(Button) findViewById(R.id.btnconsulta);

        btnsalvar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AgregarPersonas();
            }
        });
        btnconsulta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ActivityConsultar.class);
                startActivity(intent);
            }
        });


    }

    private void AgregarPersonas()
    {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombres, nombre.getText().toString());
        valores.put(Transacciones.apellidos, apellidos.getText().toString());
        valores.put(Transacciones.edad, edad.getText().toString());
        valores.put(Transacciones.correo, correo.getText().toString());
        valores.put(Transacciones.direccion, direccion.getText().toString());

        Long resultado = db.insert(Transacciones.tablaPersonas,Transacciones.id,valores);
        Toast.makeText(getApplicationContext(), "Registro exitoso: Consulta " + resultado.toString(),Toast.LENGTH_SHORT).show();

        db.close();
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