package com.ejemplos.tema12.basecontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.clienteslibreria.Clientes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClientesSqliteHelper bDSH= new ClientesSqliteHelper(this,"bdClientes",null,1);


    }
}
