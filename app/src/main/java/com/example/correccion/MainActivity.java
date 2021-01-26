package com.example.correccion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText edtNombre, edtApellido, edtCelular, edtDireccion, edtCodigo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCodigo = (EditText)findViewById(R.id.edtCodigo);
        edtNombre = (EditText)findViewById(R.id.edtNombre);
        edtApellido = (EditText)findViewById(R.id.edtApellido);
        edtCelular = (EditText)findViewById(R.id.edtCelular);
        edtDireccion = (EditText)findViewById(R.id.edtDireccion);

    }

    public void Registrar (View view){
        Sqlite01 sq = new Sqlite01(this, "personas", null,1);
        SQLiteDatabase BaseDeDatos = sq.getWritableDatabase();

        String codigo = edtCodigo.getText().toString();
        String nombre = edtNombre.getText().toString();
        String apellido = edtApellido.getText().toString();
        String celular = edtCelular.getText().toString();
        String direccion = edtDireccion.getText().toString();

        if (!codigo.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !celular.isEmpty() && !direccion.isEmpty()) {
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("apellido", apellido);
            registro.put("celular", celular);
            registro.put("direccion", direccion);

            BaseDeDatos.insert("Contactos", null, registro);

            BaseDeDatos.close();
            edtCodigo.setText("");
            edtNombre.setText("");
            edtApellido.setText("");
            edtCelular.setText("");
            edtDireccion.setText("");

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "DEBES DE LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();

        }
    }

    public void Buscar(View view) {
        Sqlite01 sq = new Sqlite01(this, "personas", null, 1);
        SQLiteDatabase BaseDeDatos = sq.getWritableDatabase();

        String codig = edtCodigo.getText().toString();
            if (!codig.isEmpty()) {
                Cursor fila = BaseDeDatos.rawQuery("select nombre, apellido, celular, direccion from contactos where codigo =" + codig, null);

                if (fila.moveToFirst()) {
                    edtNombre.setText(fila.getString(0));
                    edtApellido.setText(fila.getString(1));
                    edtCelular.setText(fila.getString(2));
                    edtDireccion.setText(fila.getString(3));
                    BaseDeDatos.close();

                } else {
                    Toast.makeText(this, "MO EXISTE EL CONTACTO", Toast.LENGTH_SHORT).show();
                    BaseDeDatos.close();
                }

            } else {
                Toast.makeText(this, "DEBES DE INTRODUCIR EL CODIGO DEL CONTACTO", Toast.LENGTH_SHORT).show();
            }
    }


    public void Eliminar(View view){
        Sqlite01 sq = new Sqlite01(this, "personas", null, 1);
        SQLiteDatabase BaseDeDatos = sq.getWritableDatabase();

        String codig = edtCodigo.getText().toString();

        if (!codig.isEmpty()){

            int cantidad = BaseDeDatos.delete("contactos", "codigo=" + codig, null);
            BaseDeDatos.close();

            edtCodigo.setText("");
            edtNombre.setText("");
            edtApellido.setText("");
            edtCelular.setText("");
            edtDireccion.setText("");

            if (cantidad ==1){
                Toast.makeText(this, "CONTACTO ELIMINADO EXITOSAMENTE", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "EL CONTACTO NO EXISTE", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "DEBES DE INTRODUCIR EL CODIGO DEL CONTACTO", Toast.LENGTH_SHORT).show();
        }

    }

    public void Modificar(View view){
        Sqlite01 sq = new Sqlite01(this, "personas", null, 1);
        SQLiteDatabase BaseDeDatos = sq.getWritableDatabase();

        String codigo = edtCodigo.getText().toString();
        String nombre = edtNombre.getText().toString();
        String apellido = edtApellido.getText().toString();
        String celular = edtCelular.getText().toString();
        String direccion = edtDireccion.getText().toString();

        if (!codigo.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !celular.isEmpty() && !direccion.isEmpty()) {

            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("nombre", nombre);
            registro.put("apellido", apellido);
            registro.put("celular", celular);
            registro.put("direccion", direccion);

            int cantidad = BaseDeDatos.update("contactos", registro, "codigo=" + nombre, null);
            BaseDeDatos.close();

            if (cantidad == 1) {
                Toast.makeText(this, "CONTACTO MODIFICADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El contacto no existe", Toast.LENGTH_SHORT).show();
            }

            } else {
                Toast.makeText(this, "DEBES DE LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();

            }
         }
}


