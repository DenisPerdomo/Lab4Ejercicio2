package com.example.denis.lab4ejercicio2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText etDatos;
    private TextView txtFile;
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Asociamos los objetos con las variables.
        etDatos = (EditText) findViewById(R.id.etDatos);
        txtFile = (TextView) findViewById(R.id.txtFile);
        //Comprobamos los permisos de la app
        checkPermission();
        //Se muestra un mensaje para indicar que para actualizar hay que clicar en la parte de abajo.
        Toast.makeText(this, "Pulsa la parte inferior para recargar", Toast.LENGTH_SHORT).show();
    }
    private void checkPermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {

            Toast.makeText(this, "This version is not Android 6 or later " + Build.VERSION.SDK_INT, Toast.LENGTH_LONG).show();

        } else {

            int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);

                Toast.makeText(this, "Requesting permissions", Toast.LENGTH_LONG).show();

            }else if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED){
                return;
            }

        }

        return;
    }

    public void guardar (View v){
        try{
            //Se crea el objeto tipo File
            File sd = Environment.getExternalStorageDirectory();
            //Se crea el objeto tipo file, donde definimos la ruta y el nombre del archivo.
            File fp = new File(sd.getAbsolutePath(), "file.txt");
            //Variable para escribir el contenido del archivo.
            String contenido = "";
            //Comprobamos si el fichero existe. Si existe guardamos el contenido.
            if(fp.exists()){
                InputStreamReader file1 = new InputStreamReader(new FileInputStream(fp));
                BufferedReader br = new BufferedReader(file1);
                String line = br.readLine();
                while (line!=null){
                    contenido = contenido + line + "\n";
                    line = br.readLine();
                }
                //Cierre del buffer
                br.close();
                //Cierre Inputstreamreader
                file1.close();
            }
            //Se escribe en el fichero el contenido que existia.
            OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(fp));
            file.write(contenido);
            //Despues se escribe lo que se haya puesto en el EditText
            file.write(etDatos.getText().toString()+"\n");
            //Se guarda lo escrito en el fichero.
            file.flush();
            //Cerramos el archivo
            file.close();
            //Mensaje de guardado correcto.
            Toast.makeText(this, "Se han guardado correctamente los datos.", Toast.LENGTH_SHORT).show();
            //Limpiamos el editText.
            etDatos.setText("");
        }catch (IOException e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void actualizar (View v){
        try{
            //Se crea el objeto tipo File
            File sd = Environment.getExternalStorageDirectory();
            //Se crea el objeto tipo file, donde definimos la ruta y el nombre del archivo.
            File fp = new File(sd.getAbsolutePath(), "file.txt");
            //Variable para escribir el contenido del archivo.
            String contenido = "";
            //Comprobamos si el fichero existe. Si existe guardamos el contenido.
            InputStreamReader file1 = new InputStreamReader(new FileInputStream(fp));
            BufferedReader br = new BufferedReader(file1);
            String line = br.readLine();
            while (line!=null){
                contenido = contenido + line + "\n";
                line = br.readLine();
            }
            //Cierre del buffer
            br.close();
            //Cierre Inputstreamreader
            file1.close();
            //Mostramos en el TextView el contenido del fichero.
            txtFile.setText(contenido);
            //Mensaje de guardado correcto.
            Toast.makeText(this, "Actualizado correctamente.", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
